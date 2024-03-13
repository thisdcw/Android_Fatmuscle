package com.maxsella.fatmuscle.ui.activity;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import com.maxsella.fatmuscle.R;
import com.maxsella.fatmuscle.common.base.BaseActivity;
import com.maxsella.fatmuscle.common.util.Config;
import com.maxsella.fatmuscle.common.util.LogUtil;
import com.maxsella.fatmuscle.databinding.ActivityWorkBinding;
import com.maxsella.fatmuscle.entity.FatRecord;
import com.maxsella.fatmuscle.sdk.fat.entity.BitmapMsg;
import com.maxsella.fatmuscle.sdk.fat.entity.DeviceMsg;
import com.maxsella.fatmuscle.sdk.fat.manager.MxsellaDeviceManager;
import com.maxsella.fatmuscle.sdk.fat.utils.BitmapUtil;
import com.maxsella.fatmuscle.viewmodel.RecordViewModel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class WorkActivity extends BaseActivity {

    private ActivityWorkBinding workBinding;

    private RecordViewModel recordViewModel = new RecordViewModel();
    private static final String TAG = "MuscleMeasureResultActivity";

    @Override
    protected void initView() {

        workBinding = DataBindingUtil.setContentView(this, R.layout.activity_work);
        initClick();

//        usbTest();
    }

    @Override
    public void initGetData() {
        super.initGetData();

    }

    private void initClick() {
        workBinding.imitateData.setOnClickListener(v -> {
            String[] s = Config.getSelectMode().split("_");
            String mode = s[0];
            String item = s[1];
            recordViewModel.imitateAddRecord(item, mode);
        });
        workBinding.btnSave.setOnClickListener(v->{
            navTo(FatMeasurePlusActivity.class);
        });
    }

    public void usbTest() {
        // Find all available drivers from attached devices.
        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();

        for (UsbDevice device : deviceList.values()) {
            int productId = device.getProductId();
            int vendorId = device.getVendorId();
            if (productId == 24596 && vendorId == 1027) { // 根据产品 ID 和厂商 ID 确定要读取的 USB 设备
                UsbDeviceConnection connection = manager.openDevice(device);
                if (connection == null) {
                    // 处理USB设备连接失败
                    return;
                }

                List<UsbSerialDriver> availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager);
                if (availableDrivers.isEmpty()) {
                    return;
                }
                UsbSerialDriver driver = availableDrivers.get(0);
                UsbSerialPort port = driver.getPorts().get(0);

                try {
                    port.open(connection);
                    port.setParameters(115200, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);

                    // 读取数据
                    byte buffer[] = new byte[16]; // 用于存储读取的数据
                    int numBytesRead = port.read(buffer, 1000); // 读取数据，最多等待1000毫秒
                    if (numBytesRead > 0) {
                        // 处理读取的数据
                        String data = new String(buffer, StandardCharsets.UTF_8);
                        workBinding.usbInfo.append(data + "\n");
                        LogUtil.d(data);
                    }
                    port.close();
                } catch (IOException e) {
                    LogUtil.i(e.getMessage());
                    throw new RuntimeException(e);
                }
                break; // 如果找到了设备，退出循环
            }
        }
    }

}