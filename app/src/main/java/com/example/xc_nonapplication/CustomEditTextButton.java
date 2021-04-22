package com.example.xc_nonapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Royal on 2021/4/8.
 * Describle:
 */
public class CustomEditTextButton extends FrameLayout {

    private EditText editText;
    private Button Button;


    public CustomEditTextButton(@NonNull Context context) {
        super(context);
    }

    public CustomEditTextButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomEditTextButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomEditTextButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.textview_button, this, true);
        init_widget();
        addButonListener();
    }

    public void init_widget() {
        this.editText = findViewById(R.id.et_message);
        this.Button = findViewById(R.id.bt_vfcode);
    }


    public void addButonListener() {
        Button.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                Log.d("CustomEditTextButton", "---" + "CustomEditTextButton" + "---");
            }
        });
    }
}
