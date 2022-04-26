package com.com3104.todolist;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class AddTodoActivity extends AppCompatActivity {
    private Calendar date = null;
    private EditText titleEt, noteEt, subtaskTitleEt, subtaskNoteEt;
    ListView subtaskLv;
    TextView subtaskTv;
    ArrayList<Subtask> subtasks = new ArrayList<>();

    private int reminderIndex = 0;
    private int importanceIndex = 0;
    private boolean setDate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        createNotificationChannel();

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


        //TextView titleTv = findViewById(R.id.title_tv);
        //titleTv.setTextColor(Global.theme.getFgColor());



        ImageButton backBt = findViewById(R.id.add_todo_back_bt);
        backBt.setBackgroundColor(Global.theme.getBgColor());
        backBt.setColorFilter(Global.theme.getBtFgColor());
        //backBt.setTextColor(Global.theme.getFgColor());

        titleEt = findViewById(R.id.title_et);
        titleEt.setTextColor(Global.theme.getFgColor());
        titleEt.setHintTextColor(Global.theme.getHintColor());
        titleEt.setBackgroundTintList(ColorStateList.valueOf(Global.theme.getFgColor()));

        Button datetimeBt = findViewById(R.id.datetime_bt);
        datetimeBt.setBackgroundColor(Global.theme.getBgColor());
        datetimeBt.setTextColor(Global.theme.getBtFgColor());

        TextView datetimeTv = findViewById(R.id.datetime_tv);
        datetimeTv.setTextColor(Global.theme.getFgColor());

        Button reminderBt = findViewById(R.id.reminder_bt);
        reminderBt.setBackgroundColor(Global.theme.getBgColor());
        reminderBt.setTextColor(Global.theme.getBtFgColor());

        TextView reminderTv = findViewById(R.id.reminder_tv);
        reminderTv.setTextColor(Global.theme.getFgColor());
        reminderTv.setText(Global.reminder[reminderIndex]);

        Button importanceBt = findViewById(R.id.importance_bt);
        importanceBt.setBackgroundColor(Global.theme.getBgColor());
        importanceBt.setTextColor(Global.theme.getBtFgColor());

        TextView importanceTv = findViewById(R.id.importance_tv);
        importanceTv.setTextColor(Global.theme.getFgColor());
        importanceTv.setText(Global.importance[importanceIndex]);

        noteEt = findViewById(R.id.note_et);
        noteEt.setTextColor(Global.theme.getFgColor());
        noteEt.setHintTextColor(Global.theme.getHintColor());
        noteEt.setBackgroundTintList(ColorStateList.valueOf(Global.theme.getFgColor()));

        subtaskTitleEt = findViewById(R.id.subtask_title_et);
        subtaskTitleEt.setTextColor(Global.theme.getFgColor());
        subtaskTitleEt.setHintTextColor(Global.theme.getHintColor());
        subtaskTitleEt.setBackgroundTintList(ColorStateList.valueOf(Global.theme.getFgColor()));

        subtaskNoteEt = findViewById(R.id.subtask_note_et);
        subtaskNoteEt.setTextColor(Global.theme.getFgColor());
        subtaskNoteEt.setHintTextColor(Global.theme.getHintColor());
        subtaskNoteEt.setBackgroundTintList(ColorStateList.valueOf(Global.theme.getFgColor()));

        Button addSubtaskBt = findViewById(R.id.add_subtask_bt);
        addSubtaskBt.setBackgroundColor(Global.theme.getBgColor());
        addSubtaskBt.setTextColor(Global.theme.getBtFgColor());

        subtaskTv = findViewById(R.id.subtask_tv);
        subtaskTv.setTextColor(Global.theme.getFgColor());

        Button saveBt = findViewById(R.id.save_bt);
        saveBt.setBackgroundColor(Global.theme.getBgColor());
        saveBt.setTextColor(Global.theme.getBtFgColor());



        TextView tv1 = findViewById(R.id.tv1);
        tv1.setTextColor(Global.theme.getFgColor());

        TextView tv2 = findViewById(R.id.tv2);
        tv2.setTextColor(Global.theme.getBtFgColor());
        tv2.setBackgroundColor(Global.theme.getBgColor());

        TextView tv3 = findViewById(R.id.tv3);
        tv3.setTextColor(Global.theme.getFgColor());

        TextView tv4 = findViewById(R.id.tv4);
        tv4.setTextColor(Global.theme.getFgColor());

        TextView tv5 = findViewById(R.id.tv5);
        tv5.setTextColor(Global.theme.getHintColor());

        TextView tv6 = findViewById(R.id.tv6);
        tv6.setTextColor(Global.theme.getHintColor());

        TextView tv7 = findViewById(R.id.tv7);
        tv7.setTextColor(Global.theme.getHintColor());






        backBt.setOnClickListener(v -> {
            if (fromPage.equals("MainActivity")) {
                Intent intent1 = new Intent(AddTodoActivity.this, MainActivity.class);
                startActivity(intent1);
                AddTodoActivity.this.finish();
            }
        });

        datetimeBt.setOnClickListener(v -> {
            setDate = false;
            datetimeTv.setText("冇");
            final Calendar currentDate = Calendar.getInstance();
            date = Calendar.getInstance();
            new DatePickerDialog(AddTodoActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    date.set(year, monthOfYear, dayOfMonth);
                    new TimePickerDialog(AddTodoActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            date.set(Calendar.MINUTE, minute);
                            date.set(Calendar.SECOND, 0);
                            date.set(Calendar.MILLISECOND, 0);
                            setDate = true;
                            DateFormat dtf1 = new SimpleDateFormat("yyyy-MM-dd");
                            DateFormat dtf2 = new SimpleDateFormat("HH:mm");

                            datetimeTv.setText(Utils.formatChineseDate(dtf1.format(date.getTime())) + " " + dtf2.format(date.getTime()));
                        }
                    }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
                }
            }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
        });

        reminderBt.setOnClickListener(v -> {
            new AlertDialog.Builder(this).setTitle("提提你").setItems(Global.reminderQ, (dialogInterface, i) -> {
                reminderIndex = i;
                reminderTv.setText(Global.reminder[reminderIndex]);
            }).show();
        });

        importanceBt.setOnClickListener(v -> {
            new AlertDialog.Builder(this).setTitle("重要性").setItems(Global.importanceQ, (dialogInterface, i) -> {
                importanceIndex = i;
                importanceTv.setText(Global.importance[importanceIndex]);
            }).show();
        });

        addSubtaskBt.setOnClickListener(v -> {
            String subtaskTitle = subtaskTitleEt.getText().toString().trim();
            String subtaskNote = subtaskNoteEt.getText().toString().trim();

            if (subtaskTitle.equals("")) {
                Toast.makeText(AddTodoActivity.this, "Please enter subtask title", Toast.LENGTH_SHORT).show();
                return;
            }

            if (subtaskNote.equals("")) {
                subtaskNote = null;
            }

            // add to subtasks
            subtasks.add(new Subtask(subtaskTitle, subtaskNote));

            // load subtasks
            ArrayList<String> temp = Utils.getSubtasksTitles(subtasks);
            String temp2 = "";
            for (int i = 0; i < temp.size(); i++) {
                temp2 += (i == 0 ? "" : "\n") + temp.get(i);
            }
            subtaskTv.setText(temp2);

            // clear input
            subtaskTitleEt.setText("");
            subtaskNoteEt.setText("");
        });

        saveBt.setOnClickListener(v -> {
            String title = titleEt.getText().toString().trim();
            if (title.equals("")) {
                Toast.makeText(AddTodoActivity.this, "Please enter title", Toast.LENGTH_SHORT).show();
                return;
            }
            if ((!setDate || date == null) && reminderIndex != 0) {
                // no date but have reminder
                Toast.makeText(AddTodoActivity.this, "Please enter date if reminder is set. Otherwise, set reminder to None.", Toast.LENGTH_SHORT).show();
                return;
            }

            // save to database
            ContentValues contentValues = new ContentValues();
            contentValues.put("title", title);
            if (setDate && (date != null)) {
                DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS.SSS");
                contentValues.put("deadline", dtf.format(date.getTime()));
            }
            contentValues.put("important", importanceIndex);
            if (noteEt != null) {
                contentValues.put("note", noteEt.getText().toString().trim());
            }
            Global.myDb.insertData("todolist", contentValues);
            // get back autoincrement id
            long todolistID = -1;
            Cursor cursor = Global.myDb.query("SELECT last_insert_rowid() id;");
            if (cursor.moveToFirst()) {
                //Log.d("DEBUG", Long.toString(cursor.getLong(0)));
                todolistID = cursor.getLong(0);
            }


            // save to database (subtasks)
            for (int i = 0, n = subtasks.size(); i < n; i++) {
                contentValues = new ContentValues();
                contentValues.put("todo_id", todolistID);
                contentValues.put("title", subtasks.get(i).getTitle());
                if (subtasks.get(i).getNote() != null) {
                    contentValues.put("note", subtasks.get(i).getNote());
                }
                contentValues.put("done", false);
                Global.myDb.insertData("subtask", contentValues);
            }




            if (setDate && (date != null) && (reminderIndex != 0)) {
                // reminder notification
                Intent intent2 = new Intent(AddTodoActivity.this, ReminderBroadcast.class);
                intent2.putExtra("title", "提提你");
                DateFormat dtf1 = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat dtf2 = new SimpleDateFormat("HH:mm");
                if (reminderIndex == 1) {
                    // 即刻提你
                    intent2.putExtra("msg", Global.importancePrefix[importanceIndex] + title + "死期到！");
                } else {
                    intent2.putExtra("msg", "，" + Global.importancePrefix[importanceIndex] + title + "仲有" + Global.reminder[reminderIndex] + "就死期到！\n死期：" + Utils.formatChineseDate(dtf1.format(date.getTime())) + " " + dtf2.format(date.getTime()));
                }
                int id = new Random().nextInt(543250);
                intent2.putExtra("id", Integer.toString(id));
                intent2.setAction("dummy_action_" + id);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(AddTodoActivity.this, id, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                long deadline = date.getTimeInMillis();

                alarmManager.set(AlarmManager.RTC_WAKEUP,
                        deadline + Global.reminderDelay[reminderIndex],
                        pendingIntent);
            }


            // change back to home page
            backBt.callOnClick();
        });

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Todo reminder channel";
            String desc = "Channel for Todo reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Todo reminder", name, importance);
            channel.setDescription(desc);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}