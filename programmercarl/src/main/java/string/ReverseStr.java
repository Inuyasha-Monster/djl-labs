package string;

/**
 * @author djl
 */
public class ReverseStr {

    /**
     * https://leetcode.cn/problems/reverse-string-ii/
     *
     * @param s
     * @param k
     * @return
     */
    public static String reverseStr(String s, int k) {
        final char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i += k * 2) {
            int start = i;
            int end = Math.min(chars.length - 1, start + k - 1);
            // start - end 区间字符进行反转
            while (start < end) {
                final char temp = chars[start];
                chars[start] = chars[end];
                chars[end] = temp;
                start++;
                end--;
            }
        }
        return new String(chars);
    }

    public static void main(String[] args) {
        final String reverseStr = reverseStr("abcdefg", 2);
        System.out.println("reverseStr = " + reverseStr);
    }
}
