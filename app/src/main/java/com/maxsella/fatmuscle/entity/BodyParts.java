package com.maxsella.fatmuscle.entity;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class BodyParts implements Serializable {
    private int curveMarkeIcon;
    private Integer guide;
    private Integer guideTip;
    private Integer guideTitle;
    private Integer guideVideo;
    private Drawable icon;
    private Integer iconNormal;
    private Integer iconSelect;
    private Integer index;
    private int[] leveArray;
    private Integer musclePartIcon;
    private Integer musclePartTip;
    private Integer[] muscleStep1Guide;
    private Integer[] muscleStep2Guide;
    private Integer[] muscleStep3Guide;
    private Integer[] muscleStep4Guide;
    private String name;
    private int tabIndex;
    private int maxThicknessValue = 55;
    private int maxThinValue = 6;
    private int maxNormalValue = 35;

    public Integer getIndex() {
        return this.index;
    }

    public void setIndex(Integer num) {
        this.index = num;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public Drawable getIcon() {
        return this.icon;
    }

    public void setIcon(Drawable drawable) {
        this.icon = drawable;
    }

    public Integer getGuideVideo() {
        return this.guideVideo;
    }

    public void setGuideVideo(Integer num) {
        this.guideVideo = num;
    }

    public Integer getGuideTitle() {
        return this.guideTitle;
    }

    public void setGuideTitle(Integer num) {
        this.guideTitle = num;
    }

    public Integer[] getMuscleStep1Guide() {
        return this.muscleStep1Guide;
    }

    public void setMuscleStep1Guide(Integer[] numArr) {
        this.muscleStep1Guide = numArr;
    }

    public Integer[] getMuscleStep2Guide() {
        return this.muscleStep2Guide;
    }

    public void setMuscleStep2Guide(Integer[] numArr) {
        this.muscleStep2Guide = numArr;
    }

    public Integer[] getMuscleStep3Guide() {
        return this.muscleStep3Guide;
    }

    public void setMuscleStep3Guide(Integer[] numArr) {
        this.muscleStep3Guide = numArr;
    }

    public Integer[] getMuscleStep4Guide() {
        return this.muscleStep4Guide;
    }

    public void setMuscleStep4Guide(Integer[] numArr) {
        this.muscleStep4Guide = numArr;
    }

    public Integer getMusclePartTip() {
        return this.musclePartTip;
    }

    public void setMusclePartTip(Integer num) {
        this.musclePartTip = num;
    }

    public Integer getMusclePartIcon() {
        return this.musclePartIcon;
    }

    public void setMusclePartIcon(Integer num) {
        this.musclePartIcon = num;
    }

    public Integer getGuide() {
        return this.guide;
    }

    public void setGuide(Integer num) {
        this.guide = num;
    }

    public Integer getGuideTip() {
        return this.guideTip;
    }

    public void setGuideTip(Integer num) {
        this.guideTip = num;
    }

    public Integer getIconNormal() {
        return this.iconNormal;
    }

    public void setIconNormal(Integer num) {
        this.iconNormal = num;
    }

    public Integer getIconSelect() {
        return this.iconSelect;
    }

    public int[] getLeveArray() {
        return this.leveArray;
    }

    public void setLeveArray(int[] iArr) {
        this.leveArray = iArr;
    }

    public void setIconSelect(Integer num) {
        this.iconSelect = num;
    }

    public int getMaxThicknessValue() {
        return this.maxThicknessValue;
    }

    public void setMaxThicknessValue(int i) {
        this.maxThicknessValue = i;
    }

    public int getMaxThinValue() {
        return this.maxThinValue;
    }

    public void setMaxThinValue(int i) {
        this.maxThinValue = i;
    }

    public int getMaxNormalValue() {
        return this.maxNormalValue;
    }

    public void setMaxNormalValue(int i) {
        this.maxNormalValue = i;
    }

    public int getTabIndex() {
        return this.tabIndex;
    }

    public void setTabIndex(int i) {
        this.tabIndex = i;
    }

    public int getCurveMarkeIcon() {
        return this.curveMarkeIcon;
    }

    public void setCurveMarkeIcon(int i) {
        this.curveMarkeIcon = i;
    }

}
