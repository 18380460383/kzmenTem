package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.percent.PercentRelativeLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.KnowageAskItemBean;
import com.kzmen.sczxjf.bean.kzbean.KnowageIndexItem;
import com.kzmen.sczxjf.bean.kzbean.ShareBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.dialog.ShareDialog;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.util.glide.GlideCircleTransform;
import com.kzmen.sczxjf.utils.TextUtil;
import com.kzmen.sczxjf.view.MyListView;
import com.kzmen.sczxjf.view.loading.LoadingView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 知识问答
 */
public class KnowageAskIndexActivity extends SuperActivity {
   /* @InjectView(R.id.iv_share)
    ImageView ivShare;*/
    @InjectView(R.id.sv_main)
    PullToRefreshScrollView mPullRefreshScrollView;
    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.lv_aks)
    MyListView lvAks;
    @InjectView(R.id.lv_ask_item)
    MyListView lv_ask_item;
    @InjectView(R.id.tv_all)
    TextView tvAll;
    @InjectView(R.id.tv_hasanswer)
    TextView tvHasanswer;
    @InjectView(R.id.iv_image)
    ImageView ivImage;
    @InjectView(R.id.tv_empty_message)
    TextView tvEmptyMessage;
    @InjectView(R.id.tv_error_message)
    TextView tvErrorMessage;
    @InjectView(R.id.btn_error)
    Button btnError;
    @InjectView(R.id.tv_loading_message)
    TextView tvLoadingMessage;
    @InjectView(R.id.loadView)
    LoadingView loadView;
    @InjectView(R.id.ll_loading)
    LinearLayout llLoading;
    @InjectView(R.id.ll_content)
    LinearLayout llContent;
    private List<KnowageIndexItem> indexList;
    private ShareBean shareBean;
    private CommonAdapter<KnowageIndexItem> indexAdapter;
    private List<KnowageAskItemBean> asksList;
    private CommonAdapter<KnowageAskItemBean> asksAdapter;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "知识问答");
        indexList = new ArrayList<>();
        asksList = new ArrayList<>();
        setOnloading(R.id.ll_content);
        mLayout.onLoading();
        initView();
        getAskItem();
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
                //执行刷新函数
                page++;
                getAsk();
                Log.e("tst", "" + page);
            }
        });
    }

    private int page = 1;
    private int opType = 0;

    private void getFoucus() {
        if (lv_ask_item == null) {
            return;
        }
        lv_ask_item.setFocusable(true);
        lv_ask_item.setFocusableInTouchMode(true);
        lv_ask_item.requestFocus();
    }

    private void getAsk() {
        Map<String, String> params1 = new HashMap<>();
        params1.put("limit", "20");
        params1.put("page", "" + page);
        params1.put("cid", "1");
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
                } catch (Exception e) {
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

    private void initView() {
        indexAdapter = new CommonAdapter<KnowageIndexItem>(this, R.layout.kz_knowage_index_item, indexList) {
            @Override
            protected void convert(ViewHolder viewHolder, KnowageIndexItem item, final int position) {
                viewHolder.glideImage(R.id.iv_icon, item.getImage())
                        .setText(R.id.tv_title, item.getTitle())
                        .setText(R.id.tv_des, item.getDes());
                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(KnowageAskIndexActivity.this, KnowageAskActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("bean", indexList.get(position));
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        };
        lv_ask_item.setAdapter(indexAdapter);
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
                Glide.with(KnowageAskIndexActivity.this).load(item.getAvatar()).placeholder(R.drawable.icon_user_normal)
                        .transform(new GlideCircleTransform(KnowageAskIndexActivity.this))
                        .into((ImageView) viewHolder.getView(R.id.iv_user_img));

                viewHolder.getView(R.id.iv_haveimage).setVisibility(View.GONE);
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
                        Intent intent = new Intent(KnowageAskIndexActivity.this, KnowageAskDetailActivity.class);
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

    private void getAskItem() {
        Map<String, String> params = new HashMap<>();
        params.put("limit", "2");
        OkhttpUtilManager.postNoCacah(this, "Interlocution/getInterlocutionType", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                Log.e("tst", data);
                JSONObject object = null;
                try {
                    object = new JSONObject(data);
                    Gson gson = new Gson();
                    List<KnowageIndexItem> datalist = gson.fromJson(object.getString("data"), new TypeToken<List<KnowageIndexItem>>() {
                    }.getType());
                    shareBean = gson.fromJson(object.getString("share"), ShareBean.class);
                    AppContext.getInstance().knowPrice = object.getString("money");
                    if (datalist.size() == 0) {
                    } else {
                        indexList.addAll(datalist);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getFoucus();
                mHandler.sendEmptyMessage(1);
                indexAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                Log.e("tst", msg);
                mHandler.sendEmptyMessage(0);
            }
        });
        getAsk();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_knowage_ask);
    }

    @OnClick({R.id.tv_all, R.id.tv_hasanswer})
    public void onViewClicked(View view) {
        /*TextPaint paintAll = tvAll.getPaint();
        TextPaint paintHas = tvHasanswer.getPaint();*/
        switch (view.getId()) {
            case R.id.tv_all:
               /* paintHas.setFakeBoldText(false);
                paintAll.setFakeBoldText(true);*/
                tvHasanswer.setTextColor(Color.argb(255, 173, 173, 173));
                tvAll.setTextColor(Color.argb(255, 0, 0, 0));
                opType = 0;
                asksList.clear();
                asksAdapter.notifyDataSetChanged();
                getAsk();
                break;
            case R.id.tv_hasanswer:
               /* paintAll.setFakeBoldText(false);
                paintHas.setFakeBoldText(true);*/
                tvAll.setTextColor(Color.argb(255, 173, 173, 173));
                tvHasanswer.setTextColor(Color.argb(255, 0, 0, 0));
                asksList.clear();
                asksAdapter.notifyDataSetChanged();
                opType = 1;
                getAsk();
                break;
            /*case R.id.iv_share:
                if (null == shareBean) {
                    return;
                }
                shareDialog = new ShareDialog(this, shareBean.getTitle(), shareBean.getDes(), shareBean.getImage(), shareBean.getLinkurl());
                shareDialog.setCancelButtonOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareDialog.dismiss();
                    }
                });
                break;*/
        }
    }

    private ShareDialog shareDialog;
}
