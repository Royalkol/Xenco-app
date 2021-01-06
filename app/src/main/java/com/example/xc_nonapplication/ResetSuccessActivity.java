package com.example.xc_nonapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ResetSuccessActivity extends AppCompatActivity {

    private Button mBtnRightNowLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_success);

        //立即登录按钮
        mBtnRightNowLogin=findViewById(R.id.btn_rightnowlogin);
        mBtnRightNowLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (v.getId()) {
                    case R.id.btn_rightnowlogin:
                        //返回上一个界面
                        intent = new Intent(ResetSuccessActivity.this, LoginActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
    }
}
