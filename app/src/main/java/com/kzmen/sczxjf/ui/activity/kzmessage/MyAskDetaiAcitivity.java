package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.KzConstanst;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.MyAskDetailBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.test.bean.Music;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.utils.TextUtil;
import com.kzmen.sczxjf.view.ExPandGridView;
import com.kzmen.sczxjf.view.MyListView;
import com.vondear.rxtools.RxLogUtils;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.dialog.RxDialogSureCancel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;

public class MyAskDetaiAcitivity extends SuperActivity {
    @InjectView(R.id.tv_price)
    TextView tvPrice;
    @InjectView(R.id.tv_sep)
    TextView tvSep;
    @InjectView(R.id.tv_content)
    TextView tvContent;
    @InjectView(R.id.gv_img)
    ExPandGridView gvImg;
    @InjectView(R.id.tv_state)
    TextView tvState;
    @InjectView(R.id.tv_msg)
    TextView tvMsg;
    @InjectView(R.id.tv_state_msg)
    TextView tvStateMsg;
    @InjectView(R.id.ll_only)
    LinearLayout llOnly;
    @InjectView(R.id.lv_answer)
    MyListView lvAnswer;
    @InjectView(R.id.activity_my_ask_detai_acitivity)
    LinearLayout activityMyAskDetaiAcitivity;
    private String uid;
    private String qid;
    private MyAskDetailBean myAskDetailBean;
    private List<MyAskDetailBean.AnswerBean> listData;
    private CommonAdapter<MyAskDetailBean.AnswerBean> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "问题提问");
        listData = new ArrayList<>();
        initData();
    }

    private void initData() {
        if (TextUtil.isEmpty(qid)) {
            return;
        }
        Map<String, String> params = new HashMap<>();
        showProgressDialog("加载中");
        params.put("qid", qid);
        OkhttpUtilManager.postNoCacah(this, "User/getInterlocutionQuestionShow", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                RxLogUtils.e("tst", data);
                dismissProgressDialog();
                try {
                    JSONObject object = new JSONObject(data);
                    Gson gson = new Gson();
                    myAskDetailBean = gson.fromJson(object.getString("data"), MyAskDetailBean.class);
                    listData.clear();
                    if (null != myAskDetailBean) {
                        listData.addAll(myAskDetailBean.getAnswer());
                        initView();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxLogUtils.e("tst", msg);
                dismissProgressDialog();
            }
        });
    }

    private void initView() {
        tvPrice.setText("赏金￥ " + myAskDetailBean.getMoney());
        tvSep.setText(myAskDetailBean.getTitle());
        tvContent.setText(myAskDetailBean.getContent());
        gvImg.setAdapter(new CommonAdapter<String>(this, R.layout.list_item_image, myAskDetailBean.getImages()) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                viewHolder.glideImageBlur(R.id.iv_img, item);
            }
        });
        tvState.setText(myAskDetailBean.getState());
        tvMsg.setText(myAskDetailBean.getState_right());
        if (uid.equals(myAskDetailBean.getUid())) {
            tvStateMsg.setText(TextUtil.isEmpty(myAskDetailBean.getOk_status_str()) ? "" : myAskDetailBean.getOk_status_str());
            llOnly.setVisibility(TextUtil.isEmpty(myAskDetailBean.getOk_status_str()) ? View.GONE : View.VISIBLE);
        }

        adapter = new CommonAdapter<MyAskDetailBean.AnswerBean>(this, R.layout.my_ask_detail_list_item, myAskDetailBean.getAnswer()) {
            @Override
            protected void convert(final ViewHolder viewHolder, final MyAskDetailBean.AnswerBean item, int position) {
                viewHolder.getView(R.id.iv_isright).setVisibility(View.VISIBLE);
                viewHolder.getView(R.id.tv_price).setVisibility(View.VISIBLE);
                viewHolder.getView(R.id.iv_check).setVisibility(View.VISIBLE);
                viewHolder.getView(R.id.tv_state).setVisibility(View.VISIBLE);
                viewHolder.setText(R.id.tv_state, "已采纳答案");
                viewHolder.getView(R.id.rb_ischeck).setVisibility(View.GONE);
                viewHolder.getView(R.id.tv_seprate).setVisibility(TextUtil.isEmpty(item.getTid_title()) ? View.GONE : View.VISIBLE);
                viewHolder.setText(R.id.tv_ask_state, "点击播放");
                if (item.getMedia_status().equals(KzConstanst.IS_FASLE)) {
                    viewHolder.getView(R.id.ll_listen).setBackgroundResource(R.drawable.bg_play_green);
                } else {
                    viewHolder.getView(R.id.ll_listen).setBackgroundResource(R.drawable.bg_play_blue);
                }
                if (!item.getOk().equals("1")) {
                    viewHolder.getView(R.id.iv_isright).setVisibility(View.GONE);
                    viewHolder.getView(R.id.tv_price).setVisibility(View.GONE);
                    viewHolder.getView(R.id.iv_check).setVisibility(View.GONE);
                    viewHolder.getView(R.id.rb_ischeck).setVisibility(View.GONE);
                    viewHolder.setText(R.id.tv_state, "未采纳");
                }
                ((RadioButton) viewHolder.getView(R.id.rb_ischeck)).setChecked(false);

                viewHolder.getView(R.id.ll_txt).setVisibility(View.GONE);
                viewHolder.getView(R.id.ll_media).setVisibility(View.GONE);
                if (TextUtil.isEmpty(item.getMedia())) {
                    viewHolder.getView(R.id.ll_txt).setVisibility(View.VISIBLE);
                } else {
                    viewHolder.getView(R.id.ll_media).setVisibility(View.VISIBLE);
                }
                if (uid.equals(myAskDetailBean.getUid()) && !myAskDetailBean.getOk_in().equals("1")) {
                    viewHolder.getView(R.id.rb_ischeck).setVisibility(View.GONE);
                    // viewHolder.getView(R.id.iv_check).setVisibility(View.GONE);
                    if (!TextUtil.isEmpty(item.getOk_status()) && !TextUtil.isEmpty(item.getOk_status_str())) {
                        if (item.getOk_status().equals("1")) {//回答状态码 1已采纳 2平分 3未采纳（出现单选框）
                            viewHolder.setText(R.id.tv_state, item.getOk_status_str());
                            //viewHolder.getView(R.id.iv_check).setVisibility(View.VISIBLE);
                        } else if (item.getOk_status().equals("2")) {
                            viewHolder.setText(R.id.tv_state, item.getOk_status_str());
                        } else if (item.getOk_status().equals("3")) {
                            viewHolder.setText(R.id.tv_state, "采纳答案");
                            viewHolder.getView(R.id.rb_ischeck).setVisibility(View.VISIBLE);
                            viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    setCollectAnswer(item.getAid());
                                }
                            });
                        }
                    }
                }
                if (Aid.equals(item.getAid())) {
                    viewHolder.getView(R.id.iv_check).setVisibility(View.VISIBLE);
                    viewHolder.getView(R.id.tv_state).setVisibility(View.VISIBLE);
                    ((RadioButton) viewHolder.getView(R.id.rb_ischeck)).setChecked(true);
                }
                viewHolder.setText(R.id.tv_answer_name, item.getUsername())
                        .setText(R.id.tv_content, item.getContent())
                        .setText(R.id.tv_answer_des, item.getTid_title())
                        .setText(R.id.tv_price, item.getMoney())
                        .setText(R.id.tv_time, item.getDatetime())
                        .setText(R.id.tv_like, item.getZans())
                        .setText(R.id.tv_views, item.getViews() + "人听过")
                        // .glideImageCircle(R.id.iv_answer_img, item.getAvatar())
                        .setText(R.id.tv_media_time, item.getMedia_time() + "\"");
                Glide.with(MyAskDetaiAcitivity.this).load(item.getAvatar()).placeholder(R.drawable.icon_user_normal).into((ImageView) viewHolder.getView(R.id.iv_answer_img));

                viewHolder.getView(R.id.iv_collect).setBackgroundResource(R.drawable.btn_collect);
                if (item.getIscollect() == 1) {
                    viewHolder.getView(R.id.iv_collect).setBackgroundResource(R.drawable.btn_collect_current);
                }
                viewHolder.getView(R.id.iv_like).setBackgroundResource(R.drawable.btn_like);
                if (item.getIszan() == 1) {
                    viewHolder.getView(R.id.iv_like).setBackgroundResource(R.drawable.btn_like_current);
                }
                viewHolder.getView(R.id.iv_collect).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int iscollect = item.getIscollect();
                        setUserCollect("4", item.getAid(), iscollect == 1 ? "0" : "1");
                    }
                });
                viewHolder.getView(R.id.iv_like).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int zan = item.getIszan();
                        setZans("4", item.getAid(), zan == 1 ? "0" : "1");
                    }
                });
                viewHolder.getView(R.id.ll_listen).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //iv_anim
                        setMusic(item.getMedia());
                        AppContext.getPlayService().setIv_anim((ImageView) viewHolder.getView(R.id.iv_anim));
                        if (mediaName.equals(item.getAid())) {
                            AppContext.getPlayService().playPause();
                        } else {
                            AppContext.getPlayService().stop();
                            AppContext.getPlayService().playStart();
                        }
                        mediaName = item.getAid();
                    }
                });

            }
        };
        lvAnswer.setAdapter(adapter);
    }

    private String mediaName = "";

    private void setMusic(String url) {
        List<Music> mMusicList = new ArrayList<>();
        Music music = new Music();
        music.setType(Music.Type.ONLINE);
        music.setPath(url);
        mMusicList.add(music);
        AppContext.getPlayService().setMusicList(mMusicList);
    }

    private String Aid = "";

    private void setCollectAnswer(final String aid) {
        final RxDialogSureCancel rxDialogSureCancel = new RxDialogSureCancel(this);
        rxDialogSureCancel.setContent("确定采纳该答案?");
        rxDialogSureCancel.setIsShow();
        rxDialogSureCancel.setCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rxDialogSureCancel.dismiss();
            }
        });
        rxDialogSureCancel.setSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rxDialogSureCancel.dismiss();
                Map<String, String> params = new HashMap<>();
                showProgressDialog("采纳中");
                params.put("aid", aid);
                OkhttpUtilManager.postNoCacah(MyAskDetaiAcitivity.this, "User/setInterlocutionOk", params, new OkhttpUtilResult() {
                    @Override
                    public void onSuccess(int type, String data) {
                        Aid = aid;
                        adapter.notifyDataSetChanged();
                        dismissProgressDialog();
                    }

                    @Override
                    public void onErrorWrong(int code, String msg) {
                        RxToast.normal(msg);
                        dismissProgressDialog();
                    }
                });
            }
        });
        rxDialogSureCancel.show();

    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_my_ask_detai_acitivity);
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            //uid = bundle.getString("uid");
            uid = AppContext.getInstance().getUserMessageBean().getUid();
            qid = bundle.getString("qid");
        }
    }

    @Override
    public void onOperateSuccess(String opType, String type, String state, String id) {
        super.onOperateSuccess(opType, type, state, id);
        switch (type) {
            case "1"://收藏
                for (MyAskDetailBean.AnswerBean bean : myAskDetailBean.getAnswer()) {
                    if (bean.getAid().equals(id)) {
                        bean.setIscollect(1);
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
                break;
            case "2"://举报
                break;
            case "3"://点赞
                for (MyAskDetailBean.AnswerBean bean : myAskDetailBean.getAnswer()) {
                    if (bean.getAid().equals(id)) {
                        bean.setIszan(1);
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
                break;
        }
    }
}
