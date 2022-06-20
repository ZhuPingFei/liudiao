package com.example.Liudiao.ui.notifications;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.Liudiao.R;
import com.example.Liudiao.login.ZhanghaoLogin;
import com.example.Liudiao.zxing.android.CaptureActivity;

public class NotificationsFragment extends Fragment {

    private SharedPreferences preferences;

    private Context context;
    private NotificationsViewModel notificationsViewModel;
    private RelativeLayout erweima_relativeLayout;
    private RelativeLayout daiban_relativeLayout;
    private RelativeLayout xiugai_relativeLayout;
    private RelativeLayout phone_relativeLayout;
    private Button user_exit;
    private TextView textView2;
    private TextView textView;

    private Button saoyisao;

    //当前用户
    private String user_name;
    private String user_phone;
    //当前办理人
    private String current_banliName;
    private int current_banliId;
    private int transactor_num;
    private int authority;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            Bundle bd = msg.getData();
//
//            int code = bd.getInt("code",0);
//            transactor_num = bd.getInt("transactor_num",0);
//
//
//            if (code==0 && transactor_num!=0){
//                int trans_id = bd.getInt("transactor_id",0);
//                String name = bd.getString("name","");
//                current_banliId = trans_id;
//                current_banliName = name;
//
//            }else {
//                current_banliId = 100000;
//                current_banliName = "未绑定";
//            }
//            textView.setText("当前办理人:  "+current_banliName);
//        }
//    };

    //Button button2 = (Button) getActivity().findViewById(R.id.exit);

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_notifications,container,false);



        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();//获取编辑器
        user_name = sharedPreferences.getString("user_name","未设置用户名");
        user_phone = sharedPreferences.getString("user_phone","未绑定手机号");

        int user_id = sharedPreferences.getInt("user_id",0);
        boolean isFirstLogin = sharedPreferences.getBoolean("is_first_login",false);
        int authority = sharedPreferences.getInt("authority",0);

//        String url = "http://175.23.169.100:9030/transactor/getTransactor";
//        RequestUserThread rdt = new RequestUserThread(url,user_id,handler);
//        rdt.start();
        preferences = getActivity().getSharedPreferences("daiban", Activity.MODE_PRIVATE);
        current_banliName = preferences.getString("current_banliName", "");
        textView = (TextView) view.findViewById(R.id.Userphone);
        textView2 = (TextView) view.findViewById(R.id.Username);
        daiban_relativeLayout = (RelativeLayout) view.findViewById(R.id.user_daiban);
        erweima_relativeLayout = (RelativeLayout) view.findViewById(R.id.user_erweima);
        xiugai_relativeLayout = (RelativeLayout) view.findViewById(R.id.user_xiugai);
        phone_relativeLayout = (RelativeLayout) view.findViewById(R.id.user_phone);
        user_exit = (Button) view.findViewById(R.id.user_exit);
        saoyisao = (Button) view.findViewById(R.id.user_saoyisao);
        if (authority == 0){
            textView.setText("自查人员");
            if (isFirstLogin == true){
                textView2.setText(" 当前办理人 未绑定");
                editor.putBoolean("is_first_login",false);
                editor.commit();
            }else {
                textView2.setText(" 当前办理人: "+current_banliName);
            }

        }else {
            if (isFirstLogin == true){
                textView2.setText(" 当前办理人 未绑定");
                editor.putBoolean("is_first_login",false);
                editor.commit();
            }else {
                textView2.setText(" 当前办理人: "+current_banliName);
            }
            textView.setText("流调员");
        }



        erweima_relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Erweima.class);
                startActivity(intent);
            }
        });
        daiban_relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Daiban.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //getActivity().finish();
            }
        });
        xiugai_relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Motification.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //getActivity().finish();
            }
        });

        phone_relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"此功能暂未开放",Toast.LENGTH_SHORT).show();

//                Intent intent = new Intent(getActivity(),Bindphone.class);
//                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                //getActivity().finish();
            }
        });
        user_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.exit(0);
                editor.putBoolean("is_login",false);
                editor.commit();
                Intent intent = new Intent(getActivity(), ZhanghaoLogin.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
            }
        });

        saoyisao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(getActivity(), CaptureActivity.class);
                    startActivityForResult(intent,1111);
                }else {
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},100);
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
//        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
//        user_name = sharedPreferences.getString("user_name","未设置用户名");
//        user_phone = sharedPreferences.getString("user_phone","未绑定手机号");
//        authority = sharedPreferences.getInt("authority",1);
//        if (authority == 1){
//            textView2.setText("自查人员");
//        }else{
//            textView2.setText("流调员");
//            textView.setText("");
//        }

    }
}