package com.example.xc_nonapplication.util;

import android.os.Handler;

import com.example.xc_nonapplication.Vo.DistributionInfoVo;
import com.example.xc_nonapplication.Vo.LoginInfoVo;
import com.example.xc_nonapplication.Vo.MessageInfoVo;
import com.example.xc_nonapplication.Vo.PhoneInfoVo;
import com.example.xc_nonapplication.Vo.TreatInfoVo;
import com.example.xc_nonapplication.request.Body;
import com.example.xc_nonapplication.request.Head;
import com.example.xc_nonapplication.request.RequsetInfo;
import com.example.xc_nonapplication.response.Response;
import com.example.xc_nonapplication.response.ResponseDistribution;
import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 作者：Royal
 * 网络通讯的工具类
 * 日期: 2020/5/21 08:37
 */
public class EsbUtil {

//    //服务端地址
//    private static final String URL_SERVER = "http://192.168.1.105:8848/xenco/getXencoInfo";
//    //http://localhost:8080/message/verifyCode
//    private static final String URL_MESSAGE = "http://192.168.1.105:8080/message/verifyCode";

    /**
     * 请求服务到longin
     */
    public void longinService(LoginInfoVo loginfoVo, Handler handler) {
        OperateData operateData = new OperateData();
        RequsetInfo requsetInfo = new RequsetInfo();
        Head head = new Head();
        head.setService_type("LOGIN");
        Body body = new Body();
        body.setLogininfo(loginfoVo);
        requsetInfo.setHead(head);
        requsetInfo.setBody(body);
        String jsonString = new Gson().toJson(requsetInfo);
        URL url = null;
        try {
            url = new URL(ConfigUtil.URL_SERVER);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        operateData.sendData(jsonString, handler, url, head.getService_type());
    }


    /**
     * 请求服务到Train_service
     */
    public void trainService(PhoneInfoVo phoneInfo, Handler handler) {
        OperateData operateData = new OperateData();
        RequsetInfo requsetInfo = new RequsetInfo();
        Head head = new Head();
        head.setService_type("TRAIN_NUMBER");
        Body body = new Body();
        body.setPhoneInfo(phoneInfo);
        requsetInfo.setHead(head);
        requsetInfo.setBody(body);
        String jsonString = new Gson().toJson(requsetInfo);
        URL url = null;
        try {
            url = new URL(ConfigUtil.URL_MESSAGE);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        operateData.sendData(jsonString, handler, url, head.getService_type());
    }

    /**
     * 请求服务到mesaage_service
     */
    public Response MessageService(MessageInfoVo messageInfoVo, Handler handler) throws InterruptedException {
        OperateData operateData = new OperateData();
        RequsetInfo requsetInfo = new RequsetInfo();
        Head head = new Head();
        head.setService_type("SENDMSSAGE");
        Body body = new Body();
        body.setMessageInfo(messageInfoVo);
        requsetInfo.setHead(head);
        requsetInfo.setBody(body);
        String jsonString = new Gson().toJson(requsetInfo);
        URL url = null;
        try {
            url = new URL(ConfigUtil.URL_MESSAGE);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Response response = operateData.sendDataRecive(jsonString, handler, url, head.getService_type());
        return response;
    }


    /**
     * 请求服务到Train_service
     */
    public void treatService(TreatInfoVo treatInfoVo, Handler handler) {
        OperateData operateData = new OperateData();
        RequsetInfo requsetInfo = new RequsetInfo();
        Head head = new Head();
        head.setService_type("TREATINFO");
        Body body = new Body();
        body.setTreatinfo(treatInfoVo);
        requsetInfo.setHead(head);
        requsetInfo.setBody(body);
        String jsonString = new Gson().toJson(requsetInfo);
        URL url = null;
        try {
            url = new URL(ConfigUtil.URL_SERVER);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        operateData.sendData(jsonString, handler, url, head.getService_type());
    }


    /**
     * 请求服务到distribution
     */
    public ResponseDistribution DistributionService(DistributionInfoVo distributionInfoVo, Handler handler) throws InterruptedException {
        ResponseDistribution responseDistribution =null;
        OperateData operateData = new OperateData();
        RequsetInfo requsetInfo = new RequsetInfo();
        Head head = new Head();
        head.setService_type("DISTRIBUTION");
        Body body = new Body();
        body.setDistributioninfo(distributionInfoVo);
        requsetInfo.setHead(head);
        requsetInfo.setBody(body);
        String jsonString = new Gson().toJson(requsetInfo);
        URL url = null;
        try {
            url = new URL(ConfigUtil.URL_SERVER);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Response response = operateData.sendDataRecive(jsonString, handler, url, head.getService_type());

        if (response!=null){
            responseDistribution =response.getBody().getDistribution();
        }
        return responseDistribution;
    }
}
