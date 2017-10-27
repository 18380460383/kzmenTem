package com.kzmen.sczxjf.ui.activity.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.IntegralListItemBean;
import com.kzmen.sczxjf.bean.kzbean.UserMessageBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.WebAcitivity;
import com.kzmen.sczxjf.view.MyListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 我的积分
 * 目前布局写好
 */
public class MyIntegralActivity extends SuperActivity {


    @InjectView(R.id.tv_duihuan)
    TextView tvDuihuan;
    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.tv_more)
    TextView tv_more;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.tv_jif)
    TextView tvJif;
    @InjectView(R.id.lv_integral)
    MyListView lvIntegral;
    @InjectView(R.id.iv_tips)
    ImageView iv_tips;

    private CommonAdapter<IntegralListItemBean> adapter;
    private List<IntegralListItemBean> data_list;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "我的积分");
        initView();
        initData();
    }

    private void initView() {
        UserMessageBean userMessageBean = AppContext.getInstance().getUserMessageBean();
        setUserInfo(userMessageBean);
    }

    private void setUserInfo(UserMessageBean userMessageBean) {
        if (userMessageBean != null) {
            tvJif.setText(userMessageBean.getScore());
        }
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_my_integral);
    }

    private void getFoucus() {
        if (tvJif == null) {
            return;
        }
        tvJif.setFocusable(true);
        tvJif.setFocusableInTouchMode(true);
        tvJif.requestFocus();
    }

    private void initData() {
        data_list = new ArrayList<>();
        page = 1;
        adapter = new CommonAdapter<IntegralListItemBean>(this, R.layout.kz_money_list_item, data_list) {
            @Override
            protected void convert(ViewHolder viewHolder, final IntegralListItemBean item, int position) {
                viewHolder.setText(R.id.tv_name, item.getType_str())
                        .setText(R.id.tv_price, item.getScore())
                        .setText(R.id.tv_date, item.getDatetime())
                ;
                viewHolder.getView(R.id.tv_money).setVisibility(View.GONE);
                if (item.getState().equals("1")) {
                    viewHolder.setBackgroundRes(R.id.iv_type, R.drawable.icon_reduce64);
                } else {
                    viewHolder.setBackgroundRes(R.id.iv_type, R.drawable.icon_add);
                }
            }
        };
        lvIntegral.setAdapter(adapter);
        getList();
    }

    public void getList() {
        if (page == 1) {
            data_list.clear();
        }
        showProgressDialog("加载中");
        Map<String, String> params = new HashMap<>();
       // params.put("data[limit]", "" + 50);
        params.put("data[page]", "" + page);
        OkhttpUtilManager.postNoCacah(this, "User/getScoreLogList", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                Log.e("tst", data);
                JSONObject object = null;
                try {
                    object = new JSONObject(data);
                    Gson gson = new Gson();
                    List<IntegralListItemBean> datalist = gson.fromJson(object.getString("data"), new TypeToken<List<IntegralListItemBean>>() {
                    }.getType());
                    if (datalist.size() == 0) {
                        tv_more.setVisibility(View.GONE);
                    } else {
                        data_list.addAll(datalist);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    tv_more.setVisibility(View.GONE);
                }
                dismissProgressDialog();
                getFoucus();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                getFoucus();
                tv_more.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                dismissProgressDialog();
            }
        });
    }


    @OnClick({R.id.tv_duihuan, R.id.tv_more, R.id.iv_tips})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_duihuan:
                //startActivity(new Intent(MyIntegralActivity.this, ShopOfIntegralActivity.class));
                break;
            case R.id.tv_more:
                page++;
                getList();
                break;
            case R.id.iv_tips:
                Intent intent1 = new Intent(MyIntegralActivity.this, WebAcitivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", "积分规则");
                bundle.putString("url", OkhttpUtilManager.URL_INTEGAL);
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;
        }
    }
}
