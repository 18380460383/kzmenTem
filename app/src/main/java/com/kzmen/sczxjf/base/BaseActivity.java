package com.kzmen.sczxjf.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.AppManager;
import com.kzmen.sczxjf.control.CustomProgressDialog;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.kzmen.sczxjf.util.StringUtils;

import butterknife.ButterKnife;


/**
 * 基础Activity
 */
public class BaseActivity extends FragmentActivity {

    protected boolean _isVisible;

    public Activity _activity;

    private Toast toast;
    private CustomProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        _activity = this;

        AppManager.getAppManager().addActivity(this);
        progressDialog = new CustomProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public void setContentView(int ID) {
        super.setContentView(ID);
        ButterKnife.inject(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        _isVisible = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        _isVisible = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
        NetworkDownload.stopRequest(this);
    }

    //显示toast
    public void showToast(String text) {
        if (toast == null) {
            toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
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
    @Override
    protected void onResume() {
        super.onResume();
        AppContext.getInstance().setOneActivity(this);
        //MobclickAgent.onResume(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);
    }
}
