package io.kimmking.dubbo.demo.provider.service;

import io.kimmking.dubbo.demo.api.entities.FreezeAccount;
import io.kimmking.dubbo.demo.api.exceptions.OpsFailureException;
import io.kimmking.dubbo.demo.api.services.FreezeAccountService;
import io.kimmking.dubbo.demo.provider.dao.FreezeAccountMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@DubboService
public class FreezeAccountServiceImpl implements FreezeAccountService {
    @Autowired
    private FreezeAccountMapper freezeAccountMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveNewAccount(FreezeAccount account) {
        int result = freezeAccountMapper.insert(account);
        if (result != 1) {
            throw new OpsFailureException("Unable to save new account");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatus(Long id, String status) {
        int result = freezeAccountMapper.updateByPrimaryKey(id, status);
        if (result != 1) {
            throw new OpsFailureException("Unable to update status for freeze table");
        }
    }

}
