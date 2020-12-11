package com.eric.dbtransaction.service.impl;

import com.eric.dbtransaction.domain.Order;
import com.eric.dbtransaction.service.IOrderService;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.RollbackException;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceTest {
    @Autowired
    private IOrderService orderService;

    @Before
    public void setUp() {
        for(int i = 0; i < 6; i++) {
            orderService.addOrderWithGeneratedId(Order.builder().build());
        }
    }

    @Test
    public void testAddTwoOrdersThrowException() {
        try {
            // failed to insert id#1 into ds_1.t_order_1 or id#2 into ds_0.t_order_2
            orderService.addTwoOrdersThrowException(Order.builder().id(1L).build(), Order.builder().id(2L).build());
        } catch (RollbackException e) {
            log.error("Transaction rollback", e);
        }

    }

    @Test
    public void getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        orders.forEach(order -> {
            System.out.println(order.getId());
        });
    }
}