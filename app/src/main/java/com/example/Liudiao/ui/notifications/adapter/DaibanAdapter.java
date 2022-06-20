package com.example.Liudiao.ui.notifications.adapter;

import android.animation.FloatEvaluator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Liudiao.Main;
import com.example.Liudiao.R;

import java.nio.DoubleBuffer;
import java.util.List;
import java.util.Map;

public class DaibanAdapter extends BaseAdapter {


    View.OnClickListener onClickListener;
    private LayoutInflater mInflater;
    private List<Map<String, String>> mDatas;
    private Context mContext;
    private ListView listView;
    private String current_banliName;
    private int current_banliId;

    private int authority;

    public DaibanAdapter(Context context, List<Map<String, String>> mList, ListView listView) {
        this.mDatas = mList;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.listView = listView;

    }

    private SharedPreferences preferences;
    private SharedPreferences preferences1;
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
        preferences = mContext.getSharedPreferences("daiban", Activity.MODE_PRIVATE);
        editor = preferences.edit();
        ;

        final ViewHolder holder;
        try {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.daiban_list, null);
                holder = new ViewHolder();
                holder.aItem = (RelativeLayout) convertView.findViewById(R.id.theWholeItem);
                holder.name = (TextView) convertView.findViewById(R.id.daiban_name);
                holder.card = (TextView) convertView.findViewById(R.id.daiban_card);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            String inputName = mDatas.get(position).get("name") + "";
            if (inputName.equals("null")) {
                inputName = "";
            }
            String inputCardid = mDatas.get(position).get("card_id") + "";
            if (inputCardid.equals("null")) {
                inputCardid = "";
            }
            String inputRelationship = mDatas.get(position).get("relationship") + "";
            if (inputRelationship.equals("null")) {
                inputRelationship = "未知关系";
            }
            if (inputRelationship.equals("本人")||inputRelationship.equals("null")) {
                inputRelationship = "";
            }

            preferences1 = mContext.getSharedPreferences("user", Activity.MODE_PRIVATE);
            authority = preferences1.getInt("authority", 0);

            final int is_myself = Integer.parseInt(mDatas.get(position).get("if_oneself").toString());

            if ( authority == 0) {
                if (is_myself == 1) {
                    if (inputName.equals("")) {
                        holder.name.setText("本人");
                        holder.card.setText("");
                    } else {
                        holder.name.setText(inputName + "（本人）");
                        holder.card.setText(inputCardid.substring(0, 6) + "********" + inputCardid.substring(14, inputCardid.length()));
                    }
                    editor.putBoolean("has_myself", true);
                    editor.commit();
                } else {
                    holder.name.setText(inputName + "（代办）" + inputRelationship);
                    holder.card.setText(inputCardid.substring(0, 6) + "********" + inputCardid.substring(14, inputCardid.length()));
                }
            } else {
                if (is_myself == 1) {
                    holder.aItem.setVisibility(View.GONE);
//                    holder.name.setText("流调员");
//                    holder.card.setText("");
                    editor.putBoolean("has_myself", true);
                    editor.commit();
                } else {
                    holder.name.setText(inputName);
                    holder.card.setText(inputCardid.substring(0, 6) + "********" + inputCardid.substring(14, inputCardid.length()));
                }
            }
            final String status = mDatas.get(position).get("audit_status")+"";

            if (status.equals("null")){
                holder.name.setTextColor(Color.rgb(255,128,0));
                holder.card.setTextColor(Color.rgb(255,128,0));
            }else if (Integer.parseInt(status) == 0){
                holder.name.setTextColor(Color.rgb(255,128,0));
                holder.card.setTextColor(Color.rgb(255,128,0));
            }else if (Integer.parseInt(status) == 1){
                holder.name.setTextColor(Color.rgb(0,0,0));
                holder.card.setTextColor(Color.rgb(0,0,0));
            }else if (Integer.parseInt(status) == 2){
                holder.name.setTextColor(Color.rgb(255,0,0));
                holder.card.setTextColor(Color.rgb(255,0,0));
            }

            final String finalInputCardid = inputCardid;
            holder.aItem.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    current_banliName = mDatas.get(position).get("name");
                    current_banliId = Integer.parseInt(mDatas.get(position).get("transactor_id"));
                    if (is_myself == 1) {
                        editor.putBoolean("isMe", true);
                    } else {
                        editor.putBoolean("isMe", false);
                    }
                    editor.putString("current_banliName", current_banliName);
                    editor.putInt("current_banliId", current_banliId);
                    editor.putString("idCard", finalInputCardid);
                    editor.commit();
                    holder.aItem.setBackgroundColor(R.color.gold);
                    Toast.makeText(mContext, "已切换至办理人 " + holder.name.getText().toString(), Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(mContext, Main.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);
                }
            });

            holder.aItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });

        } catch (Exception e) {
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
