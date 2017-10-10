package com.vondear.rxtools.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.vondear.rxtools.R;

/**
 * Created by pjj18 on 2017/9/15.
 */

public class RxDialogPayBack {
    private Context mContext;
    private Dialog mDialog;
    private View mDialogContentView;
    private TextView tv_price;
    private TextView tv_msg;
    private Handler mHandle = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (null != mDialog && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            return false;
        }
    });

    public RxDialogPayBack(Context context) {
        this.mContext = context;
        init();
    }

    private void init() {
        mDialog = new Dialog(mContext, R.style.custom_dialog_notitle);//
        mDialogContentView = LayoutInflater.from(mContext).inflate(R.layout.rx_dialog_payback, null);
        tv_price = (TextView) mDialogContentView.findViewById(R.id.tv_price);
        tv_msg = (TextView) mDialogContentView.findViewById(R.id.tv_msg);
        mDialog.setContentView(mDialogContentView);
    }

    public void setBackground(int color) {
        GradientDrawable gradientDrawable = (GradientDrawable) mDialogContentView.getBackground();
        gradientDrawable.setColor(color);
    }

    public void setPrice(String price) {
        tv_price.setText("￥  " + price);
    }

    public void setMsg(String msg) {
        tv_msg.setText(msg);
    }

    public void show() {
        mDialog.show();
    }
    public void dismiss(){
        if(null!=mDialog){
            mDialog.dismiss();
        }
    }
    public void setCanCancle(boolean iscan) {
        mDialog.setCanceledOnTouchOutside(iscan);
    }

    public Dialog getDialog() {
        return mDialog;
    }

}
