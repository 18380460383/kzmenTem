package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.kzmen.sczxjf.adapter.Kz_KnowageDetailAdapter;
import com.kzmen.sczxjf.audio.AudioRecoderDialog;
import com.kzmen.sczxjf.audio.AudioRecoderUtils;
import com.kzmen.sczxjf.bean.kzbean.KnowageAskDetailBean;
import com.kzmen.sczxjf.bean.kzbean.KnowageIndexItem;
import com.kzmen.sczxjf.bean.kzbean.ReturnOrderBean;
import com.kzmen.sczxjf.commonadapter.CommonAdapter;
import com.kzmen.sczxjf.commonadapter.ViewHolder;
import com.kzmen.sczxjf.consta.PlayState;
import com.kzmen.sczxjf.cusinterface.PlayMessage;
import com.kzmen.sczxjf.easypermissions.AfterPermissionGranted;
import com.kzmen.sczxjf.easypermissions.AppSettingsDialog;
import com.kzmen.sczxjf.easypermissions.EasyPermissions;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.interfaces.PlayDetailOperate;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.test.bean.Music;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.util.TimeFormateUtil;
import com.kzmen.sczxjf.util.glide.GlideCircleTransform;
import com.kzmen.sczxjf.utils.TextUtil;
import com.kzmen.sczxjf.view.ExPandGridView;
import com.kzmen.sczxjf.view.MyListView;
import com.vondear.rxtools.RxLogUtils;
import com.vondear.rxtools.view.RxToast;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

import static com.kzmen.sczxjf.R.id.ll_ask;


/**
 * 问答详情
 */
public class KnowageAskDetailActivity extends SuperActivity implements View.OnTouchListener, AudioRecoderUtils.OnAudioStatusUpdateListener, PlayMessage
        , EasyPermissions.PermissionCallbacks {


    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.iv_user_img)
    ImageView ivUserImg;
    @InjectView(R.id.tv_username)
    TextView tvUsername;
    @InjectView(R.id.tv_price)
    TextView tvPrice;
    @InjectView(R.id.tv_content)
    TextView tvContent;
    @InjectView(R.id.tv_ques_type)
    TextView tvQuesType;
    @InjectView(R.id.tv_state)
    TextView tvState;
    @InjectView(R.id.tv_state_des)
    TextView tvStateDes;
    @InjectView(R.id.iv_icon)
    ImageView ivIcon;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.tv_des)
    TextView tvDes;
    @InjectView(R.id.imageView5)
    ImageView imageView5;
    @InjectView(R.id.lv_aks)
    MyListView lvAks;
    @InjectView(R.id.tv_start)
    TextView tv_start;
    @InjectView(R.id.tv_media_start_time)
    TextView tvMediaStartTime;
    @InjectView(R.id.tv_media_end_time)
    TextView tvMediaEndTime;
    @InjectView(R.id.tv_pre)
    TextView tv_pre;
    @InjectView(R.id.sb_play)
    SeekBar sbPlay;
    /* @InjectView(R.id.iv_collect)
     ImageView iv_collect;*/
    @InjectView(R.id.iv_course_play)
    ImageView ivCoursePlay;
    @InjectView(R.id.iv_delete)
    ImageView ivDelete;
    @InjectView(R.id.ll_show)
    LinearLayout llShow;
    @InjectView(R.id.rl_ishow)
    RelativeLayout rlIshow;
    @InjectView(R.id.gv_image)
    ExPandGridView gv_image;
    @InjectView(R.id.ll_ask)
    LinearLayout llAsk;
    @InjectView(R.id.emotion_voice)
    ImageView emotionVoice;
    @InjectView(R.id.edit_text)
    EditText editText;
    @InjectView(R.id.tv_submit)
    TextView tvSubmit;
    @InjectView(R.id.ll_recoder)
    LinearLayout llRecoder;
    private String qid;
    private KnowageAskDetailBean knowageBean;
    // private CommonAdapter<KnowageAskDetailBean.AnswerBean> answerAdapter;
    private List<KnowageAskDetailBean.AnswerBean> answerList;

    private Kz_KnowageDetailAdapter answerAdapter;
    private CommonAdapter<String> imageAdapter;
    private List<String> imagesList;
    private InputMethodManager mInputManager;
    private String aid = "";
    private int playPos = -1;
    private int TimeCount = 0;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    TimeCount++;
                    if (TimeCount >= 120) {
                        recoderDialog.dismiss();
                        recoderUtils.stopRecord();
                    }
                    if (isStartRecord) {
                        handler.sendEmptyMessageDelayed(1, 1000);
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
        EventBus.getDefault().register(this);
    }


    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "问答详情");

        showProgressDialog("数据获取中");
        mInputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        answerList = new ArrayList<>();
        imagesList = new ArrayList<>();

        answerAdapter = new Kz_KnowageDetailAdapter(this, answerList, new PlayDetailOperate() {
            @Override
            public void doPlay(String id, String url) {
                playType = 2;
                setMusic(url, 0);
                if (id.equals(answerAdapter.getMediaName())) {
                    AppContext.getPlayService().playPause();
                } else {
                    AppContext.getPlayService().stop();
                    AppContext.getPlayService().playStart();
                }
                answerAdapter.setMediaName(id);
            }

            @Override
            public void doInput(View view, String qid) {

            }

            @Override
            public void doZan(String opType, String id, String state) {
                setZans(opType, id, state);
            }

            @Override
            public void doCollect(String opType, String id, String state) {
                setUserCollect(opType, id, state);
            }
        });
        lvAks.setAdapter(answerAdapter);
        imageAdapter = new CommonAdapter<String>(this, R.layout.list_item_image, imagesList) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                viewHolder.glideImageBlur(R.id.iv_img, item);
            }
        };
        gv_image.setAdapter(imageAdapter);
        initData();
        initRecord();
    }


    private void initData() {
        if (TextUtil.isEmpty(qid)) {
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("data[qid]", qid);
        OkhttpUtilManager.postNoCacah(this, "Interlocution/getInterlocutionShow", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                getFoucus();
                Log.e("tst", data);
                JSONObject object = null;
                try {
                    object = new JSONObject(data);
                    Gson gson = new Gson();
                    knowageBean = gson.fromJson(object.getString("data"), KnowageAskDetailBean.class);
                    answerList.clear();
                    answerList.addAll(knowageBean.getAnswer());
                    imagesList.clear();
                    imagesList.addAll(knowageBean.getImages());
                    answerAdapter.notifyDataSetChanged();
                    imageAdapter.notifyDataSetInvalidated();
                    initView();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissProgressDialog();
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                Log.e("tst", msg);
                dismissProgressDialog();
            }
        });
    }

    private void initView() {
        if (knowageBean != null) {
            Glide.with(this).load(knowageBean.getAvatar()).placeholder(R.drawable.icon_user_normal)
                    .transform(new GlideCircleTransform(KnowageAskDetailActivity.this))
                    .into(ivUserImg);
            tvUsername.setText(knowageBean.getUsername());
            tvPrice.setText("赏金￥  " + knowageBean.getMoney());
            tvContent.setText(knowageBean.getContent());
            tvState.setText(knowageBean.getDaojishi());
            Glide.with(this).load(knowageBean.getInterlocution().getImage()).into(ivIcon);
            tvTitle.setText(knowageBean.getInterlocution().getTitle());
            tvDes.setText(knowageBean.getInterlocution().getDes());
            tvStateDes.setText(knowageBean.getAnswer_tishi());
            //iv_collect.setBackgroundResource(knowageBean.get.equals("1") ? R.drawable.btn_collect_current : R.drawable.btn_collect);
            llAsk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(KnowageAskDetailActivity.this, KnowageAskActivity.class);
                    KnowageIndexItem itemBean = new KnowageIndexItem(knowageBean.getInterlocution().getId(), knowageBean.getInterlocution().getTitle(), knowageBean.getInterlocution().getImage(), knowageBean.getInterlocution().getDes());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bean", itemBean);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            rlIshow.setVisibility(View.GONE);
            if (knowageBean.getUser().getTeacher().equals("1") || knowageBean.getUser().getRole().equals("1")) {
                rlIshow.setVisibility(View.VISIBLE);
            }
            if (knowageBean.getOk().equals("1")) {
                rlIshow.setVisibility(View.GONE);
            }
        }
    }

    private void getFoucus() {
        if (ivUserImg == null) {
            return;
        }
        ivUserImg.setFocusable(true);
        ivUserImg.setFocusableInTouchMode(true);
        ivUserImg.requestFocus();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_ask_detail);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            qid = bundle.getString("qid");
        }
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
        tv_start.setOnTouchListener(this);
    }

    private void setMusic(String url, long druation) {
        List<Music> mMusicList = new ArrayList<>();
        Music music = new Music();
        music.setType(Music.Type.ONLINE);
        music.setPath(url);
        music.setDuration(druation);
        mMusicList.add(music);
        AppContext.getPlayService().setMusicList(mMusicList);
        AppContext.getPlayService().setPlayMessage(this);
    }

    private boolean isVoice = false;

    @OnClick({R.id.iv_share, ll_ask, R.id.tv_start, R.id.iv_course_play, R.id.iv_delete, R.id.tv_pre, R.id.emotion_voice,
            R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.emotion_voice:
                isVoice = !isVoice;
                if (isVoice) {
                    hideSoftInput();
                    emotionVoice.setBackgroundResource(R.drawable.icon_keyboard);
                    tv_start.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.INVISIBLE);
                    tvSubmit.setClickable(false);
                } else {
                    emotionVoice.setBackgroundResource(R.mipmap.icon_chat_voice);
                    showSoftInput();
                    tv_start.setVisibility(View.INVISIBLE);
                    editText.setVisibility(View.VISIBLE);
                    tvSubmit.setClickable(true);
                }
                break;
            case R.id.tv_submit:
                addAnswerText();
                break;
            case R.id.iv_share:
                break;
           /* case R.id.iv_collect:
                String state = knowageBean.getState();
                setUserCollect("2", knowageBean.getQid(), state.equals("1") ? "0" : "1");
                break;*/
            case R.id.ll_ask:
                break;
            case R.id.tv_start:
                RxToast.normal("开始回答");
                break;
            case R.id.iv_course_play:
                playType = 1;
                /*File file1 = new File(Environment.getExternalStorageDirectory() + "/recoder.mp3");
                setMusic(file1.getAbsolutePath(), 0);*/
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

    private void addAnswerText() {
        String content = editText.getText().toString();
        if (TextUtil.isEmpty(content)) {
            RxToast.normal("回答的内容不能为空");
        }
        Map<String, String> params = new HashMap<>();
        params.put("data[qid]", qid);
        params.put("data[content]", content);
        OkhttpUtilManager.postNoCacah(this, "Question/addAnswer", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                Log.e("tst", data);
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                Log.e("tst", msg);
            }
        });
    }

    private void addAnswer() {
        showProgressDialog("提交中。。。");
        Map<String, String> params = new HashMap<>();
        params.put("data[qid]", qid);
        File file = new File(Environment.getExternalStorageDirectory() + "/recoder.mp3");
       /* List<File> fileList = new ArrayList<>();
        fileList.add(file);*/
        OkhttpUtilManager.postObjec(this, "Question/addAnswer", params, file, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                try {
                    rlIshow.setVisibility(View.GONE);
                    Log.e("tst", data);
                    Gson gson = new Gson();
                    JSONObject object = null;
                    object = new JSONObject(data);
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

    private View view = null;
    private boolean isStartRecord = false;
    private int isFir = 0;

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.view = view;
                TimeCount = 0;
                isFir = 0;
                recordTask();
                return true;
            case MotionEvent.ACTION_UP:
                if (isStartRecord) {
                    try {
                        File file = new File(Environment.getExternalStorageDirectory() + "/recoder.mp3");
                        if (file.exists()) {
                            File file1 = new File(Environment.getExternalStorageDirectory() + "/recoder.mp3");
                            setMusic(file1.getAbsolutePath(), 0);
                            RxToast.normal("文件保存成功");
                            llShow.setVisibility(View.VISIBLE);
                            llRecoder.setVisibility(View.GONE);
                            sbPlay.setProgress(0);
                            tvMediaStartTime.setText("00:00");
                            tvMediaEndTime.setText(TimeFormateUtil.formateTime("" + TimeCount));
                            //tvMediaEndTime.setText(TimeFormateUtil.formateTime("" + (AppContext.getPlayService().mPlayer.getDuration() / 1000)));
                            tv_start.setBackgroundResource(R.drawable.shape_recoder_btn_normal);
                            recoderDialog.dismiss();
                            recoderUtils.stopRecord();
                        } else {
                            RxToast.normal("文件保存失败");
                            llRecoder.setVisibility(View.VISIBLE);
                            recoderDialog.dismiss();
                            recoderUtils.stopRecord();
                            tv_start.setBackgroundResource(R.drawable.shape_recoder_btn_normal);
                        }
                    } catch (Exception e) {
                        llRecoder.setVisibility(View.VISIBLE);
                        recoderDialog.dismiss();
                        recoderUtils.stopRecord();
                        tv_start.setBackgroundResource(R.drawable.shape_recoder_btn_normal);
                    }
                } else {
                    try {
                        llRecoder.setVisibility(View.VISIBLE);
                        recoderDialog.dismiss();
                        recoderUtils.stopRecord();
                        tv_start.setBackgroundResource(R.drawable.shape_recoder_btn_normal);
                    } catch (Exception e) {
                        llRecoder.setVisibility(View.VISIBLE);
                        tv_start.setBackgroundResource(R.drawable.shape_recoder_btn_normal);
                    }
                }
                isStartRecord = false;
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
            this.state = state;
            answerAdapter.setState(state);
            RxLogUtils.e("tst", "" + state);
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
        if (EasyPermissions.hasPermissions(this, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (isFir == 0) {
                recoderUtils.startRecord();
                downT = System.currentTimeMillis();
                recoderDialog.showAtLocation(view, Gravity.CENTER, 0, 0);
                TimeCount = 0;
                handler.sendEmptyMessage(1);
            }
            isStartRecord = true;
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_record),
                    RC_RECORD_PERM, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        ++isFir;
        try {
            recoderDialog.dismiss();
            recoderUtils.stopRecord();
        } catch (Exception e) {

        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            try {
                recoderDialog.dismiss();
                recoderUtils.stopRecord();
            } catch (Exception e) {

            }
            new AppSettingsDialog.Builder(this).build().show();
        }
        ++isFir;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            recoderDialog.dismiss();
            recoderUtils.stopRecord();
        } catch (Exception e) {

        }
        ++isFir;
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void showSoftInput() {
        editText.requestFocus();
        editText.post(new Runnable() {
            @Override
            public void run() {
                mInputManager.showSoftInput(editText, 0);
            }
        });
    }

    public void hideSoftInput() {
        mInputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (AppContext.getPlayService() != null) {
            AppContext.getPlayService().stop();
        }
    }

    @Override
    public void funOrder(ReturnOrderBean bean) {
        super.funOrder(bean);
        if (bean.getType() == 1) {
            answerAdapter.updatePlay();
        }
    }

    @Override
    public void onOperateSuccess(String opType, String type, String state, String id) {
        super.onOperateSuccess(opType, type, state, id);
        switch (type) {
            case "1"://收藏
                switch (opType) {
                    case "2":
                        // iv_collect.setBackgroundResource(state.equals("1") ? R.drawable.btn_collect_current : R.drawable.btn_collect);
                        break;
                    case "4":
                        answerAdapter.updateCollect();
                        break;
                }
                break;
            case "2"://举报
                break;
            case "3"://点赞
                answerAdapter.updateZan();
                break;
        }
    }
}
