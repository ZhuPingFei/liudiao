package com.example.Liudiao.ui.home;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.Liudiao.R;
import com.example.Liudiao.ui.ArraJsonBean;
import com.example.Liudiao.ui.GetJsonDataUtil;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

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

    private TextView text2;
    private RadioGroup radioGroup2;
    private RadioButton radioButton10;
    private RadioButton radioButton11;
    private RadioButton radioButton12;
    private RadioButton radioButton13;

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
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_weixian);

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
                dataCommit();
                onBackPressed();
            }
        });

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

        text2 = (TextView) findViewById(R.id.weixian_text2);
        radioGroup2 = (RadioGroup) findViewById(R.id.weixian_radiogroup2);
        radioButton10 = (RadioButton)findViewById(R.id.weixian_rediobutton10);
        radioButton11 = (RadioButton)findViewById(R.id.weixian_rediobutton11);
        radioButton12 = (RadioButton)findViewById(R.id.weixian_rediobutton12);
        radioButton13 = (RadioButton)findViewById(R.id.weixian_rediobutton13);

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

        SharedPreferences preferences = getSharedPreferences("user_weixian", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();//获取编辑器
        Boolean getRadioButton1 = preferences.getBoolean("weixian_button1", false);
        Boolean getRadioButton2 = preferences.getBoolean("weixian_button2", false);
        Boolean getRadioButton3 = preferences.getBoolean("weixian_button3", false);
        Boolean getRadioButton4 = preferences.getBoolean("weixian_button4", false);
        Boolean getRadioButton5 = preferences.getBoolean("weixian_button5", false);
        Boolean getRadioButton6 = preferences.getBoolean("weixian_button6", false);
        Boolean getRadioButton7 = preferences.getBoolean("weixian_button7", false);
        Boolean getRadioButton8 = preferences.getBoolean("weixian_button8", false);
        Boolean getRadioButton9 = preferences.getBoolean("weixian_button9", false);
        Boolean getRadioButton10 = preferences.getBoolean("weixian_button10", false);
        Boolean getRadioButton11 = preferences.getBoolean("weixian_button11", false);
        Boolean getRadioButton12 = preferences.getBoolean("weixian_button12", false);
        Boolean getRadioButton13 = preferences.getBoolean("weixian_button13", false);
        Boolean getRadioButton21 = preferences.getBoolean("weixian_button21",false);
        Boolean getRadioButton22 = preferences.getBoolean("weixian_button21",false);

        String getYunzhou = preferences.getString("weixian_yunzhou","");

        String getEdit1 = preferences.getString("weixian_edit1","");
        
        Boolean getJingchangshou = preferences.getBoolean("weixian_jingchengchou",false);
        Boolean getOuerchou = preferences.getBoolean("weixian_ouerchou",false);
        Boolean getNever = preferences.getBoolean("weixian_never",false);

        boolean getCheckbox1 = preferences.getBoolean("weixian_checkbox1",false);
        boolean getCheckbox2 = preferences.getBoolean("weixian_checkbox2",false);
        boolean getCheckbox3 = preferences.getBoolean("weixian_checkbox3",false);
        boolean getCheckbox4 = preferences.getBoolean("weixian_checkbox4",false);
        boolean getCheckbox5 = preferences.getBoolean("weixian_checkbox5",false);
        boolean getCheckbox6 = preferences.getBoolean("weixian_checkbox6",false);
        boolean getCheckbox7 = preferences.getBoolean("weixian_checkbox7",false);
        boolean getCheckbox8 = preferences.getBoolean("weixian_checkbox8",false);
        boolean getCheckbox9 = preferences.getBoolean("weixian_checkbox9",false);
        boolean getCheckbox10 = preferences.getBoolean("weixian_checkbox10",false);
        boolean getCheckbox11 = preferences.getBoolean("weixian_checkbox11",false);
        String getEdit3 = preferences.getString("weixian_edit3","");

        String getPlace = preferences.getString("weixian_place","");

        Boolean getRadioButton41 = preferences.getBoolean("weixian_button41", false);
        Boolean getRadioButton42 = preferences.getBoolean("weixian_button42", false);
        Boolean getRadioButton43 = preferences.getBoolean("weixian_button43", false);
        Boolean getRadioButton51 = preferences.getBoolean("weixian_button51", false);
        Boolean getRadioButton52 = preferences.getBoolean("weixian_button52", false);
        Boolean getRadioButton53 = preferences.getBoolean("weixian_button53", false);

        int getNationposition = preferences.getInt("weixian_nation",0);
        Boolean getRadioButton61 = preferences.getBoolean("weixian_button61", false);
        Boolean getRadioButton62 = preferences.getBoolean("weixian_button62", false);
        Boolean getRadioButton63 = preferences.getBoolean("weixian_button63", false);

        Boolean getRadioButton71 = preferences.getBoolean("weixian_button71", false);
        Boolean getRadioButton72 = preferences.getBoolean("weixian_button72", false);
        Boolean getRadioButton73 = preferences.getBoolean("weixian_button73", false);

        Boolean getRadioButton81 = preferences.getBoolean("weixian_button81", false);
        Boolean getRadioButton82 = preferences.getBoolean("weixian_button82", false);
        Boolean getRadioButton83 = preferences.getBoolean("weixian_button83", false);

        Boolean getRadioButton91 = preferences.getBoolean("weixian_button91", false);
        Boolean getRadioButton92 = preferences.getBoolean("weixian_button92", false);
        Boolean getRadioButton93 = preferences.getBoolean("weixian_button93", false);


        if (getRadioButton1==true){
            radioButton1.setChecked(true);
            text2.setVisibility(View.GONE);
            radioGroup2.setVisibility(View.GONE);
        }
        else if (getRadioButton2 == true){
            radioButton2.setChecked(true);
            text2.setVisibility(View.VISIBLE);
            radioGroup2.setVisibility(View.VISIBLE);
        }
        else if (getRadioButton2 == true) {
            radioButton2.setChecked(true);
            text2.setVisibility(View.GONE);
            radioGroup2.setVisibility(View.GONE);
        }
        else if (getRadioButton3 == true) {
            radioButton3.setChecked(true);
            text2.setVisibility(View.GONE);
            radioGroup2.setVisibility(View.GONE);
        }
        else if (getRadioButton4 == true) {
            radioButton4.setChecked(true);
            text2.setVisibility(View.GONE);
            radioGroup2.setVisibility(View.GONE);
        }
        else if (getRadioButton5 == true) {
            radioButton5.setChecked(true);
            text2.setVisibility(View.GONE);
            radioGroup2.setVisibility(View.GONE);
        }
        else if (getRadioButton6 == true) {
            radioButton6.setChecked(true);
            text2.setVisibility(View.GONE);
            radioGroup2.setVisibility(View.GONE);
        }
        else if (getRadioButton7 == true) {
            radioButton7.setChecked(true);
            text2.setVisibility(View.GONE);
            radioGroup2.setVisibility(View.GONE);
        }else if (getRadioButton8 == true) {
            radioButton8.setChecked(true);
            text2.setVisibility(View.GONE);
            radioGroup2.setVisibility(View.GONE);
        }else {
            radioButton9.setChecked(true);
            text2.setVisibility(View.GONE);
            radioGroup2.setVisibility(View.GONE);
        }

        if (getRadioButton10 == true){
            radioButton10.setChecked(true);
        }else if (getRadioButton11 == true){
            radioButton11.setChecked(true);
        } else if (getRadioButton12 == true) {
            radioButton12.setChecked(true);
        }else {
            radioButton13.setChecked(true);
        }

        if (getRadioButton21 == true){
            radioButton21.setChecked(true);
            visible2.setVisibility(View.VISIBLE);
        }else {
            radioButton22.setChecked(true);
            visible2.setVisibility(View.GONE);
        }

        edit1.setText(getEdit1);
        edit2.setText(getYunzhou);
        
        if (getJingchangshou == true){
            jingchangchou.setChecked(true);
        }else if (getOuerchou == true){
            ouerchou.setChecked(true);
        }else {
            neverchou.setChecked(true);
        }

        checkBox1.setChecked(getCheckbox1);
        checkBox2.setChecked(getCheckbox2);
        checkBox3.setChecked(getCheckbox3);
        checkBox4.setChecked(getCheckbox4);
        checkBox5.setChecked(getCheckbox5);
        checkBox6.setChecked(getCheckbox6);
        checkBox7.setChecked(getCheckbox7);
        checkBox8.setChecked(getCheckbox8);
        checkBox9.setChecked(getCheckbox9);
        checkBox10.setChecked(getCheckbox10);
        checkBox11.setChecked(getCheckbox11);
        edit3.setText(getEdit3);

        selectPlace1.setText(getPlace);

        if (getRadioButton41==true){
            radioButton41.setChecked(true);
            selectPlace.setVisibility(View.GONE);
        }else if (getRadioButton42==true){
            radioButton42.setChecked(true);
            selectPlace.setVisibility(View.VISIBLE);
        }else {
            radioButton43.setChecked(true);
            selectPlace.setVisibility(View.VISIBLE);
        }

        if (getRadioButton51==true){
            radioButton51.setChecked(true);
            selectPlace_spi.setVisibility(View.GONE);
        }else if (getRadioButton52==true){
            radioButton52.setChecked(true);
            selectPlace_spi.setVisibility(View.VISIBLE);
        }else {
            radioButton53.setChecked(true);
            selectPlace_spi.setVisibility(View.VISIBLE);
        }

        if (getRadioButton61==true){
            radioButton61.setChecked(true);
        }else if (getRadioButton62==true){
            radioButton62.setChecked(true);
        }else {
            radioButton63.setChecked(true);
        }

        if (getRadioButton61==true){
            radioButton71.setChecked(true);
        }else if (getRadioButton62==true){
            radioButton72.setChecked(true);
        }else {
            radioButton73.setChecked(true);
        }

        if (getRadioButton61==true){
            radioButton81.setChecked(true);
        }else if (getRadioButton62==true){
            radioButton82.setChecked(true);
        }else {
            radioButton83.setChecked(true);
        }

        if (getRadioButton61==true){
            radioButton91.setChecked(true);
        }else if (getRadioButton62==true){
            radioButton92.setChecked(true);
        }else {
            radioButton93.setChecked(true);
        }

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
                    selectPlace_spi.setVisibility(View.GONE);
                }else {
                    selectPlace_spi.setVisibility(View.VISIBLE);
                }
            }
        });

        arr_adapter_nation = new ArrayAdapter<String>(Weixian.this, R.layout.simple_spinner_item,nationArray);
        //设置样式
        arr_adapter_nation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        selectPlace_spi.setAdapter(arr_adapter_nation);

        selectPlace_spi.setSelection(getNationposition);
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

    }
    private void showPickerView() {    // 弹出选择器（省市区三级联动）
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                selectPlace1.setText(options1Items.get(options1).getPickerViewText() + "  "
                        + options2Items.get(options1).get(options2) + "  "
                        + options3Items.get(options1).get(options2).get(options3));

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
