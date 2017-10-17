package com.kzmen.sczxjf.popuwidow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.kzmen.sczxjf.R;
import com.vondear.rxtools.RxDeviceUtils;
import com.vondear.rxtools.view.RxToast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by pjj18 on 2017/10/16.
 */

public class InfoPopuwindow extends PopupWindow {
    @InjectView(R.id.ll_share)
    LinearLayout llShare;
    @InjectView(R.id.ll_rule)
    LinearLayout llRule;
    private View view;

    public InfoPopuwindow(Context context) {
        this.view = LayoutInflater.from(context).inflate(R.layout.info_popu_lay, null);
        ButterKnife.inject(this, view);
        // 设置外部可点击
        this.setOutsideTouchable(false);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        int width = RxDeviceUtils.getScreenWidth(context);
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(RelativeLayout.LayoutParams.WRAP_CONTENT);
        // 设置弹出窗体可点击
        this.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.playlist);
    }

    @OnClick({R.id.ll_share, R.id.ll_rule})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_share:
                RxToast.normal("分享");
                this.dismiss();
                break;
            case R.id.ll_rule:
                RxToast.normal("规则");
                this.dismiss();
                break;
        }
    }
}
