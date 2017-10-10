package com.kzmen.sczxjf.control;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.sql.DBManager;

import java.io.File;
import java.text.DecimalFormat;

//import com.artifex.mupdflib.MuPDFActivity;

/**
 * 创建者：Administrator
 * 时间：2016/5/13
 * 功能描述：
 */
public class DownPDF {
    private Context context;
    private String pdfurl;
    private String pdfname;
    private Handler handler;
    private DownloadChangeObserver downloadChangeObserver;
    private ProgressBack progressBack;
    private TextView pross;
    private Long state;

    public DownPDF(Context context, String pdfurl, String pdfname, Handler handler,   TextView pross) {
        this.context = context;
        this.pdfurl = pdfurl;
        this.pdfname = pdfname;
        this.handler = handler;
        this.pross = pross;
    }

    public  void init(){
        DownloadManager downloadManager;
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/eshare/pdf/"+pdfname);
        if(file.exists()){
            DBManager dbManager=DBManager.getDBManager(context);
            Cursor query = dbManager.query(true, Constants.DB_PDF, null, "pdf_url=?", new String[]{pdfurl}, null, "1");
            if(query.getCount()>0) {
                if (query.moveToFirst()) {
                    state = query.getLong(query.getColumnIndex("dowid"));
                    queryDownloadStatus(downloadManager, state);
                }
            }else{
                goReadPdf(Environment.getExternalStorageDirectory().getAbsolutePath() + "/eshare/pdf/" + pdfname);
            }

        }else {
            down(downloadManager, pdfurl);
        }
    }
    private void goReadPdf(String path) {
        /*Uri uri=Uri.parse(path);
        Intent intent = new Intent(context, MuPDFActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(uri);

        //if document protected with password
        intent.putExtra("password", "encrypted PDF password");

        //if you need highlight link boxes
        intent.putExtra("linkhighlight", true);

        //if you don't need device sleep on reading document
        intent.putExtra("idleenabled", false);

        //set true value for horizontal page scrolling, false value for vertical page scrolling
        intent.putExtra("horizontalscrolling", true);

        //document name
        intent.putExtra("docname", pdfname);

        context.startActivity(intent);
        System.out.println("hahahaha");*/
    }
    private void queryDownloadStatus(final DownloadManager dowanloadmanager, final Long lastDownloadId) {
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
                    downloadChangeObserver = new DownloadChangeObserver(null,context);
                    context.getContentResolver().registerContentObserver(Uri.parse("content://downloads/my_downloads"), true, downloadChangeObserver);
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    goReadPdf(Environment.getExternalStorageDirectory().getAbsolutePath() + "/eshare/pdf/" + pdfname);
                    break;
                case DownloadManager.STATUS_FAILED:
                    dowanloadmanager.remove(lastDownloadId);
                    down(dowanloadmanager, pdfurl);
                    break;
            }
        } else {
            down(dowanloadmanager, pdfurl);
        }
    }

    private void down(DownloadManager downloadManager, String pdfurl) {
        handler=new Handler();
        Uri uri = Uri.parse(pdfurl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/eshare/pdf/");
        if(!file.exists()){
            file.mkdirs();
        }
        File filee=new File(file.getAbsolutePath()+"/"+pdfname);
        request.setDestinationUri(Uri.fromFile(filee));
        state = downloadManager.enqueue(request);
        downloadChangeObserver = new DownloadChangeObserver(null,context);
        context.getContentResolver().registerContentObserver(Uri.parse("content://downloads/my_downloads"), true, downloadChangeObserver);
        DBManager dbManager=DBManager.getDBManager(context);
        synchronized (dbManager) {
            int delete = dbManager.delete(Constants.DB_PDF, "pdf_url=?", new String[]{pdfurl});
        }
        ContentValues values = new ContentValues();
        values.put("pdf_url",pdfurl);
        values.put("dowid",state);
        synchronized (dbManager) {
            dbManager.insert(Constants.DB_PDF, "无效数据", values);
        }
    }
    class DownloadChangeObserver extends ContentObserver {
            TextView p=pross;
        private DownloadManager.Query query;
        public DownloadChangeObserver(Handler handler,Context context) {
            super(handler);

        }

        @Override

        public void onChange(boolean selfChange) {
            if (null == query) {
                query = new DownloadManager.Query();
                query.setFilterById(state);
            }
            final Cursor c = ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE)).query(query);
            if (c != null && c.moveToFirst()) {
                int reasonIdx = c.getColumnIndex(DownloadManager.COLUMN_REASON);
                int titleIdx = c.getColumnIndex(DownloadManager.COLUMN_TITLE);
                int fileSizeIdx =
                        c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
                int bytesDLIdx =
                        c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
                String title = c.getString(titleIdx);
                int fileSize = Math.abs(c.getInt(fileSizeIdx));
                int bytesDL = Math.abs(c.getInt(bytesDLIdx));
                StringBuilder sb = new StringBuilder();
                sb.append(title).append("\n");
                sb.append("Downloaded ").append(bytesDL).append(" / ").append(fileSize);
                System.out.println(sb);
                synchronized (c) {
                    DecimalFormat df = new DecimalFormat("0");//格式化小数
                    double value = (double)bytesDL*100/(double)fileSize;
                    final String str = df.format(value)+"%...";
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            p.setTextColor(context.getResources().getColor(R.color.orange));
                            p.setText(str);
                        }
                    });
                    if (!selfChange && bytesDL == fileSize) {

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                p.setTextColor(context.getResources().getColor(R.color.text_hei));
                                p.setText("查看附件");
                            }
                        });

                    }
                }
            }
            c.close();
        }

    }
    public interface  ProgressBack{
        void progressChange(int a);
    }
}
