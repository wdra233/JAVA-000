package com.eric.tccframework.aop;

import com.eric.tccframework.constants.AppConstants;
import com.eric.tccframework.context.ThreadContext;
import com.eric.tccframework.mapper.TxInfoMapper;
import com.eric.tccframework.model.TransactionInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.HashMap;

import static com.eric.tccframework.constants.AppConstants.*;

@Aspect
@Component
@Slf4j
public class GlobalTxHandler {

    @Autowired
    private TxInfoMapper txInfoMapper;

    @Pointcut("@annotation(com.eric.tccframework.annotation.TCCTx)")
    public void globalTx() {}

    @Around("globalTx()")
    public void globalTxhandler(ProceedingJoinPoint joinPoint) throws UnknownHostException {
        log.info("init global transaction");
        // set the txid to the current thread
        String txid = createTXID();
        ThreadContext.set(txid);
        try {
            // try method executed
            joinPoint.proceed();
        } catch (Throwable throwable) {
            // try failed, update status for all branch transactions
            log.info("Global transaction status :: try method failed");
            updateBranchStatus(txid, TCC_STATUS_TRY_FAILED);


            log.info(txid + " will rollback try method");
            execNextStep(txid);
            ThreadContext.remove();
            return;
        }

        updateBranchStatus(txid, TCC_STATUS_TRY_SUCCESS);
        log.info("Now executing confirm method");
        if(!execNextStep(txid)) {
            log.info("confirmMethod failed");
            updateBranchStatus(txid, TCC_STATUS_CONFIRM_FAILED);
            log.info("Now executing cancel method");
            execNextStep(txid);
            ThreadContext.remove();
            return;
        }
        updateBranchStatus(txid, TCC_STATUS_CONFIRM_SUCCESS);
        ThreadContext.remove();
        return;

    }

    private void updateBranchStatus(String txid, int TCCActionStatus) {
        TransactionInfo txinfo = TransactionInfo.builder()
                .txid(txid)
                .status(TCCActionStatus)
                .updateTime(new Timestamp(System.currentTimeMillis()))
                .build();
        txInfoMapper.updateOne(txinfo);
    }

    private boolean execNextStep(String txid) {
        RestTemplate restTemplate = new RestTemplate();
        String[] split = txid.split(":");
        String localAddress = split[0];
        String port = split[1];

        String targetUrl = "http://" + localAddress + ":" + port + "/tm/execNext?txid=" + txid;
        Boolean response = restTemplate.getForObject(targetUrl, Boolean.class, new HashMap<>());

        if(response == null || !response.booleanValue()) {
            log.error("execute next step failed");
            return false;
        } else {
            log.info("execute next step success!!");
            return true;
        }
    }

    private String createTXID() throws UnknownHostException {
        String localAddress = Inet4Address.getLocalHost().getHostAddress();
        String currentTimeStamp = String.valueOf(System.currentTimeMillis());
        return localAddress + ":8080:" + currentTimeStamp;
    }

}
