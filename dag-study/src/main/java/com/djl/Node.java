package com.djl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Node {
    private String id;
    private List<Node> dependencies = new ArrayList<>();
    private Runnable task;
    private CountDownLatch latch;

    public Node(String id, Runnable task) {
        this.id = id;
        this.task = task;
    }

    public void addDependency(Node node) {
        dependencies.add(node);
    }

    public List<Node> getDependencies() {
        return dependencies;
    }

    public String getId() {
        return id;
    }

    public void execute() {
        try {
            if (latch != null) {
                latch.await();
            }
            task.run();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }
}

