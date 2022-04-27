package com.com3104.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

public class SubtaskActivity extends AppCompatActivity {

    int subtaskID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtask);

        Intent intent = getIntent();
        String fromPage = intent.getStringExtra("fromPage");
        subtaskID = intent.getIntExtra("subtaskID", -1);

        if (subtaskID == -1) {
            // invalid subtaskID
            Log.d("DEBUG", "Invalid subtaskID");
            SubtaskActivity.this.finish();
        }


        ConstraintLayout window = findViewById(R.id.activity_subtask_window);
        window.setBackgroundColor(Global.theme.getColor("main_activity_window"));

        ImageButton backBt = findViewById(R.id.add_todo_back_bt);
        backBt.setBackgroundColor(Global.theme.getColor("add_todo_back_bt_bg"));
        backBt.setColorFilter(Global.theme.getColor("add_todo_back_bt_filter"));

        TextView tv1 = findViewById(R.id.tv1);
        tv1.setTextColor(Global.theme.getColor("tv2_fg"));
        tv1.setBackgroundColor(Global.theme.getColor("tv2_bg"));



        backBt.setOnClickListener(v -> {
            if (fromPage.equals("MainActivity")) {
                //Intent intent1 = new Intent(AddTodoActivity.this, MainActivity.class);
                //startActivity(intent1);
                SubtaskActivity.this.finish();
            }
        });

    }
}