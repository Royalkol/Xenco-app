package com.example.xc_nonapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.xc_nonapplication.Vo.MessageInfoVo;
import com.example.xc_nonapplication.Vo.PhoneInfoVo;
import com.example.xc_nonapplication.util.EsbUtil;
import com.example.xc_nonapplication.util.OtherUtil;
import com.example.xc_nonapplication.util.ToastUtil;

public class RetrievePasswordActivity extends AppCompatActivity {
    private TextView mTv1;
    private Button mBtnPpevious, mBtnNext, mBtnGetVfCode;
    private EditText mEtPhoneNumber, mEtVfCode;
    private String [] verifyCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_password);
        mTv1= findViewById(R.id.tv_1);
        mBtnPpevious = findViewById(R.id.btn_previous);
        mBtnNext = findViewById(R.id.btn_next);

        //设置字体
        Typeface typeFace =Typeface.createFromAsset(getAssets(),"fonts/SourceHanSansCN-Medium.otf");
        mTv1.setTypeface(typeFace);
//        mBtnGetVfCode = findViewById(R.id.btn_vfcode);
        OnClick onClick = new OnClick();
        mBtnPpevious.setOnClickListener(onClick);
        mBtnNext.setOnClickListener(onClick);
//        mBtnGetVfCode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String phoneNumber = mEtPhoneNumber.getText().toString().trim();
//                //校验手机号是否输入
//                if (phoneNumber == null || "".equals(phoneNumber)) {
//                    ToastUtil.showMsgTop(RetrievePasswordActivity.this, "请先输入手机号码");
//                } else if (!OtherUtil.CheckMobilePhoneNum(mEtPhoneNumber.getText().toString().trim())) {
//                    ToastUtil.showMsgTop(RetrievePasswordActivity.this, "手机号格式不正确");
//                } else {
//                    //调用接口 查询是否绑定培训证号
//
//                    //调用后端的三方接口 并收验证码
//                    @SuppressLint("HandlerLeak") Handler handler = new Handler() {
//                        @Override
//                        public void handleMessage(Message msg) {
//                            super.handleMessage(msg);
//                            switch (msg.what) {
//                                case 0:
//                                    ToastUtil.showMsgTop(RetrievePasswordActivity.this, "服务器连接失败");
//                                    break;
//                                case 1:
//                                    //通过检验 跳转到设置新密码界面
//                                    ToastUtil.showMsgTop(RetrievePasswordActivity.this, "请查看收到的验证码");
//                                    break;
//                                case 3:
//                                    Log.e("input error", "url为空");
//                                    break;
//                                case 4:
//                                    ToastUtil.showMsgTop(RetrievePasswordActivity.this, "连接超时");
//                                    break;
//                                default:
//                            }
//                        }
//                    };
//                    MessageInfoVo messageInfoVo = new MessageInfoVo();
//                    messageInfoVo.setPhonenumber(phoneNumber);
//                    //=======================发送请求到服务器====================//
//                    EsbUtil esbUtil = new EsbUtil();
//                    verifyCode = esbUtil.MessageService(messageInfoVo, handler);
//                    System.err.println("verifyCode:"+verifyCode[0]);
//                }
//            }
//        });

//        mBtnNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //获取用户名和密码
//                String phoneNumber = mEtPhoneNumber.getText().toString().trim();
//                String vfCode = mEtVfCode.getText().toString().trim();
//                System.err.println("vfCode"+vfCode);
//                System.err.println("verifyCode"+verifyCode[0]);
//                if (TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(vfCode)) {
//                    ToastUtil.showMsgTop(RetrievePasswordActivity.this, "验证码和手机号输入不能为空");
//                } else if (!verifyCode[0].equals(vfCode)) {
//                    ToastUtil.showMsgTop(RetrievePasswordActivity.this, "输入的验证码错误");
//                } else {
//                    //调用接口 查询是否绑定培训证号
//                    @SuppressLint("HandlerLeak") Handler handler = new Handler() {
//                        @Override
//                        public void handleMessage(Message msg) {
//                            super.handleMessage(msg);
//                            switch (msg.what) {
//                                case 0:
//                                    ToastUtil.showMsgTop(RetrievePasswordActivity.this, "服务器连接失败");
//                                    break;
//                                case 1:
//                                    //通过检验 跳转到设置新密码界面
//                                    Intent intent = new Intent(RetrievePasswordActivity.this, SetPasswordActivity.class);
//                                    startActivity(intent);
////                                    RetrievePasswordActivity.this.finish();
//                                    break;
//                                case 2:
//                                    ToastUtil.showMsgTop(RetrievePasswordActivity.this, "该手机未绑定培训证号");
//                                    break;
//                                case 3:
//                                    Log.e("input error", "url为空");
//                                    break;
//                                case 4:
//                                    ToastUtil.showMsgTop(RetrievePasswordActivity.this, "连接超时");
//                                    break;
//                                default:
//                            }
//                        }
//                    };
//
//                    PhoneInfoVo phoneInfoVo = new PhoneInfoVo();
//                    phoneInfoVo.setPhonenumber(phoneNumber);
//                    //=======================发送请求到服务器====================//
//                    EsbUtil esbUtil = new EsbUtil();
//                    esbUtil.trainService(phoneInfoVo, handler);
//                }
//            }
//        });


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

    private class OnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.btn_previous:
                    //返回上一个界面
                    intent = new Intent(RetrievePasswordActivity.this, LoginActivity.class);
                    break;
                case R.id.btn_next:
                    //返回上一个界面
                    intent = new Intent(RetrievePasswordActivity.this, SetPasswordActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
}
