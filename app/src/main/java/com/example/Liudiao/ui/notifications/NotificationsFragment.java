package com.example.Liudiao.ui.notifications;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.Liudiao.R;
import com.example.Liudiao.login.Capture;


import static android.content.Context.MODE_PRIVATE;

public class NotificationsFragment extends Fragment {
    private NotificationsViewModel notificationsViewModel;
    private RelativeLayout erweima_relativeLayout;
    private RelativeLayout daiban_relativeLayout;
    private Button user_exit;
    private TextView textView;

    private Button saoyisao;


    SharedPreferences sharedPreferences;

    //Button button2 = (Button) getActivity().findViewById(R.id.exit);

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_notifications,container,false);

        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器

        String user_id = sharedPreferences.getString("user_phone","");

        textView = (TextView) view.findViewById(R.id.user_id);
        daiban_relativeLayout = (RelativeLayout) view.findViewById(R.id.user_daiban);
        erweima_relativeLayout = (RelativeLayout) view.findViewById(R.id.user_erweima);
        user_exit = (Button) view.findViewById(R.id.user_exit);
        saoyisao = (Button) view.findViewById(R.id.user_saoyisao);

        textView.setText(user_id);

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
                startActivity(intent);
            }
        });
        user_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

        saoyisao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(getActivity(), Capture.class);
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
}