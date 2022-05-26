package hash;

import java.util.HashMap;
import java.util.Map;

/**
 * @author djl
 */
public class IsAnagram {

    /**
     * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
     * <p>
     * 注意：若 s 和 t 中每个字符出现的次数都相同，则称 s 和 t 互为字母异位词。
     *
     * @param s
     * @param t
     * @return
     */
    public static boolean isAnagram(String s, String t) {
        if (s == null || s.length() == 0) {
            return false;
        }
        if (t == null || t.length() == 0) {
            return false;
        }
        if (s.length() != t.length()) {
            return false;
        }
        final char[] s1 = s.toCharArray();
        Map<Character, Integer> m1 = new HashMap<>(16);
        for (char value : s1) {
            m1.compute(value, (k, v) -> v == null ? 1 : ++v);
        }

        final char[] t1 = t.toCharArray();
        Map<Character, Integer> m2 = new HashMap<>(16);
        for (char c : t1) {
            if (!m1.containsKey(c)) {
                return false;
            }
            m2.compute(c, (k, v) -> v == null ? 1 : ++v);
        }

        return m2.entrySet().stream().allMatch(x -> m1.containsKey(x.getKey()) && m1.get(x.getKey()).equals(x.getValue()));
    }

    public static boolean isAnagram2(String s, String t) {
        int[] record = new int[26];
        for (char c : s.toCharArray()) {
            record[c - 'a']++;
        }
        for (char c : t.toCharArray()) {
            record[c - 'a']--;
        }
        for (int i : record) {
            if (i != 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        final int i = 'b' - 'a';
        System.out.println("i = " + i);
    }
}
