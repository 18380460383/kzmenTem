package com.kzmen.sczxjf.utils;

import android.text.TextUtils;
import android.util.Log;

import com.kzmen.sczxjf.bean.Config;
import com.google.gson.Gson;
import com.kzmen.sczxjf.bean.entitys.EnComboBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/30.
 */
public class JsonUtils {

    /**
     * 根据一个jsonBean 生成一个javaBean
     * @param jsonBean
     * @param cla
     * @param <T>
     * @return
     */
    public static <T>T getBean(JSONObject jsonBean, Class<T> cla) {

        Gson g = new Gson();
            T t=null;
        try {
            t = g.fromJson(jsonBean.toString(), cla);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("错误数据"+jsonBean);
        }
            return t;
    }

    /**
     * 更具一个JSONArray生成对应的javaBean集合
     * @param jsonBeanArray
     * @param cla
     * @param <T>
     * @return
     */
    public static  <T>List<T> getBeanList(JSONArray jsonBeanArray, Class<T> cla) {
        List<T> list = new ArrayList<T>();
                if(null==jsonBeanArray){
                    return list;
                }
        int length = jsonBeanArray.length();
        System.out.println("解析长度"+length);

        Gson g = new Gson();
        for (int i = 0; i < length; i++) {
        try {
            String aNull = jsonBeanArray.get(i).toString();
            System.out.println(aNull);
            T object = g.fromJson(aNull, cla);
                Log.i("tag", object.toString());
                list.add(object);
        } catch (JSONException e) {
            e.printStackTrace();
            continue;
        }catch(Exception  e){
            e.printStackTrace();
            System.out.println("出错");
        }
        }
        return list;
    }

    /**
     * 根据JSONObject解析Config
     * @param jsonObject
     * @return
     */
    public static Config getConfig( JSONObject jsonObject){
        Config config = new Config();
        try {
            config.setVer(jsonObject.optString("ver"));
            JSONObject pro = jsonObject.optJSONObject("pro");
            Iterator<String> keys = pro.keys();
            Map<String, String> promap = new HashMap<>();
            while (keys.hasNext()) {
                String next = keys.next();
                promap.put(next, pro.optString(next));
            }
            config.setPromap(promap);
            JSONObject dev = jsonObject.optJSONObject("dev");
            Iterator<String> keys1 = dev.keys();
            Map<String, String> devmap = new HashMap<>();
            while (keys1.hasNext()) {
                String next = keys1.next();
                devmap.put(next, dev.optString(next));
            }
            config.setDevmap(devmap);
        }catch(Exception e){
            System.out.println("Config数据出错");
        }
        return config;
    }

    /**根据json解析出一个经过排序的套餐集合
     * @param data
     * @return
     */
    public static List<EnComboBean> getComboBeanList(String data){
        if(TextUtils.isEmpty(data)){
            return null;
        }
        List<EnComboBean> enComboBeanList=new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()){
                JSONArray jsonArray = jsonObject.optJSONArray(keys.next());
                int length = jsonArray.length();
                for(int i=0;i<length;i++){
                    EnComboBean bean = getBean(jsonArray.getJSONObject(i), EnComboBean.class);
                    if(bean!=null){
                        enComboBeanList.add(bean);
                    }
                }
            }
            Collections.sort(enComboBeanList, new Comparator<EnComboBean>() {

                /*
                 * int compare(Student o1, Student o2) 返回一个基本类型的整型，
                 * 返回负数表示：o1 小于o2，
                 * 返回0 表示：o1和o2相等，
                 * 返回正数表示：o1大于o2。
                 */
                public int compare(EnComboBean o1, EnComboBean o2) {

                    //按照学生的年龄进行升序排列
                    if (o1.getOrder_no() < o2.getOrder_no()) {
                        return 1;
                    }
                    if (o1.getOrder_no() == o2.getOrder_no()) {
                        return 0;
                    }
                    return -1;
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
            return enComboBeanList;

    }    /**根据json解析出一个经过排序的套餐Map
     * @param data
     * @return
     */
    public static Map<String,List<EnComboBean>> getComboBeanMapList(String data){
        if(TextUtils.isEmpty(data)){
            return null;
        }
        Map<String,List<EnComboBean>> map=new HashMap<>();

        try {
            JSONObject jsonObject = new JSONObject(data);
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()){
                List<EnComboBean> enComboBeanList=new ArrayList<>();
                String next = keys.next();
                JSONArray jsonArray = jsonObject.optJSONArray(next);
                int length = jsonArray.length();
                for(int i=0;i<length;i++){
                    EnComboBean bean = getBean(jsonArray.getJSONObject(i), EnComboBean.class);
                    if(bean!=null){
                        enComboBeanList.add(bean);
                    }
                }
                Collections.sort(enComboBeanList, new Comparator<EnComboBean>() {

                    /*
                     * int compare(Student o1, Student o2) 返回一个基本类型的整型，
                     * 返回负数表示：o1 小于o2，
                     * 返回0 表示：o1和o2相等，
                     * 返回正数表示：o1大于o2。
                     */
                    public int compare(EnComboBean o1, EnComboBean o2) {

                        //按照学生的年龄进行升序排列
                        if (o1.getOrder_no() < o2.getOrder_no()) {
                            return 1;
                        }
                        if (o1.getOrder_no() == o2.getOrder_no()) {
                            return 0;
                        }
                        return -1;
                    }
                });
                map.put(next,enComboBeanList);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
            return map;

    }

}
