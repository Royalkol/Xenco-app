package com.example.xc_nonapplication.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xc_nonapplication.R;

/**
 * 作者：Royal
 * <p>
 * 日期: 2020/4/30 10:20
 */
public class ToastUtil {
    public static Toast mToast;

    /**
     * 默认
     *
     * @param context
     * @param msg
     */
    public static void showMsg(Context context, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }


    /**
     * 自定义弹出信息显示在上方
     *
     * @param context
     * @param msg
     */
    public static void showMsgTop(Context context, String msg) {
        if (mToast!=null){
            mToast.cancel();
        }
        mToast = new Toast(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_toast, null);
        TextView textView = view.findViewById(R.id.tv_toast);
        textView.setText(msg);
        mToast.setView(view);
        mToast.setGravity(Gravity.CENTER, 0, -430);
        mToast.show();
    }
}
