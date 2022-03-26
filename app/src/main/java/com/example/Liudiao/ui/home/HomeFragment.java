package com.example.Liudiao.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.Liudiao.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private LinearLayout gongneng1;
    private LinearLayout gongneng2;
    private LinearLayout gongneng3;
    private LinearLayout gongneng4;
    private LinearLayout gongneng5;
    private LinearLayout gongneng6;
    private LinearLayout gongneng7;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        gongneng1 = (LinearLayout) root.findViewById(R.id.gongneng1) ;
        gongneng2 = (LinearLayout) root.findViewById(R.id.gongneng2);
        gongneng4 = (LinearLayout) root.findViewById(R.id.gongneng4);
        gongneng3 = (LinearLayout) root.findViewById(R.id.gongneng3);
        gongneng5 = (LinearLayout) root.findViewById(R.id.gongneng5);
        gongneng6 = (LinearLayout) root.findViewById(R.id.gongneng6);
        gongneng7 = (LinearLayout) root.findViewById(R.id.gongneng7);

        gongneng1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Jibenzhuangkuang.class);
                startActivity(intent);
            }
        });
        gongneng2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Family.class);
                startActivity(intent);
            }
        });

        gongneng3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Jiankang.class);
                startActivity(intent);
            }
        });



        gongneng4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Jingwai.class);
                startActivity(intent);
            }
        });

        gongneng5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Jezhong.class);
                startActivity(intent);
            }
        });
        gongneng6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Bingli.class);
                startActivity(intent);
            }
        });
        gongneng7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Weixian.class);
                startActivity(intent);
            }
        });
        return root;
    }
}