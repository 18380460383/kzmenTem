package com.kzmen.sczxjf.cusinterface;

/**
 * Created by Administrator on 2017/8/10.
 */

public interface PlayMessage {
    void prePercent(int percent);
    void time(String start,String end,int pos);
    void playposition(int position);
    void state(int state);
}
