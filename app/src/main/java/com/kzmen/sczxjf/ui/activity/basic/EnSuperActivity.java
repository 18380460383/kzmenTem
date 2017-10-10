package com.kzmen.sczxjf.ui.activity.basic;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.view.EshareDialogFragment;

/**
 * describe:
 * notice:
 * Created by FuPei on 2016/5/31.
 */
public abstract class EnSuperActivity extends SuperActivity {

    public void setAcitityTitle(String title) {
        try{
            TextView tv = (TextView) findViewById(R.id.title_name);
            if(tv != null) {
                tv.setText(title);
            }
        } catch (Exception e) {

        }
    }

    public void setOnBackOnclick(View.OnClickListener listener) {
        try{
            View view_back = findViewById(R.id.back);
            view_back.setOnClickListener(listener);
        } catch (Exception e) {

        }
    }

    public void showExitDialog() {
        final EshareDialogFragment dialog = new EshareDialogFragment();
        dialog.setViewUI("还没有完成订单，是否退出?", "确定", "取消", new EshareDialogFragment.EshareDialogClickListener() {
            @Override
            public void onClick() {
                finish();
            }

            @Override
            public void onCancel() {
                dialog.dismiss();
            }
        });
        dialog.show(getFragmentManager(), "hehe");
    }

    public void goActivity(Class cla) {
        Intent intent = new Intent(this, cla);
        startActivity(intent);
    }
}
