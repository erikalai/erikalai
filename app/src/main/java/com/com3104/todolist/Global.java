package com.com3104.todolist;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Build;

public class Global {
    public static final String APP_NAME = "TODO List";
    public static final Theme[] THEMES = new Theme[] {
            new Theme("Dark", "#000000", "#333333", "#999999", "#FFFFFF"),
            new Theme("White", "#EEEEEE", "#999999", "#333333", "#000000")
    };
    public static Theme theme;

    public static SharedPreferences sharedPreferences;

    public static DBOpenHelper myDb;

    public static String[] reminder = new String[] {
            "None",
            "By the time",
            "5 minutes before",
            "10 minutes before",
            "15 minutes before",
            "30 minutes before",
            "1 hour before",
            "2 hours before",
            "1 day before",
            "2 days before",
            "1 week before"
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
            "Normal",
            "❗Important❗",
            "‼️Very important‼️"
    };
}
