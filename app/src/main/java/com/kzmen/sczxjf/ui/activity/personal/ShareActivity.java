package com.kzmen.sczxjf.ui.activity.personal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.Config;
import com.kzmen.sczxjf.bean.ShareMsgbean;
import com.kzmen.sczxjf.control.CustomProgressDialog;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.util.EshareLoger;
import com.kzmen.sczxjf.utils.FileUtils;
import com.kzmen.sczxjf.utils.JsonUtils;
import com.kzmen.sczxjf.view.EshareDialogFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author wu
 */
public class ShareActivity extends SuperActivity {

    @InjectView(R.id.wechat_friends)
    ImageButton wechat_friends;
    @InjectView(R.id.wechat_moments)
    ImageButton wechat_moments;
    @InjectView(R.id.wechat_favorites)
    ImageButton wechat_favorites;
    @InjectView(R.id.share_iv_code)
    public ImageView iv_code;
    @InjectView(R.id.hint_share)
    public TextView hintShare;
    private IWXAPI api;
    /*分享的链接*/
    private String url_share;
    private CustomProgressDialog dialog;
    private Bitmap bitmap_code;
    private AsyncHttpClient client;
    private ShareMsgbean shareMsgbean;
    private BitmapFactory.Options options = new BitmapFactory.Options();


    private Bitmap bitmap;


    @Override
    public void onCreateDataForView() {
        setTitle(R.id.share_title,"分享应用");
        initView();
        client = new AsyncHttpClient();
        getShareMsg();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_share);
    }

    private void initView() {
        initWeixin();
        iv_code.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final EshareDialogFragment fragment = new EshareDialogFragment();
                String text = "确定保存图片？";
                fragment.setViewUI(text, "确定", "取消", new EshareDialogFragment.EshareDialogClickListener() {
                    @Override
                    public void onClick() {
                        fragment.dismiss();
                        savePicture();
                    }

                    @Override
                    public void onCancel() {
                        fragment.dismiss();
                    }
                });
                fragment.show(getFragmentManager(), "save");
                return false;
            }
        });
    }


    @OnClick({ R.id.wechat_friends, R.id.wechat_moments,
            R.id.wechat_favorites})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.wechat_friends:
                share(0);
                break;
            case R.id.wechat_moments:
                share(1);
                break;
            case R.id.wechat_favorites:
                share(1);
                break;
        }
    }

    private void initWeixin() {
        if(api == null) {
            api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        }
        api.registerApp(Constants.APP_ID);
    }

    public void share(int flag) {
        /*WXWebpageObject webpage = new WXWebpageObject();
//        webpage.webpageUrl = "http://xiangyixia.360netnews.com/app.html?from=timeline&isappinstalled=1";
        if(null==shareMsgbean|| TextUtils.isEmpty(shareMsgbean.getWeixin().getShare())){
            webpage.webpageUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1beb00cf4c98140e&redirect_uri=http://jzz.360netnews.com/weixin/reg.php?uid=31&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
        }else{
            webpage.webpageUrl=setshareUrl(shareMsgbean.getWeixin().getShare());

        }
        WXMediaMessage msg = new WXMediaMessage(webpage);
        if(null==shareMsgbean|| TextUtils.isEmpty(shareMsgbean.getWeixin().getTitle())) {
            msg.title = "创新营销，跨卡掌门革";
        }else{
            msg.title =shareMsgbean.getWeixin().getTitle();
            msg.description=shareMsgbean.getWeixin().getDes();
        }
        if(null == bitmap){
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds=true;
            BitmapFactory.decodeResource(getResources(),
                    R.mipmap.ic_launcher, opts);
            opts.inJustDecodeBounds=false;
            opts.inSampleSize=opts.outWidth/70;
            Bitmap thumb = BitmapFactory.decodeResource(getResources(),
                    R.mipmap.ic_launcher, opts);
            msg.setThumbImage(thumb);
        } else {
            msg.setThumbImage(bitmap);
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag;
        if(api.sendReq(req)){
            Toast.makeText(this,"即将跳转到微信",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"无法访问到微信",Toast.LENGTH_SHORT).show();
        }*/
    }
    private void getShareMsg(){
        showProgressDialog("获取分享标题中");
        RequestParams params = new RequestParams();
        params.add("uid",AppContext.getInstance().getPEUser().getUid());
        NetworkDownload.jsonGetForCode1(this, Constants.URL_GET_USER_SHARE, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                shareMsgbean = JsonUtils.getBean(jsonObject.optJSONObject("data"), ShareMsgbean.class);
                System.out.println(shareMsgbean.getPicUrl());
                hintShare.setText(shareMsgbean.getPicTitle());
                getShareBitmap(shareMsgbean.getWeixin().getLitpic());
                getCodeBitmap2(shareMsgbean.getPicUrl());
                dismissProgressDialog();
            }

            @Override
            public void onFailure() {
                dismissProgressDialog();
                finish();
            }
        });
    }

    public void getShareUrl() {
        dialog = new CustomProgressDialog(this);
        dialog.setText("加载中。。。");
        dialog.show();
        Config appConfig = AppContext.getInstance().getAppConfig();
        if(null!= appConfig){
            String share = appConfig.getPromap().get("share");
            setshareUrl(share);
            getCodeBitmap();
        }else {
            NetworkDownload.jsonPost(this, Constants.SERVER_API_CONFIG, null, new NetworkDownload.NetworkDownloadCallBackJson() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                    String url = jsonObject.optJSONObject("pro").optString("share");
                    if (url == null) {
                        url_share = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1beb00cf4c98140e&redirect_uri=http://jzz.360netnews.com/weixin/reg.php?uid=31&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
                    } else {
                        EshareLoger.logI("获取的分享链接为:" + url);
                        setshareUrl(url);
                    }
                    getCodeBitmap();

                }

                @Override
                public void onFailure() {
                    dismissDialog();
                    finish();
                }
            });
        }
    }

    private String setshareUrl(String url) {
        String uid = AppContext.getInstance().getPEUser().getUid();
        int start = url.indexOf('#');
        EshareLoger.logI("第一个#号的位置：" + start);
        String str_one = url.substring(start + 1);
        url = url.substring(0, start);
        EshareLoger.logI("切割后的字符串: " + str_one);
        int end = str_one.indexOf('#');
        str_one = str_one.substring(end + 1);
        url_share = url + uid + str_one;
        EshareLoger.logI("获取的分享链接: " + url_share);
        return  url_share;
    }

    /**
     * 获取二维码
     */
    public void getCodeBitmap() {
        String userTDC = AppContext.getInstance().getUserTDC();
        File f=null;
        if(null!=userTDC) {
             f = new File(userTDC);
        }
        if(null==userTDC||f==null||!f.exists()) {
            RequestParams params = new RequestParams();
            params.add("uid", AppContext.getInstance().getPEUser().getUid());
            NetworkDownload.jsonGetForCode1(this, Constants.URL_GET_QRCODE, params, new NetworkDownload.NetworkDownloadCallBackJson() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {

                }

                @Override
                public void onFailure() {

                }
            });
        }else{
            bitmap_code = BitmapFactory.decodeFile(userTDC);
            iv_code.setImageBitmap(bitmap_code);
        }
    }

    public void getCodeBitmap2(String url) {
        NetworkDownload.byteGet(this, url, null, new NetworkDownload.NetworkDownloadCallBackbyte() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                bitmap_code = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);
                iv_code.setImageBitmap(bitmap_code);
                dismissDialog();

            }

            @Override
            public void onFailure() {
                dismissDialog();
                finish();
            }
        });
    }

    public void dismissDialog() {
        if(dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 保存二维码图片
     */
    public void savePicture() {

        File file = FileUtils.getFile();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(bitmap_code != null) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file.getPath());
                bitmap_code.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.close();
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(file);
                intent.setData(uri);
                sendBroadcast(intent);
                String path = file.getPath();
                AppContext.getInstance().setUserTDC(path);
                Toast.makeText(ShareActivity.this, "保存图片完成,路径为:" + path, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                EshareLoger.logI("保存图片出错");
            }
        } else {
            Toast.makeText(ShareActivity.this, "还没有图片呢" , Toast.LENGTH_LONG).show();
        }

    }

    public void getShareBitmap(String url) {
        NetworkDownload.byteGet(this, url, null, new NetworkDownload.NetworkDownloadCallBackbyte() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                options.inJustDecodeBounds=true;
                BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length, options);
                options.inJustDecodeBounds=false;
                options.inSampleSize=options.outWidth/70;
                bitmap = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length,options);
            }

            @Override
            public void onFailure() {

            }
        });
    }
}
