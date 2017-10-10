package com.kzmen.sczxjf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.MsgBean;
import com.kzmen.sczxjf.util.TextViewUtil;

import java.util.List;

/**
 * Created by FuPei
 * on 2015/11/19.
 */
public class MsgCenterAdapter extends BaseAdapter {
    private final int LAYOUTID = R.layout.item_msg_centre;
    private List<MsgBean> data;
    private Context mContext;
    private LayoutInflater mInflater = null;
    public MsgCenterAdapter(Context context, List<MsgBean> data) {
        this.mContext = context;
        this.data = data;
        mInflater = (LayoutInflater) mContext.getSystemService(context.LAYOUT_INFLATER_SERVICE);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MsgHold hold;
        if(convertView == null) {
            convertView = mInflater.inflate(LAYOUTID, null);
            hold = new MsgHold();
            hold.tv_title = (TextView) convertView.findViewById(R.id.item_msg_title);
            hold.tv_time = (TextView) convertView.findViewById(R.id.item_msg_time);
            hold.tv_text = (TextView) convertView.findViewById(R.id.item_msg_text);
            hold.tv_look = (TextView) convertView.findViewById(R.id.item_msg_look);
            hold.iv_isread = (ImageView) convertView.findViewById(R.id.item_msg_isread);
            convertView.setTag(hold);
        } else {
            hold = (MsgHold) convertView.getTag();
        }
        MsgBean bean = data.get(position);
        if(bean.isread.equals("1")) {
            hold.iv_isread.setBackgroundResource(R.drawable.drawable_gray_spot);
        } else if(bean.isread.equals("0")){
            hold.iv_isread.setBackgroundResource(R.drawable.drawable_red_spot);
        }
        final String title = bean.title;
        final String content = bean.content;
        final String time = bean.datetime;
        final String id = bean.id;
        hold.tv_title.setText(title);
        if(bean.nid != null) {
            hold.tv_title.append(TextViewUtil.getColorText( "@", "#ff8307").append("你:"));
        }
        hold.tv_time.setText(time);
        hold.tv_text.setText(content);
        return convertView;
    }

    private class MsgHold {
        private TextView tv_title;
        private TextView tv_look;
        private TextView tv_time;
        private TextView tv_text;
        private ImageView iv_isread;
    }
}
