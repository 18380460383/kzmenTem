package com.kzmen.sczxjf.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.ExchangeBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 说明：
 * note：兑换记录适配器
 * Created by FuPei
 * on 2016/1/26 at 10:15
 */
public class ExchangeAdapter extends BaseAdapter {

    /**
     * 类型为二维码
     */
    private final String TYPE_CODE = "1";
    private final int REQUEST_CODE_PAY = 1;
    public static final String ORDER = "order";
    private List<ExchangeBean> data;
    private Context mContext;
    private LayoutInflater mInflater;
    private ImageLoader mLoader;
    private ConfirmReceiptBack confirmReceiptBack;

    public void setConfirmReceiptBack(ConfirmReceiptBack confirmReceiptBack) {
        this.confirmReceiptBack = confirmReceiptBack;
    }

    public ExchangeAdapter(Context context, List<ExchangeBean> data) {
        mContext = context;
        this.data = data;
        mInflater = LayoutInflater.from(context);
        mLoader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HoldView hold;
        if(convertView != null) {
            hold = (HoldView) convertView.getTag();
        } else {
            convertView = mInflater.inflate(R.layout.item_exchange, null);
            hold = new HoldView();
            hold.tv_title = (TextView) convertView.findViewById(R.id.item_title);
            hold.tv_express = (TextView) convertView.findViewById(R.id.item_express);
            hold.tv_money = (TextView) convertView.findViewById(R.id.item_money);
            hold.tv_scroe = (TextView) convertView.findViewById(R.id.shop_feng);
            hold.tv_state = (TextView) convertView.findViewById(R.id.item_state);
            hold.confirmReceipt = (TextView) convertView.findViewById(R.id.confirm_receipt);
            hold.iv_image = (ImageView) convertView.findViewById(R.id.item_image);
            convertView.setTag(hold);
        }
        ExchangeBean bean = data.get(position);
        mLoader.displayImage(bean.getImage(), hold.iv_image);
        hold.tv_title.setText(bean.getTitle());
        hold.tv_state.setText(bean.getState_str());
        hold.tv_money.setText((getColorString(Float.valueOf(bean.getMoney()) / 100f + "", "#ff8307")));
        hold.tv_scroe.setText((getColorString(bean.getScore(), "#ff8307")));
        if(bean.getType().equals(TYPE_CODE)) {
            hold.tv_express.setText(bean.getRedeemcode());
        } else {
            hold.tv_express.setText(bean.getExpress());
        }
        setConfirmReceipt(bean, hold.confirmReceipt);
        return convertView;
    }

    private void setConfirmReceipt(final ExchangeBean bean,TextView confirmReceipt) {

        switch (bean.getState()){
            case 0:
                confirmReceipt.setText("确认付款");
                confirmReceipt.setBackgroundResource(R.drawable.shape_orange_tv);
                confirmReceipt.setEnabled(true);
                confirmReceipt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(confirmReceiptBack!=null){
                            confirmReceiptBack.palyMoney(bean);
                        }
                    }
                });
                break;
            case 1:
                confirmReceipt.setText("已付款");
                confirmReceipt.setBackgroundResource(R.drawable.shape_grey_tv);
                confirmReceipt.setOnClickListener(null);
                confirmReceipt.setEnabled(false);
                break;
            case 2:
                confirmReceipt.setText("确认收货");
                confirmReceipt.setBackgroundResource(R.drawable.shape_orange_tv);
                confirmReceipt.setEnabled(true);
                confirmReceipt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(confirmReceiptBack!=null){
                            confirmReceiptBack.giveGoods(bean);
                        }
                    }
                });
                break;
            case 3:
                confirmReceipt.setText("已完成");
                confirmReceipt.setBackgroundResource(R.drawable.shape_grey_tv);
                confirmReceipt.setOnClickListener(null);

                confirmReceipt.setEnabled(false);
                break;
            case 4:
                confirmReceipt.setText("已取消");
                confirmReceipt.setBackgroundResource(R.drawable.shape_grey_tv);
                confirmReceipt.setOnClickListener(null);
                confirmReceipt.setEnabled(false);
                break;
            case -1:
                confirmReceipt.setText("已删除");
                confirmReceipt.setBackgroundResource(R.drawable.shape_grey_tv);
                confirmReceipt.setOnClickListener(null);
                confirmReceipt.setEnabled(false);
                break;
            }
    }

    private class HoldView {
        TextView tv_title, tv_scroe, tv_money, tv_state, tv_express,confirmReceipt;
        ImageView iv_image;
    }

    private SpannableStringBuilder getColorString(String text, String color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor(color));
        builder.setSpan(span, 0, text.length(),  Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }
    public interface  ConfirmReceiptBack{
       abstract void  palyMoney(ExchangeBean bean);
        abstract void giveGoods(ExchangeBean bean);
    }
}
