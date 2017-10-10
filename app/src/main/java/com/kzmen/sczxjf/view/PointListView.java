package com.kzmen.sczxjf.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;

/**
 * Created by 杨操 on 2016/1/25.
 * 根据设置的ViewPager 生成导航点
 */
public class PointListView extends LinearLayout{
    private int listsize=0;
    private ViewPager viewPager;
    private TextView title;
    private LinearLayout points;
    private boolean lideRun;
    private boolean slide=true;
    private Handler h;

    public PointListView(Context context) {
        super(context);
    }

    public PointListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PointListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    public void setListsize(int size){
        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setGravity(Gravity.CENTER_VERTICAL);
        //点
        points = new LinearLayout(this.getContext());
        LayoutParams params1 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params1.setMargins(0,0,12,0);
        points.setLayoutParams(params1);
        points.setOrientation(LinearLayout.HORIZONTAL);
        points.setGravity(Gravity.CENTER);
        //标题
        title = new TextView(this.getContext());
        LayoutParams params2 = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
        params2.weight=1;
        title.setLayoutParams(params2);
        title.setPadding(6, 4, 12, 4);
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
        title.setLines(1);
        title.setEllipsize(TextUtils.TruncateAt.END);
        title.setGravity(Gravity.LEFT);
        if(size>1){
            title.setBackgroundColor(getResources().getColor(R.color.page_title_text));
        }
        title.setTextColor(getResources().getColor(R.color.no7_red_sidebar));
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(4, 0,4,0);
        this.listsize = size;
        if(size<=1){
            points.setVisibility(View.GONE);
            removeAllViews();
            addView(title);
        }else {
            this.setVisibility(View.VISIBLE);
            this.removeAllViews();
            ImageView child = null;
            for (int i = 0; i < this.listsize; i++) {
                child = new ImageView(this.getContext());
                child.setLayoutParams(params);
                child.setTag(i);
                child.setImageResource(R.drawable.guide_round);
                points.addView(child, i);
            }
            removeAllViews();
            addView(title);
            addView(points);

        }

    }
    public void select(int child){
        if(this.listsize>1) {
            if (child > this.listsize) {
                child = child % this.listsize;
            }
            for (int i = 0; i < this.listsize; i++) {
                if (child == i) {
                    points.getChildAt(i).setEnabled(false);
                } else {
                    points.getChildAt(i).setEnabled(true);
                }
            }
        }
    }

    public void setViewPager(ViewPager viewPager) {
        setViewPager(viewPager,null);
    }
    public void setViewPager(ViewPager viewPager, final Title titleback){
        this.viewPager = viewPager;
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                slide = false;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        slide = false;
                        System.out.println("不 可滑动");
                        break;
                    case MotionEvent.ACTION_UP:
                        slide = true;
                        System.out.println("可滑动");
                        break;
                }
                return false;
            }
        });
        if(listsize==1){
            if(null!=titleback){
                titleback.setTitle(0, title);
            }
        }
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                select(position);
                if(null==titleback){
                    title.setVisibility(View.INVISIBLE);
                }else {
                    if (position > listsize) {
                        position = position % listsize;
                    }
                    titleback.setTitle(position, title);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public interface Title{
         void setTitle(int position,TextView title);
    }
    /**
     * 开始{@link ViewPager}轮播
     */
    private void gopageslide() {
        lideRun =true;
        if(h==null){
            h = new Handler();
        }
        Log.i("tag", "滚动中");
        if (listsize > 0 && slide) {
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (listsize > 0 && slide) {
                        int currentItem = viewPager.getCurrentItem();
                        if (currentItem >= viewPager.getAdapter().getCount()) {
                            currentItem = listsize*100;
                        }
                        currentItem++;
                        PointListView.this.viewPager.setCurrentItem(currentItem);
                        if (slide) {
                            gopageslide();
                        }
                    }else{
                        lideRun =false;
                    }
                }
            }, 8000);
        }else{
            lideRun =false;
        }

    }

    /**
     * 启动{@link ViewPager}轮播
     * @return 是否启动成功
     */
    public boolean startpageslide(){
        if(listsize > 0 && slide){
            if(!lideRun){
                gopageslide();
            }
            return true;
        }else{
            return false;
        }
    }
    public void stopPagesLide(){
        slide=false;
    }

}
