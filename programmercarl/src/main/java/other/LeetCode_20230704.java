package other;

import java.util.Arrays;

public class LeetCode_20230704 {

    /**
     * https://leetcode.cn/problems/move-zeroes/
     * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
     * <p>
     * 请注意 ，必须在不复制数组的情况下原地对数组进行操作。
     *
     * @param nums
     */
    public static void moveZeroes(int[] nums) {
        // 思路：
        /*
        1、通过双指针，一个指针去找不等于 0 的元素，一个指针去找等于 0 的元素，并且前者 index 大于后者 index ，然后进行交换
        2、交换之后继续找，直到数组遍历结束
        注意：如果首次等于 0 的指针大于 首次不等于 0 的指针，则直接返回
         */

        // 首个 0 所在的位置
        int zeroIndex = -1;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                zeroIndex = i;
                break;
            }
        }

        // 表示没有0数字
        if (zeroIndex < 0) {
            return;
        }

        // 首个非 0 所在的位置，且在 0 所在位置后面
        int notZeroIndex = -1;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0 && i > zeroIndex) {
                notZeroIndex = i;
                break;
            }
        }

        // 表示不存在 非0 数字
        if (notZeroIndex < 0) {
            return;
        }

        while (zeroIndex < nums.length && notZeroIndex < nums.length) {
            if (nums[zeroIndex] == 0 && nums[notZeroIndex] != 0) {
                // 执行数字交换
                nums[zeroIndex] = nums[notZeroIndex];
                nums[notZeroIndex] = 0;
            }
            if (nums[zeroIndex] != 0) {
                zeroIndex = zeroIndex + 1;
            }
            if (nums[notZeroIndex] == 0) {
                notZeroIndex = notZeroIndex + 1;
            }
        }

    }

    /**
     * 野路子 = 直接遍历过滤非 0 的数字依次填充到数组中，然后尾部补充 0
     *
     * @param nums
     */
    public void moveZeroes2(int[] nums) {
        int index = 0;
        for (int num : nums) {
            if (num != 0) {
                nums[index] = num;
                index++;
            }
        }
        for (int i = index; i < nums.length; i++) {
            nums[i] = 0;
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, 0, 1};
        moveZeroes(nums);
        System.out.println("nums = " + Arrays.toString(nums));
    }

}
