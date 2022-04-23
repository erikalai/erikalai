package com.example.todolist;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
        db.execSQL("create table todolistdefault (todo_id integer primary key autoincrement, title VARCHAR(50) not null);");
        db.execSQL("create table subtaskdefault (id integer primary key autoincrement, todo_id int not null, title varchar(50) not null, done boolean not null);");
        db.execSQL("create table todolist (todo_id integer primary key autoincrement, title varchar(50) not null, deadline text, important int not null);");
        db.execSQL("create table subtask (id integer primary key autoincrement, todo_id int not null, title varchar(50) not null, done boolean not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS todolistdefault;");
        db.execSQL("DROP TABLE IF EXISTS subtaskdefault;");
        db.execSQL("DROP TABLE IF EXISTS todolist;");
        db.execSQL("DROP TABLE IF EXISTS subtask;");

        onCreate(db);
    }

    @SuppressLint("Range")
    public void init() {
        db.execSQL("DROP TABLE IF EXISTS todolistdefault;");
        db.execSQL("DROP TABLE IF EXISTS subtaskdefault;");
        db.execSQL("DROP TABLE IF EXISTS todolist;");
        db.execSQL("DROP TABLE IF EXISTS subtask;");
        onCreate(db);

        // initial data
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", "生活");
        insertData("todolistdefault", contentValues);
        /* get back autoincrement id (working code)
        Cursor cursor = query("SELECT last_insert_rowid() id;");
        if (cursor.moveToFirst()) {
            Log.d("DEBUG", Long.toString(cursor.getLong(0)));
        }
        */

        contentValues = new ContentValues();
        contentValues.put("todo_id", 1);
        contentValues.put("title", "校園探秘");
        contentValues.put("done", false);
        insertData("subtaskdefault", contentValues);

        contentValues = new ContentValues();
        contentValues.put("todo_id", 1);
        contentValues.put("title", "試過學校所有餐廳食物");
        contentValues.put("done", false);
        insertData("subtaskdefault", contentValues);

        contentValues = new ContentValues();
        contentValues.put("todo_id", 1);
        contentValues.put("title", "住HALL");
        contentValues.put("done", false);
        insertData("subtaskdefault", contentValues);

        contentValues = new ContentValues();
        contentValues.put("todo_id", 1);
        contentValues.put("title", "Grad Trip");
        contentValues.put("done", false);
        insertData("subtaskdefault", contentValues);

        contentValues = new ContentValues();
        contentValues.put("todo_id", 1);
        contentValues.put("title", "上莊");
        contentValues.put("done", false);
        insertData("subtaskdefault", contentValues);

        contentValues = new ContentValues();
        contentValues.put("todo_id", 1);
        contentValues.put("title", "拍拖");
        contentValues.put("done", false);
        insertData("subtaskdefault", contentValues);




        contentValues = new ContentValues();
        contentValues.put("title", "歎世界");
        insertData("todolistdefault", contentValues);

        contentValues = new ContentValues();
        contentValues.put("todo_id", 2);
        contentValues.put("title", "列出想去國家");
        contentValues.put("done", false);
        insertData("subtaskdefault", contentValues);

        contentValues = new ContentValues();
        contentValues.put("todo_id", 2);
        contentValues.put("title", "開一場party");
        contentValues.put("done", false);
        insertData("subtaskdefault", contentValues);




        contentValues = new ContentValues();
        contentValues.put("title", "中伏清單");
        insertData("todolistdefault", contentValues);

        contentValues = new ContentValues();
        contentValues.put("todo_id", 3);
        contentValues.put("title", "最伏canteen食物");
        contentValues.put("done", false);
        insertData("subtaskdefault", contentValues);

        contentValues = new ContentValues();
        contentValues.put("todo_id", 3);
        contentValues.put("title", "伏Professor");
        contentValues.put("done", false);
        insertData("subtaskdefault", contentValues);

        contentValues = new ContentValues();
        contentValues.put("todo_id", 3);
        contentValues.put("title", "伏course");
        contentValues.put("done", false);
        insertData("subtaskdefault", contentValues);


        contentValues = new ContentValues();
        contentValues.put("title", "挑戰");
        insertData("todolistdefault", contentValues);

        contentValues = new ContentValues();
        contentValues.put("title", "興趣");
        insertData("todolistdefault", contentValues);

        contentValues = new ContentValues();
        contentValues.put("title", "技能");
        insertData("todolistdefault", contentValues);

        contentValues = new ContentValues();
        contentValues.put("title", "工作實習");
        insertData("todolistdefault", contentValues);

        contentValues = new ContentValues();
        contentValues.put("title", "分享");
        insertData("todolistdefault", contentValues);


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
