package com.kzmen.sczxjf.ui.activity.menu;

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
import android.widget.TextView;

import com.google.gson.Gson;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.AskCountBean;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.ui.fragment.kzmessage.MyAskFragment;
import com.kzmen.sczxjf.ui.fragment.kzmessage.MyCourseFragment;
import com.kzmen.sczxjf.ui.fragment.kzmessage.MyListenFragment;
import com.kzmen.sczxjf.utils.TextUtil;
import com.vondear.rxtools.RxLogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.InjectView;

/**
 * 我的问答
 */
public class MyAskActivity extends SuperActivity {
    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.tab_layout)
    TabLayout tabLayout;
    @InjectView(R.id.info_viewpager)
    ViewPager infoViewpager;
    private String[] titles = new String[]{"课程提问", "回答提问", "我的偷听"};
    private MyFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "我的问答");
       // initView();
        initData();
    }
    private AskCountBean askBean;
    private void initData() {
        showProgressDialog("加载中");
        OkhttpUtilManager.postNoCacah(this, "User/getMyQuestionCount/", null, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                RxLogUtils.e("tst",data);
                try {
                    JSONObject object = new JSONObject(data);
                    Gson gson = new Gson();
                    askBean = gson.fromJson(object.getString("data"), AskCountBean.class);
                    if(null!=askBean){
                        initView();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissProgressDialog();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                dismissProgressDialog();
            }
        });
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_my_ask);
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
                    fragment = new MyCourseFragment();
                    bundle = new Bundle();
                    bundle.putString("type", "1");
                    break;
                case 1:
                    fragment = new MyAskFragment();
                    bundle = new Bundle();
                    bundle.putString("type", "2");
                    break;
                case 2:
                    fragment = new MyListenFragment();
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
            mInflater = LayoutInflater.from(MyAskActivity.this);
            View view = mInflater.inflate(R.layout.kz_tab_item_layout_sign, null);
            TextView tv = (TextView) view.findViewById(R.id.textView);
            TextView tv_sign = (TextView) view.findViewById(R.id.tv_sign);
            tv.setText(titles[position]);
            ImageView img = (ImageView) view.findViewById(R.id.imageView);
            tv_sign.setVisibility(View.VISIBLE);
            switch (position){
                case 0:
                    if(TextUtil.isEmpty(askBean.getCourse()) || askBean.getCourse().equals("0")){
                        tv_sign.setVisibility(View.GONE);
                    }else{
                        tv_sign.setText(askBean.getCourse());
                    }
                    break;
                case 1:
                    if(TextUtil.isEmpty(askBean.getInterlocution()) || askBean.getInterlocution().equals("0")){
                        tv_sign.setVisibility(View.GONE);
                    }else{
                        tv_sign.setText(askBean.getInterlocution());
                    }
                    break;
                case 2:
                    if(TextUtil.isEmpty(askBean.getEavesdrop()) || askBean.getEavesdrop().equals("0")){
                        tv_sign.setVisibility(View.GONE);
                    }else{
                        tv_sign.setText(askBean.getEavesdrop());
                    }
                    break;
            }
            return view;
        }

        public void setTitles(String[] titles) {
            this.titles = titles;
        }

    }

}
