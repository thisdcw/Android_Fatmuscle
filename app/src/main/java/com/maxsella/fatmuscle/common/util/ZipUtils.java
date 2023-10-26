package com.maxsella.fatmuscle.common.util;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
    private static final int BUFF_SIZE = 1024 * 1024; // 1M Byte

    public static void zipFile(File file, File zipFile)
            throws IOException {
        ZipOutputStream zipOut = null;
        try {
            zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile), BUFF_SIZE));
            zipFile(file, zipOut, "");
        } finally {
            if (zipOut != null)
                zipOut.close();
        }

    }

    /**
     * 压缩文件
     * @param resFile  需要压缩的文件（夹）
     * @param zipOut   压缩的目的文件
     * @param rootPath 压缩的文件路径
     * @throws FileNotFoundException 找不到文件时抛出
     * @throws IOException           当压缩过程出错时抛出
     */
    private static void zipFile(File resFile, ZipOutputStream zipOut,
                                String rootPath) throws FileNotFoundException, IOException {
        rootPath = rootPath
                + (rootPath.trim().length() == 0 ? "" : File.separator)
                + resFile.getName();
        rootPath = new String(rootPath.getBytes("8859_1"), UTF_8);
        BufferedInputStream in = null;
        try {
            if (resFile.isDirectory()) {
                File[] fileList = resFile.listFiles();
                for (File file : fileList) {
                    zipFile(file, zipOut, rootPath);
                }
            } else {
                byte buffer[] = new byte[BUFF_SIZE];
                in = new BufferedInputStream(new FileInputStream(resFile),
                        BUFF_SIZE);
                zipOut.putNextEntry(new ZipEntry(rootPath));
                int realLength;
                while ((realLength = in.read(buffer)) != -1) {
                    zipOut.write(buffer, 0, realLength);
                }
                in.close();
                zipOut.flush();
                zipOut.closeEntry();
            }
        } finally {
            if (in != null)
                in.close();
            if (zipOut != null)
                zipOut.close();
        }
    }
}

