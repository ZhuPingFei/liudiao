package com.example.Liudiao.ui.xingcheng;

import android.content.Context;
import android.content.SharedPreferences;
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

public class WeixianAdapter extends BaseAdapter {
    View.OnClickListener onClickListener;
    private LayoutInflater mInflater;
    private List<Map<String,String>> mDatas;
    private Context mContext;
    private ListView listView;


    public WeixianAdapter(Context context, List<Map<String,String>> mDatas, ListView listView) {
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
        final WeixianAdapter.ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.weixian_list, null);
                holder = new WeixianAdapter.ViewHolder();
                holder.aItem = (LinearLayout) convertView.findViewById(R.id.theWholeItem);

                holder.place = (TextView) convertView.findViewById(R.id.place);

                //holder.tongxing = (TextView) convertView.findViewById(R.id.tongxing);
                convertView.setTag(holder);
            }
            else {
                holder = (WeixianAdapter.ViewHolder) convertView.getTag();
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


            holder.place.setText("位置： "+str1+" — "+str2);







        return convertView;
    }

    class ViewHolder {
        LinearLayout aItem;

        TextView place;

    }

//    public void dialog(){
//        View view = LayoutInflater.from(mContext).inflate(R.layout.family_list,null);
//    }

}
