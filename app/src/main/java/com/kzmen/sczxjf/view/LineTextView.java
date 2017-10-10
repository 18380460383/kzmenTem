package com.kzmen.sczxjf.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.kzmen.sczxjf.R;

/**
 * Describe:
 * Created by FuPei on 2016/2/29.
 */
public class LineTextView extends TextView {

    private final int SIZE_LINE = 2;
    private final int COLOR_LINE = R.color.gray;
    private final int COLOR_TEXT = Color.parseColor("#353535");
    private Paint linePaint;
    private String text;

    public LineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LineTextView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int height = getHeight();
        linePaint = new Paint();
        linePaint.setColor(getResources().getColor(COLOR_LINE));
        canvas.drawLine(0, height - SIZE_LINE, getWidth(), height - SIZE_LINE, linePaint);
        if(!TextUtils.isEmpty(text)) {
            TextPaint paint = new TextPaint();
            paint.setAntiAlias(true);
            paint.setTextSize(getTextSize());
            paint.setTextAlign(Paint.Align.LEFT);
            paint.setColor(COLOR_TEXT);
            Rect bounds = new Rect();
            paint.getTextBounds(text, 0, text.length(), bounds);
            Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();

            int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
            canvas.drawText(text, getPaddingRight(), baseline, paint);
            setPadding(getTextWidth(paint, text) + getPaddingRight(),getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
        super.onDraw(canvas);
    }

    public static int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    public void setTypeText(String text) {
        this.text = text;
        invalidate();
    }
}
