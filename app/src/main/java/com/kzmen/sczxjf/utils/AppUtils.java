package com.kzmen.sczxjf.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.request.PublicParameter;
import com.kzmen.sczxjf.ebean.City;
import com.kzmen.sczxjf.ebean.Province;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/11/5.
 */
public class AppUtils {
    public static void setNullListView(Adapter adapter, LinearLayout bjLL, ImageView iv, int rid, TextView bj_title, String str, int itemnum) {
        if (adapter!=null&&adapter.getCount() > itemnum) {
            bjLL.setVisibility(View.GONE);
        } else {
            iv.setImageResource(rid);
            bj_title.setText(str);
            bjLL.setVisibility(View.VISIBLE);
        }
    }

    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    public static RequestParams getParmObj(Map<String, Object> map) {
        map.putAll(PublicParameter.getPublicParameter(AppContext.getInstance()).getMap());
        String str = new JSONObject(map).toString();
        str = str.replace("\"[", "[");
        str=str.replace("]\"", "]");
        System.out.println("没加密之前" + str);
        String s = DESUtils.ebotongEncrypto(str);
        System.out.println("加密之后" + s);
        System.out.println("解密之后" + DESUtils.ebotongDecrypto(s));
        RequestParams requestparams = new RequestParams();
        requestparams.add("data", s);
        return requestparams;
    }

    public static RequestParams getParm(Map<String, String> map) {
        map.putAll(PublicParameter.getPublicParameter(AppContext.getInstance()).getMap());
        String str = new JSONObject(map).toString();
        System.out.println("没加密之前" + str);
        String s = DESUtils.ebotongEncrypto(str);
        System.out.println("加密之后" + s);
        System.out.println("解密之后" + DESUtils.ebotongDecrypto(s));
        RequestParams requestparams = new RequestParams();
        requestparams.add("data", s);
        return requestparams;
    }

    public static RequestParams getParmBean(Object map) {
        String s = null;
        try {
            String json = map.toString();
            String str = new JSONObject(json).toString();
//            System.out.println("没加密" + str);
            s = DESUtils.ebotongEncrypto(str);
//            System.out.println("加密之后" + s);
//            System.out.println("解密之后" + DESUtils.ebotongDecrypto(s));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestParams requestparams = new RequestParams();
        requestparams.add("data", s);
        return requestparams;
    }

    public static void startTime(final Handler handler, final TextView tv) {
        tv.setEnabled(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 60; i >= 0; i--) {
                    final int j = i;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (j == 0) {
                                tv.setText("获取验证码");
                                tv.setEnabled(true);

                            } else
                                tv.setText(j + "秒后获取");
                        }
                    });
                }


            }
        }).start();

    }

    public static int getAppVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            return 0;
        }
    }
    public static String getAppVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            return "未知";
        }
    }

    public static StringBuffer getAdrress(String province, String city, String area) {
        StringBuffer addresstxt = new StringBuffer();
        addresstxt.append(province);
        if (!province.equals(city) && !"不限".equals(city)) {
            addresstxt.append("-" + city);
        }
        if (!city.equals(area) && !"不限".equals(area)) {
            addresstxt.append("-" + area);
        }
        return addresstxt;
    }

    public static List<Province> getAddressMes(final byte[] bytes, final Context context,int i) {
        List<Province> listp = new ArrayList<Province>();
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.city);
            int available = inputStream.available();
            byte[] b = new byte[available];
            inputStream.read(b);
            String json = new String(b);
            JSONObject body = new JSONObject(json);
            if (body.optInt("code") == 1) {
                JSONObject data = body.optJSONObject("data");//取出所有省
                Iterator<String> keys = data.keys();
                Province p = null;
                City city = null;
                List<City> cityList = null;
                if(i==1) {
                    p = new Province();
                    p.setItem("不限");
                    city = new City();
                    city.setItem("不限");
                    ArrayList<String> areaList1 = new ArrayList<>();
                    areaList1.add("不限");
                    city.setAreaList(areaList1);
                    ArrayList<City> listCity = new ArrayList<>();
                    listCity.add(city);
                    p.setListCity(listCity);
                    listp.add(p);
                }
                while (keys.hasNext()) {
                    p = new Province();
                    cityList = new ArrayList<City>();
                    JSONObject provinceJson = data.optJSONObject(keys.next());//取出某一个省
                    p.setItem(provinceJson.optString("item"));
                    JSONObject citys = provinceJson.optJSONObject("city");//取出所有的市
                    Iterator<String> cityKeys = citys.keys();
                    List<String> listarea = null;
                    if(i==1){
                        city = new City();
                        city.setItem("不限");
                        ArrayList<String> areaList2 = new ArrayList<>();
                        areaList2.add("不限");
                        city.setAreaList(areaList2);
                        cityList.add(city);
                    }
                    p.setListCity(cityList);
                    while (cityKeys.hasNext()) {
                        JSONObject jsonCity = citys.optJSONObject(cityKeys.next());//取出某一个市
                        if (null != jsonCity) {
                            city = new City();
                            city.setItem(jsonCity.optString("item") + "");
                            JSONObject area = jsonCity.optJSONObject("area");//取出当前市的所有地区
                            listarea = new ArrayList<String>();
                            if(i==1){
                                listarea.add("不限");
                            }
                            if (null != area) {
                                Iterator<String> areaKeys = area.keys();
                                while (areaKeys.hasNext()) {
                                    listarea.add(area.optString(areaKeys.next()));//获得某一个地区
                                }
                            }
                            city.setAreaList(listarea);

                        }
                        cityList.add(city);//每次加一个市
                    }

                    listp.add(p);//每次结束添加一个省
                }
                return listp;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  listp;
    }
    public static  List<String> getArea(String pro, String city,List<Province> listp) {
        List<String> list = null;
        int size = listp.size();
        for (int i = 0; i < size; i++) {
            Province province = listp.get(i);
            if (province.getItem().equals(pro)) {
                List<City> listCity = province.getListCity();
                int size1 = listCity.size();
                for (int j = 0; j < size1; j++) {
                    City city1 = listCity.get(j);
                    if (city1.getItem().equals(city)) {
                        list = city1.getAreaList();
                    }
                }

                return list;
            }
        }
        return null;
    }

    public static List<String> getTown(String item,List<Province> listp) {
        List<String> list = new ArrayList<String>();
        int size = listp.size();
        for (int i = 0; i < size; i++) {
            Province province = listp.get(i);
            if (province.getItem().equals(item)) {
                List<City> listCity = province.getListCity();
                int size1 = listCity.size();
                for (int j = 0; j < size1; j++) {
                    list.add(listCity.get(j).getItem());
                }

                return list;
            }
        }
        return list;
    }

    public static  Boolean judegPhon(String phoneNum,String regex){
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(phoneNum);
        return  matcher.find();
    }

    public static String dealWithNUm(int i){
        if(i>100000000){
            int a1 = i / 100000000;
            int a2 = i % 100000000;
            int a3 = a2 / 10000000;
            return a1+"."+a3+"亿";
        }else if(i>10000){
            int i1 = i/10000;
            int i2=i%10000;
            int i3 = i2/1000;
            return i1+"."+i3+"万";
        }else{
            return i+"";
        }

    }
    /*
            * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
    */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /*
            * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
    */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
