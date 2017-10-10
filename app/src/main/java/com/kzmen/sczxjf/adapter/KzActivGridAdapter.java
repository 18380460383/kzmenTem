package com.kzmen.sczxjf.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.HomeActivityBean;
import com.kzmen.sczxjf.ui.activity.kzmessage.WebAcitivity;

import java.util.List;

/**
 * Created by pjj18 on 2017/8/4.
 */

public class KzActivGridAdapter extends BaseAdapter {
    private List<HomeActivityBean> listData;
    private Context mContext;

    public KzActivGridAdapter(Context mContext, List<HomeActivityBean> listData) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.kz_image_item, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_image = (ImageView) view.findViewById(R.id.iv_image);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(mContext).load(listData.get(i).getImageurl())
                .placeholder(R.drawable.icon_image_normal).dontAnimate().into(viewHolder.iv_image);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, WebAcitivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("title",listData.get(i).getTitle());
                bundle.putString("url",listData.get(i).getLinkurl());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        return view;
    }

    class ViewHolder {
        private ImageView iv_image;
    }
}
