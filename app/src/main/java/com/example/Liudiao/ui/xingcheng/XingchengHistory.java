package com.example.Liudiao.ui.xingcheng;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Liudiao.R;
import com.example.Liudiao.ui.home.AddMijie;
import com.example.Liudiao.ui.home.Mijie;
import com.example.Liudiao.ui.xingcheng.Adapter.XingchengAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XingchengHistory extends Activity implements View.OnClickListener{

    private ImageView r_back;

    private int current_transId;

    private ImageView add;


    private ListView listView;
    private XingchengAdapter xingchengAdapter;

    SharedPreferences preferences ;
    SharedPreferences.Editor editor ;//获取编辑器

    private List<Map<String, String>> mList;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            parse(msg.obj.toString());
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xingcheng_history);
        listView = (ListView) findViewById(R.id.xingcheng_list);

        preferences = getSharedPreferences("daiban", Activity.MODE_PRIVATE);
        editor = preferences.edit();
        current_transId = preferences.getInt("current_banliId",0);

        String url = "http://175.23.169.100:9030/case-travel-trajectory/get";
        RequestXingchengHisThread rdt = new RequestXingchengHisThread(url,current_transId,handler);
        rdt.start();

        add = (ImageView) findViewById(R.id.add);
        r_back = (ImageView) findViewById(R.id.back);
        r_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(XingchengHistory.this, Xingcheng.class);
                startActivity(intent);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(XingchengHistory.this);
                builder.setMessage("确定删除该条旅程信息？");
                builder.setTitle("系统提示");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final int id = Integer.parseInt(mList.get(position).get("traj_id").toString());
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String postUrl = "http://175.23.169.100:9030/case-travel-trajectory/delete";
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("traj_id",id);

                                    URL httpUrl = new URL(postUrl);
                                    HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
                                    PrintWriter out = null;
                                    conn.setRequestMethod("POST");
                                    conn.setReadTimeout(5000);
                                    conn.setRequestProperty("Content-type", "application/json");
                                    conn.setDoInput(true);
                                    conn.setDoOutput(true);
                                    out = new PrintWriter(conn.getOutputStream());
                                    out.print(jsonObject);
                                    out.flush();
                                    InputStream is = conn.getInputStream();
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"));
                                    StringBuffer sb = new StringBuffer();
                                    String str;
                                    while ((str = reader.readLine()) != null) {
                                        sb.append(str);
                                    }
                                    JSONObject jsonObj1 = new JSONObject(sb.toString());
                                    int code = jsonObj1.getInt("code");
                                    if (code == 0){
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mList.remove(position);
                                                xingchengAdapter.notifyDataSetChanged();
                                                listView.invalidate();
                                                Toast.makeText(XingchengHistory.this, "删除成功！", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(XingchengHistory.this, "删除失败！", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return false;
            }
        });
    }



    public String getDate(){
        StringBuffer xingcheng = new StringBuffer();
        String trasffer;
        String getStartplace = preferences.getString("xingcheng_startPlace","");
        String getStartdetailplace = preferences.getString("xingcheng_startDetailplace","");
        String getStartdate = preferences.getString("xingcheng_startDate","");
        String getStartTime = preferences.getString("xingcheng_startTime","");
        int getTrasffer = preferences.getInt("xingcheng_trasffer",0);
        String getTrasfferdetail = preferences.getString("xingcheng_trasfferDetail","");
        String getTongxing = preferences.getString("xingcheng_tongxing","");
        String getEndplace = preferences.getString("xingcheng_endPlace","");
        String getEnddetailplace = preferences.getString("xingcheng_endDetailplace","");
        String getEnddate = preferences.getString("xingcheng_endDate","");
        String getEndTime = preferences.getString("xingcheng_endTime","");

        if (getTrasffer == 1){
            trasffer = "飞机";
        }else if (getTrasffer == 2){
            trasffer = "火车";
        }else if (getTrasffer == 3){
            trasffer = "客车";
        }else if (getTrasffer == 4){
            trasffer = "轮船";
        }else if (getTrasffer == 5){
            trasffer = "出租车";
        }else if (getTrasffer == 6){
            trasffer = "私家车";
        }else if (getTrasffer == 7){
            trasffer = "公交车";
        }else if (getTrasffer == 8){
            trasffer = "市内轨道交通";
        }else if (getTrasffer == 9){
            trasffer = "骑行";
        }else if (getTrasffer == 10){
            trasffer = "步行";
        }else {
            trasffer = "（未知）";
        }


        xingcheng.append("时间:  "+getStartdate+" "+getStartTime+" - "+getEnddate+" "+getEndTime+"\n");
        xingcheng.append("位置： "+getStartplace+" "+getStartdetailplace+" - "+getEndplace+" "+getEnddetailplace+"\n");
        xingcheng.append("乘坐交通方式： "+trasffer+" "+getTrasfferdetail+"\n");
        xingcheng.append("同行人员： "+getTongxing);

        return xingcheng.toString();
    }

    public void parse(String var) {
        try {

            JsonObject jsonObject1 = new JsonParser().parse(var).getAsJsonObject();
            String jGroup1 = jsonObject1.get("trajs").toString();
            mList = new Gson().fromJson(jGroup1, new TypeToken<List<Map<String, String>>>() {
            }.getType());
            datecommit();

            xingchengAdapter = new XingchengAdapter(XingchengHistory.this, mList, listView);
            listView.setAdapter(xingchengAdapter);
            xingchengAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

    }

    public void datecommit(){
        StringBuffer xingcheng = new StringBuffer("");
        for (int i = 0;i<mList.size();i++){
            Map<String, String> map = mList.get(i);
            StringBuffer strTime = new StringBuffer();
            strTime.append(map.get("start_date"));
            strTime.append(" "+map.get("start_time")+" - ");
            strTime.append(map.get("end_date"));
            strTime.append(" "+map.get("end_time")+" - ");

            StringBuffer strPlace = new StringBuffer();
            strPlace.append(map.get("start"));
            strPlace.append(" "+map.get("start_detail")+" - ");
            strPlace.append(map.get("end"));
            strPlace.append(" "+map.get("end_detail"));

            String tra = null;
            if (map.get("traffic").toString().equals("1")){
                tra ="飞机";
            }else if (map.get("traffic").toString().equals("2")){
                tra = "火车";
            }else if (map.get("traffic").toString().equals("3")){
                tra = "客车";
            }else if (map.get("traffic").toString().equals("4")){
                tra = "轮船";
            }else if (map.get("traffic").toString().equals("5")){
                tra = "出租车";
            }else if (map.get("traffic").toString().equals("6")){
                tra = "私家车";
            }else if (map.get("traffic").toString().equals("7")){
                tra = "公交车";
            }else if (map.get("traffic").toString().equals("8")){
                tra = "市内轨道交通";
            }else if (map.get("traffic").toString().equals("9")){
                tra = "骑行";
            }else if (map.get("traffic").toString().equals("10")){
                tra = "步行";
            }else {
                tra = "（未知）";
            }
            xingcheng.append("    该病例曾于 "+strTime.toString()+","+"乘坐"+tra+" "+map.get("tra_detail").toString()+","
                    +"从"+map.get("start").toString()+map.get("start_detail").toString()
                    +"到"+map.get("end").toString()+map.get("end_detail").toString()+"。");
            if(map.get("peers").toString().length()!=0 && !map.get("peers").toString().equals("无")){
                xingcheng.append("路上与 "+map.get("peers").toString()+"同行。\n");
            }else {
                xingcheng.append("\n");
            }
        }
        preferences = getSharedPreferences(current_transId+"baogao",Activity.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("xingcheng",xingcheng.toString());
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onCreate(null);
    }
}
