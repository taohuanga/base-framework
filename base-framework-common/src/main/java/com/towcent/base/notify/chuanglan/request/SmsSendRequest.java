package com.towcent.base.notify.chuanglan.request;

public class SmsSendRequest {
    private String account;
    private String password;
    private String msg;
    private String phone;
    private String sendtime;
    private String report;
    private String extend;
    private String uid;

    public SmsSendRequest() {
    }

    public SmsSendRequest(String account, String password, String msg,
        String phone) {
        this.account = account;
        this.password = password;
        this.msg = msg;
        this.phone = phone;
    }

    public SmsSendRequest(String account, String password, String msg,
        String phone, String report) {
        this.account = account;
        this.password = password;
        this.msg = msg;
        this.phone = phone;
        this.report = report;
    }

    public SmsSendRequest(String account, String password, String msg,
        String phone, String report, String sendtime) {
        this.account = account;
        this.password = password;
        this.msg = msg;
        this.phone = phone;
        this.sendtime = sendtime;
        this.report = report;
    }

    public SmsSendRequest(String account, String password, String msg,
        String phone, String sendtime, String report, String uid) {
        this.account = account;
        this.password = password;
        this.msg = msg;
        this.phone = phone;
        this.sendtime = sendtime;
        this.report = report;
        this.uid = uid;
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

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}
