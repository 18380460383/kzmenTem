package com.kzmen.sczxjf.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;

/**
 * 说明：
 * note:
 * Created by FuPei
 * on 2015/12/17 at 16:53
 */
public class DataView extends RelativeLayout{

    private final int DEFAULT_IMAGE = R.drawable.bj_recordrelay;
    private final String DEFAULT_TEXT = "暂无数据";
    private Context mContext;
    private TextView tv;
    private ImageView iv;
    private onLoadUi mload;

    public DataView(Context context, ListView lv, onLoadUi load) {
        super(context, null);
        mContext = context;
        initView();
        this.mload = load;
        setImage();
        setText();
        setVisibility(View.GONE);
        ((ViewGroup)lv.getParent()).addView(this);
        lv.setEmptyView(this);
    }

    public DataView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    public DataView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.view_public_nodata, this, true);
        iv = (ImageView) findViewById(R.id.view_iv);
        tv = (TextView) findViewById(R.id.view_tv);
    }

    public void setText() {
        if(mload.getText() == null ) {
            tv.setText(DEFAULT_TEXT);
        } else {
            tv.setText(mload.getText());
        }
    }

    public void setImage() {
        if(mload.getImage() == 0) {
            iv.setImageResource(DEFAULT_IMAGE);
        } else {
            iv.setImageResource(mload.getImage());
        }
    }

    public interface onLoadUi{
        String getText();
        int getImage();
    }
}
