package com.djl;

import cn.hutool.core.util.ReflectUtil;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author djl
 */
public class ResizableCapacityLinkedBlockingQueue<E> extends LinkedBlockingQueue<E> {

    public ResizableCapacityLinkedBlockingQueue(int capacity) {
        super(capacity);
    }

    public synchronized boolean setCapacity(Integer capacity) {
        boolean successFlag = true;
        /**
         * TODO：后续考虑切换 Rabbitmq VariableLinkedBlockingQueue
         */
        try {
            int oldCapacity = (int) ReflectUtil.getFieldValue(this, "capacity");
            AtomicInteger count = (AtomicInteger) ReflectUtil.getFieldValue(this, "count");
            int size = count.get();

            ReflectUtil.setFieldValue(this, "capacity", capacity);
            if (capacity > size && size >= oldCapacity) {
                ReflectUtil.invoke(this, "signalNotFull");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            successFlag = false;
        }

        return successFlag;
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        ResizableCapacityLinkedBlockingQueue<Integer> queue = new ResizableCapacityLinkedBlockingQueue<>(10);

        for (int i = 0; i < 10; i++) {
            queue.put(i);
        }

        new Thread(() -> {
            try {
                queue.put(888);
                System.out.println("888");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            while (true) {
                int capacity = (int) ReflectUtil.getFieldValue(queue, "capacity");
                System.out.println("queue capacity = " + capacity);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();

        System.out.println("--修改queue长度为11--");
        queue.setCapacity(11);

        System.in.read();

    }

}
