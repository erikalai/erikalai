package com.com3104.todolist;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.com3104.todolist.Model.DataItem;
import com.com3104.todolist.Model.SubCategoryItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ExpandableListView todoLv;

    ArrayList<DataItem> categories;
    ArrayList<SubCategoryItem> subCategories;

    ArrayList<DataItem> parentItems;
    ArrayList<ArrayList<SubCategoryItem>> childItems;
    MyCategoriesExpandableListAdapter myCategoriesExpandableListAdapter;

    @SuppressLint({"Range", "ResourceType"})
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

        // window background color
        ConstraintLayout window = findViewById(R.id.main_activity_window);
        window.setBackgroundColor(Global.theme.getColor("main_activity_window"));


        LinearLayout themeLl = findViewById(R.id.theme_ll);
        themeLl.setBackgroundColor(Global.theme.getColor("theme_ll"));

        Spinner themeS = findViewById(R.id.theme_s);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, Global.getThemeNames()) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                ((TextView) v).setTextSize(30);
                ((TextView) v).setTextColor(Color.BLACK);

                return v;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                v.setBackgroundColor(Global.theme.getColor("theme_s_bg"));
                ((TextView) v).setTextColor(Global.theme.getColor("theme_s_fg"));
                ((TextView) v).setTextSize(30);
                ((TextView) v).setGravity(Gravity.CENTER);

                return v;
            }
        };

        themeS.getBackground().setColorFilter(ContextCompat.getColor(this,
                R.color.black), PorterDuff.Mode.SRC_ATOP);

        themeS.setAdapter(adapter);
        themeS.setSelection(themeID, false);
        themeS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Global.theme = Global.THEMES[position];
                SharedPreferences.Editor editor = Global.sharedPreferences.edit();
                editor.putInt("Theme", position);
                editor.commit();

                Intent intent1 = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent1);
                MainActivity.this.finish();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // do nothing
            }
        });

        ImageView themeSArrow = findViewById(R.id.theme_s_arrow);
        themeSArrow.setOnClickListener(v -> {
            themeS.performClick();
        });




        TextView noTodoTv = findViewById(R.id.no_todo_tv);
        noTodoTv.setTextColor(Global.theme.getColor("no_todo_tv_fg"));

        todoLv = findViewById(R.id.todo_lv);
        todoLv.setBackgroundColor(Global.theme.getColor("todo_lv_bg"));
        todoLv.setDivider(new ColorDrawable(Global.theme.getColor("todo_lv_divider")));
        todoLv.setDividerHeight(12);
        todoLv.setChildDivider(new ColorDrawable(Global.theme.getColor("todo_lv_divider")));
        todoLv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Log.d("DEBUG", Global.todos.get(groupPosition) + " " + Global.todoSubtasks.get(groupPosition).get(childPosition).getTitle());



                Intent intent = new Intent(MainActivity.this, SubtaskActivity.class);
                intent.putExtra("fromPage", "MainActivity");
                intent.putExtra("subtaskID", Global.todoSubtasks.get(groupPosition).get(childPosition).getID());
                startActivity(intent);
                MainActivity.this.finish();

                return false;
            }
        });

        FloatingActionButton addTodoBt = findViewById(R.id.add_todo_bt);
        addTodoBt.setBackgroundTintList(ColorStateList.valueOf(Global.theme.getColor("add_todo_bt_bg")));
        addTodoBt.setRippleColor(Global.theme.getColor("add_todo_bt_ripple"));
        addTodoBt.setColorFilter(Global.theme.getColor("add_todo_bt_filter"));



        Drawable unwrappedDrawable = AppCompatResources.getDrawable(this, R.drawable.edit_text_theme);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, Global.theme.getColor("edit_text_theme"));

        // database
        Global.myDb = new DBOpenHelper(this);

        addTodoBt.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
            intent.putExtra("fromPage", "MainActivity");
            startActivity(intent);
            MainActivity.this.finish();
        });



        //ArrayList<Spanned> todos = new ArrayList<>();
        Global.todos = new ArrayList<>();
        Global.todoIDs = new ArrayList<>();
        Global.todoSubtasks = new ArrayList<>();

        categories = new ArrayList<>();
        subCategories = new ArrayList<>();
        parentItems = new ArrayList<>();
        childItems = new ArrayList<>();

        Cursor cursor = Global.myDb.query("select TL.todo_id todo_id, TL.title title, TL.important important, date(TL.deadline) deadline_date, time(TL.deadline) deadline_time, ST.id subtask_id, ST.title subtask_title, ST.note subtask_note, ST.done subtask_done, done_or_not all_done from todolist TL left join subtask ST on TL.todo_id=ST.todo_id inner join (select TL.todo_id TL2_todo_id, (sum(ST.done)/count(ST.id)) done_or_not from todolist TL left join subtask ST on TL.todo_id=ST.todo_id group by TL.todo_id) TL2 on TL.todo_id=TL2.TL2_todo_id order by case when all_done is null then 1 else 0 end, all_done, case when deadline_date is null then 1 else 0 end, deadline_date, deadline_time, important desc, title, subtask_id;");
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            // no data
            ((ViewGroup) todoLv.getParent()).removeView(todoLv);
        } else {
            ((ViewGroup) noTodoTv.getParent()).removeView(noTodoTv);

            int id, important;
            Integer subtaskID = null;
            String title, subtaskTitle, subtaskNote, deadlineDate, deadlineTime;
            boolean subtaskDone;
            ArrayList<Subtask> tempSubtasks = new ArrayList<>();
            for (int i = 0; i < resultCounts; i++) {
                id = cursor.getInt(cursor.getColumnIndex("todo_id"));
                title = cursor.getString(cursor.getColumnIndex("title"));
                important = cursor.getInt(cursor.getColumnIndex("important"));
                deadlineDate = cursor.getString(cursor.getColumnIndex("deadline_date"));
                deadlineTime = cursor.getString(cursor.getColumnIndex("deadline_time"));
                if (!Global.todoIDs.contains(id)) {
                    if (deadlineDate == null || deadlineTime == null) {
                        // no deadline
                        Global.todos.add(Global.importancePrefix[important] + title);
                    } else {
                        // have deadline
                        Global.todos.add(Global.importancePrefix[important] + title + "\n(" + Utils.formatChineseDate(deadlineDate) + " " + deadlineTime + ")");
                    }
                }
                if (!Global.todoIDs.contains(id)) {
                    tempSubtasks = new ArrayList<>();
                }

                // subtask
                // subtaskID == null if no subtask
                int index = cursor.getColumnIndexOrThrow("subtask_id");
                if (!cursor.isNull(index)) {
                    subtaskID = cursor.getInt(index);
                    subtaskTitle = cursor.getString(cursor.getColumnIndex("subtask_title"));
                    subtaskNote = cursor.getString(cursor.getColumnIndex("subtask_note"));
                    subtaskDone = cursor.getInt(cursor.getColumnIndex("subtask_done")) > 0;

                    tempSubtasks.add(new Subtask(subtaskID, subtaskTitle, subtaskNote, subtaskDone));
                }

                if (!Global.todoIDs.contains(id)) {
                    Global.todoIDs.add(id);
                    Global.todoSubtasks.add(tempSubtasks);
                }
                cursor.moveToNext();
            }
        }


        for (int i = 0, n = Global.todoIDs.size(); i < n; i++) {
            DataItem dataItem = new DataItem();
            dataItem.setCategoryID(i+1);
            dataItem.setCategoryName(Global.todos.get(i));

            subCategories = new ArrayList<>();
            for (int j = 0, m = Global.todoSubtasks.get(i).size(); j < m; j++) {
                SubCategoryItem subCategoryItem = new SubCategoryItem();
                subCategoryItem.setCategoryID(Global.todoSubtasks.get(i).get(j).getID());
                subCategoryItem.setIsChecked(Global.todoSubtasks.get(i).get(j).getDone());
                subCategoryItem.setSubCategoryName(Global.todoSubtasks.get(i).get(j).getTitle());
                subCategories.add(subCategoryItem);
            }

            dataItem.setSubCategory(subCategories);
            categories.add(dataItem);
        }

        for (DataItem finishBox : categories) {
            ArrayList<SubCategoryItem> childArrayList = new ArrayList<>();
            int countIsChecked = 0;
            for (SubCategoryItem subCategoryItem : finishBox.getSubCategory()) {
                if (subCategoryItem.getIsChecked()) {
                    countIsChecked++;
                }
                childArrayList.add(subCategoryItem);
            }

            // mark as finish if all subtask are finished
            finishBox.setIsChecked(countIsChecked == finishBox.getSubCategory().size());


            childItems.add(childArrayList);
            parentItems.add(finishBox);
        }

        Global.parentItems = parentItems;
        Global.childItems = childItems;

        myCategoriesExpandableListAdapter = new MyCategoriesExpandableListAdapter(this, parentItems, childItems, false);
        todoLv.setAdapter(myCategoriesExpandableListAdapter);
    }
}