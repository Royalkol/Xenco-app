package com.example.xc_nonapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.xc_nonapplication.Vo.LoginInfoVo;
import com.example.xc_nonapplication.Vo.TreatInfoVo;
import com.example.xc_nonapplication.util.EsbUtil;
import com.example.xc_nonapplication.util.ToastUtil;

//治疗完成
public class CompleteTreatmentActivity extends AppCompatActivity {

    private Button mBtnComplete;
    private TextView mTUser;
    private SharedPreferences mSharedPreferences = null;//声明一个SharedPreferences
    static String sex,age,height,weight;
    private String FILE = "saveUserNamePwd";//用于保存SharedPreferences的文件



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_treatment);


        //记录信息界面用户图标大小
        mTUser = findViewById(R.id.tv_user);
        Drawable drawable = getResources().getDrawable(R.drawable.user1);
        drawable.setBounds(0, 0, 60, 60);//第一个 0 是距左边距离，第二个 0 是距上边距离，40 分别是长宽
        mTUser.setCompoundDrawables(drawable, null, null, null);//只放左边


        //设置用户 这个用户名可以从前面获取
        mTUser.setText("Royal");



        //跳转信息登记界面 开始下一个患者的治疗过程
        mBtnComplete = findViewById(R.id.btn_complete);
        mBtnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("HandlerLeak") Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        switch (msg.what) {
                            case 0:
                                ToastUtil.showMsgTop(CompleteTreatmentActivity.this, "服务器连接失败");
                                break;
                            case 1:
//                      ToastUtil.showMsgTop(LoginActivity.this, "登录成功");
                                startActivity(new Intent(CompleteTreatmentActivity.this, RegistrationInformationActivity.class));
                                CompleteTreatmentActivity.this.finish();
                                break;
//                    case 2:
//                        ToastUtil.showMsgTop(CompleteTreatmentActivity.this, "登录失败,培训证号或密码输入有误");
//                        break;
                            case 3:
                                Log.e("input error", "url为空");
                                break;
                            case 4:
                                ToastUtil.showMsgTop(CompleteTreatmentActivity.this, "连接超时");
                                break;
                            default:
                        }
                    }
                };
                //获取前面保存的数据
                mSharedPreferences = getSharedPreferences(FILE, MODE_PRIVATE);
                sex= mSharedPreferences.getString("sex", "");
                age= mSharedPreferences.getString("age", "");
                height= mSharedPreferences.getString("height", "");
                weight= mSharedPreferences.getString("weight", "");
                System.out.println("sex"+sex);
                System.out.println("age"+age);
                System.out.println("height"+height);
                System.out.println("weight"+weight);


                TreatInfoVo treatInfoVo = new TreatInfoVo();
                treatInfoVo.setAge("28");
                treatInfoVo.setEuipmentserialnumber("XC001");
                treatInfoVo.setHeight("165");
                treatInfoVo.setName("royal");
                treatInfoVo.setOperater("jack");
                treatInfoVo.setPhonenumber("15067333278");
                treatInfoVo.setSex("man");
                treatInfoVo.setTreatdate("2020-12-15");
                treatInfoVo.setTreattime("3minute");
                treatInfoVo.setWeight("60kg");
                treatInfoVo.setAllowancegas("5l");
                //=======================发送请求到服务器====================//
                EsbUtil esbUtil = new EsbUtil();
                esbUtil.treatService(treatInfoVo, handler);
            }

//                Intent intent = null;
//                switch (v.getId()) {
//                    case R.id.btn_complete:
//                        intent = new Intent(CompleteTreatmentActivity.this, RegistrationInformationActivity.class);
//                        break;
//                }
//                startActivity(intent);

        });

    }
}
