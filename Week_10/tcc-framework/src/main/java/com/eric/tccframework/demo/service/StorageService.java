package com.eric.tccframework.demo.service;

import com.eric.tccframework.annotation.TCCAction;
import com.eric.tccframework.context.ThreadContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StorageService {
    @TCCAction(tryMethod = "prepare", confirmMethod = "confirm", cancelMethod = "cancel")
    public boolean prepare(boolean status) {
        log.info("store prepare");
        log.info("global transaction id:: " + ThreadContext.get());
        if (status) {
            log.info("Store prepare success");
        } else {
            log.info("Store prepare failed");
        }
        return status;
    }

    public boolean confirm() {
        log.info("Store commit");
        log.info("global transaction id:: " + ThreadContext.get());
        return true;
    }

    public boolean cancel() {
        log.info("Store cancel");
        log.info("global transaction id:: " + ThreadContext.get());
        return true;
    }


}
