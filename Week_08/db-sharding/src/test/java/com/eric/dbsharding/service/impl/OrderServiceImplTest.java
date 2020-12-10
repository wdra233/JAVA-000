package com.eric.dbsharding.service.impl;

import com.eric.dbsharding.bean.Order;
import com.eric.dbsharding.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {
    @Autowired
    private OrderService orderService;

    @Test
    public void deleteOrderById() {
    }

    @Test
    public void testAddOrder() {
        // add 16 orders to the db
        for(int i = 1; i <= 16; i++) {
            orderService.addOrder(Order.builder().id((long)i).build());
        }
    }

    @Test
    public void getAllOrders() {
    }

    @Test
    public void updateOrder() {
    }
}