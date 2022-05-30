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
