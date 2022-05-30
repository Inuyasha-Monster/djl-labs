package hash;

import java.util.*;

/**
 * @author djl
 */
public class ThreeSum {

    public static List<List<Integer>> threeSum(int[] nums) {
        if (nums == null || nums.length <= 2) {
            return Collections.emptyList();
        }
        Set<List<Integer>> results = new HashSet<>();
        for (int x = 0; x < nums.length; x++) {
            for (int y = x + 1; y < nums.length; y++) {
                for (int z = y + 1; z < nums.length; z++) {
                    final int v = nums[x] + nums[y] + nums[z];
                    if (v == 0) {
                        final ArrayList<Integer> list = new ArrayList<>();
                        list.add(nums[x]);
                        list.add(nums[y]);
                        list.add(nums[z]);
                        list.sort(Integer::compareTo);
                        results.add(list);
                    }
                }
            }
        }
        return new ArrayList<>(results);
    }

    /**
     * 采用排序+双指针的方式
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> threeSum2(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return res;
        }
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            // 排序之后第一个元素大于0直接返回
            if (nums[0] > 0) {
                return res;
            }
            int left = i + 1;
            int right = nums.length - 1;
            // 排除重复结果
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            // 当左指针与右指针相遇的时候停止循环
            while (left < right) {
                final int value = nums[i] + nums[left] + nums[right];
                if (value == 0) {
                    final List<Integer> list = Arrays.asList(nums[i], nums[left], nums[right]);
                    res.add(list);
                    // 排除重复结果
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    left++;
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    right--;
                } else if (value > 0) {
                    right--;
                } else {
                    left++;
                }
            }
        }
        return res;
    }

    public List<List<Integer>> threeSum3(int[] nums) {
        if (nums.length < 3) {
            return new ArrayList<>();
        }
        //排序
        Arrays.sort(nums);
        HashMap<Integer, Integer> map = new HashMap<>();
        List<List<Integer>> resultarr = new ArrayList<>();
        //存入哈希表
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        Integer t;
        int target = 0;
        for (int i = 0; i < nums.length; ++i) {
            target = -nums[i];
            //去重
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (int j = i + 1; j < nums.length; ++j) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                if ((t = map.get(target - nums[j])) != null) {
                    //符合要求的情况,存入
                    if (t > j) {
                        resultarr.add(new ArrayList<>
                                (Arrays.asList(nums[i], nums[j], nums[t])));

                    } else {
                        break;
                    }
                }
            }
        }
        return resultarr;
    }


    public static void main(String[] args) {
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);

        List<Integer> list2 = new ArrayList<>();
        list2.add(3);
        list2.add(2);
        list2.add(1);

        final boolean equals = list1.equals(list2);
        System.out.println("equals = " + equals);
    }
}
