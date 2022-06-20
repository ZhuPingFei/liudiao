package com.example.Liudiao.ui.huodong;

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
import com.example.Liudiao.ui.xingcheng.Adapter.XingchengAdapter;
import com.example.Liudiao.ui.xingcheng.XingchengAlter;

import java.util.List;
import java.util.Map;

public class HuodongAdapter extends BaseAdapter {
    View.OnClickListener onClickListener;
    private LayoutInflater mInflater;
    private List<Map<String,String>> mDatas;
    private Context mContext;
    private ListView listView;


    public HuodongAdapter(Context context, List<Map<String,String>> mDatas, ListView listView) {
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
        final HuodongAdapter.ViewHolder holder;
        try {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.huodong_list, null);
                holder = new HuodongAdapter.ViewHolder();
                holder.aItem = (LinearLayout) convertView.findViewById(R.id.theWholeItem);
                holder.time = (TextView) convertView.findViewById(R.id.time);
                holder.place = (TextView) convertView.findViewById(R.id.place);
                convertView.setTag(holder);
            }
            else {
                holder = (HuodongAdapter.ViewHolder) convertView.getTag();
            }

            String str1_time = mDatas.get(position).get("acti_start_date");
            String str2_time = mDatas.get(position).get("acti_end_date");
            if (str1_time.equals(str2_time)){
                holder.time.setText("时间： "+str1_time+" "+mDatas.get(position).get("acti_starttime").toString()+"-"+mDatas.get(position).get("acti_endtime").toString());
            }else {
                holder.time.setText("时间： "+str1_time+" — "+str2_time);
            }
            holder.place.setText("位置： "+mDatas.get(position).get("actipla_detail").toString());

//            holder.date.setText("时间：  "+mDatas.get(position).get("acti_start_date").toString()+" "+mDatas.get(position).get("acti_starttime").toString()
//            +"-"+mDatas.get(position).get("acti_end_date").toString()+" "+mDatas.get(position).get("acti_endtime").toString());
//            //int trans_id = Integer.parseInt(mDatas.get(position).get("traj_id"));

//            StringBuffer strTime = new StringBuffer();
//            strTime.append(mDatas.get(position).get("acti_starttime").toString());
//            strTime.append(" - "+mDatas.get(position).get("acti_endtime").toString());
//            holder.time.setText("时间： "+strTime.toString());
//            holder.time.setVisibility(View.GONE);
//
//            StringBuffer strPlace = new StringBuffer();
//            strPlace.append(mDatas.get(position).get("acti_place").toString());
//            strPlace.append(" "+mDatas.get(position).get("actipla_detail").toString());
//            holder.place.setText("地点： "+strPlace.toString());
//            final String acti_detail = mDatas.get(position).get("acti_detail")+"";
//            if (acti_detail.equals("null") || acti_detail.equals("")){
//                holder.detail.setVisibility(View.GONE);
//            }else {
//                holder.detail.setText("情况描述： "+acti_detail);
//            }



            holder.aItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int acti_id = Integer.parseInt(mDatas.get(position).get("acti_id").toString());
                    Intent intent = new Intent(mContext, HuodongAlter.class);
                    intent.putExtra("acti_id", acti_id);
                    intent.putExtra("acti_place",mDatas.get(position).get("acti_place").toString());
                    intent.putExtra("actipla_detail",mDatas.get(position).get("actipla_detail").toString());
                    intent.putExtra("acti_start_date",mDatas.get(position).get("acti_start_date").toString());
                    intent.putExtra("acti_end_date",mDatas.get(position).get("acti_end_date").toString());
                    intent.putExtra("acti_starttime",mDatas.get(position).get("acti_starttime").toString());
                    intent.putExtra("acti_endtime",mDatas.get(position).get("acti_endtime").toString());
                    if ((mDatas.get(position).get("acti_detail")+"").equals("null") || (mDatas.get(position).get("acti_detail")+"").equals("")){
                        intent.putExtra("acti_detail","");
                    }else {
                        intent.putExtra("acti_detail",mDatas.get(position).get("acti_detail")+"");
                    }

                    mContext.startActivity(intent);
//                    if (Activity.class.isInstance(mContext)) {
//                        // 转化为activity，然后finish就行了
//                        Activity activity = (Activity) mContext;
//                        activity.finish();
//                    }

                }
            });

            holder.aItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }
        return convertView;
    }

    class ViewHolder {
        LinearLayout aItem;
        TextView time;
        TextView place;

    }

//    public void dialog(){
//        View view = LayoutInflater.from(mContext).inflate(R.layout.family_list,null);
//    }

}
