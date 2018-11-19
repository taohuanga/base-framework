package com.towcent.base.common.model;

import java.io.Serializable;
import java.util.Date;


public class TaioHeacheckData implements Serializable{

    private static final long serialVersionUID = 5393963643852463058L;

    private String id;

    private String healthfileNo;

    private String fileNumber;

    private String sample;

    private String P05;

    private String N05;

    private String Duration;

    private String anal;

    private String zjanal;

    private String doctor;

    private String ECG_I;

    private String ECG_II;

    private String ECG_III;

    private String ECG_aVR;

    private String ECG_aVF;

    private String ECG_aVL;

    private String ECG_V1;

    private String ECG_V2;

    private String ECG_V3;

    private String ECG_V4;

    private String ECG_V5;

    private String ECG_V6;

    private String dataXml;

    private Date checkDate;

    private String name;

    private String orgName;

    private String doctorId;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getAnal() {
        return anal;
    }

    public void setAnal(String anal) {
        this.anal = anal;
    }

    public String getZjanal() {
        return zjanal;
    }

    public void setZjanal(String zjanal) {
        this.zjanal = zjanal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHealthfileNo() {
        return healthfileNo;
    }

    public void setHealthfileNo(String healthfileNo) {
        this.healthfileNo = healthfileNo;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public String getP05() {
        return P05;
    }

    public void setP05(String p05) {
        P05 = p05;
    }

    public String getN05() {
        return N05;
    }

    public void setN05(String n05) {
        N05 = n05;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getECG_I() {
        return ECG_I;
    }

    public void setECG_I(String eCG_I) {
        ECG_I = eCG_I;
    }

    public String getECG_II() {
        return ECG_II;
    }

    public void setECG_II(String eCG_II) {
        ECG_II = eCG_II;
    }

    public String getECG_III() {
        return ECG_III;
    }

    public void setECG_III(String eCG_III) {
        ECG_III = eCG_III;
    }

    public String getECG_aVR() {
        return ECG_aVR;
    }

    public void setECG_aVR(String eCG_aVR) {
        ECG_aVR = eCG_aVR;
    }

    public String getECG_aVF() {
        return ECG_aVF;
    }

    public void setECG_aVF(String eCG_aVF) {
        ECG_aVF = eCG_aVF;
    }

    public String getECG_aVL() {
        return ECG_aVL;
    }

    public void setECG_aVL(String eCG_aVL) {
        ECG_aVL = eCG_aVL;
    }

    public String getECG_V1() {
        return ECG_V1;
    }

    public void setECG_V1(String eCG_V1) {
        ECG_V1 = eCG_V1;
    }

    public String getECG_V2() {
        return ECG_V2;
    }

    public void setECG_V2(String eCG_V2) {
        ECG_V2 = eCG_V2;
    }

    public String getECG_V3() {
        return ECG_V3;
    }

    public void setECG_V3(String eCG_V3) {
        ECG_V3 = eCG_V3;
    }

    public String getECG_V4() {
        return ECG_V4;
    }

    public void setECG_V4(String eCG_V4) {
        ECG_V4 = eCG_V4;
    }

    public String getECG_V5() {
        return ECG_V5;
    }

    public void setECG_V5(String eCG_V5) {
        ECG_V5 = eCG_V5;
    }

    public String getECG_V6() {
        return ECG_V6;
    }

    public void setECG_V6(String eCG_V6) {
        ECG_V6 = eCG_V6;
    }

    public String getDataXml() {
        return dataXml;
    }

    public void setDataXml(String dataXml) {
        this.dataXml = dataXml;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }
}
