package stackAndQueue;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author djl
 */
public class MaxSlidingWindow {

    /**
     * 自定义实现一个单调队列（从大到小）
     */
    private static class MyQueue {

        // 定一个双向队列
        private final Deque<Integer> deque = new LinkedList<>();

        // 从出口弹出元素
        public void popFront(int value) {
            if (!deque.isEmpty() && deque.peekFirst() == value) {
                deque.removeFirst();
            }
        }

        // 从入口加入元素
        public void pushBack(int value) {
            while (!deque.isEmpty() && deque.peekLast() < value) {
                deque.removeLast();
            }
            deque.addLast(value);
        }

        // 获取队列当前最大值
        public int getMaxValue() {
            return deque.getFirst();
        }
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] results = new int[nums.length - k + 1];

        MyQueue queue = new MyQueue();
        // 先填充k个元素到myQueue中
        for (int i = 0; i < k; i++) {
            queue.pushBack(nums[i]);
        }

        int counter = 0;

        results[counter] = queue.getMaxValue();

        // 循环后续的元素
        for (int i = k; i < nums.length; i++) {
            queue.popFront(nums[i - k]);
            queue.pushBack(nums[i]);
            counter++;
            results[counter] = queue.getMaxValue();
        }
        return results;
    }

    public static void main(String[] args) {
        MaxSlidingWindow maxSlidingWindow = new MaxSlidingWindow();
        int[] nums = new int[]{1};
        final int[] result = maxSlidingWindow.maxSlidingWindow(nums, 1);
        System.out.println("result = " + Arrays.toString(result));
    }
}
