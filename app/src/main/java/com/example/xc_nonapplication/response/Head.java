package com.example.xc_nonapplication.response;

/**
 * 作者：Royal
 * <p>
 * 日期: 2020/5/19 09:14
 */
public class Head {
    String error_code;
    String error_message;
    String request_type;
    String sys_code;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public String getRequest_type() {
        return request_type;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }

    public String getSys_code() {
        return sys_code;
    }

    public void setSys_code(String sys_code) {
        this.sys_code = sys_code;
    }
}
