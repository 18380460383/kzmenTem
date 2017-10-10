package com.kzmen.sczxjf.control;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.request.UpdateMsg;
import com.kzmen.sczxjf.utils.AppUtils;
import com.kzmen.sczxjf.utils.JsonUtils;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;

/**
 * APP 升级控制器
 * Created by 杨操 on 2016/2/29.
 */
public class UpgradeControl {
    Context context ;
    LinearLayout appUpdate;
    TextView updateMsg;
    Button noUpdate;
    Button yesUpdate;
    TextView updateTitle;
    private AlertDialog show;
    private DownloadChangeObserver downloadChangeObserver;
    private static UpgradeControl upgradeControl;
    private boolean NO=false;


    private UpgradeControl(Context context){
        if(context==null){
            context= AppContext.getInstance().getOneActivity();
        }else{
            this.context=context;
        }

    }
    public static UpgradeControl getUpgradeControl(Context context){
        if(upgradeControl==null){
            upgradeControl=new UpgradeControl(context);
        }
        return upgradeControl;
    }
    public void update() {
        if(NO){
            return;
        }

        String channel = AppContext.getInstance().getChannel();
        RequestParams params = new RequestParams();
        params.add("type", "2");
        params.add("source", channel + "");
        NetworkDownload.byteGet(context, Constants.URL_UP, params, new NetworkDownload.NetworkDownloadCallBackbyte() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
                JSONObject jsonObject = null;
                try {
                    String json = new String(bytes);
                    System.out.println(json);
                    jsonObject = new JSONObject(json);

                    final UpdateMsg data = JsonUtils.getBean(jsonObject.optJSONObject("data"), UpdateMsg.class);
                    System.out.println("升级哈哈" + data);

                    if (AppUtils.getAppVersion(context) < data.getBate()) {
                        showUpdataDialog(jsonObject, data);
                    } else {
                        AppContext.getInstance().setDownId((long) 0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure() {

            }
        });
    }
    /**
     * @param body
     * @param data
     */
    private void showUpdataDialog(JSONObject body, final UpdateMsg data) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_update, null);
        builder.setView(inflate);
        updateTitle = (TextView) inflate.findViewById(R.id.update_title);
        appUpdate = (LinearLayout)inflate.findViewById(R.id.app_update);
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
                    NO =true;
                }
            }
        });
        if (data.getIsqz().equals("1")) {
            noUpdate.setVisibility(View.GONE);
            builder.setCancelable(false);
        } else {
            noUpdate.setVisibility(View.VISIBLE);

        }

        yesUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Uri  uri = Uri.parse(data.getDownload());
                Intent  intent = new  Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);*/
                String serviceString = Context.DOWNLOAD_SERVICE;
                DownloadManager downloadManager;
                downloadManager = (DownloadManager) context.getSystemService(serviceString);
                Long downId = AppContext.getInstance().getDownId();
                if (downId != 0) {
                    queryDownloadStatus(downloadManager, downId, data);
                } else {
                    down(downloadManager, data);
                }
                show.dismiss();
            }
        });
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
                    Toast.makeText(context, "文件正在下载", Toast.LENGTH_SHORT).show();
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    AlertDialog.Builder b = new AlertDialog.Builder(context).setTitle("文件已经下载成功")
                            .setMessage("如果手动删除了文件请选择重新下载").setNeutralButton("安装", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Uri uriForDownloadedFile = dowanloadmanager.getUriForDownloadedFile(lastDownloadId);
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setDataAndType(uriForDownloadedFile,
                                            "application/vnd.android.package-archive");
                                    context.startActivity(intent);
                                    dialog.dismiss();
                                }
                            }).setNegativeButton("重新下载", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dowanloadmanager.remove(lastDownloadId);
                                    down(dowanloadmanager, data);
                                    dialog.dismiss();
                                }
                            });

                    b.create();
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
        if(!file.exists()){
            file.mkdirs();
        }
        File filee=new File(file.getAbsolutePath()+"eshare" + data.getShowversion() + ".apk");

        request.setDestinationUri(Uri.fromFile(filee));
        long reference = downloadManager.enqueue(request);
        downloadChangeObserver = new DownloadChangeObserver(null,context);
        context.getContentResolver().registerContentObserver(Uri.parse("content://downloads/my_downloads"), true, downloadChangeObserver);
        AppContext.getInstance().setDownId(reference);
    }

    class DownloadChangeObserver extends ContentObserver {

        private DownloadManager.Query query;
        private ProgressDialog pbarDialog;
        public DownloadChangeObserver(Handler handler,Context context) {
            super(handler);
            pbarDialog = new ProgressDialog( context );
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
            Cursor c = ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE)).query(query);
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
                StringBuilder sb = new StringBuilder();
                sb.append(title).append("\n");
                sb.append("Downloaded ").append(bytesDL).append(" / ").append(fileSize);
                System.out.println(sb);
                DecimalFormat df = new DecimalFormat("0");//格式化小数
                double value = (double)bytesDL*100/(double)fileSize;
                pbarDialog.setProgress(Integer.valueOf(df.format(value)));
                if(!selfChange&&bytesDL==fileSize){
                    pbarDialog.dismiss();
                    Uri uriForDownloadedFile = ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE)).getUriForDownloadedFile(AppContext.getInstance().getDownId());
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(uriForDownloadedFile,
                            "application/vnd.android.package-archive");
                    context.startActivity(intent);
                }
            }
            c.close();
        }
    }
}
