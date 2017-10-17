package com.kzmen.sczxjf.popuwidow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.view.MyListView;
import com.vondear.rxtools.RxDeviceUtils;
import com.vondear.rxtools.view.RxToast;

import java.util.List;

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
    private List<String> dataList;
    private CommonAdapter<String> commonAdapter;
    private int posSelect = 0;

    public ListPopuwindow(Context context, List<String> dataList) {
        this.view = LayoutInflater.from(context).inflate(R.layout.popu_list_lay, null);
        ButterKnife.inject(this, view);
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
        commonAdapter = new CommonAdapter<String>(context, R.layout.popuwindow_list_item, dataList) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, final int position) {
                if (position == posSelect) {
                    viewHolder.getView(R.id.iv_state).setBackgroundResource(R.drawable.icon_checkbox2);
                } else {
                    viewHolder.getView(R.id.iv_state).setBackgroundResource(R.drawable.icon_checkbox1);
                }
                viewHolder.setText(R.id.tv_content, item);
                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        posSelect = position;
                        notifyDataSetChanged();
                    }
                });
            }
        };
        lv_msg.setAdapter(commonAdapter);
    }

    @OnClick(R.id.tv_sure)
    public void onViewClicked() {
        RxToast.normal("立即付款");
        this.dismiss();
    }
}
