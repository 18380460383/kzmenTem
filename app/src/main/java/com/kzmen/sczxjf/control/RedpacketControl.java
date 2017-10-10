package com.kzmen.sczxjf.control;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.returned.RedPacket;
import com.kzmen.sczxjf.utils.AppUtils;

/**
 * Created by Administrator on 2016/1/12.
 */
public class RedpacketControl implements View.OnClickListener{
    private Activity context;
    /**
     * 对话框操作对象
     */
    private AlertDialog alertDialog;
    /**
     * 对话框
     */
    private Dialog b;
    /**
     * 红包底图
     */
    private ImageView redIc;
    /**
     * 点击可以拆开红包的控件
     */
    private TextView goopenTextView;
    /**
     *拆开红包后的提示标题
     */
    private TextView opentitleTextView;
    /**
     *拆开红包后的提示内容
     */
    private TextView openmsgTextView;
    /**
     *拆开红包前底部提示标题
     */
    private TextView downtitleTextView;
    /**
     *拆开红包前底部提示内容
     */
    private TextView downmsgTextView;
    private ImageView close;
    private RedPacket redBean;
    /**
     * 红包金额
     */
    private String redMoney="100";

    public RedpacketControl(Activity context,RedPacket redPacket) {
        this.context = context;
        this.redBean=redPacket;
/*        if(this.redBean==null){
            redBean=new RedPacket();
            redBean.setNot_open_msg("拆红包");
            redBean.setNot_open_msg1("恭喜你");
            redBean.setNot_open_msg2("获得享e下红包");
            redBean.setOpened_msg("恭喜你");
            redBean.setOpened_msg1("获得享e下红包2元");
            redBean.setPackets_money(2);
        }*/
    }
    public void showRedDialog(){
        b=new Dialog(context);
        b.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View inflate = LayoutInflater.from(context).inflate(R.layout.red_packet_layout, null);
        redIc= (ImageView) inflate.findViewById(R.id.red_ic);

        redIc.setImageBitmap(AppUtils.readBitMap(context, R.drawable.ic_red_packet));
        goopenTextView= (TextView) inflate.findViewById(R.id.red_packet_go_open);
        goopenTextView.setText(redBean.getNot_open_msg());
        goopenTextView.setOnClickListener(this);
        opentitleTextView= (TextView) inflate.findViewById(R.id.red_packet_open_title);
        opentitleTextView.setText(redBean.getOpened_msg());
        opentitleTextView.setVisibility(View.GONE);
        openmsgTextView= (TextView) inflate.findViewById(R.id.red_packet_open_msg);
        openmsgTextView.setText(redBean.getOpened_msg1());
        openmsgTextView.setVisibility(View.GONE);
        downtitleTextView= (TextView) inflate.findViewById(R.id.red_packet_down_title);
        downtitleTextView.setText(redBean.getNot_open_msg1());
        downmsgTextView= (TextView) inflate.findViewById(R.id.red_packet_down_msg);
        downmsgTextView.setText(redBean.getNot_open_msg2());
        close= (ImageView) inflate.findViewById(R.id.close_red_packet);
        close.setOnClickListener(this);
        close.setVisibility(View.GONE);
        b.setContentView(inflate);
        ScreenControl s=new ScreenControl();
         b.getWindow().setLayout(s.getscreenWide(), s.getscreenHigh());
        b.getWindow().setBackgroundDrawableResource(R.color.reg_packed);
        b.setCancelable(false);
        b.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.red_packet_go_open:
                openRedEnvelope();
                break;
            case R.id.close_red_packet:
                b.dismiss();
                break;
        }
    }

    private void openRedEnvelope() {
        Animation a=new AlphaAnimation(1.0f, 0.1f);
        Animation b=new ScaleAnimation(1.0f,0f,1.0f,1.0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        a.setDuration(1000);
        b.setDuration(1000);
        AnimationSet set=new AnimationSet(true);
        set.addAnimation(a);
        set.addAnimation(b);

        goopenTextView.startAnimation(set);
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                redIc.setImageBitmap(AppUtils.readBitMap(context, R.drawable.red_packet_open));
                downtitleTextView.setVisibility(View.GONE);
                downmsgTextView.setVisibility(View.GONE);
                goopenTextView.setVisibility(View.GONE);
                opentitleTextView.setVisibility(View.VISIBLE);
                openmsgTextView.setVisibility(View.VISIBLE);
                openmsgTextView.setText(redBean.getOpened_msg1());
                close.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
