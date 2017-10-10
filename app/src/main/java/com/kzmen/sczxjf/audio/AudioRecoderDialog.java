package com.kzmen.sczxjf.audio;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kzmen.sczxjf.R;

/**
 * Created by MarioStudio on 2016/5/12.
 */

public class AudioRecoderDialog extends BasePopupWindow {

    private ImageView imageView;
    private TextView textView;

    public AudioRecoderDialog(Context context) {
        super(context);
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_recoder_dialog, null);
        imageView = (ImageView) contentView.findViewById(android.R.id.progress);
        textView = (TextView) contentView.findViewById(android.R.id.text1);
        setContentView(contentView);
    }

    public void setLevel(int level) {
        Drawable drawable = imageView.getDrawable();
        drawable.setLevel(3000 + 6000 * level / 100);
    }

    public void setTime(long time) {
        // textView.setText(ProgressTextUtils.getProgressText(time));
        textView.setText("" + time);
    }
    public void setTime(String time) {
        // textView.setText(ProgressTextUtils.getProgressText(time));
        textView.setText("" + time);
    }

    public void setMessageText(long time) {
        textView.setText("剩余录制时间" + time + "s");
    }
}
