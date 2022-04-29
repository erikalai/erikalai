package com.com3104.todolist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.com3104.todolist.Model.DataItem;
import com.com3104.todolist.Model.SubCategoryItem;
import java.util.ArrayList;

public class MyCategoriesExpandableListAdapter extends BaseExpandableListAdapter {
    private final ArrayList<ArrayList<SubCategoryItem>> childItems;
    private final ArrayList<DataItem> parentItems;
    private final LayoutInflater inflater;
    private SubCategoryItem child;
    private int count = 0;
    private final boolean isFromMyCategoriesFragment;

    public MyCategoriesExpandableListAdapter(Activity activity, ArrayList<DataItem> parentItems,
                                             ArrayList<ArrayList<SubCategoryItem>> childItems, boolean isFromMyCategoriesFragment) {
        this.parentItems = parentItems;
        this.childItems = childItems;
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
            convertView = inflater.inflate((isFromMyCategoriesFragment ? R.layout.group_list_layout_my_categories : R.layout.group_list_layout_choose_categories), null);

            ((TextView)convertView.findViewById(R.id.tvMainCategoryName)).setTextColor(Global.theme.getColor("tvMainCategoryName"));
            viewHolderParent = new ViewHolderParent();

            viewHolderParent.tvMainCategoryName = convertView.findViewById(R.id.tvMainCategoryName);
            viewHolderParent.cbMainCategory = convertView.findViewById(R.id.cbMainCategory);
            viewHolderParent.ivCategory = convertView.findViewById(R.id.ivCategory);
            convertView.setTag(viewHolderParent);
        } else {
            viewHolderParent = (ViewHolderParent) convertView.getTag();
        }


        viewHolderParent.cbMainCategory.setChecked(parentItems.get(groupPosition).getIsChecked());
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


                parentItems.get(groupPosition).setIsChecked(checked);
                for (int i = 0; i < childItems.get(groupPosition).size(); i++) {
                    childItems.get(groupPosition).get(i).setIsChecked(checked);
                }

                notifyDataSetChanged();
            }
        });

        Global.childItems = childItems;
        Global.parentItems = parentItems;

        viewHolderParent.tvMainCategoryName.setText(parentItems.get(groupPosition).getCategoryName());

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

        viewHolderChild.cbSubCategory.setChecked(childItems.get(groupPosition).get(childPosition).getIsChecked());
        notifyDataSetChanged();

        viewHolderChild.tvSubCategoryName.setText(child.getSubCategoryName());
        viewHolderChild.cbSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Global.todoSubtasks.get(groupPosition).get(childPosition).getID();
                boolean checked = viewHolderChild.cbSubCategory.isChecked();

                Global.myDb.sql("update subtask set done = " + (checked ? 1 : 0) + " where id = " + id + ";");

                childItems.get(groupPosition).get(childPosition).setIsChecked(checked);

                count = 0;
                notifyDataSetChanged();

                for (int i = 0, n = childItems.get(groupPosition).size(); i < n; i++) {
                    if (childItems.get(groupPosition).get(i).getIsChecked()) {
                        count++;
                    }
                }

                // mark as finish if all subtask are finished
                parentItems.get(groupPosition).setIsChecked(count == childItems.get(groupPosition).size());

                notifyDataSetChanged();


                Global.childItems = childItems;
                Global.parentItems = parentItems;
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
