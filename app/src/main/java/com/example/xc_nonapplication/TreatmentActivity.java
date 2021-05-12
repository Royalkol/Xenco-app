package com.example.xc_nonapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 作者：Royal
 * * <p>
 * * 日期: 2020/12/9 10:41
 * * 治疗界面
 */
public class TreatmentActivity extends AppCompatActivity {

    private Button mBtnComplete;
    private CountDownView countDownView;
    private TextView mTUser;
    private RoundProgressBar mRb1, mRb2;



    private int progress =66;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);

        //跳转至治疗结束信息展示界面
        mBtnComplete = findViewById(R.id.btn_complete);
        countDownView = findViewById(R.id.cdv_1);
        mRb1 = findViewById(R.id.rb_1);
        mRb2 = findViewById(R.id.rb_2);
        mRb1.setProgress(progress);
        mRb2.setProgress(progress);
        mBtnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (v.getId()) {
                    case R.id.btn_complete:
                        intent = new Intent(TreatmentActivity.this, CompleteTreatmentActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
        countDownView.setCountDown(0, 03, 50);
        countDownView.setTextSize(30);
        countDownView.setOnTimeChangeListener(new CountDownView.OnTimeChangeListener() {
            @Override
            public String onSecChange(long sec) {
                String mysec = sec+"" ;
                return mysec;
            }

            @Override
            public String onMinChange(long min) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String onHourChange(long hour) {
                // TODO Auto-generated method stub
                return null;
            }
        });
        countDownView.start();
    }

    /**
     * 沉浸式模式（Android 4.4及其以上）
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
