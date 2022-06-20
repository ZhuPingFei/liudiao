package com.example.Liudiao.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.Liudiao.LimitInputTextWatcher;
import com.example.Liudiao.R;
import com.example.Liudiao.ui.ArraJsonBean;
import com.example.Liudiao.ui.home.adpter.FamilyAdapter;
import com.example.Liudiao.ui.home.adpter.RequestFamilyThread;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Familynew extends Activity {

    private ArrayList<ArraJsonBean> options1Items = new ArrayList<>(); //省
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//市
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>(); //区

    private SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private AlertDialog.Builder builder;
    private ArrayList<String> relationList ;
    private ArrayAdapter<String> arr_adapter_relation;
    private ArrayList<String> typeList ;
    private ArrayAdapter<String> arr_adapter_type;

    private int current_transId;
    private String trans_address;

    private int isAddSuccess;

    private String member_nameInput;
    private int relationInput;
    private String member_phoneInput;
    private String member_idCardInput;
    private String member_addressInput;
    private int typeInput;

    private ListView listView;
    private FamilyAdapter familyAdapter;

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
        setContentView(R.layout.home_family);

        preferences = getSharedPreferences("daiban", Activity.MODE_PRIVATE);
        editor = preferences.edit();
        current_transId = preferences.getInt("current_banliId",0);
        trans_address = preferences.getString(current_transId+"address","");

        listView = (ListView) findViewById(R.id.member_list);

        String url = "http://175.23.169.100:9040/family/getFamily";
        RequestFamilyThread rdt = new RequestFamilyThread(url,current_transId,handler);
        rdt.start();

        add = (ImageView) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Familynew.this, AddFamily.class);
                startActivity(intent);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //Toast.makeText(Mijie.this, "lalallalalla！", Toast.LENGTH_SHORT).show();
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Familynew.this);
                builder.setMessage("确定删除该人员信息？");
                builder.setTitle("系统提示");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final int id = Integer.parseInt(mList.get(position).get("id").toString());
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    URL httpUrl = new URL("http://175.23.169.100:9040/family/delFamily?transactor_id="+current_transId +"&id=" + id);

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
                                                familyAdapter.notifyDataSetChanged();
                                                listView.invalidate();
                                                Toast.makeText(Familynew.this, "删除成功！", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(Familynew.this, "删除失败！", Toast.LENGTH_SHORT).show();
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
                androidx.appcompat.app.AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return false;
            }
        });

        r_back = (ImageView) findViewById(R.id.back);
        r_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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


    public void parse(String var) {
        try {

            JsonObject jsonObject1 = new JsonParser().parse(var).getAsJsonObject();
            String jGroup1 = jsonObject1.get("detial").toString();
            //mList.clear();
            mList = new Gson().fromJson(jGroup1, new TypeToken<List<Map<String, String>>>() {
            }.getType());

            datacommit();

            familyAdapter = new FamilyAdapter(Familynew.this, mList, listView);
            listView.setAdapter(familyAdapter);
            familyAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Dialog
    private void datacommit(){

        StringBuffer stringBuffer1 = new StringBuffer("");
        for (int i = 0;i<mList.size();i++){
            Map<String, String> map = mList.get(i);
            String name = map.get("name")+"";
            String contact_type = map.get("contact_type")+"";
            int relationText = Integer.parseInt(map.get("relation")+"");
            String mobile = map.get("mobile")+"";

            String idCard = map.get("card_id")+"";
            String address = map.get("address")+"";
            String address_detail =map.get("address_detail")+"";
            String dizhi;
            if (address.equals("null") || address.equals("")){
                if (address_detail.equals("null") || address_detail.equals("")){
                    dizhi = "";
                }else {
                    dizhi = address_detail;
                }
            }else {
                if (address_detail.equals("null") || address_detail.equals("")){
                    dizhi = address;
                }else {
                    dizhi = address+address_detail;
                }
            }

            String relation;
            if (relationText ==0){
                relation="未知";
            }else if (relationText == 1){
                relation="父亲";
            }else if (relationText == 2){
                relation="母亲";
            }else if (relationText == 3){
                relation="儿子";
            }else if (relationText == 4){
                relation="女儿";
            }else if (relationText == 5){
                relation="夫妻";
            }else if (relationText == 6){
                relation="兄弟";
            }else if (relationText == 7){
                relation="姐妹";
            }else if (relationText == 8){
                relation="（外）祖父母";
            }else if (relationText == 9){
                relation="（外）孙子孙女";
            }else {
                relation="（其他关系）";
            }


            StringBuffer stringBuffer = new StringBuffer("");
            if (contact_type.length()!=0 && !contact_type.equals("null")) {
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
            if (idCard!=null && !idCard.equals("null")&&idCard.length()!=0){
                if (!dizhi.equals("")){
                    stringBuffer1.append("    该病例曾与其"+relation+" "+name+" "+stringBuffer.toString()+"，后者手机号是 "+map.get("mobile").toString()
                            +",身份证号是 "+map.get("card_id").toString()+"，住在 "+dizhi+"。\n");

                }else {
                   stringBuffer1.append("    该病例曾与其"+relation+" "+map.get("name").toString()+" "+stringBuffer.toString()+"，后者手机号是 "+map.get("mobile").toString()
                            +",身份证号是 "+map.get("card_id").toString()+"。\n");
                }
            }else {
                if (!dizhi.equals("")){
                    stringBuffer1.append("    该病例曾与其"+relation+" "+name+" "+stringBuffer.toString()+"，后者手机号是 "+map.get("mobile").toString()
                            +"，住在 "+dizhi+"。\n");

                }else {
                    stringBuffer1.append("    该病例曾与其"+relation+" "+map.get("name").toString()+" "+stringBuffer.toString()+"，后者手机号是 "+map.get("mobile").toString() +"。\n");
                }
            }


        }

        preferences = getSharedPreferences(current_transId+"baogao",Activity.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("mijieFamily",stringBuffer1.toString());
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onCreate(null);
    }
}
