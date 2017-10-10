package com.kzmen.sczxjf.test;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.server.Music;
import com.kzmen.sczxjf.server.OnPlayerEventListener;
import com.kzmen.sczxjf.server.PlayServiceNew;
import com.vondear.rxtools.view.RxToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.kzmen.sczxjf.AppContext.getPlayServiceNew;

public class Mp3ServerAcitivity extends AppCompatActivity implements View.OnClickListener, OnPlayerEventListener {
    @InjectView(R.id.bt_start)
    Button btStart;
    @InjectView(R.id.bt_pause)
    Button btPause;
    @InjectView(R.id.bt_next)
    Button btNext;
    @InjectView(R.id.tv_current_time)
    TextView tvCurrentTime;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.sb_progress)
    SeekBar sbProgress;
    @InjectView(R.id.tv_total_time)
    TextView tvTotalTime;
    @InjectView(R.id.iv_prev)
    ImageView ivPrev;
    @InjectView(R.id.iv_play)
    ImageView ivPlay;
    @InjectView(R.id.iv_next)
    ImageView ivNext;
    private ServiceConnection mPlayServiceConnection;
    protected Handler mHandler = new Handler(Looper.getMainLooper());
    private Button bt_start;
    private Button bt_next;
    private Button bt_pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp3_server_acitivity);
        ButterKnife.inject(this);
        initView();
        checkService();
    }

    private void initView() {
        bt_pause = (Button) findViewById(R.id.bt_pause);
        bt_next = (Button) findViewById(R.id.bt_next);
        bt_start = (Button) findViewById(R.id.bt_start);
        bt_start.setOnClickListener(this);
        bt_pause.setOnClickListener(this);
        bt_next.setOnClickListener(this);
    }

    private void checkService() {
        if (AppContext.getInstance().getPlayServiceNew() == null) {
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
        Intent intent = new Intent(this, PlayServiceNew.class);
        startService(intent);
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setClass(this, PlayServiceNew.class);
        mPlayServiceConnection = new PlayServiceConnection();
        bindService(intent, mPlayServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start:
                play();
                break;
            case R.id.bt_next:
                next();
                break;
            case R.id.bt_pause:
                List<Music> mMusicList = new ArrayList<>();
                Music music = new Music();
                music.setType(Music.Type.ONLINE);
                music.setPath("http://api.kzmen.cn/Uploads/Download/2017-08-19/5997c36721b89.mp3");
                music.setDuration(1);
                mMusicList.add(music);
                Music music1 = new Music();
                music1.setType(Music.Type.ONLINE);
                music1.setPath("http://api.kzmen.cn/Uploads/Download/2017-09-21/59c3163718b92.WAV");
                music.setDuration(75);
                mMusicList.add(music1);
                Music music2 = new Music();
                music2.setType(Music.Type.ONLINE);
                music2.setPath("http://api.kzmen.cn/Uploads/Download/2017-09-21/59c3167ac5bb6.WAV");
                music.setDuration(34);
                mMusicList.add(music2);
                getPlayServiceNew().setOnPlayEventListener(this);
                AppContext.getInstance().getPlayServiceNew().setmMusicList(mMusicList);
                break;
        }
    }

    @OnClick({R.id.iv_prev, R.id.iv_play, R.id.iv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_prev:
                pre();
                break;
            case R.id.iv_play:
                play();
                break;
            case R.id.iv_next:
                next();
                break;
        }
    }

    @Override
    public void onChange(com.kzmen.sczxjf.server.Music music) {
        RxToast.error(music.getPath());
        tv_title.setText(music.getPath());
        sbProgress.setMax((int) music.getDuration());
        sbProgress.setProgress((int) getPlayServiceNew().getCurrentPosition());
        //tvTotalTime.setText(form(music.getDuration()));
    }

    @Override
    public void onPlayerStart() {

    }

    @Override
    public void onPlayerPause() {

    }

    private int mLastProgress;

    @Override
    public void onPublish(int progress) {
        //更新当前播放时间
        if (progress - mLastProgress >= 1000) {
            //tvCurrentTime.setText(form(progress));
            mLastProgress = progress;
        }
    }

    @Override
    public void onBufferingUpdate(int percent) {
        sbProgress.setSecondaryProgress(sbProgress.getMax() * 100 / percent);
    }

    @Override
    public void onTimer(long remain) {

    }

    @Override
    public void onMusicListUpdate() {

    }

    private class PlayServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            final PlayServiceNew playService = ((PlayServiceNew.PlayBinder) service).getService();
            AppContext.getInstance().setPlayServiceNew(playService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    }

    private void play() {
        getPlayServiceNew().playPause();
    }

    private void next() {
        getPlayServiceNew().next();
    } private void pre() {
        getPlayServiceNew().prev();
    }


}
