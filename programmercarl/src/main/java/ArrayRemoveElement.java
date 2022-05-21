import java.util.Arrays;

/**
 * @author djl
 */
public class ArrayRemoveElement {

    public static void main(String[] args) {
        int[] nums = new int[]{0, 1, 2, 2, 3, 2, 4, 9};
        int val = 2;

        //final int count = removeElement(nums, val);
        //System.out.println("count = " + count);
        //System.out.println("nums = " + Arrays.toString(nums));

        final int count2 = removeElement2(nums, val);
        System.out.println(count2);
        System.out.println("nums = " + Arrays.toString(nums));
    }

    /**
     * https://leetcode.cn/problems/remove-element/
     * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
     * <p>
     * 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
     * <p>
     * 这句话很关键【元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。】
     *
     * @param nums
     * @param val
     * @return
     */
    public static int removeElement(int[] nums, int val) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // 获取数组的长度
        int count = nums.length;
        for (int i = 0; i < count; i++) {
            if (nums[i] == val) {

                System.out.println("移除元素 index = " + i + "; 目标值 = " + val + " 之前 nums = " + Arrays.toString(nums) + " ; count = " + count);

                // 发现需要移除的元素，就将数组集体向前移动一位
                for (int j = i + 1; j < count; j++) {
                    nums[j - 1] = nums[j];
                }
                // 数组长度-1
                count--;
                // 因为向左推动了一格，所以i也向左推动一位
                i--;

                System.out.println("移除元素之后 nums = " + Arrays.toString(nums) + "; count = " + count);
                System.out.println();
            }
        }
        return count;
    }

    public static int removeElement2(int[] nums, int val) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int slow;
        int fast = 0;
        // 定义循环不变规则：fast指针向后移动
        for (slow = 0; fast < nums.length; fast++) {

            System.out.println("index = " + fast + " nums = " + Arrays.toString(nums));

            // 如果fast指针指向数据不等于val，则将slow指针数据设置为fast指向数据，同时移动slow指针
            if (nums[fast] != val) {
                nums[slow] = nums[fast];
                slow++;

                System.out.println("slow = " + slow + " ; nums = " + Arrays.toString(nums));
                System.out.println();
            }
        }
        return slow;
    }
}
