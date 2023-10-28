package com.maxsella.fatmuscle.db.bean;

import com.maxsella.fatmuscle.common.util.TimeDateUtil;

import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class Record extends LitePalSupport {

    private int id;

    private int familyId;
    private String nickname;
    private Date date;
    private String mode;
    private String item;
    private String thickness;
    private String img;
    private boolean ifUpload;

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", familyId=" + familyId +
                ", nickname='" + nickname + '\'' +
                ", date='" + date + '\'' +
                ", mode='" + mode + '\'' +
                ", item='" + item + '\'' +
                ", thickness='" + thickness + '\'' +
                ", img='" + img + '\'' +
                ", ifUpload=" + ifUpload +
                '}';
    }

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDate() {
//        return date;
        return TimeDateUtil.formatDateString(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getThickness() {
        return thickness;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isIfUpload() {
        return ifUpload;
    }

    public void setIfUpload(boolean ifUpload) {
        this.ifUpload = ifUpload;
    }
}
