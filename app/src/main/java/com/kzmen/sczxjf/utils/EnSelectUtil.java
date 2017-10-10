package com.kzmen.sczxjf.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kzmen.sczxjf.EnMoneySelectEntity;
import com.kzmen.sczxjf.bean.entitys.MultiMediaEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * describe:资源列表选择资源的工具类
 * Created by FuPei on 2016/6/2.
 */
public class EnSelectUtil {

    /**
     * 保存到SharedPreference的KEY
     */
    private static final String KEY_LIST = "selectlist";

    /**
     * 将list保存到内存
     * @param list
     */
    public static void saveSelectList(List list) {

        List<MultiMediaEntity> mlist = list;
        List<MultiMediaEntity> savelist = getSelectList();
        for(MultiMediaEntity entity : mlist) {
            if(!savelist.contains(entity)) {
                savelist.add(entity);
            }
        }
        save(savelist);
    }

    /**
     * 获取保存的列表
     * @return
     */
    public static List<MultiMediaEntity> getSelectList() {
        String text = EnDataUtils.getValueByKey(KEY_LIST);
        if(text == null || text.trim().length() == 0) {
            return new ArrayList<>();
        } else {
            return new Gson().fromJson(text,
                    new TypeToken<List<MultiMediaEntity>>(){}.getType());
        }
    }

    /**
     * 将list持久化存储
     * @param list
     */
    private static void save(List<MultiMediaEntity> list) {
        EnDataUtils.saveValueByKey(KEY_LIST, new Gson().toJson(list));
    }

    public static boolean isExistEntity(MultiMediaEntity entity) {
        String identify = getEntity(entity).getIdentify();
        for(MultiMediaEntity en : getSelectList()) {
            if(getEntity(en).getIdentify().equals(identify)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 为列表添加一个资源
     * @param entity
     */
    public static void addEntity(MultiMediaEntity entity) {
        List<MultiMediaEntity> list = getSelectList();
        list.add(entity);
        save(list);
    }

    /**
     * 移除一个资源
     * @param entity
     */
    public static void removeEntity(MultiMediaEntity entity) {
        List<MultiMediaEntity> list = getSelectList();
        String identify = getEntity(entity).getIdentify();
        for(int i = 0; i < list.size(); i++) {
            if(getEntity(list.get(i)).getIdentify().equals(identify)) {
                list.remove(i);
                break;
            }
        }
        save(list);
    }

    /**
     * 清空列表
     */
    public static void clear() {
        EnDataUtils.saveValueByKey(KEY_LIST, null);
    }

    /**
     * 获取列表数据个数
     * @return
     */
    public static int getCount() {
        return getSelectList().size();
    }

    /**
     * 获取当前列表的总金额
     * @return
     */
    public static float getMoney() {
        float money = 0;
        for (MultiMediaEntity entity : getSelectList()) {
            money += getEntity(entity).getMoney();
        }
        return money;
    }

    /**
     * 获取资源属于的资源类型实例
     * @param entity
     * @return
     */
    private static EnMoneySelectEntity getEntity(MultiMediaEntity entity) {
        if(entity.getWeibo() != null) {
            return entity.getWeibo();
        }
        if(entity.getWeixin() != null) {
            return entity.getWeixin();
        }
        if(entity.getMedia() != null) {
            return entity.getMedia();
        }
        if(entity.getWriter() != null) {
            return entity.getWriter();
        }
        if(entity.getReporter() != null) {
            return entity.getReporter();
        }
        return null;
    }
}
