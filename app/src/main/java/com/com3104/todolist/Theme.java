package com.com3104.todolist;

import android.graphics.Color;
import java.util.HashMap;

public class Theme {
    private String name;
    private HashMap<String, String> color;

    public Theme() {}

    public Theme(String name, HashMap<String, String> color) {
        this.name = name;
        this.color = color;
    }


    public String getName() {
        return name;
    }

    public int getColor(String color) {
        return Color.parseColor(this.color.get(color));
    }

    public String getColorCode(String color) {
        return this.color.get(color);
    }
}
