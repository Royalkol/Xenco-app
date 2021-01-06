package com.example.xc_nonapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 作者：Royal
 * <p>
 * 日期: 2020/12/9 10:41
 */
public class TreatmentActivity extends AppCompatActivity {

    private Button mBtnComplete;
    private TextView mTUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);

        //记录信息界面用户图标大小
        mTUser = findViewById(R.id.tv_user);
        Drawable drawable = getResources().getDrawable(R.drawable.user1);
        drawable.setBounds(0, 0, 60, 60);//第一个 0 是距左边距离，第二个 0 是距上边距离，40 分别是长宽
        mTUser.setCompoundDrawables(drawable, null, null, null);//只放左边

        //设置用户 这个用户名可以从前面获取
        mTUser.setText("Royal");

        //跳转至治疗界面
        mBtnComplete=findViewById(R.id.btn_complete2);
        mBtnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (v.getId()) {
                    case R.id.btn_complete2:
                        intent = new Intent(TreatmentActivity.this, CompleteTreatmentActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
    }


}
