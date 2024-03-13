package com.maxsella.fatmuscle.sdk.manager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.maxsella.fatmuscle.sdk.common.MxsellaConstant;
import com.maxsella.fatmuscle.sdk.fat.entity.DeviceInfo;
import com.maxsella.fatmuscle.sdk.fat.entity.DeviceUdpMsg;
import com.maxsella.fatmuscle.sdk.service.DeviceImageDataTcpService;
import com.maxsella.fatmuscle.sdk.service.DeviceUdpSerialDataService;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.videoio.Videoio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MxsellaDeviceManager {
    private static final String DATA_DEVICE_IP_KEY = "device_ip";
    private static final String DATA_DEVICE_MAC_KEY = "device_mac";
    private static final String DATA_DEVICE_MODE_KEY = "deviceMode";
    private static final String DATA_DEVICE_NAME_KEY = "deviceIdentification";
    private static final String DATA_DEVICE_SN_KEY = "device_sn";
    private static MxsellaDeviceManager deviceManager;
    private static Object obj = new Object();
    private Intent connectServiceIntent;
    private byte[] data;
    private String deviceIdentification;
    private DeviceImageDataTcpService deviceImageDataTcpService;
    private String deviceIp;
    private String deviceMac;
    private String deviceMode;
    private DeviceUdpSerialDataService deviceSerialDataService;
    private String deviceSn;
    private SharedPreferences sharedPreferences;
    private IMAGEMODE imagemode = IMAGEMODE.B_FREE;
    private IMAGETYPE imagetype = IMAGETYPE.IMAGE;
    private List<DeviceInterface> deviceInterfaces = new ArrayList();
    private List<ReceivingImageDataInterface> receivingImageDataInterfaces = new ArrayList();
    private ConcurrentHashMap<Object, DeviceResultInterface.BaseInterface> interfaceHashMap = new ConcurrentHashMap<>();
    private int IMG_WIDTH = 640;
    private int IMG_HEIGHT = Videoio.CAP_PROP_XI_CC_MATRIX_01;
    Bitmap imageBitmap = null;
    private StatusType statusType = StatusType.Normal;
    private boolean isConnected = false;
    private boolean isReConnect = false;
    private Map deviceInfos = new HashMap();
    private long msgReceiveTime = 0;
    int algoLeve = 0;

    
    public interface DeviceInterface {

        
        public enum ConnType {
            TCP,
            UDP
        }

        void onConnected(ConnType connType);

        void onDisconnected(int i, String str, ConnType connType);

//        void onTcpMessage(DeviceTcpMsg deviceTcpMsg);

        void onUdpMessage(DeviceUdpMsg deviceUdpMsg);
    }

    public boolean isConnected() {
        return false;
    }

    public void onUdpConnected() {
        DeviceUdpMsg deviceUdpMsg = new DeviceUdpMsg();
        deviceUdpMsg.setCmd(MxsellaConstant.DEVICE_UDP_CONNECTED_MSG_ID);
        EventBus.getDefault().post(deviceUdpMsg);
    }

    public void onStopped(int i, String str, DeviceInterface.ConnType connType) {

        DeviceUdpMsg deviceUdpMsg = new DeviceUdpMsg();
        deviceUdpMsg.setCmd(MxsellaConstant.DEVICE_UDP_DISCONNECTED_MSG_ID);
        deviceUdpMsg.setContent(str);
        deviceUdpMsg.setErrorCode(Integer.valueOf(i));
        EventBus.getDefault().post(deviceUdpMsg);
    }

    public void onMessage(int i, String str, int i2, DeviceInterface.ConnType connType) {
        Log.i("", "==receive: connType:" + connType + " content:" + str + " port:" + i2);
        try {
            try {
                this.isConnected = true;
                this.msgReceiveTime = System.currentTimeMillis();

                try {
                    JSONObject jSONObject = new JSONObject(str);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (i != 4261) {
                    if (i == 17476) {

                    } else if (i == 24739) {
                        this.statusType = StatusType.Upgrade;
                    } else if (i == 24741) {
                        JSONObject jSONObject2 = new JSONObject(str);
                        if (!jSONObject2.isNull("result") && jSONObject2.getString("result").contains("OK")) {

                        }
                        this.statusType = StatusType.Normal;
                    }
                }
            } catch (JsonParseException e2) {
                e2.printStackTrace();
            }
        } catch (JSONException e6) {
            e6.printStackTrace();
        }
    }

    public void setDeviceMac(String str) {
        this.deviceMac = str;
        this.sharedPreferences.edit().putString(DATA_DEVICE_MAC_KEY, str).commit();
    }

    
    public interface DeviceResultInterface {

        
        public interface BaseInterface {
        }

        
        public interface DeviceCommonInterface extends BaseInterface {
            void result(String str, boolean z);
        }

        
        public interface DeviceUpgradeInterface extends DeviceCommonInterface {
            void onUploadProgress(String str, long j, long j2);
        }
    }

    
    public enum IMAGEMODE {
        B_FREE,
        B_REAL,
        THR_FREE,
        THR_REAL,
        FOUR_FREE,
        FOUR_REAL
    }

    
    public enum IMAGETYPE {
        IMAGE,
        VIDEO
    }

    
    public interface ReceivingImageDataInterface {
        void receiveByteBitmat(byte[] bArr);

        void receiveImageBitmat(Bitmap bitmap);
    }

    public enum StatusType {
        Normal,
        Upgrade
    }

    public void onError(Exception exc) {
    }

    public Map<String, DeviceInfo> getDeviceInfos() {
        return this.deviceInfos;
    }

    public String getDeviceIdentification() {
        return this.deviceIdentification;
    }

    public String getDeviceMode() {
        return this.deviceMode;
    }

    public static MxsellaDeviceManager getInstance() {
        if (deviceManager == null) {
            synchronized (obj) {
                if (deviceManager == null) {
                    deviceManager = new MxsellaDeviceManager();
                }
            }
        }
        return deviceManager;
    }

}
