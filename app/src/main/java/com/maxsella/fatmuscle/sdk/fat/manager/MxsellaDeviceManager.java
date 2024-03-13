package com.maxsella.fatmuscle.sdk.fat.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.maxsella.fatmuscle.common.MyApplication;
import com.maxsella.fatmuscle.entity.BLEDevice;
import com.maxsella.fatmuscle.entity.DeviceDefaultParams;
import com.maxsella.fatmuscle.inter.BleScanResultCallback;
import com.maxsella.fatmuscle.sdk.common.Constant;
import com.maxsella.fatmuscle.sdk.common.MxsellaConstant;
import com.maxsella.fatmuscle.sdk.fat.entity.BitmapMsg;
import com.maxsella.fatmuscle.sdk.fat.entity.DeviceMsg;
import com.maxsella.fatmuscle.sdk.fat.entity.FirmwareUpdateMsg;
import com.maxsella.fatmuscle.sdk.fat.entity.FlashMsg;
import com.maxsella.fatmuscle.sdk.fat.entity.ResultMsg;
import com.maxsella.fatmuscle.sdk.fat.entity.VersionMsg;
import com.maxsella.fatmuscle.sdk.fat.inter.DataTransListerner;
import com.maxsella.fatmuscle.sdk.fat.interfaces.DeviceResultInterface;
import com.maxsella.fatmuscle.sdk.fat.utils.BitmapUtil;
import com.maxsella.fatmuscle.sdk.fat.utils.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class MxsellaDeviceManager implements DataTransListerner {

    private static final String ACTION_USB_DETACHED_PERMISSION = "android.hardware.usb.action.USB_DEVICE_DETACHED";
    private static final String ACTION_USB_DEVICE_ATTACHED = "android.hardware.usb.action.USB_DEVICE_ATTACHED";
    private static final String ACTION_USB_PERMISSION = "com.template.USB_PERMISSION";
    private static final String CUR_SEL_DEVICE_CODE = "cur_device_code";
    private static final String DATA_DEVICE_DATA_RATE_KEY = "blue_speed_rate";
    private static final String DATA_DEVICE_FLASH_KEY = "device_sn";
    private static final String DATA_DEVICE_MODE_KEY = "deviceMode";
    private static final String DATA_DEVICE_NAME_KEY = "deviceIdentification";
    private static final String DATA_DEVICE_VERSION_KEY = "deviceVersion";
    private static final String TAG = "MxsellaDeviceManager";
    public static final int UNKOWN_DEVICE_CODE = -1;
    public static final int Z1_DEVICE_CODE = 0;
    public static final int Z2_DEVICE_CODE = 1;
    private static Object obj = new Object();
    private static MxsellaDeviceManager sMxsellaDeviceManager;
    private int blueSpeedRateLeve;
    private String compileTime;
    private int curDeviceCode;
    private String deviceIdentification;
    private String deviceMode;
    private int deviceVersion;
    private String flashId;
    private BitmapMsg mBitmapMsg;
    private BleScanResultCallback mBleScanResultCallback;
    private Context mContext;
    private DisCoverBleInterface mDisCoverBleInterface;
    private OTGManager mOTGManager;
    private BitmapMsg.State mImageState = BitmapMsg.State.START;
    private List<DeviceInterface> deviceInterfaces = new CopyOnWriteArrayList();
    private int ocxo = 32;
    private ConcurrentHashMap<Object, DeviceResultInterface> interfaceHashMap = new ConcurrentHashMap<>();
    private boolean isEnd = false;
    BitmapMsg bitmapMsg = null;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.marvoto.fat.manager.MxsellaDeviceManager.5
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Log.i(MxsellaDeviceManager.TAG, "onReceive: " + intent);
            if (MxsellaDeviceManager.this.curDeviceCode == 1) {
                return;
            }
            if (intent.getAction().equals(MxsellaDeviceManager.ACTION_USB_DETACHED_PERMISSION)) {
                if (MxsellaDeviceManager.this.mOTGManager.isConnect()) {
                    MxsellaDeviceManager.this.disConnectDevice();
                }
            } else if (!intent.getAction().equals(MxsellaDeviceManager.ACTION_USB_DEVICE_ATTACHED) || MxsellaDeviceManager.this.mOTGManager.isConnect()) {
            } else {
                MxsellaDeviceManager.this.connectDevice();
            }
        }
    };
    public void setEnd(boolean z) {
        this.isEnd = z;
    }
    public void unregisterDeviceInterface(DeviceInterface deviceInterface) {
        this.deviceInterfaces.remove(deviceInterface);
    }

    public void connectDevice() {
        //去除蓝牙连接部分
        int i = this.curDeviceCode;
        if (i == 0) {
            this.mOTGManager.initUsbDevice();
        }
    }
    public int getCurDeviceCode() {
        return this.curDeviceCode;
    }
    public void registerDeviceInterface(DeviceInterface deviceInterface) {
        this.deviceInterfaces.add(deviceInterface);
    }

    public void setDeviceDefaultParams(int i, DeviceResultInterface deviceResultInterface) {
        DeviceDefaultParams depthAndGainParamByLeve = FatConfigManager.getInstance().getDepthAndGainParamByLeve(i);
        Log.i(TAG, "==DataLength=" + BitmapUtil.sBitmapHight + "=====Depth=" + depthAndGainParamByLeve.getDepth() + "===Gain=" + depthAndGainParamByLeve.getGain() + "==Sendcycle=" + depthAndGainParamByLeve.getSendcycle() + "==Dynamic=" + depthAndGainParamByLeve.getDynamic());
        setDeviceDepth(depthAndGainParamByLeve.getDepth().intValue(), deviceResultInterface);
        setDeviceGain(depthAndGainParamByLeve.getGain().intValue(), deviceResultInterface);
        if (this.curDeviceCode == 0) {
            setDeviceSendCycle(depthAndGainParamByLeve.getSendcycle().intValue(), deviceResultInterface);
            setDeviceDynamic(depthAndGainParamByLeve.getDynamic().intValue(), deviceResultInterface);
        }
    }

    public void setDeviceDepth(int i, DeviceResultInterface deviceResultInterface) {
        if (checkDeviceConnectStatus(deviceResultInterface, MxsellaConstant.DEVICE_DLPF_M_VALUE)) {
            sendCmd(MxsellaConstant.DEVICE_DLPF_M_VALUE, i);
            if (1 == this.curDeviceCode) {
                int i2 = (i * BitmapUtil.sBitmapHight * 2) + 165 + 24;

                sendCmd(MxsellaConstant.DEVICE_RXATE_DELAY, new byte[]{0, -91, (byte) ((i2 >> 8) & 255), (byte) (i2 & 255)});
            }
        }
    }

    public void setDeviceGain(int i, DeviceResultInterface deviceResultInterface) {
        if (checkDeviceConnectStatus(deviceResultInterface, MxsellaConstant.DEVICE_DLPF_PARA)) {
            sendCmd(MxsellaConstant.DEVICE_DLPF_PARA, i);
        }
    }

    private void sendCmd(int i, int i2) {
        if (this.mOTGManager.isConnect()) {
            this.mOTGManager.send(i, i2);
        }
    }

    private void sendCmd(int i, byte[] bArr) {
        if (this.mOTGManager.isConnect()) {
            this.mOTGManager.send(i, bArr);
        }
    }

    public void setDeviceSendCycle(int i, DeviceResultInterface deviceResultInterface) {
        int i2 = 0;
        int i3;
        if (checkDeviceConnectStatus(deviceResultInterface, MxsellaConstant.DEVICE_LINE_CYCLE)) {
            if (this.curDeviceCode == 0) {
                if (this.deviceVersion >= 20) {
                    i2 = this.ocxo;
                } else {
                    i3 = (this.ocxo * 10 * i) | 6553600;
                    sendCmd(MxsellaConstant.DEVICE_LINE_CYCLE, i3);
                }
            } else {
                i2 = this.ocxo;
            }
            i3 = i2 * i * 1000;
            sendCmd(MxsellaConstant.DEVICE_LINE_CYCLE, i3);
        }
    }

    public void setDeviceDynamic(int i, DeviceResultInterface deviceResultInterface) {
        if (checkDeviceConnectStatus(deviceResultInterface, MxsellaConstant.DEVICE_DR_PARA)) {
            sendCmd(MxsellaConstant.DEVICE_DR_PARA, ((64 - (7650 / i)) << 16) | (23029 / i));
        }
    }

    private boolean checkDeviceConnectStatus(DeviceResultInterface deviceResultInterface, int i) {
        if (!isConnect()) {
            if (deviceResultInterface != null) {
                deviceResultInterface.result(false, null);
            }
            return false;
        } else if (deviceResultInterface != null) {
            this.interfaceHashMap.put(Integer.valueOf(i), deviceResultInterface);
            return true;
        } else {
            return true;
        }
    }

    public boolean isConnect() {
        return this.mOTGManager.isConnect();
    }

    public int getOcxo() {
        return this.ocxo;
    }

    public int getBlueSpeedRateLeve() {
        return 0;
    }

    public boolean isToBusinessVersion() {
        String str = this.deviceMode;
        return str != null && "130".equalsIgnoreCase(str);
    }

    public void disConnectDevice() {
        //去除断开蓝牙连接部分
        if (this.mOTGManager.isConnect()) {
            this.mOTGManager.closeSession();
        }
    }

    public static MxsellaDeviceManager getInstance() {
        if (sMxsellaDeviceManager == null) {
            synchronized (obj) {
                if (sMxsellaDeviceManager == null) {
                    sMxsellaDeviceManager = new MxsellaDeviceManager();
                }
            }
        }
        return sMxsellaDeviceManager;
    }

    public interface DisCoverBleInterface {
        void disBlueConnected(BLEDevice bLEDevice);

        void onBlueConnected(BLEDevice bLEDevice);

        void onFindBlueDevice(List<BLEDevice> list);
    }

    public interface DeviceInterface {
        void onConnected();

        void onDisconnected(int i, String str);

        void onMessage(DeviceMsg deviceMsg);
    }

    public void setFlashId(String str) {
        this.flashId = str;
        //TODO 本地保存 DATA_DEVICE_FLASH_KEY
        SharedPreferencesUtil.savaString(this.mContext, DATA_DEVICE_FLASH_KEY, str);
        com.maxsella.fatmuscle.sdk.manager.MxsellaDeviceManager.getInstance().setDeviceMac(str);
    }

    public void setDeviceMode(String str) {
        this.deviceMode = str;
        //TODO 本地保存 DATA_DEVICE_MODE_KEY
        SharedPreferencesUtil.savaString(this.mContext, DATA_DEVICE_MODE_KEY, str);
    }

    public void notityMessage(DeviceMsg deviceMsg) {
        EventBus.getDefault().post(deviceMsg);
    }

    public void setDeviceIdentification(String str) {
        this.deviceIdentification = str;
        //TODO 本地保存 DATA_DEVICE_NAME_KEY
        SharedPreferencesUtil.savaString(this.mContext, DATA_DEVICE_NAME_KEY, str);
    }

    public void setDeviceVersion(int i) {
        this.deviceVersion = i;
        //TODO 本地保存 DATA_DEVICE_VERSION_KEY
        SharedPreferencesUtil.saveInt(this.mContext, DATA_DEVICE_VERSION_KEY, i);
    }

    @Override
    public void onCmdMessage(int i, byte[] bArr, int i2, ProtocolType protocolType) {
        if (i != -1) {
            if (i != 432) {
                if (i != 4277) {
                    if (i != 4295) {
                        if (i == 34952) {
                            setFlashId(null);
                            setDeviceMode(null);
                            DeviceMsg deviceMsg = new DeviceMsg();
                            deviceMsg.setMsgId(i);
                            deviceMsg.setProtocolType(protocolType);
                            notityMessage(deviceMsg);
                            return;
                        } else if (i != 4272) {
                            if (i == 4273) {
                                FlashMsg flashMsg = new FlashMsg();
                                flashMsg.unpack(bArr);
                                flashMsg.setMsgId(i);
                                flashMsg.setError(i2);
                                notityMessage(flashMsg);
                                setFlashId(flashMsg.getUuid());
                                return;
                            }
                            switch (i) {
                                case Constant.DEVICE_DLPF_M_VALUE /* 4288 */:
                                case Constant.DEVICE_DLPF_PARA /* 4289 */:
                                case Constant.DEVICE_CUT_POINTS /* 4290 */:
                                case Constant.DEVICE_DR_PARA /* 4291 */:
                                case Constant.DEVICE_LINE_NUM /* 4292 */:
                                case Constant.DEVICE_LINE_CYCLE /* 4293 */:
                                    break;
                                default:
                                    DeviceMsg deviceMsg2 = new DeviceMsg();
                                    deviceMsg2.setMsgId(i);
                                    deviceMsg2.setError(i2);
                                    deviceMsg2.setContentArray(bArr);
                                    notityMessage(deviceMsg2);
                                    return;
                            }
                        }
                    }
                    DeviceMsg resultMsg = new ResultMsg();
                    resultMsg.unpack(bArr);
                    resultMsg.setMsgId(i);
                    resultMsg.setError(i2);
                    notityMessage(resultMsg);
                    return;
                }
                DeviceMsg firmwareUpdateMsg = new FirmwareUpdateMsg();
                firmwareUpdateMsg.setMsgId(Constant.DEVICE_FIRMWARE_UPDATE);
                firmwareUpdateMsg.setError(i2);
                firmwareUpdateMsg.unpack(bArr);
                notityMessage(firmwareUpdateMsg);
                return;
            }
            VersionMsg versionMsg = new VersionMsg();
            versionMsg.setProtocolType(protocolType);
            versionMsg.unpack(bArr);
            versionMsg.setMsgId(i);
            versionMsg.setError(i2);
            this.compileTime = (protocolType == DataTransListerner.ProtocolType.BLE ? Integer.valueOf(versionMsg.getYear()) : "20" + versionMsg.getYear()) + "/" + versionMsg.getMonth() + "/" + versionMsg.getDay();
            if (this.curDeviceCode == 0) {
                setDeviceMode(versionMsg.getMode() + "");
            }
            if (versionMsg.getIdentificationCode() > 0) {
                setDeviceIdentification(versionMsg.getIdentificationCode() + "");
            }
            setDeviceVersion(versionMsg.getVersionNumber());
            if (protocolType == DataTransListerner.ProtocolType.BLE && (versionMsg.getVersionNumber() == 255 || i2 != 8)) {
                setDeviceVersion(0);
                this.compileTime = "";
            }
            notityMessage(versionMsg);
            return;
        }
        DeviceMsg deviceMsg3 = new DeviceMsg();
        deviceMsg3.setMsgId(i);
        deviceMsg3.setProtocolType(protocolType);
        notityMessage(deviceMsg3);
    }

    @Override
    public void onImageData(byte[] bArr, BitmapMsg.State state, ProtocolType protocolType) {
        if (this.isEnd && state == BitmapMsg.State.RUN) {
            return;
        }
        if (state == BitmapMsg.State.START) {
            this.isEnd = false;
        }
        BitmapUtil.sBitmapHight = OTGManager.BITMAP_WIDTH;
        this.ocxo = protocolType == DataTransListerner.ProtocolType.BLE ? 40 : 32;
        BitmapMsg bitmapMsg = new BitmapMsg();
        this.bitmapMsg = bitmapMsg;
        bitmapMsg.setProtocolType(protocolType);
        this.bitmapMsg.unpack(bArr);
        this.bitmapMsg.setContentArray(bArr);
        this.bitmapMsg.setState(state);
        this.bitmapMsg.setMsgId(4261);
        this.mBitmapMsg = this.bitmapMsg;
        if (state == BitmapMsg.State.START) {
            BitmapUtil.initList();
        } else {
            BitmapMsg.State state2 = BitmapMsg.State.END;
        }
        notityMessage(this.bitmapMsg);

    }
}
