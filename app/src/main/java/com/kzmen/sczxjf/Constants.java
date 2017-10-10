package com.kzmen.sczxjf;

/**
 * @author wu
 * @version 1.0
 */
public class Constants {
    //APP_ID 替换为你的应用从官方网站申请到的合法appId
    public static final String APP_ID = "wxcea3cc825740d65a";
    public static final String SECRET = "4b815e6d60c7749ca5c4df27e40af350";

    public static final String DES_KEY = "ooCZc5DIoVpUrsJ7JKiJFKb5OZmLw2Ob";

    /*获取服务器各种各种链接的接口*/
    public static final String SERVER_API_CONFIG = "http://api.jiebiannews.com/config.php";


    public static final String URL_GET_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static final String URL_GET_USERINFO = "https://api.weixin.qq.com/sns/userinfo";

    /**
     * 客户端PHP正式（黄）
     */
    public static final String PHP_SERVER_Z = "http://api.jiebiannews.com/api.php/Cmd/";
    /**
     * 服务器一根路径（赵洪非）
     */
    public static final String URL_SERVER_ONE = "http://api.jiebiannews.com/api.php/App/";

    /**
     * 增加点击次数
     */
    public static final String URL_SERVER_UP_LOAD_HITS = URL_SERVER_ONE + "UploadHits";

    /**
     * 图片上传
     */
    public static final String URL_SERVER_UPLOAD_PIC = URL_SERVER_ONE + "UploadPic";

    /**
     * 绑定手机号
     */
    public static final String URL_SERVER_BINDING_MOBILE = URL_SERVER_ONE + "UserPhone";

    /**
     * 判断手机号码
     */
    public static final String URL_USER_IS_PHONE = URL_SERVER_ONE + "UserIsPhone";

    /**
     * 获取消息列表接口
     */
    public static final String URL_GET_MESSAGES = URL_SERVER_ONE + "MessageList";
    public static final String URL_PUT_JPUSID = URL_SERVER_ONE + "UserJpush";
    public static final String URL_GET_MSGNUM = URL_SERVER_ONE + "MessageReadNum";
    /**
     * 获取详细的消息信息
     */
    public static final String URL_GET_DETAIL_MSG = URL_SERVER_ONE + "MessageShow";


    /**
     * 获取公众OpenId
     */
    public static final String URL_ATTENTION = PHP_SERVER_Z + "checkGuanzhu";
    /**
     * 开启应用时上传地址等信息的接口
     */
    public static final String URL_SET_LOCATING = PHP_SERVER_Z + "setUserLocation";

    /**
     * 获取推广的详情
     */
    public static final String URL_GET_C_D = PHP_SERVER_Z + "getProjectDetail";

    /**
     * 转发成功后，调用
     */
    public static final String URL_RELAY_SUCCESS = PHP_SERVER_Z + "relaySuccess";
    /**
     * 获取二维码
     */
    public static final String URL_GET_QRCODE = PHP_SERVER_Z + "getShareQrcode";

    /**
     * 获取设置里面的账号信息
     */
    public static final String URL_GET_SETINFO = URL_SERVER_ONE + "UserInfo";
    /**
     * 设置接收推送
     */
    public static final String URL_SET_PUSHINFO = URL_SERVER_ONE + "UserUpdatejpush";

    /**
     * 上传图片路径
     */
    public static final String URL_UP_IMAGE_URL = PHP_SERVER_Z + "uploadScreenshot";

    /**
     * 获取行业
     */
    public static final String URL_GET_INDUSTRY = PHP_SERVER_Z + "getIndustryList";
    /**
     * 用户登录
     */
    public static final String URL_POST_LOGIN = PHP_SERVER_Z + "login";

    /**
     * 获取学校
     */
    public static final String URL_GET_SCHOOL = PHP_SERVER_Z + "getSchoolList";

    /**
     * 互动页广告栏
     */
    public static final String URL_GET_AD = PHP_SERVER_Z + "getHomePageList";

    /**
     * 获取七牛token
     */
    public static final String URL_POST_QINIUTOKEN = PHP_SERVER_Z + "getQiniuToken";


    /**
     * 个人端用户修改密码
     */
    public static final String URL_GET_USER_PWD = URL_SERVER_ONE + "UserPwdNew";

    /**
     * 获取分享的展示数据
     */
    public static final String URL_GET_USER_SHARE = PHP_SERVER_Z + "getShareQrcodeNew";


    public static final String URL_ACTIVITY_LIST = URL_SERVER_ONE + "Activity";
    /**
     * 获取启动页广告
     */
    public static final String URL_GET_LOGO_AD = URL_SERVER_ONE + "UserAdvert";
    /**
     * 上传首页广告用户查看信息
     */
    public static final String URL_POST_ADLOOK = URL_SERVER_ONE + "UserLookAdvert";
    /**
     * 获取红包信息
     */
    public static final String URL_GET_REDPACKET = URL_SERVER_ONE + "UserGetRedPackets";
    /**
     * 上传活动点击数
     */
    public static final String URL_POST_ACTHIT = URL_SERVER_ONE + "ActivityHits";
    /**
     * 获取商品列表
     */
    public static final String URL_GET_GOODS = URL_SERVER_ONE + "Goods";
    /**
     * 获取商品详细信息
     */
    public static final String URL_GET_GOODSDETLIS = URL_SERVER_ONE + "GoodsShow";
    /**
     * 积分兑换生成订单
     */
    public static final String URL_POST_USERADDORDER = URL_SERVER_ONE + "UserAddOrder";
    /**
     * 获取用户收货地址
     */
    public static final String URL_GET_GOODSADDRESS = URL_SERVER_ONE + "GetUserAddress";


    /**
     * 获取兑换记录
     */
    public static final String URL_GET_EXCHANGE = URL_SERVER_ONE + "UserOrderList";
    /**
     * 兑换详情
     */
    public static final String URL_EXCHANGE_SHOW = URL_SERVER_ONE + "UserOrderShow";
    /**
     * 积分兑换订单支付
     */
    public static final String URL_POST_PLAYMENT = URL_SERVER_ONE + "UserOrderPay";

    public static final String URL_ORDER_STATE = URL_SERVER_ONE + "UserOrderState";


    /**
     * 获取用户的认证信息
     */
    public static final String URL_GET_ROLELIST = URL_SERVER_ONE + "RoleList";
    /**
     * 更新媒体认证信息
     */
    public static final String URL_UPDATE_ROLE = URL_SERVER_ONE + "UpdateRole";


    /**
     * 认证信息详情
     */
    public static final String URL_ROLES_DETAIL = URL_SERVER_ONE + "Role";

    /**
     * 发送验证码
     */
    public static final String URL_POST_NOTE = URL_SERVER_ONE + "sendmobile";
    /**
     * 判断验证码
     */
    public static final String URL_POST_NOTE_JUDGE_CODE = URL_SERVER_ONE + "codeMobileNoSign";

    public static final String URL_UP = URL_SERVER_ONE + "GETbate";
    /**
     * 邮箱发送验证码
     */
    public static final String URL_POST_EMAILCODE = URL_SERVER_ONE + "sendUserEmailCode";

    /**
     * 绑定修改邮箱
     */
    public static final String URL_SAVE_EMAIL = URL_SERVER_ONE + "saveUserEmail";


    public static final String BROADCAST_RED = "com.brocast.red";


    public static final String FRAGMENT_MONEY = "com.brocast.money";
    public static final String SHOP_PLAY_OK = "com.brocast.shop.play";
    /**
     * 微信授权广播
     */
    public static final String WEIXIN_ACCREDIT = "com.brocast.weixin.accredit";
    /**
     * 微信授权用户信息KEY
     */
    public static final String WEIXIN_ACCREDIT_KEY = "info";
    /**
     * 微信分享广播
     */
    public static final String WEIXIN_SHARE = "com.brocast.weixin.share";
    /**
     * 微信分享相应KEY
     */
    public static final String WEIXIN_SHARE_KEY = "weixin_share";
    /**
     * 微信分享成功code
     */
    public static final int WEIXIN_SHARE_VALUE_SUCCEED = 1;
    /**
     * 微信分享失败code
     */
    public static final int WEIXIN_SHARE_VALUE_FAILURE = 2;
    /**
     * 分享类型为微信
     */
    public static final String SHARE_TYPE_WEIXIN = "1";
    /**
     * 应用文件夹
     */
    public static final String ESHARE_ROOT_PATH = "eshare";
    /**
     * 企业端缓存图片文件夹
     */
    public static final String E_PATH_IMG = "zooms";
    /**
     * 个人端资讯下载图片文件夹
     */
    public static final String C_PATH_DIMG = "download";
    /**
     * ImageLoader图片缓存文件夹
     */
    public static final String LOADER_PATH_IMG = "cache";

    /**
     * 本地数据库
     * 2016.4.26
     */
    public static final String DB_NAME = "eshare_db";
    /**
     * 本地数据库版本
     */
    public static final int DB_VERSION = 3;
    /**
     * 1.1版本资讯表
     */
    public static final String DB_USER_NEWS = "eshare_news";
    /**
     * 1.1版本资讯评论表
     */
    public static final String DB_USER_NEWS_COMMENT = "eshare_news_comment";
    /**
     * 1.1版本PDF下载管理
     */
    public static final String DB_PDF = "eshare_pdf";

}
