package com.example.xc_nonapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//信息登记
public class RegistrationInformationActivity extends AppCompatActivity {

    private TextView mTUser,mTAge,mTHeight,mTWeight;
    private Button mBtnPpevious;
    private CheckBox mCbMale, mCbFemale;
    private SeekBar mSkBar,mSkBar1,mSkBar2;
    private SharedPreferences mSharedPreferences = null;//声明一个SharedPreferences
    private SharedPreferences.Editor editor=null;
    private String FILE = "saveUserNamePwd";//用于保存SharedPreferences的文件
    static String age, height,weight;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_information);
        mSharedPreferences = getSharedPreferences(FILE, MODE_PRIVATE);
        editor = mSharedPreferences.edit();


        //记录信息界面用户图标大小
        mTUser = findViewById(R.id.tv_user);
        Drawable drawable = getResources().getDrawable(R.drawable.user1);
        drawable.setBounds(0, 0, 60, 60);//第一个 0 是距左边距离，第二个 0 是距上边距离，40 分别是长宽
        mTUser.setCompoundDrawables(drawable, null, null, null);//只放左边

        //设置拖拽条
        mSkBar = findViewById(R.id.skBar);
        mTAge=findViewById(R.id.tv_age);
        /* mSkBar 监听setOnSeekBarChangeListener */
        mSkBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                mTAge.setText("年龄 " + progress + "岁");
                age= String.valueOf(((int) progress));
                editor.putString("age", age);
                editor.commit();
            }
        });

        mSkBar1 = findViewById(R.id.skBar1);
        mTHeight=findViewById(R.id.tv_height);
        /* mSkBar1 监听setOnSeekBarChangeListener */
        mSkBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                mTHeight.setText("身高 " + progress + "cm");
                height= String.valueOf(((int) progress));
                editor.putString("height", height);
                editor.commit();
            }
        });

        mSkBar2 = findViewById(R.id.skBar2);
        mTWeight=findViewById(R.id.tv_weight);
        /* mSkBar1 监听setOnSeekBarChangeListener */
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
                mTWeight.setText("体重 " + progress + "kg");
                weight= String.valueOf(((int) progress));
                editor.putString("weight", weight);
                editor.commit();
            }
        });


        //设置用户 这个用户名可以从前面获取
        mTUser.setText("Royal");


        //选择性别
        mCbMale = findViewById(R.id.cb_male);
        mCbMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("isChecked", "onCheckedChanged: " + isChecked);
                if (isChecked) {
                    //选中为男性时 后台传值同时设置female的复选框的属性为false
                    mCbFemale.setChecked(false);
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString("sex", "male");
                    editor.commit();
                    System.out.println("当前控件处于选中状态");
                } else {
                    System.out.println("当前控件取消了选中状态");
                }
            }
        });


        mCbFemale = findViewById(R.id.cb_female);
        mCbFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("isChecked", "onCheckedChanged: " + isChecked);
                if (isChecked) {
                    //选中为女性时 后台传值同时设置female的复选框的属性为false
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString("sex", "female");
                    mCbMale.setChecked(false);
                } else {
                    System.out.println("当前控件取消了选中状态");
                }
            }
        });


        //跳转至治疗界面
        mBtnPpevious = findViewById(R.id.btn_previous);
        mBtnPpevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (v.getId()) {
                    case R.id.btn_previous:
                        intent = new Intent(RegistrationInformationActivity.this, DistributionActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
    }
}
