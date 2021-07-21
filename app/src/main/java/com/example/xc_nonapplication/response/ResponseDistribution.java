package com.example.xc_nonapplication.response;

/**
 * Created by Royal on 2021/7/12.
 * Describle:
 */
public class ResponseDistribution {

    /**
     * 返回信息* 成功或者失败
     */
    private String backMessage;

    /**
     * 0000成功
     * 0001失败
     */

    private String backStatus;

    private int oxygenTime;
    private int xenonTime;

    public String getBackMessage() {
        return backMessage;
    }

    public void setBackMessage(String backMessage) {
        this.backMessage = backMessage;
    }

    public String getBackStatus() {
        return backStatus;
    }

    public void setBackStatus(String backStatus) {
        this.backStatus = backStatus;
    }

    public int getOxygenTime() {
        return oxygenTime;
    }

    public void setOxygenTime(int oxygenTime) {
        this.oxygenTime = oxygenTime;
    }

    public int getXenonTime() {
        return xenonTime;
    }

    public void setXenonTime(int xenonTime) {
        this.xenonTime = xenonTime;
    }
}
