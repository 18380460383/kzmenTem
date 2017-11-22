package com.kzmen.sczxjf.util;

import com.kzmen.sczxjf.utils.TextUtil;

/**
 * Created by pjj18 on 2017/11/1.
 */

public class MoneyFormateUtil {
    public static String coinToYuan(String coinPrice) {
        if (TextUtil.isEmpty(coinPrice)) {
            return "0";
        }
        return "" + ((Double) (Double.valueOf(coinPrice) * 1.0 / 100));
    }
}
