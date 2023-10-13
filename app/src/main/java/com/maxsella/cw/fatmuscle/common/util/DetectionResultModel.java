package com.maxsella.cw.fatmuscle.common.util;

import android.graphics.Rect;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class DetectionResultModel extends BaseResultModel{

    private Rect d;

    public DetectionResultModel() {
    }

    public DetectionResultModel(String str, float f, Rect rect) {
        super(str, f);
        this.d = rect;
    }

    public Rect getBounds() {
        return this.d;
    }

    public void setBounds(Rect rect) {
        this.d = rect;
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = super.toJsonObject();
        try {
            jsonObject.put("bounds_left", this.d.left);
            jsonObject.put("bounds_top", this.d.top);
            jsonObject.put("bounds_right", this.d.right);
            jsonObject.put("bounds_bottom", this.d.bottom);
        } catch (JSONException e) {
            Log.e("DetectionResultModel", "json serialize error", e);
        }
        return jsonObject;
    }

    public String toString() {
        return "[" + this.b + "][" + this.c + "][" + this.d.toString() + "]";
    }
}
