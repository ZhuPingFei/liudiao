package com.example.Liudiao.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.Liudiao.LimitInputTextWatcher;
import com.example.Liudiao.R;
import com.example.Liudiao.ui.home.adpter.MijieAdapter;
import com.example.Liudiao.ui.notifications.IDCard;
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
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mijie extends Activity {

    private SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private int current_transId;


    private ListView listView;
    private MijieAdapter mijieAdapter;

    private ImageView r_back;
    private ImageView add;
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
        setContentView(R.layout.home_mijie);

        preferences = getSharedPreferences("daiban", Activity.MODE_PRIVATE);
        editor = preferences.edit();
        current_transId = preferences.getInt("current_banliId", 0);

        listView = (ListView) findViewById(R.id.member_list);

        String url = "http://175.23.169.100:9040/ContactOther/getContactOther";
        RequestMijieThread rdt = new RequestMijieThread(url, current_transId, handler);
        rdt.start();

        add = (ImageView) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mijie.this, AddMijie.class);
                startActivity(intent);

            }
        });


        r_back = (ImageView) findViewById(R.id.back);
        r_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //Toast.makeText(Mijie.this, "lalallalalla！", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(Mijie.this);
                builder.setMessage("确定删除该人员信息？");
                builder.setTitle("系统提示");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final int id = Integer.parseInt(mList.get(position).get("id").toString());
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    URL httpUrl = new URL("http://175.23.169.100:9040/ContactOther/delContactOther?transactor_id="+current_transId +"&id=" + id);
                                    HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
                                    conn.setRequestMethod("GET");
                                    conn.setReadTimeout(5000);
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                    StringBuffer sb = new StringBuffer();
                                    String str;
                                    while ((str = reader.readLine()) != null) {
                                        sb.append(str);
                                    }
                                    JSONObject object1 = new JSONObject(sb.toString());
                                    int code = object1.getInt("code");
                                    if (code == 0) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mList.remove(position);
                                                mijieAdapter.notifyDataSetChanged();
                                                listView.invalidate();
                                                Toast.makeText(Mijie.this, "删除成功！", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(Mijie.this, "删除失败！", Toast.LENGTH_SHORT).show();
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

    public void parse(String var) {
        try {

            JsonObject jsonObject1 = new JsonParser().parse(var).getAsJsonObject();
            String jGroup1 = jsonObject1.get("detial").toString();
            //mList.clear();
            mList = new Gson().fromJson(jGroup1, new TypeToken<List<Map<String, String>>>() {
            }.getType());
            datacommit();
            mijieAdapter = new MijieAdapter(Mijie.this, mList, listView);
            listView.setAdapter(mijieAdapter);
            mijieAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void datacommit() {

        StringBuffer stringBuffer1 = new StringBuffer("");
        for (int i = 0; i < mList.size(); i++) {
            Map<String, String> map = mList.get(i);
            String str = "";
            String name = map.get("name");
            String mobile = map.get("mobile");
            String idCard = map.get("card_id") + "";


            String contact_type = map.get("contact_type") + "";
            StringBuffer stringBuffer = new StringBuffer("");
            if (!contact_type.equals("") && !contact_type.equals("null")) {
                String sym = contact_type;
                if (sym.length() == 1) {
                    if (Integer.parseInt(sym) == 1) {
                        stringBuffer.append("同吃");
                    } else if (Integer.parseInt(sym) == 2) {
                        stringBuffer.append("同行");
                    } else if (Integer.parseInt(sym) == 3) {
                        stringBuffer.append("同住");
                    } else {
                        stringBuffer.append("同事");
                    }
                } else {
                    String[] split = sym.split(",");
                    for (int j = 0; j < split.length; j++) {
                        if (Integer.parseInt(split[j]) == 1) {
                            stringBuffer.append("同吃 ");
                        } else if (Integer.parseInt(split[j]) == 2) {
                            stringBuffer.append("同行 ");
                        } else if (Integer.parseInt(split[j]) == 3) {
                            stringBuffer.append("同住 ");
                        } else {
                            stringBuffer.append("同事");
                        }
                    }
                }
            }
            String address = map.get("address") + "";
            String address_detail = map.get("address_detail") + "";
            String dizhi;
            if (address.equals("null") || address.equals("")) {
                if (address_detail.equals("null") || address_detail.equals("")) {
                    dizhi = "";
                } else {
                    dizhi = address_detail;
                }
            } else {
                if (address_detail.equals("null") || address_detail.equals("")) {
                    dizhi = address;
                } else {
                    dizhi = address + address_detail;
                }
            }

            if (!idCard.equals("null") && !idCard.equals("")) {
                if (!dizhi.equals("")) {
                    stringBuffer1.append("    该病例曾与其非家人密接 " + map.get("name").toString() + " " + stringBuffer.toString() + "，后者手机号是 " + map.get("mobile").toString()
                            + ",身份证号是 " + map.get("card_id").toString() + "，住在 " + dizhi + "。\n");
                } else {
                    stringBuffer1.append("    该病例曾与其非家人密接 " + map.get("name").toString() + " " + stringBuffer.toString() + "，后者手机号是 " + map.get("mobile").toString()
                            + ",身份证号是 " + map.get("card_id").toString() + "。\n");
                }
            } else {
                stringBuffer1.append("    该病例曾与其非家人密接 " + map.get("name").toString() + " " + stringBuffer.toString() + "，后者手机号是 " + map.get("mobile").toString() + "。\n");
            }
        }

        preferences = getSharedPreferences(current_transId + "baogao", Activity.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("mijie", stringBuffer1.toString());
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onCreate(null);
    }
}
