package com.kzmen.sczxjf.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.AdBean;
import com.kzmen.sczxjf.connector.BackgroundImageLoadingListener;
import com.kzmen.sczxjf.control.BannerSkip;
import com.kzmen.sczxjf.control.ImageLoaderOptionsControl;
import com.kzmen.sczxjf.ui.activity.WebviewActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/1/25.
 */
public class ShopBannerAdapter extends PagerAdapter implements View.OnClickListener{
    private List<AdBean> list;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private Context context;

    public ShopBannerAdapter(List<AdBean> list,Context context) {
        this.list = list;
        this.context=context;
    }

    @Override
    public int getCount() {
        if(list!=null&&list.size()==1){
            return 1;
        }else if(list.size()<1){
            return 0;
        }else {
            return Integer.MAX_VALUE;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        AdBean bean=list.get(position%list.size());
        ImageView inflate = (ImageView) LayoutInflater.from(container.getContext()).inflate(R.layout.ad_item, null);
        inflate.setTag(bean);
        inflate.setOnClickListener(this);
        if(null== options){
            options = ImageLoaderOptionsControl.getOptions();
        }
        if(imageLoader==null){
            imageLoader=ImageLoader.getInstance();
        }
        imageLoader.displayImage(bean.imageurl, inflate, options,new BackgroundImageLoadingListener(inflate));
        container.addView(inflate);
        return inflate;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(/*mListViews.get(position%mListViews.size())*/(View) object);
    }


    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == (arg1);
    }
    @Override
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }

    @Override
    public void onClick(View v) {
        AdBean bean = (AdBean) v.getTag();
        if(!TextUtils.isEmpty(bean.className)){
            BannerSkip bannerSkip = new BannerSkip(context, bean.className, bean.parameter);
            bannerSkip.goSkip();
        }else if(!TextUtils.isEmpty(bean.linkurl)){
            Intent intent = new Intent(context, WebviewActivity.class);
            intent.putExtra("url", bean.linkurl);
            context.startActivity(intent);
        }
    }
}
