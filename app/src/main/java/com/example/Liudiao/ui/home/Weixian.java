package com.example.Liudiao.ui.home;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.Liudiao.Main;
import com.example.Liudiao.R;
import com.example.Liudiao.ui.ArraJsonBean;
import com.example.Liudiao.ui.GetJsonDataUtil;
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
import java.util.TreeMap;

public class Weixian extends AppCompatActivity {

    private ImageView r_back;
    private TextView okay;

    private RadioGroup radioGroup1;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private RadioButton radioButton5;
    private RadioButton radioButton6;
    private RadioButton radioButton7;
    private RadioButton radioButton8;
    private RadioButton radioButton9;
    private RadioButton radioButton_1;

    private TextView text2;
    private RadioGroup radioGroup2;
    private RadioButton radioButton10;
    private RadioButton radioButton11;
    private RadioButton radioButton12;
    private RadioButton radioButton13;
    private RadioButton radioButton_2;

    private EditText edit1;

    private RadioGroup radioGroup3;
    private RadioButton radioButton21;
    private RadioButton radioButton22;
    private RelativeLayout visible2;
    private EditText edit2;
    
    private RadioButton jingchangchou;
    private RadioButton ouerchou;
    private RadioButton neverchou;

    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;
    private CheckBox checkBox5;
    private CheckBox checkBox6;
    private CheckBox checkBox7;
    private CheckBox checkBox8;
    private CheckBox checkBox9;
    private CheckBox checkBox10;
    private CheckBox checkBox11;
    private EditText edit3;

    private RadioGroup radioGroup4;
    private RadioButton radioButton41;
    private RadioButton radioButton42;
    private RadioButton radioButton43;
    private RelativeLayout selectPlace;
    private TextView selectPlace1;

    private RadioGroup radioGroup5;
    private RadioButton radioButton51;
    private RadioButton radioButton52;
    private RadioButton radioButton53;
    private RelativeLayout selectPlace2;
    private Spinner selectPlace_spi;
    private int nationPosition;

    private RadioButton radioButton61;
    private RadioButton radioButton62;
    private RadioButton radioButton63;

    private RadioButton radioButton71;
    private RadioButton radioButton72;
    private RadioButton radioButton73;

    private RadioButton radioButton81;
    private RadioButton radioButton82;
    private RadioButton radioButton83;

    private RadioButton radioButton91;
    private RadioButton radioButton92;
    private RadioButton radioButton93;

    private RadioGroup radioGroup6;
    private RadioGroup radioGroup7;
    private RadioGroup radioGroup8;
    private RadioGroup radioGroup9;
    private RadioGroup smoke_group;

    private AlertDialog.Builder builder;
    private ProgressDialog progressDialog;

    private ArrayList<ArraJsonBean> options1Items = new ArrayList<>(); //省
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//市
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>(); //区

    private String[] nationArray = {"中国",
            "安哥拉               ",
            "阿富汗                ",
            "阿尔巴尼亚           ",
            "阿尔及利亚           ",
            "安道尔共和国         ",
            "安圭拉岛            ",
            "安提瓜和巴布达      ",
            "澳大利亚              ",
            "奥地利                ",
            "阿塞拜疆             ",
            "巴哈马              ",
            "巴林                 ",
            "孟加拉国             ",
            "巴巴多斯            ",
            "白俄罗斯             ",
            "比利时                ",
            "伯利兹               ",
            "贝宁                 ",
            "百慕大群岛          ",
            "玻利维亚             ",
            "博茨瓦纳             ",
            "巴西                  ",
            "文莱                 ",
            "保加利亚             ",
            "布基纳法索           ",
            "缅甸                  ",
            "布隆迪               ",
            "喀麦隆               ",
            "加拿大                 ",
            "开曼群岛            ",
            "中非共和国           ",
            "乍得                 ",
            "智利                  ",
            "哥伦比亚              ",
            "刚果                 ",
            "库克群岛             ",
            "哥斯达黎加           ",
            "古巴                  ",
            "塞浦路斯             ",
            "捷克                 ",
            "丹麦                  ",
            "吉布提               ",
            "多米尼加共和国      ",
            "厄瓜多尔             ",
            "埃及                  ",
            "萨尔瓦多             ",
            "爱沙尼亚             ",
            "埃塞俄比亚           ",
            "斐济                 ",
            "芬兰                 ",
            "法国                  ",
            "法属圭亚那           ",
            "加蓬                 ",
            "冈比亚               ",
            "格鲁吉亚             ",
            "德国                  ",
            "加纳                 ",
            "直布罗陀             ",
            "希腊                  ",
            "关岛                ",
            "危地马拉             ",
            "几内亚               ",
            "圭亚那               ",
            "海地                 ",
            "洪都拉斯             ",
            "香港                 ",
            "匈牙利                ",
            "冰岛                 ",
            "印度                  ",
            "印度尼西亚            ",
            "伊朗                  ",
            "伊拉克               ",
            "爱尔兰               ",
            "以色列               ",
            "意大利                ",
            "科特迪瓦             ",
            "日本                  ",
            "约旦                 ",
            "柬埔寨               ",
            "肯尼亚               ",
            "韩国                  ",
            "科威特               ",
            "老挝                 ",
            "拉脱维亚             ",
            "黎巴嫩               ",
            "莱索托               ",
            "利比里亚             ",
            "利比亚               ",
            "列支敦士登           ",
            "立陶宛               ",
            "卢森堡               ",
            "澳门                 ",
            "马达加斯加           ",
            "马拉维               ",
            "马来西亚              ",
            "马尔代夫             ",
            "马里                 ",
            "马提尼克             ",
            "毛里求斯             ",
            "墨西哥                ",
            "摩尔多瓦             ",
            "摩纳哥               ",
            "蒙古                 ",
            "蒙特塞拉特岛        ",
            "摩洛哥               ",
            "莫桑比克             ",
            "纳米比亚             ",
            "尼泊尔               ",
            "荷属安的列斯         ",
            "荷兰                  ",
            "新西兰                ",
            "尼加拉瓜             ",
            "尼日尔               ",
            "尼日利亚             ",
            "挪威                  ",
            "阿曼                 ",
            "巴基斯坦              ",
            "巴拿马               ",
            "巴布亚新几内亚       ",
            "巴拉圭               ",
            "秘鲁                  ",
            "菲律宾                ",
            "波兰                  ",
            "法属玻利尼西亚       ",
            "葡萄牙               ",
            "波多黎各            ",
            "卡塔尔               ",
            "留尼旺               ",
            "罗马尼亚              ",
            "俄罗斯                 ",
            "西萨摩亚             ",
            "圣多美和普林西比     ",
            "沙特阿拉伯           ",
            "塞内加尔             ",
            "塞舌尔               ",
            "塞拉利昂             ",
            "新加坡                ",
            "斯洛伐克             ",
            "斯洛文尼亚           ",
            "所罗门群岛           ",
            "索马里               ",
            "南非                  ",
            "西班牙                ",
            "斯里兰卡              ",
            "圣卢西亚            ",
            "圣文森特            ",
            "苏丹                 ",
            "苏里南               ",
            "斯威士兰             ",
            "瑞典                  ",
            "瑞士                  ",
            "叙利亚               ",
            "台湾省               ",
            "塔吉克斯坦           ",
            "坦桑尼亚             ",
            "泰国                  ",
            "多哥                 ",
            "汤加                 ",
            "突尼斯               ",
            "土耳其                ",
            "土库曼斯坦           ",
            "乌干达               ",
            "乌克兰               ",
            "阿拉伯联合酋长国     ",
            "英国                  ",
            "美国                   ",
            "乌拉圭               ",
            "委内瑞拉              ",
            "越南                  ",
            "也门                 ",
            "南斯拉夫             ",
            "津巴布韦             ",
            "扎伊尔               ",
            "赞比亚               ",
            "牙买加              ",
            "毛里塔尼亚           ",
            "佛得角              ",
            "赤道几内亚           ",
            "几内亚比绍           ",
            "卢旺达              ",
            "科摩罗              ",
            "阿鲁巴岛            ",
            "法罗群岛            ",
            "格陵兰岛            ",
            "基里巴斯            ",
            "瓦努阿图            ",
            "不丹                "};
    private ArrayAdapter<String> arr_adapter_nation;

    private SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private int current_transId;
    private boolean isMe;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bd = msg.getData();

            int code = bd.getInt("code");

            if (code == 0){

                String job = bd.getString("job");
                String job_detail = bd.getString("job_detail");
                String other_job = bd.getString("other_job");

                String radiobutton1 = bd.getString("radiobutton1");
                String address = bd.getString("address");

                String radiobutton2 = bd.getString("radiobutton2");
                String nation = bd.getString("nation");

                String radiobutton3 = bd.getString("radiobutton3");
                String radiobutton4 = bd.getString("radiobutton4");
                String radiobutton5 = bd.getString("radiobutton5");
                String radiobutton6 = bd.getString("radiobutton6");

                radioGroup1 = (RadioGroup) findViewById(R.id.weixian_radiogroup1);
                radioButton1 = (RadioButton)findViewById(R.id.weixian_rediobutton1);
                radioButton2 = (RadioButton)findViewById(R.id.weixian_rediobutton2);
                radioButton3 = (RadioButton)findViewById(R.id.weixian_rediobutton3);
                radioButton4 = (RadioButton)findViewById(R.id.weixian_rediobutton4);
                radioButton5 = (RadioButton)findViewById(R.id.weixian_rediobutton5);
                radioButton6 = (RadioButton)findViewById(R.id.weixian_rediobutton6);
                radioButton7 = (RadioButton)findViewById(R.id.weixian_rediobutton7);
                radioButton8 = (RadioButton)findViewById(R.id.weixian_rediobutton8);
                radioButton9 = (RadioButton)findViewById(R.id.weixian_rediobutton9);
                radioButton_1 = (RadioButton)findViewById(R.id.weixian_rediobutton_1);

                text2 = (TextView) findViewById(R.id.weixian_text2);
                radioGroup2 = (RadioGroup) findViewById(R.id.weixian_radiogroup2);
                radioButton10 = (RadioButton)findViewById(R.id.weixian_rediobutton10);
                radioButton11 = (RadioButton)findViewById(R.id.weixian_rediobutton11);
                radioButton12 = (RadioButton)findViewById(R.id.weixian_rediobutton12);
                radioButton13 = (RadioButton)findViewById(R.id.weixian_rediobutton13);
                radioButton_2 = (RadioButton)findViewById(R.id.weixian_rediobutton_2);

                edit1 = (EditText) findViewById(R.id.weixian_edit1);

                radioGroup4 = (RadioGroup)findViewById(R.id.weixian_radiogroup4);
                radioButton41 = (RadioButton)findViewById(R.id.weixian_rediobutton41);
                radioButton42 = (RadioButton)findViewById(R.id.weixian_rediobutton42);
                radioButton43 = (RadioButton)findViewById(R.id.weixian_rediobutton43);
                selectPlace1 = (TextView)findViewById(R.id.weixian_selectPlace1) ;
                selectPlace = (RelativeLayout)findViewById(R.id.weixian_selectPlace);

                radioGroup5 = (RadioGroup)findViewById(R.id.weixian_radiogroup5);
                radioButton51 = (RadioButton)findViewById(R.id.weixian_rediobutton51);
                radioButton52 = (RadioButton)findViewById(R.id.weixian_rediobutton52);
                radioButton53 = (RadioButton)findViewById(R.id.weixian_rediobutton53);

                selectPlace2 = (RelativeLayout)findViewById(R.id.weixian_selectPlace2);
                selectPlace_spi = (Spinner)findViewById(R.id.weixian_selectNation);
                radioButton61 = (RadioButton)findViewById(R.id.weixian_rediobutton61);
                radioButton62 = (RadioButton)findViewById(R.id.weixian_rediobutton62);
                radioButton63 = (RadioButton)findViewById(R.id.weixian_rediobutton63);
                radioButton71 = (RadioButton)findViewById(R.id.weixian_rediobutton71);
                radioButton72 = (RadioButton)findViewById(R.id.weixian_rediobutton72);
                radioButton73 = (RadioButton)findViewById(R.id.weixian_rediobutton73);
                radioButton81 = (RadioButton)findViewById(R.id.weixian_rediobutton81);
                radioButton82 = (RadioButton)findViewById(R.id.weixian_rediobutton82);
                radioButton83 = (RadioButton)findViewById(R.id.weixian_rediobutton83);
                radioButton91 = (RadioButton)findViewById(R.id.weixian_rediobutton91);
                radioButton92 = (RadioButton)findViewById(R.id.weixian_rediobutton92);
                radioButton93 = (RadioButton)findViewById(R.id.weixian_rediobutton93);

                if (!job.equals("") && !job.equals("null")){
                    if (Integer.parseInt(job) == 0){
                        radioButton1.setChecked(true);
                    }else if (Integer.parseInt(job) == 1){
                        radioButton2.setChecked(true);
                    }else if (Integer.parseInt(job) == 2){
                        radioButton3.setChecked(true);
                    }else if (Integer.parseInt(job) == 3){
                        radioButton4.setChecked(true);
                    }else if (Integer.parseInt(job) == 4){
                        radioButton5.setChecked(true);
                    }else if (Integer.parseInt(job) == 5){
                        radioButton6.setChecked(true);
                    }else if (Integer.parseInt(job) == 6){
                        radioButton7.setChecked(true);
                    }else if (Integer.parseInt(job) == 7){
                        radioButton8.setChecked(true);
                    }else if (Integer.parseInt(job) == 8){
                        radioButton9.setChecked(true);
                    }else if (Integer.parseInt(job) == 9){
                        radioButton_1.setChecked(true);
                    }
                }
                if (!job_detail.equals("") && !job_detail.equals("null")){
                    if (Integer.parseInt(job_detail) == 1){
                        radioButton10.setChecked(true);
                    }else if (Integer.parseInt(job_detail) == 2){
                        radioButton11.setChecked(true);
                    }else if (Integer.parseInt(job_detail) == 3){
                        radioButton12.setChecked(true);
                    }else if (Integer.parseInt(job_detail) == 4){
                        radioButton13.setChecked(true);
                    }else if (Integer.parseInt(job_detail) == 5){
                        radioButton_2.setChecked(true);
                    }
                }
                if (!other_job.equals("") && !other_job.equals("null")){
                    edit1.setText(other_job);
                }

                if (!radiobutton1.equals("") && !radiobutton1.equals("null")){
                    if (Integer.parseInt(radiobutton1) == 0){
                        radioButton41.setChecked(true);
                    }else if (Integer.parseInt(radiobutton1) == 1){
                        radioButton42.setChecked(true);
                    }else if (Integer.parseInt(radiobutton1) == 2){
                        radioButton43.setChecked(true);
                    }
                }
               if (!address.equals("") && !address.equals("null")){
                   selectPlace1.setText(address);
               }


                if (!radiobutton2.equals("") && !radiobutton2.equals("null")){
                    if (Integer.parseInt(radiobutton2) == 0){
                        radioButton51.setChecked(true);
                    }else if (Integer.parseInt(radiobutton2) == 1){
                        radioButton52.setChecked(true);
                    }else if (Integer.parseInt(radiobutton2) == 2){
                        radioButton53.setChecked(true);
                    }
                }
                if (!nation.equals("") && !nation.equals("null")){
                    selectPlace_spi.setSelection(Integer.parseInt(nation));
                }

                if (!radiobutton3.equals("") && !radiobutton3.equals("null")){
                    if (Integer.parseInt(radiobutton3) == 1){
                        radioButton61.setChecked(true);
                    }else if (Integer.parseInt(radiobutton3) == 0){
                        radioButton62.setChecked(true);
                    }else if (Integer.parseInt(radiobutton3) == 2){
                        radioButton63.setChecked(true);
                    }
                }

                if (!radiobutton4.equals("") && !radiobutton4.equals("null")){
                    if (Integer.parseInt(radiobutton4) == 1){
                        radioButton71.setChecked(true);
                    }else if (Integer.parseInt(radiobutton4) == 0){
                        radioButton72.setChecked(true);
                    }else if (Integer.parseInt(radiobutton4) == 2){
                        radioButton73.setChecked(true);
                    }
                }

                if (!radiobutton5.equals("") && !radiobutton5.equals("null")){
                    if (Integer.parseInt(radiobutton5) == 1){
                        radioButton81.setChecked(true);
                    }else if (Integer.parseInt(radiobutton5) == 0){
                        radioButton82.setChecked(true);
                    }else if (Integer.parseInt(radiobutton5) == 2){
                        radioButton83.setChecked(true);
                    }
                }

                if (!radiobutton6.equals("") && !radiobutton6.equals("null")){
                    if (Integer.parseInt(radiobutton6) == 1){
                        radioButton91.setChecked(true);
                    }else if (Integer.parseInt(radiobutton6) == 0){
                        radioButton92.setChecked(true);
                    }else if (Integer.parseInt(radiobutton6) == 2){
                        radioButton93.setChecked(true);
                    }
                }


            }
        }
    };

    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_weixian);

        preferences = getSharedPreferences("daiban",Activity.MODE_PRIVATE);
        editor = preferences.edit();
        current_transId = preferences.getInt("current_banliId",0);
        isMe = preferences.getBoolean("isMe",false);

        String url = "http://175.23.169.100:9030/case-danger-exposure/get";
        RequestWeixianThread rdt = new RequestWeixianThread(url,current_transId,handler);
        rdt.start();

        radioGroup1 = (RadioGroup) findViewById(R.id.weixian_radiogroup1);
        radioButton1 = (RadioButton)findViewById(R.id.weixian_rediobutton1);
        radioButton2 = (RadioButton)findViewById(R.id.weixian_rediobutton2);
        radioButton3 = (RadioButton)findViewById(R.id.weixian_rediobutton3);
        radioButton4 = (RadioButton)findViewById(R.id.weixian_rediobutton4);
        radioButton5 = (RadioButton)findViewById(R.id.weixian_rediobutton5);
        radioButton6 = (RadioButton)findViewById(R.id.weixian_rediobutton6);
        radioButton7 = (RadioButton)findViewById(R.id.weixian_rediobutton7);
        radioButton8 = (RadioButton)findViewById(R.id.weixian_rediobutton8);
        radioButton9 = (RadioButton)findViewById(R.id.weixian_rediobutton9);
        radioButton_1 = (RadioButton)findViewById(R.id.weixian_rediobutton_1);

        text2 = (TextView) findViewById(R.id.weixian_text2);
        radioGroup2 = (RadioGroup) findViewById(R.id.weixian_radiogroup2);
        radioButton10 = (RadioButton)findViewById(R.id.weixian_rediobutton10);
        radioButton11 = (RadioButton)findViewById(R.id.weixian_rediobutton11);
        radioButton12 = (RadioButton)findViewById(R.id.weixian_rediobutton12);
        radioButton13 = (RadioButton)findViewById(R.id.weixian_rediobutton13);
        radioButton_2 = (RadioButton)findViewById(R.id.weixian_rediobutton_2);

        edit1 = (EditText) findViewById(R.id.weixian_edit1);

        radioGroup3 = (RadioGroup)findViewById(R.id.weixian_radiogroup3);
        radioButton21 = (RadioButton)findViewById(R.id.weixian_radioButton21);
        radioButton22 = (RadioButton)findViewById(R.id.weixian_radioButton22);
        visible2 = (RelativeLayout)findViewById(R.id.yunzhou);
        edit2 = (EditText)findViewById(R.id.weixian_edit2);
        
        jingchangchou = (RadioButton)findViewById(R.id.jingchangchou);
        ouerchou = (RadioButton)findViewById(R.id.ouerchou);
        neverchou = (RadioButton)findViewById(R.id.never);

        checkBox1 = (CheckBox)findViewById(R.id.checkbox1);
        checkBox2 = (CheckBox)findViewById(R.id.checkbox2);
        checkBox3 = (CheckBox)findViewById(R.id.checkbox3);
        checkBox4 = (CheckBox)findViewById(R.id.checkbox4);
        checkBox5 = (CheckBox)findViewById(R.id.checkbox5);
        checkBox6 = (CheckBox)findViewById(R.id.checkbox6);
        checkBox7 = (CheckBox)findViewById(R.id.checkbox7);
        checkBox8 = (CheckBox)findViewById(R.id.checkbox8);
        checkBox9 = (CheckBox)findViewById(R.id.checkbox9);
        checkBox10 = (CheckBox)findViewById(R.id.checkbox10);
        checkBox11 = (CheckBox)findViewById(R.id.checkbox11);
        edit3 = (EditText)findViewById(R.id.weixian_edit3);

        selectPlace1 = (TextView)findViewById(R.id.weixian_selectPlace1) ;
        selectPlace = (RelativeLayout)findViewById(R.id.weixian_selectPlace);

        radioGroup4 = (RadioGroup)findViewById(R.id.weixian_radiogroup4);
        radioButton41 = (RadioButton)findViewById(R.id.weixian_rediobutton41);
        radioButton42 = (RadioButton)findViewById(R.id.weixian_rediobutton42);
        radioButton43 = (RadioButton)findViewById(R.id.weixian_rediobutton43);

        radioGroup5 = (RadioGroup)findViewById(R.id.weixian_radiogroup5);
        radioButton51 = (RadioButton)findViewById(R.id.weixian_rediobutton51);
        radioButton52 = (RadioButton)findViewById(R.id.weixian_rediobutton52);
        radioButton53 = (RadioButton)findViewById(R.id.weixian_rediobutton53);

        selectPlace2 = (RelativeLayout)findViewById(R.id.weixian_selectPlace2);
        selectPlace_spi = (Spinner)findViewById(R.id.weixian_selectNation);
        radioButton61 = (RadioButton)findViewById(R.id.weixian_rediobutton61);
        radioButton62 = (RadioButton)findViewById(R.id.weixian_rediobutton62);
        radioButton63 = (RadioButton)findViewById(R.id.weixian_rediobutton63);
        radioButton71 = (RadioButton)findViewById(R.id.weixian_rediobutton71);
        radioButton72 = (RadioButton)findViewById(R.id.weixian_rediobutton72);
        radioButton73 = (RadioButton)findViewById(R.id.weixian_rediobutton73);
        radioButton81 = (RadioButton)findViewById(R.id.weixian_rediobutton81);
        radioButton82 = (RadioButton)findViewById(R.id.weixian_rediobutton82);
        radioButton83 = (RadioButton)findViewById(R.id.weixian_rediobutton83);
        radioButton91 = (RadioButton)findViewById(R.id.weixian_rediobutton91);
        radioButton92 = (RadioButton)findViewById(R.id.weixian_rediobutton92);
        radioButton93 = (RadioButton)findViewById(R.id.weixian_rediobutton93);

        radioGroup6 = (RadioGroup) findViewById(R.id.weixian_radiogroup6);
        radioGroup7 = (RadioGroup) findViewById(R.id.weixian_radiogroup7);
        radioGroup8 = (RadioGroup) findViewById(R.id.weixian_radiogroup8);
        radioGroup9 = (RadioGroup) findViewById(R.id.weixian_radiogroup9);
        smoke_group = (RadioGroup) findViewById(R.id.smoke_group);


        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String s = radioButton.getText().toString();
                if (s.equals("医务人员")){
                    text2.setVisibility(View.VISIBLE);
                    radioGroup2.setVisibility(View.VISIBLE);
                }else {
                    text2.setVisibility(View.GONE);
                    radioGroup2.setVisibility(View.GONE);
                }
            }
        });

        radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String s = radioButton.getText().toString();
                if (s.equals("是")){
                    visible2.setVisibility(View.VISIBLE);
                }else {
                    visible2.setVisibility(View.GONE);
                }
            }
        });

        radioGroup4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String s = radioButton.getText().toString();
                if (s.equals("否")){
                    selectPlace.setVisibility(View.GONE);
                }else {
                    selectPlace.setVisibility(View.VISIBLE);
                }
            }
        });

        radioGroup5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String s = radioButton.getText().toString();
                if (s.equals("否")){
                    selectPlace2.setVisibility(View.GONE);
                }else {
                    selectPlace2.setVisibility(View.VISIBLE);
                }
            }
        });

        arr_adapter_nation = new ArrayAdapter<String>(Weixian.this, R.layout.simple_spinner_item,nationArray);
        //设置样式
        arr_adapter_nation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        selectPlace_spi.setAdapter(arr_adapter_nation);

        //selectPlace_spi.setSelection(getNationposition);
        selectPlace_spi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nationPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        initJsonData();
        selectPlace1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickerView();
            }
        });

        r_back = (ImageView) findViewById(R.id.back);
        r_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        okay = (TextView) findViewById(R.id.okay);
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int job_select = -1;
                if (radioButton1.isChecked()){
                    job_select = 0;
                }else if (radioButton2.isChecked()){
                    job_select = 1;
                }else if (radioButton3.isChecked()){
                    job_select = 2;
                }else if (radioButton4.isChecked()){
                    job_select = 3;
                }else if (radioButton5.isChecked()){
                    job_select = 4;
                }else if (radioButton6.isChecked()){
                    job_select = 5;
                }else if (radioButton7.isChecked()){
                    job_select = 6;
                }else if (radioButton8.isChecked()){
                    job_select = 7;
                }else if (radioButton9.isChecked()){
                    job_select = 8;
                }else if (radioButton_1.isChecked()){
                    job_select = 9;
                }

                int job_detail_select = -1;
                if (radioButton10.isChecked()){
                    job_detail_select = 1;
                }else if (radioButton11.isChecked()){
                    job_detail_select = 2;
                }else if (radioButton12.isChecked()){
                    job_detail_select = 3;
                }else if (radioButton13.isChecked()){
                    job_detail_select = 4;
                }else if (radioButton_2.isChecked()){
                    job_detail_select = 5;
                }
                String other_job = edit1.getText().toString();
                if (other_job.equals("")){
                    other_job = null;
                }

                int down1 = -1;
                if (radioButton41.isChecked()){
                    down1 = 0;
                }else if (radioButton42.isChecked()){
                    down1 = 1;
                }else if (radioButton43.isChecked()){
                    down1 = 2;
                }
                String address = selectPlace1.getText().toString();
                if (address.equals("")){
                    address = null;
                }

                int down2 = -1;
                if (radioButton51.isChecked()){
                    down2 = 0;
                }else if (radioButton52.isChecked()){
                    down2 = 1;
                }else if (radioButton53.isChecked()){
                    down2 = 2;
                }

                int down3 = -1;
                if (radioButton61.isChecked()){
                    down3 = 1;
                }else if (radioButton62.isChecked()){
                    down3 = 0;
                }else if (radioButton63.isChecked()){
                    down3 = 2;
                }

                int down4 = -1;
                if (radioButton71.isChecked()){
                    down4 = 1;
                }else if (radioButton72.isChecked()){
                    down4 = 0;
                }else if (radioButton73.isChecked()){
                    down4 = 2;
                }

                int down5 = -1;
                if (radioButton81.isChecked()){
                    down5 = 1;
                }else if (radioButton82.isChecked()){
                    down5 = 0;
                }else if (radioButton83.isChecked()){
                    down5 = 2;
                }

                int down6 = -1;
                if (radioButton91.isChecked()){
                    down6 = 1;
                }else if (radioButton92.isChecked()){
                    down6 = 0;
                }else if (radioButton93.isChecked()){
                    down6 = 2;
                }

                //dataCommit();
                final int finalJob_select = job_select;
                final int finalJob_detail_select = job_detail_select;
                final int finalDown = down1;
                final int finalDown1 = down2;
                final int finalDown2 = down3;
                final int finalDown3 = down4;
                final int finalDown4 = down5;
                final int finalDown5 = down6;
                if (!isMe){
                    final String finalOther_job = other_job;
                    final String finalAddress = address;
                    builder = new AlertDialog.Builder(Weixian.this).setTitle("重要提醒")
                            .setMessage("当前为代办模式，是否确认提交？")
                            .setPositiveButton("提交", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //ToDo: 你想做的事情
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                String postUrl = "http://175.23.169.100:9030/case-danger-exposure/set";
                                                JSONObject jsonObject = new JSONObject();
                                                jsonObject.put("transactor_id",current_transId);

                                                if (finalJob_select!=-1){
                                                    jsonObject.put("job", finalJob_select);
                                                }else {
                                                    jsonObject.put("other_job", finalOther_job);
                                                }
                                                if (finalJob_select == 1){
                                                    jsonObject.put("job_detail", finalJob_detail_select);
                                                }
                                                if (finalDown != -1){
                                                    jsonObject.put("radiobutton1", finalDown);
                                                }
                                                if (finalDown == 1 || finalDown == 2){
                                                    jsonObject.put("address", finalAddress);
                                                }

                                                if (finalDown1 != -1){
                                                    jsonObject.put("radiobutton2", finalDown1);
                                                }
                                                if (finalDown1 == 1 || finalDown1 == 2){
                                                    jsonObject.put("nation",nationPosition);
                                                }
                                                if (finalDown2 != -1){
                                                    jsonObject.put("radiobutton3", finalDown2);
                                                }
                                                if (finalDown3 != -1){
                                                    jsonObject.put("radiobutton4", finalDown3);
                                                }
                                                if (finalDown4 != -1){
                                                    jsonObject.put("radiobutton5", finalDown4);
                                                }
                                                if (finalDown5 != -1){
                                                    jsonObject.put("radiobutton6", finalDown5);
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
                                                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"));
                                                StringBuffer sb = new StringBuffer();
                                                String str;
                                                while ((str = reader.readLine()) != null) {
                                                    sb.append(str);
                                                }
                                                JSONObject jsonObj1 = new JSONObject(sb.toString());
                                                int isUpadteSeccess = jsonObj1.getInt("code");
                                                if (isUpadteSeccess == 0){
                                                    editor.putBoolean(current_transId+"hasWeixian",true);
                                                    editor.commit();
                                                    Looper.prepare();
                                                    Toast.makeText(Weixian.this,"提交成功",Toast.LENGTH_SHORT).show();
                                                    Looper.loop();
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
                                    dialogInterface.dismiss();
                                    //onBackPressed();
                                    Intent intent = new Intent(Weixian.this, Main.class);
                                    startActivity(intent);
                                    finish();

                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                }else {
                    final String finalOther_job1 = other_job;
                    final String finalAddress1 = address;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String postUrl = "http://175.23.169.100:9030/case-danger-exposure/set";
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("transactor_id",current_transId);
                                jsonObject.put("transactor_id",current_transId);

                                if (finalJob_select!=-1){
                                    jsonObject.put("job", finalJob_select);
                                }else {
                                    jsonObject.put("other_job", finalOther_job1);
                                }
                                if (finalJob_select == 1){
                                    jsonObject.put("job_detail", finalJob_detail_select);
                                }
                                if (finalDown != -1){
                                    jsonObject.put("radiobutton1", finalDown);
                                }
                                if (finalDown == 1 || finalDown == 2){
                                    jsonObject.put("address", finalAddress1);
                                }

                                if (finalDown1 != -1){
                                    jsonObject.put("radiobutton2", finalDown1);
                                }
                                if (finalDown1 == 1 || finalDown1 == 2){
                                    jsonObject.put("nation",nationPosition);
                                }
                                if (finalDown2 != -1){
                                    jsonObject.put("radiobutton3", finalDown2);
                                }
                                if (finalDown3 != -1){
                                    jsonObject.put("radiobutton4", finalDown3);
                                }
                                if (finalDown4 != -1){
                                    jsonObject.put("radiobutton5", finalDown4);
                                }
                                if (finalDown5 != -1){
                                    jsonObject.put("radiobutton6", finalDown5);
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
                                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"));
                                StringBuffer sb = new StringBuffer();
                                String str;
                                while ((str = reader.readLine()) != null) {
                                    sb.append(str);
                                }
                                JSONObject jsonObj1 = new JSONObject(sb.toString());
                                int isUpadteSeccess = jsonObj1.getInt("code");
                                if (isUpadteSeccess == 0){
                                    editor.putBoolean(current_transId+"hasWeixian",true);
                                    editor.commit();
                                    Looper.prepare();
                                    Toast.makeText(Weixian.this,"提交成功",Toast.LENGTH_SHORT).show();
                                    Looper.loop();
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
                    onBackPressed();
                }
            }
        });

    }
    private void showPickerView() {    // 弹出选择器（省市区三级联动）
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                selectPlace1.setText(options1Items.get(options1).getPickerViewText() + " "
                        + options2Items.get(options1).get(options2) + " "
                        + options3Items.get(options1).get(options2).get(options3)+" ");

            }
        })
                .setTitleText("城市选择")
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

    public void dataCommit(){
        SharedPreferences preferences = getSharedPreferences("user_weixian", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();//获取编辑器

        if (radioButton1.isChecked()){
            editor.putBoolean("weixian_button1",true);
            editor.putBoolean("weixian_button2",false);
            editor.putBoolean("weixian_button3",false);
            editor.putBoolean("weixian_button4",false);
            editor.putBoolean("weixian_button5",false);
            editor.putBoolean("weixian_button6",false);
            editor.putBoolean("weixian_button7",false);
            editor.putBoolean("weixian_button8",false);
            editor.putBoolean("weixian_button9",false);

        }else if (radioButton2.isChecked()){
            editor.putBoolean("weixian_button1",false);
            editor.putBoolean("weixian_button2",true);
            editor.putBoolean("weixian_button3",false);
            editor.putBoolean("weixian_button4",false);
            editor.putBoolean("weixian_button5",false);
            editor.putBoolean("weixian_button6",false);
            editor.putBoolean("weixian_button7",false);
            editor.putBoolean("weixian_button8",false);
            editor.putBoolean("weixian_button9",false);
        }
        else if (radioButton3.isChecked()){
            editor.putBoolean("weixian_button1",false);
            editor.putBoolean("weixian_button2",false);
            editor.putBoolean("weixian_button3",true);
            editor.putBoolean("weixian_button4",false);
            editor.putBoolean("weixian_button5",false);
            editor.putBoolean("weixian_button6",false);
            editor.putBoolean("weixian_button7",false);
            editor.putBoolean("weixian_button8",false);
            editor.putBoolean("weixian_button9",false);
        }
        else if (radioButton4.isChecked()){
            editor.putBoolean("weixian_button1",false);
            editor.putBoolean("weixian_button2",false);
            editor.putBoolean("weixian_button3",false);
            editor.putBoolean("weixian_button4",true);
            editor.putBoolean("weixian_button5",false);
            editor.putBoolean("weixian_button6",false);
            editor.putBoolean("weixian_button7",false);
            editor.putBoolean("weixian_button8",false);
            editor.putBoolean("weixian_button9",false);
        }
        else if (radioButton5.isChecked()){
            editor.putBoolean("weixian_button1",false);
            editor.putBoolean("weixian_button2",false);
            editor.putBoolean("weixian_button3",false);
            editor.putBoolean("weixian_button4",false);
            editor.putBoolean("weixian_button5",true);
            editor.putBoolean("weixian_button6",false);
            editor.putBoolean("weixian_button7",false);
            editor.putBoolean("weixian_button8",false);
            editor.putBoolean("weixian_button9",false);
        }
        else if (radioButton6.isChecked()){
            editor.putBoolean("weixian_button1",false);
            editor.putBoolean("weixian_button2",false);
            editor.putBoolean("weixian_button3",false);
            editor.putBoolean("weixian_button4",false);
            editor.putBoolean("weixian_button5",false);
            editor.putBoolean("weixian_button6",true);
            editor.putBoolean("weixian_button7",false);
            editor.putBoolean("weixian_button8",false);
            editor.putBoolean("weixian_button9",false);
        }
        else if (radioButton7.isChecked()){
            editor.putBoolean("weixian_button1",false);
            editor.putBoolean("weixian_button2",false);
            editor.putBoolean("weixian_button3",false);
            editor.putBoolean("weixian_button4",false);
            editor.putBoolean("weixian_button5",false);
            editor.putBoolean("weixian_button6",false);
            editor.putBoolean("weixian_button7",true);
            editor.putBoolean("weixian_button8",false);
            editor.putBoolean("weixian_button9",false);
        }
        else if (radioButton8.isChecked()){
            editor.putBoolean("weixian_button1",false);
            editor.putBoolean("weixian_button2",false);
            editor.putBoolean("weixian_button3",false);
            editor.putBoolean("weixian_button4",false);
            editor.putBoolean("weixian_button5",false);
            editor.putBoolean("weixian_button6",false);
            editor.putBoolean("weixian_button7",false);
            editor.putBoolean("weixian_button8",true);
            editor.putBoolean("weixian_button9",false);
        }
        else{
            editor.putBoolean("weixian_button1",false);
            editor.putBoolean("weixian_button2",false);
            editor.putBoolean("weixian_button3",false);
            editor.putBoolean("weixian_button4",false);
            editor.putBoolean("weixian_button5",false);
            editor.putBoolean("weixian_button6",false);
            editor.putBoolean("weixian_button7",false);
            editor.putBoolean("weixian_button8",false);
            editor.putBoolean("weixian_button9",true);
        }

        if (radioButton10.isChecked()){
            editor.putBoolean("weixian_button10",true);
            editor.putBoolean("weixian_button11",false);
            editor.putBoolean("weixian_button12",false);
            editor.putBoolean("weixian_button13",false);
        }else if (radioButton11.isChecked()){
            editor.putBoolean("weixian_button10",false);
            editor.putBoolean("weixian_button11",true);
            editor.putBoolean("weixian_button12",false);
            editor.putBoolean("weixian_button13",false);
        }else if (radioButton12.isChecked()){
            editor.putBoolean("weixian_button10",false);
            editor.putBoolean("weixian_button11",false);
            editor.putBoolean("weixian_button12",true);
            editor.putBoolean("weixian_button13",false);
        }else {
            editor.putBoolean("weixian_button10",false);
            editor.putBoolean("weixian_button11",false);
            editor.putBoolean("weixian_button12",false);
            editor.putBoolean("weixian_button13",true);
        }

        if (radioButton21.isChecked()){
            editor.putBoolean("weixian_button21",true);
            editor.putBoolean("weixian_button22",false);
        }else {
            editor.putBoolean("weixian_button21",false);
            editor.putBoolean("weixian_button22",true);
        }

        editor.putString("weixian_edit1",edit1.getText().toString());
        editor.putString("weixian_yunzhou",edit2.getText().toString());
        
        if (jingchangchou.isChecked()){
            editor.putBoolean("weixian_jingchangshou",true);
            editor.putBoolean("weixian_ouerchou",false);
            editor.putBoolean("weixian_never",false);
        } else if (ouerchou.isChecked()) {
            editor.putBoolean("weixian_jingchangshou",false);
            editor.putBoolean("weixian_ouerchou",true);
            editor.putBoolean("weixian_never",false);
        }else {
            editor.putBoolean("weixian_jingchangshou",false);
            editor.putBoolean("weixian_ouerchou",false);
            editor.putBoolean("weixian_never",true);
        }

        editor.putBoolean("weixian_checkbox1",checkBox1.isChecked());
        editor.putBoolean("weixian_checkbox2",checkBox2.isChecked());
        editor.putBoolean("weixian_checkbox3",checkBox3.isChecked());
        editor.putBoolean("weixian_checkbox4",checkBox4.isChecked());
        editor.putBoolean("weixian_checkbox5",checkBox5.isChecked());
        editor.putBoolean("weixian_checkbox6",checkBox6.isChecked());
        editor.putBoolean("weixian_checkbox7",checkBox7.isChecked());
        editor.putBoolean("weixian_checkbox8",checkBox8.isChecked());
        editor.putBoolean("weixian_checkbox9",checkBox9.isChecked());
        editor.putBoolean("weixian_checkbox10",checkBox10.isChecked());
        editor.putBoolean("weixian_checkbox11",checkBox11.isChecked());
        editor.putString("weixian_edit3",edit3.getText().toString());

        editor.putString("weixian_place",selectPlace1.getText().toString());

        if (radioButton41.isChecked()){
            editor.putBoolean("weixian_button41",true);
            editor.putBoolean("weixian_button42",false);
            editor.putBoolean("weixian_button43",false);
        }else if(radioButton42.isChecked()){
            editor.putBoolean("weixian_button41",false);
            editor.putBoolean("weixian_button42",true);
            editor.putBoolean("weixian_button43",false);
        }else {
            editor.putBoolean("weixian_button41",false);
            editor.putBoolean("weixian_button42",false);
            editor.putBoolean("weixian_button43",true);
        }
        if (radioButton51.isChecked()){
            editor.putBoolean("weixian_button51",true);
            editor.putBoolean("weixian_button52",false);
            editor.putBoolean("weixian_button53",false);
        }else if(radioButton52.isChecked()){
            editor.putBoolean("weixian_button51",false);
            editor.putBoolean("weixian_button52",true);
            editor.putBoolean("weixian_button53",false);
        }else {
            editor.putBoolean("weixian_button51",false);
            editor.putBoolean("weixian_button52",false);
            editor.putBoolean("weixian_button53",true);
        }
        if (radioButton41.isChecked()){
            editor.putBoolean("weixian_button61",true);
            editor.putBoolean("weixian_button62",false);
            editor.putBoolean("weixian_button63",false);
        }else if(radioButton42.isChecked()){
            editor.putBoolean("weixian_button61",false);
            editor.putBoolean("weixian_button62",true);
            editor.putBoolean("weixian_button63",false);
        }else {
            editor.putBoolean("weixian_button61",false);
            editor.putBoolean("weixian_button62",false);
            editor.putBoolean("weixian_button63",true);
        }
        if (radioButton51.isChecked()){
            editor.putBoolean("weixian_button71",true);
            editor.putBoolean("weixian_button72",false);
            editor.putBoolean("weixian_button73",false);
        }else if(radioButton52.isChecked()){
            editor.putBoolean("weixian_button71",false);
            editor.putBoolean("weixian_button72",true);
            editor.putBoolean("weixian_button73",false);
        }else {
            editor.putBoolean("weixian_button71",false);
            editor.putBoolean("weixian_button72",false);
            editor.putBoolean("weixian_button73",true);
        }
        if (radioButton41.isChecked()){
            editor.putBoolean("weixian_button81",true);
            editor.putBoolean("weixian_button82",false);
            editor.putBoolean("weixian_button83",false);
        }else if(radioButton42.isChecked()){
            editor.putBoolean("weixian_button81",false);
            editor.putBoolean("weixian_button82",true);
            editor.putBoolean("weixian_button83",false);
        }else {
            editor.putBoolean("weixian_button81",false);
            editor.putBoolean("weixian_button82",false);
            editor.putBoolean("weixian_button83",true);
        }
        if (radioButton51.isChecked()){
            editor.putBoolean("weixian_button91",true);
            editor.putBoolean("weixian_button92",false);
            editor.putBoolean("weixian_button93",false);
        }else if(radioButton52.isChecked()){
            editor.putBoolean("weixian_button91",false);
            editor.putBoolean("weixian_button92",true);
            editor.putBoolean("weixian_button93",false);
        }else {
            editor.putBoolean("weixian_button91",false);
            editor.putBoolean("weixian_button92",false);
            editor.putBoolean("weixian_button93",true);
        }

        editor.putInt("weixian_nation",nationPosition);
        
        editor.commit();

    }
}
