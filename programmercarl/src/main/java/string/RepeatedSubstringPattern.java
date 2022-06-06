package string;

/**
 * @author djl
 */
public class RepeatedSubstringPattern {

    /**
     * https://leetcode.cn/problems/repeated-substring-pattern/
     *
     * @param s
     * @return
     */
    public static boolean repeatedSubstringPattern(String s) {
        String str = s + s;
        return str.substring(1, str.length() - 1).contains(s);
    }

    public static void main(String[] args) {
        final boolean b = repeatedSubstringPattern("abcabc");
        System.out.println("b = " + b);
    }
}
