package com.example.xc_nonapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.xc_nonapplication.util.ToastUtil;

public class SetPasswordActivity extends AppCompatActivity {

    private Button mBtnPpevious, mBtnComplete;
    private EditText mEtNewPassword, mEtNewPassword1;
    private CheckBox mCbNewPassword, mCbNewPassword1;
    private ImageView mIvSign, mIvSign1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        mBtnPpevious = findViewById(R.id.btn_previous);
        mBtnComplete = findViewById(R.id.btn_complete);

//        mEtNewPassword = findViewById(R.id.et_newpassword);
//        mEtNewPassword1 = findViewById(R.id.et_newpassword1);

        //输入框监听事件
//        mEtNewPassword.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String newPassword = mEtNewPassword.getText().toString().trim();
//                //新密码的长度要6至8位
//                if (newPassword.length() < 6 || newPassword.length() > 8) {
//                    ToastUtil.showMsgTop(SetPasswordActivity.this, "新密码的长度要6到8位之间");
//                } else {
//                    //checkbox后面显示一个勾
//                    Drawable drawable = getResources().getDrawable(R.drawable.right);
//                    mIvSign.setBackground(drawable);
//                }
//            }
//        });


//        mEtNewPassword1.addTextChangedListener(new TextWatcher() {
//
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String newPassword = mEtNewPassword.getText().toString().trim();
//                String newPassword1 = mEtNewPassword1.getText().toString().trim();
//                Log.d("aaa", "newPassword" + "   " + "newPassword");
//                //校验两次密码的一致性 相同则显示相同相同的勾子
//                if (newPassword.equals(newPassword1)) {
//                    Drawable drawable = getResources().getDrawable(R.drawable.right);
//                    mIvSign1.setBackground(drawable);
//                } else {
//                    Drawable drawable1 = getResources().getDrawable(R.drawable.wrong);
//                    mIvSign1.setBackground(drawable1);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });

        OnClick onClick = new OnClick();
        mBtnPpevious.setOnClickListener(onClick);
        mBtnComplete.setOnClickListener(onClick);
        //密码校验部分
//        mBtnComplete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //获取用户名和密码
//                String newPassword = mEtNewPassword.getText().toString().trim();
//                String newPassword1 = mEtNewPassword1.getText().toString().trim();
//                if (TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(newPassword1)) {
//                    ToastUtil.showMsgTop(SetPasswordActivity.this, "新密码不能为空");
//                } else {
//                    //两次密码输入不一致的的情况
//                    if (!newPassword.equals(newPassword1)) {
//                        //该手机号未绑定培训证号
//                        ToastUtil.showMsgTop(SetPasswordActivity.this, "两次输入的密码不一致");
//                        return;
//                    } else {
//                        Intent intent = new Intent(SetPasswordActivity.this, ResetSuccessActivity.class);
//                        startActivity(intent);
//                    }
//                }
//            }
//        });

//        mCbNewPassword = findViewById(R.id.cb_newPassword);
//        //切换密码显示
//        mCbNewPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Log.d("aaa", "onCheckedChanged: " + isChecked);
//                if (isChecked) {
//                    //选择状态 显示明文--设置为可见的密码
//                    mEtNewPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                } else {
//                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
//                    mEtNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                }
//            }
//        });
//
//        mCbNewPassword1 = findViewById(R.id.cb_newPassword1);
//        mCbNewPassword1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Log.d("aaa", "onCheckedChanged: " + isChecked);
//                if (isChecked) {
//                    //选择状态 显示明文--设置为可见的密码
//                    mEtNewPassword1.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                } else {
//                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
//                    mEtNewPassword1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
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
                    //跳转至治疗完成界面
                    intent = new Intent(SetPasswordActivity.this, RetrievePasswordActivity.class);
                    break;
                case R.id.btn_complete:
                    //跳转至治疗完成界面
                    intent = new Intent(SetPasswordActivity.this, ResetSuccessActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
}
