package com.kzmen.sczxjf.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.ui.activity.personal.MainTabActivity;

/**
 * 创建者：Administrator
 * 时间：2016/6/29
 * 功能描述：
 */
public class SchemeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String scheme = getIntent().getDataString();
        System.out.println("scheme"+scheme);
        if("jiebian://Login".equals(scheme)){
            if(AppContext.maintabeactivity==null){
                startActivity(new Intent(this,LogoActivity.class));
                finish();
            }else{
                startActivity(new Intent(this,MainTabActivity.class));
                finish();
            }
        }else{
            finish();
        }

    }
}
