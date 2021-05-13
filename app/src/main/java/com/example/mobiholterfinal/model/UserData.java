package com.example.mobiholterfinal.model;

public class UserData {

    private int mID;
    private String mName;
    private String mTime;
    private String ecg_channel_1;
    private String ecg_channel_2;
    private String ecg_channel_3;
    private String heart_rate;
    private String uro_data;


    public UserData() {
    }

    public UserData(String mName, String mTime, String ecg_channel_1, String ecg_channel_2, String ecg_channel_3) {
        this.ecg_channel_1 = ecg_channel_1;
        this.ecg_channel_2 = ecg_channel_2;
        this.ecg_channel_3 = ecg_channel_3;
        this.mTime = mTime;
        this.mName= mName;
    }

    public UserData(String mName, String mTime, String ecg_channel_1, String ecg_channel_2, String ecg_channel_3, String heart_rate, String uro_data) {
        this.mName = mName;
        this.mTime = mTime;
        this.ecg_channel_1 = ecg_channel_1;
        this.ecg_channel_2 = ecg_channel_2;
        this.ecg_channel_3 = ecg_channel_3;
        this.heart_rate = heart_rate;
        this.uro_data = uro_data;
    }

    public UserData(int mID, String mName, String mTime, String ecg_channel_1, String ecg_channel_2, String ecg_channel_3, String heart_rate, String uro_data) {
        this.mID = mID;
        this.mName = mName;
        this.mTime = mTime;
        this.ecg_channel_1 = ecg_channel_1;
        this.ecg_channel_2 = ecg_channel_2;
        this.ecg_channel_3 = ecg_channel_3;
        this.heart_rate = heart_rate;
        this.uro_data = uro_data;
    }

    public String getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(String heart_rate) {
        this.heart_rate = heart_rate;
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getEcg_channel_1() {
        return ecg_channel_1;
    }

    public void setEcg_channel_1(String ecg_channel_1) {
        this.ecg_channel_1 = ecg_channel_1;
    }

    public String getEcg_channel_2() {
        return ecg_channel_2;
    }

    public void setEcg_channel_2(String ecg_channel_2) {
        this.ecg_channel_2 = ecg_channel_2;
    }

    public String getEcg_channel_3() {
        return ecg_channel_3;
    }

    public void setEcg_channel_3(String ecg_channel_3) {
        this.ecg_channel_3 = ecg_channel_3;
    }

    public String getUro_data() {
        return uro_data;
    }

    public void setUro_data(String uro_data) {
        this.uro_data = uro_data;
    }
}
