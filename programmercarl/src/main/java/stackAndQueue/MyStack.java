package stackAndQueue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 用队列实现栈
 *
 * @author djl
 */
public class MyStack {

    private final Queue<Integer> queue;
    private final Queue<Integer> back;

    public MyStack() {
        queue = new LinkedList<>();
        back = new LinkedList<>();
    }

    public void push(int x) {
        queue.add(x);
    }

    public int pop() {
        int size = queue.size();
        while (size > 1) {
            back.add(queue.poll());
            size--;
        }
        final int result = queue.poll();
        while (!back.isEmpty()) {
            queue.add(back.poll());
        }
        return result;
    }

    public int top() {
        int size = queue.size();
        while (size > 1) {
            back.add(queue.poll());
            size--;
        }
        final int result = queue.poll();
        back.add(result);
        while (!back.isEmpty()) {
            queue.add(back.poll());
        }
        return result;
    }

    public boolean empty() {
        return queue.isEmpty();
    }

    public static void main(String[] args) {
        MyStack myStack = new MyStack();
        myStack.push(2);
        myStack.push(2);
        myStack.push(1);
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
