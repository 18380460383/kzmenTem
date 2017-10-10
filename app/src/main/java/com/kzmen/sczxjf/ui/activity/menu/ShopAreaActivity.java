package com.kzmen.sczxjf.ui.activity.menu;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.bean.kzbean.CityModel;
import com.kzmen.sczxjf.bean.kzbean.DistrictModel;
import com.kzmen.sczxjf.bean.kzbean.ProvinceModel;
import com.kzmen.sczxjf.test.server.XmlParserHandler;
import com.kzmen.sczxjf.ui.activity.basic.SuperActivity;
import com.kzmen.sczxjf.util.EToastUtil;
import com.kzmen.sczxjf.view.wheelview.OptionsPickerView;
import com.vondear.rxtools.view.RxToast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import butterknife.InjectView;
import butterknife.OnClick;

public class ShopAreaActivity extends SuperActivity {
    @InjectView(R.id.tv_provice)
    TextView tvProvice;
    @InjectView(R.id.ll_edit_provice)
    LinearLayout llEditProvice;
    @InjectView(R.id.tv_city)
    TextView tvCity;
    @InjectView(R.id.ll_edit_city)
    LinearLayout llEditCity;
    @InjectView(R.id.tv_user_name)
    TextView tvUserName;
    @InjectView(R.id.ll_country)
    LinearLayout llCountry;
    @InjectView(R.id.tv_save)
    TextView tvSave;
    private OptionsPickerView pvOptions;
    /* private List<String> proList;
     private List<String> cityList;
     private List<String> countryList;*/
    private List<String> tempList;
    private String provice = "";
    private String city = "";
    private String country = "";


    /**
     * 所有省
     */
    protected List<String> mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, List<String>> mCitisDatasMap = new HashMap<String, List<String>>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, List<String>> mDistrictDatasMap = new HashMap<String, List<String>>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName = "";

    /**
     * 当前区的邮政编码
     */
    protected String mCurrentZipCode = "";

    /**
     * 解析省市区的XML数据
     */

    protected void initProvinceDatas() {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            //*/ 初始化默认选中的省、市、区
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }
            //*/
            mProvinceDatas = new ArrayList<>();
            // mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas.add(provinceList.get(i).getName());
                //mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                List<String> cityLis = new ArrayList<>();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityLis.add(cityList.get(j).getName());
                    //cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    //String[] distrinctNameArray = new String[districtList.size()];
                    List<String> disList = new ArrayList<>();
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        // 区/县对于的邮编，保存到mZipcodeDatasMap
                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        // distrinctNameArray[k] = districtModel.getName();
                        disList.add(districtModel.getName());
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityList.get(j).getName(), disList);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityLis);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateDataForView() {
        setTitle(R.id.kz_tiltle, "送货区域");
       /* proList = new ArrayList<>();
        cityList = new ArrayList<>();
        countryList = new ArrayList<>();*/
        tempList = new ArrayList<>();
        initProvinceDatas();
        initData();
    }

    private void initData() {
        /*for (int i = 0; i < 10; i++) {
            proList.add("省份" + i);
            cityList.add("城市" + i);
            countryList.add("区县" + i);
        }*/
    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_shop_area);
    }

    @OnClick({R.id.ll_edit_provice, R.id.ll_edit_city, R.id.ll_country, R.id.tv_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_edit_provice:
                showSelector(0);
                break;
            case R.id.ll_edit_city:
                if (TextUtils.isEmpty(provice)) {
                    EToastUtil.show(ShopAreaActivity.this, "请先选择省份");
                    return;
                }
                showSelector(1);
                break;
            case R.id.ll_country:
                if (TextUtils.isEmpty(city)) {
                    EToastUtil.show(ShopAreaActivity.this, "请先选择城市");
                    return;
                }
                showSelector(2);
                break;
            case R.id.tv_save:
                if (TextUtils.isEmpty(country)) {
                    EToastUtil.show(ShopAreaActivity.this, "请选择区县后再保存");
                    return;
                }
                String zCode = mZipcodeDatasMap.get(country);
                Intent mIntent = new Intent();
                mIntent.putExtra("data", provice + city + country);
                mIntent.putExtra("province", provice);
                mIntent.putExtra("city", city);
                mIntent.putExtra("area", country);
                mIntent.putExtra("yb", zCode);
                // 设置结果，并进行传送
                this.setResult(1003, mIntent);
                this.finish();
                break;
        }
    }

    private void showSelector(final int type) {
        tempList.clear();
        String title = "";
        switch (type) {
            case 0:
                if (mProvinceDatas.size() == 0) {
                    RxToast.normal("省份数据获取失败");
                    return;
                }
                tempList.addAll(mProvinceDatas);
                title = "省份选择";
                break;
            case 1:
                tempList.addAll(mCitisDatasMap.get(provice));
                title = "城市选择";
                break;
            case 2:
                tempList.addAll(mDistrictDatasMap.get(city));
                title = "区县选择";
                break;

        }
        pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = tempList.get(options1);
                EToastUtil.show(ShopAreaActivity.this, tx);
                switch (type) {
                    case 0:
                        tvProvice.setText(tx);
                        provice = tx;
                        tvCity.setText("");
                        tvUserName.setText("");
                        break;
                    case 1:
                        city = tx;
                        tvCity.setText(tx);
                        tvUserName.setText("");
                        break;
                    case 2:
                        country = tx;
                        tvUserName.setText(tx);
                        break;

                }
            }
        })
                .setTitleText(title)
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.GREEN)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.DKGRAY)
                .setTitleColor(Color.LTGRAY)
                .setCancelColor(Color.YELLOW)
                .setSubmitColor(Color.YELLOW)
                .setTextColorCenter(Color.BLACK)
                .setBackgroundId(0x20000000) //设置外部遮罩颜色
                .build();
        pvOptions.setPicker(tempList);//一级选择器
        pvOptions.show();
    }
}
