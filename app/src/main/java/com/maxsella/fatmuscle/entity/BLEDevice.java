package com.maxsella.fatmuscle.entity;

import android.bluetooth.BluetoothDevice;
import android.os.ParcelUuid;

import java.io.Serializable;

public class BLEDevice implements Serializable {
    private String address;
    private BluetoothDevice bluetoothDevice;
    private boolean conntect;
    private int icon;
    private String name;
    private int rssi;
    private ParcelUuid[] uuids;

    public ParcelUuid[] getUuids() {
        return this.uuids;
    }

    public void setUuids(ParcelUuid[] parcelUuidArr) {
        this.uuids = parcelUuidArr;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String str) {
        this.address = str;
    }

    public void setName(String str) {
        this.name = str;
    }

    public int getIcon() {
        return this.icon;
    }

    public void setIcon(int i) {
        this.icon = i;
    }

    public String getName() {
        return this.name;
    }

    public boolean check_uuid(String str) {
        ParcelUuid[] parcelUuidArr = this.uuids;
        if (parcelUuidArr == null || parcelUuidArr == null || parcelUuidArr.length <= 0) {
            return false;
        }
        boolean z = false;
        for (ParcelUuid parcelUuid : parcelUuidArr) {
            if (parcelUuid.equals(str)) {
                z = true;
            }
        }
        return z;
    }

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public boolean isConntect() {
        return this.conntect;
    }

    public void setConntect(boolean z) {
        this.conntect = z;
    }

    public int getRssi() {
        return this.rssi;
    }

    public void setRssi(int i) {
        this.rssi = i;
    }
}
