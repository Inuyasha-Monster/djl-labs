package string;

/**
 * @author djl
 */
public class ReverseWords {

    /**
     * https://leetcode.cn/problems/reverse-words-in-a-string/
     *
     * @param s
     * @return
     */
    public static String reverseWords(String s) {
        final char[] chars = s.toCharArray();
        int end = chars.length - 1;
        // 保证多一个空格的空间
        char[] res = new char[chars.length + 1];
        int resIndex = 0;
        while (end >= 0) {
            // 一直从右向左推到不是空格
            while (end >= 0 && chars[end] == ' ') {
                end--;
            }
            if (end <= 0) {
                break;
            }
            int right = end;
            // 记录单词的区间
            while (end >= 0 && chars[end] != ' ') {
                end--;
            }
            // 将单词复制到res数组
            for (int i = end + 1; i <= right; i++) {
                res[resIndex] = chars[i];
                resIndex++;
                // 单词写完整一个空格在尾部
                if (i == right) {
                    res[resIndex] = ' ';
                    resIndex++;
                }
            }
        }
        return new String(res, 0, resIndex - 1);
    }

    public static void main(String[] args) {
        final String blue = "  the        sky  is    blue  ";
        final String theSkyIsBlue = reverseWords(blue);
        System.out.println(blue);
        System.out.println(theSkyIsBlue);
    }
}
