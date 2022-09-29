package stackAndQueue;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author djl
 */
public class KuoHao {

    public boolean isValid(String s) {
        if (s.length() % 2 != 0) {
            return false;
        }
        Deque<Character> queue = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            final char c = s.charAt(i);
            if (c == '{') {
                queue.push('}');
            } else if (c == '[') {
                queue.push(']');
            } else if (c == '(') {
                queue.push(')');
            }
            // 右括号的情况：如果queue为空 或者 字符不匹配
            else if (queue.isEmpty() || !queue.peek().equals(c)) {
                return false;
            } else {
                // 匹配成功弹出
                queue.pop();
            }
        }
        return queue.isEmpty();
    }

    public static void main(String[] args) {
        KuoHao kuoHao = new KuoHao();
        final boolean valid = kuoHao.isValid("((())){}[)");
        System.out.println("valid = " + valid);
    }
}
