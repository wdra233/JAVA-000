package io.kimmking.dubbo.demo.api.services;

import io.kimmking.dubbo.demo.api.entities.FreezeAccount;

public interface FreezeAccountService {
    public void saveNewAccount(FreezeAccount account);

    public void updateStatus(Long id, String status);
}
