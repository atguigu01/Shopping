package com.atguigu.shoppingmall.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.home.activity.GoodsListActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/10/9.
 */
public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> group;
    private List<List<String>> child;
    private GoodsListActivity goodsListActivity;
    private int childP;
    private int groupP;

    public ExpandableListViewAdapter(GoodsListActivity context, ArrayList<String> group, ArrayList<List<String>> child) {
        this.context = context;
        this.group = group;
        this.child = child;
        goodsListActivity = new GoodsListActivity();
    }

    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child.get(childPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.group_list_item, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.textView);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(group.get(groupPosition));
        //  holder.textView.setTextSize(20);
        holder.textView.setPadding(0, 10, 0, 10);
        if (isExpanded) {
            holder.imageView.setImageResource(R.drawable.filter_list_selected);
        } else {
            holder.imageView.setImageResource(R.drawable.filter_list_unselected);
        }
        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.child_list_item, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.textView);
            holder.childImageView = (ImageView) convertView.findViewById(R.id.childImageView);
            holder.ll_child_root = (LinearLayout) convertView.findViewById(R.id.ll_child_root);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (groupPosition != 0) {
            holder.textView.setText(child.get(groupPosition).get(childPosition));
        }

        //  Toast.makeText(context, "childP" + childP + " " + groupP, Toast.LENGTH_SHORT).show();
        if (childP == childPosition && groupP == groupPosition) {
            holder.childImageView.setVisibility(View.VISIBLE);
            notifyDataSetChanged();
        } else {
            holder.childImageView.setVisibility(View.GONE);
            notifyDataSetChanged();
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        childP = childPosition;
        groupP = groupPosition;
        return true;
    }

    class ViewHolder {
        TextView textView;
        ImageView imageView;
        ImageView childImageView;
        LinearLayout ll_child_root;
    }
}
