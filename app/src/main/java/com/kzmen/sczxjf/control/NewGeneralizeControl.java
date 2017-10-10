package com.kzmen.sczxjf.control;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.kzmen.sczxjf.ebean.Generalize;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2016/3/4.
 */
public class NewGeneralizeControl {
    private static Context context;
    private static NewGeneralizeControl newGeneralizeControl;
    private Generalize generalize;
    private static SharedPreferences sp;
    public static NewGeneralizeControl getNewGeneralizeControl(Context thiscontext){
        if(null==newGeneralizeControl){
            newGeneralizeControl=new NewGeneralizeControl();
            context=thiscontext;
            sp=context.getSharedPreferences("pushroles", Context.MODE_PRIVATE);
        }
        return newGeneralizeControl;
    }
    public void setGeneralize(Generalize generalize) {
        this.generalize = generalize;
        Gson g=new Gson();
        String s = g.toJson(generalize);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("generalize",s);
        edit.commit();
    }

    public Generalize getGeneralize() {
        Generalize generalize;
        Gson g=new Gson();
        String generalize1 = sp.getString("generalize", null);
        if(TextUtils.isEmpty(generalize1)){
            generalize=new Generalize();
        }else {
            generalize=g.fromJson(generalize1, Generalize.class);
        }

        return generalize;
    }

    public static void setNull() {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("generalize","");
        edit.commit();
    }
}
