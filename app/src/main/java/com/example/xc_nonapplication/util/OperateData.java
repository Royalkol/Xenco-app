package com.example.xc_nonapplication.util;

import android.os.Handler;
import android.util.Log;

import com.example.xc_nonapplication.response.Response;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 作者：Royal
 * <p>
 * 日期: 2020/5/18 14:49
 */
public class OperateData {
    public static final String TRAIN = "TRAIN_NUMBER";
    public static final String LOGIN = "LOGIN";
    public static final String MESSAGE = "SENDMSSAGE";
    public static final String TREAT = "TREATINFO";
    public static final String DIS = "DISTRIBUTION";


    /**
     * @param stringArray 将string数组转成json格式字符串
     * @return
     */
    public String stringTojson(String stringArray[]) {
        JSONObject jsonObject = null;
        if (stringArray == null) {
            return "";
        }
        jsonObject = new JSONObject();
        try {
            jsonObject.put("trainnumber", stringArray[0]);
            jsonObject.put("password", stringArray[1]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String jsonString = String.valueOf(jsonObject);
        return jsonString;
    }

    /**
     *
     */

    /**
     * 功能：json字符串转字符串
     *
     * @param jsonString
     * @return String
     */
    public int jsonToint(String jsonString) {
        int type = 1;
        try {
            JSONObject responseJson = new JSONObject(jsonString);
            type = responseJson.getInt("type");
            Log.i("type", "" + type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return type;
    }


    /**
     * 功能：发送jsonString到服务器并解析回应
     *
     * @param jsonString mh url
     *                   handler 参数规定
     *                   msg.what:
     *                   0：服务器连接失败
     *                   1：注册/登录成功 跳转页面
     *                   2：用户已存在/登录失败
     *                   3：地址为空
     *                   4：连接超时
     */
    public void sendData(final String jsonString, final android.os.Handler mh, final URL url, final String requestType) {

        if (url == null) {
            mh.sendEmptyMessage(3);
            Log.d("url", "----url不存在---");
        } else {
            new Thread(new Runnable() {

                @Override
                public void run() {

                    HttpURLConnection httpURLConnection = null;
                    BufferedReader bufferedReader = null;
                    try {
                        httpURLConnection = (HttpURLConnection) url.openConnection();
                        // 设置连接超时时间
                        httpURLConnection.setConnectTimeout(5 * 1000);
                        //设置从主机读取数据超时
                        httpURLConnection.setReadTimeout(5 * 1000);
                        // Post请求必须设置允许输出 默认false
                        httpURLConnection.setDoOutput(true);
                        //设置请求允许输入 默认是true
                        httpURLConnection.setDoInput(true);
                        // Post请求不能使用缓存
                        httpURLConnection.setUseCaches(false);
                        // 设置为Post请求
                        httpURLConnection.setRequestMethod("POST");
                        //设置本次连接是否自动处理重定向
                        httpURLConnection.setInstanceFollowRedirects(true);
                        // 配置请求Content-Type
                        httpURLConnection.setRequestProperty("Content-Type", "application/json");
                        //开始连接
                        httpURLConnection.connect();
                        //发送数据
                        Log.i("JSONString", jsonString);
                        DataOutputStream os = new DataOutputStream(httpURLConnection.getOutputStream());
                        os.writeBytes(jsonString);
                        os.flush();
                        os.close();
                        Log.i("状态码：", "" + httpURLConnection.getResponseCode());

                        if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                            StringBuilder response = new StringBuilder();
                            String temp;
                            while ((temp = bufferedReader.readLine()) != null) {
                                response.append(temp);
                                Log.i("Main", response.toString());
                            }
                            System.out.println(response);
//                            int type = jsonToint(response.toString());
                            //将返回的报文修改解析成对象 json字符串转对象
                            Gson gson = new Gson();
                            Response responseinfo = gson.fromJson(String.valueOf(response), Response.class);
                            System.out.println(responseinfo);
                            int type = 1;
                            //根据返回节点判断的服务类型
                            if (TRAIN.equals(requestType)) {
                                if (!"0000".equals(responseinfo.getBody().getTraininfo().getBackStatus())) {
                                    type = 0;
                                }
                            } else if (LOGIN.equals(requestType)) {
                                if (!"0000".equals(responseinfo.getBody().getResultinfo().getBackStatus())) {
                                    type = 0;
                                }
                            } else if (MESSAGE.equals(requestType)) {
                                if (!"0000".equals(responseinfo.getBody().getMessageInfoVo().getBackStatus())) {
                                    type = 0;
                                }
                                String vfcode = responseinfo.getBody().getMessageInfoVo().getVfcode();
                            } else if (TREAT.equals(requestType)) {
                                if (!"0000".equals(responseinfo.getBody().getTraininfo().getBackStatus())) {
                                    type = 0;
                                }
                            }
                            System.err.println("type:" + type);
                            //根据
                            switch (type) {
                                case 1:
                                    mh.sendEmptyMessage(1);
                                    break;
                                case 0:
                                    mh.sendEmptyMessage(2);
                                    break;
                                default:
                            }
                        } else {
                            mh.sendEmptyMessage(0);
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                        mh.sendEmptyMessage(0);
                    } finally {
                        //关闭bufferedreader
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e) {
                                e.printStackTrace();

                            }
                        }
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                    }
                }
            }).start();
        }
    }


//    /**
//     * 功能：发送jsonString到服务器并解析回应
//     *
//     * @param jsonString mh url
//     *                   handler 参数规定
//     *                   msg.what:
//     *                   0：服务器连接失败
//     *                   1：注册/登录成功 跳转页面
//     *                   2：用户已存在/登录失败
//     *                   3：地址为空
//     *                   4：连接超时
//     */
//    public String[] sendDataRecive(final String jsonString, final android.os.Handler mh, final URL url, final String requestType) {
//        final String[] vfcode = {""};
//
//        if (url == null) {
//            mh.sendEmptyMessage(3);
//        } else {
//            new Thread(new Runnable() {
//
//                @Override
//                public void run() {
//
//                    HttpURLConnection httpURLConnection = null;
//                    BufferedReader bufferedReader = null;
//                    try {
//                        httpURLConnection = (HttpURLConnection) url.openConnection();
//                        // 设置连接超时时间
//                        httpURLConnection.setConnectTimeout(5 * 1000);
//                        //设置从主机读取数据超时
//                        httpURLConnection.setReadTimeout(5 * 1000);
//                        // Post请求必须设置允许输出 默认false
//                        httpURLConnection.setDoOutput(true);
//                        //设置请求允许输入 默认是true
//                        httpURLConnection.setDoInput(true);
//                        // Post请求不能使用缓存
//                        httpURLConnection.setUseCaches(false);
//                        // 设置为Post请求
//                        httpURLConnection.setRequestMethod("POST");
//                        //设置本次连接是否自动处理重定向
//                        httpURLConnection.setInstanceFollowRedirects(true);
//                        // 配置请求Content-Type
//                        httpURLConnection.setRequestProperty("Content-Type", "application/json");
//                        //开始连接
//                        httpURLConnection.connect();
//
//
//                        //发送数据
//                        Log.i("JSONString", jsonString);
//                        DataOutputStream os = new DataOutputStream(httpURLConnection.getOutputStream());
//                        os.writeBytes(jsonString);
//                        os.flush();
//                        os.close();
//                        Log.i("状态码：", "" + httpURLConnection.getResponseCode());
//
//                        if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//
//                            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
//                            StringBuilder response = new StringBuilder();
//                            String temp;
//                            while ((temp = bufferedReader.readLine()) != null) {
//                                response.append(temp);
//                                Log.i("Main", response.toString());
//                            }
//                            System.out.println(response);
//                            Gson gson = new Gson();
//                            Response responseinfo = gson.fromJson(String.valueOf(response), Response.class);
//                            System.out.println(responseinfo);
//                            int type = 1;
//                            //根据返回节点判断的服务类型
//                            if (TRAIN.equals(requestType)) {
//                                if (!"0000".equals(responseinfo.getBody().getTraininfo().getBackStatus())) {
//                                    type = 0;
//                                }
//                            } else if (LOGIN.equals(requestType)) {
//                                if (!"0000".equals(responseinfo.getBody().getResultinfo().getBackStatus())) {
//                                    type = 1;
//                                }
//                            } else if (MESSAGE.equals(requestType)) {
//                                if (!"0000".equals(responseinfo.getBody().getMessageInfoVo().getBackStatus())) {
//                                    type = 0;
//                                }
//                                //获取验证码
//                                vfcode[0] = responseinfo.getBody().getMessageInfoVo().getVfcode();
//                                System.err.println("vfcode[0]:" + vfcode[0]);
//                            }
//                            //根据
//                            switch (type) {
//                                case 1:
//                                    mh.sendEmptyMessage(1);
//                                    break;
//                                case 0:
//                                    mh.sendEmptyMessage(2);
//                                    break;
//                                default:
//                            }
//                        } else {
//                            mh.sendEmptyMessage(0);
//                        }
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        mh.sendEmptyMessage(0);
//                    } finally {
//                        //关闭bufferedreader
//                        if (bufferedReader != null) {
//                            try {
//                                bufferedReader.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//
//                            }
//                        }
//                        if (httpURLConnection != null) {
//                            httpURLConnection.disconnect();
//                        }
//                    }
//                }
//            }).start();
//        }
//        return vfcode;
//    }


    /**
     * 功能：发送jsonString到服务器并解析回应
     *
     * @param jsonString mh url
     *                   handler 参数规定
     *                   msg.what:
     *                   0：服务器连接失败
     *                   1：地址为空
     *                   2：连接超时
     * @return
     */
    public Response sendDataRecive(final String jsonString, final Handler mh, final URL url, final String requestType) throws InterruptedException {
        final Response[] responseinfo = {new Response()};
        if (url == null) {
            mh.sendEmptyMessage(1);
        } else {
            Thread thread1=new Thread(new Runnable() {

                @Override
                public void run() {
                    HttpURLConnection httpURLConnection = null;
                    BufferedReader bufferedReader = null;
                    try {
                        httpURLConnection = (HttpURLConnection) url.openConnection();
                        // 设置连接超时时间
                        httpURLConnection.setConnectTimeout(5 * 1000);
                        //设置从主机读取数据超时
                        httpURLConnection.setReadTimeout(5 * 1000);
                        // Post请求必须设置允许输出 默认false
                        httpURLConnection.setDoOutput(true);
                        //设置请求允许输入 默认是true
                        httpURLConnection.setDoInput(true);
                        // Post请求不能使用缓存
                        httpURLConnection.setUseCaches(false);
                        // 设置为Post请求
                        httpURLConnection.setRequestMethod("POST");
                        //设置本次连接是否自动处理重定向
                        httpURLConnection.setInstanceFollowRedirects(true);
                        // 配置请求Content-Type
                        httpURLConnection.setRequestProperty("Content-Type", "application/json");
                        //开始连接
                        httpURLConnection.connect();
                        //发送数据
                        Log.i("JSONString", jsonString);
                        DataOutputStream os = new DataOutputStream(httpURLConnection.getOutputStream());
                        os.writeBytes(jsonString);
                        os.flush();
                        os.close();
                        Log.i("状态码：", "" + httpURLConnection.getResponseCode());

                        if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                            StringBuilder response = new StringBuilder();
                            String temp;
                            while ((temp = bufferedReader.readLine()) != null) {
                                response.append(temp);
                                Log.i("Main", response.toString());
                            }
                            Gson gson = new Gson();
                            responseinfo[0] = gson.fromJson(String.valueOf(response), Response.class);
                            int type = 1;
                            //根据返回节点判断的服务类型
                            String serviceType = null;
                            if (responseinfo[0] != null) {
                                serviceType = responseinfo[0].getHead().getRequest_type();
                            }
                            //判断调用的接口类型
                            if (MESSAGE.equals(serviceType)) {
                                //成功调用接口获取到配气时间
                                if (!"0000".equals(responseinfo[0].getBody().getMessageInfoVo().getBackStatus())) {
                                    type = 0;
                                }
                            } else if (DIS.equals(serviceType)) {
                                if (!"0000".equals(responseinfo[0].getBody().getDistribution().getBackStatus())) {
                                    type = 0;
                                }
                            }

                            //根据业务场景
                            switch (type) {
                                case 1:
                                    mh.sendEmptyMessage(1);
                                    break;
                                case 0:
                                    mh.sendEmptyMessage(2);
                                    break;
                                default:
                            }
                        } else {
                            mh.sendEmptyMessage(0);
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                        mh.sendEmptyMessage(0);
                    } finally {
                        //关闭bufferedreader
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e) {
                                e.printStackTrace();

                            }
                        }
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                    }
                }
            });
            thread1.start();
            thread1.join();
        }
        Response response = responseinfo[0];
        return response;
    }
}
