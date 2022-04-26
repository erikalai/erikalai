package com.com3104.todolist;

import java.util.ArrayList;

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

    public static ArrayList<String> getSubtasksTitles(ArrayList<Subtask> subtasks) {
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0, n = subtasks.size(); i < n; i++) {
            temp.add(subtasks.get(i).getTitle());
        }
        return temp;
    }
}
