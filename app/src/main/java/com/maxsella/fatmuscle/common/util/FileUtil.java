package com.maxsella.fatmuscle.common.util;

import java.io.File;

public class FileUtil {

    public static File getFileByPath(String str) {
        if (isSpace(str)) {
            return null;
        }
        return new File(str);
    }
    private static boolean isSpace(String str) {
        if (str == null) {
            return true;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    public static boolean isFileExists(File file) {
        return file != null && file.exists();
    }
    public static boolean isFileExists(String str) {
        return isFileExists(getFileByPath(str));
    }

    public static File getCacheDir(String name) {
        // 这里是你获取缓存目录的代码
        // 例如：获取应用程序的缓存目录
        return new File("/" + name);
    }
}
