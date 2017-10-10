package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.os.Bundle;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.utils.TextUtil;
import com.kzmen.sczxjf.view.PasswordToggleEditText;
import com.vondear.rxtools.view.RxToast;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

public class ResetPassActivity extends SuperActivity {

    @InjectView(R.id.et_phone)
    PasswordToggleEditText etPhone;
    @InjectView(R.id.et_pass_confim)
    PasswordToggleEditText etPassConfim;
    @InjectView(R.id.tv_complete)
    TextView tvComplete;

    private String pass;
    private String passConfir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "重置密码");
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_reset_pass);
    }

    @OnClick(R.id.tv_complete)
    public void onViewClicked() {
        pass = etPhone.getText().toString();
        passConfir = etPassConfim.getText().toString();
        if (isAllRight()) {
            Map<String, String> params = new HashMap<>();
            params.put("pwd", pass);
            OkhttpUtilManager.postNoCacah(this, "", params, new OkhttpUtilResult() {
                @Override
                public void onSuccess(int type, String data) {
                    finish();
                }

                @Override
                public void onErrorWrong(int code, String msg) {
                    RxToast.normal(msg);
                }
            });
        }

    }

    public boolean isAllRight() {
        if (TextUtil.isEmpty(pass)) {
            RxToast.normal("密码不能为空");
            return false;
        }
        if (TextUtil.isEmpty(passConfir)) {
            RxToast.normal("请确认密码");
            return false;
        }
        if (!pass.equals(passConfir)) {
            RxToast.normal("两次输入的密码不一致");
            return false;
        }
        return true;
    }
}
