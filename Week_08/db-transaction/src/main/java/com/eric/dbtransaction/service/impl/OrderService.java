package com.eric.dbtransaction.service.impl;


import com.eric.dbtransaction.dao.OrderMapper;
import com.eric.dbtransaction.domain.Order;
import com.eric.dbtransaction.service.IOrderService;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.RollbackException;
import java.util.List;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Transactional(rollbackFor = RollbackException.class)
    @ShardingTransactionType(TransactionType.XA)
    @Override
    public void addTwoOrdersThrowException(Order order1, Order order2) throws RollbackException {
        orderMapper.insertWithPrimaryKey(order1);
        orderMapper.insertWithPrimaryKey(order2);
        throw new RollbackException("Insert failed, rollback");
    }

    @Transactional(rollbackFor = RollbackException.class)
    @ShardingTransactionType(TransactionType.XA)
    @Override
    public void addOrderWithGeneratedId(Order order) {
        orderMapper.insertWithNoPrimaryKey(order);

    }

    @Override
    public List<Order> getAllOrders() {
        return orderMapper.findAllOrders();
    }


}
