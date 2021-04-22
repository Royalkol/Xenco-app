package com.example.xc_nonapplication;

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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.xc_nonapplication.util.ToastUtil;

public class SetPasswordActivity extends AppCompatActivity {

    private Button mBtnPpevious, mBtnComplete;
    private EditText mEtNewPassword, mEtConfirmPassword;
    private TextView mTv1, mTv2, mTv3, mTv4;
    private CheckBox mCbNewPassword, mCbConfirmPassword;
    private ImageView mIv1, mIv2, mIvSign1, mIvSign2;
    private String newPassword, confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        mBtnPpevious = findViewById(R.id.btn_previous);
        mBtnComplete = findViewById(R.id.btn_complete);
        mEtNewPassword = findViewById(R.id.et_newpassword);
        mEtConfirmPassword = findViewById(R.id.et_confirmpassword);
        mTv1 = findViewById(R.id.tv_1);
        mTv2 = findViewById(R.id.tv_2);
        mTv3 = findViewById(R.id.tv_3);
        mTv4 = findViewById(R.id.tv_4);
        mIv1 = findViewById(R.id.iv_1);
        mIv2 = findViewById(R.id.iv_2);
        mIvSign1 = findViewById(R.id.iv_Sign1);
        mIvSign2 = findViewById(R.id.iv_Sign2);
        mCbNewPassword = findViewById(R.id.cb_newps);
        mCbConfirmPassword = findViewById(R.id.cb_confrimps);

        //输入框监听事件
        mEtNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                newPassword = mEtNewPassword.getText().toString().trim();
                //新密码的长度要6至8位
                if (newPassword.length() < 6 || newPassword.length() > 8) {
                    //提示信息
                    mTv3.setText("新密码的长度要6到8位之间");
                    //修改提示字体颜色
                    mTv3.setTextColor(android.graphics.Color.parseColor("#EA322A"));
                    mEtNewPassword.setBackground(getDrawable(R.drawable.bg_textview_red));
                    Drawable drawable = getResources().getDrawable(R.mipmap.lock_red);
                    mEtNewPassword.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                    mEtNewPassword.setCompoundDrawablePadding(15);
                    mIv1.setVisibility(View.VISIBLE);
                    Drawable drawable2 = getResources().getDrawable(R.mipmap.icon_wrong);
                    mIvSign1.setBackground(drawable2);
                } else {
                    //提示信息
                    mTv3.setText("");
                    //修改提示字体颜色
                    mTv3.setTextColor(android.graphics.Color.parseColor("#EA322A"));
                    mEtNewPassword.setBackground(getDrawable(R.drawable.bg_textview_bule));
                    Drawable drawable = getResources().getDrawable(R.mipmap.lock_gray);
                    mEtNewPassword.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                    mEtNewPassword.setCompoundDrawablePadding(15);
                    mIv1.setVisibility(View.INVISIBLE);
                    Drawable drawable2 = getResources().getDrawable(R.mipmap.icon_right);
                    mIvSign1.setBackground(drawable2);
                }
            }
        });

        //输入框监听事件
        mEtConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                newPassword = mEtNewPassword.getText().toString().trim();
                Log.d("newPassword", newPassword);
                confirmPassword = mEtConfirmPassword.getText().toString().trim();
                Log.d("confirmPassword", confirmPassword);
                //校验两次密码的一致性
                if (newPassword.equals(confirmPassword)) {
                    mTv4.setText("");
                    mEtConfirmPassword.setBackground(getDrawable(R.drawable.bg_textview_bule));
                    Drawable drawable = getResources().getDrawable(R.mipmap.lock_gray);
                    mEtConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                    mEtConfirmPassword.setCompoundDrawablePadding(15);
                    mIv2.setVisibility(View.INVISIBLE);
                    Drawable drawable2 = getResources().getDrawable(R.mipmap.icon_right);
                    mIvSign2.setBackground(drawable2);
                } else {
                    mTv4.setText("两次输入的密码不同");
                    mEtConfirmPassword.setBackground(getDrawable(R.drawable.bg_textview_red));
                    Drawable drawable = getResources().getDrawable(R.mipmap.lock_red);
                    mEtConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                    mEtConfirmPassword.setCompoundDrawablePadding(15);
                    mIv2.setVisibility(View.VISIBLE);
                    Drawable drawable1 = getResources().getDrawable(R.mipmap.icon_wrong);
                    mIvSign2.setBackground(drawable1);
                }
            }
        });

        OnClick onClick = new OnClick();
        mBtnPpevious.setOnClickListener(onClick);
        mBtnComplete.setOnClickListener(onClick);
        //密码校验部分
        mBtnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取两次输入的密码
                newPassword = mEtNewPassword.getText().toString().trim();
                Log.d("newPassword", newPassword);
                confirmPassword = mEtConfirmPassword.getText().toString().trim();
                Log.d("confirmPassword", confirmPassword);
                if (TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
                    ToastUtil.showMsgTop(SetPasswordActivity.this, "新密码不能为空");
                } else {
                    //两次密码输入不一致的的情况
                    if (!newPassword.equals(confirmPassword)) {
                        //该手机号未绑定培训证号
                        ToastUtil.showMsgTop(SetPasswordActivity.this, "两次输入的密码不一致");
                        return;
                    } else {
                        Intent intent = new Intent(SetPasswordActivity.this, ResetSuccessActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });


        //切换密码显示
        mCbNewPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("mCbNewPassword", "onCheckedChanged: " + isChecked);
                if (isChecked) {
                    //选择状态 显示明文--设置为可见的密码
                    mEtNewPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    mEtNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });


        mCbConfirmPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("mCbConfirmPassword", "onCheckedChanged: " + isChecked);
                if (isChecked) {
                    //选择状态 显示明文--设置为可见的密码
                    mCbConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    mCbConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
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
