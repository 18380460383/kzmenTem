package com.kzmen.sczxjf.ui.activity.personal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.Constants;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.BaseBean;
import com.kzmen.sczxjf.bean.DetailEXBean;
import com.kzmen.sczxjf.bean.returned.OrderForm;
import com.kzmen.sczxjf.bean.user.User_For_pe;
import com.kzmen.sczxjf.ui.activity.BaseWebActivity;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.utils.AppUtils;
import com.kzmen.sczxjf.net.NetworkDownload;
import com.kzmen.sczxjf.util.EshareLoger;
import com.kzmen.sczxjf.view.SquareImageView;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;

/**
 * 说明：
 * note：
 * Created by FuPei
 * on 2016/1/26 at 11:08
 */
public class DetailEXActivity extends SuperActivity {

    public static final String EXTRA_ID = "id";
    public static final String ORDER = "order";
    private final int UPDATE_TIME = 1;
    /**
     * 确认收货
     */
    private final String STATE_CANTION = "3";
    /**
     * 取消订单
     */
    private final String STATE_CANCEL = "9";
    /**
     * 发送付款的请求
     */
    private final int REQUEST_CODE_PAY = 1;
    @InjectView(R.id.ex_tv_order)
    public TextView tv_order;
    @InjectView(R.id.ex_tv_datatime)
    public TextView tv_datatime;
    @InjectView(R.id.ex_tv_name)
    public TextView tv_name;
    @InjectView(R.id.ex_tv_address)
    public TextView tv_address;
    @InjectView(R.id.ex_tv_tel)
    public TextView tv_tel;
    @InjectView(R.id.ex_tv_state)
    public TextView tv_state;
    @InjectView(R.id.ex_tv_title)
    public TextView tv_goodsTitle;
    @InjectView(R.id.ex_tv_score)
    public TextView tv_score;
    @InjectView(R.id.ex_tv_money)
    public TextView tv_money;
    @InjectView(R.id.ex_tv_express)
    public TextView tv_express;
    @InjectView(R.id.ex_tv_express_num)
    public TextView tv_expressNum;
    @InjectView(R.id.ex_tv_num)
    public TextView tv_num;
    @InjectView(R.id.ex_iv_image)
    public SquareImageView iv_image;
    @InjectView(R.id.ex_tv_logistics)
    public TextView tv_logistics;
    @InjectView(R.id.ex_tv_state_bottom)
    public TextView tv_bottom;
    @InjectView(R.id.ex_rly_state_bottom)
    public RelativeLayout rly_bottom;
    @InjectView(R.id.ex_tv_time)
    public TextView tv_time;
    @InjectView(R.id.ex_tv_cancel)
    public TextView tv_cancel;
    @InjectView(R.id.ex_ly_state)
    public LinearLayout ly_state;
    @InjectView(R.id.tv_code)
    public TextView tv_code;
    @InjectView(R.id.ly_user)
    public LinearLayout ly_user;

    private String id;
    private ImageLoader mLoader;
    /**
     * 兑换状态码
     */
    private String state_code;
    private OrderForm value;
    /**
     * 是否显示倒计时的UI
     */
    private boolean canShowTime;
    private int secTime;
    /**
     * 物流链接
     */
    private String url_logistics;

    private String num_order;
    private String score;


    @Override
    public void onCreateDataForView() {
        setTitle(R.id.detail_title,"兑换详情");
        initView();
        initListener();
        getData();
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_exchange_detail);
    }

    private void initView() {

        mLoader = ImageLoader.getInstance();
        id = getIntent().getStringExtra(EXTRA_ID);
    }

    private void initListener() {
        tv_logistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogistics();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGoodsState(STATE_CANCEL);
            }
        });

        rly_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGoodsState(STATE_CANTION);
            }
        });
    }

    private void showLogistics() {
        if (!TextUtils.isEmpty(url_logistics)) {
            Intent intent = new Intent(DetailEXActivity.this, BaseWebActivity.class);
            intent.putExtra(BaseWebActivity.EXTRA_TITLE, "物流查询");
            intent.putExtra(BaseWebActivity.EXTRA_URL, url_logistics);
            startActivity(intent);
        } else {
            Toast.makeText(DetailEXActivity.this, "暂不可查看", Toast.LENGTH_SHORT).show();
        }
    }

    private void setUI(DetailEXBean.DataEntity bean) {
        score = bean.getScore();
        value = new OrderForm();
        User_For_pe peUser = AppContext.getInstance().getPEUser();
            double balance = peUser.getBalance();
            value.setBalance(balance);
        value.setMoney(Double.valueOf(bean.getMoney()));
        value.setOrder(bean.getOrder());
        value.setTitle(bean.getGoods_title());
        value.setUid(AppContext.getInstance().getPEUser().getUid());
        state_code = bean.getState();
        setBottomUI(bean);
        mLoader.displayImage(bean.getGoods_image(), iv_image);
        tv_num.setText("数量: " + bean.getNum());
        num_order = bean.getOrder();
        tv_order.setText("订单编号: " + num_order);

        tv_datatime.setText("下单时间: " + bean.getDatetime());
        tv_name.setText("姓名: " + bean.getNickname());
        tv_address.setText("地址: " + bean.getAddress());
        tv_tel.setText("联系电话: " + bean.getTel());
        tv_state.setText("订单状态: " + bean.getState_str());
        url_logistics = bean.getExpress_url();
        tv_goodsTitle.setText(bean.getGoods_title());
        tv_score.setText(getColorText(bean.getScore(), "#ff8307").append("积分"));
        tv_money.setText(getColorText(Float.valueOf(bean.getMoney()) / 100 + "", "#ff8307").append("元"));
        tv_code.setText("兑换码：" + bean.getRedeemcode());
        if (!TextUtils.isEmpty(bean.getExpress())) {
            tv_express.setText("快递公司:" + bean.getExpress());
        }
        if (!TextUtils.isEmpty(bean.getExpress_number())) {
            tv_expressNum.setText("快递编号:" + bean.getExpress_number());
        }
        if (TextUtils.isEmpty(bean.getRedeemcode()) || "0".equals(bean.getType())) {
            tv_code.setVisibility(View.GONE);
            ly_user.setVisibility(View.VISIBLE);
        } else {
            tv_code.setVisibility(View.VISIBLE);
            ly_user.setVisibility(View.GONE);
        }
    }

    /**
     * 获取详情兑换数据
     */
    private void getData() {
        RequestParams params = new RequestParams();
        String uid = AppContext.getInstance().getPEUser().getUid();
        params.add("uid", uid);
        params.add("id", id);
        showProgressDialog(null);
        EshareLoger.logI("uid = " + uid + ", id = " + id);
        NetworkDownload.jsonGetForCode1(this, Constants.URL_EXCHANGE_SHOW, params, new NetworkDownload.NetworkDownloadCallBackJson() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                EshareLoger.logI("detail:\n" + jsonObject.toString());
                BaseBean bean = BaseBean.parseEntity(jsonObject);
                if ("1".equals(bean.code)) {
                    DetailEXBean detailEXBean = DetailEXBean.parseEntity(jsonObject.toString());
                    setUI(detailEXBean.getData());
                } else if ("2".equals(bean.code)) {
                    Toast.makeText(DetailEXActivity.this, "没有此兑换记录", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailEXActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                }
                dismissProgressDialog();
            }

            @Override
            public void onFailure() {
                EshareLoger.logI("onFailure");
                dismissProgressDialog();
            }
        });
    }

    private void setBottomUI(DetailEXBean.DataEntity entity) {
        String state = entity.getState();
        if ("0".equals(state)) {
            tv_bottom.setText("付款");
            secTime = entity.getSec();
            canShowTime = true;
            setTimeUI();
            rly_bottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    payMoney();
                }
            });
            ly_state.setVisibility(View.VISIBLE);
            tv_express.setVisibility(View.GONE);
            tv_expressNum.setVisibility(View.GONE);
            rly_bottom.setVisibility(View.VISIBLE);
            tv_logistics.setVisibility(View.GONE);
        } else if ("1".equals(state)) {
            ly_state.setVisibility(View.GONE);
            rly_bottom.setVisibility(View.GONE);
            tv_logistics.setVisibility(View.GONE);
        } else if ("2".equals(state)) {
            tv_bottom.setText("确认收货");
            ly_state.setVisibility(View.GONE);
            rly_bottom.setVisibility(View.VISIBLE);
            tv_logistics.setVisibility(View.VISIBLE);
        } else if ("3".equals(state)) {
            ly_state.setVisibility(View.GONE);
            rly_bottom.setVisibility(View.GONE);
            tv_logistics.setVisibility(View.VISIBLE);
        } else {
            ly_state.setVisibility(View.GONE);
            rly_bottom.setVisibility(View.GONE);
            tv_logistics.setVisibility(View.GONE);
        }
    }

    /**
     * 获取指定颜色的文字
     *
     * @param text
     * @param color
     * @return
     */
    private SpannableStringBuilder getColorText(String text, String color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.parseColor(color));
        builder.setSpan(redSpan, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    /**
     * 改变订单的状态
     * @param state 需要设置订单的状态
     */
    private void setGoodsState(final String state) {
        Map<String, String> map = new HashMap<>();
        String uid = AppContext.getInstance().getPEUser().getUid();
//        uid = "69";
        map.put("uid", uid);
        map.put("state", state);
        map.put("order", num_order);
        EshareLoger.logI("uid = " + uid + ", state = " + state + ", order = " + num_order);
        RequestParams params = AppUtils.getParm(map);
        showProgressDialog(null);
        NetworkDownload.jsonPostForCode1(this, Constants.URL_ORDER_STATE, params,
                new NetworkDownload.NetworkDownloadCallBackJson() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) throws JSONException {
                        canShowTime = false;
                        Intent intent = new Intent();
                        intent.setAction(Constants.FRAGMENT_MONEY);
                        sendBroadcast(intent);
                        dismissProgressDialog();
                        getData();
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(DetailEXActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                        dismissProgressDialog();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        canShowTime = false;
        getData();
    }

    /**
     * 付款
     */
    private void payMoney() {
        if (secTime > 0) {
            Intent intent = new Intent(DetailEXActivity.this, ShopOrderActivity.class);
            if (value != null) {
                intent.putExtra(ORDER, value);
                intent.putExtra(ShopOrderActivity.CLASS_SOURCE, "DetailEXActivity");
            }
            startActivityForResult(intent, REQUEST_CODE_PAY);
        } else {
            Toast.makeText(DetailEXActivity.this, "此订单已超时，自动被取消了", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 设置倒计时的UI
     */
    private void setTimeUI() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (canShowTime) {
                    try {
                        Message msg = new Message();
                        msg.what = UPDATE_TIME;
                        handler.sendMessage(msg);
                        Thread.sleep(1 * 1000);
                        secTime--;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                secTime = 0;
            }
        }).start();
    }

    //更新倒计时的UI
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == UPDATE_TIME && canShowTime) {
                tv_time.setText(secToString(secTime));
            }
        }
    };


    /**
     * 将秒转为时分秒
     *
     * @return 时分秒的字符串
     */
    private String secToString(int time) {
        String str_time = "";
        String hour = "";
        String min = "";
        String sec = "";
        if (time >= 3600) {
            hour = time / 3600 + "";
        }
        if (time > 60) {
            min = time / 60 + "";
        }
        if (time > 0) {
            sec = time % 60 + "";
        }
        if (!TextUtils.isEmpty(hour)) {
            if (hour.length() == 1) {
                hour = "0" + hour;
            }
            str_time += hour + ":";
        }

        if (!TextUtils.isEmpty(min)) {
            if (min.length() == 1) {
                min = "0" + min;
            }
            str_time += min + ":";
        } else {
            str_time += "00:";
        }

        if (!TextUtils.isEmpty(sec)) {
            if (sec.length() == 1) {
                sec = "0" + sec;
            }
            str_time += sec;
        }
        return str_time;
    }


    /**
     * 关闭线程
     */
    @Override
    protected void onStop() {
        canShowTime = false;
        super.onStop();
    }
}
