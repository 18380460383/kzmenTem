package com.kzmen.sczxjf.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.fragment.kzmessage.CourseCollectionFragment;

/**
 * Created by Administrator on 2017/8/9.
 */

public class Kz_FragmentAdapter extends FragmentStatePagerAdapter {
    private String[] titles;
    private LayoutInflater mInflater;
    private Context mContext;
    public Kz_FragmentAdapter(FragmentManager fm,Context mContext,String[] titles) {
        super(fm);
        this.titles=titles;
        this.mContext=mContext;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = new CourseCollectionFragment();
        Bundle bundle = null;
        switch (position) {
            case 0:
                bundle = new Bundle();
                bundle.putString("type", "1");
                break;
            case 1:
                bundle = new Bundle();
                bundle.putString("type", "2");
                break;
            case 2:
                bundle = new Bundle();
                bundle.putString("type", "3");
                break;
            default:
                bundle = new Bundle();
                bundle.putString("type", "3");
                break;
        }
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
        return view;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }
}
