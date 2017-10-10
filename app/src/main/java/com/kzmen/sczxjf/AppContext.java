package com.kzmen.sczxjf;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kzmen.sczxjf.bean.Advertisement;
import com.kzmen.sczxjf.bean.Config;
import com.kzmen.sczxjf.bean.UserInfo;
import com.kzmen.sczxjf.bean.WeixinInfo;
import com.kzmen.sczxjf.bean.kzbean.AddressBean;
import com.kzmen.sczxjf.bean.kzbean.UserBean;
import com.kzmen.sczxjf.bean.kzbean.UserMessageBean;
import com.kzmen.sczxjf.bean.user.User_For_pe;
import com.kzmen.sczxjf.ebean.User;
import com.kzmen.sczxjf.imagepicker.ImagePicker;
import com.kzmen.sczxjf.imagepicker.loader.GlideImageLoader;
import com.kzmen.sczxjf.imagepicker.view.CropImageView;
import com.kzmen.sczxjf.multidex.MultiDexApplication;
import com.kzmen.sczxjf.server.PlayServiceNew;
import com.kzmen.sczxjf.test.server.PlayService;
import com.kzmen.sczxjf.ui.activity.BaseWebActivity;
import com.kzmen.sczxjf.ui.activity.kzmessage.MainTabActivity;
import com.kzmen.sczxjf.util.DeviceUuidFactory;
import com.kzmen.sczxjf.util.PreferenceUtil;
import com.kzmen.sczxjf.utils.FileUtils;
import com.lzy.okhttputils.OkHttpUtils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;
import com.vondear.rxtools.RxDeviceUtils;
import com.vondear.rxtools.RxLogUtils;
import com.vondear.rxtools.RxUtils;

import java.io.File;
import java.util.Date;
import java.util.List;

import cn.jpush.android.api.JPushInterface;


/**
 * @author peng
 * @version 1.0
 */
public class AppContext extends MultiDexApplication {
    private PlayService mPlayService;
    private final String SHARED_USER = "eshare_context";
    private static AppContext instance;
    private Activity oldinstance;
    private Activity oneActivity;
    public static MainTabActivity maintabeactivity;
    private static Context _context;
    private static Resources _resource;
    public String weixinCode;
    public String accessToken;
    public String openid;
    public User user;
    private UserInfo userInfo;
    public SharedPreferences sp;
    public static UserBean userBean;



	
    // 七牛sdk
    private Configuration config7niu;
    private UploadManager uploadManager;
    private Config appconfig = null;
    public BaseWebActivity mBaseWebAct;
    private User_For_pe peuser;
    public static UserMessageBean userMessageBean;

    private int netState = 0; // 0 不可用  1.wifi可用  2.wifi不可用  3.移动网可用 4.移动网不可用
    public static String sign = "";
    public static String token = "";
    public static String app_bate = "";
    public static String from = "1";
    public static String public_deviceVersion = "";
    public static String public_deviceType = "2";
    public static String public_deviceId = "";

    private PlayServiceNew playServiceNew;

    public int getNetState() {
        return netState;
    }

    public void setNetState(int netState) {
        this.netState = netState;
    }

    /**
     * 获得当前app运行的AppContext
     */
    public static AppContext getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        RxUtils.init(this);
        try {
            JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
            JPushInterface.init(this);            // 初始化 JPush
        } catch (Exception e) {
        }

        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                            //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(8);              //选中数量限制
        imagePicker.setMultiMode(true);                      //多选
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素

        sp = getSharedPreferences(SHARED_USER, Context.MODE_PRIVATE);
        userBean = getUserLogin();
        sign = getUserLogin().getSign();
        token = getUserLogin().getToken();
        app_bate = "1";
        setDeviceId();
        public_deviceVersion = "" + RxDeviceUtils.getAppVersionNo(this);
        new Runnable() {
            @Override
            public void run() {
                try {
                    user = new User();
                    setChannel();
                    ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(instance)
                            .diskCache(new UnlimitedDiskCache(new File(FileUtils.getRootFile() + "/" + Constants.LOADER_PATH_IMG)))
                            .build();
                    ImageLoader.getInstance().init(configuration);
                    init();
                    //AdManager.getInstance(this).init("ab26e1420fdcd645", "05fe4480696a8707", false);
                    //FeedbackAPI.initAnnoy(instance, "23399998");
                } catch (Exception e) {

                }
            }
        }.run();
        //必须调用初始化
        OkHttpUtils.init(this);
        //以下都不是必须的，根据需要自行选择
        OkHttpUtils.getInstance()//
                .setConnectTimeout(6 * 1000)               //全局的连接超时时间
                .setReadTimeOut(6 * 1000)                  //全局的读取超时时间
                .setWriteTimeOut(6 * 1000)                 //全局的写入超时时间
        ;

    }

    DeviceUuidFactory bulid;

    public void setDeviceId() {
        try {
            public_deviceId = RxDeviceUtils.getDeviceIdIMEI(this);
        } catch (Exception e) {
            bulid = new DeviceUuidFactory(this);
            public_deviceId = bulid.getDeviceUuid().toString();
        }
        RxLogUtils.e("uuid", "" + public_deviceId);
    }

    public static PlayService getPlayService() {
        return getInstance().mPlayService;
    }

    public static void setPlayService(PlayService service) {
        getInstance().mPlayService = service;
    }

    public static PlayServiceNew getPlayServiceNew() {
        return getInstance().playServiceNew;
    }

    public static void setPlayServiceNew(PlayServiceNew service) {
        getInstance().playServiceNew = service;
    }

    public void play() {
        getInstance().mPlayService.playPause();
    }

    public void playStart() {
        getInstance().mPlayService.playStart();
    }

    public static void playStartByPos(int position) {
        getInstance().mPlayService.play(position);
    }

    public SharedPreferences getSp() {
        if (sp == null) {
            sp = getSharedPreferences(SHARED_USER, Context.MODE_PRIVATE);
        }
        return sp;
    }

    // 初始化网络请求
    private void init() {
        AppException instance = AppException.getInstance();
        instance.init(AppContext.instance);
    }

    // 获取7牛sdk
    public Configuration getConfig7niu() {
        if (config7niu == null) {
            config7niu = new Configuration.Builder()
                    .chunkSize(256 * 1024)
                    .putThreshhold(512 * 1024)
                    .connectTimeout(10)
                    .responseTimeout(60)
                    .build();
        }
        return config7niu;
    }

    public UploadManager getUploadManager() {
        if (uploadManager == null) {
            uploadManager = new UploadManager(getConfig7niu());
        }
        return uploadManager;
    }


    public boolean isFirst() {
        return PreferenceUtil.getPrefBoolean(this, "isFirst", true);
    }

    public void setFirst() {
        PreferenceUtil.setPrefBoolean(this, "isFirst", false);
    }

    public void setFirst(boolean first) {
        PreferenceUtil.setPrefBoolean(this, "isFirst", first);
    }

    public void saveInfo(WeixinInfo info) {
        PreferenceUtil.put(this, "info", info);
    }

    public WeixinInfo getInfo() {
        return PreferenceUtil.get(this, "info", WeixinInfo.class);
    }

    public String getTime() {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
//        return format.format(new Date());
        return new Date().getTime() + "";
    }

    public String getTimeMillis(String nowTime, String endTime) {
        long time;
        try {
            time = Long.parseLong(endTime) * 1000;
            long now = Long.parseLong(nowTime) * 1000;
            long left = time - now;
            System.out.println("time" + left);
            if (left < 0) {
                return "已结束";
            } else if (left > 1000 * 60 * 60) {
                return "> 1小时";
            } else {
                int minite = (int) (left / 1000 / 60);
                if (minite < 1)
                    minite = 1;
                return "< " + minite + " 分钟";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "> 1小时";
        }
    }

    public Context context() {
        if (_context == null) {
            _context = getApplicationContext();
        }
        return _context;
    }

    public Resources resources() {
        if (_resource == null) {
            _resource = context().getResources();
        }
        return _resource;
    }

    /**
     * 获取用户的信息
     *
     * @return
     */
    public UserInfo getUserInfo() {
        if (null == this.userInfo || null == this.userInfo.weixin) {
            this.userInfo = new UserInfo();
            this.userInfo.level = sp.getInt("level", 0);
            this.userInfo.uid = sp.getInt("uid", 0);
            this.userInfo.weixin = sp.getString("weixin", null);
            this.userInfo.username = sp.getString("username", null);
            this.userInfo.token = sp.getString("token", null);
            this.userInfo.imageurl = sp.getString("imageurl", null);
            this.userInfo.on_phone = sp.getString("on_phone", null);
            this.userInfo.state = sp.getString("state", null);
            this.userInfo.score = sp.getString("score", null);
            this.userInfo.balance = sp.getInt("userbalance", 0);
            this.userInfo.hotnum = sp.getInt("hotnum", 0);
            this.userInfo.city = sp.getString("city_c", "");
            this.userInfo.province = sp.getString("province_c", "");
            this.userInfo.emali = sp.getString("emali_c", "");
            if (sp.getString("extras", null) != null) {
                List<UserInfo.ExtraEntity> list = new Gson().fromJson(
                        sp.getString("extras", null), new TypeToken<List<UserInfo.ExtraEntity>>() {
                        }.getType());
                this.userInfo.setExtras(list);
            }
        }
        return this.userInfo;
    }

    public void setNoLine() {
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("online", false);
        edit.commit();
    }

    public void setDownId(Long id) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putLong("dowid", id);
        edit.commit();
    }

    public Long getDownId() {
        return sp.getLong("dowid", 0);
    }

    public void setMsgNum(int num) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt("msgnum", num);
        edit.commit();
    }

    public int getMsgNum() {
        return sp.getInt("msgnum", 0);
    }

    public static void setMaintabeactivity(MainTabActivity maintabeactivity) {
        AppContext.maintabeactivity = maintabeactivity;
    }

    private void setChannel() {
        ApplicationInfo appInfo = null;
        try {
            appInfo = this.getPackageManager()
                    .getApplicationInfo(getPackageName(),
                            PackageManager.GET_META_DATA);
            String msg = appInfo.metaData.getInt("UMENG_CHANNEL") + "";
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("channel", msg);
            edit.commit();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getChannel() {
        String string = sp.getString("channel", "appstore");
        if ("0".equals(string)) {
            string = "appstore";
        }
        return string;
    }

    public void setEnterpriseTDC(String path) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("etdc", path);
        edit.commit();
    }

    public String getEnterpriseTDC() {
        return sp.getString("etdc", null);
    }

    public void setUserTDC(String path) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("utdc", path);
        edit.commit();
    }

    public String getUserTDC() {
        return sp.getString("etdc", null);
    }

    public Config getAppConfig() {
        return appconfig;
    }

    public void setAppConfig(Config config) {
        this.appconfig = config;
    }

    public void setAdvertisement(Advertisement advertisement) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("adtype", advertisement.getType());
        edit.putString("adtimes", advertisement.getTimes());
        edit.putString("adlinkurl", advertisement.getLinkurl());
        edit.putString("adimgurl", advertisement.getImgurl());
        edit.putString("id", advertisement.getId());
        edit.putString("startdate", advertisement.getStartdate());
        edit.putString("enddate", advertisement.getEnddate());
        edit.putString("title", advertisement.getTitle());
        edit.commit();
    }

    public Advertisement getAdvertisement() {
        Advertisement ad = new Advertisement();
        ad.setType(sp.getString("adtype", "3"));
        ad.setTimes(sp.getString("adtimes", "5"));
        ad.setLinkurl(sp.getString("adlinkurl", null));
        ad.setImgurl(sp.getString("adimgurl", null));
        ad.setId(sp.getString("id", null));
        ad.setStartdate(sp.getString("startdate", null));
        ad.setEnddate(sp.getString("enddate", null));
        ad.setTitle(sp.getString("title", null));
        return ad;
    }

    public void setOldinstance(Activity oldinstance) {
        this.oldinstance = oldinstance;
    }

    public void setOneActivity(Activity oneActivity) {
        if (this.oldinstance != oneActivity) {
            this.oldinstance = this.oneActivity;
        }
        this.oneActivity = oneActivity;
    }

    public Activity getOldinstance() {
        if (oldinstance == null || oldinstance == oneActivity) {
            return AppManager.getAppManager().getOldActivity();
        } else {
            return oldinstance;
        }
    }

    public Activity getOneActivity() {
        return oneActivity;
    }

    public void setPersonageOnLine(Boolean online) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("personageonline", online);
        edit.commit();
    }

    public Boolean getPersonageOnLine() {
        boolean personageonline = sp.getBoolean("personageonline", false);
        return personageonline;
    }

    public void setCpassword(String password) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("cpassword", password);
        edit.commit();
    }

    public String getCpassword() {
        return sp.getString("cpassword", null);
    }

    public void setOPTNoShow() {
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("optnoshow", true);
        edit.commit();
    }

    public boolean getOptNoShow() {
        return sp.getBoolean("optnoshow", false);
    }

    public void setEnMoney(String money) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("enmoney", money);
        edit.commit();
    }

    public String getEnMoney() {
        return sp.getString("enmoney", "0.00");
    }

    /**
     * 登陆类型
     *
     * @param type 0 手机  1.微信
     */
    public void setLoginType(String type) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("logintype", type);
        edit.commit();
    }

    public String getLoginType() {
        String logintype = sp.getString("logintype", "0");
        return logintype;
    }

    public void setWeixinInfo(String info) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("weixininfo", info);
        edit.commit();
    }

    public String getWeixinInfo() {
        String logintype = sp.getString("weixininfo", null);
        return logintype;
    }

    public void setPEUser(User_For_pe peuser) {
        this.peuser = peuser;
        SharedPreferences.Editor edit = sp.edit();
        Gson g = new Gson();
        String s = g.toJson(peuser);
        edit.putString("peuser", s);
        edit.commit();
    }

    public void setUserLogin(UserBean userBean) {
        this.sign = userBean.getSign();
        this.token = userBean.getToken();
        this.userBean = userBean;
        SharedPreferences.Editor edit = sp.edit();
        Gson g = new Gson();
        String s = g.toJson(userBean);
        edit.putString("userBean", s);
        edit.commit();
    }

    public void setUserLoginOut() {
        this.sign = "";
        this.token = "";
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("userBean", null);
        edit.putString("userMessageBean", null);
        edit.commit();
    }

    public void setUserMessageBean(UserMessageBean userMessageBean) {
        this.userMessageBean = userMessageBean;
        SharedPreferences.Editor edit = sp.edit();
        Gson g = new Gson();
        String s = g.toJson(userMessageBean);
        edit.putString("userMessageBean", s);
        edit.commit();
    }

    public void setUserAddress(AddressBean bean) {
        SharedPreferences.Editor edit = sp.edit();
        Gson g = new Gson();
        String s = g.toJson(bean);
        edit.putString("userAddress", s);
        edit.commit();
    }

    public AddressBean getUserAddress() {
        String peuserStr = sp.getString("userAddress", null);
        if (peuserStr == null) {
            return null;
        } else {
            Gson gson = new Gson();
            try {
                AddressBean bean = gson.fromJson(peuserStr, AddressBean.class);
                return bean;
            } catch (Exception e) {
                return null;
            }

        }

    }

    public UserMessageBean getUserMessageBean() {
        if (this.userMessageBean == null) {
            String peuserStr = sp.getString("userMessageBean", null);
            if (peuserStr == null) {
                this.userMessageBean = new UserMessageBean();
                return this.userMessageBean;
            } else {
                Gson gson = new Gson();
                this.userMessageBean = gson.fromJson(peuserStr, UserMessageBean.class);
                return this.userMessageBean;
            }
        } else {
            return this.userMessageBean;
        }
    }

    public UserBean getUserLogin() {
        if (this.userBean == null) {
            String peuserStr = sp.getString("userBean", null);
            if (peuserStr == null) {
                this.userBean = new UserBean();
                return this.userBean;
            } else {
                Gson gson = new Gson();
                this.userBean = gson.fromJson(peuserStr, UserBean.class);
                this.sign = userBean.getSign();
                this.token = userBean.getToken();
                this.app_bate = "1";
                this.userBean = userBean;
                return this.userBean;
            }
        } else {
            return this.userBean;
        }
    }

    public User_For_pe getPEUser() {
        if (this.peuser == null) {
            String peuserStr = sp.getString("peuser", null);
            if (peuserStr == null) {
                this.peuser = new User_For_pe();
                return this.peuser;
            } else {
                Gson gson = new Gson();
                this.peuser = gson.fromJson(peuserStr, User_For_pe.class);
                return this.peuser;
            }
        } else {
            return this.peuser;
        }
    }
}
