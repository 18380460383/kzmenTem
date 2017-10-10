package com.kzmen.sczxjf.view;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 说明：
 * note：
 * Created by FuPei
 * on 2016/2/1 at 14:53
 */
public class SquareImageView extends ImageView {

    public SquareImageView(Context context) {
        this(context, null);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }

    private void initView() {
        setDrawingCacheEnabled(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = getMeasuredWidth();
        heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void setUI() {
        if (getDrawable() != null) {
            int height = getHeight();
            int width = getWidth();
            int h = getDrawable().getIntrinsicHeight();
            int w = getDrawable().getIntrinsicWidth();
            Matrix matrix = new Matrix();
            float scale_x = (float) width / w;
            float scale_y = (float) height / h;
//            float scale;
//            if(w > width && h > height) {
//                scale = scale_x < scale_y?scale_x:scale_y;
//            } else if(w < width && h < height) {
//                scale = scale_x > scale_y?scale_x:scale_y;
//            } else if(w > width && h <= height) {
//
//            }
            matrix.postScale(scale_x, scale_y);
            setImageMatrix(matrix);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        setUI();
    }
}
