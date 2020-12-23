package io.kimmking.dubbo.demo.provider.service;

import io.kimmking.dubbo.demo.api.entities.FreezeAccount;
import io.kimmking.dubbo.demo.api.services.FreezeAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FreezeAccountServiceImplTest {

    private static FreezeAccount freezeAccount = FreezeAccount.builder().accountName("eric").amount(new BigDecimal(100)).currencyType("USD").version(1L).build();

    @Autowired
    private FreezeAccountService freezeAccountService;

    @Test
    public void saveNewAccount() {
        freezeAccountService.saveNewAccount(freezeAccount);
    }

    @Test
    public void updateStatus() {
        freezeAccountService.updateStatus(1L, "success");
    }
}