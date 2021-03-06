package com.example.Liudiao.ui.home;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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

public class AddFamily extends AppCompatActivity {

    private SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private int current_transId;
    private int isAddSuccess;
    private String trans_address;
    private String trans_address_detail;

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
    private Spinner relation;
    private AlertDialog.Builder builder;
    private String[] relationList = {"","??????","??????","??????","??????","??????","??????","??????","??????????????????","?????????????????????","????????????"} ;
    private ArrayAdapter<String> arr_adapter_relation;

    private ArrayList<ArraJsonBean> options1Items = new ArrayList<>(); //???
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//???
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>(); //???

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_family);

        preferences = getSharedPreferences("daiban", Activity.MODE_PRIVATE);
        editor = preferences.edit();
        current_transId = preferences.getInt("current_banliId",0);
        trans_address = preferences.getString(current_transId+"address","");
        trans_address_detail = preferences.getString(current_transId+"address_detail","");

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
        relation = (Spinner) findViewById(R.id.member_relation);



        arr_adapter_relation = new ArrayAdapter<String>(this,R.layout.simple_spinner_item,relationList);
        arr_adapter_relation.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        relation.setAdapter(arr_adapter_relation);

        checkBox3.setChecked(true);
        address.setText(trans_address);
        address_detail.setText(trans_address_detail);

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
                final IDCard cc = new IDCard();
                if (!name.getText().toString().equals("") && !phone.getText().toString().equals("") && s!=null ){
                    if (isMobilPhone(phone.getText().toString())){
                        final String finalS = s;
                        final String finalS1 = s;
                        builder = new AlertDialog.Builder(AddFamily.this).setTitle("????????????")
                                .setMessage("??????????????????????????????????????????????????????????????????????????????????????????")
                                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (idCard.getText().toString().length() != 0) {
                                            try {
                                                if (cc.IDCardValidate(idCard.getText().toString()) == "") {
                                                    final String finalS = finalS1;
                                                    new Thread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            try {
                                                                String postUrl = "http://175.23.169.100:9040/family/addFamily";
                                                                JSONObject jsonObject = new JSONObject();
                                                                jsonObject.put("transactor_id", current_transId);
                                                                jsonObject.put("name", name.getText().toString());
                                                                jsonObject.put("relation", relation.getSelectedItemPosition());
                                                                jsonObject.put("contact_type", finalS);
                                                                jsonObject.put("mobile", phone.getText().toString());
                                                                jsonObject.put("card_id", idCard.getText().toString());
                                                                if (!address.getText().toString().equals("")) {
                                                                    jsonObject.put("address", address.getText().toString());
                                                                }
                                                                if (!address_detail.getText().toString().equals("")) {
                                                                    jsonObject.put("address_detail", address_detail.getText().toString());
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
                                                                            Toast.makeText(AddFamily.this, "???????????????", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                                } else if (isAddSuccess == 808){
                                                                    runOnUiThread(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            Toast.makeText(AddFamily.this, "??????????????????????????????????????????????????????????????????????????????", Toast.LENGTH_LONG).show();

                                                                        }
                                                                    });
                                                                }else {
                                                                    runOnUiThread(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            Toast.makeText(AddFamily.this, "???????????????", Toast.LENGTH_SHORT).show();

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

                                                } else {
                                                    Toast.makeText(AddFamily.this, cc.IDCardValidate(idCard.getText().toString()), Toast.LENGTH_SHORT).show();

                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            final String finalS1 = finalS;
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        String postUrl = "http://175.23.169.100:9040/family/addFamily";
                                                        JSONObject jsonObject = new JSONObject();
                                                        jsonObject.put("transactor_id", current_transId);
                                                        jsonObject.put("name", name.getText().toString());
                                                        jsonObject.put("relation", relation.getSelectedItemPosition());
                                                        jsonObject.put("contact_type", finalS1);
                                                        jsonObject.put("mobile", phone.getText().toString());
                                                        if (!address.getText().toString().equals("")) {
                                                            jsonObject.put("address", address.getText().toString());
                                                        }
                                                        if (!address_detail.getText().toString().equals("")) {
                                                            jsonObject.put("address_detail", address_detail.getText().toString());
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
                                                                    Toast.makeText(AddFamily.this, "???????????????", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                        } else if (isAddSuccess == 808){
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    Toast.makeText(AddFamily.this, "??????????????????????????????????????????????????????????????????????????????", Toast.LENGTH_LONG).show();

                                                                }
                                                            });
                                                        }else {
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    Toast.makeText(AddFamily.this, "???????????????", Toast.LENGTH_SHORT).show();

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
                                    }
                                }).setNegativeButton("??????", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        builder.create().show();
                    }else {
                        Toast.makeText(AddFamily.this,"?????????????????????????????????",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(AddFamily.this,"????????????????????????",Toast.LENGTH_SHORT).show();
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

    private void showPickerView() {    // ??????????????????????????????????????????
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //?????????????????????????????????????????????
                address.setText(options1Items.get(options1).getPickerViewText()+" "
                        + options2Items.get(options1).get(options2)+" "
                        + options3Items.get(options1).get(options2).get(options3)+" ");

            }
        })
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //???????????????????????????
                .setContentTextSize(20)
                .build();
        /*pvOptions.setPicker(options1Items);//???????????????
        pvOptions.setPicker(options1Items, options2Items);//???????????????*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//???????????????
        pvOptions.show();
    }

    private void initJsonData() {//???????????? ???????????????????????????
        /**
         * ?????????assets ????????????Json??????????????????????????????????????????????????????
         * ???????????????????????????
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//??????assets????????????json????????????

        ArrayList<ArraJsonBean> jsonBean = parseData(JsonData);//???Gson ????????????

        /**
         * ??????????????????
         *
         * ???????????????????????????JavaBean????????????????????????????????? IPickerViewData ?????????
         * PickerView?????????getPickerViewText????????????????????????????????????
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//????????????
            ArrayList<String> CityList = new ArrayList<>();//????????????????????????????????????
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//??????????????????????????????????????????

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//??????????????????????????????
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//????????????
                ArrayList<String> City_AreaList = new ArrayList<>();//??????????????????????????????

                //??????????????????????????????????????????????????????????????????null ?????????????????????????????????????????????
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//??????????????????????????????
            }

            /**
             * ??????????????????
             */
            options2Items.add(CityList);

            /**
             * ??????????????????
             */
            options3Items.add(Province_AreaList);
        }
    }

    public ArrayList<ArraJsonBean> parseData(String result) {//Gson ??????
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
