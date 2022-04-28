package com.com3104.todolist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MyCategoriesExpandableListAdapter extends BaseExpandableListAdapter {

    private final ArrayList<ArrayList<HashMap<String, String>>> childItems;
    private ArrayList<HashMap<String, String>> parentItems;
    private LayoutInflater inflater;
    private Activity activity;
    private HashMap<String, String> child;
    private int count = 0;
    private boolean isFromMyCategoriesFragment;

    public MyCategoriesExpandableListAdapter(Activity activity, ArrayList<HashMap<String, String>> parentItems,
                                             ArrayList<ArrayList<HashMap<String, String>>> childItems, boolean isFromMyCategoriesFragment) {
        this.parentItems = parentItems;
        this.childItems = childItems;
        this.activity = activity;
        this.isFromMyCategoriesFragment = isFromMyCategoriesFragment;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return parentItems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return (childItems.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean b, View convertView, ViewGroup viewGroup) {
         final ViewHolderParent viewHolderParent;
        if (convertView == null) {
            if (isFromMyCategoriesFragment) {
                /*
                if (groupPosition == Global.finishedStartPosition) {
                    convertView = inflater.inflate(R.layout.group_list_layout_my_categories_with_bar, null);
                    ((TextView)convertView.findViewById(R.id.separate_line)).setTextColor(Global.theme.getColor("separate_line_fg"));
                    ((TextView)convertView.findViewById(R.id.bar_divider)).setBackgroundColor(Global.theme.getColor("todo_lv_divider"));
                } else {
                    convertView = inflater.inflate(R.layout.group_list_layout_my_categories, null);
                }
                */
                convertView = inflater.inflate(R.layout.group_list_layout_my_categories, null);
            } else {
                /*
                if (groupPosition == Global.finishedStartPosition) {
                    convertView = inflater.inflate(R.layout.group_list_layout_choose_categories_with_bar, null);
                    ((TextView)convertView.findViewById(R.id.separate_line)).setTextColor(Global.theme.getColor("separate_line_fg"));
                    ((TextView)convertView.findViewById(R.id.bar_divider)).setBackgroundColor(Global.theme.getColor("todo_lv_divider"));
                } else {
                    convertView = inflater.inflate(R.layout.group_list_layout_choose_categories, null);
                }
                */
                convertView = inflater.inflate(R.layout.group_list_layout_choose_categories, null);
            }

            ((TextView)convertView.findViewById(R.id.tvMainCategoryName)).setTextColor(Global.theme.getColor("tvMainCategoryName"));
            viewHolderParent = new ViewHolderParent();

            viewHolderParent.tvMainCategoryName = convertView.findViewById(R.id.tvMainCategoryName);
            viewHolderParent.cbMainCategory = convertView.findViewById(R.id.cbMainCategory);
            viewHolderParent.ivCategory = convertView.findViewById(R.id.ivCategory);
            convertView.setTag(viewHolderParent);
        } else {
            viewHolderParent = (ViewHolderParent) convertView.getTag();
        }


        viewHolderParent.cbMainCategory.setChecked(parentItems.get(groupPosition).get(ConstantManager.Parameter.IS_CHECKED).equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_TRUE));
        notifyDataSetChanged();

        viewHolderParent.cbMainCategory.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View view) {
                int todoID = Global.todoIDs.get(groupPosition);
                boolean checked = viewHolderParent.cbMainCategory.isChecked();

                ArrayList<Integer> ids = new ArrayList<>();

                // find all ids of todoID
                Cursor cursor = Global.myDb.query("select ST.id id from todolist TL inner join subtask ST on TL.todo_id=ST.todo_id where TL.todo_id=" + todoID + ";");
                int resultCounts = cursor.getCount();
                if (resultCounts == 0 || !cursor.moveToFirst()) {
                    // no data
                } else {
                    for (int i = 0; i < resultCounts; i++) {
                        ids.add(cursor.getInt(cursor.getColumnIndex("id")));
                        cursor.moveToNext();
                    }
                }

                for (int i = 0, n = ids.size(); i < n; i++) {
                    Global.myDb.sql("update subtask set done = " + (checked ? 1 : 0) + " where id = " + ids.get(i) + ";");
                }


                if (checked) {
                    parentItems.get(groupPosition).put(ConstantManager.Parameter.IS_CHECKED, ConstantManager.CHECK_BOX_CHECKED_TRUE);

                    for (int i = 0; i < childItems.get(groupPosition).size(); i++) {
                        childItems.get(groupPosition).get(i).put(ConstantManager.Parameter.IS_CHECKED, ConstantManager.CHECK_BOX_CHECKED_TRUE);
                    }
                    Log.d("DEBUG", "Group checkbox: " + groupPosition + " check");
                } else {
                    parentItems.get(groupPosition).put(ConstantManager.Parameter.IS_CHECKED, ConstantManager.CHECK_BOX_CHECKED_FALSE);
                    for (int i = 0; i < childItems.get(groupPosition).size(); i++) {
                        childItems.get(groupPosition).get(i).put(ConstantManager.Parameter.IS_CHECKED, ConstantManager.CHECK_BOX_CHECKED_FALSE);
                    }
                    Log.d("DEBUG", "Group checkbox: " + groupPosition + " uncheck");
                }
                notifyDataSetChanged();
            }
        });

        ConstantManager.childItems = childItems;
        ConstantManager.parentItems = parentItems;

        viewHolderParent.tvMainCategoryName.setText(parentItems.get(groupPosition).get(ConstantManager.Parameter.CATEGORY_NAME));

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, final boolean b, View convertView, ViewGroup viewGroup) {
        final ViewHolderChild viewHolderChild;
        child = childItems.get(groupPosition).get(childPosition);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_list_layout_choose_category, null);
            ((TextView)convertView.findViewById(R.id.tvSubCategoryName)).setTextColor(Global.theme.getColor("tvSubCategoryName"));


            convertView.findViewById(R.id.viewDivider).setBackgroundColor(Global.theme.getColor("viewDivider"));
            viewHolderChild = new ViewHolderChild();

            viewHolderChild.tvSubCategoryName = convertView.findViewById(R.id.tvSubCategoryName);
            viewHolderChild.cbSubCategory = convertView.findViewById(R.id.cbSubCategory);
            viewHolderChild.viewDivider = convertView.findViewById(R.id.viewDivider);
            convertView.setTag(viewHolderChild);
        } else {
            viewHolderChild = (ViewHolderChild) convertView.getTag();
        }

        viewHolderChild.cbSubCategory.setChecked(childItems.get(groupPosition).get(childPosition).get(ConstantManager.Parameter.IS_CHECKED).equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_TRUE));
        notifyDataSetChanged();

        viewHolderChild.tvSubCategoryName.setText(child.get(ConstantManager.Parameter.SUB_CATEGORY_NAME));
        viewHolderChild.cbSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Global.todoSubtasks.get(groupPosition).get(childPosition).getID();
                boolean checked = viewHolderChild.cbSubCategory.isChecked();

                //ContentValues cv = new ContentValues();
                //cv.put("done", checked);
                //Global.myDb.update("subtask", cv, "id = ?", new String[]{Integer.toString(id)});
                Global.myDb.sql("update subtask set done = " + (checked ? 1 : 0) + " where id = " + id + ";");


                if (checked) {
                    childItems.get(groupPosition).get(childPosition).put(ConstantManager.Parameter.IS_CHECKED, ConstantManager.CHECK_BOX_CHECKED_TRUE);
                    //Log.d("DEBUG", "checkbox: " + groupPosition + " " + childPosition + " check");
                    Log.d("DEBUG", "todo_id: " + Global.todoIDs.get(groupPosition) + ", subtask_id: " + Global.todoSubtasks.get(groupPosition).get(childPosition).getID() + " check");
                } else {
                    childItems.get(groupPosition).get(childPosition).put(ConstantManager.Parameter.IS_CHECKED, ConstantManager.CHECK_BOX_CHECKED_FALSE);
                    //Log.d("DEBUG", "checkbox: " + groupPosition + " " + childPosition + " uncheck");
                    Log.d("DEBUG", "todo_id: " + Global.todoIDs.get(groupPosition) + ", subtask_id: " + Global.todoSubtasks.get(groupPosition).get(childPosition).getID() + " uncheck");
                }


                count = 0;
                notifyDataSetChanged();

                for (int i = 0; i < childItems.get(groupPosition).size(); i++) {
                    if (childItems.get(groupPosition).get(i).get(ConstantManager.Parameter.IS_CHECKED).equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_TRUE)) {
                        count++;
                    }
                }
                if (count == childItems.get(groupPosition).size()) {
                    parentItems.get(groupPosition).put(ConstantManager.Parameter.IS_CHECKED, ConstantManager.CHECK_BOX_CHECKED_TRUE);
                } else {
                    parentItems.get(groupPosition).put(ConstantManager.Parameter.IS_CHECKED, ConstantManager.CHECK_BOX_CHECKED_FALSE);
                }
                notifyDataSetChanged();


                ConstantManager.childItems = childItems;
                ConstantManager.parentItems = parentItems;
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    private class ViewHolderParent {

        TextView tvMainCategoryName;
        CheckBox cbMainCategory;
        ImageView ivCategory;
    }

    private class ViewHolderChild {

        TextView tvSubCategoryName;
        CheckBox cbSubCategory;
        View viewDivider;
    }


}
