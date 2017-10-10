package com.kzmen.sczxjf.ui.activity.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.AddressBean;
import com.kzmen.sczxjf.bean.kzbean.OrderBean;
import com.kzmen.sczxjf.bean.kzbean.ShopDetailBean;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.vondear.rxtools.RxLogUtils;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

import static com.unionpay.sdk.ab.mContext;

public class ShopOrderComfirActivity extends SuperActivity {

    @InjectView(R.id.ll_noaddress)
    LinearLayout llNoaddress;
    @InjectView(R.id.ll_more)
    LinearLayout llmore;
    @InjectView(R.id.tv_user_name)
    TextView tvUserName;
    @InjectView(R.id.tv_phone)
    TextView tvPhone;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.ll_address)
    RelativeLayout llAddress;
    @InjectView(R.id.iv_img)
    ImageView ivImg;
    @InjectView(R.id.tv_shop_name)
    TextView tvShopName;
    @InjectView(R.id.tv_count)
    TextView tvCount;
    @InjectView(R.id.tv_price)
    TextView tvPrice;
    @InjectView(R.id.tv_ydprice)
    TextView tvYdprice;
    @InjectView(R.id.tv_confire)
    TextView tvConfire;
    @InjectView(R.id.iv_history)
    ImageView ivHistory;
    private ShopDetailBean shopDetailBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "订单确认");
        msg = AppContext.getInstance().getUserAddress();
        setAddress();
        initView();
    }

    private void initView() {
        if (null != shopDetailBean) {
            tvPrice.setText(shopDetailBean.getScore() + "积分");
            Glide.with(this).load(shopDetailBean.getImage()).into(ivImg);
            tvYdprice.setText("￥" + shopDetailBean.getPostage());
            tvShopName.setText(shopDetailBean.getTitle());
        }
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_shop_order_comfir);
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            shopDetailBean = (ShopDetailBean) bundle.getSerializable("shop");
        }
    }

    @OnClick({R.id.ll_noaddress, R.id.ll_address, R.id.tv_confire, R.id.iv_history, R.id.ll_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_noaddress:
                startActivityForResult(new Intent(ShopOrderComfirActivity.this, ShopAddressEditActivity.class), 1000);
                break;
            case R.id.ll_address:
                Intent intent = new Intent(ShopOrderComfirActivity.this, ShopAddressEditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("aid", msg.getAid());
                bundle.putSerializable("address", msg);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.tv_confire:
                commitOrder();
                break;
            case R.id.iv_history:
                startActivity(new Intent(ShopOrderComfirActivity.this, ShopHistoryActivity.class));
                break;
            case R.id.ll_more:
                startActivityForResult(new Intent(ShopOrderComfirActivity.this, ShopAddAddressActivity.class), 2000);
                break;
        }
    }

    private void commitOrder() {
        showProgressDialog("订单生成中");
        Map<String, String> params = new HashMap<>();
        params.put("data[gid]", "" + shopDetailBean.getId());
        params.put("data[num]", "1");
        params.put("data[aid]", "" + msg.getAid());
        OkhttpUtilManager.postNoCacah(this, "Goods/UserAddOrder", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                try {
                    Gson gson = new Gson();
                    JSONObject object1 = new JSONObject(data);
                    OrderBean orderBean = null;
                    orderBean = gson.fromJson(object1.getString("data"), OrderBean.class);
                    Intent intent = new Intent(mContext, PayTypeAcitivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("orderBean", orderBean);
                    intent.putExtras(bundle);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissProgressDialog();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxToast.normal(msg);
                dismissProgressDialog();
            }
        });
    }

    private AddressBean msg;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        msg = (AddressBean) data.getSerializableExtra("data");
        // 根据上面发送过去的请求吗来区别
        switch (requestCode) {
            case 1000:
                setAddress();
                break;
            case 2000:
                setAddress();
                break;
        }
    }

    private void setAddress() {
        if (msg != null) {
            llAddress.setVisibility(View.VISIBLE);
            llNoaddress.setVisibility(View.GONE);
            tvUserName.setText(msg.getNickname());
            tvAddress.setText(msg.getProvince() + msg.getCity() + msg.getArea() + msg.getAddress());
            tvPhone.setText(msg.getTel());
        } else {
            getAddressList();
            llAddress.setVisibility(View.GONE);
            llNoaddress.setVisibility(View.VISIBLE);
        }
    }

    private void getAddressList() {
        OkhttpUtilManager.postNoCacah(this, "Goods/GetUserAddress", null, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                RxLogUtils.e("tst", data);
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxLogUtils.e("tst", msg);
            }
        });
    }
}
