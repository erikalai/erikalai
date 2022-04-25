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
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class AddTodoActivity extends AppCompatActivity {
    private Calendar date = null;
    private EditText titleEt;
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



        Button backBt = findViewById(R.id.add_todo_back_bt);
        backBt.setBackgroundColor(Global.theme.getBgColor());
        backBt.setTextColor(Global.theme.getFgColor());

        titleEt = findViewById(R.id.title_et);
        titleEt.setTextColor(Global.theme.getFgColor());
        titleEt.setHintTextColor(Global.theme.getHintColor());

        Button datetimeBt = findViewById(R.id.datetime_bt);
        datetimeBt.setBackgroundColor(Global.theme.getBgColor());
        datetimeBt.setTextColor(Global.theme.getFgColor());

        TextView datetimeTv = findViewById(R.id.datetime_tv);
        datetimeTv.setTextColor(Global.theme.getFgColor());

        Button reminderBt = findViewById(R.id.reminder_bt);
        reminderBt.setBackgroundColor(Global.theme.getBgColor());
        reminderBt.setTextColor(Global.theme.getFgColor());

        TextView reminderTv = findViewById(R.id.reminder_tv);
        reminderTv.setTextColor(Global.theme.getFgColor());
        reminderTv.setText(Global.reminder[reminderIndex]);

        Button importanceBt = findViewById(R.id.importance_bt);
        importanceBt.setBackgroundColor(Global.theme.getBgColor());
        importanceBt.setTextColor(Global.theme.getFgColor());

        TextView importanceTv = findViewById(R.id.importance_tv);
        importanceTv.setTextColor(Global.theme.getFgColor());
        importanceTv.setText(Global.importance[importanceIndex]);

        Button saveBt = findViewById(R.id.save_bt);
        saveBt.setBackgroundColor(Global.theme.getBgColor());
        saveBt.setTextColor(Global.theme.getFgColor());



        TextView tv1 = findViewById(R.id.tv1);
        tv1.setTextColor(Global.theme.getFgColor());

        TextView tv2 = findViewById(R.id.tv2);
        tv2.setTextColor(Global.theme.getFgColor());

        TextView tv3 = findViewById(R.id.tv3);
        tv3.setTextColor(Global.theme.getFgColor());

        TextView tv4 = findViewById(R.id.tv4);
        tv4.setTextColor(Global.theme.getFgColor());





        backBt.setOnClickListener(v -> {
            if (fromPage.equals("MainActivity")) {
                Intent intent1 = new Intent(AddTodoActivity.this, MainActivity.class);
                startActivity(intent1);
                AddTodoActivity.this.finish();
            }
        });

        datetimeBt.setOnClickListener(v -> {
            setDate = false;
            datetimeTv.setText("None");
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
                            DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                            datetimeTv.setText(dtf.format(date.getTime()));
                            //Log.v("DEBUG", "The choosen one: " + dtf.format(date.getTime()));
                        }
                    }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
                }
            }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
        });

        reminderBt.setOnClickListener(v -> {
            new AlertDialog.Builder(this).setTitle("Reminder").setItems(Global.reminder, (dialogInterface, i) -> {
                reminderIndex = i;
                reminderTv.setText(Global.reminder[reminderIndex]);
            }).show();
        });

        importanceBt.setOnClickListener(v -> {
            new AlertDialog.Builder(this).setTitle("Importance").setItems(Global.importance, (dialogInterface, i) -> {
                importanceIndex = i;
                importanceTv.setText(Global.importance[importanceIndex]);
            }).show();
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
            Global.myDb.insertData("todolist", contentValues);


            if (setDate && (date != null) && (reminderIndex != 0)) {
                // reminder notification
                Intent intent2 = new Intent(AddTodoActivity.this, ReminderBroadcast.class);
                intent2.putExtra("title", "Reminder");
                DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                intent2.putExtra("msg", "Deadline of " + title + ": " + dtf.format(date.getTime()));
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