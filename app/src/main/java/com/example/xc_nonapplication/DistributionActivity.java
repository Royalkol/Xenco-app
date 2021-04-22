package com.example.xc_nonapplication;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.xc_nonapplication.util.BallProgress;

/**
 * 作者：Royal
 * <p>
 * 日期: 2020/12/9 10:16
 * 配气界面
 */
public class DistributionActivity extends AppCompatActivity {

    private TextView mTv1,mTv2;
    private Button mBtnPreviouse;
//    private TextView mTUser;
//    private WaveProgressView waveProgressView_0, waveProgressView_1, waveProgressView_2;


    //
    private final int PROGRESS_MESSAGE = 0;
    private float progress = 0.0f;
    private BallProgress mBallProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribution);
        mTv1=findViewById(R.id.tv_1);
        mTv2=findViewById(R.id.tv_2);


//        waveProgressView_0 = (WaveProgressView) findViewById(R.id.wpv_0);
//        waveProgressView_1 = (WaveProgressView) findViewById(R.id.wpv_1);
//        waveProgressView_2 = (WaveProgressView) findViewById(R.id.wpv_2);
//        waveProgressView_0.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            }
//        });


        //跳转治疗中界面 进行治疗
        mBtnPreviouse = findViewById(R.id.btn_previous);
        mBtnPreviouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DistributionActivity.this, TreatmentActivity.class);
                startActivity(intent);
            }
        });
        mBtnPreviouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DistributionActivity.this, TreatmentActivity.class);
                startActivity(intent);
            }
        });
    }

//    private void initView() {
//        mBallProgress = findViewById(R.id.progress);
//    }

    //发消息
//    private void initAction() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    progress += 0.02f;
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    mHandler.sendEmptyMessage(PROGRESS_MESSAGE);//每隔100毫秒发送一次消息，对进度球进度进行更新。
//                }
//            }
//        }).start();
//    }


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
//
//    public void start(View view) {
//        ObjectAnimator objectAnimator0 = ObjectAnimator.ofFloat(waveProgressView_0, "progress", 0f, 100f);
//        objectAnimator0.setDuration(3300);
//        objectAnimator0.setInterpolator(new LinearInterpolator());
//        objectAnimator0.start();
//
//        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(waveProgressView_1, "progress", 0f, 80f);
//        objectAnimator1.setDuration(3000);
//        objectAnimator1.setInterpolator(new AccelerateInterpolator());
//        objectAnimator1.start();
//
//        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(waveProgressView_2, "progress", 0f, 120f);
//        objectAnimator2.setDuration(5000);
//        objectAnimator2.setInterpolator(new BounceInterpolator());
//        objectAnimator2.start();
//    }
}
