package string;

/**
 * @author djl
 */
public class ReplaceSpace {
    public static String replaceSpace(String s) {
        StringBuilder sb = new StringBuilder();
        final char[] chars = s.toCharArray();
        for (final char temp : chars) {
            if (temp == ' ') {
                sb.append("%20");
            } else {
                sb.append(temp);
            }
        }
        return sb.toString();
    }

    /**
     * 使用静态数组的方式，防止扩容
     *
     * @param s
     * @return
     */
    public static String replaceSpace2(String s) {
        final char[] chars = s.toCharArray();
        // 统计空格的次数
        int count = 0;
        for (char c : chars) {
            if (c == ' ') {
                count++;
            }
        }
        // 申请新的char数组
        char[] res = new char[chars.length + count * 2];
        int index = 0;
        for (char c : chars) {
            if (c == ' ') {
                res[index] = '%';
                index++;
                res[index] = '2';
                index++;
                res[index] = '0';
            } else {
                res[index] = c;
            }
            index++;
        }
        return new String(res);
    }

    public static void main(String[] args) {
        final String replaceSpace = replaceSpace2("We are happy.");
        System.out.println("replaceSpace = " + replaceSpace);
    }
}
