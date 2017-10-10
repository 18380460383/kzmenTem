package com.kzmen.sczxjf.ui.activity.personal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.EnConstants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.Advertisement;
import com.kzmen.sczxjf.bean.returned.RedPacket;
import com.kzmen.sczxjf.bean.user.User_For_pe;
import com.kzmen.sczxjf.control.AdvertisementControl;
import com.kzmen.sczxjf.control.RedpacketControl;
import com.kzmen.sczxjf.control.ScreenControl;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.ui.fragment.kzmessage.KzMessageFragment;
import com.kzmen.sczxjf.ui.fragment.personal.CMenuFragment;
import com.kzmen.sczxjf.util.EshareLoger;
import com.kzmen.sczxjf.utils.AppUtils;
import com.kzmen.sczxjf.utils.BitmapUtils;
import com.kzmen.sczxjf.utils.JsonUtils;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * 主页面tab页面
 */
public class MainTabActivity extends SuperActivity implements DrawerLayout.DrawerListener {
    private static final int REDPOINT = 3;
    private static final int LOGIN = 4;
    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.main_headimage)
    ImageView headImage;
    @InjectView(R.id.main_msg_num)
    TextView mainMsgNum;
    @InjectView(R.id.framelayout)
    FrameLayout framelayout;
    @InjectView(R.id.id_drawerlayout)
    DrawerLayout idDrawerlayout;
    @InjectView(R.id.rb_1)
    RadioButton rb1;
    @InjectView(R.id.rb_2)
    RadioButton rb2;
    @InjectView(R.id.id_drawer)
    LinearLayout menu;
    @InjectView(R.id.main_ring)
    RelativeLayout mainRing;
    @InjectView(R.id.more_c_news)
    ImageView moreNews;
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
    /**
     * 个人端菜单栏
     */
    private CMenuFragment fragmentcmenu = new CMenuFragment();
    private static FragmentManager supportFragmentManager;
    /**
     * 个人端首页
     */
  /*  private Fragment1 fragmentchome;*/
    /**
     * 企业端首页
     */
    // private EnterpriseHomeFragment fragmentehome;
    private int msgnum;
    private BroadcastReceiver receiver;
    private KzMessageFragment kzMessageFragment;

    @Override
    public void onCreateDataForView() {

        setAccBroadcastReceiver();
      //  AppContext.maintabeactivity = this;
        supportFragmentManager = getSupportFragmentManager();
        initDate();
        //TODO 启动版本更新控制器
        //UpgradeControl.getUpgradeControl(this).update();
        idDrawerlayout.setDrawerListener(this);
        back.setVisibility(View.GONE);
        //TODO 设置一个空监听省去两层 事件拦截防止点击菜单栏照成Fragment控件相应
        menu.setOnClickListener(null);
        int i = new ScreenControl().getscreenWide();
        DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams) menu.getLayoutParams();
        layoutParams.width = (int) (i * 0.7);
        menu.setLayoutParams(layoutParams);

        kzMessageFragment=new KzMessageFragment();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.framelayout, kzMessageFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_main_tab);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!rb1.isChecked() && !rb2.isChecked()) {
            rb1.setChecked(true);
            rb1.setTextColor(getResources().getColor(R.color.text_hei));
        }
       /* if (fragmentchome != null) {
            fragmentchome.activityOnResume();
        }*/

    }

    @OnClick({R.id.main_headimage, R.id.rb_1, R.id.rb_2, R.id.main_ring, R.id.more_c_news})
    public void onclick(View view) {
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.more_c_news:
               /* Intent intent1 = new Intent(this, MoreNewsActivity.class);
                startActivity(intent1);*/
                break;
            case R.id.main_headimage:
                //TODO 左侧打开菜单
                if (AppContext.getInstance().getPersonageOnLine()) {
                    idDrawerlayout.openDrawer(GravityCompat.START);
                } else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.putExtra("state", 8);
                    startActivityForResult(intent, LOGIN);
                }
                break;
            case R.id.rb_1:
                rb1.setTextColor(getResources().getColor(R.color.text_hei));
                rb2.setTextColor(getResources().getColor(R.color.white));
                //TODO 打开手势滑动
                headImage.setVisibility(View.VISIBLE);
                moreNews.setVisibility(View.VISIBLE);
                mainRing.setVisibility(View.VISIBLE);
                idDrawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                //TODO 点击个人端按钮设置显示个人端主页
                /*if (kzMessageFragment == null||kzMessageFragment.getView()==null) {
                    kzMessageFragment = new KzMessageFragment();
                    fragmentTransaction.add(R.id.framelayout, kzMessageFragment);
                }
                if (kzMessageFragment != null) {
                    fragmentTransaction.hide(kzMessageFragment);
                }
                fragmentTransaction.show(kzMessageFragment);
                fragmentTransaction.commit();*/
                break;
            case R.id.rb_2:
               /* rb2.setTextColor(getResources().getColor(R.color.text_hei));
                rb1.setTextColor(getResources().getColor(R.color.white));
                //TODO 关闭手势滑动
                idDrawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                headImage.setVisibility(View.GONE);
                moreNews.setVisibility(View.GONE);
                mainRing.setVisibility(View.GONE);
                //TODO 点击企业端按钮设置显示企业端主页
                if (fragmentehome == null||fragmentehome.getView()==null) {
                    fragmentehome = new EnterpriseHomeFragment();
                    fragmentTransaction.add(R.id.framelayout, fragmentehome);
                }
                if (fragmentchome != null) {
                    fragmentTransaction.hide(fragmentchome);
                    //TODO　关闭广告轮播
                    fragmentchome.setSlide(false);
                }

                fragmentTransaction.show(fragmentehome);
                fragmentTransaction.commit();*/
                break;
            case R.id.main_ring:
                Intent intent = new Intent(this, MsgCenterActivity.class);
                String value = mainMsgNum.getText().toString();
                try {
                    if (TextUtils.isEmpty(value)) {
                        intent.putExtra(MsgCenterActivity.MSGNUM, 0);
                    } else {
                        intent.putExtra(MsgCenterActivity.MSGNUM, Integer.valueOf(value));
                    }
                } catch (Exception e) {
                    intent.putExtra(MsgCenterActivity.MSGNUM, 0);
                }
                startActivityForResult(intent, REDPOINT);
                break;
        }
    }

    /**
     * 杨操
     * 加载页面数据
     */
    private void initDate() {
        onLoginSuccess(AppContext.getInstance().getPEUser());

    }


    /**
     * 登录成功
     */
    private void onLoginSuccess(User_For_pe login) {
        if (!TextUtils.isEmpty(login.getUid())) {
            getLogoAd();
        }
        //TODO 登陆信息正常后初始化界面
        //TODO 加载菜单
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        kzMessageFragment = new KzMessageFragment();
        fragmentTransaction.replace(R.id.framelayout, kzMessageFragment);
        fragmentTransaction.commit();
        if (AppContext.getInstance().getPersonageOnLine()) {
            setHeadImageAndMenu(login);
        }
    }

    public void setHeadImageAndMenu(User_For_pe login) {
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentcmenu.setMenuBack(new CMenuFragment.MenuBack() {
            @Override
            public void startActivity() {
                idDrawerlayout.closeDrawer(GravityCompat.START);
            }
        });
        fragmentTransaction.replace(R.id.id_drawer, fragmentcmenu);
     /*   if(fragmentchome!=null){
            fragmentTransaction.show(fragmentchome);
        }*/
        fragmentTransaction.commitAllowingStateLoss();

        final double v = new ScreenControl().getscreenHigh() / 16 * 1.5 - 60;
        ImageLoader.getInstance().loadImage(login.getImageurl(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                headImage.setImageBitmap(BitmapUtils.toRoundBitmap(AppUtils.readBitMap(MainTabActivity.this, R.drawable.image_def)));
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                headImage.setImageBitmap(BitmapUtils.toRoundBitmap(AppUtils.readBitMap(MainTabActivity.this, R.drawable.image_def)));
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                if (bitmap == null) {
                } else {
                    Bitmap bm = BitmapUtils.toRoundBitmap(bitmap);
                    headImage.setImageBitmap(bm);
                }
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                headImage.setImageBitmap(BitmapUtils.toRoundBitmap(AppUtils.readBitMap(MainTabActivity.this, R.drawable.image_def)));
            }
        });
        //TODO 强制绑定电话号码
       /* if (TextUtils.isEmpty(AppContext.getInstance().getUserInfo().on_phone)) {
                startActivity(new Intent(this, CheckPhoneActivity.class));
            }*/
        getRedPacket();
        getMsgnum();
        putJPusID();
        /*if(fragmentehome!=null){
            fragmentehome.initData();
        }*/
    }


    /**
     * 上传极光ID
     */
    private void putJPusID() {
        String registrationID = JPushInterface.getRegistrationID(getApplicationContext());
        Map<String, String> map = new HashMap<>();
        new RequestParams();
        map.put("uid", AppContext.getInstance().getPEUser().getUid());
        map.put("form", "android");

        map.put("jpushid", registrationID);
        RequestParams requestParams = AppUtils.getParm(map);
        NetworkDownload.jsonPost(this, Constants.URL_PUT_JPUSID, requestParams, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {

            }

            @Override
            public void onFailure() {

            }
        });
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
            int anInt = data.getExtras().getInt(MsgCenterActivity.MSGNUM);
            setMoneyMsgNum(anInt);
        } else if (resultCode == RESULT_OK && requestCode == LOGIN) {
            if (data.getIntExtra("loginstate", 0) == 1) {
                setHeadImageAndMenu(AppContext.getInstance().getPEUser());
            }
        }
      /*  if(resultCode == EnLoginActivity.CODE_SUCCESS) {
            EshareLoger.logI("onActivityResult:login success");
            fragmentehome.initData();
        }*/
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
        NetworkDownload.stopRequest(this);
        unregisterReceiver(receiver);
    }

    /**
     * 杨操
     * 设置未读消息红点的值
     *
     * @param count 广播接受者接收的数据
     */
    public void setMoneyMsgNum(int count) {
        if (count < 1) {
            mainMsgNum.setText("0");
            mainMsgNum.setVisibility(View.GONE);
        } else {
            mainMsgNum.setVisibility(View.VISIBLE);
        }
        if (count > 0 && count <= 99) {
            mainMsgNum.setText(count + "");
        } else if (count > 99) {
            mainMsgNum.setText("99+");
        }
    }


    /**
     * 杨操
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

    /**
     * 杨操
     * 获取启动广告
     */
    private void getLogoAd() {
        RequestParams params = new RequestParams();
        params.add("uid", AppContext.getInstance().getPEUser().getUid());
        NetworkDownload.jsonGetForCode1(this, Constants.URL_GET_LOGO_AD, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                JSONObject data = jsonObject.optJSONObject("data");
                if (null != data) {
                    Advertisement bean = JsonUtils.getBean(data, Advertisement.class);
                    AdvertisementControl advertisementControl = AdvertisementControl.getAdvertisementControl();
                    advertisementControl.saveAdvertisement(bean);
                }
            }

            @Override
            public void onFailure() {

            }
        });
    }

    /**
     * 杨操
     * 查询红包
     */
    private void getRedPacket() {
        RequestParams params = new RequestParams();
        params.add("uid", AppContext.getInstance().getPEUser().getUid());
        NetworkDownload.jsonGetForCode1(this, Constants.URL_GET_REDPACKET, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                List<RedPacket> data = JsonUtils.getBeanList(jsonObject.optJSONArray("data"), RedPacket.class);
                if (data != null && data.size() > 0) {
                    RedPacket red = data.get(0);
                    if (red != null) {
                        RedpacketControl r = new RedpacketControl(MainTabActivity.this, red);
                        r.showRedDialog();
                    }
                }
            }

            @Override
            public void onFailure() {

            }
        });
    }


    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    public void getMsgnum() {
        RequestParams params = new RequestParams();
        params.add("uid", AppContext.getInstance().getPEUser().getUid());
        NetworkDownload.jsonGetForCode1(null, Constants.URL_GET_MSGNUM, params, new NetworkDownload.NetworkDownloadCallBackJson() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                msgnum = jsonObject.optInt("data");
                setMoneyMsgNum(msgnum);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*if (fragmentchome != null) {
            fragmentchome.activityOnPause();
        }*/
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }

    @Override
    public boolean isCanExit() {
        return false;
    }

    private void setAccBroadcastReceiver() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                ImageLoader.getInstance().loadImage(AppContext.getInstance().getPEUser().getImageurl(), new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String s, View view) {
                        headImage.setImageBitmap(BitmapUtils.toRoundBitmap(AppUtils.readBitMap(MainTabActivity.this, R.drawable.image_def)));
                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason) {
                        headImage.setImageBitmap(BitmapUtils.toRoundBitmap(AppUtils.readBitMap(MainTabActivity.this, R.drawable.image_def)));
                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                        if (bitmap == null) {
                            //Toast.makeText(MainTabActivity.this, "图片加载失败", Toast.LENGTH_SHORT).show();
                        } else {
                            Bitmap bm = BitmapUtils.toRoundBitmap(bitmap);
                            headImage.setImageBitmap(bm);
                        }
                    }

                    @Override
                    public void onLoadingCancelled(String s, View view) {
                        headImage.setImageBitmap(BitmapUtils.toRoundBitmap(AppUtils.readBitMap(MainTabActivity.this, R.drawable.image_def)));
                    }
                });
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.WEIXIN_ACCREDIT);
        registerReceiver(receiver, filter);
        BroadcastReceiver loginReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                EshareLoger.logI("ON LOGIN SUCCESS");
                //fragmentehome.initData();
            }
        };
        IntentFilter filter2 = new IntentFilter();
        filter2.addAction(EnConstants.BROCAST_LOGIN_SUCCESS);
        registerReceiver(loginReceiver, filter2);
    }

    public void extP() {
        headImage.setImageResource(R.drawable.userhead);
        /*if(fragmentehome!=null){
            fragmentehome.initData();
        }*/
    }
    public void closeDraw(){
        idDrawerlayout.closeDrawer(GravityCompat.START);
    }
}
