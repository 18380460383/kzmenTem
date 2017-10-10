package com.kzmen.sczxjf.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kzmen.sczxjf.R;

/**
 * Created by Group
 * on 2015/11/20.
 * at 11:07
 */
public class EshareDialogActivity extends Activity{
    private Button btn;
    private TextView tv;
    private String text;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        setContentView(R.layout.dialog_binding_phone);
        btn = (Button) findViewById(R.id.binding_phone_know);
        tv = (TextView) findViewById(R.id.dialog_phone_tv_text);
    }

    private void setListener() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();

            }
        });
    }

    private void initData() {
        text = getIntent().getExtras().getString("text");
        tv.setText(text);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}
