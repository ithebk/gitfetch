package com.ithebk.gitfetcho.utils;

import java.util.Date;

public class Utils {

    public static boolean isEmpty(String str) {
        return str==null || str.isEmpty();
    }
    public static long secondDifference(Date date1, Date date2) {
        return Math.abs(date1.getTime() - date2.getTime())/1000;
    }
}
