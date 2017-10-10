package com.kzmen.sczxjf.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by huahao on 2015/9/11.
 */
public class ExPandGridView extends GridView {

    public ExPandGridView(Context context) {
        super(context);
    }

    public ExPandGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExPandGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //测量
        int height = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, height);
    }
}
