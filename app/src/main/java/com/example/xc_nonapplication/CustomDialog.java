package com.example.xc_nonapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Royal on 2021/4/7.
 * Describle:
 */
public class CustomDialog extends Dialog implements View.OnClickListener {

    private TextView mTvConfirm, mTv1, mTv2;
    private ImageView mIv1;
    private int drawable;
    private String confirm;
    private String text1;
    private String text2;
    private IonConfirmListener confirmListener;


    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm, IonConfirmListener listener) {
        this.confirm = confirm;
        this.confirmListener = listener;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }


    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_4g);
        mTvConfirm = findViewById(R.id.tv_confrim);
        mTv1 = findViewById(R.id.tv_1);
        mTv2 = findViewById(R.id.tv_2);
        mIv1 = findViewById(R.id.iv_1);
        if (!TextUtils.isEmpty(text1)) {
            mTv1.setText(text1);
        }
        if (!TextUtils.isEmpty(text2)) {
            mTv2.setText(text2);
        }
        if (!TextUtils.isEmpty(confirm)) {
            mTvConfirm.setText(confirm);
        }
        mIv1.setImageResource(drawable);
        mTvConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confrim:
                if (confirmListener != null) {
                    confirmListener.onConfirm(this);
                }
                break;
        }
    }

    public interface IonConfirmListener {
        void onConfirm(CustomDialog dialog);
    }
}
