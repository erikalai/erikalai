package com.com3104.todolist;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

        Button addTodoBt = findViewById(R.id.add_todo_bt);
        addTodoBt.setBackgroundColor(Global.theme.getBgColor());
        addTodoBt.setTextColor(Global.theme.getFgColor());

        ListView todoList = findViewById(R.id.todo_list);
        todoList.setBackgroundColor(Global.theme.getBgColor());





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
            for (int i = 0, n = resultCounts; i < n; i++) {
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




        addTodoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
                intent.putExtra("fromPage", "MainActivity");
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
    }
}