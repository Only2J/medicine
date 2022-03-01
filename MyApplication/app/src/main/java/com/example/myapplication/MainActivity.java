package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edit_username, edit_password;
    Button bt_login;
    TextView tv_retrieve, tv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "d4b9546485e6d4e49cac882ed92a1489");
        edit_username = findViewById(R.id.edituser);
        edit_password = findViewById(R.id.editpass);
        tv_retrieve = findViewById(R.id.textView2);
        tv_retrieve.setOnClickListener(this);
        tv_register = findViewById(R.id.textView);
        tv_register.setOnClickListener(this);
        bt_login = findViewById(R.id.login);
        bt_login.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView://注册
                Intent intent_register = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent_register);
                finish();
                break;
            case R.id.login://登录
                login();
                break;
            case R.id.textView2://找回密码

        }

    }

    private void login() {
        User user = new User();
        String username = edit_username.getText().toString().trim();
        String password = edit_password.getText().toString().trim();
        user.setUsername(username);
        user.setPassword(password);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                Log.i("MainActivity", String.valueOf(user));
                if (e == null) {
                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                } else if (edit_username.getText().toString().trim().isEmpty() || edit_password.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "用户名或密码没有输入", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "用户名或密码输入有误", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}