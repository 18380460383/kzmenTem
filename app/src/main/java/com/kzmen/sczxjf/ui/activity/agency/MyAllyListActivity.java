package com.kzmen.sczxjf.ui.activity.agency;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.ui.fragment.kzmessage.MyAllyFragment;

import butterknife.InjectView;
import butterknife.OnClick;

public class MyAllyListActivity extends SuperActivity {

    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.iv_add)
    ImageView ivAdd;
    @InjectView(R.id.tab_layout)
    TabLayout tabLayout;
    @InjectView(R.id.info_viewpager)
    ViewPager infoViewpager;
    private String[] titles = new String[]{"全部", "已付款", "未付款"};
    private MyFragmentAdapter adapter;
    private int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "我的盟友");
        initView();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_my_ally_list);
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            pos = bundle.getInt("pos");
        }
    }

    @OnClick(R.id.iv_add)
    public void onViewClicked(View view) {
        showInfoPopu(view);
    }

    private void initView() {
        adapter = new MyFragmentAdapter(getSupportFragmentManager());
        adapter.setTitles(titles);
        infoViewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(infoViewpager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //设置自定义Tab--加入图标的demo
        for (int i = 0; i < titles.length; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }
        infoViewpager.setCurrentItem(pos);
    }


    public class MyFragmentAdapter extends FragmentStatePagerAdapter {
        private String[] titles;
        private LayoutInflater mInflater;

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;
            Bundle bundle = null;
            switch (position) {
                case 0:
                    fragment = new MyAllyFragment();
                    bundle = new Bundle();
                    bundle.putString("type", "1");
                    break;
                case 1:
                    fragment = new MyAllyFragment();
                    bundle = new Bundle();
                    bundle.putString("type", "2");
                    break;
                case 2:
                    fragment = new MyAllyFragment();
                    bundle = new Bundle();
                    bundle.putString("type", "3");
                    break;
            }
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
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
            mInflater = LayoutInflater.from(MyAllyListActivity.this);
            View view = mInflater.inflate(R.layout.kz_tab_item_layout_sign, null);
            TextView tv = (TextView) view.findViewById(R.id.textView);
            TextView tv_sign = (TextView) view.findViewById(R.id.tv_sign);
            tv.setText(titles[position]);
            ImageView img = (ImageView) view.findViewById(R.id.imageView);
            tv_sign.setVisibility(View.GONE);
            switch (position) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
            }
            return view;
        }

        public void setTitles(String[] titles) {
            this.titles = titles;
        }

    }
}
