package com.kzmen.sczxjf.broadcast;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.bean.request.PublicParameter;
import com.kzmen.sczxjf.ui.activity.personal.MainTabActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.jpush.android.api.JPushInterface;
import me.leolin.shortcutbadger.ShortcutBadgeException;
import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by Administrator on 2015/11/20.
 */
public class EshareBroadcast extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {

        //
        if(JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {



        }
        if(JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())){

                doMyThing(context, AppContext.getInstance().getMsgNum() + 1);
            /**
             * android 不支持在BroadcastReceiver弹出对话框
             */
/*            if(isAppInForeground2(context)){
                Bundle bundle = intent.getExtras();
                int id = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                JPushInterface.clearNotificationById(context,id);
                try {
                       final  Context c=context;
                   final JSONObject json= new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    System.out.println("type==5"+json);
                    if(json.optInt("type")==5){
                    AlertDialog.Builder builder=new AlertDialog.Builder(AppContext.getInstance())
                            .setTitle("提示")
                            .setMessage(json.getJSONObject("aps")
                                    .getString("alert"))
                            .setNeutralButton("去看看", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    JSONObject jsonObject = json.optJSONObject("news");
                                    if (jsonObject == null) {
                                        return;
                                    }
                                    try {
                                        if (jsonObject.getInt("jumptype") == 1) {
                                            Intent infWebIntent = new Intent(c, InformationWebviewActivity.class);
                                            infWebIntent.putExtra("url", jsonObject.optString("jumpurl"));
                                            infWebIntent.putExtra(InformationWebviewActivity.NID, jsonObject.optString("nid"));
                                            infWebIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            c.startActivity(infWebIntent);
                                        } else if (jsonObject.getInt("jumptype") == 0) {
                                            Intent infIntent = new Intent(c, InformationForDetails.class);
                                            infIntent.putExtra(InformationForDetails.NID, jsonObject.optString("nid"));
                                            infIntent.putExtra(InformationForDetails.TITLENAME, "详情");
                                            infIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            c.startActivity(infIntent);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    dialog.dismiss();
                                }
                            }).setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                        builder.show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }*/
        }
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Bundle extras = intent.getExtras();
            System.out.println(extras.getString(JPushInterface.EXTRA_EXTRA));
            JSONObject json=null;
            try {
                json= new JSONObject(extras.getString(JPushInterface.EXTRA_EXTRA));

            if (json.optInt("type")==1) {
                if (null != AppContext.maintabeactivity) {
                    AppContext.maintabeactivity.finish();
                }
                Intent i = new Intent(context.getApplicationContext(), MainTabActivity.class);  //自定义打开的界面
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }else if(json.optInt("type")==5){
                JSONObject jsonObject = json.optJSONObject("news");
                if(jsonObject==null){
                    return;
                }
                if(jsonObject.getInt("jumptype")==1){
                    /*Intent infWebIntent = new Intent(context, InformationWebviewActivity.class);
                    infWebIntent.putExtra("url", jsonObject.optString("jumpurl"));
                    infWebIntent.putExtra(InformationWebviewActivity.NID, jsonObject.optString("nid"));
                    infWebIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(infWebIntent);*/
                }else if(jsonObject.getInt("jumptype")==0){
                    /*Intent infIntent = new Intent(context, InformationForDetails.class);
                    infIntent.putExtra(InformationForDetails.NID,jsonObject.optString("nid"));
                    infIntent.putExtra(InformationForDetails.TITLENAME, "详情");
                    infIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(infIntent);*/
                }

            }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //TODO 监听网络状态变化
        if(ConnectivityManager.CONNECTIVITY_ACTION.endsWith(intent.getAction())){
            PublicParameter.getPublicParameter(context).setNetState();
        }
        //TODO 监听卸载应用
        if(Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())){

        }
    }

    /**
     * 消息传过来时，调用此方法
     */
    private void doMyThing(Context context,int num) {
        boolean exc=false;
        try {
            ShortcutBadger.setBadge(context, num);

        } catch (ShortcutBadgeException e) {
            exc=true;
        }
        /*if(!exc){
            JPushInterface.clearAllNotifications(AppContext.getInstance());
        }*/
        AppContext.getInstance().setMsgNum(num);
    }
    // need permission: <uses-permission android:name="android.permission.GET_TASKS" />
    public static boolean isAppInForeground2(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

}
