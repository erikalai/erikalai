package com.com3104.todolist;

import android.content.SharedPreferences;

public class Global {
    public static final String APP_NAME = "TODO List";
    public static final Theme[] THEMES = new Theme[] {
            new Theme("Dark", "#000000", "#333333", "#999999", "#FFFFFF"),
            new Theme("White", "#EEEEEE", "#999999", "#333333", "#000000")
    };
    public static Theme theme;

    public static SharedPreferences sharedPreferences;

    public static DBOpenHelper myDb;
}
