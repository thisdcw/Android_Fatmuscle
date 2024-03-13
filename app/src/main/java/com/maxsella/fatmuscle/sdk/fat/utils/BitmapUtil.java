package com.maxsella.fatmuscle.sdk.fat.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;

import com.maxsella.fatmuscle.common.util.Constant;
import com.maxsella.fatmuscle.sdk.fat.manager.OTGManager;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class BitmapUtil {
    public static long frame = 0;
    private static final ArrayList<byte[]> mLinkedList = new ArrayList<>();
    public static int sBitmapHight = OTGManager.BITMAP_WIDTH;
    public static byte[] bytePx = new byte[OTGManager.BITMAP_WIDTH * 150];

    public static final void initList() {
        bytePx = new byte[sBitmapHight * 150];
        mLinkedList.clear();
        for (int i = 0; i < 150; i++) {
            int i2 = sBitmapHight;
            byte[] bArr = new byte[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                bArr[i3] = 0;
                bytePx[(i3 * 150) + i] = 0;
            }
            mLinkedList.add(bArr);
        }
    }

    public static final String screenShots(View view) {
        String str = Constant.APP_DIR_PATH + "/image/";
        File file = new File(str + "1.png");
        if (!file.exists()) {
            file.delete();
        }
        File file2 = new File(str);
        if (!file2.exists()) {
            file2.mkdirs();
        }
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap createBitmap = Bitmap.createBitmap(view.getDrawingCache());
        if (createBitmap != null) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(str + "1.png");
                boolean compress = createBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                view.setDrawingCacheEnabled(false);
                view.destroyDrawingCache();
                if (compress) {
                    return str + "1.png";
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static final Bitmap getImageOneDimensional() {
        int i = sBitmapHight * 150;
        int[] iArr = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = bytePx[i2] & 255;
            iArr[i2] = Color.argb(255, i3, i3, i3);
        }
        return Bitmap.createBitmap(iArr, 150, sBitmapHight, Bitmap.Config.ARGB_8888);
    }

    public static final synchronized Bitmap add(byte[] bArr) {
        Bitmap createBitmap;
        synchronized (BitmapUtil.class) {
            ArrayList<byte[]> arrayList = mLinkedList;
            arrayList.remove(0);
            arrayList.add(bArr);
            frame++;
            int[] iArr = new int[sBitmapHight * 150];
            for (int i = 0; i < 150; i++) {
                byte[] bArr2 = mLinkedList.get(i);
                for (int i2 = 0; i2 < sBitmapHight; i2++) {
                    int i3 = bArr2[i2] & 255;
                    int i4 = (i2 * 150) + i;
                    iArr[i4] = Color.argb(255, i3, i3, i3);
                    bytePx[i4] = bArr2[i2];
                }
            }
            createBitmap = Bitmap.createBitmap(iArr, 150, sBitmapHight, Bitmap.Config.ARGB_8888);
        }
        return createBitmap;
    }

}
