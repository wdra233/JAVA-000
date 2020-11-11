package com.eric.concur.util;

public class FiboUtil {
    public static int sum(int num) {
        if ( num <= 2)
            return 1;
        int a = 1, b = 1;
        int ans = 0;
        for(int i = 3; i <= num; i++) {
            ans = a + b;
            a = b;
            b = ans;
        }
        return ans;
    }
}
