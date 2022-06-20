package com.example.Liudiao.ui.huodong;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.example.Liudiao.ui.xingcheng.Adapter.XingchengAdapter;
import com.example.Liudiao.ui.xingcheng.RequestXingchengHisThread;
import com.example.Liudiao.ui.xingcheng.XingchengHistory;
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

public class HuodongHistory extends Activity {
    private ImageView r_back;
    private ImageView add;
    private int current_transId;


    private ListView listView;
    private HuodongAdapter huodongAdapter;

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
        setContentView(R.layout.huodong_history);
        listView = (ListView) findViewById(R.id.huodong_list);


        preferences = getSharedPreferences("daiban", Activity.MODE_PRIVATE);
        editor = preferences.edit();
        current_transId = preferences.getInt("current_banliId",0);

        String url = "http://175.23.169.100:9030/case-aggregated-activity/get";
        RequestXingchengHisThread rdt = new RequestXingchengHisThread(url,current_transId,handler);
        rdt.start();


        r_back = (ImageView) findViewById(R.id.back);
        r_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        add = (ImageView) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HuodongHistory.this,Huodong.class);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HuodongHistory.this);
                builder.setMessage("确定删除该条行程信息？");
                builder.setTitle("系统提示");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final int id = Integer.parseInt(mList.get(position).get("acti_id").toString());
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String postUrl = "http://175.23.169.100:9030/case-aggregated-activity/delete";
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("acti_id",id);

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
                                                huodongAdapter.notifyDataSetChanged();
                                                listView.invalidate();
                                                Toast.makeText(HuodongHistory.this, "删除成功！", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(HuodongHistory.this, "删除失败！", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1){
            onCreate(null);
        }
    }

    public void parse(String var) {
        try {

            JsonObject jsonObject1 = new JsonParser().parse(var).getAsJsonObject();
            String jGroup1 = jsonObject1.get("activities").toString();
            mList = new Gson().fromJson(jGroup1, new TypeToken<List<Map<String, String>>>() {
            }.getType());

            datecommit();

            huodongAdapter = new HuodongAdapter(HuodongHistory.this, mList, listView);
            listView.setAdapter(huodongAdapter);
            huodongAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void datecommit(){
        StringBuffer xingcheng = new StringBuffer("");
        for (int i = 0;i<mList.size();i++){
            Map<String, String> map = mList.get(i);
            xingcheng.append("    该病例曾于 "+map.get("acti_start_date")+" "+map.get("acti_starttime")+" - "
                    +map.get("acti_end_date")+" "+map.get("acti_endtime")+"，在 "+map.get("acti_place")
                    +map.get("actipla_detail")+" 参加了聚集性活动。");
            if ((map.get("acti_detail")+"").length()!=0 && !(map.get("acti_detail")+"").equals("null")){
                xingcheng.append("情况描述："+map.get("acti_detail")+"。\n");
            }else{
                xingcheng.append("\n");
            }
        }
        preferences = getSharedPreferences(current_transId+"baogao",Activity.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("huodong",xingcheng.toString());
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onCreate(null);
    }
}
