package com.kzmen.sczxjf.test.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kzmen.sczxjf.R;
import com.kzmen.sczxjf.test.model.City;
import com.kzmen.sczxjf.test.model.LocateState;
import com.kzmen.sczxjf.test.utils.PinyinUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * author zaaach on 2016/1/26.
 */
public class CityListAdapter extends BaseAdapter {
   // private static final int VIEW_TYPE_COUNT = 3;

    private Context mContext;
    private LayoutInflater inflater;
    private List<City> mCities;
    private HashMap<String, Integer> letterIndexes;
    private String[] sections;
    private OnCityClickListener onCityClickListener;
    private int locateState = LocateState.LOCATING;
    private String locatedCity;

    public CityListAdapter(Context mContext, List<City> mCities) {
        this.mContext = mContext;
        this.mCities = mCities;
        this.inflater = LayoutInflater.from(mContext);
        if (mCities == null){
            mCities = new ArrayList<>();
        }

        int size = mCities.size();
        letterIndexes = new HashMap<>();
        sections = new String[size];
        for (int index = 0; index < size; index++){
            //当前城市拼音首字母
            String currentLetter = PinyinUtils.getFirstLetter(mCities.get(index).getPinyin());
            //上个首字母，如果不存在设为""
            String previousLetter = index >= 1 ? PinyinUtils.getFirstLetter(mCities.get(index - 1).getPinyin()) : "#";
            if (!TextUtils.equals(currentLetter, previousLetter)){
                letterIndexes.put(currentLetter, index);
                sections[index] = currentLetter;
            }
        }
    }

    /**
     * 更新定位状态
     * @param state
     */
    public void updateLocateState(int state, String city){
        this.locateState = state;
        this.locatedCity = city;
        notifyDataSetChanged();
    }

    /**
     * 获取字母索引的位置
     * @param letter
     * @return
     */
    public int getLetterPosition(String letter){
        Integer integer = letterIndexes.get(letter);
        return integer == null ? -1 : integer;
    }

    @Override
    public int getCount() {
        return mCities == null ? 0: mCities.size();
    }

    @Override
    public City getItem(int position) {
        return mCities == null ? null : mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        CityViewHolder holder;
         if (view == null){
            view = inflater.inflate(R.layout.cp_item_city_listview, parent, false);
            holder = new CityViewHolder();
            holder.letter = (TextView) view.findViewById(R.id.tv_item_city_listview_letter);
            holder.name = (TextView) view.findViewById(R.id.tv_item_city_listview_name);
            view.setTag(holder);
        }else{
            holder = (CityViewHolder) view.getTag();
        }
        final String city = mCities.get(position).getName();
            holder.name.setText(city);
            String currentLetter = PinyinUtils.getFirstLetter(mCities.get(position).getPinyin());
            String previousLetter = position >= 1 ? PinyinUtils.getFirstLetter(mCities.get(position - 1).getPinyin()) : "";
            if (!TextUtils.equals(currentLetter, previousLetter)){
                holder.letter.setVisibility(View.VISIBLE);
                holder.letter.setText(currentLetter);
            }else{
                holder.letter.setVisibility(View.GONE);
            }
            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCityClickListener != null){
                        onCityClickListener.onCityClick(city);
                    }
                }
            });
        return view;
    }

    public static class CityViewHolder{
        TextView letter;
        TextView name;
    }

    public void setOnCityClickListener(OnCityClickListener listener){
        this.onCityClickListener = listener;
    }

    public interface OnCityClickListener{
        void onCityClick(String name);
        void onLocateClick();
    }
}
