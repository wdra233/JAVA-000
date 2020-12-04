package com.eric.rw.dao;

import com.eric.rw.bean.Order;
import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer oid);

    int insert(Order record);

    Order selectByPrimaryKey(Integer oid);

    List<Order> selectAll();

    int updateByPrimaryKey(Order record);
}