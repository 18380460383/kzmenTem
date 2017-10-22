package com.kzmen.sczxjf.popuwidow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.AgOkhttpUtilManager;
import com.kzmen.sczxjf.utils.TextUtil;
import com.vondear.rxtools.RxDeviceUtils;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by pjj18 on 2017/10/16.
 */

public class EditPopuwindow extends PopupWindow {

    @InjectView(R.id.ed_name)
    EditText edName;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.ed_percent)
    EditText edPercent;
    @InjectView(R.id.tv_sure)
    TextView tvSure;
    private View view;
    private int opType = 0;//0显示  1 编辑或者添加
    private String name = "";
    private String percent = "";
    private String id = "";
    private String proid = "";
    private Context context;

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public EditPopuwindow(Context context, int opType, String id, String name, String percent) {
        this.view = LayoutInflater.from(context).inflate(R.layout.popuwindow_edit, null);
        this.context = context;
        this.name = name;
        this.percent = percent;
        this.id = id;
        this.opType = opType;
        ButterKnife.inject(this, view);
        // 设置外部可点击
        this.setOutsideTouchable(false);
        // 设置视图
        this.setContentView(this.view);
        this.setContentView(this.view);
        int width = RxDeviceUtils.getScreenWidth(context);
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth((int) (width * 0.8));
        // 设置弹出窗体可点击
        this.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.playlist);
        switch (opType) {
            case 0:
                edName.setVisibility(View.GONE);
                edName.setText(name);
                tvName.setText(name);
                edPercent.setText("" + (Double.valueOf(percent) * 100) + "%");
                tvSure.setText("确定修改");
                break;
            case 1:
                tvName.setVisibility(View.GONE);
                break;
            case 2:
                edName.setHint("请输入合伙人ID");
                edPercent.setVisibility(View.GONE);
                tvSure.setText("确定邀请");
                break;
        }
    }

    @OnClick(R.id.tv_sure)
    public void onViewClicked() {
        switch (opType) {
            case 0:
                if (!isAllRightEd()) {
                    return;
                }
                editChamp();
                break;
            case 1:
                if (!isAllRight()) {
                    return;
                }
                addChamp();
                break;
            case 2:
                invitePar();
                break;
        }
        this.dismiss();
    }

    private boolean isAllRight() {
        if (TextUtil.isEmpty(edName.getText().toString())) {
            RxToast.normal("请输入需要升级的盟主ID");
            return false;
        }
        if (TextUtil.isEmpty(edPercent.getText().toString())) {
            RxToast.normal("请输入分润比例");
            return false;
        }
        return true;
    }
    private boolean isAllRightEd() {
        if (TextUtil.isEmpty(edPercent.getText().toString())) {
            RxToast.normal("请输入分润比例");
            return false;
        }
        return true;
    }

    private void invitePar() {
        if (TextUtil.isEmpty(edName.getText().toString())) {
            RxToast.normal("请输入邀请的合伙人ID");
            return;
        }
        if (TextUtil.isEmpty(proid)) {
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("member_id", AppContext.getInstance().getUserMessageBean().getUid());
        params.put("send_member_id", edName.getText().toString());
        params.put("title", "项目邀请");
        params.put("partner_project_id", id);
        AgOkhttpUtilManager.postNoCacah(context, "users/member_message_add", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                RxToast.normal("邀请成功");
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxToast.normal(msg);
            }
        });
    }

    private void addChamp() {
        Map<String, String> params = new HashMap<>();
        params.put("member_id", AppContext.getInstance().getUserMessageBean().getUid());
        params.put("leader_member_id", edName.getText().toString());
        params.put("leader_share_scale", edPercent.getText().toString());
        AgOkhttpUtilManager.postNoCacah(context, "users/leader_add", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                } catch (Exception e) {
                }
                RxToast.normal("添加成功");
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxToast.normal(msg);
            }
        });
    }

    private void editChamp() {
        Map<String, String> params = new HashMap<>();
        params.put("member_id", AppContext.getInstance().getUserMessageBean().getUid());
        params.put("leader_member_id", id);
        params.put("leader_share_scale", edPercent.getText().toString());
        AgOkhttpUtilManager.postNoCacah(context, "users/leader_edit_share_scale", params, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);

                } catch (Exception e) {
                }
                RxToast.normal("修改成功");
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                RxToast.normal(msg);
            }
        });
    }
}
