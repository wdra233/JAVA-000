package com.eric.dbsharding.service.impl;

import com.eric.dbsharding.bean.Order;
import com.eric.dbsharding.dao.OrderMapper;
import com.eric.dbsharding.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void deleteOrderById(Long id) {
        orderMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @Override
    public void addOrderWithGeneratedId(Order order) {
        orderMapper.insertWithGeneratedID(order);
    }

    @Transactional
    @Override
    public void addOrder(Order order) {
        orderMapper.insert(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderMapper.selectAll();
    }

    @Transactional
    @Override
    public void updateOrder(Order order) {
        orderMapper.updateByPrimaryKey(order);
    }
}
