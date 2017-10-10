package com.kzmen.sczxjf.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.CourseDetailBean;
import com.kzmen.sczxjf.ui.fragment.kzmessage.CourseDetailPlayListFragment;

/**
 * Created by Administrator on 2017/8/9.
 */

public class Kz_Course_FragmentAdapter extends FragmentStatePagerAdapter {
    private String[] titles;
    private LayoutInflater mInflater;
    private Context mContext;
    private CourseDetailBean bean;

    public Kz_Course_FragmentAdapter(FragmentManager fm, Context mContext, String[] titles, CourseDetailBean bean) {
        super(fm);
        this.titles = titles;
        this.mContext = mContext;
        this.bean = bean;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new CourseDetailPlayListFragment();
        Bundle bundle = null;
        bundle = new Bundle();
        bundle.putString("cid", bean.getCid());
        bundle.putString("iszan", "" + bean.getIszan());
        bundle.putString("zans", bean.getZans());
        bundle.putString("type", bean.getType());
        bundle.putString("collect", "" + bean.getIscollect());
        bundle.putString("isunlock", bean.getIsunlock());
        bundle.putString("unlock_desc", bean.getUnlock_desc());
        bundle.putString("unlock_money", bean.getUnlock_money());
        bundle.putInt("tabPos", position);
        bundle.putSerializable("stage", bean.getStage_list().get(position));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) super.instantiateItem(container, position);
        } catch (Exception e) {

        }
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    /**
     * 添加getTabView的方法，来进行自定义Tab的布局View
     *
     * @param position
     * @return
     */
    public View getTabView(int position) {
        mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.kz_tab_item_layout, null);
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setText(titles[position]);
        ImageView img = (ImageView) view.findViewById(R.id.imageView);
        LinearLayout ll_main = (LinearLayout) view.findViewById(R.id.ll_main);
        tv.setTextColor(Color.argb(255, 0, 0, 0));

        if (bean.getStage_list().get(position).getIsunlock() == 1) {
            img.setVisibility(View.GONE);
            ll_main.setBackgroundResource(R.color.white);
        } else {
            img.setVisibility(View.VISIBLE);
            ll_main.setBackgroundResource(R.color.title);
            tv.setTextColor(Color.argb(255, 173, 173, 173));
        }
        if (!bean.getType().equals("1")) {
            if (!bean.getIsunlock().equals("1")) {
                img.setVisibility(View.VISIBLE);
                ll_main.setBackgroundResource(R.color.title);
                tv.setTextColor(Color.argb(255, 173, 173, 173));
            }
        }
        return view;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }
}
