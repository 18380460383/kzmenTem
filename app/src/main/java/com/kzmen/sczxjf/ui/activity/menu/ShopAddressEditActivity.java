package com.kzmen.sczxjf.ui.activity.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.AddressBean;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.util.EToastUtil;
import com.kzmen.sczxjf.utils.TextUtil;
import com.vondear.rxtools.RxLogUtils;
import com.vondear.rxtools.view.RxToast;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 地址编辑
 */

public class ShopAddressEditActivity extends SuperActivity {

    @InjectView(R.id.tv_user_name)
    TextView tvUserName;
    @InjectView(R.id.ll_edit_user)
    LinearLayout llEditUser;
    @InjectView(R.id.tv_user_phone)
    TextView tvUserPhone;
    @InjectView(R.id.ll_edit_phone)
    LinearLayout llEditPhone;
    @InjectView(R.id.tv_user_address)
    TextView tvUserAddress;
    @InjectView(R.id.ll_edit_address)
    LinearLayout llEditAddress;
    @InjectView(R.id.tv_user_street)
    TextView tvUserStreet;
    @InjectView(R.id.tv_save)
    TextView tv_save;
    @InjectView(R.id.ll_edit_street)
    LinearLayout llEditStreet;
    @InjectView(R.id.tv_user_ybian)
    TextView tvUserYbian;
    @InjectView(R.id.ll_edit_ybian)
    LinearLayout llEditYbian;
    public static final int REQ_NAME = 1001;
    public static final int REQ_PHONE = 1002;
    public static final int REQ_ADDRESS = 1003;
    public static final int REQ_STREET = 1004;
    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    private AddressBean bean = new AddressBean();
    private String aid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        //setTitle(R.id.kz_tiltle, "编辑地址");
        titleName.setText("编辑地址");
        setInfo();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_shop_address_edit);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            aid = bundle.getString("aid");
            bean = (AddressBean) bundle.getSerializable("address");
        }
    }

    @OnClick({R.id.ll_edit_user, R.id.ll_edit_phone, R.id.ll_edit_address, R.id.ll_edit_street, R.id.ll_edit_ybian, R.id.back, R.id.tv_save})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ll_edit_user:
                intent = new Intent(ShopAddressEditActivity.this, ShopNameEditActivity.class);
                startActivityForResult(intent, REQ_NAME);
                break;
            case R.id.ll_edit_phone:
                intent = new Intent(ShopAddressEditActivity.this, ShopPhoneEditAcitivity.class);
                startActivityForResult(intent, REQ_PHONE);
                break;
            case R.id.ll_edit_address:
                intent = new Intent(ShopAddressEditActivity.this, ShopAreaActivity.class);
                startActivityForResult(intent, REQ_ADDRESS);
                break;
            case R.id.ll_edit_street:
                intent = new Intent(ShopAddressEditActivity.this, ShopCountryDetailActivity.class);
                startActivityForResult(intent, REQ_STREET);
                break;
            case R.id.ll_edit_ybian:
                break;
            case R.id.tv_save:
                saveAddress();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private void saveAddress() {
        if (TextUtil.isEmpty(tvUserName.getText().toString())) {
            RxToast.normal("收货人不能为空");
            return;
        }
        if (TextUtil.isEmpty(tvUserPhone.getText().toString())) {
            RxToast.normal("手机号码不能为空");
            return;
        }
        if (TextUtil.isEmpty(tvUserAddress.getText().toString())) {
            RxToast.normal("地址不能为空");
            return;
        }
        if (TextUtil.isEmpty(tvUserStreet.getText().toString())) {
            RxToast.normal("街道不能为空");
            return;
        }
        Map<String, String> params = new HashMap<>();
        if (!TextUtil.isEmpty(aid)) {
            params.put("aid", aid);
        }
        showProgressDialog("地址保存中");
        params.put("nickname", tvUserName.getText().toString());
        params.put("tel", tvUserPhone.getText().toString());
        params.put("province", bean.getProvince());
        params.put("city", bean.getCity());
        params.put("area", bean.getArea());
        params.put("address", tvUserStreet.getText().toString());
        params.put("zip", tvUserYbian.getText().toString());
        OkhttpUtilManager.postNoCacah(this, "Goods/UserAddress", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                RxLogUtils.e("tst", data);
                dismissProgressDialog();
                bean.setNickname(tvUserName.getText().toString());
                bean.setTel(tvUserPhone.getText().toString());
                bean.setAddress(tvUserStreet.getText().toString());
                bean.setZip(tvUserYbian.getText().toString());
                AppContext.getInstance().setUserAddress(bean);
                Intent mIntent = new Intent();
                mIntent.putExtra("data", bean);
                // 设置结果，并进行传送
                setResult(1000, mIntent);
                finish();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxLogUtils.e("tst", msg);
                dismissProgressDialog();
            }
        });
    }

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        String msg = data.getStringExtra("data");
        // 根据上面发送过去的请求吗来区别
        switch (requestCode) {
            case REQ_NAME:
                tvUserName.setText(msg);
                EToastUtil.show(ShopAddressEditActivity.this, "niccc" + msg);
                break;
            case REQ_PHONE:
                tvUserPhone.setText(msg);
                EToastUtil.show(ShopAddressEditActivity.this, "phone  " + msg);
                break;
            case REQ_ADDRESS:
                tvUserAddress.setText(msg);
                String yb = data.getStringExtra("yb");
                bean.setProvince(data.getStringExtra("province"));
                bean.setCity(data.getStringExtra("city"));
                bean.setArea(data.getStringExtra("area"));
                tvUserYbian.setText(yb);
                break;
            case REQ_STREET:
                tvUserStreet.setText(msg);
                break;
        }
    }

    private void setInfo() {
        if (null != bean && !TextUtil.isEmpty(bean.getNickname())) {
            tvUserName.setText(bean.getNickname());
            tvUserPhone.setText(bean.getTel());
            tvUserAddress.setText(bean.getProvince() + bean.getCity() + bean.getArea());
            tvUserStreet.setText(bean.getAddress());
            tvUserYbian.setText(bean.getZip());
        }
    }

    @Override
    public void onBackPressed() {

    }
}
