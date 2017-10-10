package com.kzmen.sczxjf.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.ProjectBean;
import com.kzmen.sczxjf.bean.returned.PageListReturn;
import com.kzmen.sczxjf.connector.BackgroundImageLoadingListener;
import com.kzmen.sczxjf.control.ImageLoaderOptionsControl;
import com.kzmen.sczxjf.util.BitmapManager;
import com.kzmen.sczxjf.util.StringUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 列表
 *
 */
public class PageListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater = null;

    private PageListReturn listBean;
    private List<ProjectBean> rows;

    // 需要计算时间
    private boolean needCompute = false;

    BitmapManager bmpManager;

    private String type = "进行中";
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public PageListAdapter(Context ctx, List<ProjectBean> rows) {
        imageLoader = ImageLoader.getInstance();
        mContext = ctx;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.rows = rows;
        bmpManager = new BitmapManager(BitmapFactory.decodeResource(AppContext.getInstance().getResources(),
                R.drawable.image_def));
    }

    public void setType(String type) {
        if(type.equals("selfstart")) {
            this.type = "进行中";
            needCompute = true;
        } else if(type.equals("allmiss")) {
            this.type = "已结束";
        } else if(type.equals("allbuying")) {
            this.type = "进行中";
            needCompute = true;
        } else if(type.equals("allforbuy")) {
            this.type = "即将开抢";
            needCompute = true;
        }
    }

    public String getType() {
        return type;
    }

/*    public void setData(PageListReturn listBean) {
        this.rows.clear();
        if(listBean != null && listBean.msg!= null) {
            this.rows.addAll(listBean.msg);
        }
        notifyDataSetChanged();
    }*/


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        GroupViewHolder group = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_pro, null);
            group = new GroupViewHolder();
            group.image = (ImageView) convertView.findViewById(R.id.pro_image);
            group.title = (TextView) convertView.findViewById(R.id.pro_title);
            group.status = (TextView) convertView.findViewById(R.id.pro_status);
            group.precent = (TextView) convertView.findViewById(R.id.pro_precent);
            group.progress = (ProgressBar) convertView.findViewById(R.id.pro_progress);
            group.money = (TextView) convertView.findViewById(R.id.pro_money);
            convertView.setTag(group);
        } else {
            group = (GroupViewHolder) convertView.getTag();
        }
        ProjectBean projectBean = rows.get(position);
        group.title.setText(projectBean.projectname);
        group.money.setText(projectBean.reward);
        group.precent.setText(projectBean.getPrecent() + "%");
        group.progress.setProgress(projectBean.getPrecent());
        if(needCompute) {
            group.status.setText(AppContext.getInstance().getTimeMillis(projectBean.nowdate,projectBean.enddate));
        } else {
            group.status.setText(type);
        }
        if(!StringUtils.isEmpty(projectBean.imageurl)) {
            if(null== options){
                options = ImageLoaderOptionsControl.getOptions();
            }
            imageLoader.displayImage(projectBean.imageurl, group.image,options,new BackgroundImageLoadingListener(group.image));
        }
        return convertView;
    }

    private class GroupViewHolder {
        ImageView image;
        TextView title;
        TextView status;
        TextView precent;
        ProgressBar progress;
        TextView money;
    }

    @Override
    public int getCount() {
        return rows.size();
    }



    @Override
    public Object getItem(int position) {

        if(rows != null && position-1 < rows.size()&&position-1>=0) {
            return rows.get(position-1);
        }
        return null;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


}
