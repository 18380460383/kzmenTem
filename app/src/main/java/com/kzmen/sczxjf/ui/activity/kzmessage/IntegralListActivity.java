package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.percent.PercentRelativeLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.All_Rank;
import com.kzmen.sczxjf.bean.kzbean.RankListBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.util.glide.GlideCircleTransform;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

import static com.kzmen.sczxjf.R.id.tv_mingc;

/**
 * 积分榜单
 */
public class IntegralListActivity extends SuperActivity {
    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(tv_mingc)
    TextView tvMingc;
    @InjectView(R.id.lv_integral_list)
    ListView lvIntegralList;
    @InjectView(R.id.iv_user_bg)
    ImageView iv_user_bg;
    private CommonAdapter<All_Rank> adapter;
    private List<All_Rank> data_list;
    private int page = 0;

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mLayout.onError();
                    break;
                case 1:
                    mLayout.onDone();
                    break;
                case 2:
                    mLayout.onError();
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
        setTitle(R.id.kz_tiltle, "积分排行");
        setOnloading(R.id.ll_content);
        data_list = new ArrayList<>();
        mLayout.onLoading();
        initView();
        initData();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_integral_list);
    }

    private void initData() {

        OkhttpUtilManager.postNoCacah(this, "Evaluation/scoreRank", null, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                JSONObject object = null;
                try {
                    object = new JSONObject(data);
                    Gson gson = new Gson();
                    RankListBean bean = gson.fromJson(object.getString("data"), RankListBean.class);
                    if (bean != null) {
                        data_list.addAll(bean.getAll_rank());
                        // titleName.setText(bean.getMe_rank().getRank());
                        tvName.setText(AppContext.getInstance().getUserLogin().getUsername());
                        tvMingc.setText(bean.getMe_rank().getRank());
                        Glide.with(IntegralListActivity.this).load(AppContext.getInstance().getUserLogin().getAvatar())
                                .transform(new GlideCircleTransform(IntegralListActivity.this))
                                .placeholder(R.drawable.icon_user_normal)
                                .into(iv_user_bg);
                        Log.e("tst", "" + data_list.size());
                    } else {
                        mHandler.sendEmptyMessage(0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(0);
                }
                mHandler.sendEmptyMessage(1);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                Log.e("tst", msg);
                mHandler.sendEmptyMessage(0);
            }
        });
    }

    private void initView() {
        adapter = new CommonAdapter<All_Rank>(IntegralListActivity.this, R.layout.kz_integral_list_item, data_list) {
            @Override
            protected void convert(ViewHolder viewHolder, All_Rank item, int position) {
                viewHolder.setText(R.id.tv_name, item.getUsername())
                        .setText(R.id.tv_mingc, item.getRank())
                        .setText(R.id.tv_grade, item.getAll_score() + "分")
                        .glideImageCircleUser(R.id.iv_img, item.getAvatar());
                viewHolder.setText(tv_mingc, "" + item.getRank());
                viewHolder.getView(R.id.iv_sign).setVisibility(View.VISIBLE);
                switch (position) {
                    case 0:
                        viewHolder.glideImage(R.id.iv_sign, R.drawable.icon_gold);
                        break;
                    case 1:
                        viewHolder.glideImage(R.id.iv_sign, R.drawable.icon_silver);
                        break;
                    case 2:
                        viewHolder.glideImage(R.id.iv_sign, R.drawable.icon_copper);
                        break;
                    default:
                        viewHolder.getView(R.id.iv_sign).setVisibility(View.INVISIBLE);
                        break;
                }
            }
        };
        lvIntegralList.setAdapter(adapter);
    }
}
