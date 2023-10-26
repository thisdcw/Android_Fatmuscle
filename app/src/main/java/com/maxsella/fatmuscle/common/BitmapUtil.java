package com.maxsella.fatmuscle.common;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.maxsella.fatmuscle.common.util.DetectionResultModel;
import com.maxsella.fatmuscle.common.util.FileUtil;
import com.maxsella.fatmuscle.common.util.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.opencv.features2d.*;
import kotlin.Suppress;

public class BitmapUtil {
    public static Bitmap addFreezzOrLiveIcon(Bitmap bitmap, Bitmap bitmap2) {
        int max = Math.max(bitmap.getWidth(), bitmap2.getWidth());
        Bitmap createBitmap = Bitmap.createBitmap(max, Math.max(bitmap.getHeight(), bitmap2.getHeight()), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        canvas.drawBitmap(bitmap2, max / 6f, 10.0f, (Paint) null);
        return createBitmap;
    }

    public static Bitmap addLandscape2Bitmap(Bitmap bitmap, Bitmap bitmap2) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth() + bitmap2.getWidth(), Math.max(bitmap.getHeight(), bitmap2.getHeight()), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        canvas.drawBitmap(bitmap2, bitmap.getWidth() - 10, 0.0f, (Paint) null);
        return createBitmap;
    }

    public static Bitmap addPortrait2Bitmap(Bitmap bitmap, Bitmap bitmap2) {
        Bitmap createBitmap = Bitmap.createBitmap(Math.max(bitmap.getWidth(), bitmap2.getWidth()), bitmap.getHeight() + bitmap2.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, bitmap.getWidth() / 2f, 0.0f, (Paint) null);
        canvas.drawBitmap(bitmap2, 0.0f, bitmap.getHeight(), (Paint) null);
        return createBitmap;
    }

    public static Bitmap addTimePerHPortrait2Bitmap(Bitmap bitmap, Bitmap bitmap2) {
        Bitmap createBitmap = Bitmap.createBitmap(Math.max(bitmap.getWidth(), bitmap2.getWidth()), bitmap.getHeight() + bitmap2.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        canvas.drawBitmap(bitmap2, 0.0f, bitmap.getHeight(), (Paint) null);
        return createBitmap;
    }

    public static Bitmap addDepthBitmap(Bitmap bitmap, Bitmap bitmap2) {
        int width = bitmap.getWidth() + 20;
        Bitmap createBitmap = Bitmap.createBitmap(width, Math.max(bitmap.getHeight(), bitmap2.getHeight()), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        canvas.drawBitmap(bitmap2, width - bitmap2.getWidth(), 0.0f, (Paint) null);
        return createBitmap;
    }

    public static Bitmap addSampleBitmap(Bitmap bitmap, Bitmap bitmap2) {
        Bitmap createBitmap = Bitmap.createBitmap(Math.max(bitmap.getWidth(), bitmap2.getWidth()), Math.max(bitmap.getHeight(), bitmap2.getHeight()), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        canvas.drawBitmap(bitmap2, 0.0f, 0.0f, (Paint) null);
        return createBitmap;
    }

    public static Bitmap addLeftDepthBitmap(Bitmap bitmap, Bitmap bitmap2) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth() + 20, Math.max(bitmap.getHeight(), bitmap2.getHeight()), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 10.0f, 0.0f, (Paint) null);
        canvas.drawBitmap(bitmap2, 0.0f, 0.0f, (Paint) null);
        return createBitmap;
    }

    public static Bitmap detectByAiBitmap(Bitmap bitmap, List<DetectionResultModel> list) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        if (list.size() > 0) {
            Paint paint = new Paint();
            paint.setColor(Color.parseColor("#3B85F5"));
            paint.setStrokeWidth(5.0f);
            paint.setStyle(Paint.Style.STROKE);
            Paint paint2 = new Paint();
            paint2.setStyle(Paint.Style.FILL);
            paint2.setColor(Color.parseColor("#3B85F5"));
            paint2.setAlpha(50);
            Paint paint3 = new Paint();
            paint3.setStyle(Paint.Style.FILL);
            paint3.setColor(Color.parseColor("#3B85F5"));
            for (DetectionResultModel detectionResultModel : list) {
                LogUtil.i("===============AI=======" + detectionResultModel.getBounds());
                Rect bounds = detectionResultModel.getBounds();
                canvas.drawRect(bounds, paint);
                canvas.drawRect(bounds, paint2);
                canvas.drawRect(new Rect(bounds.left, bounds.top, bounds.right, bounds.top), paint3);
            }
        }
        return createBitmap;
    }

    public static void saveFileByBitmap(Bitmap bitmap, String str) {
        File file = new File(FileUtil.getCacheDir("raw").getPath() + "/" + str + ".png");
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream)) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
