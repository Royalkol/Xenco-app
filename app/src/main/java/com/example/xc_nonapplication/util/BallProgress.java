package com.example.xc_nonapplication.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ClipDrawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.xc_nonapplication.R;

/**
 * 作者：Royal
 * <p>
 * 日期: 2021/1/5 15:14
 */
public class BallProgress extends View {
    private float mProgress = 0.0f; //取值位 0 - 1.0

    private boolean selected = true;

    public BallProgress(Context context) {
        super(context);
    }

    public BallProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BallProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mProgressPaint = new Paint();//初始化，定义画笔。
        mProgressPaint.setAntiAlias(true);//设置抗锯齿
    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float progress) {//设置进度，通过进度的大小实现裁剪的大小
        mProgress = progress;
        invalidate();
    }

    private Paint mProgressPaint;


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap dst = getRectangleBitmap();//获取bitmap
        setLayerType(LAYER_TYPE_HARDWARE, null); //开启硬件离屏缓存
        canvas.drawBitmap(dst, 0, 0, mProgressPaint);
    }

    private Bitmap getRectangleBitmap() {
        int width = getWidth();
        int height = getHeight();

        Bitmap dstBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        ClipDrawable clipDrawable = null;
        clipDrawable = (ClipDrawable) getContext().getResources().getDrawable(R.drawable.bottom_top_clip_single_color
        );//获取球形的背景图片，用于裁剪，就是上面看到的进度球中的图片

        clipDrawable.setBounds(new Rect(0, 0, width, height));//设置边界

        clipDrawable.setLevel((int) (10000 * mProgress));//设置进度，

        Canvas canvas = new Canvas(dstBitmap);//设置画布

        clipDrawable.draw(canvas);//绘制

        return dstBitmap;//将bitmap返回
    }
}
