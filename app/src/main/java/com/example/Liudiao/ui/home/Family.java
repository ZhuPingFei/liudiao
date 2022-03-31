package com.example.Liudiao.ui.home;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Liudiao.R;
import com.example.Liudiao.ui.home.adpter.FamilyAdapter;
import com.example.Liudiao.ui.notifications.adapter.DaibanAdapter;

import java.util.ArrayList;

public class Family extends AppCompatActivity {

    private ListView listView;
    private FamilyAdapter familyAdapter;

    private TextView textView;
    private TextView textView2;
    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private LinearLayout linearLayout3;

    private ImageView r_back;
    private TextView okey;

    private EditText name1;
    private EditText name2;
    private EditText name3;
    private EditText name4;

    private Spinner guanxi1;
    private int guanxi1Position;
    private Spinner guanxi2;
    private int guanxi2Position;
    private Spinner guanxi3;
    private int guanxi3Position;
    private Spinner guanxi4;
    private int guanxi4Position;

    private EditText phone1;
    private EditText phone2;
    private EditText phone3;
    private EditText phone4;

    private ArrayList<String> guanxileixing1 = new ArrayList<String>();
    private ArrayList<String> guanxileixing2 = new ArrayList<String>();
    private ArrayList<String> guanxileixing3 = new ArrayList<String>();
    private ArrayList<String> guanxileixing4 = new ArrayList<String>();

    private ArrayAdapter<String> arr_adapter_guanxi1;
    private ArrayAdapter<String> arr_adapter_guanxi2;
    private ArrayAdapter<String> arr_adapter_guanxi3;
    private ArrayAdapter<String> arr_adapter_guanxi4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.family);

        guanxileixing1.add("父亲");
        guanxileixing1.add("母亲");
        guanxileixing1.add("兄弟");
        guanxileixing1.add("姐妹");
        guanxileixing2.add("母亲");
        guanxileixing2.add("父亲");
        guanxileixing2.add("兄弟");
        guanxileixing2.add("姐妹");
        guanxileixing3.add("兄弟");
        guanxileixing3.add("父亲");
        guanxileixing3.add("母亲");
        guanxileixing3.add("姐妹");
        guanxileixing4.add("姐妹");
        guanxileixing4.add("父亲");
        guanxileixing4.add("母亲");
        guanxileixing4.add("兄弟");

        name1 = (EditText) findViewById(R.id.chengyuan1_name);
        name2 = (EditText) findViewById(R.id.chengyuan2_name);
        name3 = (EditText) findViewById(R.id.chengyuan3_name);
        name4 = (EditText) findViewById(R.id.chengyuan4_name);

        phone1 = (EditText) findViewById(R.id.chengyuan1_phone);
        phone2 = (EditText) findViewById(R.id.chengyuan2_phone);
        phone3 = (EditText) findViewById(R.id.chengyuan3_phone);
        phone4 = (EditText) findViewById(R.id.chengyuan4_phone);

        guanxi1 = (Spinner) findViewById(R.id.chengyuan1_guanxi);
        guanxi2 = (Spinner) findViewById(R.id.chengyuan2_guanxi);
        guanxi3 = (Spinner) findViewById(R.id.chengyuan3_guanxi);
        guanxi4 = (Spinner) findViewById(R.id.chengyuan4_guanxi);


        SharedPreferences preferences = getSharedPreferences("user_family", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();//获取编辑器
        String getname1 = preferences.getString("family_name1","");
        int getguanxi1 = preferences.getInt("family_guanxi1", 0);
        String getphone1 = preferences.getString("family_phone1","");
        String getname2 = preferences.getString("family_name2","");
        int getguanxi2 = preferences.getInt("family_guanxi2", 0);
        String getphone2 = preferences.getString("family_phone2","");
        String getname3 = preferences.getString("family_name3","");
        int getguanxi3 = preferences.getInt("family_guanxi3", 0);
        String getphone3 = preferences.getString("family_phone3","");
        String getname4 = preferences.getString("family_name4","");
        int getguanxi4 = preferences.getInt("family_guanxi4", 0);
        String getphone4 = preferences.getString("family_phone4","");
        //textView = (TextView) findViewById(R.id.zhengjiaguanxi1);

        name1.setText(getname1);
        name2.setText(getname2);
        name3.setText(getname3);
        name4.setText(getname4);
        phone1.setText(getphone1);
        phone2.setText(getphone2);
        phone3.setText(getphone3);
        phone4.setText(getphone4);

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
                dataCommit();
                onBackPressed();
            }
        });

        arr_adapter_guanxi1 = new ArrayAdapter<String>(Family.this,R.layout.simple_spinner_item,guanxileixing1);
        arr_adapter_guanxi2 = new ArrayAdapter<String>(Family.this,R.layout.simple_spinner_item,guanxileixing2);
        arr_adapter_guanxi3 = new ArrayAdapter<String>(Family.this,R.layout.simple_spinner_item,guanxileixing3);
        arr_adapter_guanxi4 = new ArrayAdapter<String>(Family.this,R.layout.simple_spinner_item,guanxileixing4);
        arr_adapter_guanxi1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        arr_adapter_guanxi2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        arr_adapter_guanxi3.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        arr_adapter_guanxi4.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        guanxi1.setAdapter(arr_adapter_guanxi1);
        guanxi2.setAdapter(arr_adapter_guanxi2);
        guanxi3.setAdapter(arr_adapter_guanxi3);
        guanxi4.setAdapter(arr_adapter_guanxi4);


        guanxi1.setSelection(getguanxi1);
        guanxi2.setSelection(getguanxi2);
        guanxi3.setSelection(getguanxi3);
        guanxi4.setSelection(getguanxi4);

        guanxi1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                guanxi1Position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        guanxi2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                guanxi2Position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        guanxi3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                guanxi3Position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        guanxi4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                guanxi4Position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//                textView.setVisibility(View.GONE);
//                linearLayout1.setVisibility(View.VISIBLE);
//                linearLayout2.setVisibility(View.VISIBLE);
//                linearLayout3.setVisibility(View.VISIBLE);
//                textView2.setVisibility(View.VISIBLE);
//            }
//        });
    }

    public void dataCommit(){
        SharedPreferences preferences = getSharedPreferences("user_family", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("family_name1",name1.getText().toString());
        editor.putInt("family_guanxi1",guanxi1Position);
        editor.putString("family_phone1",phone1.getText().toString());
        editor.putString("family_name2",name2.getText().toString());
        editor.putInt("family_guanxi2",guanxi2Position);
        editor.putString("family_phone2",phone2.getText().toString());
        editor.putString("family_name3",name3.getText().toString());
        editor.putInt("family_guanxi3",guanxi3Position);
        editor.putString("family_phone3",phone3.getText().toString());
        editor.putString("family_name4",name4.getText().toString());
        editor.putInt("family_guanxi4",guanxi4Position);
        editor.putString("family_phone4",phone4.getText().toString());
        editor.commit();
    }
}
