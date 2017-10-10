package com.kzmen.sczxjf.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 新手引导页适配器
 * viewpager
 */

public class GuidePagerAdapter extends PagerAdapter{

    public List<View> mListViews;
    
    public GuidePagerAdapter(List<View> mListViews) {
        this.mListViews = mListViews;
    }
    




    @Override
    public int getCount() {
        return mListViews.size();
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mListViews.get(position));

        return mListViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mListViews.get(position));
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == (arg1);
    }





    
}
