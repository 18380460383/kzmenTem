package com.kzmen.sczxjf.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.agency.AllyIndexActivity;
import com.kzmen.sczxjf.ui.activity.agency.ChampionsIndexActivity;
import com.kzmen.sczxjf.ui.activity.agency.PartnerIndexAcitivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class WeixTestActivity extends AppCompatActivity {
    @InjectView(R.id.tv_ally)
    TextView tvAlly;
    @InjectView(R.id.tv_champ)
    TextView tvChamp;
    @InjectView(R.id.tv_part)
    TextView tvPart;
    @InjectView(R.id.activity_weix_test)
    LinearLayout activityWeixTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weix_test);
        ButterKnife.inject(this);

    }

    @OnClick({R.id.tv_ally, R.id.tv_champ, R.id.tv_part})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_ally:
                intent = new Intent(WeixTestActivity.this, AllyIndexActivity.class);
                break;
            case R.id.tv_champ:
                intent = new Intent(WeixTestActivity.this, ChampionsIndexActivity.class);
                break;
            case R.id.tv_part:
                intent = new Intent(WeixTestActivity.this, PartnerIndexAcitivity.class);
                break;
        }
        startActivity(intent);
    }
}
