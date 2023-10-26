package com.maxsella.fatmuscle.common.util;

public class UnitConversionUtil {

    public static double cmToIn(double d) {
        return d * 0.393700787d;
    }

    public static double inToCm(double d) {
        return d * 2.54d;
    }

    public static double kgToLib(double d) {
        return d * 2.2046226d;
    }

    public static double libTokg(double d) {
        return d * 0.45359237d;
    }

    public static double mmToCm(double d) {
        return d / 10.0d;
    }

    public static double mmToIn(double d) {
        return d * 0.0393701d;
    }

    public static String cmToFtAndIn(double d) {
        StringBuilder stringBuffer = new StringBuilder();
        int i = (int) (d / 30.48d);
        if (i > 0) {
            stringBuffer.append(i).append("ft");
        }
        int round = (int) Math.round((d % 30.48d) / 2.54d);
        if (round > 0) {
            stringBuffer.append(round).append("in");
        }
        return stringBuffer.toString();
    }

    public static String getValueStr(double d) {
        return "";
    }

}
