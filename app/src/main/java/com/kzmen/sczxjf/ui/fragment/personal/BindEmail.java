package com.kzmen.sczxjf.ui.fragment.personal;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.user.User_For_pe;
import com.kzmen.sczxjf.dialogs.EmailAndAgency;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.kzmen.sczxjf.ui.fragment.basic.SuperFragment;
import com.kzmen.sczxjf.utils.AppUtils;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 创建者：Administrator
 * 时间：2016/7/21
 * 功能描述：管理绑定邮箱的Fragment
 */
public class BindEmail extends SuperFragment {
    public static final String TITLETYPE = "titletype";
    public static final int TYPE_NUM_BIND = 0;
    public static final int TYPE_NUM_MODUIFY = 1;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.or_title)
    TextView orTitle;
    @InjectView(R.id.email_address)
    EditText emailAddress;
    @InjectView(R.id.email_num)
    EditText emailNum;
    @InjectView(R.id.get_num)
    TextView getNum;
    @InjectView(R.id.bind_go)
    TextView bindGo;
    @InjectView(R.id.old_email_title)
    TextView oldEmailTitle;
    @InjectView(R.id.old_email_str)
    TextView oldEmailStr;
    private int anInt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = setContentView(inflater, container, R.layout.fragment_bind_email);
        ButterKnife.inject(this, view);
        setOnclick();
        return view;
    }

    private void setOnclick() {
        anInt = this.getArguments().getInt(TITLETYPE);
        switch (anInt) {
            case 0:
                title.setText("绑定邮箱");
                orTitle.setText("绑定投稿邮箱");
                break;
            case 1:
                title.setText("修改邮箱");
                orTitle.setText("填写新邮箱");
                oldEmailTitle.setVisibility(View.VISIBLE);
                oldEmailStr.setVisibility(View.VISIBLE);
                oldEmailStr.setText(AppContext.getInstance().getPEUser().getEmail());
                break;
        }

        getNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(emailAddress.getText().toString())) {
                    Toast.makeText(getActivity(), "请填写邮箱地址", Toast.LENGTH_SHORT).show();
                } else {
                    showProgressDialog(null);
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("email", emailAddress.getText().toString());
                    map.put("uid", AppContext.getInstance().getPEUser().getUid());
                    RequestParams parm = AppUtils.getParm(map);
                    NetworkDownload.jsonPostForCode1(getActivity(), Constants.URL_POST_EMAILCODE, parm, new NetworkDownload.NetworkDownloadCallBackJson() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                            dismissProgressDialog();
                            Toast.makeText(getActivity(), jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                            AppUtils.startTime(new Handler(), getNum);
                        }

                        @Override
                        public void onFailure() {
                            dismissProgressDialog();
                            Toast.makeText(getActivity(), "获取验证码失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        bindGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(emailAddress.getText().toString())) {
                    Toast.makeText(getActivity(), "请填写邮箱地址", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(emailNum.getText().toString())) {
                    Toast.makeText(getActivity(), "请填写验证码", Toast.LENGTH_SHORT).show();
                } else if (emailAddress.getText().toString().equals(AppContext.getInstance().getPEUser().getEmail())) {
                    Toast.makeText(getActivity(), "修改邮箱不能相同", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("email", emailAddress.getText().toString());
                    map.put("uid", AppContext.getInstance().getPEUser().getUid());
                    map.put("code", emailNum.getText().toString());
                    RequestParams parm = AppUtils.getParm(map);
                    NetworkDownload.jsonPostForCode1(getActivity(), Constants.URL_SAVE_EMAIL, parm, new NetworkDownload.NetworkDownloadCallBackJson() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {

                            AppContext instance = AppContext.getInstance();
                             User_For_pe peUser = instance.getPEUser();
                            peUser.setEmail(emailAddress.getText().toString());
                            instance.setPEUser(peUser);
                            if (BindEmail.this.getArguments().getInt(TITLETYPE) == 0) {
                                EmailAndAgency emailAndAgency = new EmailAndAgency();
                                emailAndAgency.showDialog(getActivity(), "久候主人多时啦！快去卡掌门畅游吧！\n" +
                                        "注册卡掌门 绑定时代！\n");
                            } else {
                                Toast.makeText(getActivity(), jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }

                        }

                        @Override
                        public void onFailure() {
                            Toast.makeText(getActivity(), "获取验证码失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    protected void lazyLoad() {

    }

}
