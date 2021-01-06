package com.example.xc_nonapplication.response;

/**
 * 作者：Royal
 * <p>
 * 日期: 2020/6/1 08:49
 */
public class ResponseMessage {
    private String backStatus;

    private String backmessage;

    private String vfcode;

    public String getBackStatus() {
        return backStatus;
    }

    public void setBackStatus(String backStatus) {
        this.backStatus = backStatus;
    }

    public String getBackmessage() {
        return backmessage;
    }

    public void setBackmessage(String backmessage) {
        this.backmessage = backmessage;
    }

    public String getVfcode() {
        return vfcode;
    }

    public void setVfcode(String vfcode) {
        this.vfcode = vfcode;
    }
}
