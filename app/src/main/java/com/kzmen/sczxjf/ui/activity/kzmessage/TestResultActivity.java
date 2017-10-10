package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.Intent;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.TestResultBean;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;

import butterknife.InjectView;
import butterknife.OnClick;

public class TestResultActivity extends SuperActivity {

    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.tv_test_grade)
    TextView tvTestGrade;
    @InjectView(R.id.tv_answer_count)
    TextView tvAnswerCount;
    @InjectView(R.id.tv_right_count)
    TextView tvRightCount;
    @InjectView(R.id.tv_wrong_count)
    TextView tvWrongCount;
    @InjectView(R.id.tv_get_jf)
    TextView tvGetJf;
    @InjectView(R.id.ll_test_bd)
    LinearLayout llTestBd;
    @InjectView(R.id.tv_conplete_test)
    TextView tvConpleteTest;
    private TestResultBean testResultBean;
    private String count="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "测评结果");
        initView();
    }

    private void initView() {
        if(testResultBean!=null){
            tvTestGrade.setText(""+testResultBean.getScore());
            tvRightCount.setText(""+testResultBean.getCorrect());
            tvWrongCount.setText(""+testResultBean.getError());
            tvAnswerCount.setText(""+count);
            tvGetJf.setText("+"+testResultBean.getTrue_score());
        }
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_test_result);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            count=bundle.getString("count");
            testResultBean= (TestResultBean) bundle.getSerializable("result");
            Log.e("tst",count+"    "+testResultBean.toString());
        }
    }

    @OnClick({R.id.ll_test_bd, R.id.tv_conplete_test})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_test_bd:
                startActivity(new Intent(TestResultActivity.this,IntegralListActivity.class));
                finish();
                break;
            case R.id.tv_conplete_test:
                finish();
                break;
        }
    }
}
