package com.com3104.todolist;

public class Subtask {
    private String title;
    private String note;

    public Subtask() {}

    public Subtask(String title, String note) {
        this.title = title;
        this.note = note;
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }
}
