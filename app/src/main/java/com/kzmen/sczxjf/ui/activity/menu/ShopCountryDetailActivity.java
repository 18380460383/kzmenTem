package com.kzmen.sczxjf.ui.activity.menu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.view.DJEditText;

import butterknife.InjectView;
import butterknife.OnClick;

public class ShopCountryDetailActivity extends SuperActivity {

    @InjectView(R.id.tv_save)
    TextView tvSave;
    @InjectView(R.id.et_input)
    DJEditText etInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "详细街道");
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_shop_country_detail);
    }

    @OnClick(R.id.tv_save)
    public void onClick() {
        Intent mIntent = new Intent();
        mIntent.putExtra("data",etInput.getText().toString());
        // 设置结果，并进行传送
        this.setResult(1004, mIntent);
        this.finish();
    }
}
