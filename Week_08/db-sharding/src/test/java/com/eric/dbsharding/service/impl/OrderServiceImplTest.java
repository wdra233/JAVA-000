package com.eric.dbsharding.service.impl;

import com.eric.dbsharding.bean.Order;
import com.eric.dbsharding.service.OrderService;
import org.assertj.core.util.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {
    @Autowired
    private OrderService orderService;

    @Before
    public void setUp() {
        // add 16 orders to the db
        for(int i = 1; i <= 16; i++) {
            orderService.addOrder(Order.builder().id((long)i).build());
        }
    }

    @Test
    public void deleteOrderById() {
        // delete order from ds1.t_order_1
        orderService.deleteOrderById(1L);
    }

    @Test
    public void testAddOrder() {
        // expect this test order to be inserted into ds0.t_order_0
        orderService.addOrder(Order.builder().id(32L).build());
    }

    @Test
    public void getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        orders.forEach(order -> System.out.println(order.getId()));
    }

    @Test
    public void updateOrder() {
        // update createTime field for order with id 16 in ds0.t_order_0
        orderService.updateOrder(Order.builder().id(16L).createTime(DateUtil.now()).build());
    }
}