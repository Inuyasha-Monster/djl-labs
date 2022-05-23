package linked;

import java.util.Stack;

/**
 * @author djl
 */
public class ReverseListTest {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            return String.valueOf(val);
        }
    }

    public static ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }
        Stack<ListNode> stack = new Stack<>();
        ListNode cur = head;
        while (cur != null) {
            stack.push(new ListNode(cur.val));
            cur = cur.next;
        }

        ListNode newHead = new ListNode(stack.pop().val);
        ListNode current = newHead;
        while (!stack.isEmpty()) {
            current.next = stack.pop();
            current = current.next;
        }
        return newHead;
    }

    public static void printListNode(ListNode head) {
        while (head != null) {
            System.out.print(head.val + "->");
            head = head.next;
        }
    }

    public static void main(String[] args) {
        final ListNode next3 = new ListNode(3);
        final ListNode next2 = new ListNode(2, next3);
        final ListNode next1 = new ListNode(2, next2);
        final ListNode head = new ListNode(1, next1);

        printListNode(head);

        final ListNode reverseList = reverseList(head);
        System.out.println();
        printListNode(reverseList);
    }
}
