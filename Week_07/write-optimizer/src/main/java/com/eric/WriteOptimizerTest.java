package com.eric;

import com.eric.batch.dao.OrderDao;
import com.eric.batch.dao.OrderDaoImpl;
import com.eric.load.CSVFileLoader;
import com.eric.utils.FileUtils;

public class WriteOptimizerTest {
    public static void main(String[] args) throws Exception {
//        OrderDao orderDao = new OrderDaoImpl();
        CSVFileLoader csvFileLoader = new CSVFileLoader();
        long start = System.currentTimeMillis();
        csvFileLoader.loadOrdersFromResource(FileUtils.getAbsoluteFilePath("write-optimizer/src/main/resources/orders.csv"));
        long duration = System.currentTimeMillis() - start;
        System.out.println("batch insert duration is: " + duration / 1000.0 + "s");
    }
}
