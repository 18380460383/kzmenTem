package com.kzmen.sczxjf.ui.activity.personal;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.user.User_For_pe;
import com.kzmen.sczxjf.net.EnWebUtil;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.utils.AppUtils;
import com.kzmen.sczxjf.utils.JsonUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.InjectView;

/**
 * Created by Administrator on 2015/12/8.
 */
public class PersonageRegister extends SuperActivity implements View.OnClickListener{
    @InjectView(R.id.phone_num)
    EditText phoneNum;
    @InjectView(R.id.auth_code)
    EditText authCode;
    @InjectView(R.id.get_code)
    TextView getCode;
    @InjectView(R.id.register_password)
    EditText registerPassword;
    @InjectView(R.id.register_submit)
    Button registerSubmit;
    private AsyncHttpClient client;
    private int data;
    private String  regex = "[1]{1}[0-9]{1}[0-9]{9}";

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.e_register_title,"用户注册");
        initView();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_e_register);
    }

    public void initView() {
        client =new AsyncHttpClient();
        getCode.setOnClickListener(this);
        registerSubmit.setOnClickListener(this);

    }
    @Override
     public void onClick(View view) {
        switch (view.getId()){
            case R.id.get_code:
                getPhoneCode();
                break;
            case R.id.register_submit:
                submit();
                break;
        }
    }

    private void submit() {
        final String phone=phoneNum.getText().toString();
        final String code=authCode.getText().toString();
        final String password=registerPassword.getText().toString();
        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this, "请填写手机号获取验证码进行注册", Toast.LENGTH_SHORT).show();
        }else if(!AppUtils.judegPhon(phone,regex)){
            Toast.makeText(this, "手机号格式错误", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(code)){
            Toast.makeText(this,"请填写手机接收到的验证码",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"请填写初始密码",Toast.LENGTH_SHORT).show();
        }else if(password.length()<6){
            Toast.makeText(this,"密码过短",Toast.LENGTH_SHORT).show();
        }else{
            showProgressDialog(null);
            //注册
            RequestParams requestParams = new RequestParams();
            requestParams.put("on_phone", phone);
            requestParams.put("on_pwd", password);
            requestParams.put("source", "android");
            requestParams.put("msg_code", code);
                    EnWebUtil.getInstance().post(PersonageRegister.this, new String[]{"OwnAccount", "registerApp"}, requestParams, new EnWebUtil.AesListener2() {
                        @Override
                        public void onSuccess(String errorCode, String errorMsg, String data) {
                            dismissProgressDialog();
                            Toast.makeText(PersonageRegister.this,errorMsg,Toast.LENGTH_SHORT).show();
                            if("0".equals(errorCode)){
                                try {
                                    User_For_pe bean = JsonUtils.getBean(new JSONObject(data), User_For_pe.class);
                                    AppContext.getInstance().setPEUser(bean);
                                    AppContext.getInstance().setFirst();
                                    AppContext.getInstance().setPersonageOnLine(true);
                                    PersonageRegister.this.finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                        @Override
                        public void onFail(String result) {
                            Toast.makeText(PersonageRegister.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                                dismissProgressDialog();
                        }
                    });
                }
    }

    public void getPhoneCode() {
        final String phone=phoneNum.getText().toString();
        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this,"请填写手机号获取验证码进行注册",Toast.LENGTH_SHORT).show();
        }else if(!AppUtils.judegPhon(phone,regex)){
            Toast.makeText(this, "手机号格式错误", Toast.LENGTH_SHORT).show();
        } else {
            getCode.setEnabled(false);
            showProgressDialog(null);
             RequestParams params = new RequestParams();
            params.put("type","app_register_msg");
            params.put("mobile_no",phone);
            EnWebUtil.getInstance().post(this, new String[]{"OwnAccount", "getMsgCode"}, params, new EnWebUtil.AesListener2() {
                    @Override
                    public void onSuccess(String errorCode, String errorMsg, String data) {
                        Toast.makeText(PersonageRegister.this,errorMsg,Toast.LENGTH_SHORT).show();
                        if("0".equals(errorCode)){
                            AppUtils.startTime(new android.os.Handler(), getCode);
                        }
                        getCode.setEnabled(true);
                    }

                    @Override
                    public void onFail(String result) {
                        getCode.setEnabled(true);
                        dismissProgressDialog();
                    }
                });
        }
    }

    @Override
    protected boolean isShareActivity() {
        return true;
    }
}
