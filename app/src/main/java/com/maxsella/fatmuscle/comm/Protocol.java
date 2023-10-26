package com.maxsella.fatmuscle.comm;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Protocol {

    public static final short HEAD = 0x5AA5; // 帧头
    public static final byte DEVICE_TO_CLIENT = (byte) 0x82;
    public static final byte CLIENT_TO_DEVICE = (byte) 0x83;


//    public static byte[] SELECT_LINE = Protocol.hexToByteArray("5AA506831001010001");


    public static int toInt(byte[] bytes) {
        int len = bytes.length;
        byte[] byteArray = Arrays.copyOfRange(bytes, len - 2, len);
        int intValue = 0;

        for (int i = 0; i < byteArray.length; i++) {
            intValue <<= 8;  // 左移8位
            intValue |= (byteArray[i] & 0xFF);
        }
        return intValue;
    }

    /**
     * 包编码
     */
    public static byte[] encode(short address, short command) {
        // 数据长度 来源:1字节 地址:2字节 几个地址的数据: 1 字节  命令: 2字节
        byte len = 1 + 2 + 1 + 2;
        ByteBuffer buffer = ByteBuffer.allocate(9);
        buffer.putShort(HEAD); // 2字节头部
        buffer.put(len); // 1字节数据长度
        buffer.put(CLIENT_TO_DEVICE); // 客户端 到 硬件
        buffer.putShort(address); // 地址
        buffer.put((byte) 1); // 地址个数
        buffer.putShort(command); // 命令
        return buffer.array();
    }

    public static String gbkBytesToShow(byte[] gbkBytes) {

        StringBuilder format = new StringBuilder();
        for (byte gbkByte : gbkBytes) {
            format.append(String.format("%02X", gbkByte));
        }
        String result = format.toString();
        try {
            return new String(hexToByteArray(result), "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";  // 返回空字符串表示解码失败
        }
    }


    /**
     * short转字节数组
     */
    public static byte[] short2Bytes(short num) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) ((num >> 8) & 0xFF);
        bytes[1] = (byte) num;
        return bytes;
    }

    /**
     * short数组转字节数组
     */
    public static byte[] shorts2Bytes(short[] shorts) {
        byte[] bytes = new byte[shorts.length * 2];
        byte[] temp = null;
        int i = 0;
        for (short s : shorts) {
            temp = short2Bytes(s);
            bytes[i++] = temp[0];
            bytes[i++] = temp[1];
        }
        return bytes;
    }

    /**
     * 是否相等
     */
    public static boolean endWith(byte[] src, byte[] target) {
        if (target.length == 0) return false;
        if (src.length < target.length) return false;
        for (int i = 0; i < target.length; i++) {
            if (target[target.length - i - 1] != src[src.length - i - 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字节数组转int
     */
    public static int bytesToInt(byte[] bytes) {
        int res = 0;
        for (int i = 0; i < bytes.length; i++) {
            res = (res << 8) | (bytes[i] & 0xff);
        }
        return res;
    }

    /**
     * Hex字符串转byte
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte
     */
    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    /**
     * hex字符串转byte数组
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte数组结果
     */
    public static byte[] hexToByteArray(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            //奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            //偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    public static String bytesToHex(byte[] bytes) {
        String strHex = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < bytes.length; n++) {
            strHex = Integer.toHexString(bytes[n] & 0xFF);
            sb.append((strHex.length() == 1) ? "0" + strHex : strHex); // 每个字节由两个字符表示，位数不够，高位补0
        }
        return sb.toString().trim();
    }
}
