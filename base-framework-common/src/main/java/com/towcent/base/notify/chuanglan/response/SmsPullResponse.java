package com.towcent.base.notify.chuanglan.response;

import java.util.List;


public class SmsPullResponse {
    private String ret;
    private String error;
    private List<Result> result;

    public String getRet() {
        return this.ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Result> getResult() {
        return this.result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    static class Result {
        private String moTime;
        private String spCode;
        private String mobile;
        private String destCode;
        private String messageContent;

        public String getMoTime() {
            return this.moTime;
        }

        public void setMoTime(String moTime) {
            this.moTime = moTime;
        }

        public String getSpCode() {
            return this.spCode;
        }

        public void setSpCode(String spCode) {
            this.spCode = spCode;
        }

        public String getMobile() {
            return this.mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getDestCode() {
            return this.destCode;
        }

        public void setDestCode(String destCode) {
            this.destCode = destCode;
        }

        public String getMessageContent() {
            return this.messageContent;
        }

        public void setMessageContent(String messageContent) {
            this.messageContent = messageContent;
        }
    }
}
