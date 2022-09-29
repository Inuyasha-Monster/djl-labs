package stackAndQueue;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author djl
 */
public class RemoveDuplicates {

    /**
     * "abbaca"
     *
     * @param s
     * @return
     */
    public String removeDuplicates(String s) {
        Deque<Character> deque = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            final char c = s.charAt(i);
            if (deque.isEmpty() || !deque.peek().equals(c)) {
                deque.push(c);
            } else {
                deque.pop();
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (!deque.isEmpty()) {
            stringBuilder.append(deque.removeLast());
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        RemoveDuplicates removeDuplicates = new RemoveDuplicates();
        final String duplicates = removeDuplicates.removeDuplicates("abbaca");
        System.out.println("duplicates = " + duplicates);
    }
}
