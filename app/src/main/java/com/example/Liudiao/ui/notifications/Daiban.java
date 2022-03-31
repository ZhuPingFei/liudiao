package com.example.Liudiao.ui.notifications;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Liudiao.R;
import com.example.Liudiao.ui.notifications.adapter.DaibanAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Daiban extends Activity {

    public static final String TAG = "Daiban";

    private ImageView r_back;
    private TextView add_daiban;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private int current_banliId;
    private String current_banliName;
    private boolean has_myself;

    private String myselfName;
    private String myselfCard;
    private Boolean hasmyself;

    private ListView listView;
    private DaibanAdapter daibanAdapter;

    private AlertDialog.Builder builder;

    private EditText add_name;
    private Spinner type;
    private EditText add_card;
    private TextView textView;
    private ArrayList<String> typeList ;
    private ArrayList<String> typeList2 ;
    private ArrayAdapter<String> arr_adapter_type;

    private int isBindSuccess;
    private int myself_tranID;
    private int uid;

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
        setContentView(R.layout.user_daiban);

        mList = new ArrayList<>();




        listView = (ListView) findViewById(R.id.daiban_list);
        textView = (TextView) findViewById(R.id.current_banliren);
        preferences = getSharedPreferences("user",Activity.MODE_PRIVATE);
        editor = preferences.edit();
        uid = preferences.getInt("user_id",0);
        Log.d(TAG, "uid = "+uid);

        preferences = getSharedPreferences("daiban", Activity.MODE_PRIVATE);
        editor = preferences.edit();
        current_banliId = preferences.getInt("current_banliId",0);
        current_banliName = preferences.getString("current_banliName","");
        hasmyself = preferences.getBoolean("has_myself",false);
        if (current_banliName == ""){
            textView.setText("当前办理人： 未选择");
        }else {
            textView.setText("当前办理人： "+current_banliName);
        }

        typeList = new ArrayList<>();
        typeList.add("本人");
        typeList.add("代办");
        typeList2 = new ArrayList<>();
        typeList2.add("代办");

        String url = "http://175.23.169.100:9000/transactor/getTransactor";
        RequestDaibanthread rdt = new RequestDaibanthread(url,uid,handler);
        rdt.start();

        add_daiban = (TextView) findViewById(R.id.add_daiban);

        r_back = (ImageView) findViewById(R.id.back);
        r_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        add_daiban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMyStyle();
            }
        });

    }

    /**
     * 原生自定义 dialog
     */
    private void showMyStyle() {
        @SuppressLint("InflateParams") final View view = LayoutInflater.from(this).inflate(R.layout.daiban_dialog, null);
        add_name = view.findViewById(R.id.name_edit);
        add_card = view.findViewById(R.id.card_edit);
        type = view.findViewById(R.id.type_spinner);
        if (hasmyself){
            arr_adapter_type = new ArrayAdapter<String>(view.getContext(),R.layout.simple_spinner_item,typeList2);
        }else {
            arr_adapter_type = new ArrayAdapter<String>(view.getContext(),R.layout.simple_spinner_item,typeList);
        }

        arr_adapter_type.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        type.setAdapter(arr_adapter_type);

        builder = new AlertDialog.Builder(this).setView(view).setTitle("添加办理")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        int banli_type = 0;
                        if (hasmyself){
                            banli_type = 0;
                        }else {
                            if (type.getSelectedItemPosition()==0){
                                banli_type = 1;
                            }else {
                                banli_type = 0;
                            }
                        }
                        if (add_name.getText().toString().length()!=0 && add_card.getText().toString().length()!=0){
                            if (add_card.getText().toString().length()==18){
                                final int finalBanli_type = banli_type;
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try{
                                            String postUrl = "http://175.23.169.100:9000/transactor/bindTransactor";
                                            JSONObject jsonObject = new JSONObject();
                                            jsonObject.put("name",add_name.getText().toString());
                                            jsonObject.put("card_id",add_card.getText().toString());
                                            jsonObject.put("user_id",uid);
                                            jsonObject.put("relationship","朋友");
                                            jsonObject.put("qr_code","qrtest");
                                            jsonObject.put("if_oneself", finalBanli_type);
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
                                            isBindSuccess = jsonObj1.getInt("code");
                                            if (isBindSuccess == 0){
                                                if (finalBanli_type == 1){
                                                    editor.putBoolean("has_myself",true);
                                                    editor.commit();
                                                }
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(Daiban.this,"添加成功！",Toast.LENGTH_SHORT).show();
                                                        onCreate(null);
                                                        //daibanAdapter.notifyDataSetChanged();
                                                    }
                                                });
                                            }else {
                                                //Toast.makeText(Daiban.this,"添加失败！",Toast.LENGTH_SHORT).show();
                                            }
                                        }catch (MalformedURLException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }).start();
                            }else {
                                Toast.makeText(Daiban.this,"请输入正确的身份证号！",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(Daiban.this,"请填写完整提交！",Toast.LENGTH_SHORT).show();
                        }


                        //editor.putInt("current_banliID", myself_tranID);
//                editor.putInt("Mytransactor_id", myself_tranID);
//                //Log.d(TAG, "Mytransactor_id  =" + myself_tranID);
//                editor.putBoolean("isMe", true);
//                editor.putString("current_banliName", myselfName);
//                editor.putInt("myself_tranId", myself_tranID);
//                editor.putBoolean("daiban_hasmyself", true);
//                editor.putString("daiban_myselfName", myselfName);
//                editor.putString("daiban_myselfCard", myselfCard);
//                editor.commit();
                        //Toast.makeText(Daiban.this, "账号： " + etUsername.getText().toString() + "  密码： " + etPassword.getText().toString(), Toast.LENGTH_LONG).show();
                    }
                })
        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //ToDo: 你想做的事情
                //Toast.makeText(Daiban.this, "关闭按钮", Toast.LENGTH_LONG).show();
                dialogInterface.dismiss();
            }
        });

        builder.create().show();
    }

    public void parse(String var) {
        try {
            mList.clear();
            JsonObject jsonObject1 = new JsonParser().parse(var).getAsJsonObject();
            String jGroup1 = jsonObject1.get("detial").toString();
            List<Map<String,String>> list = new Gson().fromJson(jGroup1, new TypeToken<List<Map<String, String>>>() {
            }.getType());
            mList.addAll(list);

            daibanAdapter = new DaibanAdapter(Daiban.this, mList, listView);
            listView.setAdapter(daibanAdapter);
            daibanAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
