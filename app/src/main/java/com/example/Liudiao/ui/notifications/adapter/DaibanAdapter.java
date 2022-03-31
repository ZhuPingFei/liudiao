package com.example.Liudiao.ui.notifications.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Liudiao.Main;
import com.example.Liudiao.R;
import com.example.Liudiao.ui.notifications.Daiban;
import com.example.Liudiao.ui.notifications.NotificationsFragment;

import java.util.List;
import java.util.Map;

import static com.example.Liudiao.ui.notifications.Daiban.TAG;

public class DaibanAdapter extends BaseAdapter {



    View.OnClickListener onClickListener;
    private LayoutInflater mInflater;
    private List<Map<String,String>> mDatas;
    private Context mContext;
    private ListView listView;
    private String current_banliName;
    private int current_banliId;


    public DaibanAdapter(Context context, List<Map<String,String>> mList, ListView listView) {
        this.mDatas = mList;
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

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //int nowselect = preferences.getInt("now_daiban",0);
        preferences  = mContext.getSharedPreferences("daiban",Activity.MODE_PRIVATE);
        editor = preferences.edit();;
        final int myTransId = preferences.getInt("Mytransactor_id",0);
        final ViewHolder holder;
        try {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.daiban_list, null);
                holder = new ViewHolder();
                holder.aItem=(RelativeLayout) convertView.findViewById(R.id.theWholeItem);
                holder.name = (TextView) convertView.findViewById(R.id.daiban_name);
                holder.card = (TextView) convertView.findViewById(R.id.daiban_card);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            final int is_myself = Integer.parseInt(mDatas.get(position).get("if_oneself").toString());
            if (is_myself == 1){
                holder.name.setText(mDatas.get(position).get("name").toString()+"（本人）");
            }else {
                holder.name.setText(mDatas.get(position).get("name").toString()+"（代办）");
            }
            holder.card.setText(mDatas.get(position).get("card_id").toString());

            //holder.aItem.setBackgroundColor(R.color.crimson);

            holder.aItem.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    current_banliName = holder.name.getText().toString();
                    current_banliId = Integer.parseInt(mDatas.get(position).get("transactor_id"));
                    if (is_myself == 1){
                        editor.putBoolean("isMe",true);
                    }else {
                        editor.putBoolean("isMe",false);
                    }
                    Log.d(TAG, "Currenttransactor_id  ="+current_banliId);
                    editor.putString("current_banliName",current_banliName);
                    editor.putInt("current_banliId",current_banliId);
                    editor.commit();
                    holder.aItem.setBackgroundColor(R.color.gold);
                    Toast.makeText(mContext,"已切换至办理人 "+current_banliName,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, Main.class);
                    mContext.startActivity(intent);
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
        return convertView;
    }

    class ViewHolder {
        RelativeLayout aItem;
        TextView name;
        TextView card;
    }
}
