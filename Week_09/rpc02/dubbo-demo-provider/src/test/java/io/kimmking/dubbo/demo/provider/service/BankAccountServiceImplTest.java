package io.kimmking.dubbo.demo.provider.service;

import io.kimmking.dubbo.demo.api.entities.BankAccount;
import io.kimmking.dubbo.demo.provider.dao.BankAccountMapper;
import org.assertj.core.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BankAccountServiceImplTest {

    private static BankAccount bankAccount = BankAccount.builder()
            .accountName("eric")
            .rmbAmount(new BigDecimal(100))
            .usdAmount(new BigDecimal(100))
            .createTime(DateUtil.now())
            .updateTime(DateUtil.now())
            .build();
    @Autowired
    private BankAccountServiceImpl bankAccountService;

    @Test
    public void subtractRMBAmount() {
        bankAccountService.subtractRMBAmount(1L, new BigDecimal(100));
    }

    @Test
    public void subtractUSDAmount() {
        bankAccountService.subtractRMBAmount(1L, new BigDecimal(100));
    }

    @Test
    public void saveNewAccount() {
        bankAccountService.saveNewAccount(bankAccount);
    }
}