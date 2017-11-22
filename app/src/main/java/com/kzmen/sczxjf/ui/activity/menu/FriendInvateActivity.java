package com.kzmen.sczxjf.ui.activity.menu;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.FriendInvitedBean;
import com.kzmen.sczxjf.bean.kzbean.UserMessageBean;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.server.DownLoadImageService;
import com.kzmen.sczxjf.server.ImageDownLoadCallBack;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.utils.TextUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vondear.rxtools.RxLogUtils;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.dialog.RxDialogSureCancel;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 邀请好友
 */
public class FriendInvateActivity extends SuperActivity {

    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.tv_yqm)
    TextView tvYqm;
    @InjectView(R.id.iv_qr)
    ImageView ivQr;
    @InjectView(R.id.ll_wx)
    LinearLayout llWx;
    @InjectView(R.id.ll_friend)
    LinearLayout llFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "注册卡掌门");
        initView();
        initData();

        ivQr.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final RxDialogSureCancel rxDialogSureCancel = new RxDialogSureCancel(FriendInvateActivity.this);
                rxDialogSureCancel.setContent("是否保存？");
                rxDialogSureCancel.setCancelListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rxDialogSureCancel.dismiss();
                    }
                });
                rxDialogSureCancel.setSureListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rxDialogSureCancel.dismiss();
                        if (null != friendInvitedBean && !TextUtil.isEmpty(friendInvitedBean.getImage())) {
                            showProgressDialog("保存中");
                            onDownLoad(friendInvitedBean.getImage());
                        }
                    }
                });
                rxDialogSureCancel.show();
                return false;
            }
        });
    }

    /**
     * 启动图片下载线程
     */
    private void onDownLoad(String url) {
        DownLoadImageService service = new DownLoadImageService(getApplicationContext(),
                url,
                new ImageDownLoadCallBack() {

                    @Override
                    public void onDownLoadSuccess(File file) {
                    }

                    @Override
                    public void onDownLoadSuccess(Bitmap bitmap) {
                        // 在这里执行图片保存方法
                        Message message = new Message();
                        message.what = MSG_VISIBLE;
                        handler.sendMessageDelayed(message, delayTime);
                    }

                    @Override
                    public void onDownLoadFailed() {
                        // 图片保存失败
                        Message message = new Message();
                        message.what = MSG_ERROR;
                        handler.sendMessageDelayed(message, delayTime);
                    }
                });
        new Thread(service).start();
    }

    private final static int MSG_VISIBLE = 1;
    private final static int MSG_ERROR = 0;
    private final int delayTime = 500;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_VISIBLE:
                    RxToast.normal("保存成功");
                    break;
                case MSG_ERROR:
                    RxToast.error("保存失败");
                    break;
            }
            dismissProgressDialog();
            return false;
        }
    });
    private FriendInvitedBean friendInvitedBean;

    private void initData() {
        Map<String, String> params = new HashMap<>();
        try {
            File file1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile() + File.separator + "kzmen" + File.separator + "qr.png");
            if (!file1.exists()) {
                params.put("make", "1");
            }
        } catch (Exception e) {
        }
        OkhttpUtilManager.postNoCacah(this, "User/getUserInviteCode", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                RxLogUtils.e("tst", data);
                JSONObject object = null;
                try {
                    object = new JSONObject(data);
                    Gson gson = new Gson();
                    friendInvitedBean = gson.fromJson(object.getString("data"), FriendInvitedBean.class);
                    if (null != friendInvitedBean) {
                        Glide.with(FriendInvateActivity.this).load(friendInvitedBean.getImage()).into(ivQr);
                        tvYqm.setText(friendInvitedBean.getInvite_code());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxLogUtils.e("tst", msg);
            }
        });
    }

    private void initView() {
        UserMessageBean bean = AppContext.getInstance().getUserMessageBean();
        if (bean != null) {
            tvYqm.setText(bean.getInvite_code());
        }
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_friend_invate);
    }

    @OnClick({R.id.ll_wx, R.id.ll_friend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_wx:
                if (null != friendInvitedBean) {
                    shareText(1);
                }
                break;
            case R.id.ll_friend:
                if (null != friendInvitedBean) {
                    shareText(2);
                }
                break;
        }
    }

    public static IWXAPI api;

    // 文本分享
    private void shareText(int type) {
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
        // 向微信终端注册你的id
        api.registerApp(Constants.APP_ID);
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = friendInvitedBean.getShareLink();
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = friendInvitedBean.getShareTitle();
        msg.description = friendInvitedBean.getShareDes();
        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
        req.message = msg;
        // 分享或收藏的目标场景，通过修改scene场景值实现。
        // 发送到聊天界面 —— WXSceneSession
        // 发送到朋友圈 —— WXSceneTimeline
        // 添加到微信收藏 —— WXSceneFavorite
        switch (type) {
            case 1:
                req.scene = SendMessageToWX.Req.WXSceneSession;
                break;
            case 2:
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                break;
        }
        // 调用api接口发送数据到微信
        api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
