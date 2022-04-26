package com.com3104.todolist;

import android.content.SharedPreferences;

import java.util.ArrayList;

public class Global {
    public static final String APP_NAME = "記低";
    public static final Theme[] THEMES = new Theme[] {
            new Theme("活力黃", "#FFFFFF", "#FFC11E", "#FFC11E", "#606060", "#000000"),
            new Theme("神秘黑", "#000000", "#000000", "#000000", "#606060", "#FFC11E")
    };
    public static Theme theme;

    public static SharedPreferences sharedPreferences = null;

    public static DBOpenHelper myDb;

    public static ArrayList<String> todos = new ArrayList<>();
    public static ArrayList<Integer> todoIDs = new ArrayList<>();
    public static ArrayList<ArrayList<Subtask>> todoSubtasks = new ArrayList<>();




    public static String[] reminder = new String[] {
            "冇",
            "即刻提你",
            "5分鐘前",
            "10分鐘前",
            "15分鐘前",
            "30分鐘前",
            "1粒鐘前",
            "2粒鐘前",
            "1日前",
            "2日前",
            "1禮拜前"
    };

    public static String[] reminderQ = new String[] {
            "冇？",
            "即刻提你？",
            "5分鐘前？",
            "10分鐘前？",
            "15分鐘前？",
            "30分鐘前？",
            "1粒鐘前？",
            "2粒鐘前？",
            "1日前？",
            "2日前？",
            "1禮拜前？"
    };

    public static long reminderDelay[] = new long[] {
            0, // None
            0, // By the time
            1000 * 60 * (-5), // 5 minutes before
            1000 * 60 * (-10), // 10 minutes before
            1000 * 60 * (-15), // 15 minutes before
            1000 * 60 * (-30), // 30 minutes before
            1000 * 60 * (-60), // 1 hour before
            1000 * 60 * 60 * (-2), // 2 hours before
            1000 * 60 * 60 * (-24), // 1 day before
            1000 * 60 * 60 * 24 * (-2), // 2 days before
            1000 * 60 * 60 * 24 * (-7), // 1 week before
    };

    public static String[] importance = new String[] {
            "輕鬆搞掂",
            "好似重要",
            "❗重要❗",
            "⚠️好很重要⚠️️"
    };

    public static String[] importanceQ = new String[] {
            "輕鬆搞掂❔",
            "好似重要❔",
            "❗重要❗❔",
            "‼️️好很重要‼️️️❔"
    };

    public static String[] importancePrefix = new String[] {
            "",
            "",
            "❗",
            "‼️"
    };


    public static String[] getThemeNames() {
        int n = THEMES.length;
        String[] names = new String[n];
        for (int i = 0; i < n; i++) {
            names[i] = THEMES[i].getName();
        }
        return names;
    }
}
