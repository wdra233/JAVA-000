package com.eric.utils;

import java.io.File;

public class FileUtils {
    public static String getAbsoluteFilePath(String fileName) {
        File file = new File(fileName);
        return file.getAbsolutePath();
    }
}
