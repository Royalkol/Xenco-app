package com.example.xc_nonapplication.request;

/**
 * 作者：Royal
 * <p>
 * 日期: 2020/5/18 15:10
 */
public class Head {
    private String sys_code;
    private String appkeymd5;
    private String time_tick;
    private String service_type;
    private String key;

    public String getSys_code() {
        return sys_code;
    }

    public void setSys_code(String sys_code) {
        this.sys_code = sys_code;
    }

    public String getAppkeymd5() {
        return appkeymd5;
    }

    public void setAppkeymd5(String appkeymd5) {
        this.appkeymd5 = appkeymd5;
    }

    public String getTime_tick() {
        return time_tick;
    }

    public void setTime_tick(String time_tick) {
        this.time_tick = time_tick;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
