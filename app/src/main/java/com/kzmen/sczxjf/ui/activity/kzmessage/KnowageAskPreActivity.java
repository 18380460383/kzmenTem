package com.kzmen.sczxjf.ui.activity.kzmessage;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.kzmen.sczxjf.AppContext;
import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.adapter.ImagePickerAdapter;
import com.kzmen.sczxjf.bean.kzbean.OrderBean;
import com.kzmen.sczxjf.dialog.SelectDialog;
import com.kzmen.sczxjf.easypermissions.AfterPermissionGranted;
import com.kzmen.sczxjf.easypermissions.AppSettingsDialog;
import com.kzmen.sczxjf.easypermissions.EasyPermissions;
import com.kzmen.sczxjf.imagepicker.ImagePicker;
import com.kzmen.sczxjf.imagepicker.bean.ImageItem;
import com.kzmen.sczxjf.imagepicker.ui.ImageGridActivity;
import com.kzmen.sczxjf.imagepicker.ui.ImagePreviewDelActivity;
import com.kzmen.sczxjf.interfaces.OkhttpUtilResult;
import com.kzmen.sczxjf.net.OkhttpUtilManager;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.ui.activity.menu.PayTypeAcitivity;
import com.kzmen.sczxjf.utils.TextUtil;
import com.vondear.rxtools.view.RxToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 提问
 */

public class KnowageAskPreActivity extends SuperActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener, EasyPermissions.PermissionCallbacks {
    @InjectView(R.id.tv_cancle)
    TextView tvCancle;
    @InjectView(R.id.back)
    PercentRelativeLayout back;
    @InjectView(R.id.title_name)
    TextView titleName;
    @InjectView(R.id.tv_pre)
    TextView tvPre;
    @InjectView(R.id.et_content)
    EditText etContent;
    @InjectView(R.id.iv_choice_pic)
    ImageView ivChoicePic;
    @InjectView(R.id.tv_limite)
    TextView tvLimite;
    @InjectView(R.id.tv_price)
    TextView tvprice;
    @InjectView(R.id.tv_dialog_text)
    TextView tvDialogText;
    @InjectView(R.id.ll_show_dialog)
    LinearLayout llShowDialog;
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.sw_nim)
    Switch swNim;
    @InjectView(R.id.sw_pri)
    Switch swPri;
    @InjectView(R.id.kz_tiltle)
    LinearLayout kzTiltle;
    @InjectView(R.id.tb_nim)
    ToggleButton tbNim;
    @InjectView(R.id.tb_pri)
    ToggleButton tbPri;
    private String cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "");
        initView();
        initWidget();
    }

    private void initWidget() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        tvprice.setText("￥  " + AppContext.getInstance().knowPrice);
    }

    private void initView() {
        etContent.addTextChangedListener(etWatcher);
    }

    TextWatcher etWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            tvLimite.setText(s.length() + "/150");
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_knowage_ask_pre);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            cid = bundle.getString("cid");
        }
        back.setVisibility(View.GONE);
    }

    private void preQues() {
        String content = etContent.getText().toString();
        if (TextUtil.isEmpty(content)) {
            RxToast.normal("内容不能为空");
            return;
        }
        //  String nim = swNim.isChecked() ? "1" : "0";
        String nim = tbNim.isChecked() ? "1" : "0";
        //String pri = swPri.isChecked() ? "1" : "0";
        String pri = tbPri.isChecked() ? "1" : "0";

        Map<String, String> params = new HashMap<>();
        params.put("data[type]", "2");
        params.put("data[cid]", cid);
        params.put("data[content]", content);
        params.put("data[isopen]", pri);
        params.put("data[isanony]", nim);
        List<File> fileList = new ArrayList<>();
        for (ImageItem bean : selImageList) {
            File file = new File(bean.path);
            fileList.add(file);
        }

        OkhttpUtilManager.postObjec(this, "Question/addQuestion", params, fileList, new OkhttpUtilResult() {
            @Override
            public void onSuccess(int type, String data) {
                try {
                    Log.e("tst", data);
                    Gson gson = new Gson();
                    JSONObject object = null;
                    object = new JSONObject(data);
                    OrderBean orderBean = gson.fromJson(object.getString("data"), OrderBean.class);
                    Intent intent = new Intent(KnowageAskPreActivity.this, PayTypeAcitivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("orderBean", orderBean);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorWrong(int code, String msg) {
                Log.e("tst", msg);
            }
        });
    }

    @OnClick({R.id.tv_cancle, R.id.tv_pre, R.id.iv_choice_pic, R.id.ll_show_dialog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancle:
                finish();
                break;
            case R.id.tv_pre:
                preQues();
                //EToastUtil.show(KnowageAskPreActivity.this, "发布吧");
                break;
            case R.id.iv_choice_pic:
                List<String> names = new ArrayList<>();
                names.add("拍照");
                names.add("相册");
                showDialog(new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0: // 直接调起相机
                                //打开选择,本次允许选择的数量
                                cameraTask();
                                break;
                            case 1:
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent1 = new Intent(KnowageAskPreActivity.this, ImageGridActivity.class);
                                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                                break;
                            default:
                                break;
                        }
                    }
                }, names);
                break;
            case R.id.ll_show_dialog:
                Intent intent1 = new Intent(KnowageAskPreActivity.this, WebAcitivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", "提问-》免责");
                bundle.putString("url", OkhttpUtilManager.URL_ASK);
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;
        }
    }

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 3;               //允许选择图片最大数


    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style.transparentFrameWindowStyle, listener, names);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    private static final int RC_CAMERA_PERM = 123;

    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void cameraTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
            Intent intent = new Intent(KnowageAskPreActivity.this, ImageGridActivity.class);
            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
            startActivityForResult(intent, REQUEST_CODE_SELECT);
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera),
                    RC_CAMERA_PERM, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
        Intent intent = new Intent(KnowageAskPreActivity.this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
