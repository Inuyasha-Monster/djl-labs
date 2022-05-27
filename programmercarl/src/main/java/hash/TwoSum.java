package hash;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author djl
 */
public class TwoSum {

    /**
     * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            final int tmp = target - nums[i];
            final Integer v = map.get(tmp);
            if (v != null && v != i) {
                return new int[]{i, v};
            }
            map.put(nums[i], i);
        }
        return null;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{3, 3};
        final int[] ints = twoSum(nums, 6);
        System.out.println("ints = " + Arrays.toString(ints));
    }
}
