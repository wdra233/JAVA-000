package com.eric.tccframework.service;


import com.eric.tccframework.constants.AppConstants;
import com.eric.tccframework.mapper.TxInfoMapper;
import com.eric.tccframework.model.TransactionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.List;

import static com.eric.tccframework.constants.AppConstants.*;

@Service
@Slf4j
public class TxInfoService {
    @Autowired
    private TxInfoMapper txInfoMapper;

    public Boolean handleTxInfo(String txid) {
        List<TransactionInfo> txinfos = txInfoMapper.selectByTXID(txid);

        boolean executeConfirm = true;
        for (TransactionInfo txinfo : txinfos) {
            if (txinfo.getStatus() == TCC_STATUS_TRY_FAILED || txinfo.getStatus() == TCC_STATUS_CONFIRM_FAILED) {
                executeConfirm = false;
                break;
            }
        }

        if(executeConfirm) {
            return executeMethod(txinfos, CONFIRM_METHOD);
        } else {
            return executeMethod(txinfos, CANCEL_METHOD);
        }



    }

    private Boolean executeMethod(List<TransactionInfo> txinfos, String methodType) {
        for (TransactionInfo txinfo : txinfos) {
            String methodName = null;
            if (methodType.equals(CONFIRM_METHOD)) {
                methodName = txinfo.getConfirmMethod();
            } else {
                methodName = txinfo.getCancelMethod();
            }

            try {
                Class<?> klass = Class.forName(txinfo.getClassName());

                Method method = klass.getDeclaredMethod(methodName);
                Object result = method.invoke(klass.newInstance());
                log.info("result after executing: {}", result.toString());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public void registerBranchTx(String txid, String className, String confirmMethod, String cancelMethod) {
        TransactionInfo txinfo = TransactionInfo.builder()
                .txid(txid)
                .status(AppConstants.TCC_STATUS_INIT)
                .className(className)
                .confirmMethod(confirmMethod)
                .cancelMethod(cancelMethod)
                .createTime(new Timestamp(System.currentTimeMillis()))
                .updateTime(new Timestamp(System.currentTimeMillis()))
                .build();
        txInfoMapper.insertOne(txinfo);

    }
}
