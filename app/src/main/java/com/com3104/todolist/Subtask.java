package com.com3104.todolist;

public class Subtask {
    private String title;
    private String note;
    private boolean done;

    public Subtask() {}

    public Subtask(String title, String note, boolean done) {
        this.title = title;
        this.note = note;
        this.done = done;
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }

    public boolean getDone() {
        return done;
    }


    public void setDone(boolean done) {
        this.done = done;
    }
}
