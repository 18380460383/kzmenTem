package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.UserMessageBean;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.utils.TextUtil;
import com.kzmen.sczxjf.view.DJEditText;
import com.vondear.rxtools.view.RxToast;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

public class UpdateNickNameAcitivy extends SuperActivity {

    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.tv_save)
    TextView tvSave;
    @InjectView(R.id.et_input)
    DJEditText etInput;
    @InjectView(R.id.tv_mess)
    TextView tvMess;
    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCreateDataForView() {
        if (!TextUtil.isEmpty(type)) {
            setTitle(R.id.kz_tiltle, type.equals("username") ? "昵称修改" : "真实姓名修改");
            if (!type.equals("username")) {
                etInput.setHint("请输入真实姓名");
                tvMess.setText("请输入真实姓名");
            }
        }

    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_update_nick_name_acitivy);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getString("type");
        }
    }

    @OnClick(R.id.tv_save)
    public void onViewClicked() {
        updateInfo();
    }

    private void updateInfo() {
        String nicname = etInput.getText().toString();
        if (TextUtil.isEmpty(nicname)) {
            RxToast.normal(type.equals("username") ? "昵称不能为空" : "真实姓名不能为空");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("data[" + type + "]", nicname);
        OkhttpUtilManager.postNoCacah(this, "User/save_user_info", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                Log.e("tst", data);
                try {
                    JSONObject object = new JSONObject(data);
                    Gson gson = new Gson();
                    UserMessageBean bean = gson.fromJson(object.getString("data"), UserMessageBean.class);
                    AppContext.getInstance().setUserMessageBean(bean);
                    EventBus.getDefault().post(bean);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxToast.normal(msg);
            }
        });
    }
}
