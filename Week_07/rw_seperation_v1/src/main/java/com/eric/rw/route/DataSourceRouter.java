package com.eric.rw.route;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 实现通过集成Spring提供的AbstractRoutingDataSource，只需要实现determineCurrentLookupKey方法即可
 */
public class DataSourceRouter extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceHolder.get();
    }
}
