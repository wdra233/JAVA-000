package com.eric.tccframework.service;

import com.eric.tccframework.mapper.TxInfoMapper;
import com.eric.tccframework.model.TransactionInfo;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class TxInfoServiceTest {
    @Autowired
    private TxInfoMapper txInfoMapper;

    @org.junit.jupiter.api.Test
    public void testInsert() {
        TransactionInfo txinfo = new TransactionInfo();
        txInfoMapper.insertOne(txinfo);
    }

}