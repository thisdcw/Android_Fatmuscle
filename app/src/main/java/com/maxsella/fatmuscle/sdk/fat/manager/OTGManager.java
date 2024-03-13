package com.maxsella.fatmuscle.sdk.fat.manager;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.SystemClock;
import android.util.Log;

import com.maxsella.fatmuscle.sdk.common.Constant;
import com.maxsella.fatmuscle.sdk.fat.entity.BitmapMsg;
import com.maxsella.fatmuscle.sdk.fat.entity.DeviceMsg;
import com.maxsella.fatmuscle.sdk.fat.entity.FirmwareUpdateMsg;
import com.maxsella.fatmuscle.sdk.fat.inter.DataTransListerner;
import com.maxsella.fatmuscle.sdk.fat.interfaces.IDataTransfer;

import org.opencv.videoio.Videoio;

import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class OTGManager implements IDataTransfer {
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    public static final int OCXO = 32;
    private HashMap<String, UsbDevice> deviceList;
    private int fimwareAllSize;
    private int fimwareCurrentSize;
    private PendingIntent intent;
    private Context mContext;
    private DataTransListerner mDataTransListerner;
    private ReceiveThread mReceiveThread;
    private SendThread mSendThread;
    private UsbDevice usbDevice;
    private UsbEndpoint usbEpIn;
    private UsbInterface usbInterface;
    private UsbManager usbManager;
    private boolean isRunning = false;
    private Queue<DeviceMsg> msgsQueue = new ConcurrentLinkedQueue();
    private Queue<DeviceMsg> fimwareQueue = new ConcurrentLinkedQueue();
    private long currentTime = 0;
    private boolean isRequestPermission = false;
    private boolean isFimwareFlag = false;

    public static final int BITMAP_WIDTH = 496;
    private static final int MAGIC_NUMBER = 9188;
    private static final String TAG = "OTGManager";
    private UsbDeviceConnection deviceConnection;
    private UsbEndpoint usbEpOut;

    BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i(OTGManager.TAG, "onReceive: " + action);
            if (OTGManager.ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    Log.i(OTGManager.TAG, "onReceive: 111111111111");
                    UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra("device");
                    if (intent.getBooleanExtra("permission", false)) {
                        if (usbDevice.getVendorId() == 1027 && usbDevice.getProductId() == 24596) {
                            OTGManager.this.usbDevice = usbDevice;
                        }
                        OTGManager.this.findInterface();
                        OTGManager.this.start();
                    }
                }
            }
        }
    };
    private boolean isSendFirmwareData = false;

    public interface OTGManagerListerner {
        void onImageData(byte[] bArr, BitmapMsg.State state);

        void onMessage(int i, byte[] bArr, int i2);
    }
    public void setOTGManagerListerner(DataTransListerner dataTransListerner) {
        this.mDataTransListerner = dataTransListerner;
    }

    public void send(DeviceMsg deviceMsg) {
        if (this.isRunning) {
            this.msgsQueue.add(deviceMsg);
            return;
        }
        DataTransListerner dataTransListerner = this.mDataTransListerner;
        if (dataTransListerner != null) {
            dataTransListerner.onCmdMessage(deviceMsg.getMsgId(), new byte[4], 1, DataTransListerner.ProtocolType.OTG);
        }
    }

    public void send(int i, int i2) {
        DeviceMsg deviceMsg = new DeviceMsg();
        deviceMsg.setMsgId(i);
        deviceMsg.setContent(i2);
        send(deviceMsg);
    }

    public void send(int i, byte[] bArr) {
        DeviceMsg deviceMsg = new DeviceMsg();
        deviceMsg.setMsgId(i);
        deviceMsg.setContentArray(bArr);
        send(deviceMsg);
    }

    public void findInterface() {
        UsbDevice usbDevice = this.usbDevice;
        if (usbDevice == null) {
            return;
        }
        if (usbDevice.getInterfaceCount() > 0) {
            UsbInterface usbInterface = this.usbDevice.getInterface(0);
            this.usbInterface = usbInterface;
            Log.e(TAG, usbInterface.toString());
        }
        getEndpoint(this.usbInterface);
        if (this.usbInterface != null) {
            if (this.usbManager.hasPermission(this.usbDevice)) {
                Log.e(TAG, "已经获得权限");
                UsbDeviceConnection openDevice = this.usbManager.openDevice(this.usbDevice);
                Log.e(TAG, openDevice == null ? "true" : "false");
                if (openDevice == null) {
                    Log.e(TAG, "设备连接为空");
                } else if (openDevice != null && openDevice.claimInterface(this.usbInterface, true)) {
                    this.deviceConnection = openDevice;
                    Log.e(TAG, openDevice != null ? "false" : "true");
                } else {
                    openDevice.close();
                }
            } else if (this.isRequestPermission) {
            } else {
                this.isRequestPermission = true;
                this.usbManager.requestPermission(this.usbDevice, this.intent);
            }
        }
    }

    private void getEndpoint(UsbInterface usbInterface) {
        Log.e(TAG, "getEndpoint1: " + usbInterface.getEndpointCount());
        for (int endpointCount = usbInterface.getEndpointCount() - 1; endpointCount >= 0; endpointCount--) {
            UsbEndpoint endpoint = usbInterface.getEndpoint(endpointCount);
            Log.e(TAG, "getEndpoint: " + endpoint.getType());
            if (endpoint.getType() == 2) {
                if (endpoint.getDirection() == 0) {
                    this.usbEpOut = endpoint;
                } else {
                    this.usbEpIn = endpoint;
                }
            }
        }
    }

    public boolean start() {
        if (!this.isRunning && this.deviceConnection != null) {
            SendThread sendThread = this.mSendThread;
            if (sendThread != null && sendThread.isRunning) {
                this.mSendThread.close();
            }
            this.mSendThread = new SendThread();
            ReceiveThread receiveThread = this.mReceiveThread;
            if (receiveThread != null && receiveThread.isRunning) {
                this.mReceiveThread.close();
            }
            this.mReceiveThread = new ReceiveThread();
            this.isRunning = true;
            this.mSendThread.start();
            this.mReceiveThread.start();
        }
        if (this.isRunning) {
            DataTransListerner dataTransListerner = this.mDataTransListerner;
            if (dataTransListerner != null) {
                dataTransListerner.onCmdMessage(Constant.DEVICE_CONNECTED_MSG_ID, new byte[0], 0, DataTransListerner.ProtocolType.OTG);
            }
        } else {
            DataTransListerner dataTransListerner2 = this.mDataTransListerner;
            if (dataTransListerner2 != null) {
                dataTransListerner2.onCmdMessage(-1, new byte[0], 0, DataTransListerner.ProtocolType.OTG);
            }
        }
        return this.isRunning;
    }

    @Override
    public void onSendTimeOut(int i, DeviceMsg deviceMsg) {

    }

    @Override
    public void sendRestransmissionData(DeviceMsg deviceMsg) {
        Log.i(TAG, "sendRestransmissionData: " + deviceMsg.getMsgId());
        sendDatas(deviceMsg);

    }

    public boolean sendDatas(DeviceMsg deviceMsg) {
        if (this.deviceConnection == null) {
            return false;
        }
        byte[] protocolBytes = deviceMsg.getProtocolBytes();
        int bulkTransfer = this.deviceConnection.bulkTransfer(this.usbEpOut, protocolBytes, protocolBytes.length, 3000);
        Log.i(TAG, "sendDatas: result:" + bulkTransfer);
        return bulkTransfer > 0;
    }

    public class ReceiveThread extends Thread {
        private boolean isRunning;

        private ReceiveThread() {
            this.isRunning = true;
        }

        public void close() {
            this.isRunning = false;
        }

        @Override
        public void run() {
            //TODO
        }
    }

    public class SendThread extends Thread {
        private boolean isRunning;

        private SendThread() {
            this.isRunning = true;
        }

        public void close() {
            this.isRunning = false;
            interrupt();
        }

        @Override
        public void run() {
            int currentSendNum;
            super.run();
            Log.i(OTGManager.TAG, "run: SendThread");
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int i = 0;
            while (this.isRunning) {
                if (OTGManager.this.fimwareQueue.isEmpty() || !OTGManager.this.isSendFirmwareData) {
                    if (SystemClock.uptimeMillis() - OTGManager.this.currentTime > 50) {
                        if (!OTGManager.this.msgsQueue.isEmpty()) {
                            Log.i(OTGManager.TAG, "run: " + (SystemClock.uptimeMillis() - OTGManager.this.currentTime));
                            DeviceMsg deviceMsg = (DeviceMsg) OTGManager.this.msgsQueue.poll();
                            deviceMsg.setLastSendTime(SystemClock.uptimeMillis());
                            Log.i(OTGManager.TAG, "send: " + Integer.toHexString(deviceMsg.getMsgId()));
                            OTGManager.this.sendDatas(deviceMsg);
                        }
                    } else {
                        Log.i(OTGManager.TAG, "run: senderror");
                    }
                    i++;
                    if (i > 3) {
                        if (!OTGManager.this.checkIsConnectDevice()) {
                            OTGManager.this.closeSession();
                        }
                        i = 0;
                    }
                    try {
                        Thread.sleep(60L);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                } else {
                    DeviceMsg deviceMsg2 = (DeviceMsg) OTGManager.this.fimwareQueue.poll();
                    deviceMsg2.setCurrentSendNum(Videoio.CAP_AVFOUNDATION);
                    OTGManager.this.isFimwareFlag = false;
                    if (((FirmwareUpdateMsg) deviceMsg2).getFirmwareFlag() == 16) {
                        OTGManager oTGManager = OTGManager.this;
                        oTGManager.fimwareAllSize = oTGManager.fimwareQueue.size();
                        OTGManager.this.fimwareCurrentSize = 0;
                        OTGManager.this.mDataTransListerner.onCmdMessage(Constant.DEVICE_FIRMWARE_UPDATE, new byte[4], 16, DataTransListerner.ProtocolType.OTG);
                    } else {
                        OTGManager oTGManager2 = OTGManager.this;
                        oTGManager2.fimwareCurrentSize = oTGManager2.fimwareAllSize - OTGManager.this.fimwareQueue.size();
                    }
                    Log.i("", "=====================Send=fimwareCurrentSize=" + OTGManager.this.fimwareCurrentSize + " size=" + OTGManager.this.fimwareQueue.size());
                    while (true) {
                        currentSendNum = deviceMsg2.getCurrentSendNum();
                        if (currentSendNum < 0) {
                            break;
                        }
                        if (currentSendNum % 600 == 0) {
                            OTGManager.this.sendDatas(deviceMsg2);
                        }
                        if (OTGManager.this.isFimwareFlag) {
                            break;
                        }
                        deviceMsg2.setCurrentSendNum(deviceMsg2.getCurrentSendNum() - 1);
                        try {
                            Thread.sleep(5L);
                        } catch (InterruptedException e3) {
                            e3.printStackTrace();
                        }
                    }
                    if (currentSendNum == -1) {
                        OTGManager.this.mDataTransListerner.onCmdMessage(Constant.DEVICE_FIRMWARE_UPDATE, new byte[4], 3, DataTransListerner.ProtocolType.OTG);
                        OTGManager.this.fimwareQueue.clear();
                    }
                }
            }
            this.isRunning = false;
        }
    }

    public void closeSession() {
        this.isRequestPermission = false;
        this.isRunning = false;
        Queue<DeviceMsg> queue = this.fimwareQueue;
        if (queue != null) {
            queue.clear();
        }
        Queue<DeviceMsg> queue2 = this.msgsQueue;
        if (queue2 != null) {
            queue2.clear();
        }
        SendThread sendThread = this.mSendThread;
        if (sendThread != null) {
            sendThread.close();
        }
        ReceiveThread receiveThread = this.mReceiveThread;
        if (receiveThread != null) {
            receiveThread.close();
        }
        if (this.deviceConnection != null) {
            this.deviceConnection = null;
        }
        if (this.usbDevice != null) {
            this.usbDevice = null;
        }
        if (this.usbInterface != null) {
            this.usbInterface = null;
        }
        //此处或 蓝牙是否连接
        if (this.mDataTransListerner == null) {
            return;
        }
        this.mDataTransListerner.onCmdMessage(-1, new byte[0], 1, DataTransListerner.ProtocolType.OTG);
    }

    public boolean checkIsConnectDevice() {
        UsbManager usbManager = (UsbManager) this.mContext.getSystemService(Context.USB_SERVICE);
        this.usbManager = usbManager;
        HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
        this.deviceList = deviceList;
        for (UsbDevice usbDevice : deviceList.values()) {
            if (usbDevice.getVendorId() == 1027 && usbDevice.getProductId() == 24596) {
                this.usbDevice = usbDevice;
            }
        }
        return this.usbDevice != null;
    }
    public boolean isConnect() {
        return this.isRunning;
    }
    public boolean initUsbDevice() {
        UsbManager usbManager = (UsbManager) this.mContext.getSystemService(Context.USB_SERVICE);
        this.usbManager = usbManager;
        HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
        this.deviceList = deviceList;
        for (UsbDevice usbDevice : deviceList.values()) {
            if (usbDevice.getVendorId() == 1027 && usbDevice.getProductId() == 24596) {
                this.usbDevice = usbDevice;
            }
        }
        findInterface();
        return start();
    }
}
