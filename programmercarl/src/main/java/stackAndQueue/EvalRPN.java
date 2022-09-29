package stackAndQueue;

import java.util.Stack;

/**
 * @author djl
 */
public class EvalRPN {

    public int evalRPN(String[] tokens) {
        Stack<String> stack = new Stack<>();
        for (String token : tokens) {
            if ("*".equals(token) || "+".equals(token) || "-".equals(token) || "/".equals(token)) {
                final int pop1 = Integer.parseInt(stack.pop());
                final int pop2 = Integer.parseInt(stack.pop());
                final int res;
                switch (token) {
                    case "*":
                        res = pop2 * pop1;
                        break;
                    case "+":
                        res = pop2 + pop1;
                        break;
                    case "-":
                        res = pop2 - pop1;
                        break;
                    case "/":
                        res = pop2 / pop1;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + token);
                }
                stack.push(res + "");
            } else {
                stack.push(token);
            }
        }
        final String res = stack.pop();
        return Integer.parseInt(res);
    }

    public static void main(String[] args) {
        EvalRPN rpn = new EvalRPN();
        final int evalRPN = rpn.evalRPN(new String[]{"10","6","9","3","+","-11","*","/","*","17","+","5","+"});
        System.out.println("evalRPN = " + evalRPN);
    }
}
