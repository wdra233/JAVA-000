package com.eric.tccframework.demo;

import com.eric.tccframework.annotation.TCCTx;
import com.eric.tccframework.context.ThreadContext;
import com.eric.tccframework.demo.service.StorageService;
import com.eric.tccframework.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DemoService {
    @Autowired
    private StorageService storeService;

    @Autowired
    private UserService userService;

    @TCCTx
    public void txSuccess() {
        log.info("global transaction id:: " + ThreadContext.get());
        if (!userService.prepare(true)) {
            log.info("user try failed");
            throw new RuntimeException("user prepare failed!");
        }
        log.info("user try success");
        if (!storeService.prepare(true)) {
            log.info("store try failed");
            throw new RuntimeException("store prepare failed");
        }
        log.info("store try success");
    }

    @TCCTx
    public void txFailed() {
        log.info("global transaction id:: " + ThreadContext.get());
        if (!userService.prepare(true)) {
            log.info("user try failed");
            throw new RuntimeException("user prepare failed!");
        }
        log.info("user try success");
        if (!storeService.prepare(false)) {
            log.info("store try failed");
            throw new RuntimeException("store prepare failed");
        }
        log.info("store try success");
    }
}
