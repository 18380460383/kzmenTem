package com.kzmen.sczxjf.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.ShopBean;
import com.kzmen.sczxjf.connector.BackgroundImageLoadingListenerForWaterfall;
import com.kzmen.sczxjf.control.ImageLoaderOptionsControl;
import com.kzmen.sczxjf.ui.activity.personal.ShopDetailsActivity;
import com.kzmen.sczxjf.view.PointListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/1/22.
 */
public class ShopAdapter /*extends BaseAdapter implements View.OnClickListener{*/extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ImageLoader  imageLoader = ImageLoader.getInstance();
    private static final int TYPE_HEADER = 0, TYPE_ITEM = 1, TYPE_FOOT = 2;
    private DisplayImageOptions options;
    public List<ShopBean> mDatas;
    private Context context;
    private int headViewid;
    private int headViewSize;
    private boolean isAddHead;
    private int footViewid;
    private int footViewSize;
    private boolean isAddFoot;
    private View mHeaderView;
    private ViewPager shopBanner;
    private TextView myIntegralNum;
    private TextView intoExchangeList;
    private PointListView shopPointListView;
    private int itemNum=0;
    private HeadBack headBack;

    public ShopAdapter(Context context,List<ShopBean> mDatas) {
        this.mDatas = mDatas;
        this.context=context;
    }

    public void addHeadView(int view,HeadBack headBack) {
        headViewid = view;
        headViewSize = 1;
        isAddHead=true;
        this.headBack=headBack;
    }

    public void addFootView(int view) {
        footViewid = view;
        footViewSize = 1;
        isAddFoot=true;
    }

    @Override
    public MyViewHolder1 onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = null;
        switch (i) {
            case TYPE_HEADER:
                mHeaderView= LayoutInflater.from(viewGroup.getContext()).inflate(headViewid, viewGroup, false);
                view =mHeaderView;
                if(headBack!=null){
                    headBack.setHeadView(mHeaderView);
                }
                break;

            case TYPE_ITEM:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_shop_gridview, viewGroup, false);

                break;

            case TYPE_FOOT:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(footViewid, viewGroup, false);
                break;
        }
        return new MyViewHolder1(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
       itemNum=i;
        switch (holder.getItemViewType()) {
            case TYPE_HEADER:
                // 获取cardview的布局属性，记住这里要是布局的最外层的控件的布局属性，如果是里层的会报cast错误
                StaggeredGridLayoutManager.LayoutParams clp = (StaggeredGridLayoutManager.LayoutParams)mHeaderView.getLayoutParams();
                // 最最关键一步，设置当前view占满列数，这样就可以占据两列实现头部了
                if(clp!=null)
                    clp.setFullSpan(true);
                break;
           default:

               final int pos = getRealPosition(holder);
               final ShopBean shopBean = mDatas.get(pos);
               final View itemView = holder.itemView;
               itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent intent = new Intent(context, ShopDetailsActivity.class);
                       intent.putExtra(ShopDetailsActivity.SHOPID, shopBean.getId());
                       context.startActivity(intent);
                   }
               });
               if(holder instanceof MyViewHolder1) {
                   ((MyViewHolder1) holder).title.setText(shopBean.getTitle());
                   ((MyViewHolder1) holder).integral.setText(shopBean.getScore());
                   if (null == options) {
                       options = ImageLoaderOptionsControl.getOptions();
                   }
                   if (imageLoader == null) {
                       imageLoader = ImageLoader.getInstance();
                   }
                   imageLoader.loadImage(shopBean.getImage(),options,new BackgroundImageLoadingListenerForWaterfall(((MyViewHolder1) holder).imageView,((MyViewHolder1) holder).image_argb));

               }
               if(pos-1>=mDatas.size()){
                   notifyDataSetChanged();
               }
                   break;
        }
    }
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }


    @Override
    public int getItemViewType(int position) {

        int type = TYPE_ITEM;
        if (headViewSize==1 && position == 0) {
            type = TYPE_HEADER;
        } else if (footViewSize==1 && position == getItemCount()-1) {
            //最后一个位置
            type = TYPE_FOOT;
        }
        return type;
    }

    @Override
    public int getItemCount() {
        return mDatas.size()+headViewSize+footViewSize;
    }


class MyViewHolder1 extends RecyclerView.ViewHolder{
    TextView title;
    TextView integral;
    TextView image_argb;
    ImageView imageView;
    public MyViewHolder1(View itemView) {
        super(itemView);
        if(itemView ==mHeaderView)
            return;
        title = (TextView) itemView.findViewById(R.id.shop_title);
        image_argb = (TextView) itemView.findViewById(R.id.image_argb);
        integral = (TextView) itemView.findViewById(R.id.shop_integral);
        imageView = (ImageView) itemView.findViewById(R.id.shop_image);
    }
}
    public interface HeadBack{
        void setHeadView(View head);
    }
}

