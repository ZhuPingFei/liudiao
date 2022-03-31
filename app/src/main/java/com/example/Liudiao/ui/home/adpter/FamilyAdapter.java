package com.example.Liudiao.ui.home.adpter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.Liudiao.Main;
import com.example.Liudiao.R;
import com.example.Liudiao.ui.home.Family;
import com.example.Liudiao.ui.notifications.adapter.DaibanAdapter;
import com.mob.tools.gui.PullToRequestBaseAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.Liudiao.ui.notifications.Daiban.TAG;

public class FamilyAdapter extends BaseAdapter {



    View.OnClickListener onClickListener;
    private LayoutInflater mInflater;
    private List<Map<String,String>> mDatas;
    private Context mContext;
    private ListView listView;


    public FamilyAdapter(Context context, List<Map<String,String>> mDatas, ListView listView) {
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
    public View getView(int position, View convertView, ViewGroup parent) {

        //preferences  = mContext.getSharedPreferences("daiban", Activity.MODE_PRIVATE);
        //editor = preferences.edit();
        final FamilyAdapter.ViewHolder holder;
        try {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.family_list2, null);
                holder = new FamilyAdapter.ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.member_name2);
                holder.relation = (TextView) convertView.findViewById(R.id.member_relation2);
                holder.phone = (TextView) convertView.findViewById(R.id.member_phone2);
                convertView.setTag(holder);
            }
            else {
                holder = (FamilyAdapter.ViewHolder) convertView.getTag();
            }
            holder.name.setText("姓名：   "+mDatas.get(position).get("name").toString());
            int type = Integer.parseInt(mDatas.get(position).get("relation").toString());
            if (type ==0){
                holder.relation.setText("与本人关系：    父亲");
            }else if (type == 1){
                holder.relation.setText("与本人关系：    母亲");
            }else if (type == 2){
                holder.relation.setText("与本人关系：    兄弟");
            }else if (type == 3){
                holder.relation.setText("与本人关系：    姐妹");
            }else if (type == 4){
                holder.relation.setText("与本人关系：    儿子");
            }else {
                holder.relation.setText("与本人关系：    女儿");
            }

            holder.phone.setText("手机号码：    "+mDatas.get(position).get("mobile").toString());
            //holder.name.setText(mDatas.get(position).get("name").toString());

        }catch (Exception e){
            e.printStackTrace();
        }
        return convertView;
    }

    class ViewHolder {
        TextView name;
        TextView relation;
        TextView phone;
    }

    public void dialog(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.family_list,null);
    }

}
