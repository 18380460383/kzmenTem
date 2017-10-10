package com.kzmen.sczxjf.ui.activity.menu;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.audio.AudioRecoderDialog;
import com.kzmen.sczxjf.audio.AudioRecoderUtils;
import com.kzmen.sczxjf.bean.kzbean.MyCourseDetailBean;
import com.kzmen.sczxjf.consta.PlayState;
import com.kzmen.sczxjf.cusinterface.PlayMessage;
import com.kzmen.sczxjf.easypermissions.AfterPermissionGranted;
import com.kzmen.sczxjf.easypermissions.AppSettingsDialog;
import com.kzmen.sczxjf.easypermissions.EasyPermissions;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.test.bean.Music;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.CourseDetailAcitivity;
import com.kzmen.sczxjf.util.TimeFormateUtil;
import com.kzmen.sczxjf.util.glide.GlideCircleTransform;
import com.kzmen.sczxjf.view.CircleImageView;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;


/**
 * 课程提问
 */
public class CourseAskActivity extends SuperActivity implements View.OnTouchListener, AudioRecoderUtils.OnAudioStatusUpdateListener, PlayMessage
        , EasyPermissions.PermissionCallbacks {

    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.ll_course)
    LinearLayout llCourse;
    @InjectView(R.id.tv_ques_content)
    TextView tvQuesContent;
    @InjectView(R.id.tv_my)
    TextView tvMy;
    @InjectView(R.id.iv_userhead)
    CircleImageView ivUserhead;
    @InjectView(R.id.iv_anim)
    ImageView ivAnim;
    @InjectView(R.id.tv_ask_state)
    TextView tvAskState;
    @InjectView(R.id.ll_listen)
    LinearLayout llListen;
    @InjectView(R.id.tv_times)
    TextView tvTimes;
    @InjectView(R.id.tv_views)
    TextView tvViews;
    @InjectView(R.id.ll_answer)
    LinearLayout llAnswer;
    @InjectView(R.id.tv_start)
    TextView tvStart;
    @InjectView(R.id.ll_recoder)
    LinearLayout llRecoder;
    @InjectView(R.id.emotion_voice)
    ImageView emotionVoice;
    @InjectView(R.id.edit_text)
    EditText editText;
    @InjectView(R.id.tv_submit)
    TextView tvSubmit;
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
    @InjectView(R.id.tv_pre)
    TextView tvPre;
    @InjectView(R.id.ll_show)
    LinearLayout llShow;
    @InjectView(R.id.rl_ishow)
    RelativeLayout rlIshow;
    @InjectView(R.id.activity_course_ask)
    RelativeLayout activityCourseAsk;
    private String qid = "1";
    private MyCourseDetailBean myCourseBean;
    private int TimeCount = 0;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    TimeCount++;
                    handler.sendEmptyMessageDelayed(1, 1000);
                    if (TimeCount >= 120) {
                        recoderDialog.dismiss();
                        recoderUtils.stopRecord();
                    }
                    break;
                case 0:
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "课程提问");
        initData();
        animationDrawable = (AnimationDrawable) ivAnim.getBackground();
    }

    private void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("data[qid]", qid);
        OkhttpUtilManager.postNoCacah(this, "User/getCourseQuestionShow", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                Log.e("tst", data);
                JSONObject object = null;
                try {
                    object = new JSONObject(data);
                    Gson gson = new Gson();
                    myCourseBean = gson.fromJson(object.getString("data"), MyCourseDetailBean.class);
                    initView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissProgressDialog();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                Log.e("tst", "" + code + "          " + msg);
            }
        });
    }

    private void initView() {
        if (null != myCourseBean) {
            tvTitle.setText(myCourseBean.getTitle());
            tvQuesContent.setText(myCourseBean.getContent());
            if (!myCourseBean.getIsteacher().equals("1")) {
                tvMy.setText(myCourseBean.getAnswer_tid_title());
            }
            Glide.with(this).load(myCourseBean.getAnswer_tid_avatar()).transform(new GlideCircleTransform(this)).into(ivUserhead);
            tvTimes.setText(myCourseBean.getAnswer_datetime() + "\"");
            tvViews.setText(myCourseBean.getViews() + "人听过");
            if (!myCourseBean.getOk().equals("1")) {
                rlIshow.setVisibility(View.VISIBLE);
                tvMy.setVisibility(View.GONE);
                llAnswer.setVisibility(View.GONE);
            } else {
                tvMy.setVisibility(View.VISIBLE);
                llAnswer.setVisibility(View.VISIBLE);
            }
            initRecord();
        }
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_course_ask);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            qid = bundle.getString("qid");
        }
    }


    @OnClick({R.id.ll_course, R.id.ll_listen, R.id.tv_start, R.id.iv_course_play, R.id.iv_delete, R.id.tv_pre})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_course:
                Intent intent = new Intent(CourseAskActivity.this, CourseDetailAcitivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("cid", myCourseBean.getCid());
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.ll_listen:
                playType = 2;
                setMusic(myCourseBean.getAnswer_media());
                AppContext.getPlayService().playPause();
                break;
            case R.id.tv_start:
                break;
            case R.id.iv_course_play:
                playType = 1;
                File file1 = new File(Environment.getExternalStorageDirectory() + "/recoder.mp3");
                setMusic(file1.getAbsolutePath());
                //AppContext.getPlayService().setPlayPercentView(tvMediaStartTime, tvMediaEndTime, sbPlay,ivCoursePlay);
                AppContext.getPlayService().playPause();
                break;
            case R.id.iv_delete:
                File file = new File(Environment.getExternalStorageDirectory() + "/recoder.mp3");
                if (file.exists()) {
                    file.delete();
                } else {
                }
                llShow.setVisibility(View.GONE);
                llRecoder.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_pre:
                addAnswer();
                break;
        }
    }

    private void setMusic(String url) {
        List<Music> mMusicList = new ArrayList<>();
        Music music = new Music();
        music.setType(Music.Type.ONLINE);
        music.setPath(url);
        mMusicList.add(music);
        AppContext.getPlayService().setMusicList(mMusicList);
        AppContext.getPlayService().setPlayMessage(this);
    }

    private void addAnswer() {
        showProgressDialog("提交中。。。");
        Map<String, String> params = new HashMap<>();
        params.put("data[qid]", qid);
        File file = new File(Environment.getExternalStorageDirectory() + "/recoder.mp3");
        List<File> fileList = new ArrayList<>();
        fileList.add(file);
        OkhttpUtilManager.postObjec(this, "Question/addAnswer", params, fileList, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                try {
                    rlIshow.setVisibility(View.GONE);
                    Log.e("tst", data);
                    Gson gson = new Gson();
                    JSONObject object = null;
                    object = new JSONObject(data);
                    initData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissProgressDialog();
                RxToast.normal("提交成功");
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                dismissProgressDialog();
                RxToast.normal(msg);
            }
        });
    }

    private AudioRecoderDialog recoderDialog;
    private AudioRecoderUtils recoderUtils;
    private long downT;

    private void initRecord() {
        recoderDialog = new AudioRecoderDialog(this);
        recoderDialog.setShowAlpha(0.98f);
        recoderUtils = new AudioRecoderUtils(new File(Environment.getExternalStorageDirectory() + "/recoder.mp3"));
        recoderUtils.setOnAudioStatusUpdateListener(this);
        sbPlay.setOnSeekBarChangeListener(new SeekBarChangeEvent());
        tvStart.setOnTouchListener(this);
    }

    private View view = null;

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                TimeCount = 0;
                handler.sendEmptyMessage(1);
                this.view = view;
                recordTask();
                return true;
            case MotionEvent.ACTION_UP:
                handler.sendEmptyMessage(0);
                llShow.setVisibility(View.VISIBLE);
                llRecoder.setVisibility(View.GONE);
                try {
                    recoderDialog.dismiss();
                    recoderUtils.stopRecord();
                    sbPlay.setProgress(0);
                    tvMediaStartTime.setText("00:00");
                    tvMediaEndTime.setText(TimeFormateUtil.formateTime("" + TimeCount));
                    tvStart.setBackgroundResource(R.drawable.shape_recoder_btn_normal);
                    File file = new File(Environment.getExternalStorageDirectory() + "/recoder.mp3");
                } catch (Exception e) {

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
            //recoderDialog.setTime(System.currentTimeMillis() - downT);
            recoderDialog.setTime("剩余录制时间：" + ((downT + 120 * 1000 - System.currentTimeMillis()) / 1000) + "s");
        }
    }

    private int playType = 1;

    @Override
    public void prePercent(int percent) {
        if (playType == 1) {
            sbPlay.setSecondaryProgress(percent);
        }
    }

    @Override
    public void time(String start, String end, int pos) {
        if (playType == 1) {
            tvMediaStartTime.setText(start);
            tvMediaEndTime.setText(end);
            sbPlay.setProgress(pos);
        } else {

        }
    }

    @Override
    public void playposition(int position) {
    }

    private int state = -1;
    private String mediaName = "";

    private AnimationDrawable animationDrawable;//kz_play_anim

    @Override
    public void state(int state) {
        if (playType == 1) {
            switch (state) {
                case PlayState.PLAY_PLAYING:
                    ivCoursePlay.setBackgroundResource(R.drawable.btn_player_pause);
                    break;
                case PlayState.PLAY_PAUSE:
                    ivCoursePlay.setBackgroundResource(R.drawable.btn_player_play);
                    break;
            }
        } else {
            switch (state) {
                case PlayState.PLAY_PLAYING:
                    animationDrawable.start();
                    break;
                case PlayState.PLAY_PAUSE:
                    animationDrawable.stop();
                    animationDrawable.selectDrawable(0);
                    break;
            }
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

    private static final int RC_RECORD_PERM = 123;

    @AfterPermissionGranted(RC_RECORD_PERM)
    public void recordTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.RECORD_AUDIO)) {
            recoderUtils.startRecord();
            downT = System.currentTimeMillis();
            recoderDialog.showAtLocation(view, Gravity.CENTER, 0, 0);
            tvStart.setBackgroundResource(R.drawable.shape_recoder_btn_recoding);

        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_record),
                    RC_RECORD_PERM, Manifest.permission.RECORD_AUDIO);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
