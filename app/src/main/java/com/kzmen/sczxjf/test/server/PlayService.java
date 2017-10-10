package com.kzmen.sczxjf.test.server;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.cusinterface.PlayMessage;
import com.kzmen.sczxjf.test.bean.Music;
import com.vondear.rxtools.RxLogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 音乐播放后台服务
 * Created by wcy on 2015/11/27.
 */
public class PlayService extends Service implements MediaPlayer.OnCompletionListener, AudioManager.OnAudioFocusChangeListener {
    private static final String TAG = "Service";
    private static final long TIME_UPDATE = 100L;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PREPARING = 1;
    private static final int STATE_PLAYING = 2;
    private static final int STATE_PAUSE = 3;

    public static List<Music> mMusicList;
    private final IntentFilter mNoisyFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
    private final NoisyAudioStreamReceiver mNoisyReceiver = new NoisyAudioStreamReceiver();
    private final Handler mHandler = new Handler();
    public MediaPlayer mPlayer = new MediaPlayer();
    private AudioManager mAudioManager;
    private OnPlayerEventListener mListener;
    // 正在播放的歌曲[本地|网络]
    private Music mPlayingMusic;
    // 正在播放的本地歌曲的序号
    private int mPlayingPosition;
    private long quitTimerRemain;
    private int mPlayState = STATE_IDLE;
    private int realPost = 0;
    private PlayMessage playMessage;

    private AnimationDrawable animationDrawable;//kz_play_anim
    private ImageView iv_anim;
    private ImageView iv_play;
    private TextView tv_start;
    private TextView tv_end;
    private SeekBar sb_play;

    private void stopTimer() {
        if (mTimer == null) {
            return;
        }
        mTimer.cancel();
        mTimer = null;
    }

    private void startTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mPlayer == null) {
                    return;
                }
                if (isPlaying()) {
                    handler.sendEmptyMessage(0); // 发送消息
                }
            }
        }, 0, 1000);
    }

    private Timer mTimer = new Timer(); // 计时器
    // 计时器
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            /*int position = mPlayer.getCurrentPosition();
            int duration = mPlayer.getDuration();
            if (duration > 0) {
                // 计算进度（获取进度条最大刻度*当前音乐播放位置 / 当前音乐时长）
                int pos = 100 * position / duration;
                int musicTime = position / 1000;
                int min = musicTime / 60;
                int sec = musicTime % 60;
                String show = (min < 10 ? "0" + min : min) + ":" + (sec < 10 ? "0" + sec : sec);
                RxLogUtils.e("tst", "::::::::::::");
                if (null != tv_start) {
                    tv_start.setText(show);
                    RxLogUtils.e("tst", "::::::::::::" + show);
                }
                int emusicTime = duration / 1000;
                int emin = emusicTime / 60;
                int esec = emusicTime % 60;
                String eshow = (emin < 10 ? "0" + emin : emin) + ":" + (esec < 10 ? "0" + esec : esec);
                if (null != tv_end) {
                    tv_end.setText(eshow);
                }
                if (null != sb_play) {
                    sb_play.setProgress(pos);
                }
                if (playMessage != null) {
                    playMessage.time(show, eshow, pos);
                }
            }*/
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mMusicList = new ArrayList<>();
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        mPlayer.setOnCompletionListener(this);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new PlayBinder();
    }

    public static void startCommand(Context context, String action) {
        Intent intent = new Intent(context, PlayService.class);
        intent.setAction(action);
        context.startService(intent);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                case Actions.ACTION_MEDIA_PLAY_PAUSE:
                    playPause();
                    break;
                case Actions.ACTION_MEDIA_NEXT:
                    next();
                    break;
                case Actions.ACTION_MEDIA_PREVIOUS:
                    prev();
                    break;
            }
        }
        //startTimer();
        return START_NOT_STICKY;
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        next();
    }

    public OnPlayerEventListener getOnPlayEventListener() {
        return mListener;
    }

    public void setOnPlayEventListener(OnPlayerEventListener listener) {
        mListener = listener;
    }

    public void play(int position) {
        if (mMusicList.isEmpty()) {
            return;
        }
        if (position < 0) {
            position = mMusicList.size() - 1;
        } /*else if (position >= mMusicList.size()) {
            realPost = position;
            position = 0;

        }*/
        if (position >= mMusicList.size()) {
            //EToastUtil.show(getApplicationContext(),"播放完毕");
            realPost = 0;
            position = 0;
            mPlayingPosition = position;
            stop();
            return;
        }

        mPlayingPosition = position;
        Music music = mMusicList.get(mPlayingPosition);
        play(music);
    }

    public void play(Music music) {
        startTimer();
        mPlayingMusic = music;
        try {
            mPlayer.reset();
            mPlayer.setDataSource(music.getPath());
            mPlayer.prepareAsync();
            mPlayState = STATE_PREPARING;
            mPlayer.setOnPreparedListener(mPreparedListener);
            mPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);
            if (mListener != null) {
                mListener.onChange(music);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private MediaPlayer.OnPreparedListener mPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            if (isPreparing()) {
                start();
            }
        }
    };

    private MediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            if (mListener != null) {
                mListener.onBufferingUpdate(percent);
            }
            if (playMessage != null) {
                playMessage.prePercent(percent);
            }
            if (null != sb_play) {
                sb_play.setSecondaryProgress(percent);
            }
            if (playMessage != null) {
                playMessage.playposition(mPlayingPosition);
            }
        }
    };

    public void playPause() {
        if (isPreparing()) {
            stop();
        } else if (isPlaying()) {
            pause();
        } else if (isPausing()) {
            resume();
        } else {
            play(getPlayingPosition());
        }
    }

    public void playStart() {
        play(getPlayingPosition());
    }

    private boolean start() {
        mPlayer.start();
        if (mPlayer.isPlaying()) {
            mPlayState = STATE_PLAYING;
            mHandler.post(mPublishRunnable);

            mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            registerReceiver(mNoisyReceiver, mNoisyFilter);
        }
        if (playMessage != null) {
            playMessage.state(1);
        }
        if (null != iv_play) {
            iv_play.setBackgroundResource(R.drawable.btn_player_pause);
        }
        if (animationDrawable != null) {
            animationDrawable.start();
        }
        return mPlayer.isPlaying();
    }

    private void pause() {
        if (!isPlaying()) {
            return;
        }
        mPlayer.pause();
        mPlayState = STATE_PAUSE;
        mHandler.removeCallbacks(mPublishRunnable);
        mAudioManager.abandonAudioFocus(this);
        unregisterReceiver(mNoisyReceiver);
        if (mListener != null) {
            mListener.onPlayerPause();
        }
        if (playMessage != null) {
            playMessage.state(0);
        }
        if (null != iv_play) {
            iv_play.setBackgroundResource(R.drawable.btn_player_play);
        }
        stopAn();
    }

    private void stopAn() {
        if (null != animationDrawable) {
            animationDrawable.stop();
            animationDrawable.selectDrawable(0);
        }
    }

    public void stop() {
        if (isIdle()) {
            return;
        }
        stopTimer();
        pause();
        mPlayer.reset();
        mPlayState = STATE_IDLE;
    }

    private void resume() {
        if (!isPausing()) {
            return;
        }

        if (start()) {
            if (mListener != null) {
                mListener.onPlayerResume();
            }
        }
    }

    public void next() {
        if (mMusicList.isEmpty()) {
            return;
        }
        play(mPlayingPosition + 1);
    }

    public void prev() {
        if (mMusicList.isEmpty()) {
            return;
        }
        play(mPlayingPosition - 1);
    }

    /**
     * 跳转到指定的时间位置
     *
     * @param msec 时间
     */
    public void seekTo(int msec) {
        if (isPlaying() || isPausing()) {
            mPlayer.seekTo(msec);
            if (mListener != null) {
                mListener.onPublish(msec);
            }
        }
    }

    public boolean isPlaying() {
        return mPlayState == STATE_PLAYING;
    }

    public boolean isPausing() {
        return mPlayState == STATE_PAUSE;
    }

    public boolean isPreparing() {
        return mPlayState == STATE_PREPARING;
    }

    public boolean isIdle() {
        return mPlayState == STATE_IDLE;
    }

    /**
     * 获取正在播放的本地歌曲的序号
     */
    public int getPlayingPosition() {
        return mPlayingPosition;
    }

    /**
     * 获取正在播放的歌曲[本地|网络]
     */
    public Music getPlayingMusic() {
        return mPlayingMusic;
    }

    private Runnable mPublishRunnable = new Runnable() {
        @Override
        public void run() {
            if (isPlaying()) {//&& mListener != null
                //  mListener.onPublish(mPlayer.getCurrentPosition());
                RxLogUtils.e("debug", "" + mPlayer.getCurrentPosition());
                int position = mPlayer.getCurrentPosition();
                int duration = (int) mMusicList.get(mPlayingPosition).getDuration() * 1000;
                RxLogUtils.e("debug", "duration::::::" + duration);
                if (duration == 0) {
                    duration = mPlayer.getDuration();
                }
                RxLogUtils.e("debug", "durationnew::::::" + duration + "   " + mPlayingPosition);
                if (duration > 0) {
                    // 计算进度（获取进度条最大刻度*当前音乐播放位置 / 当前音乐时长）
                    int pos = 100 * position / duration;
                    int musicTime = position / 1000;
                    int min = musicTime / 60;
                    int sec = musicTime % 60;
                    String show = (min < 10 ? "0" + min : min) + ":" + (sec < 10 ? "0" + sec : sec);
                    if (null != tv_start) {
                        tv_start.setText(show);
                    }
                    int emusicTime = duration / 1000;
                    int emin = emusicTime / 60;
                    int esec = emusicTime % 60;
                    String eshow = (emin < 10 ? "0" + emin : emin) + ":" + (esec < 10 ? "0" + esec : esec);
                    if (null != tv_end) {
                        tv_end.setText(eshow);
                    }
                    if (null != sb_play) {
                        sb_play.setProgress(pos);
                    }
                    if (playMessage != null) {
                        playMessage.time(show, eshow, pos);
                    }
                }
            }
            mHandler.postDelayed(this, 1000);
        }
    };

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_LOSS:
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                pause();
                break;
        }
    }

    public void startQuitTimer(long milli) {
        stopQuitTimer();
        if (milli > 0) {
            quitTimerRemain = milli + DateUtils.SECOND_IN_MILLIS;
            mHandler.post(mQuitRunnable);
        } else {
            quitTimerRemain = 0;
            if (mListener != null) {
                mListener.onTimer(quitTimerRemain);
            }
        }
    }

    private void stopQuitTimer() {
        mHandler.removeCallbacks(mQuitRunnable);
    }

    private Runnable mQuitRunnable = new Runnable() {
        @Override
        public void run() {
            quitTimerRemain -= DateUtils.SECOND_IN_MILLIS;
            if (quitTimerRemain > 0) {
                if (mListener != null) {
                    mListener.onTimer(quitTimerRemain);
                }
                mHandler.postDelayed(this, DateUtils.SECOND_IN_MILLIS);
            } else {
                quit();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppContext.setPlayService(null);
        Log.i(TAG, "onDestroy: " + getClass().getSimpleName());
    }

    public void quit() {
        stop();
        stopQuitTimer();
        mPlayer.reset();
        mPlayer.release();
        mPlayer = null;
        AppContext.setPlayService(null);
        stopSelf();
    }

    public class PlayBinder extends Binder {
        public PlayService getService() {
            return PlayService.this;
        }
    }

    public void setMusicList(List<Music> mMusicList) {
        this.mMusicList.clear();
        this.mMusicList.addAll(mMusicList);
        realPost = 0;
        mPlayingPosition = 0;
    }

    public void setMusic(Music music) {
        this.mMusicList.clear();
        this.mMusicList.add(music);
        realPost = 0;
        mPlayingPosition = 0;
    }

    public PlayMessage getPlayMessage() {
        return playMessage;
    }

    public void setPlayMessage(PlayMessage playMessage) {
        this.playMessage = playMessage;
    }

    public void setIv_anim(ImageView iv_anim) {
        if (null != this.iv_anim) {
            stopAn();
        }
        this.iv_anim = iv_anim;
        animationDrawable = (AnimationDrawable) iv_anim.getBackground();
    }

    public void setPlayPercentView(TextView tv_start, TextView tv_end, SeekBar sb_play, ImageView iv_play) {
        this.tv_start = tv_start;
        this.tv_end = tv_end;
        this.sb_play = sb_play;
        this.iv_play = iv_play;
    }
}
