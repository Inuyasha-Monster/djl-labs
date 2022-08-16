package com.djl;

import cn.hutool.core.util.ReflectUtil;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author djl
 */
public class CapacityTest {

    private final int capacity;

    public CapacityTest(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) throws IOException {
        CapacityTest capacityTest = new CapacityTest(10);

        for (int i = 0; i < 100000; i++) {
            capacityTest.getCapacity();
        }

        EXECUTOR_SERVICE.scheduleAtFixedRate(() -> {
            final int capacity = capacityTest.getCapacity();
            System.out.println("loop capacity = " + capacity);
        }, 0, 1, TimeUnit.SECONDS);

        //final int capacity = (int) ReflectUtil.getFieldValue(capacityTest, "capacity");
        //System.out.println("capacity = " + capacity);

        ReflectUtil.setFieldValue(capacityTest, "capacity", 11);

        //final int testCapacity = capacityTest.getCapacity();
        //System.out.println("testCapacity = " + testCapacity);
        System.in.read();
    }
}
