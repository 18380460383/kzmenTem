package com.kzmen.sczxjf.popuwidow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.agent.CodeListBean;
import com.kzmen.sczxjf.bean.agent.ProxyOrderBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.control.CustomProgressDialog;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.AgOkhttpUtilManager;
import com.kzmen.sczxjf.utils.TextUtil;
import com.kzmen.sczxjf.view.MyListView;
import com.vondear.rxtools.RxDeviceUtils;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by pjj18 on 2017/10/16.
 */

public class ListPopuwindow extends PopupWindow {
    @InjectView(R.id.lv_msg)
    MyListView lv_msg;
    @InjectView(R.id.tv_sure)
    TextView tvSure;
    private View view;
    private CommonAdapter<CodeListBean> commonAdapter;
    private int posSelect = 0;
    private Context context;
    private String count = "-1";

    public ListPopuwindow(Context context, List<CodeListBean> dataList) {
        this.view = LayoutInflater.from(context).inflate(R.layout.popu_list_lay, null);
        ButterKnife.inject(this, view);
        this.context = context;
        progressDialog = new CustomProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setText("生成订单中");
        // 设置外部可点击
        this.setOutsideTouchable(false);
        // 设置视图
        this.setContentView(this.view);
        int width = RxDeviceUtils.getScreenWidth(context);
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth((int) (width * 0.8));
        // 设置弹出窗体可点击
        this.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.playlist);
        if (null != dataList && dataList.size() > 0) {
            count = dataList.get(0).getBuy_count();
        }
        commonAdapter = new CommonAdapter<CodeListBean>(context, R.layout.popuwindow_list_item, dataList) {
            @Override
            protected void convert(ViewHolder viewHolder, final CodeListBean item, final int position) {
                if (position == posSelect) {
                    viewHolder.getView(R.id.iv_state).setBackgroundResource(R.drawable.icon_checkbox2);
                } else {
                    viewHolder.getView(R.id.iv_state).setBackgroundResource(R.drawable.icon_checkbox1);
                }
                viewHolder.setText(R.id.tv_content, item.getPrice() + "元购买" + item.getBuy_count() + "个优惠码");
                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        posSelect = position;
                        count = item.getBuy_count();
                        notifyDataSetChanged();
                    }
                });
            }
        };
        lv_msg.setAdapter(commonAdapter);
    }

    @OnClick(R.id.tv_sure)
    public void onViewClicked() {
        setOrder();
    }

    private void popDismiss() {
        this.dismiss();
    }

    private CustomProgressDialog progressDialog;
    private ProxyOrderBean proxyOrderBean;

    private void setOrder() {
        if (TextUtil.isEmpty(count) || count.equals("-1")) {
            RxToast.normal("请选择购买数量");
            return;
        }
        showPro();
        Map<String, String> params = new HashMap<>();
        params.put("member_id", "" + AppContext.getInstance().getUserMessageBean().getUid());
        params.put("buy_count", count);
        AgOkhttpUtilManager.postNoCacah(context, "users/leader_buy_discount_code", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                Gson gson = new Gson();
                JSONObject object1 = null;
                try {
                    object1 = new JSONObject(data);
                    proxyOrderBean = gson.fromJson(object1.getString("data"), ProxyOrderBean.class);
                    // doPay(payment);
                    if (null != proxyOrderBean && null != onOrderSuccess) {
                        onOrderSuccess.onSuccess(proxyOrderBean);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                popDismiss();
                dismissProgressDialogL();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxToast.normal(msg);
                dismissProgressDialogL();
            }
        });
    }

    private void showPro() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    private void dismissProgressDialogL() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private onOrderSuccess onOrderSuccess;

    public ListPopuwindow.onOrderSuccess getOnOrderSuccess() {
        return onOrderSuccess;
    }

    public void setOnOrderSuccess(ListPopuwindow.onOrderSuccess onOrderSuccess) {
        this.onOrderSuccess = onOrderSuccess;
    }

    public interface onOrderSuccess {
        void onSuccess(ProxyOrderBean proxyOrderBean);
    }
}
