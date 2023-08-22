package com.djl;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        DAG dag = new DAG();
        Node nodeA = dag.addNode("A", () -> System.out.println("Executing node A"));
        Node nodeB = dag.addNode("B", () -> System.out.println("Executing node B"));
        Node nodeC = dag.addNode("C", () -> System.out.println("Executing node C"));
        nodeC.addDependency(nodeA);
        nodeC.addDependency(nodeB);
        dag.execute();
    }
}

