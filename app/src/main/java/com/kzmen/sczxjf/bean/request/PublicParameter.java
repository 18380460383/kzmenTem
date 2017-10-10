package com.kzmen.sczxjf.bean.request;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.bean.user.User_For_pe;
import com.kzmen.sczxjf.utils.AppUtils;
import com.loopj.android.http.RequestParams;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/30.
 */
public class PublicParameter {
    private Context context;
    private static PublicParameter publicParameter;
    /**
     *  设备 唯一id  (有就传)
     */
   private String  public_uuid;
    /**
     *  渠道 (必有的)
     */
    private String public_score;
    /**
     *  设备类型  (必有的) 1:ios，2:Android
     */
    private String public_deviceType;
    /**
     *    当前版本(必有的)
     */
    private String public_version;
    /**
     *  :微信id (有就传)
     */
    private String public_weiXinId;
    /**
     *  : 网络类型 1:非wifi  2:wifi
     */
    private String public_reachabilityType;
    /**
     *  : 网络类型字符串 notWifi、wifi
     */
    private String public_reachability;
    /**
     *  ：当前 ip
     */
    private String public_Ip;
    /**
     * : 当前个人端用户id||企业端用户ID
     */
    private String public_uid;
    public void setNetState(){
        String str=null;
         public_reachabilityType = getNetType(context);
        if("1".equals(public_reachabilityType)){
            public_reachability="notWifi";
        }
        if("2".equals(public_reachabilityType)){
            public_reachability="Wifi";
        }
    }

    private  PublicParameter(Context context,String public_uuid, String public_score, String public_deviceType,
                             String public_version, String public_weiXinId, String public_reachabilityType,
                             String public_reachability, String public_Ip, String public_uid) {
        this.context=context;
        this.public_uuid = public_uuid;
        this.public_score = public_score;
        this.public_deviceType = public_deviceType;
        this.public_version = public_version;
        this.public_weiXinId = public_weiXinId;
        this.public_reachabilityType = public_reachabilityType;
        this.public_reachability = public_reachability;
        this.public_Ip = public_Ip;
        this.public_uid = public_uid;
    }

    public static PublicParameter  getPublicParameter(Context context){
        AppContext instance = AppContext.getInstance();
        User_For_pe peUser = instance.getPEUser();
        if(null==publicParameter){
            String str=null;
            String netType = getNetType(context);
            if("1".equals(netType)){
                str="notWifi";
            }
            if("2".equals(netType)){
                str="Wifi";
            }

            publicParameter=new PublicParameter(context,"", instance.getChannel(),"2",
                    AppUtils.getAppVersion(context) + "",peUser.getWeixin(),
                    netType,str,getLocalIpAddress()+"",peUser.getUid());
        }
        publicParameter.public_uid=peUser.getUid();
        return publicParameter;
    }
    public  Map<String,String> getMap(){
        Map<String,String> map=new HashMap<>();
        map.put("public_uuid",publicParameter.public_uuid);
        map.put("public_score",publicParameter.public_score);
        map.put("public_deviceType",publicParameter.public_deviceType);
        map.put("public_version",publicParameter.public_version);
        map.put("public_weiXinId",publicParameter.public_weiXinId);
        map.put("public_reachabilityType",publicParameter.public_reachabilityType);
        map.put("public_reachability",publicParameter.public_reachability);
        map.put("public_Ip",publicParameter.public_Ip);
        map.put("public_uid",AppContext.getInstance().getPEUser().getUid());
        return map;
    }
    private static String getNetType(Context context){
        ConnectivityManager connectMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        int type=0;
        try {
             type = info.getType() + 1;
        }catch (Exception e){
            Toast.makeText(context,"当前没有网络请设置",Toast.LENGTH_LONG).show();
            type=1;
        }
        return type+"";
    }
    private static  String getLocalIpAddress() {
        try {
            for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = (NetworkInterface) en.nextElement();
                for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
//这里需要注意：这里增加了一个限定条件( inetAddress instanceof Inet4Address ),主要是在Android4.0高版本中可能优先得到的是IPv6的地址。参考：http://blog.csdn.net/stormwy/article/details/8832164
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {

        }
        return null;
    }
    public RequestParams getRequestParams(RequestParams params){
        params.add("public_uuid", publicParameter.public_uuid);
        params.add("public_score", publicParameter.public_score);
        params.add("public_deviceType", publicParameter.public_deviceType);
        params.add("public_version", publicParameter.public_version);
        params.add("public_weiXinId", publicParameter.public_weiXinId);
        params.add("public_reachabilityType", publicParameter.public_reachabilityType);
        params.add("public_reachability", publicParameter.public_reachability);
        params.add("public_Ip", publicParameter.public_Ip);
        params.add("public_uid", publicParameter.public_uid);
        return params;
    }
}
