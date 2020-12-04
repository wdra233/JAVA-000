package com.eric.rw.service;

import com.eric.rw.bean.Order;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void testAddOneOrder() {
        orderService.addOneOrder(new Order(5, "order_5"));
    }

    @Test
    public void testGetOrderById() {
        Order order = orderService.findOrderById(100);
        assertEquals("order_100", order.getDetails());
    }
}