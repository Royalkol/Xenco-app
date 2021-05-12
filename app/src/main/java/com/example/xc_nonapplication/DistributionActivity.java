package com.example.xc_nonapplication;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.xc_nonapplication.util.Data_syn;
import com.example.xc_nonapplication.util.TCP_client;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 作者：Royal
 * <p>
 * 日期: 2020/12/9 10:16
 * 配气界面
 */
public class DistributionActivity extends AppCompatActivity {

    private TextView mTv1, mTv2, mTv3, mTv4, mTv5, mTv6;
    private Button mBtnStart, mBtnStop, mBtnNext;
    private RoundProgressBar mRb1, mRb2;
    private WaveProgressView mWp1, mWp2;
    private int progress = 100;

    // TCP客户端通信模式下
    private TCP_client tcp_client = null;
    private final static int CLIENT_STATE_CORRECT_READ = 7;
    public final static int CLIENT_STATE_CORRECT_WRITE = 8;               //正常通信信息
    public final static int CLIENT_STATE_ERROR = 9;                 //发生错误异常信息
    public final static int CLIENT_STATE_IOFO = 10;                  //发送SOCKET信息
    private boolean client_islink = false;

    //复选状态信息
    private boolean Hex_show = false;
    private boolean Auto_huang = false;
    private boolean Hex_send = false;
    private boolean Auto_send = false;

    //计数用
    private int countin = 0;
    private int countout = 0;

    //ip 地址
    private InetAddress inetAddress;
    //端口号
    private int port = 12345;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribution);
        mTv1 = findViewById(R.id.tv_1);
        mTv2 = findViewById(R.id.tv_2);
        mTv4 = findViewById(R.id.tv_4);
        mTv5 = findViewById(R.id.tv_5);
        mTv6 = findViewById(R.id.tv_6);
        mBtnStart = findViewById(R.id.btn_start);
        mBtnStop = findViewById(R.id.btn_stop);
        mRb1 = findViewById(R.id.rb_1);
        mRb2 = findViewById(R.id.rb_2);
        mWp1 = findViewById(R.id.wp_1);
        mWp2 = findViewById(R.id.wp_2);
        mRb1.setProgress(progress);
        mRb2.setProgress(progress);

        //设置字体
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/SourceHanSansCN-Medium.otf");
        mTv4.setTypeface(typeFace);
        mTv5.setTypeface(typeFace);
        mTv6.setTypeface(typeFace);

        if (tcp_client == null) {
            tcp_client = new TCP_client(cli_handler);
            try {
                InetAddress ipAddress = InetAddress.getByName("192.168.0.199");
                tcp_client.setInetAddress(ipAddress);
                tcp_client.setPort(port);

            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            tcp_client.start();
            Log.d("---tcp_client---", "连接完毕");
        }

        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (client_islink == true) {
                    String message = "AT+STACH1=1" + "\r\n";
                    sendmessage(message);
                    String message1 = "AT+STACH2=1" + "\r\n";
                    sendmessage(message1);
                    Log.d("---继电器开关1打开---", "AT+STACH1=1");
                    Log.d("---继电器开关2打开---", "AT+STACH2=1");

                } else {
                    // "连接未建立"
                }

                //球行进度
                ObjectAnimator objectAnimator0 = ObjectAnimator.ofFloat(mWp1, "progress", 0f, 100f);
                objectAnimator0.setDuration(3300);
                objectAnimator0.setInterpolator(new LinearInterpolator());
                objectAnimator0.start();

                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mWp2, "progress", 0f, 100f);
                objectAnimator1.setDuration(3300);
                objectAnimator1.setInterpolator(new LinearInterpolator());
                objectAnimator1.start();
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        while (progress > 0) {
                            progress -= 2;
                            System.out.println("progress" + progress);
                            mRb1.setProgress(progress);
                            mRb2.setProgress(progress);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("---progress---", progress+"");
//                        //若气体剩余量为0时则弹出提示窗口
//                        if (progress==0){
//                            final CustomDialog customDialog = new CustomDialog(DistributionActivity.this);
//                            customDialog.setDrawable(R.mipmap.yang_less);
//                            customDialog.setConfirm("确认", new CustomDialog.IonConfirmListener() {
//                                @Override
//                                public void onConfirm(CustomDialog dialog) {
//                                    //点击确认后 alert对话框消失
//                                    customDialog.dismiss();
//                                }
//                            });
//                            customDialog.show();
//                        }

                        String message = "AT+STACH1=0" + "\r\n";
                        String message1 = "AT+STACH2=0" + "\r\n";
                        sendmessage(message);
                        sendmessage(message1);
                        Log.d("---继电器开关1关闭---", "AT+STACH1=0");
                        Log.d("---继电器开关2关闭---", "AT+STACH2=0");
                        Looper.loop();
                    }
                }).start();




            }
        });

        mBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //中止配气
                String message = "AT+STACH1=0" + "\r\n";
                String message1 = "AT+STACH2=0" + "\r\n";
                sendmessage(message);
                sendmessage(message1);
            }
        });

        //跳转治疗中界面 进行治疗
        mBtnNext = findViewById(R.id.btn_next);
        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DistributionActivity.this, TreatmentActivity.class);
                startActivity(intent);
            }
        });
        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DistributionActivity.this, TreatmentActivity.class);
                startActivity(intent);
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


    @Override
    protected void onStart() {
        super.onStart();
    }

    //发送数据函数
    public void sendmessage(String message) {
        if (Hex_send == true) {
            byte[] send = Data_syn.hexStr2Bytes(message);

            tcp_client.sendmessage(send);

        } else {
            byte[] send = null;
            try {
                send = message.getBytes("GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (send == null)
                return;

            tcp_client.sendmessage(send);
        }
    }

    //客户端通信模式下
    private Handler cli_handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CLIENT_STATE_ERROR:
                    Toast.makeText(DistributionActivity.this, "连接异常"
                            , Toast.LENGTH_SHORT).show();
                    client_islink = false;
                    break;
                case CLIENT_STATE_IOFO:
                    client_islink = true;
                    String[] strings = (String[]) msg.obj;
                    break;
                //接收数据
                case CLIENT_STATE_CORRECT_READ:
                    Handler_receive(msg);
                    break;
                //发送数据
                case CLIENT_STATE_CORRECT_WRITE:
                    Handler_send(msg);
                    break;
            }
        }
    };

    // 接收数据处理分析函数，通过handler从子线程回传到主线程
    private void Handler_receive(Message msg) {
        byte[] buffer = (byte[]) msg.obj;
        if (Hex_show == true) {
            String readMessage = " "
                    + Data_syn.bytesToHexString(buffer, msg.arg1);
            //re_data_show.append(readMessage);
            if (Auto_huang == true) {
                // re_data_show.append("\n");
            }
            countin += readMessage.length() / 2;                               // 接收计数
        } else if (Hex_show == false) {
            String readMessage = null;
            //   try {
            //       readMessage = new String(buffer, 0, msg.arg1, "GBK");
            //   } catch (UnsupportedEncodingException e) {
            //       e.printStackTrace();
            //   }
            //re_data_show.append(readMessage);
            if (Auto_huang == true) {
                //   re_data_show.append("\n");
            }
//            countin += readMessage.length();                                   // 接收计数
            // re_count.setText("" + countin+"个");
        }
    }

    //发送数据处理分析函数，通过handler从子线程回传主线程
    private void Handler_send(Message msg) {
        byte[] writeBuf = (byte[]) msg.obj;
        if (Auto_send == true) {
            //String s = edit_time.getText().toString();
            // long t = Long.parseLong(s);
            //ser_handler.postDelayed(runnable, t);
        } else if (Auto_send == false) {
            // ser_handler.removeCallbacks(runnable);
        }

        if (Hex_send == true) {
            String writeMessage = Data_syn.Bytes2HexString(writeBuf);
            countout += writeMessage.length() / 2;
            // se_count.setText("" + countout+"个");
        } else if (Hex_send == false) {
            String writeMessage = null;
            // try {
            //     writeMessage = new String(writeBuf, "GBK");
            // } catch (UnsupportedEncodingException e1) {
            //     e1.printStackTrace();
            // }
            // countout += writeMessage.length();
            // se_count.setText("" + countout+"个");
        }
    }
}
