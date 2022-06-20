package com.example.Liudiao.ui.huodong;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.Liudiao.R;

import java.util.List;
import java.util.Map;

public class HuodongAdapter2 extends BaseAdapter {

    View.OnClickListener onClickListener;
    private LayoutInflater mInflater;
    private List<Map<String,String>> mDatas;
    private Context mContext;
    private ListView listView;


    public HuodongAdapter2(Context context, List<Map<String,String>> mDatas, ListView listView) {
        this.mDatas = mDatas;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.listView = listView;

    }

    private SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    public int getCount() {
        if (mDatas == null) {
            return 0;
        }
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        if (mDatas == null) {
            return null;
        }
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //preferences  = mContext.getSharedPreferences("daiban", Activity.MODE_PRIVATE);
        //editor = preferences.edit();
        final HuodongAdapter2.ViewHolder holder;
        try {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.huodong_list2, null);
                holder = new HuodongAdapter2.ViewHolder();
                holder.aItem = (LinearLayout) convertView.findViewById(R.id.theWholeItem);
                holder.time = (TextView) convertView.findViewById(R.id.time);
                holder.time1 = (TextView) convertView.findViewById(R.id.time1);
                convertView.setTag(holder);
            }
            else {
                holder = (HuodongAdapter2.ViewHolder) convertView.getTag();
            }


            holder.time.setText("    该病例曾于 "+mDatas.get(position).get("acti_start_date")+" "+mDatas.get(position).get("acti_starttime")+" - "
            +mDatas.get(position).get("acti_end_date")+" "+mDatas.get(position).get("acti_endtime")+"，在 "+mDatas.get(position).get("acti_place")
                    +mDatas.get(position).get("actipla_detail")+" 参加了聚集性活动。");
            if (mDatas.get(position).get("acti_detail").toString().length()!=0 && !mDatas.get(position).get("acti_detail").toString().equals("无")){
                holder.time1.setText("情况描述："+mDatas.get(position).get("acti_detail")+"。");
            }else{
                holder.time1.setVisibility(View.GONE);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return convertView;
    }

    class ViewHolder {
        LinearLayout aItem;
        TextView time;
        TextView time1;
    }
}
