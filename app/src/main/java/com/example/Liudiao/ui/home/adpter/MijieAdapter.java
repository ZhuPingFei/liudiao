package com.example.Liudiao.ui.home.adpter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.Liudiao.R;
import com.example.Liudiao.ui.home.Mijie;
import com.example.Liudiao.ui.home.MijieAlter;
import com.example.Liudiao.ui.xingcheng.XingchengAlter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class MijieAdapter extends BaseAdapter {

    View.OnClickListener onClickListener;
    private LayoutInflater mInflater;
    private List<Map<String, String>> mDatas;
    private Context mContext;
    private ListView listView;


    public MijieAdapter(Context context, List<Map<String, String>> mDatas, ListView listView) {
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
        final MijieAdapter.ViewHolder holder;
        try {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.mijie_list2, null);
                holder = new MijieAdapter.ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.user_mijie);
                holder.member = (RelativeLayout) convertView.findViewById(R.id.member_name2);
                holder.phone = (TextView) convertView.findViewById(R.id.user_mijie_phone);
                convertView.setTag(holder);
            } else {
                holder = (MijieAdapter.ViewHolder) convertView.getTag();
            }
            holder.name.setText(mDatas.get(position).get("name"));
            int iscase = Integer.parseInt(mDatas.get(position).get("isCase"));
            if (iscase == 1){
                holder.name.setText(mDatas.get(position).get("name")+"(已确诊)");
                holder.name.setTextColor(Color.rgb(255,0,0));
            }
            if (iscase == 0){
                holder.name.setTextColor(Color.rgb(0,0,0));
            }
            holder.phone.setText("("+mDatas.get(position).get("mobile")+")");
            //holder.phone.setText("手机号码："+mDatas.get(position).get("mobile").toString());

            final String cardID;
            if ((mDatas.get(position).get("card_id") + "").equals("null") || (mDatas.get(position).get("card_id") + "").equals("")) {
                cardID = "";
            } else {
                cardID = mDatas.get(position).get("card_id") + "";
            }
            final String address;
            final String address_detail;

            if ((mDatas.get(position).get("address") + "").equals("null") || (mDatas.get(position).get("address") + "").equals("")) {
                address = "";
            } else {
                address = mDatas.get(position).get("address") + "";
            }
            if ((mDatas.get(position).get("address_detail") + "").equals("null") || (mDatas.get(position).get("address_detail") + "").equals("")) {
                address_detail = "";
            } else {
                address_detail = mDatas.get(position).get("address_detail") + "";
            }

            holder.member.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = Integer.parseInt(mDatas.get(position).get("id").toString());
                    Intent intent = new Intent(mContext, MijieAlter.class);
                    intent.putExtra("id", id);
                    intent.putExtra("name", mDatas.get(position).get("name").toString());
                    intent.putExtra("contact_type", mDatas.get(position).get("Contact_type").toString());
                    intent.putExtra("mobile", mDatas.get(position).get("mobile").toString());
                    intent.putExtra("card_id", cardID);
                    intent.putExtra("address", address);
                    intent.putExtra("address_detail", address_detail);
                    mContext.startActivity(intent);
//                if (Activity.class.isInstance(mContext)) {
//                    // 转化为activity，然后finish就行了
//                    Activity activity = (Activity) mContext;
//                    activity.finish();
//                }
                }
            });

            holder.member.setOnLongClickListener(new View.OnLongClickListener() {
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
        TextView name;
        TextView phone;
        RelativeLayout member;


    }


}
