package stackAndQueue;

import java.util.Stack;

/**
 * 用栈实现队列
 *
 * @author djl
 */
public class MyQueue {

    private final Stack<Integer> in;
    private final Stack<Integer> out;

    public MyQueue() {
        in = new Stack<>();
        out = new Stack<>();
    }

    public void push(int x) {
        in.push(x);
    }

    public int pop() {
        transfer();
        return out.pop();
    }

    private void transfer() {
        if (!out.empty()) {
            return;
        }
        while (!in.empty()) {
            out.push(this.in.pop());
        }
    }

    public int peek() {
        transfer();
        return out.peek();
    }

    public boolean empty() {
        return in.empty() && out.empty();
    }

    public static void main(String[] args) {
        MyQueue myQueue = new MyQueue();
        for (int i = 0; i < 5; i++) {
            myQueue.push(i);
        }
        while (!myQueue.empty()) {
            System.out.println(myQueue.pop());
        }
    }
}
