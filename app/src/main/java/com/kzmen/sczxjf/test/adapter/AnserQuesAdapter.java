package com.kzmen.sczxjf.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.TestItemBean;

import java.util.List;

/**
 * Created by pjj18 on 2017/8/3.
 */

public class AnserQuesAdapter extends BaseAdapter {

    Context context;
    LayoutInflater mInflater;
    ViewHolder holder;
    List<TestItemBean.ResultBean> list;
    TestItemBean.ResultBean question;
    private String rightAnswer;
    private int selected = -1;
    private int clickCount = 0;

    public AnserQuesAdapter(Context context, List<TestItemBean.ResultBean> list) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
    }

    public void reseat() {
        this.selected = -1;
        this.clickCount = 0;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount() {
        this.clickCount++;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public void setList(List<TestItemBean.ResultBean> Book) {
        this.list = Book;
    }

    public int getSelect() {
        return selected;
    }

    public void setSelect(int selected) {
        this.selected = selected;
    }

    // 选中当前选项时，让其他选项不被选中
   /* public void select(int position) {
        if (!list.get(position).isSelected()) {
            list.get(position).setSelected(true);
            for (int i = 0; i < list.size(); i++) {
                if (i != position) {
                    list.get(i).setSelected(false);
                }
            }
        }
        notifyDataSetChanged();
    }*/

    @Override
    public int getCount() {
        return list.size();
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
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.anser_question_item, null);
            holder.rb_check = (RadioButton) convertView
                    .findViewById(R.id.rb_check);
            holder.rb_check.setClickable(false);
            holder.iv_state = (ImageView) convertView
                    .findViewById(R.id.iv_state);
            holder.ll_content = (LinearLayout) convertView.findViewById(R.id.ll_content);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        question = (TestItemBean.ResultBean) getItem(position);
        // holder.rb_check.setChecked(question.isSelected());
        holder.tv_name.setText(question.getOpt_title());
        holder.iv_state.setVisibility(View.GONE);
        // if(selected!=-1){
           /* if(question.getOpt_id().equals(rightAnswer)){
                holder.iv_state.setBackgroundResource(R.drawable.right);
            }else{
                holder.iv_state.setBackgroundResource(R.drawable.wrong);
            }*/
        //}
        //holder.rb_check.setChecked(false);
        holder.ll_content.setBackgroundResource(R.drawable.kz_rb_bg_white);
        //if(clickCount==1){
        if (position == selected) {
            holder.rb_check.setChecked(true);
            holder.ll_content.setBackgroundResource(R.drawable.kz_rb_bg_yellow);
            //holder.iv_state.setVisibility(View.VISIBLE);
        } else if (selected != -1) {
            if (question.getOpt_id().equals(rightAnswer)) {
                //holder.iv_state.setVisibility(View.VISIBLE);
            }
        }
        //  }
        return convertView;
    }

    class ViewHolder {
        RadioButton rb_check;
        ImageView iv_state;
        TextView tv_name;
        LinearLayout ll_content;
    }

}
