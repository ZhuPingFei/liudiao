package com.example.Liudiao.ui.xingcheng;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.Liudiao.R;
import com.example.Liudiao.ui.huodong.Huodong;

public class XingchengFragment extends Fragment {

    private XingchengViewModel xingchengViewModel;

    private LinearLayout gongneng1;
    private LinearLayout gongneng2;
    private LinearLayout gongneng5;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        xingchengViewModel = ViewModelProviders.of(this).get(XingchengViewModel.class);
        View view = inflater.inflate(R.layout.fragment_xingcheng, container, false);

        gongneng1 = (LinearLayout) view.findViewById(R.id.xingcheng_gongneng1);
        gongneng2 = (LinearLayout) view.findViewById(R.id.xingcheng_gongneng2);
        gongneng5 = (LinearLayout) view.findViewById(R.id.xingcheng_gongneng5);

        gongneng1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Xingcheng.class);
                startActivity(intent);
            }
        });

        gongneng2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Huodong.class);
                startActivity(intent);
            }
        });

        gongneng5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), XingchengHistory.class);
                startActivity(intent);
            }
        });


        return view;

    }
}
