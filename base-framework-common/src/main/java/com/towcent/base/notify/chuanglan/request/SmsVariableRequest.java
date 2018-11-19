package com.towcent.base.notify.chuanglan.request;

public class SmsVariableRequest {
    private String account;
    private String password;
    private String msg;
    private String params;
    private String sendtime;
    private String report;
    private String extend;
    private String uid;

    public SmsVariableRequest() {
    }

    public SmsVariableRequest(String account, String password, String msg,
        String params) {
        this.account = account;
        this.password = password;
        this.msg = msg;
        this.params = params;
    }

    public SmsVariableRequest(String account, String password, String msg,
        String params, String report) {
        this.account = account;
        this.password = password;
        this.msg = msg;
        this.params = params;
        this.report = report;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSendtime() {
        return this.sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getReport() {
        return this.report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getExtend() {
        return this.extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getParams() {
        return this.params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
