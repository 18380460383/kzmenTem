package com.kzmen.sczxjf.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.control.ScreenControl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：Administrator
 * 时间：2016/4/19
 * 功能描述：竖状条形统计图
 */
public class HistogramView extends RelativeLayout {
    /**
     * Y轴最大值
     */
    private double YMAX;
    private int YPortion=1;
    private int XPortion=1;

    /**
     * Y轴主控件
     */
    private LinearLayout YLL;
    /**
     * X轴主控件
     */
    private LinearLayout XLL;
    /**
     * X轴Title
     */
    private String[] columnTitle;
    private int[] columnNum;
    /**
     * 试图变动周期（次）
     */
    private int data=20;
    private boolean iscreat;
    /**
     * 每个树状图的颜色
     */
    private String[] color;
    private ScreenControl screenControl;
    private int a=1;

    public boolean iscreat() {
        return iscreat;
    }

    public HistogramView(Context context) {
        super(context);
        setYLL(context);

    }
    public HistogramView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setYLL(context);
    }

    public HistogramView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setYLL(context);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 创建纵向,横向的线性布局
     * @param context
     */
    private void setYLL(Context context) {
        screenControl=new ScreenControl();
        YLL=new LinearLayout(context);
        LayoutParams paramsyll = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        int i = screenControl.getscreenWide();
        paramsyll.setMargins((int)(i *0.03),0,0,0);
        YLL.setLayoutParams(paramsyll);
        YLL.setOrientation(LinearLayout.VERTICAL);
        XLL=new LinearLayout(context);
        LayoutParams paramsxll = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        paramsxll.setMargins((int)(screenControl.getscreenWide()*0.12),0,30,0);
        XLL.setLayoutParams(paramsxll);
        XLL.setOrientation(LinearLayout.HORIZONTAL);

    }
    public void setYPortion(int YPortion) {
        this.YPortion = YPortion;
        YLL.setWeightSum(this.YPortion + 2);

    }
    public void setYMAX(double YMAX){
        this.YMAX=YMAX;
    }
    public void setColumnTitle(String[] columnTitle) {
        this.columnTitle = columnTitle;
        this.XPortion = this.columnTitle.length;
        XLL.setWeightSum(this.XPortion);
        XLL.setPadding((int)(screenControl.getscreenWide()*0.03), 0, 26, 0);
    }
    public void setColumnNum(int[] columnNum) {
        this.columnNum = columnNum;
    }

    public void setColor(String[] color) {
        this.color = color;
    }

    /**
     * 向LinearLayout 按比重添加控件
     * @param num 总权重
     */
    private void YLLAddView(int num,LinearLayout ll) {
        Log.i("tag","YYYYYYYYYYYY");
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        double value = this.YMAX/num;
        double a=this.YMAX/num;
        for(int i=num+1;i>0;i--){
            LinearLayout linearLayout = new LinearLayout(this.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
            params.weight=1;
            linearLayout.setLayoutParams(params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams paramstv = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
            paramstv.weight=1;
            TextView textView = new TextView(this.getContext());
            textView.setGravity(Gravity.BOTTOM);
            textView.setLayoutParams(paramstv);
            textView.setText(df.format(a*(i-1)));
            linearLayout.addView(textView);
            TextView textViewlin = new TextView(this.getContext());
            LinearLayout.LayoutParams paramstvlin = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
            textViewlin.setLayoutParams(paramstvlin);
            textViewlin.setBackgroundColor(Color.parseColor("#dedede"));
            linearLayout.addView(textViewlin);
            ll.addView(linearLayout);
        }
        LinearLayout linearLayout = new LinearLayout(this.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
        params.weight=1;
        linearLayout.setLayoutParams(params);
        ll.addView(linearLayout);
    }


    private List<View> XLLAddView(int num, LinearLayout ll) {
        Log.i("tag","XXXXXXXXXXXXXXXXxx");
        List<View> list=new ArrayList<>();
        for(int i=0;i<num;i++){
            LinearLayout linearLayout = new LinearLayout(this.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight=1;
            linearLayout.setLayoutParams(params);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setWeightSum(this.YPortion + 2);
            RelativeLayout relativeLayout = new RelativeLayout(this.getContext());
            LinearLayout.LayoutParams paramsrl = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0);
            paramsrl.weight=this.YPortion+1;
            relativeLayout.setLayoutParams(paramsrl);
            LayoutParams paramstvrl = new LayoutParams(LayoutParams.MATCH_PARENT,0);
            paramstvrl.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            paramstvrl.setMargins((int)(screenControl.getscreenWide()*0.03), 0, (int)(screenControl.getscreenWide()*0.03), 0);
            TextView textView = new TextView(this.getContext());
            textView.setId(R.id.histogramview_title);
            textView.setLayoutParams(paramstvrl);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.drawable_histogram);
            GradientDrawable myGrad = (GradientDrawable)textView.getBackground();
            myGrad.setColor(Color.parseColor(color[i]));
            TextView textViewTop = new TextView(this.getContext());
            LayoutParams paramstvrTop = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //paramstvrTop.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            paramstvrTop.addRule(RelativeLayout.ABOVE,textView.getId());
            paramstvrTop.setMargins((int) (screenControl.getscreenWide() * 0.03), 0, (int) (screenControl.getscreenWide() * 0.03), 0);
            textViewTop.setLayoutParams(paramstvrTop);
            textViewTop.setGravity(Gravity.CENTER);
            textViewTop.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            textViewTop.setText(this.columnNum[i] + "人");

            textView.setTextColor(getContext().getResources().getColor(R.color.write));
            Log.i("tag", textView.getText() + "" + textView.getTextColors().getDefaultColor());
            list.add(textView);
            relativeLayout.addView(textView);
            relativeLayout.addView(textViewTop);
            linearLayout.addView(relativeLayout);
            LinearLayout.LayoutParams paramstv = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
            paramstv.weight=1;
            TextView textViewy = new TextView(this.getContext());
            textViewy.setLayoutParams(paramstv);
            textViewy.setGravity(Gravity.CENTER);
            textViewy.setText(this.columnTitle[i]);
            linearLayout.addView(textViewy);
            ll.addView(linearLayout);
        }
        return list;
    }

    public void creation(){
        YLLAddView(this.YPortion, YLL);
        List<View> views = XLLAddView(this.XPortion, XLL);
        iscreat =true;
        this.addView(YLL);
        this.addView(XLL);
        setTextView(views);
    }
    private void setTextView(final List<View> views){

        Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LayoutParams params;
                    int size = views.size();
                    for (int i = 0; i < size; i++) {
                        params = new LayoutParams(LayoutParams.MATCH_PARENT, getHigt(HistogramView.this.columnNum[i]) * a / 20);
                        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                        params.setMargins((int)(screenControl.getscreenWide()*0.03), 0, (int)(screenControl.getscreenWide()*0.05), 0);
                        views.get(i).setLayoutParams(params);

                    }
                    data--;
                    if (data > 0) {
                        a++;
                        setTextView(views);
                    }

                }
            }, 14);
    }

    private int getHigt(int num) {
        int height = this.getHeight();
        int i = (height / (this.YPortion + 2)+1) * this.YPortion;
        return (int)(i * num / this.YMAX);
    }
    public void empty(){
        this.YLL.removeAllViews();
        this.XLL.removeAllViews();
        setYLL(this.getContext());
        data=20;
        a=1;
        this.removeAllViews();
        iscreat=false;
    }
}
