package io.kimmking.dubbo.demo.consumer.service;

import io.kimmking.dubbo.demo.api.entities.TransactionInfo;

public interface TransactionService {
    void initTransaction(TransactionInfo transactionInfo);
}
