package com.com3104.todolist;

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
        db.execSQL("create table todolist (todo_id integer primary key autoincrement, title varchar(50) not null, deadline text, important int not null, note varchar(300));");
        db.execSQL("create table subtask (id integer primary key autoincrement, todo_id int not null, title varchar(50) not null, note varchar(300), done boolean not null);");
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
        // get back autoincrement id
        long id = -1;
        Cursor cursor = query("SELECT last_insert_rowid() id;");
        if (cursor.moveToFirst()) {
            //Log.d("DEBUG", Long.toString(cursor.getLong(0)));
            id = cursor.getLong(0);
        }


        String[] temp = new String[] {"校園探秘", "試過學校所有餐廳食物", "住HALL", "Grad Trip", "上莊", "拍拖"};

        for (int i = 0, n = temp.length; i < n; i++) {
            contentValues = new ContentValues();
            contentValues.put("todo_id", id);
            contentValues.put("title", temp[i]);
            contentValues.put("done", false);
            insertData("subtaskdefault", contentValues);
        }



        contentValues = new ContentValues();
        contentValues.put("title", "歎世界");
        insertData("todolistdefault", contentValues);

        cursor = query("SELECT last_insert_rowid() id;");
        if (cursor.moveToFirst()) {
            //Log.d("DEBUG", Long.toString(cursor.getLong(0)));
            id = cursor.getLong(0);
        }

        temp = new String[] {"列出想去國家", "開一場party"};

        for (int i = 0, n = temp.length; i < n; i++) {
            contentValues = new ContentValues();
            contentValues.put("todo_id", id);
            contentValues.put("title", temp[i]);
            contentValues.put("done", false);
            insertData("subtaskdefault", contentValues);
        }




        contentValues = new ContentValues();
        contentValues.put("title", "中伏清單");
        insertData("todolistdefault", contentValues);

        cursor = query("SELECT last_insert_rowid() id;");
        if (cursor.moveToFirst()) {
            //Log.d("DEBUG", Long.toString(cursor.getLong(0)));
            id = cursor.getLong(0);
        }

        temp = new String[] {"最伏canteen食物", "伏Professor", "伏course"};

        for (int i = 0, n = temp.length; i < n; i++) {
            contentValues = new ContentValues();
            contentValues.put("todo_id", id);
            contentValues.put("title", temp[i]);
            contentValues.put("done", false);
            insertData("subtaskdefault", contentValues);
        }



        contentValues = new ContentValues();
        contentValues.put("title", "挑戰");
        insertData("todolistdefault", contentValues);

        cursor = query("SELECT last_insert_rowid() id;");
        if (cursor.moveToFirst()) {
            //Log.d("DEBUG", Long.toString(cursor.getLong(0)));
            id = cursor.getLong(0);
        }

        temp = new String[] {"get drunk", "通宵一次完成一件功課", "exchange", "IELTS 7.5以上", "普通話水平測試", "碌過IT Proficiency test", "考車牌", "參加比賽", "瘋狂納喊一次"};

        for (int i = 0, n = temp.length; i < n; i++) {
            contentValues = new ContentValues();
            contentValues.put("todo_id", id);
            contentValues.put("title", temp[i]);
            contentValues.put("done", false);
            insertData("subtaskdefault", contentValues);
        }


        contentValues = new ContentValues();
        contentValues.put("title", "興趣");
        insertData("todolistdefault", contentValues);

        cursor = query("SELECT last_insert_rowid() id;");
        if (cursor.moveToFirst()) {
            //Log.d("DEBUG", Long.toString(cursor.getLong(0)));
            id = cursor.getLong(0);
        }

        temp = new String[] {"游一次水", "踩一次單車", "溜一次冰", "打一場羽毛球", "行一次山", "玩一次健身室"};

        for (int i = 0, n = temp.length; i < n; i++) {
            contentValues = new ContentValues();
            contentValues.put("todo_id", id);
            contentValues.put("title", temp[i]);
            contentValues.put("done", false);
            insertData("subtaskdefault", contentValues);
        }


        contentValues = new ContentValues();
        contentValues.put("title", "技能");
        insertData("todolistdefault", contentValues);

        cursor = query("SELECT last_insert_rowid() id;");
        if (cursor.moveToFirst()) {
            //Log.d("DEBUG", Long.toString(cursor.getLong(0)));
            id = cursor.getLong(0);
        }

        temp = new String[] {"學會microsoft office", "學會打字", "學會剪片"};

        for (int i = 0, n = temp.length; i < n; i++) {
            contentValues = new ContentValues();
            contentValues.put("todo_id", id);
            contentValues.put("title", temp[i]);
            contentValues.put("done", false);
            insertData("subtaskdefault", contentValues);
        }


        contentValues = new ContentValues();
        contentValues.put("title", "工作實習");
        insertData("todolistdefault", contentValues);

        cursor = query("SELECT last_insert_rowid() id;");
        if (cursor.moveToFirst()) {
            //Log.d("DEBUG", Long.toString(cursor.getLong(0)));
            id = cursor.getLong(0);
        }

        temp = new String[] {"入大公司做一次Intern"};

        for (int i = 0, n = temp.length; i < n; i++) {
            contentValues = new ContentValues();
            contentValues.put("todo_id", id);
            contentValues.put("title", temp[i]);
            contentValues.put("done", false);
            insertData("subtaskdefault", contentValues);
        }


        contentValues = new ContentValues();
        contentValues.put("title", "分享");
        insertData("todolistdefault", contentValues);

        cursor = query("SELECT last_insert_rowid() id;");
        if (cursor.moveToFirst()) {
            //Log.d("DEBUG", Long.toString(cursor.getLong(0)));
            id = cursor.getLong(0);
        }

        temp = new String[] {"分享此APP"};

        for (int i = 0, n = temp.length; i < n; i++) {
            contentValues = new ContentValues();
            contentValues.put("todo_id", id);
            contentValues.put("title", temp[i]);
            contentValues.put("done", false);
            insertData("subtaskdefault", contentValues);
        }


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
