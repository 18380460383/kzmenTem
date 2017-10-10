package com.kzmen.sczxjf.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.percent.PercentRelativeLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.user.User_For_pe;
import com.kzmen.sczxjf.control.CustomProgressDialog;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.ui.activity.personal.LoginActivity;
import com.kzmen.sczxjf.utils.AppUtils;
import com.kzmen.sczxjf.utils.DESUtils;
import com.kzmen.sczxjf.view.EshareDialogFragment;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fupei
 * on 2015/11/18.
 */
public class CheckPhoneActivity extends SuperActivity {

    /*Log标签*/
    private final String TAG = "checkphone";
    /*请求验证码返回的状态信息*/
    public final String CODE_ZERO = "发送验证码失败";
    public final String CODE_ONE = "发送验证码成功";
    public final String CODE_TWO = "手机号码不合法";
    public final String CODE_THREE = "手机号已被注册";
    /*获取验证码*/
    private TextView btn_getcode;
    /*发送验证信息*/
    private Button btn_check;
    /*手机号*/
    private EditText et_phone;
    /*验证码*/
    private EditText et_code;
    private CustomProgressDialog dialog;


    @Override
    public void onCreateDataForView() {
        setTitle(R.id.activity_check_phone_title,"绑定手机号");
        initView();
        initData();
        setListener();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_check_phone);
    }

    /**
     * 初始化组件
     */
    private void initView() {

        btn_check = (Button) findViewById(R.id.activity_check_phone_check);
        btn_getcode = (TextView) findViewById(R.id.activity_check_phone_getcode);
        et_phone = (EditText) findViewById(R.id.activity_check_phone_num);
        et_code = (EditText) findViewById(R.id.activity_check_phone_code);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitInfo();
            }
        });

        btn_getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPhone();
            }
        });


    }

    /**
     * 初始化数据
     */
    private void initData() {
        dialog = new CustomProgressDialog(CheckPhoneActivity.this);
        dialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 验证手机号,如果手机号合法，则请求获取验证码接口
     */
    public boolean checkPhone() {
        String phone = et_phone.getText().toString();
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            toastInfo("手机号格式不正确");
            return false;
        } else {
            AppUtils.startTime(new Handler(), btn_getcode);
            Map<String, String> map = new HashMap<>();
            map.put("on_phone", phone);
            map.put("type", 3 + "");
            RequestParams par = AppUtils.getParm(map);
            dialog.setText("正在处理，请等待。。。");
            dialog.show();
            NetworkDownload.bytePost(this, Constants.URL_USER_IS_PHONE, par, new NetworkDownload.NetworkDownloadCallBackbyte() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        judgePhone(statusCode, responseBody);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure() {

                }
            });
            return true;
        }
    }

    /**
     * 获取验证码
     */
    public void getCode() {

        String phoneNum = et_phone.getText().toString();
        RequestParams params = new RequestParams();
        params.add("mobile_no",phoneNum);
        params.add("type", "1");
        NetworkDownload.byteGet(this, Constants.URL_POST_NOTE, params, new NetworkDownload.NetworkDownloadCallBackbyte() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
               /* try {
                    judgeCode(statusCode, headers, responseBody);
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {*/
                    stopDialog();
//                }
            }

            @Override
            public void onFailure() {
                stopDialog();
            }
        });

    }

    /**
     * 发送验证码后，验证填写的信息
     *
     * @return
     */
    public boolean checkInfo() {
        String phoneNum = et_phone.getText().toString();
        String info_code = et_code.getText().toString();
        if(TextUtils.isEmpty(phoneNum)){
            toastInfo("请输入手机号");
            return false;
        }
        if (TextUtils.isEmpty(info_code)) {
            return false;
        }
        if(!TextUtils.isEmpty(phoneNum)&&!TextUtils.isEmpty(info_code)){
            return  true;
        }
        return false;

           /* if (!phoneNum.equals(check_phone)) {
                toastInfo("此验证码不适于此手机号，请重新获取");
                return false;
            }
            if (code.equals(info_code)) {
                return true;
            } else {
                toastInfo("输入的验证码不正确");
                return false;
            }*/

    }

    /**
     * 请求接口判断手机号是否符合要求
     */
    public void judgePhone(int statusCode, byte[] responseBody) throws JSONException {
        if (statusCode == 200) {
            String msg = new String(responseBody);
            Log.i(TAG, "获取验证手机号码的信息:" + msg);
            JSONTokener jsonTokener = new JSONTokener(DESUtils.ebotongDecrypto(msg));
            Object o = jsonTokener.nextValue();
            if (o instanceof JSONObject) {
                JSONObject body = (JSONObject) o;
                int result_code = body.optInt("code");
                //1，成功；0，已存在
                switch (result_code) {
                    case 0:
                        Log.i(TAG, "此手机号码已被注册");
                        stopDialog();
                        showDialog();
                        break;
                    case 1:
                        Log.i(TAG, "手机号码验证成功，开始发送获取验证码请求");
                        getCode();
                        break;
                    default:
                        Log.i(TAG, "其他项");
                        stopDialog();
                        break;
                }
                Log.i(TAG, body.optString("msg"));
            } else {
                toastInfo("获取手机信息发生错误");
                stopDialog();
            }
        } else {
            toastInfo("连接服务器失败");
            stopDialog();
        }
    }

    /**
     * 判断发送验证码请求后数据的信息
     */
    public void judgeCode(int statusCode, Header[] headers, byte[] responseBody) throws JSONException {
        if (statusCode == 200) {
            String msg = new String(responseBody);
            JSONTokener jsonTokener = new JSONTokener(DESUtils.ebotongDecrypto(msg));
            Object o = jsonTokener.nextValue();
            if (o instanceof JSONObject) {
                Log.i(TAG, "获取的验证码信息:" + o.toString());
                JSONObject body = (JSONObject) o;
                int result_code = body.optInt("code");
                switch (result_code) {
                    case 0:
                        toastInfo(CODE_ZERO);

                        break;
                    case 1:
                        toastInfo(CODE_ONE);


                        break;
                    case 2:
                        toastInfo(CODE_TWO);

                        break;
                    case 3:
                        showDialog();
                        break;
                    default:
                        break;
                }
            } else {
                toastInfo("获取验证码发生错误");
            }
        } else {
            toastInfo("连接服务器失败");
        }
    }

    /**
     * 提交验证信息到接口
     */
    public void submitInfo() {
        if (checkInfo()) {
            RequestParams params1 = new RequestParams();
            params1.add("mobile_no", et_phone.getText().toString());
            params1.add("msg_code", et_code.getText().toString());
            NetworkDownload.jsonPostForCode1(this, Constants.URL_POST_NOTE_JUDGE_CODE, params1, new NetworkDownload.NetworkDownloadCallBackJson() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                    Map<String, String> map = new HashMap<>();
                    map.put("uid", AppContext.getInstance().getPEUser().getUid());
                    map.put("on_phone", et_phone.getText().toString());
                    RequestParams par = AppUtils.getParm(map);
                    NetworkDownload.bytePost(CheckPhoneActivity.this, Constants.URL_SERVER_BINDING_MOBILE, par, new NetworkDownload.NetworkDownloadCallBackbyte() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            try {
                                judgeBinding(statusCode, responseBody);
                            } catch (JSONException e) {
                                Log.i(TAG, "信息正在处理当中");
                            }
                        }

                        @Override
                        public void onFailure() {
                            toastInfo("服务器反馈信息失败，可在：设置-账号信息界面查看准确信息");
                        }
                    });
                }

                @Override
                public void onFailure() {

                }
            });

        }
    }

    /**
     * 判断返回的绑定信息
     *
     * @param statusCode   返回的请求码
     * @param responseBody 返回的数据
     * @throws JSONException 返回的数据不是Json格式
     */
    private void judgeBinding(int statusCode, byte[] responseBody) throws JSONException {
        if (statusCode == 200) {
            JSONObject json = new JSONObject(DESUtils.ebotongDecrypto(new String(responseBody)));
            int code = json.getInt("code");
            switch (code) {
                case 1:
                    toastInfo("绑定成功");
                     User_For_pe peUser = AppContext.getInstance().getPEUser();
                    peUser.setOn_phone(et_phone.getText().toString());
                    AppContext.getInstance().setPEUser(peUser);
                    finish();
                    break;
                case 0:
                    toastInfo("此手机号已绑定其他账号");
                    break;
                default:
                    toastInfo("绑定失败");
                    break;
            }
        }
    }

    /**
     * Toast消息
     *
     * @param info 消息
     */
    public void toastInfo(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    /**
     * 弹出手机号码已被注册提示框
     */
    public void showDialog() {
        final EshareDialogFragment fra = new EshareDialogFragment();
        fra.setViewUI("此手机号已被绑定，请重新输入手机号", "知道了", new EshareDialogFragment.EshareDialogClickListener() {
            @Override
            public void onClick() {
                fra.dismiss();
            }

            @Override
            public void onCancel() {

            }
        });
        fra.show(getFragmentManager(), "binding");

    }

    private void stopDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
       /* Intent intent = new Intent();
        intent.setAction(EXIT);
        sendBroadcast(intent);
        startActivity(new Intent(this, LoginActivity.class));*/
    }
    /**
     * 设置界面的标题栏
     * @param id 标题栏id
     * @param name 标题栏名
     */
    public void setTitle(int id,String name){
        if(title==null&&id!=0){
            title = findViewById(id);
            PercentRelativeLayout viewById = (PercentRelativeLayout) title.findViewById(R.id.back);
            viewById.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setAction(EXIT);
                    sendBroadcast(intent);
                    startActivity(new Intent(CheckPhoneActivity.this, LoginActivity.class));
                }
            });
            titleNameView = (TextView) findViewById(R.id.title_name);
            if(!TextUtils.isEmpty(name)){
                titleNameView.setText(name);
            }

        }
    }

}
