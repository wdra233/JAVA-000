package com.eric.dao;

import com.eric.bean.Order;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class OrderMapperTest {

    @Autowired
    private OrderMapper mapper;

    @Autowired
    private SqlSession sqlSession;

    @Test
    public void testBatchInsert() {
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        long start = System.currentTimeMillis();
        for(int i = 0; i < 1000000; i++) {
            mapper.insert(new Order());
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println("The duration time is " + duration / 1000.0 + "s");
    }
}