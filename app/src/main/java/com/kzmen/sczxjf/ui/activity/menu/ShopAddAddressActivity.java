package com.kzmen.sczxjf.ui.activity.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.AddressBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.ListViewActivity;
import com.kzmen.sczxjf.view.MyListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class ShopAddAddressActivity extends ListViewActivity {

    @InjectView(R.id.msg_center_lv)
    MyListView mPullRefreshListView;
    @InjectView(R.id.ll_main)
    LinearLayout llMain;
    @InjectView(R.id.ll_add_address)
    LinearLayout llAddAddress;
    private CommonAdapter<AddressBean> adapter;
    private List<AddressBean> data_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "选择地址");
        initData();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_shop_add_address);
    }

    @OnClick(R.id.ll_add_address)
    public void onClick() {
        startActivity(new Intent(ShopAddAddressActivity.this, ShopAddressEditActivity.class));
    }

    private String selctAid = "";

    private void initData() {
        data_list = new ArrayList<>();
        adapter = new CommonAdapter<AddressBean>(this, R.layout.kz_address_list_item, data_list) {
            @Override
            protected void convert(ViewHolder viewHolder, final AddressBean item, int position) {
                //viewHolder.setText(R.id.tv_title, item);
                viewHolder.setText(R.id.tv_user_name, item.getNickname())
                        .setText(R.id.tv_phone, item.getTel())
                        .setText(R.id.tv_address, item.getProvince() + item.getCity() + item.getArea() + item.getAddress());
                ((RadioButton) viewHolder.getView(R.id.rb_check)).setChecked(false);
                if (selctAid.equals(item.getAid())) {
                    ((RadioButton) viewHolder.getView(R.id.rb_check)).setChecked(true);
                }
                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppContext.getInstance().setUserAddress(item);
                        Intent mIntent = new Intent();
                        mIntent.putExtra("data", item);
                        // 设置结果，并进行传送
                        setResult(1000, mIntent);
                        finish();
                    }
                });
                viewHolder.getView(R.id.ll_more).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selctAid = item.getAid();
                        notifyDataSetChanged();
                        Intent intent = new Intent(ShopAddAddressActivity.this, ShopAddressEditActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("aid", item.getAid());
                        bundle.putSerializable("address", item);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        };
        mPullRefreshListView.setAdapter(adapter);
        getList();
    }


    public void getList() {
        showProgressDialog("地址获取中");
        OkhttpUtilManager.postNoCacah(this, "Goods/GetUserAddress", null, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                JSONObject object = null;
                try {
                    object = new JSONObject(data);
                    Gson gson = new Gson();
                    List<AddressBean> datalist = gson.fromJson(object.getString("data"), new TypeToken<List<AddressBean>>() {
                    }.getType());
                    data_list.addAll(datalist);
                } catch (JSONException e) {
                    mPullRefreshListView.setEmptyView(llMain);
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
                dismissProgressDialog();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                adapter.notifyDataSetChanged();
                dismissProgressDialog();
            }
        });
    }
}
