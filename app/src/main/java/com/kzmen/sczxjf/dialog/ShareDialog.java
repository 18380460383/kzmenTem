package com.kzmen.sczxjf.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.view.ExPandGridView;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;


public class ShareDialog {
    private Context context;
    private AlertDialog dialog;
    private ExPandGridView gridView;
    private RelativeLayout cancelButton;
    private SimpleAdapter saImageItems;
    private int[] image = {R.drawable.icon_weibo, R.drawable.icon_wechat, R.drawable.icon_pyq};
    private String[] name = {"新浪微博", "微信", "微信朋友圈"};
    private String share_title;
    private String share_des;
    private String share_image;
    private String share_linkurl;

    public ShareDialog(Context context, String share_title, String share_des, String share_image, String share_linkurl) {
        initDialog(context);
        this.share_title = share_title;
        this.share_des = share_des;
        this.share_image = share_image;
        this.share_linkurl = share_linkurl;
    }

    private void initDialog(Context context) {
        this.context = context;
        dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.kz_share_dialog);
        gridView = (ExPandGridView) window.findViewById(R.id.share_gridView);
        cancelButton = (RelativeLayout) window.findViewById(R.id.share_cancel);
        List<HashMap<String, Object>> shareList = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < image.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", image[i]);//添加图像资源的ID
            map.put("ItemText", name[i]);//按序号做ItemText
            shareList.add(map);
        }
        saImageItems = new SimpleAdapter(context, shareList, R.layout.kz_share_item, new String[]{"ItemImage", "ItemText"}, new int[]{R.id.imageView1, R.id.textView1});
        gridView.setAdapter(saImageItems);
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (name[position]) {
                    case "新浪微博":
                        showShare(0);
                        break;
                    case "微信":
                        showShare(1);
                        break;
                    case "微信朋友圈":
                        showShare(2);
                        break;
                }
            }
        });
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 宽度设置为屏幕的0.8
        window.setAttributes(lp);
    }

    public void showShare(int type) {
        switch (type) {
            case 0:
                OnekeyShare oks = new OnekeyShare();
                oks.setImageUrl(share_image);
                oks.setTitleUrl(share_linkurl);
                oks.setText(share_des);
                oks.setTitle(share_title);
                oks.setPlatform(SinaWeibo.NAME);
                oks.show(context);
                break;
            case 1:
                shareText(1);
                break;
            case 2:
                shareText(2);
                break;
        }

    }

    public static IWXAPI api;
    // 文本分享
    private void shareText(int type) {
        api = WXAPIFactory.createWXAPI(context, Constants.APP_ID, true);
        // 向微信终端注册你的id
        api.registerApp(Constants.APP_ID);
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = share_linkurl;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = share_title;
        msg.description = share_des;
        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
        req.message = msg;
        // 分享或收藏的目标场景，通过修改scene场景值实现。
        // 发送到聊天界面 —— WXSceneSession
        // 发送到朋友圈 —— WXSceneTimeline
        // 添加到微信收藏 —— WXSceneFavorite
        switch (type) {
            case 1:
                req.scene = SendMessageToWX.Req.WXSceneSession;
                break;
            case 2:
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                break;
        }
        // 调用api接口发送数据到微信
        api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public void setCancelButtonOnClickListener(View.OnClickListener Listener) {
        cancelButton.setOnClickListener(Listener);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        gridView.setOnItemClickListener(listener);
    }


    /**
     * 关闭对话框
     */
    public void dismiss() {
        dialog.dismiss();
    }
}