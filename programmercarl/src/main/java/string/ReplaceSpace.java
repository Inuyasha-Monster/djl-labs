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

    public static void main(String[] args) {
        final String replaceSpace = replaceSpace("We are happy.");
        System.out.println("replaceSpace = " + replaceSpace);
    }
}
