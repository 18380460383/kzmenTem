package com.kzmen.sczxjf.view;


import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.util.EshareLoger;

/**
 * Fragment对话框
 * 使用：在实体化对象之后，必须调用setViewUI方法
 * Created by Fupei
 * on 2015/11/18.
 */
public class EshareDialogFragment extends DialogFragment {
    private final int mResource1 = R.layout.dialog_binding_phone;
    private final int mResource2 = R.layout.dialog_eshare;
    private int mResource;
    private Button btn, btn_no;
    private TextView tv;
    private EshareDialogClickListener linstener;

    /*显示的文本*/
    private String text, text_btn, text_no;

    public void setViewUI(String text, String btn_text, EshareDialogClickListener listener) {
        mResource = mResource1;
        this.text = text;
        this.linstener = listener;
        this.text_btn = btn_text;
        EshareLoger.logI("设置了文本:" + this.text);
    }

    public void setViewUI(String text, String btn_text, String btn_no_text, EshareDialogClickListener listener) {
        mResource = mResource2;
        this.text = text;
        this.linstener = listener;
        this.text_btn = btn_text;
        this.text_no = btn_no_text;
        EshareLoger.logI("设置了文本:" + this.text);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(mResource, container);
        btn = (Button) view.findViewById(R.id.binding_phone_know);
        tv = (TextView) view.findViewById(R.id.dialog_phone_tv_text);
        tv.setText(text);
        btn.setText(text_btn);
        EshareLoger.logI("onCreateView");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linstener.onClick();
            }
        });

        if(mResource == mResource2) {
            btn_no = (Button) view.findViewById(R.id.binding_phone_no);
            btn_no.setText(text_no);
            btn_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linstener.onCancel();
                }
            });
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * 按钮点击事件
     */
    public interface EshareDialogClickListener {
        void onClick();
        void onCancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        setCancelable(true);
    }
}
