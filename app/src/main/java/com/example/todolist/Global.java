package com.example.todolist;

public class Global {
    public static final String APP_NAME = "TODO List";
    public static final Theme[] THEMES = new Theme[] {
            new Theme("Dark", "#000000", "#333333", "#999999", "#FFFFFF"),
            new Theme("White", "#EEEEEE", "#999999", "#333333", "#000000")
    };
    public static Theme theme = THEMES[0];
}
