package com.example.xc_nonapplication.response;

/**
 * 作者：Royal
 * <p>
 * 日期: 2020/5/19 09:15
 */
public class Body {
    private ResponseLogin resultinfo;
    private ResponseTrainInfo traininfo;
    private ResponseMessage messageInfoVo;

    public ResponseTrainInfo getTraininfo() {
        return traininfo;
    }

    public void setTraininfo(ResponseTrainInfo traininfo) {
        this.traininfo = traininfo;
    }

    public ResponseLogin getResultinfo() {
        return resultinfo;
    }

    public void setResultinfo(ResponseLogin resultinfo) {
        this.resultinfo = resultinfo;
    }

    public ResponseMessage getMessageInfoVo() {
        return messageInfoVo;
    }

    public void setMessageInfoVo(ResponseMessage messageInfoVo) {
        this.messageInfoVo = messageInfoVo;
    }
}

