package com.example.Liudiao.ui.xingcheng;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.Liudiao.R;
import com.example.Liudiao.ui.home.Jibenzhuangkuang;
import com.example.Liudiao.ui.huodong.Huodong;

public class XingchengFragment extends Fragment {

    private AlertDialog.Builder builder;
    private ProgressDialog progressDialog;

    private SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private int current_transid;

    private XingchengViewModel xingchengViewModel;

    private LinearLayout gongneng1;
    private LinearLayout gongneng2;
    private LinearLayout gongneng5;
    private LinearLayout gongneng3;
    private LinearLayout gongneng4;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        xingchengViewModel = ViewModelProviders.of(this).get(XingchengViewModel.class);
        View view = inflater.inflate(R.layout.fragment_xingcheng, container, false);

        preferences = getActivity().getSharedPreferences("daiban", Context.MODE_PRIVATE);
        editor = preferences.edit();//获取编辑器
        current_transid = preferences.getInt("current_banliId",0);

        gongneng1 = (LinearLayout) view.findViewById(R.id.xingcheng_gongneng1);
        gongneng2 = (LinearLayout) view.findViewById(R.id.xingcheng_gongneng2);
        gongneng5 = (LinearLayout) view.findViewById(R.id.xingcheng_gongneng5);
        gongneng3 = (LinearLayout) view.findViewById(R.id.xingcheng_gongneng3);
        gongneng4 = (LinearLayout) view.findViewById(R.id.xingcheng_gongneng4);

        gongneng1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current_transid!=100000&& current_transid!=0){
                    Intent intent = new Intent(getActivity(), Xingcheng.class);
                    startActivity(intent);
                }else {
                    builder = new AlertDialog.Builder(getActivity()).setTitle("提醒")
                            .setMessage("当前未选择办理人，不能填写此项内容。请在个人主页进行流调办理并选择办理人之后再填写。").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //ToDo: 你想做的事情
                                    dialogInterface.dismiss();
                                }
                            });
                    builder.create().show();
                }
            }
        });

        gongneng2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current_transid!=100000&& current_transid!=0){
                    Intent intent = new Intent(getActivity(), Huodong.class);
                    startActivity(intent);
                }else {
                    builder = new AlertDialog.Builder(getActivity()).setTitle("提醒")
                            .setMessage("当前未选择办理人，不能填写此项内容。请在个人主页进行流调办理并选择办理人之后再填写。").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //ToDo: 你想做的事情
                                    dialogInterface.dismiss();
                                }
                            });
                    builder.create().show();
                }
            }
        });

        gongneng5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current_transid!=100000&& current_transid!=0){
                    Intent intent = new Intent(getActivity(), XingchengHistory.class);
                    startActivity(intent);
                }else {
                    builder = new AlertDialog.Builder(getActivity()).setTitle("提醒")
                            .setMessage("当前未选择办理人，不能填写此项内容。请在个人主页进行流调办理并选择办理人之后再填写。").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //ToDo: 你想做的事情
                                    dialogInterface.dismiss();
                                }
                            });
                    builder.create().show();
                }
            }
        });
        gongneng3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"该功能暂未开放",Toast.LENGTH_SHORT).show();
            }
        });
        gongneng4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"该功能暂未开放",Toast.LENGTH_SHORT).show();
            }
        });


        return view;

    }
}
