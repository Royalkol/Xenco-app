package com.example.xc_nonapplication;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CountDownView extends LinearLayout {

	private TextView tv_min;
	private TextView tv_sec;
	private MyCount mc;
	private Boolean isStarted=false;
	/**
	 * 定义一个倒计时的监听接口，来监听时间变化
	 * @author fuxinpeng
	 *
	 */
	public interface OnTimeChangeListener{
		String onHourChange(long hour);
		String onMinChange(long min);
		String onSecChange(long sec);
	}

	private OnTimeChangeListener onTimeChangeListener;
	/**
	 * 设置实现了监听接口的类对象
	 * @param onTimeChangeListener
	 */
	public void setOnTimeChangeListener(OnTimeChangeListener onTimeChangeListener){
		this.onTimeChangeListener=onTimeChangeListener;
	}
	/**
	 * 下边为三个构造方法，主要用到了第二个，因为系统实例化时默认是调用第二个构造的
	 * 
	 */
	public CountDownView(Context context) {
		super(context);
	}
	public CountDownView(Context context, AttributeSet attrs){
		super(context,attrs);
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.countdownlayout,this);
		tv_min=(TextView) findViewById(R.id.tv_min);
		tv_sec=(TextView) findViewById(R.id.tv_sec);
	}
	public CountDownView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	/**
	 * 为view设置初始时间，这个不是很实用，可以根据自己的需要来重载
	 * @param millisInFuture
	 * @param countDownInterval
	 */
	public void setCountDown(long millisInFuture, long countDownInterval){
		if(mc==null){
			mc = new MyCount(millisInFuture, countDownInterval);
		}
	}
	/**
	 * 为view设置初始时间，对时、分、秒分别设置，主要用这个来设置时间，比较实用
	 * @param hour
	 * @param min
	 * @param sec
	 */
	public void setCountDown(long hour,long min,long sec){
		if(mc==null){
			tv_min.setText(min+"");
			tv_sec.setText(sec+"");
			mc = new MyCount(min*60*1000+sec*1000, 1000);
		}

		
	}
	/**
	 * 调用这个方法来开始倒计时
	 */
	public void start(){
		if(!isStarted){
			isStarted=true;
			mc.start();
		}
		
	}
	public void cancel(){
		if(isStarted){
			mc.cancel();
			isStarted=false;
		}

	}
	/**
	 * 设置文字大小，可以根据自己的需要添加别的方法
	 * @param size
	 */
	public void setTextSize(float size){
		tv_min.setTextSize(size);
		tv_sec.setTextSize(size);
	}
	/* 定义一个倒计时的内部类 */
	class MyCount extends CountDownTimer {
		private String sec=null;
		private String min=null;
		private String hour=null;
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			//倒计时结束
		}
		@Override
		public void onTick(long millisUntilFinished) {

			if(onTimeChangeListener!=null&&(sec=onTimeChangeListener.onSecChange(millisUntilFinished / 1000%60))!=null){
				tv_sec.setText(sec);
			}else{
				tv_sec.setText(millisUntilFinished / 1000%60+"");
			}
			
			//如果秒数=59则说明分钟数有变化，则对分进行设置
			if(millisUntilFinished / 1000%60==59){
				if(onTimeChangeListener!=null&&(min=onTimeChangeListener.onMinChange(millisUntilFinished/1000%(60*60)/60))!=null){
					tv_min.setText(min);
				}else{
					tv_min.setText(millisUntilFinished/1000%(60*60)/60+"");
				}
				
			}
		}
	}
}
