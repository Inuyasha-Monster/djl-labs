package com.djl.feature;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * 测试内存映射文件的方式随机读写文件
 *
 * @author djl
 */
public class MappedFile {

    /**
     * 1M
     */
    private static final int MAX_FILE_SIZE = 1024 * 1024;

    MappedFile(String filename) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(filename, "rw");
        randomAccessFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, MAX_FILE_SIZE);
    }

    public static void main(String[] args) {

    }
}
