package com.kzmen.sczxjf.ui.activity.personal;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.request.UpdateMsg;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.AccountMessageActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.WebAcitivity;
import com.kzmen.sczxjf.util.EshareLoger;
import com.kzmen.sczxjf.utils.AppUtils;
import com.kzmen.sczxjf.utils.JsonUtils;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import cn.jpush.android.api.JPushInterface;

/**
 * 说明：设置界面
 * note：在此界面进入用户账户信息时，需传入在此界面获取的设置数据
 * Created by FuPei
 * on 2015/11/30 at 10:02
 */
public class SetActivity extends SuperActivity {

    @InjectView(R.id.activity_set_ly_about)
    public LinearLayout ly_about;
    @InjectView(R.id.activity_set_ly_account)
    public LinearLayout ly_account;
    @InjectView(R.id.activity_set_tb_msg)
    public ToggleButton tb_msg;
    @InjectView(R.id.activity_set_tv_version)
    public TextView tv_version;
    @InjectView(R.id.activity_set_ly_check)
    public LinearLayout ly_check;
    @InjectView(R.id.exit)
    public LinearLayout exit;

    /**
     * 设置的信息
     */
    public String info_set;
    public boolean currentSet;
    public JSONObject data;
    private AlertDialog show;
    private AlertDialog alertDialog;
    private DownloadChangeObserver downloadChangeObserver;
    private AlertDialog dialog1;

    @Override
    public void onCreateDataForView() {
        initData();
        setListener();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_set);
    }

    void initData() {
        setTitle(R.id.set_title, "设置");
        tv_version.setText(getAppVersionName());
        info_set = "";
        currentSet = false;
        data = null;
        getSetInfo();
    }

    void setListener() {
        SetOnClick onclick = new SetOnClick();
        ly_about.setOnClickListener(onclick);
        ly_account.setOnClickListener(onclick);
        ly_check.setOnClickListener(onclick);
        tb_msg.setOnClickListener(onclick);
        exit.setOnClickListener(onclick);
    }

    private class SetOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.activity_set_ly_about:
                    Intent intent1 = new Intent(SetActivity.this, WebAcitivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("title", "关于卡掌门");
                    bundle.putString("url", OkhttpUtilManager.URL_ABOUT);
                    intent1.putExtras(bundle);
                    startActivity(intent1);
                    break;
                case R.id.activity_set_ly_account:
                    Intent intent = new Intent(SetActivity.this, AccountMessageActivity.class);
                    startActivity(intent);
                    break;
                case R.id.title_back:
                    finish();
                    break;
                case R.id.activity_set_ly_check:
                    //update();
                    break;
                case R.id.activity_set_tb_msg:
                    EshareLoger.logI("currentSet = " + currentSet);
                    sendPushInfo(!currentSet);
                    break;
                case R.id.exit:
                    exitDialog();

                    break;
            }
        }
    }

    private void exitDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this).setTitle("提示").setMessage("是否退出当前个人账号").setNeutralButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppContext instance = AppContext.getInstance();
                instance.setPersonageOnLine(false);
                //instance.setUserLogin(null);
                AppContext.getInstance().setUserLoginOut();
                finish();
                AppContext.maintabeactivity.extP();
                dialog1.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog1.dismiss();
            }
        });
        dialog1 = alertDialog.create();
        alertDialog.show();

    }

    /**
     * 获取版本信息
     *
     * @return
     */
    public int getAppVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            return 0;
        }
    }

    public String getAppVersionName() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            return "未知";
        }
    }

    private void update() {
        showProgressDialog(null);
        RequestParams params = new RequestParams();
        params.add("type", "2");
        params.add("source", AppContext.getInstance().getChannel());
        NetworkDownload.byteGet(this, Constants.URL_UP, params, new NetworkDownload.NetworkDownloadCallBackbyte() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
                dismissProgressDialog();
                JSONObject jsonObject;
                try {
                    String json = new String(bytes);
                    System.out.println(json);
                    jsonObject = new JSONObject(json);
                    final UpdateMsg data = JsonUtils.getBean(jsonObject.optJSONObject("data"), UpdateMsg.class);
                    System.out.println("升级哈哈" + data);
                    if (AppUtils.getAppVersion(SetActivity.this) < data.getBate()) {
                        showUpdataDialog(jsonObject, data);
                    } else {
                        AppContext.getInstance().setDownId((long) 0);
                        Toast.makeText(SetActivity.this, "已经是最新版本", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure() {
                dismissProgressDialog();
            }
        });
    }


    /**
     * 获取设置信息
     */
    public void getSetInfo() {

        if (null != AppContext.getInstance().getUserMessageBean() && AppContext.getInstance().getUserMessageBean().getIsjpush().equals("1")) {
            tb_msg.setChecked(true);
            currentSet = true;
            JPushInterface.resumePush(SetActivity.this);
        } else {
            tb_msg.setChecked(false);
            currentSet = false;
            JPushInterface.stopPush(SetActivity.this);
        }
      /* showProgressDialog(null);
        RequestParams params = new RequestParams();
        params.put("uid", AppContext.getInstance().getPEUser().getUid());
        NetworkDownload.jsonGetForCode1(this, Constants.URL_GET_SETINFO, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                   dismissProgressDialog();
                System.out.println(jsonObject.toString());
                data = jsonObject.getJSONObject("data");
                SetInfoReturn info = SetInfoReturn.parseJson(data);
                if (info.isjpush.equals("1")) {
                    tb_msg.setChecked(true);
                    currentSet = true;
                    JPushInterface.resumePush(SetActivity.this);
                } else {
                    tb_msg.setChecked(false);
                    currentSet = false;
                    JPushInterface.stopPush(SetActivity.this);
                }
                info_set = jsonObject.toString();
            }

            @Override
            public void onFailure() {
               dismissProgressDialog();
            }
        });*/
    }

    /**
     * 发送是否接收推送的消息
     *
     * @param can
     */
    String isjp = "";

    public void sendPushInfo(final boolean can) {
        EshareLoger.logI("sendPushInfo(" + can + ")");
        Map<String, String> map = new HashMap<>();

        if (can) {
            map.put("isjpush", "1");
            isjp = "1";
        } else {
            isjp = "2";
            map.put("isjpush", "2");
        }
        showProgressDialog("正在设置。。。");
        OkhttpUtilManager.postNoCacah(this, "User/save_user_info", map, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                AppContext.getInstance().getUserMessageBean().setIsjpush(isjp);
                dismissProgressDialog();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                dismissProgressDialog();
            }
        });

    }

    private void showUpdataDialog(JSONObject body, final UpdateMsg data) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(SetActivity.this);
        View inflate = LayoutInflater.from(SetActivity.this).inflate(R.layout.dialog_update, null);
        builder.setView(inflate);
        LinearLayout appUpdate;
        TextView updateMsg;
        Button noUpdate;
        Button yesUpdate;
        TextView updateTitle;
        updateTitle = (TextView) inflate.findViewById(R.id.update_title);
        appUpdate = (LinearLayout) findViewById(R.id.app_update);
        updateMsg = (TextView) inflate.findViewById(R.id.update_msg);
        updateTitle.setText("最新版本:" + data.getShowversion());
        noUpdate = (Button) inflate.findViewById(R.id.no_update);
        yesUpdate = (Button) inflate.findViewById(R.id.yes_update);
        updateMsg.setText(data.getQzcont());
        noUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != show) {
                    show.dismiss();
                }
            }
        });
        if (data.getIsqz().equals("1")) {
            noUpdate.setVisibility(View.GONE);

        } else {
            noUpdate.setVisibility(View.VISIBLE);
        }
        yesUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serviceString = Context.DOWNLOAD_SERVICE;
                DownloadManager downloadManager;
                downloadManager = (DownloadManager) getSystemService(serviceString);
                Long downId = AppContext.getInstance().getDownId();
                if (downId != 0) {
                    queryDownloadStatus(downloadManager, downId, data);
                } else {
                    down(downloadManager, data);
                }
                show.dismiss();
            }
        });
        builder.setCancelable(false);
        show = builder.show();
    }

    private void queryDownloadStatus(final DownloadManager dowanloadmanager, final Long lastDownloadId, final UpdateMsg data) {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(lastDownloadId);
        Cursor c = dowanloadmanager.query(query);
        if (c != null && c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                case DownloadManager.STATUS_PENDING:
                case DownloadManager.STATUS_RUNNING:
                    Toast.makeText(this, "文件正在下载", Toast.LENGTH_SHORT).show();
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    AlertDialog.Builder b = new AlertDialog.Builder(SetActivity.this).setTitle("文件已经下载成功")
                            .setMessage("如果手动删除了文件请选择重新下载").setNeutralButton("安装", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Uri uriForDownloadedFile = dowanloadmanager.getUriForDownloadedFile(lastDownloadId);
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setDataAndType(uriForDownloadedFile,
                                            "application/vnd.android.package-archive");
                                    startActivity(intent);
                                    alertDialog.dismiss();
                                }
                            }).setNegativeButton("重新下载", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dowanloadmanager.remove(lastDownloadId);
                                    down(dowanloadmanager, data);
                                    alertDialog.dismiss();
                                }
                            });
                    alertDialog = b.create();
                    b.show();
                    break;
                case DownloadManager.STATUS_FAILED:
                    dowanloadmanager.remove(lastDownloadId);
                    down(dowanloadmanager, data);
                    break;
            }
        } else {
            down(dowanloadmanager, data);
        }
    }

    private void down(DownloadManager downloadManager, UpdateMsg data) {
        Uri uri = Uri.parse(data.getDownload());
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/eshare/");
        if (!file.exists()) {
            file.mkdirs();
        }
        File filee = new File(file.getAbsolutePath() + "eshare" + data.getShowversion() + ".apk");

        request.setDestinationUri(Uri.fromFile(filee));
        long reference = downloadManager.enqueue(request);
        downloadChangeObserver = new DownloadChangeObserver(null, this);
        getContentResolver().registerContentObserver(Uri.parse("content://downloads/my_downloads"), true, downloadChangeObserver);
        AppContext.getInstance().setDownId(reference);
    }

    class DownloadChangeObserver extends ContentObserver {

        private DownloadManager.Query query;
        private ProgressDialog pbarDialog;

        public DownloadChangeObserver(Handler handler, Context context) {

            super(handler);

            pbarDialog = new ProgressDialog(context);
            pbarDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pbarDialog.setMessage("下载进度...");
            pbarDialog.setCancelable(false);
            pbarDialog.show();

        }

        @Override

        public void onChange(boolean selfChange) {

            if (null == query) {
                query = new DownloadManager.Query();
                query.setFilterById(AppContext.getInstance().getDownId());
            }
            Cursor c = ((DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE)).query(query);
            if (c != null && c.moveToFirst()) {
                int reasonIdx = c.getColumnIndex(DownloadManager.COLUMN_REASON);
                int titleIdx = c.getColumnIndex(DownloadManager.COLUMN_TITLE);
                int fileSizeIdx =
                        c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
                int bytesDLIdx =
                        c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
                String title = c.getString(titleIdx);
                int fileSize = c.getInt(fileSizeIdx);
                int bytesDL = c.getInt(bytesDLIdx);
                int reason = c.getInt(reasonIdx);
                StringBuilder sb = new StringBuilder();
                sb.append(title).append("\n");
                sb.append("Downloaded ").append(bytesDL).append(" / ").append(fileSize);
                System.out.println(sb);
                DecimalFormat df = new DecimalFormat("0");//格式化小数
                double value = (double) bytesDL * 100 / (double) fileSize;
                pbarDialog.setProgress(Integer.valueOf(df.format(value)));
                if (!selfChange && bytesDL == fileSize) {
                    pbarDialog.dismiss();
                    Uri uriForDownloadedFile = ((DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE)).getUriForDownloadedFile(AppContext.getInstance().getDownId());
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(uriForDownloadedFile,
                            "application/vnd.android.package-archive");
                    startActivity(intent);
                }
            }
            c.close();
        }
    }
}
