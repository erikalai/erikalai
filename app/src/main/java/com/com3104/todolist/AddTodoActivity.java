package com.com3104.todolist;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddTodoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        Intent intent = getIntent();
        String fromPage = intent.getStringExtra("fromPage");

        // background color
        ConstraintLayout window = findViewById(R.id.add_todo_activity_window);
        window.setBackgroundColor(Global.theme.getWindowBgColor());

        // title bar color
        //ActionBar titleBar;
        //titleBar = getSupportActionBar();
        //ColorDrawable cd = new ColorDrawable(Global.theme.getBgColor());
        //titleBar.setBackgroundDrawable(cd);
        //titleBar.setTitle(Html.fromHtml("<font color=\"" + Global.theme.getFg() + "\">" + Global.APP_NAME + "</font>"));


        TextView titleTv = findViewById(R.id.title_tv);
        titleTv.setTextColor(Global.theme.getFgColor());

        EditText titleEt = findViewById(R.id.title_et);
        titleEt.setTextColor(Global.theme.getFgColor());
        titleEt.setHintTextColor(Global.theme.getHintColor());

        Button backBt = findViewById(R.id.add_todo_back_bt);
        backBt.setBackgroundColor(Global.theme.getBgColor());
        backBt.setTextColor(Global.theme.getFgColor());






        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromPage.equals("MainActivity")) {
                    Intent intent = new Intent(AddTodoActivity.this, MainActivity.class);
                    startActivity(intent);
                    AddTodoActivity.this.finish();
                }
            }
        });

    }
}