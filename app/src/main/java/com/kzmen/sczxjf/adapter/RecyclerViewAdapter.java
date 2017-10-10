package com.kzmen.sczxjf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.CouseQuestionBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.utils.TextUtil;
import com.kzmen.sczxjf.view.MyListView;
import com.vondear.rxtools.view.RxToast;

import java.util.List;

import static com.kzmen.sczxjf.R.id.tv_time;

public class RecyclerViewAdapter extends Adapter<ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private Context context;
    private List<CouseQuestionBean> beanlist;


    public RecyclerViewAdapter(Context context, List<CouseQuestionBean> beanlist) {
        this.context = context;
        this.beanlist = beanlist;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return beanlist.size() == 0 ? 0 : beanlist.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.kz_good_ask_item, parent,
                    false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_foot, parent,
                    false);
            return new FootViewHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            //holder.tv.setText(data.get(position));
            CouseQuestionBean item=beanlist.get(position-1);
            ((ItemViewHolder) holder).tv_title.setText("" + item.getContent());
            ((ItemViewHolder) holder).tv_user_name.setText("" + item.getUsername());
            ((ItemViewHolder) holder).tv_time.setText("" + item.getDatetime());
            ((ItemViewHolder) holder).tv_zans.setText("" + item.getZans());
            ((ItemViewHolder) holder).tv_views.setText("" + item.getViews());
            ((ItemViewHolder) holder).tv_ask_listen_state2.setText("" + item.getMedia_button());
            ((ItemViewHolder) holder).tv_content.setText("" + item.getAnswer_content());
            Glide.with(context).load(item.getAvatar()).placeholder(R.drawable.icon_image_normal).into(((ItemViewHolder) holder).iv_user_head);
            Glide.with(context).load(item.getAnswer_avatar()).placeholder(R.drawable.icon_image_normal).into(((ItemViewHolder) holder).iv_answer_img);
            ((ItemViewHolder) holder).ll_listens.setBackgroundResource(R.drawable.bg_play_blue);

            if(item.getIsopen().equals("1")){
                ((ItemViewHolder) holder).ll_listens.setBackgroundResource(R.drawable.bg_play_green);
                ((ItemViewHolder) holder).ll_listens.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // doPay(item.getMedia_money());
                    }
                });
            }else{
               // RxToast.normal("播放"+item.getAnswer_media());
            }
            if(TextUtil.isEmpty(item.getAnswer_media())){
                ((ItemViewHolder) holder).ll_txt.setVisibility(View.VISIBLE);
                ((ItemViewHolder) holder).ll_media.setVisibility(View.GONE);
            }else{
                ((ItemViewHolder) holder).ll_txt.setVisibility(View.GONE);
                ((ItemViewHolder) holder).ll_media.setVisibility(View.VISIBLE);
            }
            ((ItemViewHolder) holder).lv_add_question.setVisibility(View.GONE);
            if(item.getZhuijia_list()!=null){
                ((ItemViewHolder) holder).lv_add_question.setVisibility(View.VISIBLE);
                ((ItemViewHolder) holder).lv_add_question.setAdapter(new CommonAdapter<CouseQuestionBean.ZhuijiaListBean>(context,R.layout.kz_question_list_item,item.getZhuijia_list()) {
                    @Override
                    protected void convert(com.kzmen.sczxjf.commonadapter.ViewHolder viewHolder, final CouseQuestionBean.ZhuijiaListBean item, int position) {
                        viewHolder.setText(R.id.tv_title, "" + item.getContent())
                                .glideImage(R.id.iv_user_head,item.getAvatar())
                                .glideImage(R.id.iv_answer_img,item.getAnswer_avatar())
                                .setText(R.id.tv_user_name,item.getUsername())
                                .setText(tv_time,""+item.getDatetime())
                                .setText(R.id.tv_zans,""+item.getZans())
                                .setText(R.id.tv_views,""+item.getViews())
                                .setText(R.id.tv_ask_listen_state2,item.getMedia_button())
                                .setText(R.id.tv_content,item.getAnswer_content());
                        viewHolder.getView(R.id.ll_listens).setBackgroundResource(R.drawable.bg_play_blue);
                        if(item.getIsopen().equals("1")){
                            viewHolder.getView(R.id.ll_listens).setBackgroundResource(R.drawable.bg_play_green);
                            viewHolder.getView(R.id.ll_listens).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //doPay(item.getMedia_money());
                                }
                            });
                        }else{
                            RxToast.normal("播放"+item.getAnswer_media());
                        }
                        if(TextUtil.isEmpty(item.getAnswer_media())){
                            viewHolder.getView(R.id.ll_txt).setVisibility(View.VISIBLE);
                            viewHolder.getView(R.id.ll_media).setVisibility(View.GONE);
                        }else{
                            viewHolder.getView(R.id.ll_txt).setVisibility(View.GONE);
                            viewHolder.getView(R.id.ll_media).setVisibility(View.VISIBLE);
                        }
                    }
                });
            }

        }
    }


    static class ItemViewHolder extends ViewHolder {

        TextView tv_title;
        TextView tv_user_name;
        TextView tv_time;
        TextView tv_zans;
        TextView tv_views;
        TextView tv_ask_listen_state2;
        TextView tv_content;
        ImageView iv_user_head;
        ImageView iv_answer_img;
        LinearLayout ll_listens;
        LinearLayout ll_txt;
        LinearLayout ll_media;
        MyListView lv_add_question;

        public ItemViewHolder(View view) {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_zans = (TextView) view.findViewById(R.id.tv_zans);
            tv_views = (TextView) view.findViewById(R.id.tv_views);
            tv_ask_listen_state2 = (TextView) view.findViewById(R.id.tv_ask_listen_state2);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
            iv_user_head= (ImageView) view.findViewById(R.id.iv_user_head);
            iv_answer_img= (ImageView) view.findViewById(R.id.iv_answer_img);
            ll_listens= (LinearLayout) view.findViewById(R.id.ll_listens);
            ll_txt= (LinearLayout) view.findViewById(R.id.ll_txt);
            ll_media= (LinearLayout) view.findViewById(R.id.ll_media);
            lv_add_question= (MyListView) view.findViewById(R.id.lv_add_question);
        }
    }

    static class FootViewHolder extends ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }
}