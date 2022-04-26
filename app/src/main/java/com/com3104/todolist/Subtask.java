package com.com3104.todolist;

public class Subtask {
    private int id;
    private String title;
    private String note;
    private boolean done;

    public Subtask() {}

    public Subtask(int id, String title, String note, boolean done) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.done = done;
    }

    public Subtask(String title, String note, boolean done) {
        this.id = -1;
        this.title = title;
        this.note = note;
        this.done = done;
    }

    public int getID() {
        return id;
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
