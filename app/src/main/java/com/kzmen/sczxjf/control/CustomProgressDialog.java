package com.kzmen.sczxjf.control;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.kzmen.sczxjf.R;


public class CustomProgressDialog extends AlertDialog {
    View viewProgress;
    TextView textView;
    CircularProgress cp;

    public CustomProgressDialog(Context context) {
        super(context);

        viewProgress = getLayoutInflater().inflate(
                R.layout.view_loginactivity_progress, null);
        textView = (TextView) viewProgress.findViewById(R.id.text_progress);
        cp = (CircularProgress) viewProgress
                .findViewById(R.id.cp_loginactivity_progress);

        CustomProgressDialog.this.setView(viewProgress);
    }

    // progressdialog 提示文字
    public void setText(String text) {
        if(null != text && text.length() > 0) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(text);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    // progressdialog 圈圈颜色
    public void setCircularColor(int color) {
        cp.setColor(color);
    }

    @Override
    public void show() {
        super.show();
        if(textView.getText().length() == 0) {
            textView.setVisibility(View.GONE);
        }
    }
}
