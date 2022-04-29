package com.com3104.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "todo.db";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase db;


    public DBOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create tables
        db.execSQL("create table todolist (todo_id integer primary key autoincrement, title varchar(50) not null, deadline text, important int not null, note varchar(300));");
        db.execSQL("create table subtask (id integer primary key autoincrement, todo_id int not null, title varchar(50) not null, note varchar(300), done boolean not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop tables
        db.execSQL("DROP TABLE IF EXISTS todolist;");
        db.execSQL("DROP TABLE IF EXISTS subtask;");

        // reset tables
        onCreate(db);
    }

    public boolean insertData(String dbTable, ContentValues contentValues) {
        db = this.getWritableDatabase();
        long result = db.insert(dbTable, null, contentValues);
        return result != -1;
    }

    public void sql(String s) {
        db.execSQL(s);
    }

    public Cursor query(String s) {
        db = this.getWritableDatabase();
        return db.rawQuery(s, null);
    }

}
