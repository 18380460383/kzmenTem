package com.kzmen.sczxjf;

import android.app.Activity;
import android.content.Intent;

import com.kzmen.sczxjf.ui.activity.personal.FirstGuideActivity;
import com.kzmen.sczxjf.ui.activity.personal.GuideActivity;
import com.kzmen.sczxjf.ui.activity.personal.SetActivity;

/**
 * UI跳转管理
 */
public class UIManager {



    /**
     * 显示第一次引导页面
     */
    public static void showFirstGuideActivity(Activity activity) {
        Intent intent = new Intent(activity, FirstGuideActivity.class);
        activity.startActivity(intent);
    }


    /**
     * 显示使用说明页面
     */
    public static void showGuideActivity(Activity activity) {
        Intent intent = new Intent(activity, GuideActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 显示个人信息界面
     */
    public static void showPersonInfoActivity(Activity activity) {
        /*Intent intent = new Intent(activity, UserDetailInfoActivity.class);
        activity.startActivity(intent);*/
    }


    /**
     * 跳转到设置界面
     * @param activity
     */
    public static void showSetActivity(Activity activity) {
        Intent intent = new Intent(activity, SetActivity.class);
        activity.startActivity(intent);
    }



}
