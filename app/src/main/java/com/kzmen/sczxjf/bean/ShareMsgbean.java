package com.kzmen.sczxjf.bean;

import com.kzmen.sczxjf.AppContext;

/**
 * Created by Administrator on 2015/12/20.
 */
public class ShareMsgbean {

    /**
     * picUrl : http://192.168.0.104:9010/./Public/images/user/31/qrcode2.png
     * weixin : {"title":"闲着没事，赚点零花钱","des":"那么多时间用在手机上，不如赚点零花钱，立即入圈，当有薪一族","litpic":"http://7xnffx.com1.z0.glb.clouddn.com/180.png","share":"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1beb00cf4c98140e&redirect_uri=http://jzz.360netnews.com/weixin/reg.php?uid=#XEXUID#&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect","replaceUid":"uid=#XEXUID#"}
     */

    private String picUrl;
    private String picTitle;
    /**
     * title : 闲着没事，赚点零花钱
     * des : 那么多时间用在手机上，不如赚点零花钱，立即入圈，当有薪一族
     * litpic : http://7xnffx.com1.z0.glb.clouddn.com/180.png
     * share : https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1beb00cf4c98140e&redirect_uri=http://jzz.360netnews.com/weixin/reg.php?uid=#XEXUID#&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect
     * replaceUid : uid=#XEXUID#
     */

    private WeixinEntity weixin;

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public void setWeixin(WeixinEntity weixin) {
        this.weixin = weixin;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public WeixinEntity getWeixin() {
        return weixin;
    }

    public String getPicTitle() {
        return picTitle;
    }

    public void setPicTitle(String picTitle) {
        this.picTitle = picTitle;
    }

    public static class WeixinEntity {
        private String title;
        private String des;
        private String litpic;
        private String share;
        private String replaceUid;

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public void setLitpic(String litpic) {
            this.litpic = litpic;
        }

        public void setShare(String share) {
            this.share = share;
        }

        public void setReplaceUid(String replaceUid) {
            this.replaceUid = replaceUid;
        }

        public String getTitle() {
            return title;
        }

        public String getDes() {
            return des;
        }

        public String getLitpic() {
            return litpic;
        }

        public String getShare() {
            return share.replace(getReplaceUid(), "uid="+AppContext.getInstance().getPEUser().getUid());
        }

        public String getReplaceUid() {
            return replaceUid;
        }
    }
}
