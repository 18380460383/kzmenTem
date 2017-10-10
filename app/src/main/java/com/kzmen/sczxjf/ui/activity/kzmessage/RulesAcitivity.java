package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.os.Bundle;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;

/**
 * 积分规则  或者  提现规则
 */
public class RulesAcitivity extends SuperActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle,"规则");
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_rules_acitivity);
    }
}
