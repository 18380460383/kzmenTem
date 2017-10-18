package com.kzmen.sczxjf.popuwidow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.vondear.rxtools.RxDeviceUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by pjj18 on 2017/10/16.
 */

public class MsgShowPopuwindow extends PopupWindow {
    @InjectView(R.id.tv_content)
    TextView tvContent;
    @InjectView(R.id.tv_sure)
    TextView tvSure;
    private View view;

    public MsgShowPopuwindow(Context context, String msg) {
        this.view = LayoutInflater.from(context).inflate(R.layout.popuwindow_msg, null);
        ButterKnife.inject(this, view);
        // 设置外部可点击
        this.setOutsideTouchable(false);
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
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
        tvContent.setText(Html.fromHtml(msg.toString()));
    }

    @OnClick(R.id.tv_sure)
    public void onViewClicked() {
        this.dismiss();
    }
}
