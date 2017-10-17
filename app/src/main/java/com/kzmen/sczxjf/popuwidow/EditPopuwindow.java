package com.kzmen.sczxjf.popuwidow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.vondear.rxtools.RxDeviceUtils;
import com.vondear.rxtools.view.RxToast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by pjj18 on 2017/10/16.
 */

public class EditPopuwindow extends PopupWindow {

    @InjectView(R.id.ed_name)
    EditText edName;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.ed_percent)
    EditText edPercent;
    @InjectView(R.id.tv_sure)
    TextView tvSure;
    private View view;
    private int opType = 0;//0显示  1 编辑
    private String name = "";
    private String percent = "";

    public EditPopuwindow(Context context, int opType, String name, String percent) {
        this.view = LayoutInflater.from(context).inflate(R.layout.popuwindow_edit, null);
        ButterKnife.inject(this, view);
        // 设置外部可点击
        this.setOutsideTouchable(false);
        // 设置视图
        this.setContentView(this.view);
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
        switch (opType) {
            case 0:
                edName.setVisibility(View.GONE);
                tvName.setText(name);
                edPercent.setText(percent);
                break;
            case 1:
                tvName.setVisibility(View.GONE);
                break;
        }
    }

    @OnClick(R.id.tv_sure)
    public void onViewClicked() {
        RxToast.normal("确定");
        this.dismiss();
    }
}
