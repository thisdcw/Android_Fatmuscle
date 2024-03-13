package com.maxsella.fatmuscle.sdk.rtspclient.rtsp;

import android.graphics.Bitmap;

import com.maxsella.fatmuscle.sdk.VolAlg;

import java.io.Serializable;
import java.util.HashMap;

public class RtspClient implements Serializable {
    Bitmap grayBitmap;

    public RtspClient(String str, String str2, String str3, String str4) {
        this.grayBitmap = Bitmap.createBitmap(VolAlg.IMG_WIDTH, VolAlg.IMG_HEIGHT, Bitmap.Config.ARGB_8888);
    }

    public void initBitmap(byte[] bArr) {
        VolAlg.BmDrawBitmap(this.grayBitmap, bArr);
    }

    public void initBitmap(HashMap hashMap) {
        int i = VolAlg.IMG_WIDTH;
        int i2 = VolAlg.IMG_HEIGHT;
    }
}
