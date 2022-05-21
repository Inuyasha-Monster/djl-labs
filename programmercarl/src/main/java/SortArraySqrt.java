import java.util.Arrays;

/**
 * @author djl
 */
public class SortArraySqrt {
    public static void main(String[] args) {
        int[] nums = new int[]{-7, -3, 2, 3, 11};
        final int[] squares = sortedSquares(nums);
        System.out.println("squares = " + Arrays.toString(squares));
    }

    /**
     * https://leetcode.cn/problems/squares-of-a-sorted-array/
     *
     * @param nums
     * @return
     */
    public static int[] sortedSquares(int[] nums) {
        if (nums == null || nums.length == 0) {
            return nums;
        }
        int left = 0;
        int right = nums.length - 1;
        int k = nums.length - 1;
        int[] result = new int[nums.length];
        // 左/右 指针相遇的时候视为有效情况
        while (left <= right) {
            final int leftSqrt = nums[left] * nums[left];
            final int rightSqrt = nums[right] * nums[right];
            // 左指针对应数据大于右边则填充数据之后右移左指针
            if (leftSqrt >= rightSqrt) {
                result[k] = leftSqrt;
                left++;
            } else {
                result[k] = rightSqrt;
                right--;
            }
            k--;
        }
        return result;
    }
}
