package com.example.xc_nonapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.xc_nonapplication.Vo.LoginInfoVo;
import com.example.xc_nonapplication.util.CheckUtil;
import com.example.xc_nonapplication.util.EsbUtil;
import com.example.xc_nonapplication.util.ToastUtil;


public class LoginActivity extends AppCompatActivity {

    private Button mBtnLogin, mBtnForget;
    private CheckBox mCbDisplayPassword;
    private EditText mEttrainnumber, mEtPassword;
    private CheckBox mCbRemberPs;
    static String YES = "yes";
    static String NO = "no";
    private String isMemory = "";//isMemory变量用来判断SharedPreferences有没有数据，包括上面的YES和NO
    private String FILE = "saveUserNamePwd";//用于保存SharedPreferences的文件
    private SharedPreferences mSharedPreferences = null;//声明一个SharedPreferences
    static String trainnumber, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //控制登录用户名图标大小
        mEttrainnumber = findViewById(R.id.et_trainnumber);
        Drawable drawable = getResources().getDrawable(R.drawable.trainnumber);
        drawable.setBounds(0, 0, 60, 60);//第一个 0 是距左边距离，第二个 0 是距上边距离，40 分别是长宽
        mEttrainnumber.setCompoundDrawables(drawable, null, null, null);//只放左边

        //控制密码图标大小
        mEtPassword = findViewById(R.id.et_password);
        Drawable drawable1 = getResources().getDrawable(R.drawable.password);
        drawable1.setBounds(0, 0, 60, 60);//第一个 0 是距左边距离，第二个 0 是距上边距离，40 分别是长宽
        mEtPassword.setCompoundDrawables(drawable1, null, null, null);//只放左边

        mBtnLogin = findViewById(R.id.btn_login);
        mBtnForget = findViewById(R.id.btn_forgetps);
        mCbDisplayPassword = findViewById(R.id.cb_DisplayPassword);
        mEtPassword = findViewById(R.id.et_password);
        mCbRemberPs = findViewById(R.id.cb_remmberps);
        OnClick onClick = new OnClick();
        //登陆功能
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //获取用户名和密码
                    String trainnumber = mEttrainnumber.getText().toString().trim();
                    String password = mEtPassword.getText().toString().trim();
                    String number = "";
                    if (trainnumber != null && !"".equals(trainnumber)) {
                        number = trainnumber.substring(4, trainnumber.length());
                    }
                    //校验账号和密码
                    if (TextUtils.isEmpty(trainnumber) || TextUtils.isEmpty(password)) {
                        ToastUtil.showMsgTop(LoginActivity.this, "用户名密码输入不能为空");
                    } else if (trainnumber.length() > 12) {
                        ToastUtil.showMsgTop(LoginActivity.this, "培训证号的长度小于12位,请检查您输入的培训证号");
                    } else if (!trainnumber.startsWith("xcpx")) {
                        ToastUtil.showMsgTop(LoginActivity.this, "请输入合法的培训证");
                    } else if (number.length() != 8 || !CheckUtil.isInteger(number)) {
                        ToastUtil.showMsgTop(LoginActivity.this, "请输入合法的培训证");
                    } else if (password.length() > 8 || password.length() < 6) {
                        ToastUtil.showMsgTop(LoginActivity.this, "输入密码的长度应大于6位小于8位,请检查您输入的密码");
                    } else {
                        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                switch (msg.what) {
                                    case 0:
                                        ToastUtil.showMsgTop(LoginActivity.this, "服务器连接失败");
                                        break;
                                    case 1:
//                                      ToastUtil.showMsgTop(LoginActivity.this, "登录成功");
                                        remenber();
                                        startActivity(new Intent(LoginActivity.this, RegistrationInformationActivity.class));
                                        LoginActivity.this.finish();
                                        break;
                                    case 2:
                                        ToastUtil.showMsgTop(LoginActivity.this, "登录失败,培训证号或密码输入有误");
                                        break;
                                    case 3:
                                        Log.e("input error", "url为空");
                                        break;
                                    case 4:
                                        ToastUtil.showMsgTop(LoginActivity.this, "连接超时");
                                        break;
                                    default:
                                }
                            }
                        };
                        LoginInfoVo loginInfoVo = new LoginInfoVo();
                        loginInfoVo.setTrainnumber(trainnumber);
                        loginInfoVo.setPassword(password);
                        //=======================发送请求到服务器====================//
                        EsbUtil esbUtil = new EsbUtil();
                        esbUtil.longinService(loginInfoVo, handler);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mBtnForget.setOnClickListener(onClick);
        //切换密码显示
        mCbDisplayPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("aaa", "onCheckedChanged: " + isChecked);
                if (isChecked) {
                    //选择状态 显示明文--设置为可见的密码
                    mEtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    mEtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        //记住密码功能
        mSharedPreferences = getSharedPreferences(FILE, MODE_PRIVATE);
        isMemory = mSharedPreferences.getString("isMemory", NO);
        //进入界面时，这个if用来判断SharedPreferences里面name和password有没有数据，有的话则直接打在EditText上面
        if (isMemory.equals(YES)) {
            trainnumber = mSharedPreferences.getString("trainnumber", "");
            password = mSharedPreferences.getString("password", "");
            mEttrainnumber.setText(trainnumber);
            mEtPassword.setText(password);
        }
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(trainnumber, mEttrainnumber.toString());
        editor.putString(password, mEtPassword.toString());
        editor.commit();
    }


    private class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.btn_forgetps:
                    //跳转找回密码界面
                    intent = new Intent(LoginActivity.this, RetrievePasswordActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }

    // remenber方法用于判断是否记住密码，mCbRemberPs选中时，提取出EditText里面的内容，放到SharedPreferences里面的trainnumber和password中
    public void remenber() {
        if (mCbRemberPs.isChecked()) {
            if (mSharedPreferences == null) {
                mSharedPreferences = getSharedPreferences(FILE, MODE_PRIVATE);
            }
            SharedPreferences.Editor edit = mSharedPreferences.edit();
            edit.putString("trainnumber", mEttrainnumber.getText().toString());
            edit.putString("password", mEtPassword.getText().toString());
            edit.putString("isMemory", YES);
            edit.commit();
        } else if (!mCbRemberPs.isChecked()) {
            if (mSharedPreferences == null) {
                mSharedPreferences = getSharedPreferences(FILE, MODE_PRIVATE);
            }
            SharedPreferences.Editor edit = mSharedPreferences.edit();
            edit.putString("isMemory", NO);
            edit.commit();
        }
    }
}