package com.example.Liudiao.ui.xingcheng.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.Liudiao.R;
import com.example.Liudiao.ui.xingcheng.XingchengAlter;

import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

public class XingchengAdapter2 extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Map<String,String>> mDatas;
    private Context mContext;
    private ListView listView;


    public XingchengAdapter2(Context context, List<Map<String,String>> mDatas, ListView listView) {
        this.mDatas = mDatas;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.listView = listView;

    }

    private SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private String current_banliId;
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

        preferences  = mContext.getSharedPreferences("daiban", Activity.MODE_PRIVATE);
        editor = preferences.edit();
        current_banliId = preferences.getString("current_banliId","");
        StringBuffer stringBuffer = new StringBuffer("");

        final XingchengAdapter2.ViewHolder holder;
        try {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.xingcheng_list2, null);
                holder = new XingchengAdapter2.ViewHolder();
                holder.aItem = (LinearLayout) convertView.findViewById(R.id.theWholeItem);
                holder.time = (TextView) convertView.findViewById(R.id.time);
                holder.time1 = (TextView) convertView.findViewById(R.id.time1);
                convertView.setTag(holder);
            }
            else {
                holder = (XingchengAdapter2.ViewHolder) convertView.getTag();
            }
            int trans_id = Integer.parseInt(mDatas.get(position).get("traj_id"));

            StringBuffer strTime = new StringBuffer();
            strTime.append(mDatas.get(position).get("start_date").toString());
            strTime.append(" "+mDatas.get(position).get("start_time").toString()+" - ");
            strTime.append(mDatas.get(position).get("end_date").toString());
            strTime.append(" "+mDatas.get(position).get("end_time").toString());

            StringBuffer strPlace = new StringBuffer();
            strPlace.append(mDatas.get(position).get("start").toString());
            strPlace.append(" "+mDatas.get(position).get("start_detail").toString()+" - ");
            strPlace.append(mDatas.get(position).get("end").toString());
            strPlace.append(" "+mDatas.get(position).get("end_detail").toString());
            String tra = null;
            if (mDatas.get(position).get("traffic").toString().equals("1")){
                tra ="飞机";
            }else if (mDatas.get(position).get("traffic").toString().equals("2")){
                tra = "火车";
            }else if (mDatas.get(position).get("traffic").toString().equals("3")){
                tra = "客车";
            }else if (mDatas.get(position).get("traffic").toString().equals("4")){
                tra = "轮船";
            }else if (mDatas.get(position).get("traffic").toString().equals("5")){
                tra = "出租车";
            }else if (mDatas.get(position).get("traffic").toString().equals("6")){
                tra = "私家车";
            }else if (mDatas.get(position).get("traffic").toString().equals("7")){
                tra = "公交车";
            }else if (mDatas.get(position).get("traffic").toString().equals("8")){
                tra = "市内轨道交通";
            }else if (mDatas.get(position).get("traffic").toString().equals("9")){
                tra = "骑行";
            }else if (mDatas.get(position).get("traffic").toString().equals("10")){
                tra = "步行";
            }else {
                tra = "（未知）";
            }


            holder.time.setText("    该病例曾于 "+strTime.toString()+","+"乘坐"+tra+" "+mDatas.get(position).get("tra_detail").toString()+","
            +"从"+mDatas.get(position).get("start").toString()+mDatas.get(position).get("start_detail").toString()
                    +"到"+mDatas.get(position).get("end").toString()+mDatas.get(position).get("end_detail").toString()+"。"
                    );
            if(mDatas.get(position).get("peers").toString().length()!=0 && !mDatas.get(position).get("peers").toString().equals("无")){
                holder.time1.setText("路上与 "+mDatas.get(position).get("peers").toString()+"同行。");
            }else {
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
