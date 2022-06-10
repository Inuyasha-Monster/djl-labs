package com.djl.feature;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试内存映射文件的方式随机读写文件
 *
 * @author djl
 */
public class MappedFileTest {

    /**
     * 10M
     */
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 10;

    @Data
    @AllArgsConstructor
    @ToString
    public static class Person {
        private Integer age;
        private String name;
    }

    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("000", "rw");
        final MappedByteBuffer mappedByteBuffer = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, MAX_FILE_SIZE);

        final int position = mappedByteBuffer.position();
        System.out.println("position = " + position);

        final int limit = mappedByteBuffer.limit();
        System.out.println("limit = " + limit);

        final int capacity = mappedByteBuffer.capacity();
        System.out.println("capacity = " + capacity);

        /**
         * position = 0
         * limit = 1024
         * capacity = 1024
         */

        final long start = System.currentTimeMillis();
        int count = 0;
        int writePosition = 0;
        final ByteBuffer byteBuffer = mappedByteBuffer.slice();
        for (int i = 0; i < 1_000_000; i++) {
            final Person person = new Person(i, "name-" + i);
            final String json = JSON.toJSONString(person);
            final byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            // 当消息字节长度+8大于剩余空间的时候
            if (bytes.length + 4 > byteBuffer.remaining()) {
                // 设置文件末尾标记
                byteBuffer.putInt(123456789);
                break;
            }
            byteBuffer.putInt(bytes.length);
            byteBuffer.put(bytes);
            count++;
            writePosition = writePosition + 4 + bytes.length;
        }
        mappedByteBuffer.force();
        final long end = System.currentTimeMillis();
        System.out.println("cost = " + (end - start) + " ms");
        System.out.println("count = " + count);

        final int byteBufferPosition = byteBuffer.position();
        System.out.println("byteBufferPosition = " + byteBufferPosition);

        final int byteBufferLimit = byteBuffer.limit();
        System.out.println("byteBufferLimit = " + byteBufferLimit);

        System.out.println("writePosition = " + writePosition);

        System.out.println("remain bytes = " + (mappedByteBuffer.capacity() - writePosition));

        System.out.println("开始读取文件内容...");
        List<Person> list = new ArrayList<>();
        final ByteBuffer readBuffer = mappedByteBuffer.slice();
        while (true) {
            final int length = readBuffer.getInt();
            if (length == 123456789) {
                break;
            }
            final byte[] bytes = new byte[length];
            readBuffer.get(bytes);
            final String json = new String(bytes, StandardCharsets.UTF_8);
            final Person person = JSON.parseObject(json, Person.class);
            list.add(person);
        }

        System.out.println("list.size() = " + list.size());

        // 抽查几个验证
        for (int i = 0; i < list.size(); i++) {
            if (i % 1000 == 0) {
                final Person person = list.get(i);
                System.out.println("person = " + person);
            }
        }
    }
}
