package com.eric.rw.service;

import com.eric.rw.annotation.UseSlaveDataSource;
import com.eric.rw.bean.Order;
import com.eric.rw.dao.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @UseSlaveDataSource
    public List<Order> getAllOrders() {
        return orderMapper.selectAll();
    }

    @UseSlaveDataSource
    public Order findOrderById(int id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    public void addOneOrder(Order order) {
        orderMapper.insert(order);
    }
}
