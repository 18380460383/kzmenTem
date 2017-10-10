package com.kzmen.sczxjf.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;

/**
 * 创建者：Administrator
 * 时间：2016/7/26
 * 功能描述：
 */
public class EmailAndAgency {
    public void showDialog(final Context context,String string){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_email_agency, null);
        View viewById = inflate.findViewById(R.id.close_dialog);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView textDialog = (TextView) inflate.findViewById(R.id.text_dialog);
        textDialog.setText(string);
        View viewByIdok = inflate.findViewById(R.id.ok_dialog);
        viewByIdok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ((SuperActivity)context).finish();
            }
        });
        dialog.setContentView(inflate);
        dialog.show();
    }
}
