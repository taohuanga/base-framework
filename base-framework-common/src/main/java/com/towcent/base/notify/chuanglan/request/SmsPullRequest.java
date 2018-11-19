package com.towcent.base.notify.chuanglan.request;

public class SmsPullRequest {
    private String account;
    private String password;
    private String count;

    public SmsPullRequest() {
    }

    public SmsPullRequest(String account, String password, String count) {
        this.account = account;
        this.password = password;
        this.count = count;
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

    public String getCount() {
        return this.count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
