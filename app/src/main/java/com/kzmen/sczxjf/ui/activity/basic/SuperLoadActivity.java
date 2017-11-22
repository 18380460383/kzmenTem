package com.kzmen.sczxjf.ui.activity.basic;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.EventBusBean;
import com.kzmen.sczxjf.bean.kzbean.ReturnOrderBean;
import com.kzmen.sczxjf.bean.kzbean.UserMessageBean;
import com.kzmen.sczxjf.control.CustomProgressDialog;
import com.kzmen.sczxjf.cusinterface.ServerConnect;
import com.kzmen.sczxjf.dialog.ShareDialog;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.interfaces.ScrollViewOnScroll;
import com.kzmen.sczxjf.interfaces.UserOperate;
import com.kzmen.sczxjf.net.AgOkhttpUtilManager;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.popuwidow.InfoPopuwindow;
import com.kzmen.sczxjf.popuwidow.MsgShowPopuwindow;
import com.kzmen.sczxjf.smartlayout.widgit.CustomLoadingLayout;
import com.kzmen.sczxjf.smartlayout.widgit.SmartLoadingLayout;
import com.kzmen.sczxjf.test.server.PlayService;
import com.kzmen.sczxjf.ui.activity.kzmessage.IndexActivity;
import com.kzmen.sczxjf.util.EToastUtil;
import com.kzmen.sczxjf.util.Utils;
import com.kzmen.sczxjf.view.MyScrollView;
import com.vondear.rxtools.RxLogUtils;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.dialog.RxDialogPayBack;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * Created by pjj18 on 2017/11/15.
 */

public abstract class SuperLoadActivity extends FragmentActivity implements ServerConnect,
        ScrollViewOnScroll, UserOperate {
    private static final String TAG = "BasicActivity";
    /**
     * 标题栏
     */
    public View title;
    /**
     * 标题控件
     */
    public TextView titleNameView;
    /**
     * 网络请求对话框
     */
    private CustomProgressDialog progressDialog;
    /**
     * 退出应用广播Action
     */
    public static final String EXIT = "com.brocast.exit";
    /**
     * 退出应用广播对象
     */
    private BroadcastReceiver exitReceiver;
    /**
     * 判断退出应用广播是否注册
     */
    private boolean isStartExitReceiver = false;

    private MyScrollView scrollView;

    protected RxDialogPayBack rxDialogPayBack;

    public boolean isHasPemis = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //判断此界面是不是对外直接开放的
        //如果不是
        if (!isShareActivity()) {
            if (!AppContext.getInstance().getPersonageOnLine()) {
                Intent intent = new Intent(this, IndexActivity.class);
                this.startActivity(intent);
                this.finish();
            } else {
                initActivity();
            }
        }
        //如果是
        else {
            initActivity();
        }
        // setInnerAct();
        //  Utils.setStatusBar(this,false,false);
    }

    private void initActivity() {
        setThisContentView();
        if (isCanExit()) {
            setExitBroadcastReceiver();
        }
        progressDialog = new CustomProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        rxDialogPayBack = new RxDialogPayBack(this);
        rxDialogPayBack.setCanCancle(false);
        onCreateDataForView();
    }

    public abstract void onCreateDataForView();

    public abstract void setThisContentView();

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //MobclickAgent.onResume(this);
        AppContext.getInstance().setOneActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // MobclickAgent.onPause(this);
        AppContext.getInstance().setOldinstance(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void funFinish(EventBusBean bean) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void funUpdate(UserMessageBean bean) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void funOrder(ReturnOrderBean bean) {

        if (bean.getType() == 1) {
            rxDialogPayBack.setPrice("" + bean.getPrice());
            rxDialogPayBack.setMsg("支付成功");
        } else {
            rxDialogPayBack.setPrice("" + bean.getPrice());
            String msg = "";
            switch (bean.getErrType()) {
                case "fail":
                    msg = "支付失败";
                    break;
                case "cancel":
                    msg = "取消支付";
                    break;
                case "invalid":
                    msg = "支付插件未安装";
                    break;
                case "unknown":
                    msg = "app进程异常被杀死";
                    break;
            }
            rxDialogPayBack.setMsg("" + msg);
        }
        rxDialogPayBack.show();
        handler.sendEmptyMessageDelayed(1, 1500);
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (null != rxDialogPayBack) {
                try {
                    rxDialogPayBack.dismiss();
                } catch (Exception e) {
                }
            }
            return false;
        }
    });

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            progressDialog.dismiss();
            rxDialogPayBack.getDialog().dismiss();
        } catch (Exception e) {
        }
        NetworkDownload.stopRequest(this);
        if (isStartExitReceiver) {
            unregisterReceiver(exitReceiver);
            isStartExitReceiver = false;
        }
        if (mPlayServiceConnection != null) {
            unbindService(mPlayServiceConnection);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);
    }

    /**
     * 设置界面的标题栏
     *
     * @param id   标题栏id
     * @param name 标题栏名
     */
    public void setTitle(int id, String name) {
        if (title == null && id != 0) {
            title = findViewById(id);
            PercentRelativeLayout viewById = (PercentRelativeLayout) title.findViewById(R.id.back);
            viewById.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            titleNameView = (TextView) findViewById(R.id.title_name);
            if (!TextUtils.isEmpty(name)) {
                titleNameView.setText(name);
            }

        }
    }

    private ShareDialog shareDialog;

    public void setShare(int id, final String title, final String des, final String image, final String link) {
        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog = new ShareDialog(SuperLoadActivity.this, title, des, image, link);
                shareDialog.setCancelButtonOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareDialog.dismiss();
                    }
                });
            }
        });

    }

    /**
     * 设置界面的标题栏
     *
     * @param id 标题栏id
     */
    public void setTitle(int id) {
        setTitle(id, "");
    }

    /**
     * 杨操
     * 显示进度条
     *
     * @param text 对话框提示字段
     */
    public void showProgressDialog(String text) {
        Log.i("tag", "对话框显示");
        if (text != null && !TextUtils.isEmpty(text.trim())) {
            progressDialog.setText(text);
        }
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    /**
     * 杨操
     * 关闭进度条
     */
    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * 杨操
     * 设置退出广播
     */
    private void setExitBroadcastReceiver() {
        isStartExitReceiver = true;
        exitReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SuperLoadActivity.this.finish();
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(EXIT);
        registerReceiver(exitReceiver, filter);
    }

    /**
     * 判断是否支持群发广播退出界面的功能
     * 默认支持
     */
    public boolean isCanExit() {
        return true;
    }

    /**
     * 用户没登录是否可以使用此页面（用于浏览器启动界面时做判断）
     * 默认不能在没登录时使用
     */
    protected boolean isShareActivity() {
        return true;
    }

    /**
     * 获取values文件夹的string.xml文件的配置信息
     *
     * @param stringId 文字ID
     * @return 字符串
     */
    protected String getStringForeValues(int stringId) {
        return getResources().getString(stringId);
    }

    /**
     * 设置当前界面时企业界面
     * 默认不能在没登录时使用
     */
    protected boolean isEnterpriseActivity() {
        return false;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        if (AppContext.getPlayService() != null) {
            if (AppContext.getPlayService().isPlaying()) {
                AppContext.getPlayService().stop();
            }
        }
    }


    private ServiceConnection mPlayServiceConnection;
    protected Handler mHandler = new Handler(Looper.getMainLooper());

    public void checkService() {
        if (AppContext.getPlayService() == null) {
            startService();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bindService();
                }
            }, 1000);
        } else {
            connectSuccess();
        }
    }

    private void startService() {
        Intent intent = new Intent(this, PlayService.class);
        startService(intent);
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setClass(this, PlayService.class);
        mPlayServiceConnection = new PlayServiceConnection();
        bindService(intent, mPlayServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private class PlayServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            final PlayService playService = ((PlayService.PlayBinder) service).getService();
            AppContext.setPlayService(playService);
            connectSuccess();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    @Override
    public void connectSuccess() {

    }

    private LinearLayout ll_title;

    public boolean setOnScroll(int id) {
        scrollView = (MyScrollView) findViewById(id);
        if (scrollView == null) {
            return false;
        }
        scrollView.setOnScrollListener(this);
        return true;
    }

    public boolean setLl_title() {
        try {
            ll_title = (LinearLayout) findViewById(R.id.kz_tiltle);
        } catch (Exception e) {
            Log.e("tst", e.toString());
            return false;
        }
        if (ll_title == null) {
            return false;
        }
        return true;
    }

    @Override
    public void onScroll(int scrollY) {
        if (ll_title == null) {
            return;
        }
        if (scrollY > 0) {
            ll_title.setBackgroundResource(R.color.white);
            Utils.setStatusBar(this, true, true);
        } else {
            ll_title.setBackgroundResource(R.color.transparent);
            Utils.setStatusBar(this, false, false);
        }
    }

    private boolean isConnect() {
        if (AppContext.getInstance().getNetState() == 1 || AppContext.getInstance().getNetState() == 3) {
            return true;
        }
        EToastUtil.show(this, "当前无网络链接！");
        return false;
    }

    protected CustomLoadingLayout mLayout; //SmartLoadingLayout对象

    protected void setOnloading(int contentID) {
        mLayout = SmartLoadingLayout.createCustomLayout(this);
        mLayout.setLoadingView(R.id.my_loading_page);
        mLayout.setContentView(contentID);
        mLayout.setEmptyView(R.id.my_empty_page);
        mLayout.setErrorView(R.id.my_error_page);
    }

    public SuperLoadActivity() {
        super();
    }

    @Override
    public void onOperateSuccess(String opType, String type, String state, String id) {
    }

    @Override
    public void onError(String type) {

    }

    protected void setUserCollect(final String optype, final String aid, final String state) {
        Map<String, String> params = new HashMap<>();
        params.put("type", optype);//收藏类型1课程2问题3商品4回答
        params.put("aid", aid);//id
        params.put("state", state);//1收藏 其他取消
        OkhttpUtilManager.postNoCacah(this, "User/setCollect", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                RxLogUtils.e("tst", data);
                onOperateSuccess(optype, "1", state, aid);
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                onError("1");
                RxLogUtils.e("tst", msg);
            }
        });
    }

    protected void setReports(final String optype, final String rid, final String state) {
        Map<String, String> params = new HashMap<>();
        params.put("type", optype);//举报类型1课程2问题3商品4回答
        params.put("rid", rid);//id
        OkhttpUtilManager.postNoCacah(this, "User/setCollect", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                onOperateSuccess(optype, "2", state, rid);
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                onError("2");
            }
        });
    }

    protected void setZans(final String optype, final String zid, final String state) {
        Map<String, String> params = new HashMap<>();
        params.put("type", optype);//举报类型1课程2问题3商品4回答
        params.put("zid", zid);//id
        params.put("state", state);//id
        OkhttpUtilManager.postNoCacah(this, "User/setZans", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                RxLogUtils.e("tst", data);
                onOperateSuccess(optype, "3", state, zid);
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxLogUtils.e("tst", msg);
                onError("3");
            }
        });
    }

    private InfoPopuwindow infoPopuwindow;
    private WindowManager.LayoutParams params;
    private View infoView;

    public void showInfoPopu(View view) {
        infoView = view;
        infoPopuwindow = new InfoPopuwindow(this, new InfoPopuwindow.infoClick() {
            @Override
            public void doclick(int pos) {
                switch (pos) {
                    case 1:
                        break;
                    case 2:
                        getRule(Constants.allyEx);
                        break;
                }
            }
        });
        infoPopuwindow.showAsDropDown(view);
        params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        infoPopuwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }

    private MsgShowPopuwindow msgShowPopuwindows;

    public void showMsgPopuS(View view, String msg) {
        msgShowPopuwindows = new MsgShowPopuwindow(this, msg);
        msgShowPopuwindows.showAtLocation(view, Gravity.CENTER | Gravity.CENTER_VERTICAL, 0, 0);
        params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        msgShowPopuwindows.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }

    private void getRule(String name) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        AgOkhttpUtilManager.postNoCacah(this, "bases/get_config_explain", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (null != jsonObject.getString("value")) {
                        showMsgPopuS(infoView, jsonObject.getString("value"));
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxToast.normal(msg);
            }
        });
    }

    protected SwipeRefreshLayout swipeRefreshLayout;
    protected ScrollView mScrollView;

    public void setLoad(int swId, int svId) {
        try {
            swipeRefreshLayout = (SwipeRefreshLayout) findViewById(swId);
            mScrollView = (ScrollView) findViewById(svId);
        } catch (Exception e) {

        }
        if (null == swipeRefreshLayout || null == mScrollView) {
            return;
        }
        mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                swipeRefreshLayout.setEnabled(mScrollView.getScrollY() == 0);
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                loadData();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    protected void closeRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

    public abstract void loadData();
}