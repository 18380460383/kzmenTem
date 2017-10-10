package com.kzmen.sczxjf.test;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.audio.AudioRecoderDialog;
import com.kzmen.sczxjf.audio.AudioRecoderUtils;
import com.kzmen.sczxjf.consta.PlayState;
import com.kzmen.sczxjf.cusinterface.PlayMessage;
import com.kzmen.sczxjf.mp3record.MP3Recorder;
import com.kzmen.sczxjf.test.bean.Music;
import com.kzmen.sczxjf.test.server.PlayService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.kzmen.sczxjf.AppContext.getPlayService;

public class Mp3RecorderActivity extends AppCompatActivity implements View.OnTouchListener, AudioRecoderUtils.OnAudioStatusUpdateListener, PlayMessage {
    @InjectView(android.R.id.button1)
    TextView button1;
    @InjectView(R.id.tv_media_start_time)
    TextView tvMediaStartTime;
    @InjectView(R.id.tv_media_end_time)
    TextView tvMediaEndTime;
    @InjectView(R.id.sb_play)
    SeekBar sbPlay;
    @InjectView(R.id.iv_course_play)
    ImageView ivCoursePlay;
    @InjectView(R.id.iv_delete)
    ImageView ivDelete;
    @InjectView(R.id.ll_show)
    LinearLayout llShow;
    @InjectView(R.id.ll_recoder)
    LinearLayout ll_recoder;
    private AudioRecoderDialog recoderDialog;
    private AudioRecoderUtils recoderUtils;
    private ListView listView;
    private TextView button;
    private long downT;
    private MP3Recorder recorder;
    private ServiceConnection mPlayServiceConnection;
    protected Handler mHandler = new Handler(Looper.getMainLooper());
    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case MP3Recorder.MSG_REC_STARTED:
                    showMsg("开始录音");
                    break;
                case MP3Recorder.MSG_REC_STOPPED:
                    showMsg("结束录音");
                    break;
                case MP3Recorder.MSG_ERROR_CREATE_FILE:
                    showMsg("创建文件时扑街了");
                    break;
                case MP3Recorder.MSG_ERROR_REC_START:
                    showMsg("初始化录音器时扑街了");
                    break;
                case MP3Recorder.MSG_ERROR_WRITE_FILE:
                    showMsg("写文件时挂了");
                    break;
                case MP3Recorder.MSG_ERROR_AUDIO_ENCODE:
                    showMsg("编码时挂了");
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp3_recorder);
        ButterKnife.inject(this);
        button = (TextView) findViewById(android.R.id.button1);
        button.setOnTouchListener(this);

        recoderDialog = new AudioRecoderDialog(this);
        recoderDialog.setShowAlpha(0.98f);
        recoderUtils = new AudioRecoderUtils(new File(Environment.getExternalStorageDirectory() + "/recoder.mp3"));
        recoderUtils.setOnAudioStatusUpdateListener(this);
        sbPlay.setOnSeekBarChangeListener(new SeekBarChangeEvent());
        checkService();
    }
    private void checkService() {
        if (getPlayService() == null) {
            startService();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bindService();
                }
            }, 1000);
        }
    }

    private void startService() {
        Intent intent = new Intent(this, PlayService.class);
        startService(intent);
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setClass(this, PlayService.class);
        mPlayServiceConnection = new PlayServiceConnection();
        bindService(intent, mPlayServiceConnection, Context.BIND_AUTO_CREATE);
    }
    private class PlayServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            final PlayService playService = ((PlayService.PlayBinder) service).getService();
            AppContext.setPlayService(playService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    }
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                recoderUtils.startRecord();
                downT = System.currentTimeMillis();
                recoderDialog.showAtLocation(view, Gravity.CENTER, 0, 0);
                button.setBackgroundResource(R.drawable.shape_recoder_btn_recoding);
                return true;
            case MotionEvent.ACTION_UP:
                llShow.setVisibility(View.VISIBLE);
                ll_recoder.setVisibility(View.GONE);
                //recorder.stop();
                recoderUtils.stopRecord();
                recoderDialog.dismiss();
                button.setBackgroundResource(R.drawable.shape_recoder_btn_normal);
                File file = new File(Environment.getExternalStorageDirectory() + "/recoder.mp3");
                if (file.exists()) {
                    Toast.makeText(Mp3RecorderActivity.this, "文件保存在：" + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Mp3RecorderActivity.this, "文件保存失败", Toast.LENGTH_LONG).show();
                }
                return true;
        }
        return false;
    }

    @Override
    public void onUpdate(double db) {
        if (null != recoderDialog) {
            int level = (int) db;
            recoderDialog.setLevel((int) db);
            recoderDialog.setTime(System.currentTimeMillis() - downT);
        }
    }

   /* @Override
    public void onUpText(int db) {
        recoderDialog.setMessageText(System.currentTimeMillis() - downT);
    }*/

    @OnClick({R.id.iv_course_play, R.id.iv_delete})
    public void onViewClicked(View view) {
        File file = new File(Environment.getExternalStorageDirectory() + "/recoder.mp3");
        switch (view.getId()) {
            case R.id.iv_course_play:
                List<Music> mMusicList = new ArrayList<>();
                Music music = new Music();
                music.setType(Music.Type.ONLINE);
                music.setPath(file.getAbsolutePath());
                mMusicList.add(music);
                AppContext.getPlayService().setMusicList(mMusicList);
                AppContext.getPlayService().setPlayMessage(this);
                AppContext.getPlayService().playPause();
                break;
            case R.id.iv_delete:
                if (file.exists()) {
                    file.delete();
                    Toast.makeText(Mp3RecorderActivity.this, "文件删除成功：" + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Mp3RecorderActivity.this, "文件删除失败", Toast.LENGTH_LONG).show();
                }
                llShow.setVisibility(View.GONE);
                ll_recoder.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void prePercent(int percent) {
        sbPlay.setSecondaryProgress(percent);
    }

    @Override
    public void time(String start, String end, int pos) {
        Log.e("tst",""+start+"    "+end+"     "+pos);
        tvMediaStartTime.setText(start);
        tvMediaEndTime.setText(end);
        sbPlay.setProgress(pos);
    }

    @Override
    public void playposition(int position) {

    }

    @Override
    public void state(int state) {
        switch (state) {
            case PlayState.PLAY_PLAYING:
                ivCoursePlay.setBackgroundResource(R.drawable.btn_player_pause);
                break;
            case PlayState.PLAY_PAUSE:
                ivCoursePlay.setBackgroundResource(R.drawable.btn_player_play);
                break;
        }
    }
    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        int progress;


        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
                this.progress = progress * (AppContext.getPlayService().mPlayer.getDuration())
                        / seekBar.getMax();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
                AppContext.getPlayService().mPlayer.seekTo(progress);
        }
    }
}
