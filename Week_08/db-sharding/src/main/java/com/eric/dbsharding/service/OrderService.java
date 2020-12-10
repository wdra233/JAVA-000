package com.eric.dbsharding.service;

import com.eric.dbsharding.bean.Order;

import java.util.List;

public interface OrderService {
    void deleteOrderById(Long id);

    void addOrder(Order oder);

    List<Order> getAllOrders();

    void updateOrder(Order order);
}
