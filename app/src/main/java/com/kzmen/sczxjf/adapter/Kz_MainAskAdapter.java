package com.kzmen.sczxjf.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.KzConstanst;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.HomeAskBean;
import com.kzmen.sczxjf.consta.PlayState;
import com.kzmen.sczxjf.interfaces.MainAskListClick;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.test.bean.Music;
import com.kzmen.sczxjf.util.glide.GlideCircleTransform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by pjj18 on 2017/8/21.
 */

public class Kz_MainAskAdapter extends BaseAdapter {
    private List<HomeAskBean> listData;
    private Context mContext;
    private AnimationDrawable animationDrawable;
    private int state = -1;
    private MainAskListClick mainAskListClick;
    private int playPosition = -1;
    private String mediaName = "";

    public Kz_MainAskAdapter(Context mContext, List<HomeAskBean> listData, MainAskListClick mainAskListClick) {
        this.mContext = mContext;
        this.listData = listData;
        this.mainAskListClick = mainAskListClick;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.kz_main_ask_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvAskTitle1.setText(listData.get(position).getContent());
        viewHolder.tvAskListenState2.setText(listData.get(position).getIsopen_str());
        viewHolder.tvAskListenCount1.setText(listData.get(position).getViews() + "人听过");
        viewHolder.tvAskListenType1.setText("" + listData.get(position).getTeacher_title());
        viewHolder.tvAskListenType1.setVisibility(View.GONE);
       /* if ("1".equals(listData.get(position).getTeacher())) {
            viewHolder.tvAskListenType1.setVisibility(View.VISIBLE);
        }*/
        viewHolder.tvAskListenName1.setText(listData.get(position).getNickname());
        if (listData.get(position).getMedia_status().equals(KzConstanst.IS_FASLE)) {
            viewHolder.ll_listen.setBackgroundResource(R.drawable.bg_play_green);
        } else {
            viewHolder.ll_listen.setBackgroundResource(R.drawable.bg_play_blue);
            viewHolder.tvAskListenState2.setText("点击播放");
        }
        Glide.with(mContext).load(listData.get(position).getAvatar()).transform(new GlideCircleTransform(mContext)).
                placeholder(R.drawable.icon_user_normal).dontAnimate().into(viewHolder.ivAskHead2);
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.ll_listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainAskListClick != null) {
                   /* mainAskListClick.onPosClick(position);
                    playPosition = position;*/
                    if (listData.get(position).getMedia_status().equals(KzConstanst.IS_FASLE)) {
                        playPosition = position;
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("type", "2");
                        params.put("aid", listData.get(position).getAid());
                        OkhttpUtilManager.setOrder(mContext, KzConstanst.addEavesdropOrder, params);
                    } else {
                        mainAskListClick.onPosClick(position);
                        playPosition = position;
                    }
                }
            }
        });
        animationDrawable = (AnimationDrawable) finalViewHolder.iv_anim
                .getDrawable();
        if (position == playPosition) {
            switch (state) {
                case PlayState.PLAY_PLAYING:
                    animationDrawable.start();
                    break;
                case PlayState.PLAY_PAUSE:
                    animationDrawable.stop();
                    animationDrawable.selectDrawable(0);
                    break;
            }
        } else {
            animationDrawable.stop();
            animationDrawable.selectDrawable(0);
        }
        return convertView;
    }

    private void setMusic(String url) {
        List<Music> mMusicList = new ArrayList<>();
        Music music = new Music();
        music.setType(Music.Type.ONLINE);
        music.setPath(url);
        mMusicList.add(music);
        AppContext.getPlayService().setMusicList(mMusicList);
    }

    public void state(int state) {
        this.state = state;
        notifyDataSetChanged();
    }

    public void setPlayPosition(int playPosition) {
        this.playPosition = playPosition;
    }

    public void updateOpen() {
        if (this.playPosition != -1) {
            listData.get(playPosition).setIsopen("0");
            listData.get(playPosition).setMedia_status(KzConstanst.IS_TRUE);
            listData.get(playPosition).setIsopen_str("点击播放");
            this.notifyDataSetChanged();
        }
    }

    public int getPlayPosition() {
        return playPosition;
    }

    class ViewHolder {
        @InjectView(R.id.tv_ask_title1)
        TextView tvAskTitle1;
        @InjectView(R.id.iv_ask_head2)
        ImageView ivAskHead2;
        @InjectView(R.id.tv_ask_listen_state2)
        TextView tvAskListenState2;
        @InjectView(R.id.tv_ask_listen_type1)
        TextView tvAskListenType1;
        @InjectView(R.id.tv_ask_listen_name1)
        TextView tvAskListenName1;
        @InjectView(R.id.tv_ask_listen_count1)
        TextView tvAskListenCount1;
        @InjectView(R.id.ll_listen)
        LinearLayout ll_listen;
        @InjectView(R.id.iv_anim)
        ImageView iv_anim;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
