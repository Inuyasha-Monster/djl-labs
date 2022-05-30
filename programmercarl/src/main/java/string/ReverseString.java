package string;

import java.util.Arrays;

/**
 * @author djl
 */
public class ReverseString {

    public static void reverseString(char[] s) {
        int left = 0;
        int right = s.length - 1;
        while (left < right) {
            final char temp = s[right];
            s[right] = s[left];
            s[left] = temp;
            left++;
            right--;
        }
    }

    public static void main(String[] args) {
        char[] chars = new char[]{'h','e','l','l','o'};
        reverseString(chars);
        System.out.println("chars = " + Arrays.toString(chars));
    }
}
