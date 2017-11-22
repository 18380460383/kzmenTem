package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.KnowageAskItemBean;
import com.kzmen.sczxjf.bean.kzbean.KnowageIndexItem;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.utils.TextUtil;
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
 * Created by Administrator on 2017/8/11.
 */

public class KnowageAskActivity extends SuperActivity {
    @InjectView(R.id.sv_main)
    PullToRefreshScrollView mPullRefreshScrollView;
    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    /*@InjectView(R.id.iv_share)
    ImageView ivShare;*/
    @InjectView(R.id.tv_ask)
    TextView tvAsk;
    @InjectView(R.id.lv_aks)
    MyListView lvAks;
    @InjectView(R.id.ll_ask)
    LinearLayout ll_ask;
    @InjectView(R.id.iv_type)
    ImageView iv_type;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_percent)
    TextView tv_percent;
    @InjectView(R.id.tv_jiangs)
    TextView tv_jiangs;
    @InjectView(R.id.tv_des)
    TextView tv_des;
    @InjectView(R.id.tv_xiaojiangq)
    TextView tv_xiaojiangq;
    @InjectView(R.id.tv_all)
    TextView tvAll;
    @InjectView(R.id.tv_hasanswer)
    TextView tvHasanswer;
    private KnowageIndexItem knowageIndexItem;
    private int page = 1;
    private List<KnowageAskItemBean> asksList;
    private CommonAdapter<KnowageAskItemBean> asksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "知识问答");
        //setShare(R.id.iv_share,knowageIndexItem.getShare_title(),knowageIndexItem.getShare_des(),knowageIndexItem.getShare_image(),knowageIndexItem.getShare_linkurl());
        initView();
        getAsk();
    }

    private void initView() {
        if (knowageIndexItem != null) {
            Glide.with(this).load(knowageIndexItem.getImage()).into(iv_type);
            tv_title.setText(knowageIndexItem.getTitle());
            tv_des.setText(knowageIndexItem.getDes());

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
                    //执行刷新函数
                    page++;
                    getAsk();
                    Log.e("tst", "" + page);
                }
            });
            asksAdapter = new CommonAdapter<KnowageAskItemBean>(this, R.layout.kz_knowage_ask_item, asksList) {
                @Override
                protected void convert(ViewHolder viewHolder, final KnowageAskItemBean item, int position) {
                    viewHolder
                            //.glideImage(R.id.iv_user_img, item.getAvatar())
                            .setText(R.id.tv_username, item.getUsername())
                            .setText(R.id.tv_price, "￥ " + item.getMoney())
                            .setText(R.id.tv_content, item.getContent())
                            .setText(R.id.tv_daojishi, item.getDaojishi())
                            .setText(R.id.tv_state, item.getState());
                    viewHolder.getView(R.id.iv_haveimage).setVisibility(View.GONE);
                    Glide.with(KnowageAskActivity.this).load(item.getAvatar()).placeholder(R.drawable.icon_user_normal).into((ImageView) viewHolder.getView(R.id.iv_user_img));

                    if (item.getImages() > 0) {
                        viewHolder.getView(R.id.iv_haveimage).setVisibility(View.VISIBLE);
                    }
                    viewHolder.getView(R.id.ll_have_answer).setVisibility(View.GONE);
                    if (item.getAnswer() != null) {
                        viewHolder.getView(R.id.ll_have_answer).setVisibility(View.VISIBLE);
                        viewHolder.getView(R.id.ll_have_answer).setVisibility(View.VISIBLE);
                        viewHolder.setText(R.id.tv_answer_name, item.getAnswer().getUsername())
                                .setText(R.id.tv_answer_title, item.getAnswer().getTitle())
                                .setText(R.id.tv_answer_datetime, item.getAnswer().getDatetime());
                        if (TextUtil.isEmpty(item.getAnswer().getTitle())) {
                            viewHolder.getView(R.id.tv_seprate).setVisibility(View.INVISIBLE);
                        }
                    }

                    viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(KnowageAskActivity.this, KnowageAskDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("qid", item.getQid());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                }
            };
            lvAks.setAdapter(asksAdapter);
        }
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_knowage_ask1);
        asksList = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            knowageIndexItem = (KnowageIndexItem) bundle.getSerializable("bean");
        }
    }

    private int opType = 0;
    private void getFoucus() {
        if (iv_type== null) {
            return;
        }
        iv_type.setFocusable(true);
        iv_type.setFocusableInTouchMode(true);
        iv_type.requestFocus();
    }
    private void getAsk() {
        Map<String, String> params1 = new HashMap<>();
        params1.put("limit", "20");
        params1.put("page", "" + page);
        params1.put("cid", "" + knowageIndexItem.getId());
        params1.put("type", "" + opType);
        OkhttpUtilManager.postNoCacah(this, "Interlocution/getInterlocutionList", params1, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                Log.e("tst", data);
                JSONObject object = null;
                try {
                    object = new JSONObject(data);
                    Gson gson = new Gson();
                    List<KnowageAskItemBean> datalist = gson.fromJson(object.getString("data"), new TypeToken<List<KnowageAskItemBean>>() {
                    }.getType());
                    if (datalist.size() == 0) {
                    } else {
                        asksList.addAll(datalist);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getFoucus();
                mPullRefreshScrollView.onRefreshComplete();
                asksAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                Log.e("tst", msg);
                mPullRefreshScrollView.onRefreshComplete();
            }
        });
    }

    @OnClick({R.id.ll_ask, R.id.tv_all, R.id.tv_hasanswer})
    public void onViewClicked(View view) {
        Intent intent = null;
        TextPaint paintAll = tvAll.getPaint();
        TextPaint paintHas = tvHasanswer.getPaint();
        switch (view.getId()) {
            case R.id.ll_ask:
                intent = new Intent(KnowageAskActivity.this, KnowageAskPreActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("cid", "" + knowageIndexItem.getId());
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.tv_all:
                tvHasanswer.setTextColor(Color.argb(255,173,173,173));
                tvAll.setTextColor(Color.argb(255,0,0,0));
                asksList.clear();
                asksAdapter.notifyDataSetChanged();
                opType = 0;
                getAsk();
                break;
            case R.id.tv_hasanswer:
                tvAll.setTextColor(Color.argb(255,173,173,173));
                tvHasanswer.setTextColor(Color.argb(255,0,0,0));
                asksList.clear();
                asksAdapter.notifyDataSetChanged();
                opType = 1;
                getAsk();
                break;
        }
    }
}
