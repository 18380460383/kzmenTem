package com.kzmen.sczxjf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.returned.Activitys;
import com.kzmen.sczxjf.connector.BackgroundImageLoadingListener;
import com.kzmen.sczxjf.control.ImageLoaderOptionsControl;
import com.kzmen.sczxjf.control.ScreenControl;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/1/13.
 */
public class ActivitysAdapter  extends BaseAdapter{
    private List<Activitys> list;
    private Context context;
    private LayoutInflater from;
    private ImageLoader loader;
    private DisplayImageOptions options;

    public ActivitysAdapter(List<Activitys> list, Context context) {
        this.list = list;
        this.context = context;
        from= LayoutInflater.from(context);
        loader=ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return list.size()>0?list.size():0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ActivitysHodler hodler=null;
        if(convertView==null){
            convertView=from.inflate(R.layout.item_activitys,null);
            hodler=new ActivitysHodler();
            hodler.activityImage= (ImageView) convertView.findViewById(R.id.activity_image);
            hodler.activitysDate= (TextView) convertView.findViewById(R.id.activity_date);
            hodler.activityUserNum= (TextView) convertView.findViewById(R.id.activitys_user_num);
            convertView.setTag(hodler);
        }else{
            hodler= (ActivitysHodler) convertView.getTag();
        }
        Activitys activitys = list.get(position);
        if(null== options){
            options = ImageLoaderOptionsControl.getOptions();
        }
        ScreenControl s=new ScreenControl();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(s.getscreenWide(),s.getscreenHigh()/4);
        hodler.activityImage.setLayoutParams(params);
        loader.displayImage(activitys.getImageurl(), hodler.activityImage, options,new BackgroundImageLoadingListener(hodler.activityImage));
        hodler.activitysDate.setText("活动时间："+activitys.getStartdate().substring(0, 10) + "-" + activitys.getEnddate().substring(0, 10));
        hodler.activityUserNum.setText(activitys.getHits());
        return convertView;
    }
     class ActivitysHodler{
         ImageView activityImage;
         TextView activitysDate;
         TextView activityUserNum;
     }
}
