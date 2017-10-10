package com.kzmen.sczxjf.interfaces;

import android.view.View;

/**
 * Created by pjj18 on 2017/9/10.
 */

public interface PlayDetailOperate {
    void doPlay(String id,String url);
    void doInput(View view,String qid);
    void doZan(String opType,String id,String state);//setZans("2", item.getQid(), item.getIszan() == 1 ? "0" : "1");
    void doCollect(String opType,String id,String state);//setZans("2", item.getQid(), item.getIszan() == 1 ? "0" : "1");
}
