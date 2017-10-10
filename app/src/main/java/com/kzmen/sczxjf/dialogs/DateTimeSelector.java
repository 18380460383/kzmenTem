package com.kzmen.sczxjf.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.view.WheelView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 创建者：Administrator
 * 时间：2016/7/18
 * 功能描述：使用WheelView 做成的日期选择器
 */
public class DateTimeSelector {
    private Context context;
    private int LINE=5;
    private BackDateTime backDateTime;
    /**
     * 年份选择器
     */
    private WheelView wheelViewYear;
    /**
     * 天数选择器
     */
    private WheelView wheelViewDay;
    /**
     * 月份选择器
     */
    private WheelView wheelViewMonth;
    /**
     * 小时选择器
     */
    private WheelView wheelViewHous;
    /**
     * 分钟选择器
     */
    private WheelView wheelViewMinute;

    public DateTimeSelector(Context context,BackDateTime backDateTime) {
        this.context = context;
        this.backDateTime=backDateTime;
    }

    public void setLINE(int LINE) {
        this.LINE = LINE;
    }

    /**
     * 创建整个选择器
     * @return
     */
    public  AlertDialog.Builder creatDialoge(){
        AlertDialog.Builder dialog=new AlertDialog.Builder(context);
        LinearLayout view = new LinearLayout(context);
        view.setOrientation(LinearLayout.HORIZONTAL);
        view.setGravity(Gravity.CENTER_VERTICAL);
        Calendar presentTime = getPresentTime();
        wheelViewYear = addYear(view, presentTime);
        TextView childg1 = new TextView(context);
        childg1.setText("-");
        childg1.setGravity(Gravity.CENTER);
        view.addView(childg1);
        wheelViewMonth = addMonth(view, presentTime);
        TextView childg2 = new TextView(context);
        childg2.setText("-");
        childg2.setGravity(Gravity.CENTER);
        view.addView(childg2);
        wheelViewDay = addDay(view, presentTime);
        wheelViewMonth.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                int i = selectedIndex;
                ArrayList<String> data = new ArrayList<>();
                if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12) {
                    for (int j = 1; j <= 31; j++) {
                        data.add(j + "");
                    }
                } else if (i != 2) {
                    for (int j = 1; j < 31; j++) {
                        data.add(j + "");
                    }
                } else if (i == 2) {
                    Integer integer = Integer.valueOf(wheelViewYear.getSeletedItem());
                    if (integer % 4 == 0 && integer % 100 != 0 || integer % 400 == 0) {
                        for (int j = 1; j <= 29; j++) {
                            data.add(j + "");
                        }
                    } else {
                        for (int j = 1; j < 29; j++) {
                            data.add(j + "");
                        }
                    }
                }
                int seletedIndex = wheelViewDay.getSeletedIndex();
                wheelViewDay.setItems(data);
                wheelViewDay.setSeletion(selectedIndex);
            }
        });
        wheelViewHous = addHous(view, presentTime);
        TextView child = new TextView(context);
        child.setText(":");
        child.setGravity(Gravity.CENTER);
        view.addView(child);
        wheelViewMinute = addMinute(view, presentTime);
        dialog.setView(view);
        dialog.setNeutralButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Integer year = Integer.valueOf(wheelViewYear.getSeletedItem());
                    Integer month = Integer.valueOf(wheelViewMonth.getSeletedItem());
                    Integer day = Integer.valueOf(wheelViewDay.getSeletedItem());
                    Integer hous = Integer.valueOf(wheelViewHous.getSeletedItem());
                    Integer minute = Integer.valueOf(wheelViewMinute.getSeletedItem());
                    DateTimeSelector.this.backDateTime.back(year, month, day, hous, minute);
                }catch (Exception e){

                }
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return dialog;
    }

    private WheelView addMinute(LinearLayout view, Calendar presentTime) {
        int i = presentTime.get(Calendar.MINUTE);
        WheelView child = new WheelView(context);
        /*LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight=1;
        child.setLayoutParams(params);*/
        child.setBackgroundResource(R.color.text_hei);
        ArrayList<String> data = new ArrayList<>();
        for(int j=0;j<=60;j++){
            data.add(j+"");
        }
        child.setItems(data);
        child.setSeletion(i);
        view.addView(child);
        return child;
    }

    private WheelView addHous(LinearLayout view, Calendar presentTime) {
        int i = presentTime.get(Calendar.HOUR_OF_DAY);
        WheelView child = new WheelView(context);
        child.setBackgroundResource(R.color.text_hei);
        /*LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight=1;
        child.setLayoutParams(params);*/
        ArrayList<String> data = new ArrayList<>();
        for(int j=0;j<=24;j++){
            data.add(j+"");
        }
        child.setItems(data);
        child.setSeletion(i);
        view.addView(child);
        return child;
    }

    private WheelView addDay(LinearLayout view, Calendar presentTime) {
        int i = presentTime.get(Calendar.DAY_OF_MONTH);
        int month = presentTime.get(Calendar.MONTH)+1;
        WheelView child = new WheelView(context);
        child.setBackgroundResource(R.color.text_hei);
        ArrayList<String> data = new ArrayList<>();
        if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){
            for(int j=1;j<=31;j++){
                data.add(j+"");
            }
        }else if(month!=2){
            for(int j=1;j<31;j++){
                data.add(j+"");
            }
        }else if(month==2){
            int i1 = presentTime.get(Calendar.YEAR);
            if(i1 % 4 == 0 && i1 % 100 != 0 || i1 % 400 == 0){
                for(int j=1;j<=29;j++){
                    data.add(j+"");
                }
            }else {
                for(int j=1;j<29;j++){
                    data.add(j+"");
                }
            }
        }
        child.setItems(data);
        child.setSeletion(i);
        view.addView(child);
        return child;
    }

    private WheelView addMonth(LinearLayout view, Calendar presentTime) {
        int i = presentTime.get(Calendar.MONTH);
        WheelView child = new WheelView(context);
        child.setBackgroundResource(R.color.text_hei);
        ArrayList<String> data = new ArrayList<>();
        for(int j=1;j<13;j++){
            data.add(j+"");
        }
        child.setItems(data);
        child.setSeletion(i);
        view.addView(child);
        return child;
    }

    private WheelView addYear(LinearLayout view,Calendar presentTime) {
        int i = presentTime.get(Calendar.YEAR);
        WheelView child = new WheelView(context);
        child.setBackgroundResource(R.color.text_hei);
        ArrayList<String> data = new ArrayList<>();
        for(int j=i;j<=2049;j++){
            data.add(j+"");
        }
        child.setItems(data);
        view.addView(child);
        return child;
    }

    /**
     * 获取当前时间的毫秒数
     * @return
     */
    public Calendar getPresentTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis() + 1000 * 3600 * 24 * 2));
        return calendar;
    }
    public interface  BackDateTime{
        void back(int year,int month,int day,int hous,int minute);
    }
}
