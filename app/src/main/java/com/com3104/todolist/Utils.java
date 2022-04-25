package com.com3104.todolist;

public class Utils {
    public static String formatChineseDate(String s) {
        String[] temp = s.split("-");
        return removeZero(temp[0]) + "年" + removeZero(temp[1]) + "月" + removeZero(temp[2]) + "日";
    }

    public static String removeZero(String s) {
        while (s.charAt(0) == '0') {
            s = s.substring(1);
        }
        return s;
    }
}
