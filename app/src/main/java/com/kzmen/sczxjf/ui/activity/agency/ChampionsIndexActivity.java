package com.kzmen.sczxjf.ui.activity.agency;

import android.content.Intent;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.agent.ChampIndexBean;
import com.kzmen.sczxjf.bean.agent.ChampListBean;
import com.kzmen.sczxjf.bean.agent.CodeListBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.AgOkhttpUtilManager;
import com.kzmen.sczxjf.popuwidow.EditPopuwindow;
import com.kzmen.sczxjf.popuwidow.InfoPopuwindow;
import com.kzmen.sczxjf.popuwidow.ListPopuwindow;
import com.kzmen.sczxjf.popuwidow.MsgShowPopuwindow;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.util.StringUtils;
import com.kzmen.sczxjf.view.MyListView;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 盟主首页
 */
public class ChampionsIndexActivity extends SuperActivity {
    @InjectView(R.id.sv_main)
    PullToRefreshScrollView mPullRefreshScrollView;
    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.iv_add)
    ImageView ivAdd;
    @InjectView(R.id.rl_top)
    RelativeLayout rlTop;
    @InjectView(R.id.tv_count)
    TextView tvCount;
    @InjectView(R.id.iv_ann)
    ImageView ivAnn;
    @InjectView(R.id.tv_buy)
    TextView tvBuy;
    @InjectView(R.id.tv_all_earing)
    TextView tvAllEaring;
    @InjectView(R.id.tv_today_earing)
    TextView tvTodayEaring;
    @InjectView(R.id.tv_people_all)
    TextView tvPeopleAll;
    @InjectView(R.id.tv_today_add)
    TextView tvTodayAdd;
    @InjectView(R.id.tv_add_part)
    TextView tvAddPart;
    @InjectView(R.id.iv_percent)
    ImageView ivPercent;
    @InjectView(R.id.ll_add_new_pro)
    LinearLayout llAddNewPro;
    @InjectView(R.id.activity_partner_index_acitivity)
    RelativeLayout activityPartnerIndexAcitivity;
    @InjectView(R.id.lv_leader_list)
    MyListView lvLeaderList;
    @InjectView(R.id.iv_img)
    ImageView ivImg;
    @InjectView(R.id.tv_msg)
    TextView tvMsg;
    @InjectView(R.id.ll_main)
    LinearLayout llMain;
    @InjectView(R.id.ll_all_earing)
    LinearLayout llAllEaring;
    @InjectView(R.id.ll_today_earing)
    LinearLayout llTodayEaring;

    private InfoPopuwindow infoPopuwindow;
    private MsgShowPopuwindow msgShowPopuwindow;
    private ListPopuwindow listPopuwindow;
    private EditPopuwindow editPopuwindow;
    private WindowManager.LayoutParams params;
    private List<ChampListBean> leaderList = new ArrayList<>();
    private CommonAdapter<ChampListBean> commAdapter;
    private ChampIndexBean champIndexBean;
    private int page = 1;
    private View viewShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "盟主");
        showProgressDialog("加载中");
        Map<String, String> params = new HashMap<>();
        params.put("member_id", "" + AppContext.getInstance().getUserMessageBean().getUid());
        AgOkhttpUtilManager.postNoCacah(this, "users/leader_statistics", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                getFoucus();
                try {
                    Gson gson = new Gson();
                    champIndexBean = gson.fromJson(data, ChampIndexBean.class);
                    if (null != champIndexBean) {
                        initView();
                    }
                } catch (Exception e) {

                }
                dismissProgressDialog();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxToast.normal(msg);
                dismissProgressDialog();
            }
        });
        commAdapter = new CommonAdapter<ChampListBean>(this, R.layout.ally_leader_list_item, leaderList) {
            @Override
            protected void convert(ViewHolder viewHolder, final ChampListBean item, int position) {
                viewHolder.setText(R.id.tv_name, item.getMember_name())
                        .setText(R.id.tv_count, item.getLeader_count())
                        .setText(R.id.tv_percent, "" + (Double.valueOf(item.getLeader_share_scale()) * 100) + "%");
                //ll_edit  iv_edit
                viewHolder.getView(R.id.ll_edit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showMsgPopu(view, 0, item.getMember_id(), item.getMember_name(), item.getLeader_share_scale());
                    }
                });

            }
        };
        lvLeaderList.setAdapter(commAdapter);
        //这几个刷新Label的设置
        mPullRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel("lastUpdateLabel");
        mPullRefreshScrollView.getLoadingLayoutProxy().setPullLabel("PULLLABLE");
        mPullRefreshScrollView.getLoadingLayoutProxy().setRefreshingLabel("refreshingLabel");
        mPullRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel("releaseLabel");
        //上拉、下拉设定
        mPullRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        //上拉监听函数
        mPullRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                page++;
                getChampList();
            }
        });
        getChampList();
        getShopCount();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_champions_index);
    }

    private void getFoucus() {
        if (tvCount == null) {
            return;
        }
        tvCount.setFocusable(true);
        tvCount.setFocusableInTouchMode(true);
        tvCount.requestFocus();
    }

    private void getChampList() {
        Map<String, String> params1 = new HashMap<>();
        params1.put("page", "" + page);
        params1.put("limit", "20");
        params1.put("member_id", "" + AppContext.getInstance().getUserMessageBean().getUid());
        AgOkhttpUtilManager.postNoCacah(this, "users/leader_list", params1, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                getFoucus();
                JSONObject object = null;
                try {
                    Gson gson = new Gson();
                    object = new JSONObject(data);
                    List<ChampListBean> datalist = gson.fromJson(object.getString("data"), new TypeToken<List<ChampListBean>>() {
                    }.getType());
                    if (datalist.size() == 0) {
                        lvLeaderList.setEmptyView(llMain);
                    } else {
                        leaderList.addAll(datalist);
                    }
                } catch (Exception e) {
                }
                commAdapter.notifyDataSetChanged();
                dismissProgressDialog();
                mPullRefreshScrollView.onRefreshComplete();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxToast.normal(msg);
                dismissProgressDialog();
                lvLeaderList.setEmptyView(llMain);
                commAdapter.notifyDataSetChanged();
                mPullRefreshScrollView.onRefreshComplete();
            }
        });
    }

    private int discount_code_count = 0;

    private void getShopCount() {
        Map<String, String> params = new HashMap<>();
        params.put("member_id", "" + AppContext.getInstance().getUserMessageBean().getUid());
        AgOkhttpUtilManager.postNoCacah(this, "users/top_leader_discount_code_count", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                getFoucus();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(data);
                    discount_code_count = jsonObject.getInt("discount_code_count");
                    initCount();
                } catch (Exception e) {
                }
            }

            @Override
            public void onErrorWrong(int code, String msg) {

            }
        });
    }

    private void initCount() {
        tvCount.setText("" + discount_code_count);
        if (discount_code_count == 0) {
            tvBuy.setVisibility(View.GONE);
        }
    }

    private void initView() {
        if (null != champIndexBean) {
            tvAllEaring.setText(StringUtils.addComma("" + champIndexBean.getTotal_income()));
            tvTodayEaring.setText(StringUtils.addComma("" + champIndexBean.getToday_income()));
            tvPeopleAll.setText(StringUtils.addComma(champIndexBean.getLeader_count()));
            tvTodayAdd.setText(StringUtils.addComma(champIndexBean.getToday_new_leader()));
        }
    }

    @OnClick({R.id.iv_add, R.id.iv_ann, R.id.tv_buy, R.id.ll_all_earing, R.id.ll_today_earing, R.id.tv_people_all, R.id.tv_today_add, R.id.tv_add_part, R.id.iv_percent, R.id.ll_add_new_pro})
    public void onViewClicked(View view) {
        Intent intent = null;
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_add:
                showInfoPopu(view);
                break;
            case R.id.iv_ann:
                viewShow = view;
                getRule(Constants.discodeEx);
                break;
            case R.id.tv_buy:
                viewShow = view;
                getShopList();
                break;
            case R.id.ll_all_earing:
                intent = new Intent(ChampionsIndexActivity.this, MyMoneyDetailActivity.class);
                bundle.putString("distrubution_type", "20");
                intent.putExtras(bundle);
                break;
            case R.id.ll_today_earing:
                intent = new Intent(ChampionsIndexActivity.this, MyMoneyDetailActivity.class);
                bundle.putString("distrubution_type", "20");
                intent.putExtras(bundle);
                break;
            case R.id.tv_people_all:
                break;
            case R.id.tv_today_add:
                break;
            case R.id.tv_add_part:
                showMsgPopu(view, 1, "", "", "");
                break;
            case R.id.iv_percent:
                viewShow = view;
                getRule(Constants.champEx);
                break;
            case R.id.ll_add_new_pro:
                intent = new Intent(ChampionsIndexActivity.this, CreateNewProActivity.class);
                break;
        }
        if (null != intent) {
            startActivity(intent);
        }
    }

    public void showMsgPopu(View view, String msg) {
        msgShowPopuwindow = new MsgShowPopuwindow(this, msg);
        msgShowPopuwindow.showAtLocation(view, Gravity.CENTER | Gravity.CENTER_VERTICAL, 0, 0);
        params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        msgShowPopuwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }

    public void showMsgPopu(View view, int opType, String id, String name, String percent) {
        editPopuwindow = new EditPopuwindow(this, opType, id, name, percent);
        editPopuwindow.showAtLocation(view, Gravity.CENTER | Gravity.CENTER_VERTICAL, 0, 0);
        params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        editPopuwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }

    private List<CodeListBean> codeListList = new ArrayList<>();

    private void getShopList() {
        AgOkhttpUtilManager.postNoCacah(this, "bases/discount_code_config_list", null, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                JSONObject object = null;
                try {
                    Gson gson = new Gson();
                    object = new JSONObject(data);
                    List<CodeListBean> datalist = gson.fromJson(object.getString("data"), new TypeToken<List<CodeListBean>>() {
                    }.getType());
                    if (datalist.size() == 0) {
                        RxToast.normal("优惠码获取错误");
                    } else {
                        codeListList.addAll(datalist);
                        showListPopu(viewShow);
                    }
                } catch (Exception e) {
                    RxToast.normal("优惠码获取错误");
                }
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxToast.normal(msg);
            }
        });
    }

    private List<String> dataList = new ArrayList<>();

    public void showListPopu(View view) {
        listPopuwindow = new ListPopuwindow(this, codeListList);
        listPopuwindow.showAtLocation(view, Gravity.CENTER | Gravity.CENTER_VERTICAL, 0, 0);
        params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        listPopuwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }

   /* public void showInfoPopu(View view) {
        infoPopuwindow = new InfoPopuwindow(this);
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
    }*/

    private void getRule(String name) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        AgOkhttpUtilManager.postNoCacah(this, "bases/get_config_explain", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (null != jsonObject.getString("value")) {
                        showMsgPopu(viewShow, jsonObject.getString("value"));
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
}
