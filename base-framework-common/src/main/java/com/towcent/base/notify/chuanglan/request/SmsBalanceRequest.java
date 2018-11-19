package com.towcent.base.notify.chuanglan.request;

public class SmsBalanceRequest {
    private String account;
    private String password;

    public SmsBalanceRequest() {
    }

    public SmsBalanceRequest(String account, String password) {
        this.account = account;
        this.password = password;
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
}
