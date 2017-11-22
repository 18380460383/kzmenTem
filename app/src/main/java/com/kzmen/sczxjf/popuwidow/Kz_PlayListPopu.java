package com.kzmen.sczxjf.popuwidow;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.KzConstanst;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.CourseDetailBean;
import com.kzmen.sczxjf.bean.kzbean.KzCoursePlayBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.consta.PlayState;
import com.kzmen.sczxjf.cusinterface.PlayPopuInterface;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.utils.TextUtil;
import com.kzmen.sczxjf.view.MyListView;
import com.vondear.rxtools.RxDeviceUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pjj18 on 2017/8/10.
 */

public class Kz_PlayListPopu extends PopupWindow {
    private Context mContext;
    private View view;
    private TextView tv_cancle;
    private MyListView tv_play_list;
    private MyListView lv_xiaojiang_list;
    private LinearLayout ll_xiaojiang;
    private PlayPopuInterface popuInterface;
    private int playPos = -1;
    private int state = -1;
    private AnimationDrawable animationDrawable;
    private List<KzCoursePlayBean> beanlist;
    public CommonAdapter<KzCoursePlayBean> adapter2;
    private List<CourseDetailBean.StageListBean.XiaojiangListBean> xiaojiangList;
    private CommonAdapter<CourseDetailBean.StageListBean.XiaojiangListBean> xiaojAdapter;
    private String title = "";
    private String mediaName = "";

    public PlayPopuInterface getPopuInterface() {
        return popuInterface;
    }

    public void setPopuInterface(PlayPopuInterface popuInterface) {
        this.popuInterface = popuInterface;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public int getState() {
        return state;
    }

    public void updateNo() {
        adapter2.notifyDataSetChanged();
    }

    public void setState(int state) {
        this.state = state;
        adapter2.notifyDataSetChanged();
    }

    public int getPlayPos() {
        return playPos;
    }

    public void setPlayPos(int playPos) {
        this.playPos = playPos;
    }

    public Kz_PlayListPopu(Context mContext, final List<KzCoursePlayBean> beanlist, List<CourseDetailBean.StageListBean.XiaojiangListBean> xiaojiangList) {
        this.beanlist = beanlist;
        this.xiaojiangList = xiaojiangList;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.kz_course_play_list, null);
        tv_cancle = (TextView) view.findViewById(R.id.tv_cancle);
        tv_play_list = (MyListView) view.findViewById(R.id.lv_play_list);
        lv_xiaojiang_list = (MyListView) view.findViewById(R.id.lv_xiaojiang_list);
        ll_xiaojiang = (LinearLayout) view.findViewById(R.id.ll_xiaojiang);
        tv_cancle.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dismiss();
            }
        });
        if (null == xiaojiangList || xiaojiangList.size() == 0) {
            ll_xiaojiang.setVisibility(View.GONE);
        }
        adapter2 = new CommonAdapter<KzCoursePlayBean>(mContext, R.layout.kz_course_detail_list_item, beanlist) {
            @Override
            protected void convert(ViewHolder viewHolder, final KzCoursePlayBean item, final int position) {
                if (TextUtil.isEmpty(item.getMedia_time())) {
                    item.setMedia_time("0");
                }
                final int musicTime = Integer.valueOf(item.getMedia_time());
                int min = musicTime / 60;
                int sec = musicTime % 60;
                String show = (min < 10 ? "0" + min : min) + ":" + (sec < 10 ? "0" + sec : sec);
                viewHolder.getView(R.id.top_line).setVisibility(View.VISIBLE);
                viewHolder.getView(R.id.bottom_line).setVisibility(View.VISIBLE);
                if (position == 0) {
                    viewHolder.getView(R.id.top_line).setVisibility(View.INVISIBLE);
                }
                if (position == (beanlist.size() - 1)) {
                    viewHolder.getView(R.id.bottom_line).setVisibility(View.INVISIBLE);
                }
                if (item.getType() == 0) {
                    viewHolder.getView(R.id.iv_play_state).setVisibility(View.GONE);
                    viewHolder.getView(R.id.v_cricle_point).setVisibility(View.VISIBLE);
                    viewHolder.getView(R.id.tv_title).setVisibility(View.VISIBLE);
                    viewHolder.setText(R.id.tv_title, item.getName());
                    viewHolder.getView(R.id.ll_play_state).setVisibility(View.INVISIBLE);
                    viewHolder.getView(R.id.tv_second).setVisibility(View.INVISIBLE);
                } else {
                    viewHolder.getView(R.id.v_cricle_point).setVisibility(View.GONE);
                    viewHolder.getView(R.id.iv_play_state).setVisibility(View.GONE);
                    viewHolder.setText(R.id.tv_second, item.getName())
                            .setText(R.id.tv_time, show);
                    viewHolder.getView(R.id.tv_second).setVisibility(View.VISIBLE);
                    viewHolder.getView(R.id.tv_title).setVisibility(View.INVISIBLE);
                    viewHolder.getView(R.id.ll_play_state).setVisibility(View.VISIBLE);
                }
                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.getType() == 1) {
                            if (popuInterface != null) {
                                title = item.getName();
                                mediaName = item.getMedia();
                                popuInterface.onItemClic(item.getName(), item.getMedia(), musicTime, 0, position);
                                notifyDataSetChanged();
                            }
                        }
                    }
                });
                final AnimationDrawable animationDrawable = (AnimationDrawable) ((ImageView) viewHolder.getView(R.id.iv_play_state))
                        .getDrawable();
                if (item.getName().equals(title) && item.getMedia().equals(mediaName)) {
                    viewHolder.getView(R.id.iv_play_state).setVisibility(View.VISIBLE);
                    switch (state) {
                        case PlayState.PLAY_PLAYING:
                            animationDrawable.start();
                            break;
                        case PlayState.PLAY_PAUSE:
                            animationDrawable.stop();
                            break;
                    }
                } else {
                    viewHolder.getView(R.id.iv_play_state).setVisibility(View.INVISIBLE);
                    animationDrawable.stop();
                }
            }
        };

        xiaojAdapter = new CommonAdapter<CourseDetailBean.StageListBean.XiaojiangListBean>(mContext, R.layout.kz_xiaojiang_list_item, this.xiaojiangList) {
            @Override
            protected void convert(ViewHolder viewHolder, final CourseDetailBean.StageListBean.XiaojiangListBean item, final int pos) {
                final int musicTime = Integer.valueOf(item.getMedia_time());
                int min = musicTime / 60;
                int sec = musicTime % 60;
                String show = (min < 10 ? "0" + min : min) + ":" + (sec < 10 ? "0" + sec : sec);
                viewHolder.setText(R.id.tv_xiaojiang_title1, item.getTitle())
                        .setText(R.id.tv_xiaogjiangtime1, show);
                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.getCharge_type().equals("1")) {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("type", "1");
                            params.put("aid", item.getId());
                            OkhttpUtilManager.setOrder(mContext, KzConstanst.addEavesdropOrder, params);
                        } else {
                            if (popuInterface != null) {
                                popuInterface.onItemClic(item.getTitle(), item.getMedia(), musicTime, 1, pos);
                            }
                        }
                    }
                });
            }
        };
        lv_xiaojiang_list.setAdapter(xiaojAdapter);
        tv_play_list.setAdapter(adapter2);
        this.setOutsideTouchable(false);
        this.setContentView(this.view);
        this.setHeight((int) (RxDeviceUtils.getScreenHeight(mContext) * 0.5));
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.playlist);
    }
}
