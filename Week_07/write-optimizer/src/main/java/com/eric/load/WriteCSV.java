package com.eric.load;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteCSV {
    // generate 1000000 records and write them to csv file
    public static void generateCSVFile() {

        File csvFile = new File("write-optimizer/src/main/resources/orders.csv");
        try(BufferedWriter csvWriter = new BufferedWriter(new FileWriter(csvFile, false))) {
            String header = "oid,detail\n";
            csvWriter.write(header);
            for(int i = 1; i <= 1000000; i++) {
                StringBuilder singleLine = new StringBuilder();
                singleLine.append(i);
                singleLine.append(",");
                singleLine.append("order_" + i);
                singleLine.append("\n");
                csvWriter.write(singleLine.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        generateCSVFile();
        long duration = System.currentTimeMillis() - start;
        System.out.println("The duration time is: " + duration / 1000.0 + "s");
    }
}
