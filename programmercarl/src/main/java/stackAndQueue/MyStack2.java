package stackAndQueue;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 用队列实现栈
 *
 * @author djl
 */
public class MyStack2 {

    private Deque<Integer> queue;

    public MyStack2() {
        queue = new LinkedList<>();
    }

    public void push(int x) {
        queue.add(x);
    }

    public int pop() {
        int size = queue.size();
        while (size > 1) {
            queue.add(queue.poll());
            size--;
        }
        return queue.poll();
    }

    public int top() {
        int size = queue.size();
        while (size > 1) {
            queue.add(queue.poll());
            size--;
        }
        final Integer res = queue.poll();
        queue.add(res);
        return res;
    }

    public boolean empty() {
        return queue.isEmpty();
    }

    public static void main(String[] args) {
        MyStack2 myStack = new MyStack2();
        myStack.push(1);
        myStack.push(3);
        myStack.push(5);
        final int top = myStack.top();
        System.out.println("top = " + top);
        while (!myStack.empty()) {
            final int pop = myStack.pop();
            System.out.println("pop = " + pop);
        }
        final boolean empty = myStack.empty();
        System.out.println("empty = " + empty);
    }
}
