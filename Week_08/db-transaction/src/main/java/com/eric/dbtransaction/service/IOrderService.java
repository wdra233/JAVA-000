package com.eric.dbtransaction.service;


import com.eric.dbtransaction.domain.Order;

import javax.transaction.RollbackException;
import java.util.List;

public interface IOrderService {
    void addTwoOrdersThrowException(Order oder1, Order order2) throws RollbackException;

    void addOrderWithGeneratedId(Order order);

    List<Order> getAllOrders();
}
