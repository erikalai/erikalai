package com.example.todolist;

import android.graphics.Color;

public class Theme {
    private String name;
    private String windowBg;
    private String bg;
    private String fg;
    private String hint;

    public Theme() {}

    public Theme(String name, String windowBg, String bg, String hint, String fg) {
        this.name = name;
        this.windowBg = windowBg;
        this.bg = bg;
        this.hint = hint;
        this.fg = fg;
    }

    public String getName() {
        return name;
    }

    public String getWindowBg() {
        return windowBg;
    }

    public String getBg() {
        return bg;
    }

    public String getHint() {
        return hint;
    }

    public String getFg() {
        return fg;
    }

    public int getWindowBgColor() {
        return Color.parseColor(windowBg);
    }

    public int getBgColor() {
        return Color.parseColor(bg);
    }

    public int getHintColor() {
        return Color.parseColor(hint);
    }

    public int getFgColor() {
        return Color.parseColor(fg);
    }
}
