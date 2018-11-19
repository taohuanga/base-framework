package com.towcent.base.notify.chuanglan.response;

public class SmsBalanceResponse {
    private String time;
    private String balance;
    private String errorMsg;
    private String code;

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBalance() {
        return this.balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
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

    public String toString() {
        return "SmsBalanceResponse [time=" + this.time + ", balance=" +
        this.balance + ", errorMsg=" + this.errorMsg + ", code=" + this.code +
        "]";
    }
}
