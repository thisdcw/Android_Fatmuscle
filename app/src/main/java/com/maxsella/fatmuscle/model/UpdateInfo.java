package com.maxsella.fatmuscle.model;

import java.io.Serializable;

public class UpdateInfo implements Serializable {

    private int id;
    private String key;
    private String time;
    private int versionCode;
    private String versionName;
    private boolean forceUpdate;
    private String downLoadUrl;
    private String log;

    public UpdateInfo(int id, String key, String time, int versionCode, String versionName, boolean forceUpdate, String downLoadUrl, String log) {
        this.id = id;
        this.key = key;
        this.time = time;
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.forceUpdate = forceUpdate;
        this.downLoadUrl = downLoadUrl;
        this.log = log;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public boolean getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getDownLoadUrl() {
        return downLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    @Override
    public String toString() {
        return "UpdateInfo{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", time='" + time + '\'' +
                ", versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", forceUpdate='" + forceUpdate + '\'' +
                ", downLoadUrl='" + downLoadUrl + '\'' +
                ", log='" + log + '\'' +
                '}';
    }
}
