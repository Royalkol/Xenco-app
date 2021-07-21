package com.example.xc_nonapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.xc_nonapplication.Vo.DistributionInfoVo;
import com.example.xc_nonapplication.Vo.LoginInfoVo;
import com.example.xc_nonapplication.response.Response;
import com.example.xc_nonapplication.response.ResponseDistribution;
import com.example.xc_nonapplication.util.EsbUtil;

/**
 * 作者：Royal
 * * <p>
 * * 日期: 2020/12/9 10:41
 * * 信息录入界面
 */
public class RegistrationInformationActivity extends AppCompatActivity {

    private TextView mTvAge, mTvHeight;
    private Button mBtnPpevious;
    private CheckBox mCbMale, mCbFemale;
    private SeekBar mSkBar1, mSkBar2;
    private String sex;
    private int age, height, oxygenTime, xenonTime;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_information);
        mTvAge = findViewById(R.id.tv_age);
        mTvHeight = findViewById(R.id.tv_height);
        mBtnPpevious = findViewById(R.id.btn_previous);
        mCbMale = findViewById(R.id.cb_male);
        mCbFemale = findViewById(R.id.cb_female);
        mSkBar1 = findViewById(R.id.sb_age);
        mSkBar2 = findViewById(R.id.sb_height);
        //跳转至配气界面
        mBtnPpevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    //个人信息接口获取配气时间
                    @SuppressLint("HandlerLeak") Handler handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            switch (msg.what) {
                                case 0:
                                    //网络连接存在问题 弹出一个提示窗口
                                    Log.d("网络连接故障", "---请检查---");
                                    final CustomDialog customDialog = new CustomDialog(RegistrationInformationActivity.this);
                                    customDialog.setDrawable(R.mipmap.connection_error);
                                    customDialog.setConfirm("确认", new CustomDialog.IonConfirmListener() {
                                        @Override
                                        public void onConfirm(CustomDialog dialog) {
                                            //点击确认后 alert对话框消失
                                            customDialog.dismiss();
                                        }
                                    });
                                    customDialog.show();
                                    break;
                                case 1:
                                    Log.d("获取配气时间成功", "---请继续一下操作---");
                                    //传递数据至配气界面
                                    Intent intent = new Intent(RegistrationInformationActivity.this, DistributionActivity.class);
                                    Bundle bundle =new Bundle();
                                    bundle.putInt("xenonTime",xenonTime);
                                    bundle.putInt("oxygenTime",oxygenTime);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    break;
                                default:
                            }
                        }
                    };
                    DistributionInfoVo distributionInfoVo = new DistributionInfoVo();
                    distributionInfoVo.setSex(sex);
                    distributionInfoVo.setHeight(height);
                    distributionInfoVo.setAge(age);
                    System.out.println(distributionInfoVo);
                    //=======================发送请求到服务器====================//
                    EsbUtil esbUtil = new EsbUtil();
                    ResponseDistribution responseDistribution = esbUtil.DistributionService(distributionInfoVo, handler);
                    System.out.println(responseDistribution);

                    //获取配气时间
                    xenonTime = responseDistribution.getXenonTime();
                    oxygenTime = responseDistribution.getOxygenTime();
                } catch (Exception e) {
                    e.printStackTrace();
                }


//                //先弹出一个alert dialog提示打开气瓶阀门
//                final CustomDialog customDialog = new CustomDialog(RegistrationInformationActivity.this);
//                customDialog.setDrawable(R.mipmap.tip_bulb);
//                customDialog.setText1("温馨提示");
//                customDialog.setText2("准备为您配气，请确保气瓶阀门已打开！");
//                customDialog.setConfirm("确认", new CustomDialog.IonConfirmListener() {
//                    @Override
//                    public void onConfirm(CustomDialog dialog) {
//                        //点击确认后 alert对话框消失
//                        customDialog.dismiss();
//                        Intent intent = new Intent(RegistrationInformationActivity.this, DistributionActivity.class);
//                        startActivity(intent);
//                    }
//                });
//                customDialog.show();
            }
        });

        //选择性别
        mCbFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //选中为女性时 后台传值同时设置male的复选框的属性为false
                    mCbMale.setChecked(false);
                    sex = "female";
                    Log.d("---mCbMale---", "被选中");
                } else {
                    Log.d("---mCbMale---", "取消选中");
                }
            }
        });

        //选择性别
        mCbMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //选中为男性时 后台传值同时设置female的复选框的属性为false
                    mCbFemale.setChecked(false);
                    sex = "male";
                    Log.d("---mCbMale---", "被选中");
                } else {
                    Log.d("---mCbMale---", "取消选中");
                }
            }
        });
//        mSharedPreferences = getSharedPreferences(FILE, MODE_PRIVATE);
//        editor = mSharedPreferences.edit();


        if (mSkBar1 != null) {
            mSkBar1.setMax(100);
            mSkBar1.setMin(10);
        }
        mSkBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /*拖动条停止拖动时调用 */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.i("---SeekBar1---", "拖动停止");
            }

            /*拖动条开始拖动时调用*/
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.i("---SeekBar1---", "开始拖动");
            }

            /* 拖动条进度改变时调用*/
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTvAge.setText("左滑选年龄: " + progress + "岁");
                age = progress;
            }
        });

        if (mSkBar2 != null) {
            mSkBar2.setMax(190);
            mSkBar2.setMin(100);
        }
        mSkBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /*拖动条停止拖动时调用 */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.i("SeekBarActivity", "拖动停止");
            }

            /*拖动条开始拖动时调用*/
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.i("SeekBarActivity", "开始拖动");
            }

            /* 拖动条进度改变时调用*/
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTvHeight.setText("左滑选身高: " + progress + "cm");
                height = progress;
            }
        });
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
