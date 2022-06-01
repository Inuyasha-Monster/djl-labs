package string;

/**
 * @author djl
 */
public class ReverseLeftWords {

    /**
     * 字符串的左旋转操作是把字符串前面的若干个字符转移到字符串的尾部。请定义一个函数实现字符串左旋转操作的功能。比如，输入字符串"abcdefg"和数字2，该函数将返回左旋转两位得到的结果"cdefgab"。
     * <p>
     * <p>
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/zuo-xuan-zhuan-zi-fu-chuan-lcof
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param s
     * @param n
     * @return
     */
    public static String reverseLeftWords(String s, int n) {
        if (s == null || n <= 0) {
            return null;
        }
        if (n >= s.length()) {
            return s;
        }
        char[] preArr = new char[n];

        char[] lastArr = new char[s.length() - n];
        int lastIndex = 0;

        final char[] chars = s.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (i < n) {
                preArr[i] = chars[i];
            } else {
                lastArr[lastIndex] = chars[i];
                lastIndex++;
            }
        }


        char[] res = new char[chars.length];
        int resIndex = 0;

        for (char c : lastArr) {
            res[resIndex] = c;
            resIndex++;
        }

        for (char c : preArr) {
            res[resIndex] = c;
            resIndex++;
        }


        return new String(res);
    }

    public static String reverseLeftWords2(String s, int n) {
        final char[] array = s.toCharArray();
        char[] chars = new char[array.length];
        int index = 0;
        for (int i = n; i < array.length; i++) {
            chars[index] = array[i];
            index++;
        }
        for (int i = 0; i < n; i++) {
            chars[index] = array[i];
            index++;
        }
        return new String(chars);
    }

    public static void main(String[] args) {
        final String leftWords = reverseLeftWords2("abcdefg", 2);
        System.out.println("leftWords = " + leftWords);
    }
}
