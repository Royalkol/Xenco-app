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
 *  * <p>
 *  * 日期: 2020/12/9 10:41
 *  * 治疗界面
 */
public class TreatmentActivity extends AppCompatActivity {

    private Button mBtnComplete;
    private TextView mTUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);

        //跳转至治疗结束信息展示界面
        mBtnComplete=findViewById(R.id.btn_complete);
        mBtnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (v.getId()) {
                    case R.id.btn_complete:
                        intent = new Intent(TreatmentActivity.this, CompleteTreatmentActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
    }


}
