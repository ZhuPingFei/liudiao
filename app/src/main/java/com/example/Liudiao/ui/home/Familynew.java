package com.example.Liudiao.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Liudiao.R;
import com.example.Liudiao.ui.home.adpter.FamilyAdapter;
import com.example.Liudiao.ui.home.adpter.RequestFamilyThread;
import com.example.Liudiao.ui.notifications.Daiban;
import com.example.Liudiao.ui.notifications.RequestDaibanthread;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Familynew extends Activity {

    private SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private AlertDialog.Builder builder;
    private ArrayList<String> relationList ;
    private ArrayAdapter<String> arr_adapter_relation;

    private int current_transId;

    private int isAddSuccess;

    private String member_nameInput;
    private int relationInput;
    private String member_phoneInput;

    private ListView listView;
    private FamilyAdapter familyAdapter;

    private ImageView r_back;
    private TextView okey;
    private TextView add;
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
        setContentView(R.layout.home_family);

        preferences = getSharedPreferences("daiban", Activity.MODE_PRIVATE);
        editor = preferences.edit();
        current_transId = preferences.getInt("current_banliId",0);

        relationList = new ArrayList<>();
        relationList.add("父亲");
        relationList.add("母亲");
        relationList.add("兄弟");
        relationList.add("姐妹");
        relationList.add("儿子");
        relationList.add("女儿");

        listView = (ListView) findViewById(R.id.member_list);

        String url = "http://175.23.169.100:9000/family/getFamily";
        RequestFamilyThread rdt = new RequestFamilyThread(url,current_transId,handler);
        rdt.start();

        add = (TextView) findViewById(R.id.add_member);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                familyAdapter = new FamilyAdapter(Familynew.this, listView);
//                listView.setAdapter(familyAdapter);
//                familyAdapter.notifyDataSetChanged();
                showMyStyle();

            }
        });

        r_back = (ImageView) findViewById(R.id.back);
        r_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        okey = (TextView) findViewById(R.id.okey);
        okey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dataCommit();
                onBackPressed();
            }
        });
    }

    public static boolean isMobilPhone(String phone) {
        if (phone.isEmpty()) {
            return false;
        }
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18([0-4]|[6-9]))|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }

    private void showMyStyle() {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(this).inflate(R.layout.family_list, null);
        final EditText name = view.findViewById(R.id.member_name);
        final Spinner relation = view.findViewById(R.id.member_relation);
        final EditText phone = view.findViewById(R.id.member_phone);
        arr_adapter_relation = new ArrayAdapter<String>(view.getContext(),R.layout.simple_spinner_item,relationList);
        arr_adapter_relation.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        relation.setAdapter(arr_adapter_relation);

        builder = new AlertDialog.Builder(this).setView(view).setTitle("添加成员")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        member_nameInput = name.getText().toString();
                        relationInput =relation.getSelectedItemPosition();
                        member_phoneInput = phone.getText().toString();
                        if (isMobilPhone(member_phoneInput)){
                            if (!member_phoneInput.equals("")&&!member_nameInput.equals("")){
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            String postUrl = "http://175.23.169.100:9000/family/addFamily";
                                            JSONObject jsonObject = new JSONObject();
                                            jsonObject.put("transactor_id",current_transId);
                                            jsonObject.put("name",member_nameInput);
                                            jsonObject.put("relation",relationInput);
                                            jsonObject.put("mobile",member_phoneInput);
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
                                            isAddSuccess = jsonObj1.getInt("code");
                                            if (isAddSuccess == 0){

                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(Familynew.this,"添加成功！",Toast.LENGTH_SHORT).show();
                                                        onCreate(null);
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
                                Toast.makeText(Familynew.this,"请填写完整信息！",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(Familynew.this,"请填写正确的手机号码！",Toast.LENGTH_SHORT).show();
                        }

                        //Toast.makeText(Familynew.this,member_nameInput+" "+relationInput+" "+member_phoneInput,Toast.LENGTH_SHORT).show();
                        //Toast.makeText(Familynew.this,"",Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
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

            JsonObject jsonObject1 = new JsonParser().parse(var).getAsJsonObject();
            String jGroup1 = jsonObject1.get("detial").toString();
            //mList.clear();
            mList = new Gson().fromJson(jGroup1, new TypeToken<List<Map<String, String>>>() {
            }.getType());

            familyAdapter = new FamilyAdapter(Familynew.this, mList, listView);
            listView.setAdapter(familyAdapter);
            familyAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Dialog
}
