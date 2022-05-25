package linked;

/**
 * @author djl
 */
public class SwapPairs {

    /**
     * Definition for singly-linked list.
     */
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
    }

    /**
     * 给你一个链表，两两交换其中相邻的节点，并返回交换后链表的头节点。你必须在不修改节点内部的值的情况下完成本题（即，只能进行节点交换）。
     *
     * @param head
     * @return
     */
    public static ListNode swapPairs(ListNode head) {
        if (head == null) {
            return null;
        }
        if (head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode cur = dummy;
        // 定义循环不变量判断条件：保证有连续的三个节点可供交换使用
        while (cur != null && cur.next != null && cur.next.next != null) {
            ListNode left = cur;
            ListNode middle = cur.next;
            ListNode right = middle.next;

            // 交换节点(注意顺序)
            left.next = right;
            middle.next = right.next;
            right.next = middle;

            cur = cur.next.next;
        }
        return dummy.next;
    }

    public static void printListNode(ListNode head) {
        while (head != null) {
            System.out.print(head.val + "->");
            head = head.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        final ListNode next3 = new ListNode(4);
        final ListNode next2 = new ListNode(3, next3);
        final ListNode next1 = new ListNode(2, next2);
        final ListNode head = new ListNode(1, next1);
        printListNode(head);
        final ListNode swapPairs = swapPairs(head);
        printListNode(swapPairs);
    }
}
