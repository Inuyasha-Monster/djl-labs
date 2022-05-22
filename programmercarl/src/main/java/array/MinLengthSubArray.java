package array;

/**
 * @author djl
 */
public class MinLengthSubArray {
    public static void main(String[] args) {
        int[] nums = new int[]{2, 3, 1, 2, 4, 3};
        int target = 7;
        final int subArrayLen = minSubArrayLen(target, nums);
        System.out.println("subArrayLen = " + subArrayLen);

        final int minSubArrayLen2 = minSubArrayLen2(target, nums);
        System.out.println("minSubArrayLen2 = " + minSubArrayLen2);
    }

    /**
     * https://leetcode.cn/problems/minimum-size-subarray-sum/
     * 给定一个含有 n 个[正整数的数组]和一个[正整数 target] 。
     * <p>
     * 找出该数组中满足其和 ≥ target 的长度最小的 连续子数组 [numsl, numsl+1, ..., numsr-1, numsr] ，
     * 并返回「其长度」。如果不存在符合条件的子数组，返回 0 。
     *
     * @param target
     * @param nums
     * @return
     */
    public static int minSubArrayLen(int target, int[] nums) {
        // 采用暴力求解：for + for
        // 第一层循环数组每一个元素，第二层求解几乎当前元素往后推的最小连续子数组长度，然后比较长度，留下最小值
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < nums.length; i++) {
            // 首先判断当前元素是否符合条件，符合则直接返回1
            if (nums[i] >= target) {
                return 1;
            }
            int sum = nums[i];
            // 不符合，则开始迭代当前元素的后续元素累积是否符合条件
            for (int j = i + 1; j < nums.length; j++) {
                sum += nums[j];
                // 找到当前情况的最小长度则直接退出循环，同时比较后记录最小值
                if (sum >= target) {
                    min = Math.min(min, j - i + 1);
                    break;
                }
            }
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }

    /**
     * 采用滑动窗口求解
     *
     * @param target
     * @param nums
     * @return
     */
    public static int minSubArrayLen2(int target, int[] nums) {
        // 定义 startIndex 与 endIndex
        int startIndex = 0;
        int endIndex;
        // 定义结果
        int result = Integer.MAX_VALUE;
        // 定义临时累积结果
        int sum = 0;
        // 定义循环不变量判断条件：当endIndex来到数组末尾的时候结束
        for (endIndex = 0; endIndex < nums.length; endIndex++) {
            sum += nums[endIndex];
            // 当累积结果大于目标值的时候，说明找到一个样本值，startIndex 移动
            while (sum >= target) {
                // 记录最小样本值
                result = Math.min(result, endIndex - startIndex + 1);
                // sum更新
                sum -= nums[startIndex];
                // startIndex 右移动一位
                startIndex++;
            }
        }
        return result == Integer.MAX_VALUE ? 0 : result;
    }
}
