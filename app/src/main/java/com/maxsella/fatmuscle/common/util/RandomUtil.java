package com.maxsella.fatmuscle.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

public class RandomUtil {
    private static final Random random = new Random();

    public static String imitateData() {
        return String.format(Locale.CHINA, "%.1f", random.nextDouble() * 2)+"cm"; // 生成0到2的随机小数
    }


    public static Date imitateDate() {
        int startYear = 2000;
        int endYear = 2022;

        int year = startYear + random.nextInt(endYear - startYear + 1);
        int month = 1 + random.nextInt(12);
        int maxDay = getMaxDayForMonth(year, month);
        int day = 1 + random.nextInt(maxDay);
        int hour = random.nextInt(24);
        int minute = random.nextInt(60);
        int second = random.nextInt(60);

        Calendar calendar = new GregorianCalendar(year, month - 1, day, hour, minute, second);
        return calendar.getTime();
    }

    private static int getMaxDayForMonth(int year, int month) {
        Calendar calendar = new GregorianCalendar(year, month - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static String formatDateString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}
