package hash;

import java.util.HashMap;
import java.util.Map;

/**
 * @author djl
 */
public class FourSumCount {

    /**
     * 给你四个整数数组 nums1、nums2、nums3 和 nums4 ，数组长度都是 n ，请你计算有多少个元组 (i, j, k, l) 能满足：
     * <p>
     * 0 <= i, j, k, l < n
     * nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/4sum-ii
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param nums1
     * @param nums2
     * @param nums3
     * @param nums4
     * @return
     */
    public static int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        Map<Integer, Integer> map1 = new HashMap<>();
        // 首先第一步统计 1和2数组中的两数只和出现的次数
        for (int a : nums1) {
            for (int b : nums2) {
                final int key = a + b;
                map1.put(key, map1.getOrDefault(key, 0) + 1);
            }
        }
        int result = 0;
        // 第二步骤：判断map中的key能够匹配 2和3 数组中的两数只和减法为0
        Map<Integer, Integer> map2 = new HashMap<>();
        for (int c : nums3) {
            for (int d : nums4) {
                final int key = -c - d;
                // 如果map1中存在key说明4数之和为0
                if (map1.containsKey(key)) {
                    // 直接取value值作为匹配当前情况的次数
                    result += map1.get(key);
                }
            }
        }
        // 返回匹配次数即可
        return result;
    }

    public static void main(String[] args) {

    }
}
