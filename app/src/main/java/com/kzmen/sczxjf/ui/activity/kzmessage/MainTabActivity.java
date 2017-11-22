package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.ReturnOrderBean;
import com.kzmen.sczxjf.bean.kzbean.UserBean;
import com.kzmen.sczxjf.bean.kzbean.UserMessageBean;
import com.kzmen.sczxjf.control.ScreenControl;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.AgOkhttpUtilManager;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.ui.fragment.kzmessage.KzMessageFragment;
import com.kzmen.sczxjf.ui.fragment.personal.CMenuFragment;
import com.kzmen.sczxjf.util.glide.GlideCircleTransform;
import com.kzmen.sczxjf.utils.TextUtil;
import com.vondear.rxtools.RxLogUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * 主页面tab页面
 */
public class MainTabActivity extends SuperActivity implements DrawerLayout.DrawerListener {
    private static final int REDPOINT = 3;
    private static final int LOGIN = 4;
    @InjectView(R.id.main_headimage)
    ImageView headImage;
    @InjectView(R.id.iv_sign)
    ImageView iv_sign;
    @InjectView(R.id.iv_search)
    ImageView iv_search;
    @InjectView(R.id.framelayout)
    FrameLayout framelayout;
    @InjectView(R.id.id_drawerlayout)
    DrawerLayout idDrawerlayout;
    @InjectView(R.id.id_drawer)
    LinearLayout menu;
    @InjectView(R.id.iv_history)
    ImageView ivHistory;
    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.tv_state)
    TextView tv_state;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.ll_msg)
    RelativeLayout ll_msg;
    @InjectView(R.id.ll_search)
    LinearLayout ll_search;
    @InjectView(R.id.rb_1)
    RadioButton rb1;
    @InjectView(R.id.rb_2)
    RadioButton rb2;
    private ServiceConnection mPlayServiceConnection;
    // protected Handler mHandler = new Handler(Looper.getMainLooper());
    /**
     * 当前dialog是否显示在界面上
     */
    private boolean isDialogShowing = false;
    /**
     * 弹出对话框
     */
    private final int CODE_DIALOG = 1;
    /**
     * 判断返回键两次点击时常
     */
    private long exitTime = 0;

    private CMenuFragment fragmentcmenu = new CMenuFragment();
    private static FragmentManager supportFragmentManager;
    private BroadcastReceiver receiver;
    private KzMessageFragment kzMessageFragment;
    private boolean isopen = false;

    @Override
    public void onCreateDataForView() {
        checkService();
        //setAccBroadcastReceiver();
        initUserMessage();
        AppContext.setMaintabeactivity(this);
        supportFragmentManager = getSupportFragmentManager();
        initDate();
        //TODO 启动版本更新控制器
        //UpgradeControl.getUpgradeControl(this).update();
        idDrawerlayout.setDrawerListener(this);
        //TODO 设置一个空监听省去两层 事件拦截防止点击菜单栏照成Fragment控件相应
        menu.setOnClickListener(null);
        int i = new ScreenControl().getscreenWide();
        DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams) menu.getLayoutParams();
        layoutParams.width = (int) (i * 0.7);
        menu.setLayoutParams(layoutParams);
        back.setVisibility(View.INVISIBLE);
        setOnloading(R.id.ll_content);
        mLayout.onLoading();
        Glide.with(this).load(AppContext.getInstance().getUserLogin().getAvatar()).placeholder(R.drawable.icon_user_normal).transform(new GlideCircleTransform(this)).into(headImage);
        updataToken();

    }

    private void initUserMessage() {

    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 数据下载完成，转换状态，显示内容视图
            switch (msg.what) {
                case 0:
                    mLayout.onError();
                    break;
                case 1:
                    mLayout.onDone();
                    break;
                default:
                    mLayout.onEmpty();
                    break;
            }
        }
    };

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_main_tab2);
        EventBus.getDefault().register(this);
        ButterKnife.inject(this);
    }

    private void getMsgCount() {
        Map<String, String> params = new HashMap<>();
        params.put("page", "1");
        params.put("limit", "50");
        params.put("member_id", "" + AppContext.getInstance().getUserMessageBean().getUid());
        params.put("message_type", "20");
        params.put("is_read", "0");
        AgOkhttpUtilManager.postNoCacah(this, "users/member_message_list", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String count = jsonObject.getString("total");
                    if (!TextUtil.isEmpty(count) && Integer.valueOf(count) > 0) {
                        tv_state.setVisibility(View.VISIBLE);
                    } else {
                        tv_state.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    tv_state.setVisibility(View.GONE);
                }
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                tv_state.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!rb1.isChecked() && !rb2.isChecked()) {
            rb1.setChecked(true);
            rb1.setTextColor(getResources().getColor(R.color.white));
        }
    }

    @OnClick({R.id.main_headimage, R.id.ll_msg, R.id.ll_search, R.id.rb_1, R.id.iv_search})
    public void onclick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.main_headimage:
                //TODO 左侧打开菜单
                if (AppContext.getInstance().getPersonageOnLine()) {
                    isopen = true;
                    getUserInfo();
                } else {
                    intent = new Intent(this, IndexActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_msg:
                intent = new Intent(this, MsgCenterActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_search:
                intent = new Intent(this, CourseSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.rb_1:
                rb1.setTextColor(getResources().getColor(R.color.white));
                rb2.setTextColor(getResources().getColor(R.color.yellow));
                break;
            case R.id.iv_search:
                intent = new Intent(this, CourseSearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * peng
     * 加载页面数据
     */
    private void initDate() {
        onLoginSuccess(AppContext.getInstance().getUserLogin());
    }


    /**
     * 登录成功
     */
    private void onLoginSuccess(UserBean login) {
        //TODO 登陆信息正常后初始化界面
        //TODO 加载菜单
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        kzMessageFragment = new KzMessageFragment();
        fragmentTransaction.add(R.id.framelayout, kzMessageFragment);
        fragmentTransaction.commit();
        setHeadImageAndMenu(login);
    }

    public void setHeadImageAndMenu(UserBean login) {
        setHeadImageNew(login);
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentcmenu.setMenuBack(new CMenuFragment.MenuBack() {
            @Override
            public void startActivity() {
                idDrawerlayout.closeDrawer(GravityCompat.START);
            }
        });
        fragmentTransaction.add(R.id.id_drawer, fragmentcmenu);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void setHeadImageNew(UserBean login) {
        if (null != login && AppContext.getInstance().getPersonageOnLine()) {
            Glide.with(this).load(login.getAvatar()).placeholder(R.drawable.icon_user_normal)
                    .transform(new GlideCircleTransform(this)).into(headImage);
            if (null != login.getRole() && login.getRole().equals("1")) {
                iv_sign.setBackgroundResource(R.drawable.icon_vip);
            }
        }
    }

    @Override
    public void onBackPressed() {
        //TODO 如果显示了冻结窗口，拦截back按键，返回微信登陆
        if (isDialogShowing) {
            returnLogin();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == CODE_DIALOG) {
            isDialogShowing = false;
            returnLogin();
        } else if (resultCode == RESULT_OK && requestCode == REDPOINT) {
        } else if (resultCode == RESULT_OK && requestCode == LOGIN) {
            if (data.getIntExtra("loginstate", 0) == 1) {
                setHeadImageAndMenu(AppContext.getInstance().getUserLogin());
            }
        }
    }

    /**
     * 失败后返回微信引导页
     */
    public void returnLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("justLogin", true);
        this.startActivity(intent);
        this.finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppContext.getInstance().setNoLine();
        EventBus.getDefault().unregister(this);
        NetworkDownload.stopRequest(this);
        //unregisterReceiver(receiver);
    }


    /**
     * peng
     * 重写方法实现双击返回键退出应用设置前提先关闭菜单栏
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (idDrawerlayout.isDrawerOpen(GravityCompat.START)) {
                idDrawerlayout.closeDrawer(GravityCompat.START);
            } else {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    AppContext.getInstance().setNoLine();
                    //OffersManager.getInstance(this).onAppExit();
                    finish();
                    System.exit(0);
                }
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {
        if (AppContext.getInstance().getPersonageOnLine() && fragmentcmenu != null) {
            fragmentcmenu.setUserInfo();
        }
    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (AppContext.getPlayService() != null) {
            AppContext.getPlayService().stop();
        }
    }

    @Override
    public boolean isCanExit() {
        return false;
    }

    public void extP() {
        headImage.setImageResource(R.drawable.icon_user_normal);
    }

    public void closeDraw() {
        idDrawerlayout.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void funOrder(ReturnOrderBean bean) {
        super.funOrder(bean);
        // showOrderPayBack(bean);
        RxLogUtils.e("tst", bean.toString());
        if (bean.getType() == 1) {
            kzMessageFragment.kz_mainAskAdapter.updateOpen();
        }
    }

    private void updataToken() {
        OkhttpUtilManager.postNoCacah(this, "Public/autoLogin", null, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                try {
                    JSONObject object = new JSONObject(data);
                    Gson gson = new Gson();
                    UserBean bean = gson.fromJson(object.getString("data"), UserBean.class);
                    Log.e("tst", bean.toString());
                    AppContext.getInstance().setUserLogin(bean);
                    AppContext.getInstance().setPersonageOnLine(true);
                    getMsgCount();
                    getUserInfo();
                } catch (Exception e) {
                    e.printStackTrace();
                    AppContext.getInstance().setPersonageOnLine(false);
                    AppContext.getInstance().setUserLoginOut();
                }
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                AppContext.getInstance().setPersonageOnLine(false);
                AppContext.getInstance().setUserLoginOut();
            }
        });
    }

    public void getUserInfo() {
        OkhttpUtilManager.postNoCacah(this, "User/get_user_info", null, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                try {
                    JSONObject object = new JSONObject(data);
                    Gson gson = new Gson();
                    UserMessageBean bean = gson.fromJson(object.getString("data"), UserMessageBean.class);
                    AppContext.userMessageBean = bean;
                    AppContext.getInstance().setUserMessageBean(bean);
                    if (isopen) {
                        idDrawerlayout.openDrawer(GravityCompat.START);
                    }
                    isopen = false;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                AppContext.getInstance().setPersonageOnLine(false);
                AppContext.getInstance().setUserLoginOut();
                startActivity(new Intent(MainTabActivity.this, IndexActivity.class));
            }
        });
    }
}
