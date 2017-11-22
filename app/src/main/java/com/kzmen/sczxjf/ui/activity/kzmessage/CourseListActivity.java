package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

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

public class CourseListActivity extends ListViewActivity {

    @InjectView(R.id.msg_center_lv)
    PullToRefreshListView mPullRefreshListView;
    @InjectView(R.id.ll_main)
    LinearLayout llMain;
    private int page;
    private List<CourseListBean>listData;
    private CommonAdapter<CourseListBean>adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "课程");
        initData();
    }

    private void initData() {
        listData = new ArrayList<>();
        page = 1;
        adapter =new CommonAdapter<CourseListBean>(CourseListActivity.this,R.layout.kz_course_list_item,listData) {
            @Override
            protected void convert(ViewHolder viewHolder, CourseListBean item, int position) {
                viewHolder.setText(R.id.tv_title,item.getDescribe())
                        .setText(R.id.tv_tid_title,item.getTid_title())
                        .setText(R.id.tv_name,item.getName())
                        ;
                Glide.with(CourseListActivity.this).load(item.getImage()).
                        transform(new GlideRoundTransform(CourseListActivity.this, 0)).
                        into((ImageView) viewHolder.getView(R.id.iv_user_img));
                ((MyListView)viewHolder.getView(R.id.lv_course_list)).setAdapter(new CommonAdapter<CourseBean>(CourseListActivity.this,R.layout.kz_course_listitem,listData.get(position).getCourse_list()) {
                    @Override
                    protected void convert(ViewHolder viewHolder, final CourseBean item, int position) {
                        viewHolder.setText(R.id.tv_title,item.getTitle())
                                .setText(R.id.tv_descprit,item.getDescribe())
                                .setText(R.id.tv_count,item.getViews()+"人正在学习");
                        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               // Intent intent=new Intent(CourseListActivity.this,CourseDetailAcitivity.class);
                                Intent intent=new Intent(CourseListActivity.this,CourseDetailNewAcitivity.class);
                                Bundle bundle=new Bundle();
                                bundle.putCharSequence("cid",item.getCid());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        };
        setmPullRefreshListView(mPullRefreshListView, adapter);
        setADD();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_course_list);
    }

    /**
     * 下拉刷新
     *
     * @param refreshView
     */
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        page=1;
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

    /**
     * 设置PullToRefreshListView自动加载
     */
    public void setADD() {
        final Handler h = new Handler();
        ViewTreeObserver vto = mPullRefreshListView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mPullRefreshListView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullRefreshListView.onRefreshComplete();
                        mPullRefreshListView.setRefreshing(true);
                    }
                }, 1000);

            }
        });
    }
    private void getData(int page){
        if(page==1){
            listData.clear();
        }
        Map<String, String> params = new HashMap<>();
        params.put("limit", "" + 10);
        params.put("page", "" + page);
        params.put("keywords", "" );
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
                Log.e("tst",msg);
                if (mPullRefreshListView == null) {
                    return;
                }
                mPullRefreshListView.setEmptyView(llMain);
                mPullRefreshListView.onRefreshComplete();
                adapter.notifyDataSetChanged();
            }
        });
    }
}
