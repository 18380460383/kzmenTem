package com.kzmen.sczxjf.ui.fragment.personal;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.UIManager;
import com.kzmen.sczxjf.bean.kzbean.UserBean;
import com.kzmen.sczxjf.bean.kzbean.UserMessageBean;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.agency.AllyIndexActivity;
import com.kzmen.sczxjf.ui.activity.agency.ChampionsIndexActivity;
import com.kzmen.sczxjf.ui.activity.agency.PartnerIndexAcitivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.MainTabActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.PersonMessActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.WebAcitivity;
import com.kzmen.sczxjf.ui.activity.menu.FriendOfmineAcitivty;
import com.kzmen.sczxjf.ui.activity.menu.MyAskActivity;
import com.kzmen.sczxjf.ui.activity.menu.MyCollectionAcitivity;
import com.kzmen.sczxjf.ui.activity.menu.MyIntegralActivity;
import com.kzmen.sczxjf.ui.activity.menu.MyPackageAcitivity;
import com.kzmen.sczxjf.ui.activity.menu.SpecialPowerActivity;
import com.kzmen.sczxjf.ui.fragment.basic.SuperFragment;
import com.kzmen.sczxjf.util.TextViewUtil;
import com.kzmen.sczxjf.util.glide.GlideCircleTransform;
import com.kzmen.sczxjf.utils.AppUtils;
import com.kzmen.sczxjf.utils.BitmapUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 创建者：杨操
 * 时间：2016/4/12
 * 功能描述：个人端菜单栏
 */
public class CMenuFragment extends SuperFragment {

    @InjectView(R.id.c_menu_user_head_iv)
    ImageView cMenuUserHeadIv;
    @InjectView(R.id.c_menu_user_name_tv)
    TextView cMenuUserNameTv;
    @InjectView(R.id.c_menu_attestation_mark)
    TextView cMenuAttestationMark;
    @InjectView(R.id.c_menu_user_landing_num_tv)
    TextView cMenuUserLandingNumTv;
    @InjectView(R.id.c_menu_integral)
    TextView cMenuIntegral;
    @InjectView(R.id.c_menu_balance)
    TextView cMenuBalance;
    @InjectView(R.id.c_menu_caifu)
    LinearLayout cMenuCaifu;
    @InjectView(R.id.c_menu_collect_onc)
    LinearLayout cMenuCollectonc;
    @InjectView(R.id.c_menu_friend_onc)
    LinearLayout cMenuFriendonc;
    @InjectView(R.id.c_menu_activity_onc)
    LinearLayout cMenuActivity;
    @InjectView(R.id.c_menu_credits_exchange_onc)
    LinearLayout cMenuCreditsExchangeonc;
    @InjectView(R.id.c_menu_creative_collection_rl)
    LinearLayout cMenuCreativeCollectionrl;
    @InjectView(R.id.ll_setting)
    LinearLayout cMenuSetting;
    @InjectView(R.id.c_menu_feedback_onc)
    LinearLayout cMenuFeedback;
    @InjectView(R.id.ll_package)
    LinearLayout ll_package;
    @InjectView(R.id.ll_jifen)
    LinearLayout ll_jifen;
    @InjectView(R.id.c_menu_attestation_mark_for_e)
    TextView cMenuAttestationMarkForE;
    @InjectView(R.id.c_menu_attestation_mark_for_m)
    TextView cMenuAttestationMarkForM;
    @InjectView(R.id.iv_close)
    ImageView iv_close;
    @InjectView(R.id.tv_jifen)
    TextView tvJifen;
    @InjectView(R.id.tv_package)
    TextView tvPackage;
    @InjectView(R.id.tv_ask_count)
    TextView tv_ask_count;
    @InjectView(R.id.c_menu_collect)
    TextView cMenuCollect;
    @InjectView(R.id.c_menu_friend)
    TextView cMenuFriend;
    @InjectView(R.id.c_menu_creative_collection)
    TextView cMenuCreativeCollection;
    @InjectView(R.id.c_menu_credits_exchange)
    TextView cMenuCreditsExchange;
    @InjectView(R.id.imageView6)
    ImageView imageView6;
    @InjectView(R.id.c_menu_ally)
    TextView cMenuAlly;
    @InjectView(R.id.c_menu_activity_ally)
    LinearLayout cMenuActivityAlly;
    @InjectView(R.id.c_menu_champ)
    TextView cMenuChamp;
    @InjectView(R.id.c_menu_activity_champ)
    LinearLayout cMenuActivityChamp;
    @InjectView(R.id.c_menu_par)
    TextView cMenuPar;
    @InjectView(R.id.c_menu_activity_par)
    LinearLayout cMenuActivityPar;
    private boolean DOINGGETBALANCE;
    private MenuBack menuBack;
    private View view;
    private BroadcastReceiver bannerReceiver;

    public void setMenuBack(MenuBack menuBack) {
        this.menuBack = menuBack;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = setContentView(inflater, container, R.layout.fragment_menu);
        ButterKnife.inject(this, view);
        setRecriver();
        AppContext instance = AppContext.getInstance();
        if (!TextUtils.isEmpty(instance.getUserLogin().getUid())) {
            setUserInfo();
            // getUserInfo();
            setDatauser();
            //R.id.c_menu_activity_ally, R.id.c_menu_activity_champ, R.id.c_menu_activity_par

        }
        return view;
    }


    private void setRecriver() {
        bannerReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //getBanner();
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.FRAGMENT_MONEY);
        getContext().registerReceiver(bannerReceiver, filter);
    }

    @OnClick({R.id.c_menu_user_head_iv, R.id.c_menu_collect_onc,
            /*R.id.c_menu_order_onc,*/ R.id.c_menu_friend_onc, R.id.c_menu_activity_onc, R.id.c_menu_balance,
            R.id.c_menu_credits_exchange_onc, R.id.ll_setting, R.id.c_menu_feedback_onc, R.id.c_menu_creative_collection_rl
            , R.id.iv_close, R.id.ll_package, R.id.ll_jifen, R.id.c_menu_activity_ally, R.id.c_menu_activity_champ, R.id.c_menu_activity_par})
    public void Listener(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.c_menu_activity_ally:
                intent = new Intent(getContext(), AllyIndexActivity.class);
                startActivity(intent);
                break;
            case R.id.c_menu_activity_champ:
                intent = new Intent(getContext(), ChampionsIndexActivity.class);
                startActivity(intent);
                break;
            case R.id.c_menu_activity_par:
                intent = new Intent(getContext(), PartnerIndexAcitivity.class);
                startActivity(intent);
                break;
            case R.id.c_menu_user_head_iv:
                intent = new Intent(getContext(), PersonMessActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_jifen:
                intent = new Intent(getContext(), MyIntegralActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_package:
                intent = new Intent(getContext(), MyPackageAcitivity.class);
                startActivity(intent);
                break;
            case R.id.c_menu_collect_onc:
                //TODO 点击收藏
                Intent intent3 = new Intent(getContext(), MyCollectionAcitivity.class);
                getContext().startActivity(intent3);
                break;
            case R.id.c_menu_friend_onc:
                //TODO 点击好友
                getContext().startActivity(new Intent(getContext(), FriendOfmineAcitivty.class));
                break;
            case R.id.c_menu_activity_onc:
                //TODO 点击活动
                getContext().startActivity(new Intent(getContext(), SpecialPowerActivity.class));
                break;
            case R.id.c_menu_credits_exchange_onc:
                //getContext().startActivity(new Intent(getContext(), ShopOfIntegralActivity.class));
                //RxToast.normal("开发中。。。");
                break;
            case R.id.ll_setting:
                UIManager.showSetActivity((Activity) getContext());
                break;
            case R.id.c_menu_feedback_onc:
                Intent intent1 = new Intent(getContext(), WebAcitivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", "帮助");
                bundle.putString("url", OkhttpUtilManager.URL_VIP_CNENTER);
                intent1.putExtras(bundle);
                getContext().startActivity(intent1);
                break;
            case R.id.c_menu_balance:

                break;
            case R.id.c_menu_creative_collection_rl:
                Intent intentn = new Intent(getContext(), MyAskActivity.class);
                getContext().startActivity(intentn);
                break;
            case R.id.iv_close:
                ((MainTabActivity) getActivity()).closeDraw();
                break;

        }
        if (menuBack != null) {
            menuBack.startActivity();
        }
    }


    public void setUserInfo() {
        cMenuUserHeadIv.setImageBitmap(BitmapUtils.toRoundBitmap(AppUtils.readBitMap(getContext(), R.drawable.icon_user_normal)));
        setDatauser();
    }

    private UserBean peUser;

    public void setDatauser() {
        //getBanner();
        peUser = AppContext.getInstance().getUserLogin();
        if (!TextUtils.isEmpty(peUser.getUsername())) {
            cMenuUserNameTv.setText(peUser.getUsername());
        } else if (!TextUtils.isEmpty(peUser.getPhone())) {
            String userphone = peUser.getPhone();
            String s = userphone.substring(0, 3) + "****" + userphone.substring(7, 11);
            cMenuUserNameTv.setText(s);
        }
        SpannableStringBuilder colorText = TextViewUtil.getColorText(peUser.getHotnum() + "天", "#ff8307");
        SpannableStringBuilder str = new SpannableStringBuilder("连续登陆：");
        cMenuUserLandingNumTv.setText(str.append(colorText));
        Glide.with(getActivity()).load(peUser.getAvatar()).placeholder(R.drawable.icon_user_normal).transform(new GlideCircleTransform(getActivity())).into(cMenuUserHeadIv);
        tvJifen.setText(peUser.getScore());
        tvPackage.setText(Integer.valueOf(peUser.getBalance()) / 100 + "");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public interface MenuBack {
        void startActivity();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(bannerReceiver);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data.getIntExtra("loginstate", 0) == 1) {
            setUserInfo();
            //getBanner();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    public void getUserInfo() {
        OkhttpUtilManager.postNoCacah(getActivity(), "User/get_user_info", null, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                try {
                    JSONObject object = new JSONObject(data);
                    Gson gson = new Gson();
                    UserMessageBean bean = gson.fromJson(object.getString("data"), UserMessageBean.class);
                    AppContext.userMessageBean = bean;
                    AppContext.getInstance().setUserMessageBean(bean);
                    tv_ask_count.setVisibility(View.GONE);
                    if (null != bean.getWenda_num() && Integer.valueOf(bean.getWenda_num()) > 0) {
                        tv_ask_count.setVisibility(View.VISIBLE);
                        tv_ask_count.setText(bean.getWenda_num());
                        setProxy();
                    } else {
                        tv_ask_count.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorWrong(int code, String msg) {
            }
        });
    }

    private void setProxy() {
        cMenuActivityAlly.setVisibility(View.VISIBLE);
        cMenuActivityChamp.setVisibility(View.GONE);
        cMenuActivityPar.setVisibility(View.GONE);
        String leader = AppContext.getInstance().getUserLogin().getLeader();
        String par = AppContext.getInstance().getUserLogin().getPartner();
        if (!TextUtils.isEmpty(leader) && leader.equals("1")) {
            cMenuActivityChamp.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(par) && par.equals("1")) {
            cMenuActivityPar.setVisibility(View.VISIBLE);
        }
    }
}
