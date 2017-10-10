package com.kzmen.sczxjf.ui.activity;

import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.AppManager;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.control.CustomProgressDialog;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.kzmen.sczxjf.util.StringUtils;


/**
 * Created by Administrator on 2015/10/28.
 */
public abstract class BeasActivity extends AppCompatActivity {
    private View title;
    private CustomProgressDialog progressDialog;
    public boolean isPdShow;
    private TextView titleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setThisContentView();
        progressDialog = new CustomProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        setTitle();
        initView();
        getData();
    }

    /**
     * 设置这个{@link AppCompatActivity}的ContentView
     * 在{@link AppCompatActivity#onCreate}方法里面调用
     */
    public abstract void setThisContentView() ;

    /**
     * 设置当前界面的标题栏ID
     * @return 返回标题栏的ＩＤ
     */
    public abstract  int setTitleId();

    /**
     * 设置标题栏的名字
     * @return 标题栏的名字
     */
    public abstract CharSequence setTitleName();

    /**
     * 绑定以及初始化控件
     */
    public abstract void initView();
    /**
     * 获取数据
     */
    public void getData() {

    }
    private void setTitle(){
         title = findViewById(setTitleId());
        PercentRelativeLayout viewById = (PercentRelativeLayout) title.findViewById(R.id.back);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        titleName = (TextView) findViewById(R.id.title_name);
        titleName.setText(setTitleName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppContext.getInstance().setOneActivity(this);
       // MobclickAgent.onResume(this);
    }
    //显示进度条
    public void showProgressDialog(String text) {
        Log.i("tag", "对话框显示");
        if(!StringUtils.isEmpty(text)) {
            progressDialog.setText(text);
        }
        if(!progressDialog.isShowing()){
            progressDialog.show();
        }
    }
    //关闭进度条
    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    public void amendTitle(CharSequence title){
        titleName.setText(title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkDownload.stopRequest(this);
    }
}
