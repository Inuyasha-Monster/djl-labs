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
        // 如果目标字符串长度大于原始字符串长度直接返回
        if (n < m) {
            return -1;
        }
        int i = 0;
        int j = 0;
        while (i < n - m + 1) {
            // 找到2个串的首字母相等，i移动，j不动
            while (i < n && haystack.charAt(i) != needle.charAt(j)) {
                i++;
            }
            // 如果i遍历到目标末尾了，还没有首字母相等的
            if (i == n) {
                return -1;
            }
            // 遍历后续字符，判断是否相等
            i++;
            j++;
            while (i < n && j < m && haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
            }
            // 找到结果
            if (j == m) {
                return i - j;
            } else {
                // 未找到，回拨i的指针
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
