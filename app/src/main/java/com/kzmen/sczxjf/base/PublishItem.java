package com.kzmen.sczxjf.base;


import android.graphics.Typeface;
import android.view.Gravity;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.util.EshareLoger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 说明：添加推广信息的单个数据
 * note：
 * Created by FuPei
 * on 2015/12/7 at 19:28
 */
public class PublishItem implements Serializable {

    /**
     * html标签列表
     */
    public final String TAG_SIZE = "font-size";
    public final String TAG_COLOR = "color";
    public final String TAG_BOLD = "font-weight";
    public final String TAG_ALIGN = "text-align";

    /**
     * 图片类型的type编号
     */
    public static final int TYPE_IMG = 2;
    /**
     * 文本类型的type编号
     */
    public static final int TYPE_TEXT = 1;
    /**
     * 默认文字的颜色
     */
    public final int DEFAULT_COLOR = R.color.default_text;
    /**
     * 默认文字的大小
     */
    public final float DEFAULT_SIZE = 18;
    /**
     * 默认对齐方式
     */
    public final int DEFAULT_ALIGN = Gravity.LEFT;
    /**
     * 按键的数量
     */
    private final int COUNT_TYPE = 6;

    /**
     * 类型
     */
    public enum Type {
        IMG, TEXT
    }

    private Type type;
    private String id;
    private String value;
    private int textColor;
    private float textSize;
    private int textAlign;
    private int textType;

    public PublishItem(String id, String value, Type type) {
        this.id = id;
        this.value = value;
        this.type = type;
    }

    public int getTextAlign() {
        if (textAlign == 0) {
            return DEFAULT_ALIGN;
        }
        return textAlign;
    }

    public void setTextAlign(int textAlign) {
        this.textAlign = textAlign;
    }

    public int getTextType() {
        return textType;
    }

    public void setTextType(int textType) {
        this.textType = textType;
    }

    public int getTextColor() {
        if (textColor == 0) {
            return DEFAULT_COLOR;
        }
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        if (textSize == 0) {
            return DEFAULT_SIZE;
        }
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        try {
            return toJson().toString();
        } catch (JSONException e) {
            return "";
        }
    }

    /**
     * 将颜色转成 Html的颜色格式
     *
     * @param color View获取的文本颜色
     * @return Html颜色
     */
    public static String colorToHexString(int color) {
        String result = String.format("#%06X", 0xFFFFFFFF & color);
        return "#" + result.substring(3);
//        return result;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", getId());
        if (getType() == Type.IMG) {
            json.put("type", TYPE_IMG);
            json.put("url", getValue());
        } else {
            json.put("type", TYPE_TEXT);
            json.put("content", getValue());
            JSONArray array = new JSONArray();
            for (int i = 0; i < COUNT_TYPE; i++) {
                array.put(setTypeData(i + 1));
            }
            json.put("style", array);
        }
        return json;
    }

    public JSONObject setTypeData(int index) throws JSONException {
        JSONObject json = new JSONObject();

        switch (index) {
            /*case AddTextActivity.TYPE_SIZE:
                json.put("type", index);
                json.put("value", getTextSize());
                break;
            case AddTextActivity.TYPE_COLOR:
                json.put("type", index);
                json.put("value", colorToHexString(getTextColor()));
                break;
            case AddTextActivity.TYPE_CHU:
                json.put("type", index);
                if (getTextType() == 0) {
                    json.put("value", "2");
                } else {
                    json.put("value", getTextType());
                }
                break;
            case AddTextActivity.TYPE_ALIGN_LEFT:
                json.put("type", AddTextActivity.TYPE_ALIGN_LEFT);
                if (getTextAlign() == Gravity.CENTER_HORIZONTAL) {
                    json.put("value", "2");
                } else if (getTextAlign() == Gravity.RIGHT) {
                    json.put("value", "3");
                } else if (getTextAlign() == Gravity.LEFT) {
                    json.put("value", "1");
                }
                break;*/
            default:
                json.put("type", index);
                json.put("value", "");
                break;
        }
        return json;
    }

    /**
     * 将对象转为html
     *
     * @return
     */
    public String toHtml() {
        StringBuffer html = new StringBuffer();

        if (getType() == Type.TEXT) {
            html.append("<div>");
            html.append("<p ");
            html.append("id=").append(getId())
                    .append(" style=").append(toCSS()).append(">")
                    .append(toHtmlText(getValue())).append("</p></div>\n");
        } else {
            html.append("<div");
            html.append(" style=").append("\"text-align: center\">");
            html.append("<img ");
            html.append("id=").append(getId())
                    .append(" src=").append(getValue())
                    .append(">").append("</img></div>\n");
        }
        return html.toString();
    }

    public String toHtmlText(String text) {
        String htmltext = "";
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '\n') {
                htmltext += "<br>";
            } else {
                htmltext += text.charAt(i);
            }
        }
        return htmltext;
    }


    /**
     * 提取 CSS
     *
     * @return
     */
    public String toCSS() {
        StringBuffer css = new StringBuffer();
        if (getType() == Type.IMG) {
            return "";
        } else {
            int size = (int) getTextSize();
            css.append("\"");
            css.append(TAG_SIZE).append(":").append(size).append("px;");
            css.append(TAG_COLOR).append(":").append(colorToHexString(getTextColor())).append(";");
            if (getTextType() == Typeface.BOLD) {
                css.append(TAG_BOLD).append(":").append("bold").append(";");
            }
            String align;
            EshareLoger.logI("align:" + getTextAlign());
            if (getTextAlign() == Gravity.RIGHT) {
                align = "right";
            } else if (getTextAlign() == Gravity.CENTER_HORIZONTAL) {
                align = "center";
            } else {
                align = "left";
            }
            css.append(TAG_ALIGN).append(":").append(align).append("\n");
        }
        css.append("\"");
        return css.toString();
    }

    /**
     * 将json转此对象
     *
     * @return
     */
    public static PublishItem parseJsonText(String text) throws JSONException {
        JSONObject json = new JSONObject(text);
        PublishItem item;
        String value;
        String id = json.optString("id");
        if (json.getInt("type") == TYPE_TEXT) {
            value = json.optString("content");
            item = new PublishItem(id, value, Type.TEXT);
            item.setValue(value);
            item.setId(id);
            JSONArray array = json.optJSONArray("style");
            for (int i = 0; i < array.length(); i++) {
                json = array.getJSONObject(i);
                toTypeData(item, json);
            }
        } else if (json.getInt("type") == TYPE_IMG) {
            value = json.optString("url");
            item = new PublishItem(id, value, Type.IMG);
            item.setValue(value);
            item.setId(id);
        } else {
            item = null;
        }
        return item;
    }

    public static void toTypeData(PublishItem item, JSONObject currentjson) {
        int index = currentjson.optInt("type");
        String value = currentjson.optString("value");
        switch (index) {
            /*case AddTextActivity.TYPE_SIZE:
                item.setTextSize(Float.valueOf(value));
                break;
            case AddTextActivity.TYPE_COLOR:
                item.setTextColor(Color.parseColor(currentjson.optString("value")));
                break;
            case AddTextActivity.TYPE_CHU:
                if (value.equals("1")) {
                    item.setTextType(Typeface.BOLD);
                } else {
                    item.setTextType(Typeface.NORMAL);
                }
                break;
            case AddTextActivity.TYPE_ALIGN_LEFT:
                if (value.equals("2")) {
                    item.setTextAlign(Gravity.CENTER_HORIZONTAL);
                } else if (value.equals("3")) {
                    item.setTextAlign(Gravity.RIGHT);
                } else if (value.equals("1")) {
                    item.setTextAlign(Gravity.LEFT);
                }
                break;*/
        }
    }
}
