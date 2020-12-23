package io.kimmking.dubbo.demo.api.services;

import io.kimmking.dubbo.demo.api.entities.BankAccount;

import java.math.BigDecimal;

public interface BankAccountService {
    public void subtractRMBAmount(Long id, BigDecimal rmb);

    public void addRMBAmount(Long id, BigDecimal rmb);

    public void subtractUSDAmount(Long id, BigDecimal usd);

    public void addUSDAmount(Long id, BigDecimal usd);

    public void saveNewAccount(BankAccount bankAccount);

    default BigDecimal convertRMBToUSD(BigDecimal rmb) {
        return rmb.divide(new BigDecimal(7.0));
    }

    default BigDecimal convertUSDToRMB(BigDecimal usd) {
        return usd.multiply(new BigDecimal(7.0));
    }
}
