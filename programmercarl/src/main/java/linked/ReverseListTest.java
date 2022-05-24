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

    /**
     * 通过stack的特性实现了链表的反转
     *
     * @param head
     * @return
     */
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

    /**
     * 通过双指针的方式实现原地反转，注意该方式会原地修改指针
     *
     * @param head
     * @return
     */
    public static ListNode reverseList2(ListNode head) {
        if (head == null) {
            return null;
        }
        // 定义前指针
        ListNode pre = null;
        // 定义当前指针
        ListNode cur = head;
        while (cur != null) {
            final ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    /**
     * 通过for循环实现
     *
     * @param head
     * @return
     */
    public static ListNode reverseList4(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode temp = null;
        for (ListNode cur = head; cur != null; cur = cur.next) {
            temp = new ListNode(cur.val, temp);
        }
        return temp;
    }

    public static ListNode reverseList3(ListNode head) {
        return reverse(null, head);
    }

    private static ListNode reverse(ListNode pre, ListNode cur) {
        // 定义退出递归条件
        if (cur == null) {
            return pre;
        }
        final ListNode next = cur.next;
        cur.next = pre;
        return reverse(cur, next);
    }


    public static void printListNode(ListNode head) {
        while (head != null) {
            System.out.print(head.val + "->");
            head = head.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        final ListNode next3 = new ListNode(3);
        final ListNode next2 = new ListNode(2, next3);
        final ListNode next1 = new ListNode(2, next2);
        final ListNode head = new ListNode(1, next1);

        printListNode(head);

        //final ListNode reverseList = reverseList(head);
        //System.out.println();
        //printListNode(reverseList);
        //
        //System.out.println();
        //System.out.println("---plus2---");
        //final ListNode list2 = reverseList2(head);
        //printListNode(list2);
        //
        //System.out.println();
        //System.out.println("---v3---");
        //printListNode(head);
        //final ListNode list3 = reverseList3(head);
        //printListNode(list3);

        final ListNode list4 = reverseList4(head);
        printListNode(list4);
    }
}
