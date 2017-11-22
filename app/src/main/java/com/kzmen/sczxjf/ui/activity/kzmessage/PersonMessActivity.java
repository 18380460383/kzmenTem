package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.WeixinInfo;
import com.kzmen.sczxjf.bean.kzbean.UserMessageBean;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.util.glide.GlideCircleTransform;
import com.kzmen.sczxjf.utils.TextUtil;
import com.kzmen.sczxjf.view.wheelview.OptionsPickerView;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.dialog.RxDialogWheelYearMonthDay;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.kzmen.sczxjf.R.id.tv_qq;

public class PersonMessActivity extends SuperActivity {


    @InjectView(R.id.tv_user_name)
    TextView tvUserName;
    @InjectView(R.id.tv_day_sign)
    TextView tvDaySign;
    @InjectView(R.id.iv_user_head)
    ImageView ivUserHead;
    @InjectView(R.id.tv_nickname)
    TextView tvNickname;
    @InjectView(R.id.ll_nickname)
    LinearLayout llNickname;
    @InjectView(R.id.tv_realname)
    TextView tvRealname;
    @InjectView(R.id.ll_realname)
    LinearLayout llRealname;
    @InjectView(R.id.tv_sex)
    TextView tvSex;
    @InjectView(R.id.ll_sex)
    LinearLayout llSex;
    @InjectView(R.id.tv_birth)
    TextView tvBirth;
    @InjectView(R.id.ll_birth)
    LinearLayout llBirth;
    @InjectView(R.id.tv_hangye)
    TextView tvHangye;
    @InjectView(R.id.ll_type)
    LinearLayout llType;
    @InjectView(R.id.tv_phone)
    TextView tvPhone;
    @InjectView(R.id.ll_phone)
    LinearLayout llPhone;
    @InjectView(R.id.tv_wx)
    TextView tvWx;
    @InjectView(R.id.ll_wx)
    LinearLayout llWx;
    @InjectView(tv_qq)
    TextView tvQq;
    @InjectView(R.id.ll_qq)
    LinearLayout llQq;
    @InjectView(R.id.tv_mail)
    TextView tvMail;
    @InjectView(R.id.ll_mail)
    LinearLayout llMail;
    @InjectView(R.id.tv_area)
    TextView tvArea;
    @InjectView(R.id.ll_area)
    LinearLayout llArea;
    private OptionsPickerView pvOptions;
    private List<String> sexList;
    private List<String> hangyeList;
    private List<String> ageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void funUpdate(UserMessageBean bean) {
        super.funUpdate(bean);
        setUserInfo(bean);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "个人信息");
        initData();
        initView();
    }

    private void initView() {
        UserMessageBean userMessageBean = AppContext.getInstance().getUserMessageBean();
        setUserInfo(userMessageBean);
    }

    private void setUserInfo(UserMessageBean userMessageBean) {
        if (userMessageBean != null) {
            Glide.with(this).load(userMessageBean.getAvatar()).placeholder(R.drawable.icon_user_normal).transform(new GlideCircleTransform(this)).into(ivUserHead);
            tvUserName.setText(userMessageBean.getUsername());
            tvDaySign.setText("连续登录 " + userMessageBean.getHotnum() + " 天");
            tvNickname.setText(userMessageBean.getUsername());
            tvRealname.setText(userMessageBean.getNickname());
            tvSex.setText(userMessageBean.getSex().equals("1") ? "男" : "女");
            tvBirth.setText(userMessageBean.getBirthday());
            tvHangye.setText(userMessageBean.getRole().equals("0") ? "普通会员" : "认证会员");
            tvPhone.setText(userMessageBean.getPhone());
            String wxId = AppContext.getInstance().getUserLogin().getWeixin();
            tvWx.setText(TextUtil.isEmpty(userMessageBean.getWeixin()) ? "未绑定" : "已绑定");
            tvQq.setVisibility(View.VISIBLE);
            llQq.setVisibility(View.VISIBLE);
            if (!TextUtil.isEmpty(userMessageBean.getLeader()) && !userMessageBean.getLeader().equals("0")) {
                if (!TextUtil.isEmpty(userMessageBean.getLeaderid()) && userMessageBean.getLeaderid().equals("0")) {
                    tvQq.setVisibility(View.GONE);
                    llQq.setVisibility(View.GONE);
                }
            }
            tvQq.setText(TextUtil.isEmpty(userMessageBean.getParentid_invite_code()) ? "" : userMessageBean.getParentid_invite_code());
        }
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_account);
        ButterKnife.inject(this);
    }


    private void initData() {
        sexList = new ArrayList<>();
        hangyeList = new ArrayList<>();
        ageList = new ArrayList<>();
        sexList.add("男");
        sexList.add("女");
        for (int i = 0; i < 10; i++) {
            ageList.add("" + (19 + i));
        }
    }

    private RxDialogWheelYearMonthDay rxDialogWheelYearMonthDay;

    @OnClick({R.id.ll_nickname, R.id.ll_sex, R.id.ll_birth, R.id.ll_realname, R.id.ll_wx, R.id.iv_user_head, R.id.ll_qq})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ll_wx:
                getToken();
                break;
            case R.id.ll_qq:
                intent = new Intent(PersonMessActivity.this, UpdateInvCodeActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_user_head:
                getToken();
                break;
            case R.id.ll_nickname:
                intent = new Intent(PersonMessActivity.this, UpdateNickNameAcitivy.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", "username");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.ll_realname:
                intent = new Intent(PersonMessActivity.this, UpdateNickNameAcitivy.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("type", "nickname");
                intent.putExtras(bundle1);
                startActivity(intent);
                break;
            case R.id.ll_sex:
                pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = sexList.get(options1);
                        tvSex.setText(tx);
                        updata("sex", tx.equals("男") ? "1" : "2");
                    }
                })
                        .setTitleText("性别选择")
                        .setContentTextSize(20)//设置滚轮文字大小
                        .setDividerColor(Color.GREEN)//设置分割线的颜色
                        .setSelectOptions(0, 1)//默认选中项
                        .setBgColor(Color.WHITE)
                        .setTitleBgColor(Color.DKGRAY)
                        .setTitleColor(Color.LTGRAY)
                        .setCancelColor(Color.YELLOW)
                        .setSubmitColor(Color.YELLOW)
                        .setTextColorCenter(Color.BLACK)
                        .setBackgroundId(0x20000000) //设置外部遮罩颜色
                        .build();
                pvOptions.setPicker(sexList);//一级选择器
                pvOptions.show();
                break;
            case R.id.ll_birth:
                rxDialogWheelYearMonthDay = new RxDialogWheelYearMonthDay(PersonMessActivity.this, 1950, 2017);
                rxDialogWheelYearMonthDay.show();
                rxDialogWheelYearMonthDay.getTv_sure().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String date = rxDialogWheelYearMonthDay.getSelectorYear() + "-" + rxDialogWheelYearMonthDay.getSelectorMonth() + "-" + rxDialogWheelYearMonthDay.getSelectorDay();
                        updata("birthday", date);
                        rxDialogWheelYearMonthDay.dismiss();
                        tvBirth.setText("" + date);
                    }
                });
                rxDialogWheelYearMonthDay.getTv_cancle().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rxDialogWheelYearMonthDay.dismiss();
                    }
                });
                break;

        }
    }

    private void updata(String option, String name) {
        Map<String, String> params = new HashMap<>();
        params.put("" + option + "", name);
        OkhttpUtilManager.postNoCacah(this, "User/save_user_info", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                Log.e("tst", data);
                try {
                    JSONObject object = new JSONObject(data);
                    Gson gson = new Gson();
                    UserMessageBean bean = gson.fromJson(object.getString("data"), UserMessageBean.class);
                    AppContext.getInstance().setUserMessageBean(bean);
                    EventBus.getDefault().post(bean);
                    // finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxToast.normal(msg);
            }
        });
    }

    public void getToken() {
        setAccBroadcastReceiver();
        IWXAPI api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.registerApp(Constants.APP_ID);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "none";
        if (!api.sendReq(req)) {
            Toast.makeText(this, "请确定是否安装微信", Toast.LENGTH_LONG).show();
            dismissProgressDialog();
        }
    }

    private BroadcastReceiver receiver;
    private boolean isStartShareReceiver = false;

    private void setAccBroadcastReceiver() {
        isStartShareReceiver = true;
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    loginForWeixin(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.WEIXIN_ACCREDIT);
        registerReceiver(receiver, filter);
    }

    private void loginForWeixin(Intent data) throws JSONException {
        dismissProgressDialog();
        String json = data.getExtras().getString(Constants.WEIXIN_ACCREDIT_KEY);
        final WeixinInfo info = WeixinInfo.parseJson(new JSONObject(json));
        if (info != null) {
            showProgressDialog("绑定中");
            Map<String, String> params = new HashMap<>();
            params.put("weixin", info.unionid + "");
            params.put("openid", info.openid + "");
            params.put("username", info.nickname + "");
            params.put("avatar", info.headimgurl + "");
            params.put("third_country", info.country + "");
            params.put("third_province", info.province + "");
            params.put("third_city", info.city + "");
            params.put("third_sex", info.sex + "");
            OkhttpUtilManager.postNoCacah(this, "public/blind_weixin", params, new OkhttpUtilResult() {
                @Override
                public void onSuccess(int type, String data) {
                    Glide.with(PersonMessActivity.this).load(info.headimgurl).into(ivUserHead);
                    tvWx.setText(info.unionid);
                    dismissProgressDialog();
                }

                @Override
                public void onErrorWrong(int code, String msg) {
                    Toast.makeText(PersonMessActivity.this, "微信绑定失败", Toast.LENGTH_SHORT).show();
                    dismissProgressDialog();
                }
            });
        }
    }

}
