package com.kzmen.sczxjf.view;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kzmen.sczxjf.R;

/**
 * 说明：选择字体大小弹框
 * Created by FuPei
 * on 2015/12/10 at 13:53
 */
public class ChooseSizeFragment extends DialogFragment {


    private final String[] SIZES = {"6","8","10","12","14","16","18","20","22","24","26","28","30",
                                    "32","34","36","38","40","42","44","46","48","50"};
    private ListView lv;
    private OnChooseSize inter;
    private BaseAdapter adapter;

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout( dm.widthPixels - 200, 600);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, 0);
        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return SIZES.length;
            }

            @Override
            public Object getItem(int position) {
                return SIZES[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Holder holder;
                if(convertView == null) {
                    holder = new Holder();
                    convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_size, null);
                    holder.tv = (TextView) convertView.findViewById(R.id.item_size_tv_name);
                    convertView.setTag(holder);
                } else {
                    holder = (Holder) convertView.getTag();
                }
                holder.tv.setText(SIZES[position]);
                holder.tv.setTextSize(Float.valueOf(SIZES[position]));
                return convertView;
            }

            class Holder {
                TextView tv;
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_choose_size, container);
        lv = (ListView) view.findViewById(R.id.dialog_choose_lv);
        lv.setAdapter(adapter);
        setListener();
        return view;
    }

    private void setListener() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                float size = Float.valueOf(SIZES[position]);
                inter.OnChooseFinish(size);
                ChooseSizeFragment.this.dismiss();
            }
        });
    }

    public void setOnChooseFinish(OnChooseSize inter) {
        this.inter = inter;
    }

    public interface OnChooseSize{
        void OnChooseFinish(float size);
    }

}
