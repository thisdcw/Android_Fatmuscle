package com.maxsella.fatmuscle.common.util;

public class ByteUtil {
    private static final String HEX = "0123456789abcdef";

    public ByteUtil() {
    }

    public static StringBuffer bytesToHex(byte[] byteArray) {
        StringBuffer stringBuffer = new StringBuffer(byteArray.length * 2);
        for (byte b : byteArray) {
            int v = b & 0xFF;
            stringBuffer.append(HEX.charAt(v >> 4));
            stringBuffer.append(HEX.charAt(v & 0x0F));
            stringBuffer.append(" ");
        }
        return stringBuffer;
    }

    public static String convertString(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = str.length() - 2; i >= 0; i -= 2) {
            String substring = str.substring(i, i + 2);
            stringBuffer.append(substring);
        }
        return stringBuffer.toString();
    }

    public static byte[] dataConversion(byte[] byteArray) {
        int length = byteArray.length * 2;
        byte[] newByteArray = new byte[length];
        for (int i = 0, j = 0; i < byteArray.length; i++) {
            newByteArray[j++] = (byte) ((byteArray[i] >> 4) & 0xff);
            newByteArray[j++] = (byte) (byteArray[i] & 0xff);
        }
        return newByteArray;
    }

    public static int getInt(byte[] byteArray, int index) {
        int result = (byteArray[index] & 0xff) << 24 |
                (byteArray[index + 1] & 0xff) << 16 |
                (byteArray[index + 2] & 0xff) << 8 |
                (byteArray[index + 3] & 0xff);
        return result;
    }

    public static int getIntByShort(byte[] byteArray, int index) {
        int result = (byteArray[index] & 0xff) << 8 | (byteArray[index + 1] & 0xff);
        return result;
    }

    public static byte[] hexStringToByteArray(String str) {
        int len = str.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4)
                    + Character.digit(str.charAt(i + 1), 16));
        }
        return data;
    }

    public static byte[] intToByteArray(int value) {
        byte[] byteArray = new byte[4];
        byteArray[0] = (byte) (value >> 24);
        byteArray[1] = (byte) (value >> 16);
        byteArray[2] = (byte) (value >> 8);
        byteArray[3] = (byte) value;
        return byteArray;
    }

    public static boolean pingIpAddress(String ip) {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("ping -c 1 -W 1 " + ip);
            int exitValue = process.waitFor();
            if (exitValue == 0) {
                return true;
            }
        } catch (Exception e) {
            // handle exceptions
        }
        return false;
    }
}
