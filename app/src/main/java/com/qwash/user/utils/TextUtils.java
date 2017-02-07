package com.qwash.user.utils;

public class TextUtils {

    public static boolean isNullOrEmpty(String str) {
        return (str == null || str.equals("null") || str.equals(""));
    }

    public static boolean isNullOrEmpty(int str) {
        return str == 0 ;
    }
}
