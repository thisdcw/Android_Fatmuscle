package com.maxsella.fatmuscle.common.util;

import com.maxsella.fatmuscle.comm.ICommunicateService.DataReceiveListener;
import com.maxsella.fatmuscle.comm.ReceivePacket;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tools {

    private DataReceiveListener mListener;
    private ByteBuffer byteBuffer = ByteBuffer.allocate(500);
    private class ReceiveTask implements Runnable {

        private byte[] head;
        private byte[] tail;
        private int headLen, tailLen;
        private byte[] data;

        public ReceiveTask(byte[] head, byte[] data) {
            this(head, "".getBytes(), data);
        }

        public ReceiveTask(byte[] head, byte[] tail, byte[] data) {
            this.head = head;
            this.tail = tail;
            this.headLen = head.length;
            this.tailLen = tail.length;
            this.data = data;
        }

        private boolean endWith(Byte[] src, byte[] target) {
            if (target.length == 0) return false;
            if (src.length < target.length) return false;
            for (int i = 0;i < target.length;i++) {
                if (target[target.length - i - 1] != src[src.length - i - 1]) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public void run() {
            DataInputStream inputStream = null;
            int readLength = 0;
            try {
                if (byteBuffer.position() != 0) {
                    byteBuffer.put(data);
                    byteBuffer.flip();
                    data = new byte[byteBuffer.limit()];
                    byteBuffer.get(data);
                    byteBuffer.clear();
                }
                inputStream = new DataInputStream(new ByteArrayInputStream(data));
                int flag = -1;
                while((flag = inputStream.read()) != -1) {
                    readLength++;
                    List<Byte> byteList = new ArrayList<>();
                    byteList.add((byte) flag);
                    int read = -1;
                    boolean hasHead = false;
                    while ((read = inputStream.read()) != -1) {
                        readLength++;
                        byteList.add((byte) read);
                        Byte[] bytes = byteList.toArray(new Byte[0]);
                        if (endWith(bytes, head)) {
                            hasHead = true;
                            break;
                        }
                    }
                    if (!hasHead) {
                        break;
                    }
                    int len = -1;
                    if ((len = inputStream.read()) != -1) {
                        if (len > 50)
                            return;
                        readLength++;
                        if (readLength + len > data.length) {
                            byteBuffer.put(Arrays.copyOfRange(data, readLength - 3, data.length));
                            break;
                        }
                        byte[] bytes = new byte[len];
                        inputStream.readFully(bytes);
                        readLength += len;
/*
                        ReceivePacket packet = new ReceivePacket(bytes);
                        if (mListener != null) {
                            LogUtil.d("---------->" + Arrays.toString(bytes));
                            mListener.onReceive(packet);
                        }
                        */
                    } else {
                        byteBuffer.put(Arrays.copyOfRange(data, data.length - 2, data.length));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
