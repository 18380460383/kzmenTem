package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.content.Intent;
import android.os.Bundle;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;

import butterknife.OnClick;

public class KnowageAskSheqActivity extends SuperActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "知识问答");
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_knowage_ask_sheq);
    }

    @OnClick(R.id.ll_ask)
    public void onViewClicked() {
        Intent intent = null;
        intent = new Intent(KnowageAskSheqActivity.this, KnowageAskPreActivity.class);
        startActivity(intent);

    }
}
