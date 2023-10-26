package com.maxsella.fatmuscle.common.util;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class DeviceIdUtil {


    private static DeviceIdUtil instance;
    private final String TAG = "DeviceIdUtils";

    private DeviceIdUtil() {
    }

    public static DeviceIdUtil getInstance() {
        if (instance == null) {
            synchronized (DeviceIdUtil.class) {
                instance = new DeviceIdUtil();
            }
        }
        return instance;
    }

    public static String getDeviceId(Context context) {
        if (TextUtils.isEmpty(Config.deviceId)) {
            Config.deviceId = Config.mainSP.getString(Config.Tag_MAIN_SP.deviceId, "");
            if (TextUtils.isEmpty(Config.deviceId)) {
                String mac = getWifiMac(context);
                Log.d("deviceId", "deviceId mac=" + mac);
                if (mac != null && !TextUtils.isEmpty(mac)) {
                    mac = mac.replaceAll(" ", "");// 去掉空格
                    mac = mac.replaceAll(":", "");// 去掉冒号
                    mac = Long.parseLong(mac, 16) + "";
                    Config.deviceId = mac.substring(mac.length() - 9, mac.length());
                    Log.d("deviceId", "deviceId deviceId = " + Config.deviceId);
                    Config.mainSP.edit().putString(Config.Tag_MAIN_SP.deviceId, Config.deviceId).apply();
                } else {
                    String cid = readCid();
                    if (cid != null && !TextUtils.isEmpty(cid)) {
                        String str = (Long.parseLong(cid.substring(cid.length() - 15, cid.length()), 16) + "");
                        Config.deviceId = str.substring(str.length() - 10, str.length());
                        Log.d("deviceId", "deviceId deviceId = " + Config.deviceId);
                        Config.mainSP.edit().putString(Config.Tag_MAIN_SP.deviceId, Config.deviceId).apply();
                    } else {
                        Config.deviceId = "12345678900";
                        Log.d("deviceId", "deviceId deviceId = " + Config.deviceId);
                        Config.mainSP.edit().putString(Config.Tag_MAIN_SP.deviceId, Config.deviceId).apply();
                    }
                }
            }
        }
        return Config.deviceId;
    }

    public static String getWifiMac(Context context) {
        String mac = "";
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        boolean isWifiEnabled = wifi.isWifiEnabled();
        if (!isWifiEnabled) {
            boolean enabled = wifi.setWifiEnabled(true);
            Log.e("test", "wifiManager.isWifiEnabled()=false  wifiManager.setWifiEnabled(true) = " + enabled);

        }
        int count = 0;
        while (TextUtils.isEmpty(mac) && count < 30) {
            count++;
            mac = getWifiMacAddr();
            if (mac == null)
                mac = "";

            SystemClock.sleep(500);
        }

        if (!isWifiEnabled) {
            wifi.setWifiEnabled(false);
            Log.e("test", "wifiManager.isWifiEnabled()=false  关闭===========");
        }
        return mac;
    }

    public static String getWifiMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0"))
                    continue;
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return null;
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return null;
    }

    private static String readCid() {
        String str1 = null;
        Object localOb;
        try {
            localOb = new FileReader("/sys/block/mmcblk0/device/cid"); // nand ID
            str1 = new BufferedReader((Reader) localOb).readLine();
            Log.v("deviceId", "deviceId " + str1);
        } catch (Exception e1) {
            e1.printStackTrace();
            str1 = "";
        }
        return str1;
    }

    public static String readTemperature() {
        String str1 = "";
        Object localOb;
        try {
            localOb = new FileReader("/sys/devices/ff280000.tsadc/temp1_input"); // nand ID
            str1 = new BufferedReader((Reader) localOb).readLine();
            Log.v("test", "readTemperature " + str1);
        } catch (Exception e1) {
            e1.printStackTrace();
            str1 = "";
        }
        return str1;
    }
}
