package com.kzmen.sczxjf.ui.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.net.EnWebUtil;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.utils.AppUtils;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.InjectView;

/**
 * Created by Administrator on 2015/12/2.
 */
public class AlterPassword extends SuperActivity {
    @InjectView(R.id.alter_phone)
    EditText phone;
    @InjectView(R.id.alter_code)
    EditText code;
    @InjectView(R.id.alter_get_code)
    TextView getCode;
    @InjectView(R.id.alter_new_password)
    EditText newPassword;
    @InjectView(R.id.alter_new_password_ok)
    EditText newPasswordOk;
    @InjectView(R.id.alter_submit)
    Button submit;
    private int data;
    private String regex = "[1]{1}[0-9]{1}[0-9]{9}";
    private int type;
    private String getCodePhone;

    @Override
    public void onCreateDataForView() {
        Intent intent = getIntent();
        if(null!=intent){
            type = intent.getExtras().getInt("type");
        }
        setTitle(R.id.alter_password_title,"修改密码");
        initView();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_alterpassword);

    }


    public void initView() {
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String phoneNum = phone.getText().toString();
                Pattern p = Pattern.compile(regex);
                Matcher matcher = p.matcher(phoneNum);
                if (!matcher.find()) {
                    phone.setError("请输入正确的手机号");
                }
            }
        });

        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhoneCode();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    private void submit() {
        final String phoneNum=phone.getText().toString();
        final String codenum=code.getText().toString();
       final  String password=newPassword.getText().toString();
        final String passwordOk=newPasswordOk.getText().toString();

        if(TextUtils.isEmpty(phoneNum)){
            Toast.makeText(this,"请填写手机号获取验证码",Toast.LENGTH_SHORT).show();
            return;
        } if(TextUtils.isEmpty(codenum)){
            Toast.makeText(this,"请填写手机接收到的验证码",Toast.LENGTH_SHORT).show();
            return;
        } if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"请填写新密码",Toast.LENGTH_SHORT).show();
            return;
        } if(!password.equals(passwordOk)){
            Toast.makeText(this,"请核对好密码后再提交",Toast.LENGTH_SHORT).show();
            return;
        }
        RequestParams params1 = new RequestParams();
        params1.add("mobile_no",phoneNum);
        params1.add("msg_code", codenum);
        showProgressDialog(null);
        NetworkDownload.jsonGetForCode1(this, Constants.URL_POST_NOTE_JUDGE_CODE, params1, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                Map<String, String> map = new HashMap<>();
                map.put("on_phone", phoneNum);
                map.put("on_pwd", password);
                map.put("smscode", code.getText().toString());
                RequestParams params = AppUtils.getParm(map);

                NetworkDownload.jsonPostForCode1(AlterPassword.this, Constants.URL_GET_USER_PWD, params, new NetworkDownload.NetworkDownloadCallBackJson() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                        dismissProgressDialog();
                        Toast.makeText(AlterPassword.this, "修改密码成功", Toast.LENGTH_SHORT).show();
                        AlterPassword.this.finish();
                    }
                    @Override
                    public void onFailure() {
                        dismissProgressDialog();
                    }
                });
            }

            @Override
            public void onFailure() {
                dismissProgressDialog();
            }
        });

        }

    public void getPhoneCode() {
        final String phonestr=phone.getText().toString();
        if(TextUtils.isEmpty(phonestr)){
            Toast.makeText(this,"请填写手机号",Toast.LENGTH_SHORT).show();
        }else if(!AppUtils.judegPhon(phonestr,regex)){
            Toast.makeText(this, "手机号格式错误", Toast.LENGTH_SHORT).show();
        } else {
            getCode.setEnabled(false);
            showProgressDialog(null);
            RequestParams params = new RequestParams();
            params.put("type","app_findpwd_msg");
            params.put("mobile_no",phonestr);
            EnWebUtil.getInstance().post(this, new String[]{"OwnAccount", "getMsgCode"}, params, new EnWebUtil.AesListener2() {
                @Override
                public void onSuccess(String errorCode, String errorMsg, String data) {
                    dismissProgressDialog();
                    Toast.makeText(AlterPassword.this, errorMsg, Toast.LENGTH_SHORT).show();
                    if ("0".equals(errorCode)) {
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
