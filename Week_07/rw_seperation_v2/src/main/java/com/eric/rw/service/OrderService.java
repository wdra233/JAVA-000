package com.eric.rw.service;

import com.eric.rw.bean.Order;
import com.eric.rw.dao.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    public Order findOrderById(int id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    public void addOneOrder(Order order) {
        orderMapper.insert(order);
    }
}
