package com.kzmen.sczxjf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.HomeCourseBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.consta.PlayState;
import com.kzmen.sczxjf.interfaces.MainCourseListClick;
import com.kzmen.sczxjf.view.MyListView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by pjj18 on 2017/8/21.
 */

public class Kz_MainCourseAdapter extends BaseAdapter {
    private List<HomeCourseBean> listData;
    private Context mContext;
    private MainCourseListClick mainCourseListClick;
    private int playPosition = -1;
    private int plPosition = -1;
    private boolean isPlay = false;

    public Kz_MainCourseAdapter(Context mContext, List<HomeCourseBean> listData, MainCourseListClick mainCourseListClick) {
        this.mContext = mContext;
        this.listData = listData;
        this.mainCourseListClick = mainCourseListClick;
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.kz_main_course_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.sbPlay.setTag(position);
        viewHolder.sbPlay.setOnSeekBarChangeListener(new SeekBarChangeEvent());
        viewHolder.tvTitle.setText(listData.get(position).getDescribe());
        if (position == playPosition && plPosition != -1) {
            viewHolder.tvTitle.setText(listData.get(position).getKejian_arr().get(plPosition).getTitle());
        }
        viewHolder.tvCourseEx.setText(listData.get(position).getTitle());
        viewHolder.tvUserName.setText(listData.get(position).getTid_name());
        viewHolder.tvUserIdentity.setText(listData.get(position).getTid_title());
        viewHolder.tv_views.setText(listData.get(position).getViews() + "人听过");
        Glide.with(mContext).load(listData.get(position).getImage())
                .placeholder(R.drawable.icon_image_normal)
                .dontAnimate()
                .into(viewHolder.ivUserHead);
        if (null != listData.get(position).getXiaojiang_arr()) {
            viewHolder.lvXiaojiang.setAdapter(new CommonAdapter<HomeCourseBean.XiaojiangArrBean>(mContext, R.layout.kz_xiaojiang_list_item, listData.get(position).getXiaojiang_arr()) {
                @Override
                protected void convert(com.kzmen.sczxjf.commonadapter.ViewHolder viewHolder, HomeCourseBean.XiaojiangArrBean item, int position) {
                    int musicTime = Integer.valueOf(item.getMedia_time());
                    int min = musicTime / 60;
                    int sec = musicTime % 60;
                    String show = (min < 10 ? "0" + min : min) + ":" + (sec < 10 ? "0" + sec : sec);
                    viewHolder.setText(R.id.tv_xiaojiang_title1, item.getTitle())
                            .setText(R.id.tv_xiaogjiangtime1, show);
                }
            });
            viewHolder.lvXiaojiang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                    if (mainCourseListClick != null) {
                        mainCourseListClick.onClickXiaoJiang(position, pos);
                    }
                }
            });
        }
        viewHolder.ivCoursePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainCourseListClick != null) {
                    mainCourseListClick.onPlay(position);
                    playPosition = position;
                    isPlay = true;
                }
            }
        });
        viewHolder.llMoreCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainCourseListClick != null) {
                    mainCourseListClick.onClickMore(position);
                }
            }
        });
        if (position == playPosition) {
            viewHolder.tvMediaStartTime.setText(start);
            viewHolder.tvMediaEndTime.setText(end);
            viewHolder.sbPlay.setProgress(pos);
            switch (state) {
                case PlayState.PLAY_PLAYING:
                    viewHolder.ivCoursePlay.setBackgroundResource(R.drawable.btn_player_pause);
                    break;
                case PlayState.PLAY_PAUSE:
                    viewHolder.ivCoursePlay.setBackgroundResource(R.drawable.btn_player_play);
                    break;
            }
        } else {
            viewHolder.tvMediaStartTime.setText("00:00");
            if (null != listData.get(position) && listData.get(position).getKejian_arr().size() > 0) {
                int musicTime = Integer.valueOf(listData.get(position).getKejian_arr().get(0).getMedia_time());
                int min = musicTime / 60;
                int sec = musicTime % 60;
                String show = (min < 10 ? "0" + min : min) + ":" + (sec < 10 ? "0" + sec : sec);
                viewHolder.tvMediaEndTime.setText(show);
            } else {
                viewHolder.tvMediaEndTime.setText("00:00");
            }
            viewHolder.sbPlay.setProgress(0);
            viewHolder.ivCoursePlay.setBackgroundResource(R.drawable.btn_player_play);
        }
        viewHolder.tv_play_state.setVisibility(View.GONE);
        if (isPlay) {
            if (pos <= percent && percent < 100) {
                viewHolder.tv_play_state.setVisibility(View.VISIBLE);
            }
        }
        return convertView;
    }

    private String getTime(int musicTime) {
        int min = musicTime / 60;
        int sec = musicTime % 60;
        String show = (min < 10 ? "0" + min : min) + ":" + (sec < 10 ? "0" + sec : sec);
        return show;
    }

    private int percent;
    private String start;
    private String end;
    private int pos;
    private int state;

    public int getPlPosition() {
        return plPosition;
    }

    public void setPlPosition(int plPosition) {
        this.plPosition = plPosition;
        notifyDataSetChanged();
    }

    public void prePercent(int percent) {
        this.percent = percent;
        notifyDataSetChanged();
    }

    public void time(String start, String end, int pos) {
        this.start = start;
        this.end = end;
        this.pos = pos;
        notifyDataSetChanged();
    }

    public void state(int state) {
        this.state = state;
        notifyDataSetChanged();
    }

    public void setPlayPosition(int playPosition) {
        this.playPosition = playPosition;
    }

    public int getPlayPosition() {
        return playPosition;
    }

    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        int progress;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            if (((int) seekBar.getTag()) == playPosition) {
                this.progress = progress * (AppContext.getPlayService().mPlayer.getDuration())
                        / seekBar.getMax();
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
            if (((int) seekBar.getTag()) == playPosition) {
                AppContext.getPlayService().mPlayer.seekTo(progress);
            }
        }
    }

    class ViewHolder {
        @InjectView(R.id.iv_user_head)
        ImageView ivUserHead;
        @InjectView(R.id.tv_user_identity)
        TextView tvUserIdentity;
        @InjectView(R.id.tv_views)
        TextView tv_views;
        @InjectView(R.id.tv_user_name)
        TextView tvUserName;
        @InjectView(R.id.ll_user_head)
        LinearLayout llUserHead;
        @InjectView(R.id.tv_course_ex)
        TextView tvCourseEx;
        @InjectView(R.id.tv_title)
        TextView tvTitle;
        @InjectView(R.id.tv_media_start_time)
        TextView tvMediaStartTime;
        @InjectView(R.id.tv_media_end_time)
        TextView tvMediaEndTime;
        @InjectView(R.id.tv_play_state)
        TextView tv_play_state;
        @InjectView(R.id.sb_play)
        SeekBar sbPlay;
        @InjectView(R.id.iv_course_play)
        ImageView ivCoursePlay;
        @InjectView(R.id.lv_xiaojiang)
        MyListView lvXiaojiang;
        @InjectView(R.id.ll_more_course)
        LinearLayout llMoreCourse;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
