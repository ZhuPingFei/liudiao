package com.example.Liudiao.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.Liudiao.R;
import com.example.Liudiao.ui.ArraJsonBean;
import com.example.Liudiao.ui.GetJsonDataUtil;
import com.example.Liudiao.ui.notifications.IDCard;
import com.google.gson.Gson;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MijieAlter extends AppCompatActivity {

    private SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private int current_transId;
    private int isAddSuccess;

    private ImageView r_back;
    private TextView okay;
    private EditText name;
    private EditText phone;
    private EditText idCard;
    private TextView address;
    private EditText address_detail;
    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;

    private ArrayList<ArraJsonBean> options1Items = new ArrayList<>(); //省
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//市
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>(); //区

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alter_mijie);

        preferences = getSharedPreferences("daiban", Activity.MODE_PRIVATE);
        editor = preferences.edit();
        current_transId = preferences.getInt("current_banliId",0);


        Intent intent = getIntent();
        final int id1 = intent.getIntExtra("id",0);
        String name1 = intent.getStringExtra("name");
        String contact_type1 = intent.getStringExtra("contact_type");
        String mobile1 = intent.getStringExtra("mobile");
        String card_id1 = intent.getStringExtra("card_id");
        String address1 = intent.getStringExtra("address");
        String address_detail1 = intent.getStringExtra("address_detail");

        r_back = (ImageView) findViewById(R.id.back);
        okay = (TextView) findViewById(R.id.okay);
        name = (EditText) findViewById(R.id.member_name);
        phone = (EditText) findViewById(R.id.member_phone);
        idCard = (EditText) findViewById(R.id.member_idCard);
        address = (TextView) findViewById(R.id.select_place);
        address_detail = (EditText) findViewById(R.id.member_address_detail);
        checkBox1 = (CheckBox) findViewById(R.id.checkbox1);
        checkBox2 = (CheckBox) findViewById(R.id.checkbox2);
        checkBox3 = (CheckBox) findViewById(R.id.checkbox3);
        checkBox4 = (CheckBox) findViewById(R.id.checkbox4);

        name.setText(name1);

        name.setEnabled(false);//不让改名

        phone.setText(mobile1);
        if (!contact_type1.equals("")){
            String[] split = contact_type1.split(",");
            for (int i = 0;i<split.length;i++){
                if (Integer.parseInt(split[i])==1){
                    checkBox1.setChecked(true);
                }else if (Integer.parseInt(split[i])==2){
                    checkBox2.setChecked(true);
                }else if (Integer.parseInt(split[i])==3){
                    checkBox3.setChecked(true);
                }else {
                    checkBox4.setChecked(true);
                }
            }
        }
        idCard.setText(card_id1);
        if (card_id1.length() > 0){
            idCard.setEnabled(false);//填过身份证了就不让改身份证号
        }


        address.setText(address1);
        address_detail.setText(address_detail1);

        r_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initJsonData();
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickerView();
            }
        });

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<Integer> list = new ArrayList<>();
                if (checkBox1.isChecked()){
                    list.add(1);
                }if (checkBox2.isChecked()){
                    list.add(2);
                }
                if (checkBox3.isChecked()){
                    list.add(3);
                }if (checkBox4.isChecked()) {
                    list.add(4);
                }
                String s = list.toString().replace(" ","");
                if (s.length()==2){
                    s = null;
                }else {
                    s = s.substring(1,s.length()-1);
                }

                IDCard cc = new IDCard();
                if (!name.getText().toString().equals("") && !phone.getText().toString().equals("") && s!=null){
                    if (isMobilPhone(phone.getText().toString())){
                        if (idCard.getText().toString().length()!= 0){
                            try {
                                if (cc.IDCardValidate(idCard.getText().toString()) == "") {
                                    final String finalS = s;
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                String postUrl = "http://175.23.169.100:9040/ContactOther/updateContactOther";
                                                JSONObject jsonObject = new JSONObject();
                                                jsonObject.put("id",id1);
                                                jsonObject.put("transactor_id", current_transId);
                                                jsonObject.put("name", name.getText().toString());
                                                jsonObject.put("contact_type", finalS);
                                                jsonObject.put("mobile", phone.getText().toString());
                                                jsonObject.put("card_id",idCard.getText().toString());
                                                if (!address.getText().toString().equals("")){
                                                    jsonObject.put("address",address.getText().toString());
                                                }
                                                if (!address_detail.getText().toString().equals("")){
                                                    jsonObject.put("address_detail",address_detail.getText().toString());
                                                }

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
                                                isAddSuccess = jsonObj1.getInt("code");
                                                if (isAddSuccess == 0) {

                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(MijieAlter.this, "修改成功！", Toast.LENGTH_SHORT).show();

                                                        }
                                                    });

                                                }else if (isAddSuccess == 808){
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(MijieAlter.this, "库中已存在该人，与所填写不符，如有疑问请联系运维人员", Toast.LENGTH_LONG).show();

                                                        }
                                                    });

                                                } else {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(MijieAlter.this, "修改失败！", Toast.LENGTH_SHORT).show();

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
//                                    Intent intent = new Intent(AddMijie.this,Mijie.class);
//                                    startActivity(intent);
//                                    finish();
                                    onBackPressed();

                                }else {
                                    Toast.makeText(MijieAlter.this,cc.IDCardValidate(idCard.getText().toString()),Toast.LENGTH_SHORT).show();

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else {
                            final String finalS1 = s;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        String postUrl = "http://175.23.169.100:9040/ContactOther/updateContactOther";
                                        JSONObject jsonObject = new JSONObject();
                                        jsonObject.put("id",id1);
                                        jsonObject.put("transactor_id", current_transId);
                                        jsonObject.put("name", name.getText().toString());
                                        jsonObject.put("contact_type", finalS1);
                                        jsonObject.put("mobile", phone.getText().toString());
                                        if (!address.getText().toString().equals("")){
                                            jsonObject.put("address",address.getText().toString());
                                        }
                                        if (!address_detail.getText().toString().equals("")){
                                            jsonObject.put("address_detail",address_detail.getText().toString());
                                        }
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
                                        isAddSuccess = jsonObj1.getInt("code");
                                        if (isAddSuccess == 0) {

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(MijieAlter.this, "修改成功！", Toast.LENGTH_SHORT).show();

                                                }
                                            });

                                        }else if (isAddSuccess == 808){
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(MijieAlter.this, "库中已存在该人，与所填写不符，如有疑问请联系运维人员", Toast.LENGTH_LONG).show();

                                                }
                                            });

                                        } else {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(MijieAlter.this, "修改失败！", Toast.LENGTH_SHORT).show();

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
                            onBackPressed();
                        }
                    }else {
                        Toast.makeText(MijieAlter.this,"请填写正确的手机号码！",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MijieAlter.this,"请填写完整信息！",Toast.LENGTH_SHORT).show();
                }
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

    private void showPickerView() {    // 弹出选择器（省市区三级联动）
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                address.setText(options1Items.get(options1).getPickerViewText()+" "
                        + options2Items.get(options1).get(options2)+" "
                        + options3Items.get(options1).get(options2).get(options3)+" ");

            }
        })
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private void initJsonData() {//解析数据 （省市区三级联动）
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<ArraJsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三级）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    public ArrayList<ArraJsonBean> parseData(String result) {//Gson 解析
        ArrayList<ArraJsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                ArraJsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), ArraJsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }
}
