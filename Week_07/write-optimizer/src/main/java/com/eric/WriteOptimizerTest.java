package com.eric;

import com.eric.batch.dao.OrderDao;
import com.eric.batch.dao.OrderDaoImpl;

public class WriteOptimizerTest {
    public static void main(String[] args) {
        OrderDao orderDao = new OrderDaoImpl();
        long start = System.currentTimeMillis();
        orderDao.batchInsertOrder();
        long duration = System.currentTimeMillis() - start;
        System.out.println("batch insert duration is: " + duration / 1000.0 + "s");
    }
}
