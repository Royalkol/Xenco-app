package com.example.xc_nonapplication.request;

import com.example.xc_nonapplication.Vo.LoginInfoVo;
import com.example.xc_nonapplication.Vo.MessageInfoVo;
import com.example.xc_nonapplication.Vo.PhoneInfoVo;
import com.example.xc_nonapplication.Vo.TreatInfoVo;

/**
 * 作者：Royal
 * <p>
 * 日期: 2020/5/18 15:10
 */
public class Body {

    private LoginInfoVo logininfo;
    private PhoneInfoVo phoneInfo;
    private MessageInfoVo messageInfo;
    private TreatInfoVo treatinfo;


    public LoginInfoVo getLogininfo() {
        return logininfo;
    }

    public void setLogininfo(LoginInfoVo logininfo) {
        this.logininfo = logininfo;
    }

    public PhoneInfoVo getPhoneInfo() {
        return phoneInfo;
    }

    public void setPhoneInfo(PhoneInfoVo phoneInfo) {
        this.phoneInfo = phoneInfo;
    }

    public MessageInfoVo getMessageInfo() {
        return messageInfo;
    }

    public void setMessageInfo(MessageInfoVo messageInfo) {
        this.messageInfo = messageInfo;
    }

    public TreatInfoVo getTreatinfo() {
        return treatinfo;
    }

    public void setTreatinfo(TreatInfoVo treatinfo) {
        this.treatinfo = treatinfo;
    }
}
