package com.kzmen.sczxjf.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 说明：Toast辅助类
 * note：
 * Created by FuPei
 * on 2015/12/1 at 14:50
 */
public class EToastUtil {
    public static void show(Context context, String text) {
        if(text == null && text.length() == 0)
            return;
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
