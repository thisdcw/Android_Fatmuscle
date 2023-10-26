package com.maxsella.fatmuscle.common.util;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class LocalLog {

    private static final String TAG = "LocalLog";
    private static final int LOG_FILE_MAX = 20; // 日志文件的最多保存个数,
    private static final String LOG_FILE_EXT = ".txt"; // 本类输出的日志文件名称
    private static final String LOG_FILE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/mxsella" + "/LocalLog/"; // 日志文件在sdcard中的路径
    private static SimpleDateFormat LogSdf; // 日志的输出格式
    private static SimpleDateFormat logfile; // 日志文件格式
    private static boolean init = false;

    private static File file;
    private static FileWriter filerWriter;
    private static BufferedWriter bufWriter;
    private static String curFileName = "";
    private static void init() {
        init = true;
        LogSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logfile = new SimpleDateFormat("yyyyMMdd");
        LogSdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        logfile.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        File logDir = new File(LOG_FILE_DIR);
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
        delFile();
    }

    public static void test(String s){
        Log.d("ooo", s);
        if(Config.writeLog) {
            writeLogToFile("test", "ooo", s);
        }
    }

    public static void d(String tag, String s){
        Log.d(TAG, s);
    }

    public static void w(String tag, String s){
        Log.w(TAG, s);
    }

    public static void e(String tag, String s){
        Log.e(TAG, s);
    }

    public static void i(String tag, String s){
        Log.i(TAG, s);
    }

    /**
     * 写入文件
     */
    private static synchronized void writeLogToFile(String type, String tag, String text) {
        if (!init)
            init();
        try {
            Date time = new Date();
            String logMsg = LogSdf.format(time) + " Log." + type + " " + tag + " " + text;
            String fileName = logfile.format(time);
            if (!fileName.equals(curFileName)) {
                curFileName = fileName;
                file = new File(LOG_FILE_DIR, fileName + LOG_FILE_EXT);
                filerWriter = new FileWriter(file, true);
                bufWriter = new BufferedWriter(filerWriter);
            } else {
                if (file == null)
                    file = new File(LOG_FILE_DIR, fileName + LOG_FILE_EXT);
                if (filerWriter == null)
                    filerWriter = new FileWriter(file, true);
                if (bufWriter == null)
                    bufWriter = new BufferedWriter(filerWriter);
            }
            bufWriter.write(logMsg);
            bufWriter.newLine();
            bufWriter.flush();
            filerWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断最大日志个数是否大于最大个数，如果是的则删除最小的那个日志
     */
    public static void delFile() {
        File file = new File(LOG_FILE_DIR);
        File[] files = file.listFiles();
        if (files != null && files.length > LOG_FILE_MAX) {
            String fileName = files[0].getName();
            int temp = Integer.parseInt(fileName.substring(0,
                    fileName.indexOf(".")).replace("-", ""));
            int min = temp;
            String minName = fileName;
            for (File value : files) {
                fileName = value.getName();
                temp = Integer.parseInt(fileName.substring(0,
                        fileName.indexOf(".")).replace("-", ""));
                if (temp <= min) {
                    min = temp;
                    minName = fileName; // 保存文件名
                }
            }
            if (!"".equals(minName)) {
                file = new File(LOG_FILE_DIR, minName);
                if (file.exists())
                    file.delete();
            }
        }
    }

    /**
     * 获取要上传的文件(zip)
     * @param fileName 目标日志文件名，格式：20190319
     */
    public static String getUploadFile(String fileName, String deviceId) {
        LocalLog.test("日志操作 获取目标日志文件");
        String filePath = LOG_FILE_DIR + fileName + LOG_FILE_EXT;
        String ZipFilePath = LOG_FILE_DIR + "-" + fileName + ".zip";
        File file = new File(filePath);
        File zipFile = new File(ZipFilePath);
        if (file.exists()) {
            if (zipFile.exists())
                zipFile.delete();
            try {
                ZipUtils.zipFile(file, zipFile);
                return zipFile.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
