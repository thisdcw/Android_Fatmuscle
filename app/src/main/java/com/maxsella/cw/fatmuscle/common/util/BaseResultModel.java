package com.maxsella.cw.fatmuscle.common.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseResultModel implements Comparable<BaseResultModel> {
    protected int a;
    protected String b;
    protected float c;

    public BaseResultModel() {
    }

    public BaseResultModel(int i, float f) {
        this.a = i;
        this.c = f;
    }

    public BaseResultModel(String str, float f) {
        this.b = str;
        this.c = f;
    }

    @Override
    public int compareTo(BaseResultModel baseResultModel) {
//        return Float.valueOf(baseResultModel.getConfidence()).compareTo(Float.valueOf(this.c));
        return Float.compare(baseResultModel.getConfidence(), this.c);
    }

    public float getConfidence() {
        return this.c;
    }

    public String getLabel() {
        return this.b;
    }

    public int getLableIndex() {
        return this.a;
    }

    public void setConfidence(float f) {
        this.c = f;
    }

    public void setLabel(String str) {
        this.b = str;
    }

    public void setLableIndex(int i) {
        this.a = i;
    }

    public JSONObject toJsonObject() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("label", this.b);
            jSONObject.put("confidence", this.c);
        } catch (JSONException e) {
            Log.e("BaseResultModel", "json serialize error", e);
        }
        return jSONObject;
    }
}

