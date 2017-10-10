package com.kzmen.sczxjf.util;

import android.content.Context;

import com.kzmen.sczxjf.base.PublishItem;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 说明：
 * note：
 * Created by FuPei
 * on 2015/12/14 at 11:54
 */
public class PublishUtils implements Serializable {

    /**整体web结构*/
    private String MODE_HTML = "<!DOCTYPE html><html><head lang=\"en\">" +
            "<meta name=\"apple-mobile-web-app-capable\" content=\"yes\">\n" +
            "<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\">\n" +
            "<meta name=\"format-detection\" content=\"telephone=no\">\n" +
            "<meta charset=\"UTF-8\">\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\"/>\n" +
            "<script type=\"text/javascript\" src=\"http://xiangyixia.360netnews.com/Public/static/jquery-2.0.3.min.js\"></script>\n" +
            "<script type=\"text/javascript\" src=\"http://xiangyixia.360netnews.com/Public/static/jQuery.autoIMG.min.js\"></script>\n";

    /**设置整体样式的Css*/
    private String MODE_CSS = "*{margin: 0 auto;padding: 0;}\n" +
            "body {background: #fafafa;font: 14px \"Microsoft Yahei\",Simsun,Arial;padding:15px 0px}\n" +
            ".body{width:100%; overflow:hidden; line-height:1.5}\n" +
            ".body div{padding:5px 0px}\n" +
            ".body img{ text-align:center; padding:5px 0px}\n" +
            ".body .nrk{ width:90%; margin:0 auto; overflow:hidden}";

    /**适配图片需要添加的js*/
    private String MODE_JS = "<script>" +
            "$(function(){\n" +
            "\t$(document).ready(function(){\n" +
            "\t\t$(\".nrk\").autoIMG();\n" +
            "\t});\n" +
            "\t$(window).resize(function(){\n" +
            "\t\t$(\".nrk\").autoIMG();\n" +
            "\t});\n" +
            "})\n" +
            "</script>\n";
    private String body;
    private String css;
    private String title;
    private List<PublishItem> data;
    private Context mContext;

    public List<PublishItem> getData() {
        return data;
    }

    public void setData(List<PublishItem> data) {
        this.data = data;
    }

    public String toHtml2() {
        String text = "";
        for(PublishItem item : data) {
            text += item.toHtml();
        }
        return text;
    }

    /**
     * 转出html
     * @return
     */
    public String toHtml() {
        String html = "";
        html += MODE_HTML;
        setCss();
        setBody();
        setTitle();
        html += title + "\n";
        html += css + "</head>";
        html += body + "</html>";
        return html;
    }

    public void setCss() {
        css = "<style>\n";
        css += MODE_CSS;
//        for(PublishItem item : data) {
//            css += item.toCSS();
//        }
        css += "</style>\n";
    }

    public void setBody() {
        body = "<body>\n" +
                "<div class=\"body\">\n" +
                "<div class=\"nrk\">\n";
        for(PublishItem item : data) {
            body += item.toHtml();
        }
        body += "</div></div>\n";
        body += MODE_JS;
        body += "</body>";
    }

    public void setTitle() {
        title = "<title>" + "推广链接" + "</title>";
    }

    /**
     * 生成网页文件
     * @param file
     * @return
     * @throws IOException
     */
    public boolean makeHtmlFile(File file) throws IOException {
        if(!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(toHtml().getBytes());
        outputStream.close();
        return true;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将信息转为 json
     * @return
     * @throws JSONException 单个信息转json失败
     */
    public String toJson() throws JSONException {
        JSONArray array = new JSONArray();
        for(PublishItem item : data) {
            array.put(item.toJson());
        }
        return array.toString();
    }

    public static PublishUtils parseJson(JSONArray array) throws JSONException {
        PublishUtils util = new PublishUtils();
        List<PublishItem> list = new ArrayList<>();
        PublishItem item;
        for(int i = 0; i < array.length(); i++) {
            item = PublishItem.parseJsonText(array.get(i).toString());
            list.add(item);
        }
        util.setData(list);
        return util;
    }

}
