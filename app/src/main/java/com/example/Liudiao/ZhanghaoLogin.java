package com.example.Liudiao;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ZhanghaoLogin extends AppCompatActivity {

    private TextView login1;
    private Button login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login2);

        login1 = (TextView) findViewById(R.id.login1_btn);
        login = (Button) findViewById(R.id.login1_btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ZhanghaoLogin.this,Main.class);
                startActivity(intent);
                finish();
            }
        });
        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ZhanghaoLogin.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
