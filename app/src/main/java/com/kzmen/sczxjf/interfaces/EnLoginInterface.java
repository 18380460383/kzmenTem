package com.kzmen.sczxjf.interfaces;

/**
 * describe:
 * notice:
 * Created by FuPei on 2016/5/20.
 */
public interface EnLoginInterface {
    /**
     * 验证成功
     * @param token
     */
    void onLoginSuccess(String token);

    /**
     * 验证信息失败
     * @param reason
     */
    void onLoginFail(String reason);

    /**
     * 网络连接错误
     */
    void onNetError();
}
