package stackAndQueue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

/**
 * @author djl
 */
public class TopKFrequent {

    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.merge(num, 1, Integer::sum);
        }
        PriorityQueue<Map.Entry<Integer, Integer>> queue = new PriorityQueue<>(k, Map.Entry.comparingByValue());
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            queue.add(entry);
            if (queue.size() > k) {
                queue.remove();
            }
        }
        return queue.stream().map(Map.Entry::getKey).flatMapToInt(IntStream::of).toArray();
    }

    public static void main(String[] args) {
        TopKFrequent topKFrequent = new TopKFrequent();
        int[] nums = new int[]{1};
        final int[] result = topKFrequent.topKFrequent(nums, 1);
        System.out.println("result = " + Arrays.toString(result));
    }
}
