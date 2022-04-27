package com.com3104.todolist;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;

public class Global {
    private static HashMap<String, String> colorMap;

    public static final String APP_NAME = "記低";
    public static final Theme[] THEMES = new Theme[] {
            //colorMap = new HashMap<>();
            new Theme("活力黃", new HashMap<String, String>() {{
                put("main_activity_window", "#FFFFFF");
                put("no_todo_tv_fg", "#606060");
                put("theme_ll", "#FFC11E");
                put("theme_bt_bg", "#FFC11E");
                put("theme_bt_fg", "#000000");
                put("todo_lv_bg", "#FFFFFF");
                put("todo_lv_divider", "#EEEEEE");
                put("add_todo_bt_bg", "#000000");
                put("add_todo_bt_ripple", "#FFC11E");
                put("add_todo_bt_filter", "#FFFFFF");
                put("edit_text_theme", "#FFC11E");
                put("tvMainCategoryName", "#000000");
                put("tvSubCategoryName", "#606060");
                put("viewDivider", "#606060");
                put("add_todo_activity_window", "#FFFFFF");
                put("add_todo_back_bt_bg", "#FFC11E");
                put("add_todo_back_bt_filter", "#000000");
                put("title_et_fg", "#606060");
                put("title_et_hint", "#FFC11E");
                put("title_et_bg", "#606060");
                put("datetime_bt_bg", "#FFC11E");
                put("datetime_bt_fg", "#000000");
                put("datetime_tv", "#606060");
                put("reminder_ll", "#FFC11E");
                put("reminder_bt_bg", "#FFC11E");
                put("reminder_bt_fg", "#000000");
                put("reminder_tv", "#606060");
                put("importance_bt_bg", "#FFC11E");
                put("importance_bt_fg", "#000000");
                put("importance_tv", "#606060");
                put("note_et_fg", "#606060");
                put("note_et_hint", "#FFC11E");
                put("note_et_bg", "#606060");
                put("subtask_title_et_fg", "#606060");
                put("subtask_title_et_hint", "#FFC11E");
                put("subtask_title_et_bg", "#606060");
                put("subtask_note_et_fg", "#606060");
                put("subtask_note_et_hint", "#FFC11E");
                put("subtask_note_et_bg", "#606060");
                put("add_subtask_bt_bg", "#606060");
                put("add_subtask_bt_fg", "#FFC11E");
                put("save_bt_bg", "#FFC11E");
                put("save_bt_fg", "#000000");
                put("tv_fg", "#606060");
                put("tv_hint", "#FFC11E");
                put("tv2_fg", "#000000");
                put("tv2_bg", "#FFC11E");
                put("add_todo_subtask_title", "#000000");
                put("add_todo_subtask_note", "#606060");
                put("theme_s_bg", "#FBB600");
                put("theme_s_fg", "#FFFFFF");
            }}),


            new Theme("神秘黑", new HashMap<String, String>() {{
                put("main_activity_window", "#000000");
                put("no_todo_tv_fg", "#CCCCCC");
                put("theme_ll", "#FFC11E");
                put("theme_bt_bg", "#FFC11E");
                put("theme_bt_fg", "#000000");
                put("todo_lv_bg", "#000000");
                put("todo_lv_divider", "#EEEEEE");
                put("add_todo_bt_bg", "#FFFFFF");
                put("add_todo_bt_ripple", "#FFC11E");
                put("add_todo_bt_filter", "#000000");
                put("edit_text_theme", "#000000");
                put("tvMainCategoryName", "#FFC11E");
                put("tvSubCategoryName", "#FFFFFF");
                put("viewDivider", "#606060");
                put("add_todo_activity_window", "#000000");
                put("add_todo_back_bt_bg", "#FFC11E");
                put("add_todo_back_bt_filter", "#000000");
                put("title_et_fg", "#FFFFFF");
                put("title_et_hint", "#FFC11E");
                put("title_et_bg", "#606060");
                put("datetime_bt_bg", "#FFC11E");
                put("datetime_bt_fg", "#000000");
                put("datetime_tv", "#CCCCCC");
                put("reminder_ll", "#FFC11E");
                put("reminder_bt_bg", "#FFC11E");
                put("reminder_bt_fg", "#000000");
                put("reminder_tv", "#CCCCCC");
                put("importance_bt_bg", "#FFC11E");
                put("importance_bt_fg", "#000000");
                put("importance_tv", "#CCCCCC");
                put("note_et_fg", "#FFFFFF");
                put("note_et_hint", "#FFC11E");
                put("note_et_bg", "#606060");
                put("subtask_title_et_fg", "#FFFFFF");
                put("subtask_title_et_hint", "#FFC11E");
                put("subtask_title_et_bg", "#606060");
                put("subtask_note_et_fg", "#FFFFFF");
                put("subtask_note_et_hint", "#FFC11E");
                put("subtask_note_et_bg", "#606060");
                put("add_subtask_bt_bg", "#606060");
                put("add_subtask_bt_fg", "#FFC11E");
                put("save_bt_bg", "#FFC11E");
                put("save_bt_fg", "#000000");
                put("tv_fg", "#CCCCCC");
                put("tv_hint", "#FFC11E");
                put("tv2_fg", "#000000");
                put("tv2_bg", "#FFC11E");
                put("add_todo_subtask_title", "#FFFFFF");
                put("add_todo_subtask_note", "#CCCCCC");
                put("theme_s_bg", "#FBB600");
                put("theme_s_fg", "#000000");
            }})

            //new Theme("活力黃", "#FFFFFF", "#FFC11E", "#FFC11E", "#606060", "#000000"),
            //new Theme("神秘黑", "#000000", "#000000", "#000000", "#606060", "#FFC11E")
    };
    public static Theme theme;

    public static SharedPreferences sharedPreferences = null;

    public static DBOpenHelper myDb;

    public static ArrayList<String> todos = new ArrayList<>();
    public static ArrayList<Integer> todoIDs = new ArrayList<>();
    public static ArrayList<ArrayList<Subtask>> todoSubtasks = new ArrayList<>();




    public static String[] reminder = new String[] {
            "冇",
            "即刻提你",
            "5分鐘前",
            "10分鐘前",
            "15分鐘前",
            "30分鐘前",
            "1粒鐘前",
            "2粒鐘前",
            "1日前",
            "2日前",
            "1個禮拜前"
    };

    public static String[] reminderQ = new String[] {
            "冇？",
            "即刻提你？",
            "5分鐘前？",
            "10分鐘前？",
            "15分鐘前？",
            "30分鐘前？",
            "1粒鐘前？",
            "2粒鐘前？",
            "1日前？",
            "2日前？",
            "1個禮拜前？"
    };

    public static long reminderDelay[] = new long[] {
            0, // None
            0, // By the time
            1000 * 60 * (-5), // 5 minutes before
            1000 * 60 * (-10), // 10 minutes before
            1000 * 60 * (-15), // 15 minutes before
            1000 * 60 * (-30), // 30 minutes before
            1000 * 60 * (-60), // 1 hour before
            1000 * 60 * 60 * (-2), // 2 hours before
            1000 * 60 * 60 * (-24), // 1 day before
            1000 * 60 * 60 * 24 * (-2), // 2 days before
            1000 * 60 * 60 * 24 * (-7), // 1 week before
    };

    public static String[] importancePrefix = new String[] {
            "",
            "",
            "❗",
            "‼️"
    };

    public static String[] importance = new String[] {
            importancePrefix[0] + "輕鬆搞掂" + importancePrefix[0],
            importancePrefix[1] + "好似重要" + importancePrefix[1],
            importancePrefix[2] + "重要" + importancePrefix[2],
            importancePrefix[3] + "️️️️️️好很重要" + importancePrefix[3]
    };

    public static String[] importanceQ = new String[] {
            importance[0] + "❔",
            importance[1] + "❔",
            importance[2] + "❔",
            importance[3] + "❔"
    };




    public static String[] getThemeNames() {
        int n = THEMES.length;
        String[] names = new String[n];
        for (int i = 0; i < n; i++) {
            names[i] = THEMES[i].getName();
        }
        return names;
    }
}
