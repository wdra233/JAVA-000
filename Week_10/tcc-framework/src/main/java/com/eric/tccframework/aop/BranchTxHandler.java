package com.eric.tccframework.aop;

import com.eric.tccframework.annotation.TCCAction;
import com.eric.tccframework.context.ThreadContext;
import com.eric.tccframework.mapper.TxInfoMapper;
import com.eric.tccframework.service.TxInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Proc;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.mapping.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class BranchTxHandler {
    @Autowired
    private TxInfoService txInfoService;

    @Pointcut("@annotation(com.eric.tccframework.annotation.TCCAction)")
    public void branchTransaction() {}

    @Before("branchTransaction()")
    public void branchTransactionHandler(JoinPoint joinPoint) {
        log.info("branch transaction handler: {}", ThreadContext.get());

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        TCCAction tccAnnotation = method.getAnnotation(TCCAction.class);
        // get the confirmMethod and cancelMethod from annotation
        String confirmMethod = tccAnnotation.confirmMethod();
        String cancelMethod = tccAnnotation.cancelMethod();

        // Get the target Class
        Class<?> klass = joinPoint.getTarget().getClass();
        String className = klass.getName();

        txInfoService.registerBranchTx(ThreadContext.get(), className, confirmMethod, cancelMethod);
    }
}
