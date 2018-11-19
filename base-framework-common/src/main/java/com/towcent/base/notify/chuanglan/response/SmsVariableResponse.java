package com.towcent.base.notify.chuanglan.response;

public class SmsVariableResponse {
    private String time;
    private String msgId;
    private String errorMsg;
    private String failNum;
    private String successNum;
    private String code;

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMsgId() {
        return this.msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFailNum() {
        return this.failNum;
    }

    public void setFailNum(String failNum) {
        this.failNum = failNum;
    }

    public String getSuccessNum() {
        return this.successNum;
    }

    public void setSuccessNum(String successNum) {
        this.successNum = successNum;
    }

    public String toString() {
        return "SmsVarableResponse [time=" + this.time + ", msgId=" +
        this.msgId + ", errorMsg=" + this.errorMsg + ", failNum=" +
        this.failNum + ", successNum=" + this.successNum + ", code=" +
        this.code + "]";
    }
}
