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
import com.example.Liudiao.ui.home.adpter.FamilyAdapter;
import com.example.Liudiao.ui.xingcheng.Xingcheng;
import com.example.Liudiao.ui.xingcheng.XingchengAlter;

import java.util.List;
import java.util.Map;

public class XingchengAdapter extends BaseAdapter {
    View.OnClickListener onClickListener;
    private LayoutInflater mInflater;
    private List<Map<String,String>> mDatas;
    private Context mContext;
    private ListView listView;


    public XingchengAdapter(Context context, List<Map<String,String>> mDatas, ListView listView) {
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
        final XingchengAdapter.ViewHolder holder;
        try {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.xingcheng_list, null);
                holder = new XingchengAdapter.ViewHolder();
                holder.aItem = (LinearLayout) convertView.findViewById(R.id.theWholeItem);
                holder.time = (TextView) convertView.findViewById(R.id.time);
                holder.placeChange = (TextView) convertView.findViewById(R.id.placeChange);
                holder.traffic = (TextView) convertView.findViewById(R.id.traffic);
                //holder.tongxing = (TextView) convertView.findViewById(R.id.tongxing);
                convertView.setTag(holder);
            }
            else {
                holder = (XingchengAdapter.ViewHolder) convertView.getTag();
            }
            String str1 ;
            String str2 ;
            String[] split_start = mDatas.get(position).get("start").toString().split(" ");
            String[] split_end = mDatas.get(position).get("end").toString().split(" ");
            if (!split_start[0].equals(split_start[1])){
                str1 = split_start[0]+split_start[1];
            }else {
                str1 = split_start[0];
            }
            if (!split_end[0].equals(split_end[1])){
                str2 = split_end[0]+split_end[1];
            }else {
                str2 = split_end[0];
            }

//            StringBuffer strTime = new StringBuffer();
//            strTime.append(mDatas.get(position).get("start_date").toString());
//            strTime.append(" "+mDatas.get(position).get("start_time").toString()+" - ");
//            strTime.append(mDatas.get(position).get("end_date").toString());
//            strTime.append(" "+mDatas.get(position).get("end_time").toString());

            String str1_time = mDatas.get(position).get("start_date");
            String str2_time = mDatas.get(position).get("end_date");
            if (str1_time.equals(str2_time)){
                holder.time.setText("时间： "+str1_time+" "+mDatas.get(position).get("start_time").toString()+"-"+mDatas.get(position).get("end_time").toString());
            }else {
                holder.time.setText("时间： "+str1_time+" — "+str2_time);
            }
            holder.placeChange.setText("位置： "+str1+" — "+str2);

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
            holder.traffic.setText("交通方式： "+tra+" "+mDatas.get(position).get("tra_detail").toString());

            //holder.tongxing.setText("同行人员及身份： "+mDatas.get(position).get("peers").toString());
            holder.aItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int traj_id = Integer.parseInt(mDatas.get(position).get("traj_id").toString());
                    Intent intent = new Intent(mContext, XingchengAlter.class);
                    intent.putExtra("traj_id", traj_id);
                    intent.putExtra("start_date",mDatas.get(position).get("start_date").toString());
                    intent.putExtra("start_time",mDatas.get(position).get("start_time").toString());
                    intent.putExtra("start",mDatas.get(position).get("start").toString());
                    intent.putExtra("start_detail",mDatas.get(position).get("start_detail").toString());
                    intent.putExtra("traffic",Integer.parseInt(mDatas.get(position).get("traffic").toString()));
                    intent.putExtra("tra_detail",mDatas.get(position).get("tra_detail").toString());
                    intent.putExtra("peers",mDatas.get(position).get("peers").toString());
                    intent.putExtra("end",mDatas.get(position).get("end").toString());
                    intent.putExtra("end_detail",mDatas.get(position).get("end_detail").toString());
                    intent.putExtra("end_date",mDatas.get(position).get("end_date").toString());
                    intent.putExtra("end_time",mDatas.get(position).get("end_time").toString());
                    mContext.startActivity(intent);
//                    if (Activity.class.isInstance(mContext)) {
//                        // 转化为activity，然后finish就行了
//                        Activity activity = (Activity) mContext;
////                        activity.finish();
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
        TextView placeChange;
        TextView traffic;
    }

//    public void dialog(){
//        View view = LayoutInflater.from(mContext).inflate(R.layout.family_list,null);
//    }

}
