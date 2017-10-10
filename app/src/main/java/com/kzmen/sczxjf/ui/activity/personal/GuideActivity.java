package com.kzmen.sczxjf.ui.activity.personal;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.adapter.GuidePagerAdapter;
import com.kzmen.sczxjf.base.BaseActivity;
import com.kzmen.sczxjf.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用引导界面
 * 登录
 */

public class GuideActivity extends BaseActivity {

    private ViewPager mPager;//页卡内容
    private List<View> listViews; // Tab页面列表
    //private ImageView[] imageViews; // 点集合
    private TextView tv_ignore;

    //背景集合
    private static final int viewBackground[] = {R.drawable.guide04, R.drawable.guide05,R.drawable.guide06,
             R.drawable.guide07};

    private int mViewCount; //view个数
    private int currIndex;
    private GuidePagerAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.guide);
        initViewPager();
        initImagePager();
    }
    /**
     *
     * 初始化ViewPager
     */
    private void initViewPager() {
        mPager = (ViewPager) findViewById(R.id.guide_viewpager);
        tv_ignore = (TextView) findViewById(R.id.guide_ignore);
        listViews = new ArrayList<>();
        LayoutInflater mInflater = getLayoutInflater();
        mViewCount = viewBackground.length;
        for (int i = 0; i < mViewCount; i++) {
            View view = mInflater.inflate(R.layout.guide_item, null);
            view.setBackgroundDrawable(new BitmapDrawable(AppUtils.readBitMap(this, viewBackground[i])));

            if (i == mViewCount - 1) {
                TextView button = (TextView) view.findViewById(R.id.guide_done);
                button.setVisibility(View.VISIBLE);
                button.setGravity(Gravity.CENTER);
                button.setTextColor(Color.WHITE);
                button.setOnClickListener(new GuideBtnOnClickListener());
            }
            listViews.add(view);
        }
        adapter = new GuidePagerAdapter(listViews);
        mPager.setAdapter(adapter);
        mPager.setCurrentItem(0);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
        tv_ignore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 初始化点
     */
    private void initImagePager() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.guide_dot_layout);
       /* imageViews = new ImageView[mViewCount];
        for (int i = 0; i < mViewCount; i++) {
            imageViews[i] = (ImageView) linearLayout.getChildAt(i);
            imageViews[i].setEnabled(true);
            imageViews[i].setTag(i);
        }*/
        currIndex = 0;
        //imageViews[currIndex].setEnabled(false);
    }


    /**
     * 设置点被选中
     */
    private void setCurPoint(int index) {
        if (index < 0 || index > mViewCount - 1 || currIndex == index) {
            return;
        }
        /*imageViews[currIndex].setEnabled(true);
        imageViews[index].setEnabled(false);*/
        currIndex = index;
    }


    /**
     * 跳转
     */
    private void redirectTo() {
        finish();
    }


    /**
     * 尾页按钮点击监听
     */
    public class GuideBtnOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // 跳转
            redirectTo();
        }
    }


    /**
     * 页卡切换监听
     */
    public class MyOnPageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            setCurPoint(arg0);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter=null;
        System.gc();
    }
}
