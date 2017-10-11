package com.kzmen.sczxjf.interfaces;

import java.util.List;

/**
 * Created by pjj18 on 2017/10/11.
 */

public interface PermissionListener {
    void onGranted();//已授权

    void onDenied(List<String> deniedPermission);//未授权
}
