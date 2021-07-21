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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.xc_nonapplication.Vo.MessageInfoVo;
import com.example.xc_nonapplication.response.Response;
import com.example.xc_nonapplication.util.EsbUtil;
import com.example.xc_nonapplication.util.OtherUtil;
import com.example.xc_nonapplication.util.ToastUtil;

public class RetrievePasswordActivity extends AppCompatActivity {
    private TextView mTv1, mTv2, mTv3;
    private Button mBtnPpevious, mBtnNext, mBtnVfCode;
    private ImageView mIv1, mIv2;
    private EditText mEtPhoneNumber,mEtMessage;
    private CustomEditTextButton customEditTextButton;
    private String phoneNumber;
    private String[] verifyCode={"000000"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_password);
        mTv1 = findViewById(R.id.tv_1);
        mTv2 = findViewById(R.id.tv_2);
        mTv3 = findViewById(R.id.tv_3);
        mIv1 = findViewById(R.id.iv_1);
        mIv2 = findViewById(R.id.iv_2);
        mBtnPpevious = findViewById(R.id.btn_previous);
        mBtnNext = findViewById(R.id.btn_next);
        mEtPhoneNumber = findViewById(R.id.et_phonenumber);
        mEtMessage = findViewById(R.id.et_message);
        mBtnVfCode = findViewById(R.id.bt_vfcode);
        customEditTextButton = findViewById(R.id.cs_1);

        //设置字体
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/SourceHanSansCN-Medium.otf");
        Typeface typeFace2 = Typeface.createFromAsset(getAssets(), "fonts/SourceHanSansCN-Regular.otf");
        mTv1.setTypeface(typeFace);
        mTv2.setTypeface(typeFace2);
        mTv3.setTypeface(typeFace2);

        OnClick onClick = new OnClick();
        mBtnPpevious.setOnClickListener(onClick);
//        mBtnNext.setOnClickListener(onClick);

        mBtnVfCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("mBtnVfCode", "---onClick---");
                phoneNumber = mEtPhoneNumber.getText().toString().trim();
                Log.d("phoneNumber", "---" + phoneNumber + "---");
                //手号码为输入时
                if (TextUtils.isEmpty(phoneNumber)) {
                    //提示信息
                    mTv2.setText("手机号未填写 请输入");
                    //修改提示字体颜色
                    mTv2.setTextColor(android.graphics.Color.parseColor("#EA322A"));
                    //修改边框布局(改变颜色)
                    mEtPhoneNumber.setBackground(getDrawable(R.drawable.bg_textview_red));
                    //将隐藏的红色感叹号图标显示出来
                    mIv1.setVisibility(View.VISIBLE);
                } else if (!OtherUtil.CheckMobilePhoneNum(phoneNumber)) {
                    //提示信息
                    mTv2.setText("请输入正确的手机号");
                    //修改提示字体颜色
                    mTv2.setTextColor(android.graphics.Color.parseColor("#EA322A"));
                    //修改边框布局(改变颜色)
                    mEtPhoneNumber.setBackground(getDrawable(R.drawable.bg_textview_red));
                    Drawable drawable = getResources().getDrawable(R.mipmap.phone_red);
                    mEtPhoneNumber.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                    mEtPhoneNumber.setCompoundDrawablePadding(15);
                    //将隐藏的红色感叹号图标显示出来
                    mIv1.setVisibility(View.VISIBLE);
                } else {
                    //当输入的手机号通过上面的校验时 恢复成之前的颜色
                    mTv2.setText("");
                    //修改边框布局(改变颜色)
                    mEtPhoneNumber.setBackground(getDrawable(R.drawable.bg_textview_bule));
                    Drawable drawable = getResources().getDrawable(R.mipmap.phone_bule);
                    mEtPhoneNumber.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                    mEtPhoneNumber.setCompoundDrawablePadding(15);
                    //将隐藏的红色感叹号图标显示出来
                    mIv1.setVisibility(View.INVISIBLE);
                    //调用验证码接口 该接口先会查询该手机号码是否绑定培训证号 若成功贼会调用
                    @SuppressLint("HandlerLeak") Handler handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            switch (msg.what) {
                                case 0:
                                    Log.d("网络连接故障", "---请检查---");
                                    final CustomDialog customDialog = new CustomDialog(RetrievePasswordActivity.this);
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
                                    //通过检验 跳转到设置新密码界面
                                    ToastUtil.showMsgTop(RetrievePasswordActivity.this, "请查看收到的验证码");
                                    break;
                                default:
                            }
                        }
                    };
                    MessageInfoVo messageInfoVo = new MessageInfoVo();
                    messageInfoVo.setPhonenumber(phoneNumber);
                    //=======================发送请求到服务器====================//
                    EsbUtil esbUtil = new EsbUtil();
                    Response response = null;
                    try {
                        response = esbUtil.MessageService(messageInfoVo, handler);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String verifyCode =response.getBody().getMessageInfoVo().getVfcode();
                    System.err.println("verifyCode:" + verifyCode);
                }
            }
        });


        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取手机号和验证码
                phoneNumber = mEtPhoneNumber.getText().toString().trim();
                Log.d("phoneNumber", phoneNumber);
                //获取输入的验证码
                String vfCode = mEtMessage.getText().toString().trim();
                Log.d("vfCode", vfCode);
                if (TextUtils.isEmpty(phoneNumber)) {
                    //提示信息
                    mTv2.setText("手机号未填写 请输入");
                    //修改提示字体颜色
                    mTv2.setTextColor(android.graphics.Color.parseColor("#EA322A"));
                    //修改边框布局(改变颜色)
                    mEtPhoneNumber.setBackground(getDrawable(R.drawable.bg_textview_red));
                    //将隐藏的红色感叹号图标显示出来
                    mIv1.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(vfCode)) {
                    //提示信息
                    mTv3.setText("验证码未填写 请输入");
                    //修改提示字体颜色
                    mTv3.setTextColor(android.graphics.Color.parseColor("#EA322A"));
                    //修改边框布局(改变颜色)
                    customEditTextButton.setBackground(getDrawable(R.drawable.bg_textview_red));
                    //将隐藏的红色感叹号图标显示出来
                    mIv2.setVisibility(View.VISIBLE);
                } else if (!verifyCode[0].equals(vfCode)) {
                    //提示信息
                    mTv3.setText("输入的验证码错误");
                    //修改提示字体颜色
                    mTv3.setTextColor(android.graphics.Color.parseColor("#EA322A"));
                    //修改边框布局(改变颜色)
                    customEditTextButton.setBackground(getDrawable(R.drawable.bg_textview_red));
                    //将隐藏的红色感叹号图标显示出来
                    mIv2.setVisibility(View.VISIBLE);
                } else {
                    Intent intent = new Intent(RetrievePasswordActivity.this, SetPasswordActivity.class);
                    startActivity(intent);
                }
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

    private class OnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.btn_previous:
                    //返回上一个界面
                    intent = new Intent(RetrievePasswordActivity.this, LoginActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
}
