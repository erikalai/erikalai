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
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView todoLv;

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
        themeBt.setTextColor(Global.theme.getBtFgColor());

        todoLv = findViewById(R.id.todo_lv);
        todoLv.setBackgroundColor(Global.theme.getWindowBgColor());
        todoLv.setDivider(new ColorDrawable(Global.theme.getFgColor()));
        todoLv.setDividerHeight(12);

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
            new AlertDialog.Builder(this).setTitle("主題色").setItems(Global.getThemeNames(), (dialogInterface, i) -> {
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

        ArrayList<Spanned> todos = new ArrayList<>();
        ArrayList<Integer> todoIDs = new ArrayList<>();

        cursor = Global.myDb.query("select todo_id, title, important, date(deadline) deadline_date, time(deadline) deadline_time from todolist order by case when deadline_date is null then 1 else 0 end, deadline_date, deadline_time, important desc;");
        resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            // no data
        } else {
            int id, important;
            String title, deadlineDate, deadlineTime;
            for (int i = 0; i < resultCounts; i++) {
                id = cursor.getInt(cursor.getColumnIndex("todo_id"));
                title = cursor.getString(cursor.getColumnIndex("title"));
                important = cursor.getInt(cursor.getColumnIndex("important"));
                deadlineDate = cursor.getString(cursor.getColumnIndex("deadline_date"));
                deadlineTime = cursor.getString(cursor.getColumnIndex("deadline_time"));
                if (deadlineDate == null || deadlineTime == null) {
                    // no deadline
                    todos.add(Html.fromHtml(Global.importancePrefix[important] + "<font color=\"" + Global.theme.getBtFg() + "\">" + title + "</font>"));
                } else {
                    // have deadline
                    todos.add(Html.fromHtml(Global.importancePrefix[important] + "<font color=\"" + Global.theme.getBtFg() + "\">" + title + "</font><br>(" + Utils.formatChineseDate(deadlineDate) + " " + deadlineTime + ")"));
                }
                todoIDs.add(id);
                cursor.moveToNext();
            }
        }

        ArrayAdapter<Spanned> adapter = new ArrayAdapter<Spanned>(
                MainActivity.this, R.layout.todo_listview, todos) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView textView = (TextView) view.findViewById(R.id.text1);

                textView.setTextColor(Global.theme.getFgColor());

                return view;
            }
        };
        todoLv.setAdapter(adapter);

    }


    
}