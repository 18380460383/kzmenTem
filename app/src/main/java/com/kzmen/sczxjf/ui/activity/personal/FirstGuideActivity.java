package com.kzmen.sczxjf.ui.activity.personal;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.AppManager;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.adapter.GuidePagerAdapter;
import com.kzmen.sczxjf.control.CustomProgressDialog;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.utils.AppUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 初始新手引导
 */
public class FirstGuideActivity extends SuperActivity {


    // private ImageView[] imageViews; // 点集合
    //背景集合
    private static final int viewBackground[] = {R.mipmap.kz_welcome
    };

    private int mViewCount; //view个数
    private int currIndex;

    ViewPager mPager;

    private CustomProgressDialog progressDialog;
    private IWXAPI api;

    private boolean justLogin = false;
    private GuidePagerAdapter adapter;


    @Override
    public void onCreateDataForView() {
        AppContext.getInstance().setOneActivity(this);
        AppManager.getAppManager().addActivity(this);
        justLogin = getIntent().getBooleanExtra("justLogin", false);
        initWeixin();
        initViewPager();
        initImagePager();
    }

    @Override
    public void setThisContentView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weixin_guide);
    }


    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        mPager = (ViewPager) findViewById(R.id.guide_viewpager);

        List<View> listViews = new ArrayList<>();
        LayoutInflater mInflater = getLayoutInflater();
        mViewCount = viewBackground.length;
        for (int i = 0; i < mViewCount; i++) {
            View view = mInflater.inflate(R.layout.guide_weixin_item, null);
            view.setBackgroundDrawable(new BitmapDrawable(AppUtils.readBitMap(this, viewBackground[i])));

            if (i == mViewCount - 1) {
                Button button = (Button) view.findViewById(R.id.guide_done);
                button.setVisibility(View.VISIBLE);
                button.setOnClickListener(new GuideBtnOnClickListener());
            }

            listViews.add(view);
        }

        adapter = new GuidePagerAdapter(listViews);

        mPager.setAdapter(adapter);
        if (justLogin) {
            mPager.setCurrentItem(mViewCount - 1);
        } else {
            mPager.setCurrentItem(0);
        }

        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }


    /**
     * 初始化点
     */
    private void initImagePager() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.guide_dot_layout);

      /*  imageViews = new ImageView[mViewCount];

        for (int i = 0; i < mViewCount; i++) {
            imageViews[i] = (ImageView) linearLayout.getChildAt(i);
            imageViews[i].setEnabled(true);
            imageViews[i].setTag(i);
        }
        currIndex = 0;
        imageViews[currIndex].setEnabled(false);*/

        if (justLogin) {
            setCurPoint(mViewCount - 1);
        } else {
            setCurPoint(0);
        }
    }


    /**
     * 设置点被选中
     */
    private void setCurPoint(int index) {
        if (index < 0 || index > mViewCount - 1 || currIndex == index) {
            return;
        }
       /* imageViews[currIndex].setEnabled(true);
        imageViews[index].setEnabled(false);*/

        currIndex = index;
    }


    /**
     * 尾页按钮点击监听
     */
    public class GuideBtnOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            /*// 跳转
            showProgressDialog(null);
            getToken();*/
            startActivity(new Intent(FirstGuideActivity.this, com.kzmen.sczxjf.ui.activity.kzmessage.MainTabActivity.class));
            finish();
        }
    }


    /**
     * 页卡切换监听
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
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


    private void initWeixin() {
        if (api == null) {
            api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        }
        api.registerApp(Constants.APP_ID);
    }


    public void getToken() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "none";
        if (!api.sendReq(req)) {
            dismissProgressDialog();
            Toast.makeText(this, "请安装微信App", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }
}
