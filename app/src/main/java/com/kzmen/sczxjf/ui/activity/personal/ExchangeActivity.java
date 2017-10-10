package com.kzmen.sczxjf.ui.activity.personal;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.adapter.ExchangeAdapter;
import com.kzmen.sczxjf.bean.BaseBean;
import com.kzmen.sczxjf.bean.ExchangeBean;
import com.kzmen.sczxjf.bean.returned.OrderForm;
import com.kzmen.sczxjf.ui.activity.basic.ListViewActivity;
import com.kzmen.sczxjf.utils.AppUtils;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.kzmen.sczxjf.util.EshareLoger;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;

/**
 * 说明：
 * note：
 * Created by FuPe
 * on 2016/1/26 at 9:25
 * Amend 杨操 2016/4/25 统一父级
 */
public class ExchangeActivity extends ListViewActivity implements ExchangeAdapter.ConfirmReceiptBack{

    @InjectView(R.id.lv_list)
    public PullToRefreshListView lv_data;
    private final int REQUEST_CODE_PAY = 1;
    public static final String ORDER = "order";
    /**当前请求的页面*/
    private int currentPage=1;
    private List<ExchangeBean> listdata;
    private ExchangeAdapter mAdapter;
    ImageView bjNullIv;
    TextView biTitle;
    LinearLayout bjLl;
    private View inflate;
    private OrderForm value;
    private ExchangeBean beaning;

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_exchange);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.exchange_activity_title, "兑换记录");
        listdata = new ArrayList<>();
        mAdapter = new ExchangeAdapter(ExchangeActivity.this, listdata);
        inflate = LayoutInflater.from(this).inflate(R.layout.listview_null_bj, null);
        inflate.setVisibility(View.GONE);
        bjLl= (LinearLayout) inflate.findViewById(R.id.bj_ll);
        bjNullIv= (ImageView) inflate.findViewById(R.id.bj_null_iv);
        biTitle= (TextView) inflate.findViewById(R.id.bi_title);
        AppUtils.setNullListView(mAdapter, bjLl, bjNullIv, R.drawable.no_g_start, biTitle, "暂无数据", 0);
        lv_data.setEmptyView(inflate);
        mAdapter.setConfirmReceiptBack(this);
        setmPullRefreshListView(lv_data, mAdapter);
        setADD();
    }

    private void getData() {
        RequestParams params = new RequestParams();
        params.add("uid", AppContext.getInstance().getPEUser().getUid());
        params.add("page", currentPage + "");
        params.add("limit", "10");
        NetworkDownload.jsonGetForCode1(this, Constants.URL_GET_EXCHANGE, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                inflate.setVisibility(View.VISIBLE);
                EshareLoger.logI("exchange:\n" + jsonObject.toString());
                BaseBean bean = BaseBean.parseEntity(jsonObject);
                List<ExchangeBean> data = new ArrayList<>();
                if ("1".equals(bean.code)) {
                    if (currentPage == 1) {
                        listdata.clear();
                    }
                    JSONArray array = new JSONArray(bean.info);
                    for (int i = 0; i < array.length(); i++) {
                        EshareLoger.logI(i + ":" + array.get(i).toString());
                        JSONObject json = array.getJSONObject(i);
                        data.add(ExchangeBean.parseEntity(json));
                    }
                    listdata.addAll(data);
                    mAdapter.notifyDataSetChanged();
                    currentPage++;
                }
                lv_data.onRefreshComplete();
                dismissProgressDialog();
            }

            @Override
            public void onFailure() {
                inflate.setVisibility(View.VISIBLE);
                EshareLoger.logI("onFailure");
                Toast.makeText(ExchangeActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                lv_data.onRefreshComplete();
                dismissProgressDialog();
            }
        });
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        EshareLoger.logI("刷新");
        if(listdata == null) {
            listdata = new ArrayList<>();
        }
        currentPage = 1;
        getData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        EshareLoger.logI("加载更多");
        getData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        {
            EshareLoger.logI("positon = " + position);
            ExchangeBean exchangeBean = listdata.get(position - 1);
            if (exchangeBean.getType() != null && exchangeBean.getType().equals("0")) {
                String exchangeId = exchangeBean.getId();
                Intent intent = new Intent(ExchangeActivity.this, DetailEXActivity.class);
                intent.putExtra(DetailEXActivity.EXTRA_ID, exchangeId);
                startActivity(intent);
            } else {
                Intent intent = new Intent(ExchangeActivity.this, ShopDetailsActivity.class);
                intent.putExtra(ShopDetailsActivity.SHOPID, exchangeBean.getGid());
                startActivity(intent);
            }

        }
    }

    @Override
    public void palyMoney(ExchangeBean bean) {
        Intent intent = new Intent(this, ShopOrderActivity.class);
        if (value == null) {
            value=new OrderForm();
        }
            value.setUid(AppContext.getInstance().getPEUser().getUid());
            value.setMoney(Double.valueOf(bean.getMoney()));
            value.setTitle(bean.getTitle());
            value.setScore(bean.getScore());
            value.setBalance(AppContext.getInstance().getPEUser().getBalance());
            value.setOrder(bean.getOrider());
        System.out.println(bean.getOrider()+"订单号++++++++++");
            intent.putExtra(ORDER, value);
        intent.putExtra(ShopOrderActivity.CLASS_SOURCE, "ExchangeActivity");

        startActivityForResult(intent, REQUEST_CODE_PAY);
        beaning=bean;
    }

    @Override
    public void giveGoods(ExchangeBean bean) {
        setGoodsState(bean,3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if(requestCode==REQUEST_CODE_PAY&&resultCode==RESULT_OK){
           int size = listdata.size();
           for (ExchangeBean exc: listdata) {
               if(exc==beaning){
                   exc.setState(1);
                   exc.setState_str("确认收货");
                   mAdapter.notifyDataSetChanged();
                   break;
               }
           }
       }

    }
    /**
     * 改变订单的状态
     *
     * @param state 需要设置订单的状态
     */
    private void setGoodsState(final ExchangeBean data,final int state) {
        Map<String, String> map = new HashMap<>();
        String uid = AppContext.getInstance().getPEUser().getUid();
        map.put("uid", uid);
        map.put("state", state+"");
        map.put("order", data.getOrider());
        RequestParams params = AppUtils.getParm(map);
        showProgressDialog(null);
        NetworkDownload.jsonPostForCode1(this, Constants.URL_ORDER_STATE, params,
                new NetworkDownload.NetworkDownloadCallBackJson() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                        data.setState(3);
                        data.setState_str("已完成");
                        mAdapter.notifyDataSetChanged();
                        dismissProgressDialog();
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(ExchangeActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                        dismissProgressDialog();
                    }
                });
    }
}
