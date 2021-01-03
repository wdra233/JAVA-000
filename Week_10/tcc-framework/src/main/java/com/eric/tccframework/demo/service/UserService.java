package com.eric.tccframework.demo.service;

import com.eric.tccframework.annotation.TCCAction;
import com.eric.tccframework.context.ThreadContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @TCCAction(tryMethod = "prepare", confirmMethod = "confirm", cancelMethod = "cancel")
    public boolean prepare(boolean success) {
        log.info("user prepare");
        log.info("global transaction id:: " + ThreadContext.get());
        if (success) {
            log.info("User prepare success");
        } else {
            log.info("User prepare failed");
        }
        return success;
    }

    public boolean confirm() {
        log.info("User confirm");
        log.info("global transaction id:: " + ThreadContext.get());
        return true;
    }

    public boolean cancel() {
        log.info("User cancel");
        log.info("global transaction id:: " + ThreadContext.get());
        return true;
    }
}
