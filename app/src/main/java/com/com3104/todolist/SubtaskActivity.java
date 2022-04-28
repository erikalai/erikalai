package com.com3104.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class SubtaskActivity extends AppCompatActivity {

    int subtaskID;

    @SuppressLint("Range")
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

        ImageButton backBt = findViewById(R.id.activity_subtask_back_bt);
        backBt.setBackgroundColor(Global.theme.getColor("add_todo_back_bt_bg"));
        backBt.setColorFilter(Global.theme.getColor("add_todo_back_bt_filter"));

        TextView tv1 = findViewById(R.id.tv1);
        tv1.setTextColor(Global.theme.getColor("tv2_fg"));
        tv1.setBackgroundColor(Global.theme.getColor("tv2_bg"));


        EditText titleEt = findViewById(R.id.title_et);
        titleEt.setTextColor(Global.theme.getColor("title_et_fg"));
        titleEt.setHintTextColor(Global.theme.getColor("title_et_hint"));
        titleEt.setBackgroundTintList(ColorStateList.valueOf(Global.theme.getColor("title_et_bg")));

        EditText noteEt = findViewById(R.id.note_et);
        noteEt.setTextColor(Global.theme.getColor("tv_fg"));
        noteEt.setHintTextColor(Global.theme.getColor("note_et_hint"));
        noteEt.setBackgroundTintList(ColorStateList.valueOf(Global.theme.getColor("note_et_bg")));


        String title = "", note = "";
        Cursor cursor = Global.myDb.query("select title, note from subtask where id=" + subtaskID + ";");
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            // no data
        } else {
            title = cursor.getString(cursor.getColumnIndex("title"));
            note = cursor.getString(cursor.getColumnIndex("note"));
            note = (note == null ? "" : note);
        }

        titleEt.setText(title);
        noteEt.setText(note);







        backBt.setOnClickListener(v -> {
            // save data to database
            String saveTitle = titleEt.getText().toString().trim();
            String saveNote = noteEt.getText().toString().trim().replaceAll("\n+", "\n");

            saveNote = (saveNote.equals("") ? null : saveNote);

            if (saveTitle.equals("")) {
                // delete subtask
                Global.myDb.sql("delete from subtask where id = " + subtaskID + ";");
            } else {
                // update
                Global.myDb.sql("update subtask set title = '" + saveTitle + "', note = " + (saveNote == null ? "NULL" : "'" + saveNote + "'") + " where id = " + subtaskID + ";");
            }

            // change page
            if (fromPage.equals("MainActivity")) {
                Intent intent1 = new Intent(SubtaskActivity.this, MainActivity.class);
                startActivity(intent1);
                SubtaskActivity.this.finish();
            }
        });

    }
}