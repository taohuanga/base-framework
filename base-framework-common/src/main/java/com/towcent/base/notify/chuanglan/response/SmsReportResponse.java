package com.towcent.base.notify.chuanglan.response;

import java.util.List;


public class SmsReportResponse {
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
        private String msgId;
        private String reportTime;
        private String mobile;
        private String status;
        private String statusDesc;
        private String count;

        public String getMsgId() {
            return this.msgId;
        }

        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }

        public String getReportTime() {
            return this.reportTime;
        }

        public void setReportTime(String reportTime) {
            this.reportTime = reportTime;
        }

        public String getMobile() {
            return this.mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatusDesc() {
            return this.statusDesc;
        }

        public void setStatusDesc(String statusDesc) {
            this.statusDesc = statusDesc;
        }

        public String getCount() {
            return this.count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }
}
