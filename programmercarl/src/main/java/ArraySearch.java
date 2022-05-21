/**
 * @author djl
 */
public class ArraySearch {
    public static void main(String[] args) {
        int[] nums = new int[]{-1, 0, 3, 5, 9, 12};
        int target = 2;
        final int search = search(nums, target);
        System.out.println("search = " + search);
    }

    /**
     * https://leetcode.cn/problems/binary-search/
     * 提示：
     * <p>
     * 你可以假设 nums 中的所有元素是不重复的。
     * n 将在 [1, 10000]之间。
     * nums 的每个元素都将在 [-9999, 9999]之间。
     * <p>
     * 描述：
     * 给定一个n个元素有序的（升序）整型数组 nums 和一个目标值 target ，写一个函数搜索 nums 中的 target，
     * 如果目标值存在返回下标，否则返回 -1。
     *
     * @param nums
     * @param target
     * @return
     */
    public static int search(int[] nums, int target) {
        // 如果第一个元素大于目标元素或者最后一个元素小于目标元素则直接返回，避免无效遍历
        if (nums[0] > target || nums[nums.length - 1] < target) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;
        // 定义「循环不变量规则」
        while (left <= right) {
            // 求出中间位置下标
            final int mid = (left + right) / 2;
            if (nums[mid] > target) {
                // 目标值在左区间，所以在 [left,mid-1]
                right = mid - 1;
            } else if (nums[mid] < target) {
                // 目标值在右区间，所以在 [mid+1,right]
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }
}
