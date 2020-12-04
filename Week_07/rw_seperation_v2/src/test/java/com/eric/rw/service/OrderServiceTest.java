package com.eric.rw.service;

import com.eric.rw.bean.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;


    @Test
    public void findOrderById() {
        Order order = orderService.findOrderById(100);
        System.out.println("---------" + order.getDetails() + "-------------------");
    }

    @Test
    public void addOneOrder() {
        orderService.addOneOrder(new Order(null, "order_2"));
    }
}