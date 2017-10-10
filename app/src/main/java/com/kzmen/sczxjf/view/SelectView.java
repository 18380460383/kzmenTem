package com.kzmen.sczxjf.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.kzmen.sczxjf.R;
import java.util.ArrayList;
import java.util.List;

/**
 * describe:
 * notice:
 * Created by FuPei on 2016/5/24.
 */
public class SelectView extends LinearLayout implements View.OnTouchListener {

    private final int PADDING = 20;
    private final int PADDING_DRAWABLE = 20;

    private OnItemClickListener itemClickListener;
    private Context mContext;
    private List<TextView> list;
    private List<Integer> list_tag;

    public SelectView(Context context) {
        this(context, null);
    }

    public SelectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        setOrientation(HORIZONTAL);
        setPadding(0, PADDING, 0, PADDING);
        list = new ArrayList<>();
        list_tag = new ArrayList<>();
    }

    public void addTag(int index) {
        list_tag.add(index);
    }

    public void setSelectText(String... texts) {
        list.clear();
        removeAllViews();
        for(int i = 0 ; i < texts.length; i++) {
//            TextView textView = createTextView(texts[i], i);
            addView(createTextView(texts[i], i), new LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            if(i != texts.length - 1) {
                addView(createDivider(), new LinearLayout.LayoutParams(
                        1, ViewGroup.LayoutParams.MATCH_PARENT));
            }
        }
    }

    public void setSelectText(List<String> listtext) {
        list.clear();
        removeAllViews();
        for(int i = 0 ; i < listtext.size(); i++) {
//            TextView textView = createTextView(texts[i], i);
            addView(createTextView(listtext.get(i), i), new LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            if(i != listtext.size() - 1) {
                addView(createDivider(), new LinearLayout.LayoutParams(
                        1, ViewGroup.LayoutParams.MATCH_PARENT));
            }
        }
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            int width = getWidth();
            float index = list.size() * (event.getX() / width);
            itemClickListener.onItemClick((int)index);
        }
        return true;
    }

    public interface OnItemClickListener {
        void onItemClick(int index);
    }

    private View createTextView(String text, int tag) {
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setOrientation(HORIZONTAL);
        linearLayout.setTag(tag);
        TextView textView = new TextView(mContext);
        textView.setText(text);
        ImageView imageView = new ImageView(mContext);
        imageView.setBackgroundResource(R.drawable.unfold);
//        Drawable drawable= getResources().getDrawable(R.mipmap.unfold);
// 这一步必须要做,否则不会显示.
//        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//        textView.setCompoundDrawables(null, null, drawable, null);
//        textView.setCompoundDrawablePadding(PADDING_DRAWABLE);
//        textView.setCompoundDrawables(null, null, mContext.getResources().getDrawable(R.mipmap.icon_e_calendar), null);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        textView.setPadding(0, 0, PADDING_DRAWABLE, 0);
        if(list_tag.contains(tag)) {
            textView.setTextColor(Color.parseColor("#ff8307"));
        }
        linearLayout.addView(textView);
        linearLayout.addView(imageView);
        list.add(textView);
        return linearLayout;
    }

    private ImageView createDivider() {
        ImageView imageView = new ImageView(mContext);
        imageView.setBackgroundColor(Color.GRAY);
        return imageView;
    }
}
