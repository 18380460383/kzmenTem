package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.TestItemBean;
import com.kzmen.sczxjf.bean.kzbean.TestResultBean;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.test.adapter.AnserQuesAdapter;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.utils.TextUtil;
import com.kzmen.sczxjf.view.MyListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDetailActivity extends SuperActivity {

    private PercentRelativeLayout back;
    //总的题目数据
    private int count;
    //当前显示的题目
    private int corrent;
    private TextView tv_title;
    private TextView title_name;
    private LinearLayout ll_next;
    private Button btn_down;
    private MyListView lv_question;
    private AnserQuesAdapter anserQuesAdapter;
    private String testID = "";
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 数据下载完成，转换状态，显示内容视图
            switch (msg.what) {
                case 0:
                    ll_next.setVisibility(View.GONE);
                    mLayout.onError();
                    break;
                case 1:
                    ll_next.setVisibility(View.VISIBLE);
                    mLayout.onDone();
                    initDB();
                    break;
                default:
                    ll_next.setVisibility(View.GONE);
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
        setTitle(R.id.kz_tiltle, "");
        questionBeanList = new ArrayList<>();
        itemBeanList = new ArrayList<>();
        resultData = new ArrayList<>();
        setOnloading(R.id.ll_content);
        mLayout.onLoading();
        initView();
        initData();

    }

    private void initData() {
        if (TextUtil.isEmpty(testID)) {
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("id", testID);
        OkhttpUtilManager.postNoCacah(this, "Evaluation/getEvaluationShow", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                Log.e("tst", data);
                JSONObject object = null;
                try {
                    object = new JSONObject(data);
                    Gson gson = new Gson();
                    List<TestItemBean> datalist = gson.fromJson(object.getString("data"), new TypeToken<List<TestItemBean>>() {
                    }.getType());
                    if (datalist.size() > 0) {
                        title_name.setText("题目[1/" + datalist.size() + "]");
                        questionBeanList.addAll(datalist);
                        mHandler.sendEmptyMessage(1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                Log.e("tst", msg);
                mHandler.sendEmptyMessage(0);
            }
        });
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_test_detail);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            testID = bundle.getString("id");
        }
    }

    /**
     * 初始化View
     */
    private void initView() {
        back = (PercentRelativeLayout) findViewById(R.id.back);
        ll_next = (LinearLayout) findViewById(R.id.ll_next);
        lv_question = (MyListView) findViewById(R.id.lv_question);
        tv_title = (TextView) findViewById(R.id.tv_title);
        title_name = (TextView) findViewById(R.id.title_name);
        btn_down = (Button) findViewById(R.id.btn_down);
        anserQuesAdapter = new AnserQuesAdapter(this, itemBeanList);
        lv_question.setAdapter(anserQuesAdapter);
        lv_question.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                anserQuesAdapter.setClickCount();
                // if (anserQuesAdapter.getClickCount() == 1) {
                anserQuesAdapter.setSelect(i);
                anserQuesAdapter.notifyDataSetChanged();
                answer = itemBeanList.get(i).getOpt_id();

                //}
            }
        });
    }

    private String answer = "";
    private List<TestItemBean> questionBeanList;
    private List<TestItemBean.ResultBean> itemBeanList;

    /**
     * 初始化数据库服务
     */
    private void initDB() {
        itemBeanList.clear();
        if (questionBeanList != null) {
            itemBeanList.addAll(questionBeanList.get(0).getResult());
            anserQuesAdapter.setRightAnswer(questionBeanList.get(0).getCorrect());
            anserQuesAdapter.notifyDataSetChanged();
        }
        count = questionBeanList.size();
        corrent = 0;
        TestItemBean q = questionBeanList.get(0);
        tv_title.setText(q.getTitle());
        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否为最后一题
                if (corrent < count - 1) {
                    resultData.add(new result(questionBeanList.get(corrent).getTid(), answer));
                    answer = "";
                    corrent++;
                    title_name.setText("题目[" + (corrent + 1) + "/" + questionBeanList.size() + "]");
                    TestItemBean q = questionBeanList.get(corrent);
                    tv_title.setText(q.getTitle());
                    itemBeanList.clear();
                    itemBeanList.addAll(q.getResult());
                    anserQuesAdapter.setRightAnswer(q.getCorrect());
                    anserQuesAdapter.reseat();
                    anserQuesAdapter.notifyDataSetChanged();
                } else if (corrent == count - 1) {
                    resultData.add(new result(questionBeanList.get(corrent).getTid(), answer));
                    new AlertDialog.Builder(TestDetailActivity.this).setTitle("提示").setMessage("已经到达最后一道题，是否提交？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    data da = new data(testID, resultData);
                                    Gson gson = new Gson();
                                    String data = gson.toJson(da);
                                    Log.e("tst", data);
                                    commit(data);

                                }
                            }).setNegativeButton("取消", null).show();
                }
            }
        });
    }

    private void commit(String data) {
        Map<String, String> params = new HashMap<>();
        params.put("data", data);
        OkhttpUtilManager.postNoCacah(this, "Evaluation/submitEvaluation", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                Log.e("tst", data);
                try {
                    JSONObject object = new JSONObject(data);
                    Gson gson = new Gson();
                    TestResultBean bean = gson.fromJson(object.getString("data"), TestResultBean.class);
                    Log.e("tst", bean.toString());
                    Bundle bundle = new Bundle();
                    bundle.putString("count", "" + questionBeanList.size());
                    bundle.putSerializable("result", bean);
                    Intent intent = new Intent(TestDetailActivity.this, TestResultActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                Log.e("tst", msg);
            }
        });
    }

    private List<result> resultData;

    private class data {
        private String id;
        private List<result> result;

        public data(String id, List<TestDetailActivity.result> result) {
            this.id = id;
            this.result = result;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<TestDetailActivity.result> getResult() {
            return result;
        }

        public void setResult(List<TestDetailActivity.result> result) {
            this.result = result;
        }

        @Override
        public String toString() {
            return "data{" +
                    "id='" + id + '\'' +
                    ", result=" + result +
                    '}';
        }
    }

    public class result {
        private String tid;
        private String answer;

        public result(String tid, String answer) {
            this.tid = tid;
            this.answer = answer;
        }
    }
}
