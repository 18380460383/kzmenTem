package com.kzmen.sczxjf;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Process;

import com.kzmen.sczxjf.utils.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 当app出现错误时执行此类的方法
 * Created by 杨操 on 2016/1/11.
 */
public class AppException implements Thread.UncaughtExceptionHandler {
    public static final String TAG = "CrashHandler";
    private static AppException INSTANCE = new AppException();
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private Date date ;
    private boolean YES =false;



    private AppException() {
    }

    public static AppException getInstance() {
        return INSTANCE;
    }

    public void init(Context ctx) {
        mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(mDefaultHandler);
    }

    @Override
    public void uncaughtException(final Thread thread, Throwable ex) {
/*        if(YES){
            android.os.Process.killProcess(Process.myPid());
            System.exit(0);
        }
        YES =true;*/
        try {
            testSaveMessage(ex);
      /*  System.out.println("crash");

            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();

                builder = new AlertDialog.Builder(AppContext.getInstance().getOneActivity());
                builder.setTitle("提示").setCancelable(false)
                        .setMessage("程序崩溃了...").setNeutralButton("我知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("点击了按钮了 了了了");
                        System.exit(0);
                    }
                });
                alertDialog = builder.create();
                alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                alertDialog.show();
                    Looper.loop();
                }
            }.start();*/
        }catch (Exception e){
            android.os.Process.killProcess(Process.myPid());
            System.exit(0);
        } finally {
            System.out.println(Build.MODEL);
            if (Build.MODEL.contains("GT")) {
                android.os.Process.killProcess(Process.myPid());
                System.exit(0);

            }
        }
    }


    /**
     * 测试时保存错误提示的方法
     * @param ex
     */
    private void testSaveMessage(Throwable ex) {
        System.out.println("lllllllllllllllllllllllllllllllllll");
        File file = new File(FileUtils.getRootFile().getAbsolutePath() + "/error.txt");
        StackTraceElement[] stackTrace = ex.getStackTrace();
        StringBuffer message =new StringBuffer();
        date= new Date();
        Long time = date.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        message.append("------------"+sdf.format(new Date(System.currentTimeMillis()))+"--------------" + "\n" + ex.toString());
        int length = stackTrace.length;
        for(int i=0;i<length;i++){
            message.append("   "+stackTrace[i]+"\n");
        }
        try {
            FileOutputStream out=new FileOutputStream(file,true);
            out.write(message.toString().getBytes());
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
