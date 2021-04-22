package com.example.xc_nonapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.xc_nonapplication.Vo.LoginInfoVo;
import com.example.xc_nonapplication.util.EsbUtil;

/**
 * 作者：Royal
 * * <p>
 * * 日期: 2020/12/9 10:41
 * * 登录界面
 */
public class LoginActivity extends AppCompatActivity {

    private TextView mTv1, mTv2, mTv3;
    private Button mBtnLogin, mBtnForget;
    //    private CheckBox mCbDisplayPassword;
    private EditText mEtTrainNumber, mEtPassword;

    private CheckBox mCbRemberPs;
    static String YES = "yes";
    static String NO = "no";
    private String isMemory = "";//isMemory变量用来判断SharedPreferences有没有数据，包括上面的YES和NO
    private String FILE = "saveUserNamePwd";//用于保存SharedPreferences的文件
    private SharedPreferences mSharedPreferences = null;//声明一个SharedPreferences
    static String trainNumber, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mTv1 = findViewById(R.id.tv_1);
        mTv2 = findViewById(R.id.tv_2);
        mTv3 = findViewById(R.id.tv_3);
        mEtTrainNumber = findViewById(R.id.et_trainnumber);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnForget = findViewById(R.id.btn_forgetps);
        mEtPassword = findViewById(R.id.et_password);
        mCbRemberPs = findViewById(R.id.cb_remmberps);

        //设置字体
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/SourceHanSansCN-Medium.otf");
        Typeface typeFace2 = Typeface.createFromAsset(getAssets(), "fonts/SourceHanSansCN-Regular.otf");
        mTv1.setTypeface(typeFace);
        mTv2.setTypeface(typeFace2);
        mTv3.setTypeface(typeFace);

        //设置点击事件
        OnClick onClick = new OnClick();
        mBtnForget.setOnClickListener(onClick);
        mBtnLogin.setOnClickListener(onClick);

        //读取保存的密码并展示在输入框中
        this.showUserInfo();


        //切换密码显示
//        mCbDisplayPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Log.d("aaa", "onCheckedChanged: " + isChecked);
//                if (isChecked) {
//                    //选择状态 显示明文--设置为可见的密码
//                    mEtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                } else {
//                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
//                    mEtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
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
                case R.id.btn_forgetps:
                    //跳转找回密码界面
                    intent = new Intent(LoginActivity.this, RetrievePasswordActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_login: {
                    try {
                        trainNumber = mEtTrainNumber.getText().toString().trim();
                        Log.d("trainNumber", "---" + trainNumber + "---");
                        password = mEtPassword.getText().toString().trim();
                        Log.d("password", "---" + password + "---");
                        //账号密码错误时提示错误信息
                        if (TextUtils.isEmpty(trainNumber) || TextUtils.isEmpty(password)) {
                            //提示信息
                            mTv2.setText("账号或密码不能为空，请重新登录");
                            //修改提示字体颜色
                            mTv2.setTextColor(android.graphics.Color.parseColor("#EA322A"));
                            //修改边框布局(改变颜色)
                            mEtTrainNumber.setBackground(getDrawable(R.drawable.bg_textview_red));
                            mEtPassword.setBackground(getDrawable(R.drawable.bg_textview_red));
                        } else {
                            @SuppressLint("HandlerLeak") Handler handler = new Handler() {
                                @Override
                                public void handleMessage(Message msg) {
                                    super.handleMessage(msg);
                                    switch (msg.what) {
                                        case 0:
                                            //网络连接存在问题 弹出一个提示窗口
                                            Log.d("网络连接故障", "---请检查---");
                                            final CustomDialog customDialog = new CustomDialog(LoginActivity.this);
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
                                            //登录成功 保存密码
                                            Log.d("登录成功", "---请继续一下操作---");
                                            saveUserInfo();
                                            startActivity(new Intent(LoginActivity.this, AttentionActivity.class));
                                            break;
                                        case 2:
                                            Log.d("登录失败", "---账号或密码错误---");
                                            //提示信息
                                            mTv2.setText("账号或密码错误，请重新登录");
                                            //修改提示字体颜色
                                            mTv2.setTextColor(android.graphics.Color.parseColor("#EA322A"));
                                            //修改边框布局(改变颜色)
                                            mEtTrainNumber.setBackground(getDrawable(R.drawable.bg_textview_red));
                                            mEtPassword.setBackground(getDrawable(R.drawable.bg_textview_red));
                                            //修改用户图标为红色
                                            Drawable drawable = getResources().getDrawable(R.mipmap.user_red);
                                            mEtTrainNumber.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                                            mEtTrainNumber.setCompoundDrawablePadding(15);
                                            Drawable drawable2 = getResources().getDrawable(R.mipmap.password_red);
                                            mEtPassword.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);
                                            mEtPassword.setCompoundDrawablePadding(15);
                                            break;
                                        default:
                                    }
                                }
                            };
                            LoginInfoVo loginInfoVo = new LoginInfoVo();
                            loginInfoVo.setTrainnumber(trainNumber);
                            loginInfoVo.setPassword(password);
                            //=======================发送请求到服务器====================//
                            EsbUtil esbUtil = new EsbUtil();
                            esbUtil.longinService(loginInfoVo, handler);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    }

    // remenber方法用于判断是否记住密码，mCbRemberPs选中时，提取出EditText里面的内容，放到SharedPreferences里面的trainnumber和password中
    public void saveUserInfo() {
        if (mCbRemberPs.isChecked()) {
            if (mSharedPreferences == null) {
                mSharedPreferences = getSharedPreferences(FILE, MODE_PRIVATE);
            }
            SharedPreferences.Editor edit = mSharedPreferences.edit();
            edit.putString("trainnumber", mEtTrainNumber.getText().toString());
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

    public void showUserInfo() {
        //记住密码功能
        mSharedPreferences = getSharedPreferences(FILE, MODE_PRIVATE);
        isMemory = mSharedPreferences.getString("isMemory", NO);
        //进入界面时，这个if用来判断SharedPreferences里面name和password有没有数据，有的话则直接打在EditText上面
        if (isMemory.equals(YES)) {
            trainNumber = mSharedPreferences.getString("trainnumber", "");
            password = mSharedPreferences.getString("password", "");
            mEtTrainNumber.setText(trainNumber);
            mEtPassword.setText(password);
        }
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(trainNumber, mEtTrainNumber.toString());
        editor.putString(password, mEtPassword.toString());
        editor.commit();
    }
}