package com.kzmen.sczxjf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.CourseListBean;

import java.util.List;

/**
 * Created by pjj18 on 2017/8/9.
 */

public class Kz_CourseDetaiListAdapter extends BaseAdapter {
    private List<CourseListBean>beanList;
    private Context mContext;

    public Kz_CourseDetaiListAdapter(Context mContext, List<CourseListBean> beanList) {
        this.mContext = mContext;
        this.beanList = beanList;
    }

    @Override
    public int getCount() {
        return beanList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            view= LayoutInflater.from(mContext).inflate(R.layout.kz_course_detail_list_item,null);
            viewHolder=new ViewHolder();
            viewHolder.v_cricle_point=view.findViewById(R.id.v_cricle_point);
            viewHolder.tv_title= (TextView) view.findViewById(R.id.tv_title);
            viewHolder.tv_second= (TextView) view.findViewById(R.id.tv_second);
            viewHolder.tv_time= (TextView) view.findViewById(R.id.tv_time);
            viewHolder.iv_play= (ImageView) view.findViewById(R.id.iv_play);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
       /* CourseListBean bean=beanList.get(i);
        viewHolder.v_cricle_point.setVisibility(bean.getType()==0?View.VISIBLE:View.GONE);
        viewHolder.tv_title.setVisibility(bean.getType()==0?View.VISIBLE:View.GONE);
        viewHolder.tv_second.setVisibility(bean.getType()==0?View.GONE:View.VISIBLE);
        if(bean.getType()==0){
            viewHolder.tv_title.setText(bean.getName());
            viewHolder.iv_play.setVisibility(View.GONE);
            viewHolder.tv_time.setVisibility(View.GONE);
        }else{
            viewHolder.tv_second.setText(bean.getName());
            viewHolder.tv_time.setText(bean.getTime());
        }*/
        return view;
    }
    class ViewHolder{
        private View v_cricle_point;
        private TextView tv_title;
        private TextView tv_second;
        private TextView tv_time;
        private ImageView iv_play;
    }
}
