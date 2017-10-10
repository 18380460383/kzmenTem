package com.kzmen.sczxjf.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WeixTestActivity extends AppCompatActivity {
    private IWXAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weix_test);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        findViewById(R.id.tv_gettoken).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "none";
                api.sendReq(req);
            }
        });
    }
}
