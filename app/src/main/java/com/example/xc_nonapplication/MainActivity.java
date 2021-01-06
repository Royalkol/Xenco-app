package com.example.xc_nonapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    //登陆按钮
    private ImageView mIvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIvLogin=findViewById(R.id.iv_login);
        setListeners();
    }

    //监听器方法
    private void setListeners(){
        OnClick onClick= new OnClick();
        mIvLogin.setOnClickListener(onClick);
    };

    private class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.iv_login:
                    //跳转到登陆演示界面
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
}
