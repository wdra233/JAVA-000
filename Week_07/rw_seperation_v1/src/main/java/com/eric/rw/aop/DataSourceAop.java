package com.eric.rw.aop;

import com.eric.rw.route.DataSourceHolder;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAop implements Ordered {

    @Override
    public int getOrder() {
        return -9999;
    }

    @Pointcut("@annotation(com.eric.rw.annotation.UseSlaveDataSource)")
    public void readPointCut() {}

    @Before("readPointCut()")
    public void readOnly() {
        DataSourceHolder.slave();
    }
}
