package com.djl.feature;

import java.nio.ByteBuffer;

/**
 * @author djl
 */
public class ByteBufferTest {
    public static void main(String[] args) {
        final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(10);
        byteBuffer.putInt(10);
        byteBuffer.putInt(99);

        //byteBuffer.putChar('a');

        final int position = byteBuffer.position();
        // 8
        System.out.println("position = " + position);

        final int limit = byteBuffer.limit();
        // 10
        System.out.println("limit = " + limit);

        final int capacity = byteBuffer.capacity();
        // 10
        System.out.println("capacity = " + capacity);

        final ByteBuffer slice = byteBuffer.slice();
        final int slicePosition = slice.position();
        System.out.println("slicePosition = " + slicePosition);
        final int sliceLimit = slice.limit();
        System.out.println("sliceLimit = " + sliceLimit);
        final int sliceCapacity = slice.capacity();
        System.out.println("sliceCapacity = " + sliceCapacity);


        byteBuffer.flip();

        final int position1 = byteBuffer.position();
        // 0
        System.out.println("position1 = " + position1);

        final int limit1 = byteBuffer.limit();
        // 8
        System.out.println("limit1 = " + limit1);
    }
}
