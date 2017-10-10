package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.Intent;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.CourseBean;
import com.kzmen.sczxjf.bean.kzbean.CourseListBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.ListViewActivity;
import com.kzmen.sczxjf.util.glide.GlideRoundTransform;
import com.kzmen.sczxjf.view.MyListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

public class CourseSearchActivity extends ListViewActivity {

    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.tv_search)
    TextView tvSearch;
    @InjectView(R.id.msg_center_lv)
    PullToRefreshListView mPullRefreshListView;
    @InjectView(R.id.ll_main)
    LinearLayout llMain;
    @InjectView(R.id.et_keyword)
    EditText etKeyword;
    private int page;
    private List<CourseListBean> listData;
    private CommonAdapter<CourseListBean> adapter;
    private String keyWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "课程搜索");
        initData();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_course_search);
    }

    private void initData() {
        listData = new ArrayList<>();
        page = 1;
        adapter = new CommonAdapter<CourseListBean>(CourseSearchActivity.this, R.layout.kz_course_list_item, listData) {
            @Override
            protected void convert(ViewHolder viewHolder, CourseListBean item, int position) {
                viewHolder.setText(R.id.tv_title, item.getDescribe())
                        .setText(R.id.tv_tid_title, item.getTid_title())
                        .setText(R.id.tv_name, item.getName())
                ;
                Glide.with(CourseSearchActivity.this).load(item.getImage()).
                        transform(new GlideRoundTransform(CourseSearchActivity.this, 0)).
                        into((ImageView) viewHolder.getView(R.id.iv_user_img));
                ((MyListView) viewHolder.getView(R.id.lv_course_list)).setAdapter(new CommonAdapter<CourseBean>(CourseSearchActivity.this, R.layout.kz_course_listitem, listData.get(position).getCourse_list()) {
                    @Override
                    protected void convert(ViewHolder viewHolder, final CourseBean item, int position) {
                        viewHolder.setText(R.id.tv_title, item.getTitle())
                                .setText(R.id.tv_descprit, item.getDescribe())
                                .setText(R.id.tv_count, item.getViews() + "人正在学习");
                        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //RxToast.normal(item.getCid());
                                Intent intent = new Intent(CourseSearchActivity.this, CourseDetailAcitivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putCharSequence("cid", item.getCid());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        };
        setmPullRefreshListView(mPullRefreshListView, adapter);
        //setADD();
    }

    /**
     * 下拉刷新
     *
     * @param refreshView
     */
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        page = 1;
        getData(page);
    }

    /**
     * 上滑加载更多
     *
     * @param refreshView
     */
    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        page++;
        getData(page);
    }

    private void getData(int page) {
        keyWord = etKeyword.getText().toString();
        if (page == 1) {
            listData.clear();
        }
        Map<String, String> params = new HashMap<>();
        params.put("data[limit]", "" + 10);
        params.put("data[page]", "" + page);
        params.put("data[keywords]", keyWord);
        OkhttpUtilManager.postNoCacah(this, "Course/getCourseList", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                Log.e("tst", data);
                JSONObject object = null;
                try {
                    object = new JSONObject(data);
                    Gson gson = new Gson();
                    List<CourseListBean> datalist = gson.fromJson(object.getString("data"), new TypeToken<List<CourseListBean>>() {
                    }.getType());
                    if (datalist.size() == 0) {
                        mPullRefreshListView.setEmptyView(llMain);
                    } else {
                        listData.addAll(datalist);
                    }
                } catch (JSONException e) {
                    mPullRefreshListView.setEmptyView(llMain);
                    e.printStackTrace();
                }
                mPullRefreshListView.onRefreshComplete();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                Log.e("tst", msg);
                if (mPullRefreshListView == null) {
                    return;
                }
                mPullRefreshListView.setEmptyView(llMain);
                mPullRefreshListView.onRefreshComplete();
                adapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick(R.id.tv_search)
    public void onViewClicked() {
        getData(1);
    }
}
