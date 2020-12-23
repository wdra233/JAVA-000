package io.kimmking.dubbo.demo.consumer.service;


import io.kimmking.dubbo.demo.api.entities.BankAccount;
import io.kimmking.dubbo.demo.api.entities.FreezeAccount;
import io.kimmking.dubbo.demo.api.entities.TransactionInfo;
import io.kimmking.dubbo.demo.api.services.BankAccountService;
import io.kimmking.dubbo.demo.api.services.FreezeAccountService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class TransactionServiceImpl implements TransactionService {

    @DubboReference(check = false)
    private BankAccountService bankAccountService;

    @DubboReference(check = false)
    private FreezeAccountService freezeAccountService;


    @Override
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    @Transactional(rollbackFor = RuntimeException.class)
    public void initTransaction(TransactionInfo transactionInfo) {
        FreezeAccount fromFreeze = buildFreezeAccount(transactionInfo.getFromAccountId(), transactionInfo.getFromFreezeId(), transactionInfo.getCurrencyType(), transactionInfo.getAmount());
        FreezeAccount toFreeze = buildFreezeAccount(transactionInfo.getToAccountId(), transactionInfo.getToFreezeId(), transactionInfo.getCurrencyType(), transactionInfo.getAmount());
        freezeAccountService.saveNewAccount(fromFreeze);
        freezeAccountService.saveNewAccount(toFreeze);
        Long fromAccountId = transactionInfo.getFromAccountId();
        BigDecimal amount = transactionInfo.getAmount();
        if(transactionInfo.getCurrencyType().equals("USD")) {
            // subtract amount
            bankAccountService.subtractUSDAmount(fromAccountId, amount);
        } else {
            bankAccountService.subtractRMBAmount(fromAccountId, amount);
        }
    }

    public void confirm(TransactionInfo transactionInfo) {
        freezeAccountService.updateStatus(transactionInfo.getFromFreezeId(), "SUCCESS");
        freezeAccountService.updateStatus(transactionInfo.getToFreezeId(), "SUCCESS");
        Long toAccountId = transactionInfo.getToAccountId();
        BigDecimal amount = transactionInfo.getAmount();
        if(transactionInfo.getCurrencyType().equals("USD")) {
            bankAccountService.addRMBAmount(toAccountId, bankAccountService.convertUSDToRMB(amount));
        } else {
            bankAccountService.addUSDAmount(toAccountId, bankAccountService.convertRMBToUSD(amount));
        }

    }

    public void cancel(TransactionInfo transactionInfo) {
        freezeAccountService.updateStatus(transactionInfo.getFromFreezeId(), "FAIL");
        freezeAccountService.updateStatus(transactionInfo.getToFreezeId(), "FAIL");
    }

    private FreezeAccount buildFreezeAccount(Long fromAccountId, String freezeId, String currencyType, BigDecimal amount) {
        return FreezeAccount.builder()
                .accountId(fromAccountId)
                .freezeId(freezeId)
                .amount(amount)
                .currencyType(currencyType)
                .state("INIT")
                .version(1L)
                .createTime(new Date())
                .updateTime(new Date())
                .build();
    }
}
