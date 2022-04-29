package com.com3104.todolist;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class AddTodoActivity extends AppCompatActivity {
    private Calendar date = null;
    private EditText titleEt, subtaskTitleEt, subtaskNoteEt;
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
        window.setBackgroundColor(Global.theme.getColor("add_todo_activity_window"));


        ImageButton backBt = findViewById(R.id.add_todo_back_bt);
        backBt.setBackgroundColor(Global.theme.getColor("add_todo_back_bt_bg"));
        backBt.setColorFilter(Global.theme.getColor("add_todo_back_bt_filter"));

        titleEt = findViewById(R.id.title_et);
        titleEt.setTextColor(Global.theme.getColor("title_et_fg"));
        titleEt.setHintTextColor(Global.theme.getColor("title_et_hint"));
        titleEt.setBackgroundTintList(ColorStateList.valueOf(Global.theme.getColor("title_et_bg")));

        Button datetimeBt = findViewById(R.id.datetime_bt);
        datetimeBt.setBackgroundColor(Global.theme.getColor("datetime_bt_bg"));
        datetimeBt.setTextColor(Global.theme.getColor("datetime_bt_fg"));

        TextView datetimeTv = findViewById(R.id.datetime_tv);
        datetimeTv.setTextColor(Global.theme.getColor("datetime_tv"));

        Button reminderBt = findViewById(R.id.reminder_bt);
        reminderBt.setBackgroundColor(Global.theme.getColor("reminder_bt_bg"));
        reminderBt.setTextColor(Global.theme.getColor("reminder_bt_fg"));

        TextView reminderTv = findViewById(R.id.reminder_tv);
        reminderTv.setTextColor(Global.theme.getColor("reminder_tv"));
        reminderTv.setText(Global.reminder[reminderIndex]);

        Button importanceBt = findViewById(R.id.importance_bt);
        importanceBt.setBackgroundColor(Global.theme.getColor("importance_bt_bg"));
        importanceBt.setTextColor(Global.theme.getColor("importance_bt_fg"));

        TextView importanceTv = findViewById(R.id.importance_tv);
        importanceTv.setTextColor(Global.theme.getColor("importance_tv"));
        importanceTv.setText(Global.importance[importanceIndex]);


        subtaskTitleEt = findViewById(R.id.subtask_title_et);
        subtaskTitleEt.setTextColor(Global.theme.getColor("subtask_title_et_fg"));
        subtaskTitleEt.setHintTextColor(Global.theme.getColor("subtask_title_et_hint"));
        subtaskTitleEt.setBackgroundTintList(ColorStateList.valueOf(Global.theme.getColor("subtask_title_et_bg")));

        subtaskNoteEt = findViewById(R.id.subtask_note_et);
        subtaskNoteEt.setTextColor(Global.theme.getColor("subtask_note_et_fg"));
        subtaskNoteEt.setHintTextColor(Global.theme.getColor("subtask_note_et_hint"));
        subtaskNoteEt.setBackgroundTintList(ColorStateList.valueOf(Global.theme.getColor("subtask_note_et_bg")));

        Button addSubtaskBt = findViewById(R.id.add_subtask_bt);
        addSubtaskBt.setBackgroundColor(Global.theme.getColor("add_subtask_bt_bg"));
        addSubtaskBt.setTextColor(Global.theme.getColor("add_subtask_bt_fg"));

        Button saveBt = findViewById(R.id.save_bt);
        saveBt.setBackgroundColor(Global.theme.getColor("save_bt_bg"));
        saveBt.setTextColor(Global.theme.getColor("save_bt_fg"));



        TextView tv1 = findViewById(R.id.tv1);
        tv1.setTextColor(Global.theme.getColor("tv_fg"));

        TextView tv2 = findViewById(R.id.tv2);
        tv2.setTextColor(Global.theme.getColor("tv2_fg"));
        tv2.setBackgroundColor(Global.theme.getColor("tv2_bg"));

        TextView tv3 = findViewById(R.id.tv3);
        tv3.setTextColor(Global.theme.getColor("tv_fg"));

        TextView tv4 = findViewById(R.id.tv4);
        tv4.setTextColor(Global.theme.getColor("tv_fg"));

        //TextView tv5 = findViewById(R.id.tv5);
        //tv5.setTextColor(Global.theme.getColor("tv_hint"));

        //TextView tv6 = findViewById(R.id.tv6);
        //tv6.setTextColor(Global.theme.getColor("tv_hint"));

        TextView tv7 = findViewById(R.id.tv7);
        tv7.setTextColor(Global.theme.getColor("tv_hint"));

        //TextView tv8 = findViewById(R.id.tv8);
        //tv8.setTextColor(Global.theme.getColor("tv_hint"));






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
            String subtaskNote = subtaskNoteEt.getText().toString().trim().replaceAll("\n+", "\n");

            if (subtaskTitle.equals("")) {
                Toast.makeText(AddTodoActivity.this, "你想加啲咩子任務呢？", Toast.LENGTH_SHORT).show();
                return;
            }

            subtaskNote = (subtaskNote.equals("") ? null : subtaskNote);

            // add to subtasks
            subtasks.add(new Subtask(subtaskTitle, subtaskNote, false));

            // load subtasks
            ArrayList<String> temp = Utils.getSubtasksTitles(subtasks);

            TableLayout table = AddTodoActivity.this.findViewById(R.id.subtask_tl);

            // reset TableLayout
            while (table.getChildCount() >= 1) {
                table.removeViewAt(0);
            }

            for (int i = 0; i < temp.size(); i++) {
                // Inflate your row "template" and fill out the fields.
                TableRow row = (TableRow) LayoutInflater.from(AddTodoActivity.this).inflate(R.layout.add_todo_subtask_row, null);
                ((TextView)row.findViewById(R.id.tv)).setText(Html.fromHtml("&nbsp;<font color=\"" + Global.theme.getColorCode("add_todo_subtask_title") + "\">" + temp.get(i) + "</font>" + (subtasks.get(i).getNote() != null ? "<br>&nbsp;<small><font color=\"" + Global.theme.getColorCode("add_todo_subtask_note") + "\">" + subtasks.get(i).getNote().replaceAll("\n", "</font></small><br>&nbsp;<small><font color=\"" + Global.theme.getColorCode("add_todo_subtask_note") + "\">") + "</font></small>" : "")));

                ((CheckBox)row.findViewById(R.id.cb)).setChecked(subtasks.get(i).getDone());
                int finalI = i;
                ((CheckBox)row.findViewById(R.id.cb)).setOnClickListener(view -> {
                    subtasks.get(finalI).setDone(!subtasks.get(finalI).getDone());
                });

                table.addView(row);
            }
            table.requestLayout();



            // clear input
            subtaskTitleEt.setText("");
            subtaskNoteEt.setText("");
        });

        saveBt.setOnClickListener(v -> {
            String title = titleEt.getText().toString().trim();
            if (title.equals("")) {
                Toast.makeText(AddTodoActivity.this, "有咩做先？", Toast.LENGTH_SHORT).show();
                return;
            }
            if ((!setDate || date == null) && reminderIndex != 0) {
                // no date but have reminder
                Toast.makeText(AddTodoActivity.this, "你未有死期，唔知點提你好喎\uD83D\uDE48", Toast.LENGTH_SHORT).show();
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
            // get back autoincrement id
            long todolistID = -1;
            Cursor cursor = Global.myDb.query("SELECT last_insert_rowid() id;");
            if (cursor.moveToFirst()) {
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
                contentValues.put("done", subtasks.get(i).getDone());
                Global.myDb.insertData("subtask", contentValues);
            }




            if (setDate && (date != null) && (reminderIndex != 0)) {
                // reminder notification
                Intent intent2 = new Intent(AddTodoActivity.this, ReminderBroadcast.class);
                intent2.putExtra("title", "提提你！");
                DateFormat dtf1 = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat dtf2 = new SimpleDateFormat("HH:mm");
                if (reminderIndex == 1) {
                    // 即刻提你
                    intent2.putExtra("msg", Global.importancePrefix[importanceIndex] + title + "死期到！");
                } else {
                    intent2.putExtra("msg", Global.importancePrefix[importanceIndex] + title + "仲有" + Global.reminder[reminderIndex].replaceAll("前", "") + "就死期到！\uD83D\uDE48\n死期：" + Utils.formatChineseDate(dtf1.format(date.getTime())) + " " + dtf2.format(date.getTime()));
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