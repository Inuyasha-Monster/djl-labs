package string;

/**
 * @author djl
 */
public class StrStr {

    public static int strStr(String haystack, String needle) {
        return haystack.indexOf(needle);
    }

    /**
     * 滑动窗口
     *
     * @param haystack
     * @param needle
     * @return
     */
    public static int strStr2(String haystack, String needle) {
        int m = needle.length();
        // 当 needle 是空字符串时我们应当返回 0
        if (m == 0) {
            return 0;
        }
        int n = haystack.length();
        if (n < m) {
            return -1;
        }
        int i = 0;
        int j = 0;
        while (i < n - m + 1) {
            // 找到首字母相等
            while (i < n && haystack.charAt(i) != needle.charAt(j)) {
                i++;
            }
            if (i == n) {// 没有首字母相等的
                return -1;
            }
            // 遍历后续字符，判断是否相等
            i++;
            j++;
            while (i < n && j < m && haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
            }
            // 找到
            if (j == m) {
                return i - j;
            } else {
                // 未找到
                i -= j - 1;
                j = 0;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        final int i = strStr2("helloaaaaaa", "oab");
        System.out.println("i = " + i);
    }
}
