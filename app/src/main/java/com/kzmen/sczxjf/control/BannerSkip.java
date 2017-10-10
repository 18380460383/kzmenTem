package com.kzmen.sczxjf.control;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**用于Banner跳转到APp内部某一个界面
 * Created by 杨操 on 2016/2/22.
 */
public class BannerSkip {
    public static String BANNER_SKIP_JSON="banner_json";
    private Context context;
    private String className;
    private String skipJaon;

    public BannerSkip(Context context, String className,String skipJaon) {
        this.context = context;
        this.className=className;
        this.skipJaon=skipJaon;
    }

    public void goSkip(){
        try {
            Intent intent = new Intent(context, Class.forName("com.cardholder.adwlf." + className));
            intent.putExtra(BANNER_SKIP_JSON,skipJaon);
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            Toast.makeText(context,"请安装支持此功能的新版本",Toast.LENGTH_LONG).show();
        }

    }

}
