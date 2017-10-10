package com.kzmen.sczxjf;

/**
 * describe:
 * notice:
 * Created by FuPei on 2016/5/20.
 */
public class EnConstants {
    public static final String MEIQIA_KEY="2ed8f757771421b34cf754d01978e647";
    /**
     * 更新金钱消息广播
     */
    public static final String BROCAST_LOGIN_SUCCESS = "com.jiebian.update.money";

    /**
     * 添加到购物车成功广播
     */
    public static final String BROCAST_FINISH_SHOP = "com.jiebian.finish.shop";

    /**
     * 下单完成广播
     */
    public static final String CREATE_ORDER = "com.jiebian.create.order";

    private static final String IP_DEBUG = "http://ipx.login.360netnews.com";

    private static final String IP_OFFICAIL = "http://login.360netnews.com";

    private static final String IP = IP_OFFICAIL;

    public static final String URL_API_OFFICAIL = "http://api.360netnews.com";

    public static final String URL_API_DEBUG = "http://ipx.api.360netnews.com";

    public static final String URL_API = URL_API_OFFICAIL;
    private static final String IMG_DEBUG = "http://ipx.2016.360netnews.com/";

    private static final String IMG_OFFICAIL = "http://chuanbo.360netnews.com/";

    public static final String IMG_HTTP = IMG_DEBUG;


    /**
     * 企业端登录
     * account, pwd, sign(随便写)
     */
    public static final String URL_LOGIN = IP + "/web/account/signon";
    /**
     * 获取Token
     */
    public static final String URL_GET_TOKEN = IP + "/common/api/gettoken";

    /**
     * 上传文件
     */
    public static final String URL_UPLOAD_FILE = URL_API + "/index.php/index/upload";

    /**
     * 获取微博列表
     * number=20&page=1&cid=null
     */
    public static final String[] URL_GET_WEIBO = {"WeiboMedia", "findPagerWeibo"};

    /**
     * 获取微信列表
     * number=20&page=1&cid=null
     */
    public static final String[] URL_GET_WEIXIN = {"WeixinMedia", "findPagerWeixin"};

    /**
     * 注册
     */
    public static final String[] URL_POST_REGISTER = {"OwnAccount", "userRegister"};
    /**
     * 企业获取验证码
     */
    public static final String[] URL_POST_CODE = {"OwnAccount", "getMsgCode"};
    /**
     * 企业财务记录
     *
     */
    public static final String[] URL_POST_FINANCIAL_RECORD = {"BalanceChange", "findPagerBalanceChange"};
    /**
     * 查询企业最大开发票金额
     */
    public static final String[] URL_POST_INVOICE_MAXNUM= {"BalanceChange", "findEnterpriseBalanceChangeByUid"};
    /**
     * 企业开发票金额
     */
    public static final String[] URL_POST_INVOICE= {"InvoiceApply", "saveInvoiceApplyInfo"};
    /**
     * 用个人端账号登录企业端
     *
     * */
    public static final String[] URL_BINDING_P_FOR_E= {"OwnAccount", "jiebianBundingNetnews"};
    /**
     * 获取媒体信息
     * number=20&page=1&cid=null
     */
    public static final String[] URL_GET_MEDIA = {"AdvertorialsPrice", "findPagerAdvertorialsMediaPrice"};
    /**
     * 查找不同媒体的类型列表
     * key:WEIXIN_CATEGORY, WEIBO_CATEGORY, SOFT_ARTICLE_INDUSTRY
     */
    public static final String[] URL_GET_TYPE = {"DictionaryItem", "findListDictionaryItem"};
    /**
     * 获取企业金钱
     */
    public static final String[] URL_GET_MONEY = {"Message", "countOwnCount"};
    /**
     * 获取企业信息
     */
    public static final String[] URL_GET_ENPRISE_INFO = {"EnterpriseInfo", "findOneEnterprise"};
    /**
     * 上传企业信息
     */
    public static final String[] URL_POST_ENPRISE_INFO = {"EnterpriseInfo", "saveEnterpriseInfoByApp"};
    /**
     * 登录app
     */
    public static final String[] URL_LOGIN_APP = {"OwnAccount","loginApp"};
    /**
     * 检查手机号是否存在
     */
    public static final String[] URL_CHECK_PHONE = {"OwnAccount", "checkedMobile"};
    /**
     * 获取手机验证码
     * mobile_no, flag:1, type:find
     */
    public static final String[] URL_GET_CODE = {"OwnAccount", "getMsgCode"};
    /**
     * 验证验证码是否正确
     * msg_code, flag:1, mobile_no
     */
    public static final String[] URL_CHECK_CODE = {"OwnAccount", "codeMobile"};
    /**
     * 最后修改密码
     * mobile_no, newpwd
     */
    public static final String[] URL_ALTER_PWD = {"OwnAccount", "findPwd"};
    /**
     * 写手列表
     */
    public static final String[] URL_GET_WRITER = {"WriterInfo", "findPagerWriter"};
    /**
     * 添加媒体信息到购物车
     */
    public static final String[] URL_SHOPCAR_MEDIA = {"Car", "saveCarManuscriptInfo"};
    /**
     * 添加微信信息到购物车
     */
    public static final String[] URL_SHOPCAR_WEIXIN = {"Car", "saveCarWeixinInfo"};
    /**
     * 添加微博信息到购物车
     */
    public static final String[] URL_SHOPCAR_WEIBO = {"Car", "saveWeiboCarInfo"};
    /**
     * 微信详情信息
     */
    public static final String[] URL_DETAIL_WEIXIN = {"WeixinMedia", "getweixininfo"};
    /**
     * 获取记者列表
     */
    public static final String[] URL_GET_REPORTER = {"ReporterInfo", "find_reporter_pager"};
    /**
     * 获取购物车以及订单数量
     */
    public static final String[] URL_COUNT_SHOP = {"EnterpriseInfo", "numCarOrder"};

    /**
     * 将写手推广添加到购物车
     */
    public static final String[] URL_SHOPCAR_WRITER = {"Car", "saveCarRequiresInfo"};
}
