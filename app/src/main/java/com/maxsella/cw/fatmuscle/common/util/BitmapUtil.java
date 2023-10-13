package com.maxsella.cw.fatmuscle.common.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;
import java.util.Locale;

public class BitmapUtil {
    public static String customTagPrefix = "x_log";

    public static String generateTag() {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[2];
        String className = stackTraceElement.getClassName();
        String format = String.format(Locale.CHINA, "%s.%s(L:%d)", className.substring(className.lastIndexOf(".") + 1), stackTraceElement.getMethodName(), stackTraceElement.getLineNumber());
        return TextUtils.isEmpty(customTagPrefix) ? format : customTagPrefix + ":" + format;
    }

    /*
    *
        1. 创建一个画笔 `paint`，并设置画笔颜色为蓝色（`Color.parseColor("#3B85F5")`），设置笔画宽度为 5.0f，以及画笔样式为描边（`Paint.Style.STROKE`）。

        2. 创建第二个画笔 `paint2`，设置画笔样式为填充（`Paint.Style.FILL`），颜色与第一个画笔相同，但透明度降低（`setAlpha(50)`），以实现半透明效果。

        3. 创建第三个画笔 `paint3`，样式为填充，颜色同样为蓝色。

        接下来，它遍历检测结果列表中的每个元素 `detectionResultModel`，每个元素都包含一个矩形边界框 `bounds`。

        对于每个检测结果，它在画布上绘制了三个矩形：
        - 一个描边矩形，用来显示边界框的轮廓，颜色为蓝色。
        - 一个半透明的填充矩形，与描边矩形重叠，用来强调边界框的区域。
        - 一个非常细的横线，位于边界框的顶部，用来表示某种特殊标记（可能是检测的结果的某个属性）。
    * */
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
                Rect bounds = detectionResultModel.getBounds();
                canvas.drawRect(bounds, paint);
                canvas.drawRect(bounds, paint2);
                canvas.drawRect(new Rect(bounds.left, bounds.top, bounds.right, bounds.top), paint3);
            }
        }
        return createBitmap;
    }
}
