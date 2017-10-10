package com.kzmen.sczxjf.util;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

/**
 * Describe:
 * Created by FuPei on 2016/3/1.
 */
public class TextViewUtil {

    /**
     * 获取指定颜色的文字
     *
     * @param text
     * @param color
     * @return
     */
    public static SpannableStringBuilder getColorText(String text, String color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.parseColor(color));
        builder.setSpan(redSpan, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    /**
     * 设置EditText的文字可见随CheckBox的状态而变化
     *
     * @param cb
     * @param et
     */
    public static void setHideTextListener(CheckBox cb, final EditText et) {
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    et.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());
                } else {
                    et.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());
                }
            }
        });
    }

    /**
     * 判断一系列的EditText的值是否为空
     *
     * @param editTexts
     * @return
     */
    public static boolean checkIsEmpty(EditText... editTexts) {
        String text;
        for (EditText editText : editTexts) {
            text = editText.getText().toString().trim();
            if (text == null || text.length() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 设置组件是否可以获取焦点
     * @param canEdit
     * @param views
     */
    public static void setUIEnable(boolean canEdit, View... views) {
        for (android.view.View view : views) {
            view.setFocusable(canEdit);
            view.setFocusableInTouchMode(canEdit);
//            if (view instanceof CheckBox || view instanceof TextView || view instanceof ImageView) {
                view.setClickable(canEdit);
                view.setEnabled(canEdit);
//            }
        }
    }

}
