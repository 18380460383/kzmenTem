package com.kzmen.sczxjf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.HomepageMenuBean;

import java.util.List;

/**
 * Created by pjj18 on 2017/8/4.
 */

public class KzMainColumnAdapter extends BaseAdapter {
    private List<HomepageMenuBean> listData;
    private Context mContext;

    public KzMainColumnAdapter(Context mContext, List<HomepageMenuBean> listData) {
        this.mContext = mContext;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.kz_main_column_item, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_type = (ImageView) view.findViewById(R.id.iv_type);
            viewHolder.tv_tiltle = (TextView) view.findViewById(R.id.tv_title);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_tiltle.setText(listData.get(i).getName());
        Glide.with(mContext).load(listData.get(i).getIcon())
                .placeholder(R.drawable.icon_image_normal).dontAnimate().into(viewHolder.iv_type);
        return view;
    }

    class ViewHolder {
        private ImageView iv_type;
        private TextView tv_tiltle;
    }
}
