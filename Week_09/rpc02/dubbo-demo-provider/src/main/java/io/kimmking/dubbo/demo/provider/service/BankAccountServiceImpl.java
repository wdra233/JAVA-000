package io.kimmking.dubbo.demo.provider.service;

import io.kimmking.dubbo.demo.api.entities.BankAccount;
import io.kimmking.dubbo.demo.api.exceptions.InsufficientBalanceException;
import io.kimmking.dubbo.demo.api.exceptions.OpsFailureException;
import io.kimmking.dubbo.demo.api.services.BankAccountService;
import io.kimmking.dubbo.demo.provider.dao.BankAccountMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Service
@DubboService
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountMapper bankAccountMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void subtractRMBAmount(Long id, BigDecimal rmb) {
        BankAccount bankAccount = bankAccountMapper.selectByPrimaryKey(id);
        if (bankAccount.getRmbAmount().compareTo(rmb) < 0) {
            throw new InsufficientBalanceException("Insufficient RMB balance");
        }
        BigDecimal balance = bankAccount.getRmbAmount().subtract(rmb);
        int result = bankAccountMapper.updateRMBAmountById(id, balance);
        if (result != 1) {
            throw new OpsFailureException("Unable to update rmb amount!");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addRMBAmount(Long id, BigDecimal rmb) {
        BankAccount bankAccount = bankAccountMapper.selectByPrimaryKey(id);
        BigDecimal balance = bankAccount.getRmbAmount().add(rmb);
        int result = bankAccountMapper.updateRMBAmountById(id, balance);
        if (result != 1) {
            throw new OpsFailureException("Unable to update rmb amount!");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void subtractUSDAmount(Long id, BigDecimal usd) {
        BankAccount bankAccount = bankAccountMapper.selectByPrimaryKey(id);
        if (bankAccount.getUsdAmount().compareTo(usd) < 0) {
            throw new InsufficientBalanceException("Insufficient RMB balance");
        }
        BigDecimal balance = bankAccount.getUsdAmount().subtract(usd);
        int result = bankAccountMapper.updateUSDAmountById(id, balance);
        if (result != 1) {
            throw new OpsFailureException("Unable to update rmb amount!");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addUSDAmount(Long id, BigDecimal usd) {
        BankAccount bankAccount = bankAccountMapper.selectByPrimaryKey(id);
        BigDecimal balance = bankAccount.getUsdAmount().add(usd);
        int result = bankAccountMapper.updateUSDAmountById(id, balance);
        if (result != 1) {
            throw new OpsFailureException("Unable to update usd amount!");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveNewAccount(BankAccount bankAccount) {
        int result = bankAccountMapper.insert(bankAccount);
        if (result !=1 ) {
            throw new OpsFailureException("Unable to save new account!!");
        }
    }
}
