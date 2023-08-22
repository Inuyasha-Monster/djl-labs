package com.djl;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DAG {
    private Map<String, Node> nodes = new HashMap<>();

    public Node addNode(String id, Runnable task) {
        Node node = new Node(id, task);
        nodes.put(id, node);
        return node;
    }

    public Node getNode(String id) {
        return nodes.get(id);
    }

    public Collection<Node> getNodes() {
        return nodes.values();
    }

    public void execute() throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Node> nodeList = new ArrayList<>(this.nodes.values());
        for (Node node : nodeList) {
            CountDownLatch latch = new CountDownLatch(node.getDependencies().size());
            node.setLatch(latch);
            for (Node dependency : node.getDependencies()) {
                executor.execute(() -> {
                    dependency.execute();
                    latch.countDown();
                });
            }
        }
        for (Node node : nodes.values()) {
            executor.execute(node::execute);
        }
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }
}
