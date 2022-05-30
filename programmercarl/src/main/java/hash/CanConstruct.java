package hash;

import java.util.HashMap;
import java.util.Map;

/**
 * @author djl
 */
public class CanConstruct {

    /**
     * 给你两个字符串：ransomNote 和 magazine ，判断 ransomNote 能不能由 magazine 里面的字符构成。
     * <p>
     * 如果可以，返回 true ；否则返回 false 。
     * <p>
     * [magazine 中的每个字符只能在 ransomNote 中使用一次。]
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/ransom-note
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param ransomNote
     * @param magazine
     * @return
     */
    public static boolean canConstruct(String ransomNote, String magazine) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : magazine.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        Map<Character, Integer> map2 = new HashMap<>();
        for (char c : ransomNote.toCharArray()) {
            map2.put(c, map2.getOrDefault(c, 0) + 1);
        }
        for (Map.Entry<Character, Integer> entry : map2.entrySet()) {
            final Integer value = map.get(entry.getKey());
            if (value == null) {
                return false;
            }
            if (entry.getValue() > value) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        final boolean b = canConstruct("aa", "aab");
        System.out.println("b = " + b);
    }
}
