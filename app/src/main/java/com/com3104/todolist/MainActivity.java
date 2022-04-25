package com.com3104.todolist;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Global.sharedPreferences = getSharedPreferences("MyProfile", Context.MODE_PRIVATE);


        int themeID = Global.sharedPreferences.getInt("Theme", -1);
        if (themeID == -1) {
            // first time, set to default value
            themeID = 0;
            SharedPreferences.Editor editor = Global.sharedPreferences.edit();
            editor.putInt("Theme", themeID);
            editor.commit();
        }

        // set theme according to SharedPreferences
        Global.theme = Global.THEMES[themeID];

        // background color
        ConstraintLayout window = findViewById(R.id.main_activity_window);
        window.setBackgroundColor(Global.theme.getWindowBgColor());

        // title bar color
        //ActionBar titleBar;
        //titleBar = getSupportActionBar();
        //ColorDrawable cd = new ColorDrawable(Global.theme.getBgColor());
        //titleBar.setBackgroundDrawable(cd);
        //titleBar.setTitle(Html.fromHtml("<font color=\"" + Global.theme.getFg() + "\">" + Global.APP_NAME + "</font>"));

        Button themeBt = findViewById(R.id.theme_bt);
        themeBt.setBackgroundColor(Global.theme.getBgColor());
        themeBt.setTextColor(Global.theme.getFgColor());

        ListView todoList = findViewById(R.id.todo_list);
        todoList.setBackgroundColor(Global.theme.getWindowBgColor());

        FloatingActionButton addTodoBt = findViewById(R.id.add_todo_bt);
        addTodoBt.setBackgroundTintList(ColorStateList.valueOf(Global.theme.getFgColor()));
        addTodoBt.setRippleColor(Global.theme.getHintColor());
        addTodoBt.setColorFilter(Global.theme.getWindowBgColor());


        // database
        Global.myDb = new DBOpenHelper(this);

        // check if tables have data
        Cursor cursor = Global.myDb.query("select TLD.todo_id id, TLD.title title, STD.title subtask from todolistdefault TLD left join subtaskdefault STD on TLD.todo_id=STD.todo_id;");
        Cursor cursor2 = Global.myDb.query("select * from subtaskdefault;");
        int resultCounts = cursor.getCount();
        int resultCounts2 = cursor2.getCount();
        if ((resultCounts == 0 || !cursor.moveToFirst()) && (resultCounts2 == 0 || !cursor2.moveToFirst())) {
            // no init data
            Global.myDb.init();
        } else {
            int id;
            String title, subtask;
            for (int i = 0; i < resultCounts; i++) {
                id = cursor.getInt(cursor.getColumnIndex("id"));
                title = cursor.getString(cursor.getColumnIndex("title"));
                subtask = cursor.getString(cursor.getColumnIndex("subtask"));
                if (subtask == null) {
                    Log.d("DEBUG", "Todo list " + id + ": " + title + " (empty subtask)");
                } else {
                    Log.d("DEBUG", "Todo list " + id + ": " + title + ", subtask: " + subtask);
                }
                cursor.moveToNext();
            }
        }




        themeBt.setOnClickListener(v -> {
            new AlertDialog.Builder(this).setTitle("Theme").setItems(Global.getThemeNames(), (dialogInterface, i) -> {
                Global.theme = Global.THEMES[i];
                SharedPreferences.Editor editor = Global.sharedPreferences.edit();
                editor.putInt("Theme", i);
                editor.commit();

                Intent intent1 = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent1);
                MainActivity.this.finish();
            }).show();
        });

        addTodoBt.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
            intent.putExtra("fromPage", "MainActivity");
            startActivity(intent);
            MainActivity.this.finish();
        });

    }
}