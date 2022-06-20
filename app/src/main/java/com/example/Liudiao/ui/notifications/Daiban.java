package com.example.Liudiao.ui.notifications;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.Liudiao.LimitInputTextWatcher;
import com.example.Liudiao.R;
import com.example.Liudiao.ui.notifications.adapter.DaibanAdapter;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Daiban extends Activity {

    public static final String TAG = "Daiban";

    private ImageView r_back;
    private ImageView add_daiban;
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
    private AlertDialog.Builder builder1;

    private EditText add_name;
    private EditText add_relation;
    private Spinner type;
    private EditText add_card;
    private TextView textView;
    private ArrayList<String> typeList;
    private ArrayList<String> typeList2;
    private ArrayAdapter<String> arr_adapter_type;

    private int isBindSuccess;
    private int uid;
    private int authority;

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
        preferences = getSharedPreferences("user", Activity.MODE_PRIVATE);
        authority = preferences.getInt("authority",0);
        editor = preferences.edit();
        uid = preferences.getInt("user_id", 0);
        Log.d(TAG, "uid = " + uid);

        preferences = getSharedPreferences("daiban", Activity.MODE_PRIVATE);
        editor = preferences.edit();
        current_banliId = preferences.getInt("current_banliId", 0);
        current_banliName = preferences.getString("current_banliName", "");
        hasmyself = preferences.getBoolean("has_myself", false);


        if ( authority == 0){
            if (current_banliName == "") {
                textView.setText("当前办理人： 未选择");
            } else {
                textView.setText("当前办理人： " + current_banliName);
            }
        }else{
            hasmyself = true;
            textView.setText("当前办理人： " + current_banliName);
        }


        typeList = new ArrayList<>();
        typeList.add("本人");
        typeList.add("代办");
        typeList2 = new ArrayList<>();
        typeList2.add("代办");

        String url = "http://175.23.169.100:9040/transactor/getTransactor";
        RequestDaibanthread rdt = new RequestDaibanthread(url, uid, handler);
        rdt.start();

        add_daiban = (ImageView) findViewById(R.id.add);

        r_back = (ImageView) findViewById(R.id.back);
        r_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //添加
        add_daiban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMyStyle();
            }
        });

        //修改
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showMyStyle1(position);
                return false;
            }
        });

    }

    /**
     * 原生自定义 dialog
     */
    private void showMyStyle() {
        @SuppressLint("InflateParams") final View view = LayoutInflater.from(this).inflate(R.layout.daiban_dialog, null);
        add_name = view.findViewById(R.id.name_edit);
        add_name.addTextChangedListener(new LimitInputTextWatcher(add_name));
        add_relation = view.findViewById(R.id.relation_edit);
        add_relation.addTextChangedListener(new LimitInputTextWatcher(add_relation));




        add_card = view.findViewById(R.id.card_edit);
        type = view.findViewById(R.id.type_spinner);
        if (authority != 0){
            arr_adapter_type = new ArrayAdapter<String>(view.getContext(), R.layout.simple_spinner_item, typeList2);
        }else {
            if (hasmyself){
                arr_adapter_type = new ArrayAdapter<String>(view.getContext(), R.layout.simple_spinner_item, typeList2);
            }else {
                arr_adapter_type = new ArrayAdapter<String>(view.getContext(), R.layout.simple_spinner_item, typeList);
            }
        }

        arr_adapter_type.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        type.setAdapter(arr_adapter_type);


        builder = new AlertDialog.Builder(Daiban.this).setView(view).setTitle("添加办理")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, int i) {
                        if (add_name.getText().toString().length() != 0 && add_card.getText().toString().length() != 0) {
                            builder1 = new AlertDialog.Builder(Daiban.this).setTitle("系统提示")
                                    .setMessage("输入姓名和身份证后二者会绑定且不可修改，您是否确定要提交？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(final DialogInterface dialogInterface, int i) {

                                            IDCard cc = new IDCard();

                                            int banli_type = 0;
                                            if (hasmyself) {
                                                banli_type = 0;
                                            } else {
                                                if (type.getSelectedItemPosition() == 0) {
                                                    banli_type = 1;
                                                } else {
                                                    banli_type = 0;
                                                }
                                            }

                                                try {
                                                    if (cc.IDCardValidate(add_card.getText().toString()) == "") {
                                                        final int finalBanli_type = banli_type;
                                                        new Thread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                try {
                                                                    String postUrl = "http://175.23.169.100:9040/transactor/bindTransactor";
                                                                    JSONObject jsonObject = new JSONObject();
                                                                    jsonObject.put("name", add_name.getText().toString());
                                                                    jsonObject.put("card_id", add_card.getText().toString());
                                                                    jsonObject.put("user_id", uid);
                                                                    jsonObject.put("relationship", add_relation.getText().toString());

                                                                    jsonObject.put("qr_code", "qrtest");
                                                                    jsonObject.put("if_oneself", finalBanli_type);

                                                                    Log.e("123", String.valueOf(jsonObject));
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
                                                                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
                                                                    StringBuffer sb = new StringBuffer();
                                                                    String str;
                                                                    while ((str = reader.readLine()) != null) {
                                                                        sb.append(str);
                                                                    }
                                                                    JSONObject jsonObj1 = new JSONObject(sb.toString());
                                                                    isBindSuccess = jsonObj1.getInt("code");
                                                                    if (isBindSuccess == 0) {
                                                                        //editor.putString("idCard",add_card.getText().toString());

                                                                        runOnUiThread(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                Toast.makeText(Daiban.this, "添加成功！", Toast.LENGTH_SHORT).show();
                                                                                onCreate(null);
                                                                                try {
                                                                                    Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                                                                                    field.setAccessible(true);
                                                                                    field.set(dialogInterface, true);
                                                                                } catch (NoSuchFieldException e) {
                                                                                    e.printStackTrace();
                                                                                } catch (IllegalAccessException e) {
                                                                                    e.printStackTrace();
                                                                                }
                                                                                //daibanAdapter.notifyDataSetChanged();
                                                                            }
                                                                        });
                                                                    } else if (isBindSuccess == 502){
                                                                        runOnUiThread(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                Toast.makeText(Daiban.this, "库中已存在该人，与所填写不符，如有疑问请联系运维人员", Toast.LENGTH_LONG).show();
                                                                                onCreate(null);
                                                                                try {
                                                                                    Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                                                                                    field.setAccessible(true);
                                                                                    field.set(dialogInterface, true);
                                                                                } catch (NoSuchFieldException e) {
                                                                                    e.printStackTrace();
                                                                                } catch (IllegalAccessException e) {
                                                                                    e.printStackTrace();
                                                                                }
                                                                                //daibanAdapter.notifyDataSetChanged();
                                                                            }
                                                                        });
                                                                    } else {
                                                                        runOnUiThread(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                Toast.makeText(Daiban.this, "添加失败！", Toast.LENGTH_SHORT).show();
                                                                                onCreate(null);
                                                                                try {
                                                                                    Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                                                                                    field.setAccessible(true);
                                                                                    field.set(dialogInterface, true);
                                                                                } catch (NoSuchFieldException e) {
                                                                                    e.printStackTrace();
                                                                                } catch (IllegalAccessException e) {
                                                                                    e.printStackTrace();
                                                                                }
                                                                                //daibanAdapter.notifyDataSetChanged();
                                                                            }
                                                                        });

                                                                    }
                                                                } catch (MalformedURLException e) {
                                                                    e.printStackTrace();
                                                                } catch (IOException e) {
                                                                    e.printStackTrace();
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }

                                                            }
                                                        }).start();
                                                    } else {
                                                        Toast.makeText(Daiban.this, cc.IDCardValidate(add_card.getText().toString()), Toast.LENGTH_SHORT).show();
                                                        try {
                                                            Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                                                            field.setAccessible(true);
                                                            field.set(dialogInterface, false);
                                                        } catch (NoSuchFieldException e) {
                                                            e.printStackTrace();
                                                        } catch (IllegalAccessException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //ToDo: 你想做的事情
                                            //Toast.makeText(Daiban.this, "关闭按钮", Toast.LENGTH_LONG).show();
                                            try {
                                                Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                                                field.setAccessible(true);
                                                field.set(dialogInterface, true);
                                            } catch (NoSuchFieldException e) {
                                                e.printStackTrace();
                                            } catch (IllegalAccessException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                            builder1.create().show();
                        }else {
                            Toast.makeText(Daiban.this, "请填写完整提交！", Toast.LENGTH_SHORT).show();
                            try {
                                Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                                field.setAccessible(true);
                                field.set(dialogInterface, false);
                            } catch (NoSuchFieldException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo: 你想做的事情
                        //Toast.makeText(Daiban.this, "关闭按钮", Toast.LENGTH_LONG).show();
                        try {
                            Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                            field.setAccessible(true);
                            field.set(dialogInterface, true);
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                });

        builder.create().show();
    }

    private void showMyStyle1(int position) {
        @SuppressLint("InflateParams") final View view = LayoutInflater.from(this).inflate(R.layout.daiban_dialog2, null);
        add_name = view.findViewById(R.id.name_edit);
        add_name.addTextChangedListener(new LimitInputTextWatcher(add_name));
        add_card = view.findViewById(R.id.card_edit);
        add_relation = view.findViewById(R.id.relation_edit);
        add_relation.addTextChangedListener(new LimitInputTextWatcher(add_relation));




        add_name.setText(mList.get(position).get("name"));
        add_name.setEnabled(false);
        add_card.setText(mList.get(position).get("card_id"));
        add_card.setEnabled(false);
        add_relation.setText(mList.get(position).get("relationship"));
        final int transactor_id = Integer.parseInt(mList.get(position).get("transactor_id"));

//        if (mList.get(position).get("if_oneself"))
//        type.setSelection(mList.get(position).get(""));

        builder = new AlertDialog.Builder(this).setView(view).setTitle("修改办理")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, int i) {
                        IDCard cc = new IDCard();

                        if (add_name.getText().toString().length() != 0 && add_card.getText().toString().length() != 0) {
                            try {
                                if (cc.IDCardValidate(add_card.getText().toString()) == "") {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                String postUrl = "http://175.23.169.100:9040/transactor/updateTransactor";
                                                JSONObject jsonObject = new JSONObject();
                                                jsonObject.put("name", add_name.getText().toString());
                                                jsonObject.put("card_id", add_card.getText().toString());
                                                jsonObject.put("transactor_id", transactor_id);
                                                jsonObject.put("relationship",add_relation.getText().toString() );
                                                jsonObject.put("audit_status", 0);
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
                                                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
                                                StringBuffer sb = new StringBuffer();
                                                String str;
                                                while ((str = reader.readLine()) != null) {
                                                    sb.append(str);
                                                }
                                                JSONObject jsonObj1 = new JSONObject(sb.toString());
                                                isBindSuccess = jsonObj1.getInt("code");
                                                if (isBindSuccess == 0) {
                                                    //editor.putString("idCard",add_card.getText().toString());

                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(Daiban.this, "修改成功！", Toast.LENGTH_SHORT).show();
                                                            onCreate(null);
                                                            try {
                                                                Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                                                                field.setAccessible(true);
                                                                field.set(dialogInterface, true);
                                                            } catch (NoSuchFieldException e) {
                                                                e.printStackTrace();
                                                            } catch (IllegalAccessException e) {
                                                                e.printStackTrace();
                                                            }
                                                            //daibanAdapter.notifyDataSetChanged();
                                                        }
                                                    });
                                                } else {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(Daiban.this, "修改失败！", Toast.LENGTH_SHORT).show();
                                                            onCreate(null);
                                                            try {
                                                                Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                                                                field.setAccessible(true);
                                                                field.set(dialogInterface, true);
                                                            } catch (NoSuchFieldException e) {
                                                                e.printStackTrace();
                                                            } catch (IllegalAccessException e) {
                                                                e.printStackTrace();
                                                            }
                                                            //daibanAdapter.notifyDataSetChanged();
                                                        }
                                                    });

                                                }
                                            } catch (MalformedURLException e) {
                                                e.printStackTrace();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }).start();
                                } else {
                                    Toast.makeText(Daiban.this, cc.IDCardValidate(add_card.getText().toString()), Toast.LENGTH_SHORT).show();
                                    try {
                                        Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                                        field.setAccessible(true);
                                        field.set(dialogInterface, false);
                                    } catch (NoSuchFieldException e) {
                                        e.printStackTrace();
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(Daiban.this, "请填写完整提交！", Toast.LENGTH_SHORT).show();
                            try {
                                Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                                field.setAccessible(true);
                                field.set(dialogInterface, false);
                            } catch (NoSuchFieldException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo: 你想做的事情
                        //Toast.makeText(Daiban.this, "关闭按钮", Toast.LENGTH_LONG).show();
                        try {
                            Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                            field.setAccessible(true);
                            field.set(dialogInterface, true);
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                });

        builder.create().show();
    }

    public void parse(String var) {
        try {
            JsonObject jsonObject1 = new JsonParser().parse(var).getAsJsonObject();
            String jGroup1 = jsonObject1.get("detial").toString();
            mList = new Gson().fromJson(jGroup1, new TypeToken<List<Map<String, String>>>() {
            }.getType());

            daibanAdapter = new DaibanAdapter(Daiban.this, mList, listView);
            listView.setAdapter(daibanAdapter);
            daibanAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
