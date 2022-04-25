package com.com3104.todolist;

import android.graphics.Color;

public class Theme {
    private String name;
    private String windowBg;
    private String bg;
    private String fg;
    private String hint;
    private String btFg;

    public Theme() {}

    public Theme(String name, String windowBg, String bg, String hint, String fg, String btFg) {
        this.name = name;
        this.windowBg = windowBg;
        this.bg = bg;
        this.hint = hint;
        this.fg = fg;
        this.btFg = btFg;
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

    public String getBtFg() {
        return btFg;
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

    public int getBtFgColor() {
        return Color.parseColor(btFg);
    }
}
