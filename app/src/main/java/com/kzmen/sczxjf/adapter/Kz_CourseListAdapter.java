package com.kzmen.sczxjf.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/8/9.
 */

public class Kz_CourseListAdapter extends BaseAdapter{
    private List<String>listData;
    private Context mContext;

    public Kz_CourseListAdapter(List<String> listData, Context mContext) {
        this.listData = listData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

}
