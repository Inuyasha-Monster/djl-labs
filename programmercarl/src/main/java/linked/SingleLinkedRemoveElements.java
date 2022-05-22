package linked;

/**
 * @author djl
 */
public class SingleLinkedRemoveElements {
    public static void main(String[] args) {
        final ListNode next3 = new ListNode(3);
        final ListNode next2 = new ListNode(2, next3);
        final ListNode next1 = new ListNode(2, next2);
        final ListNode head = new ListNode(1, next1);
        printListNode(head);

        System.out.println();
        final ListNode elements = removeElements(head, 2);
        printListNode(elements);

        System.out.println();
        final ListNode elements2 = removeElements2(head, 2);
        printListNode(elements2);
    }

    public static void printListNode(ListNode head) {
        while (head != null) {
            System.out.print(head.val + "->");
            head = head.next;
        }
    }

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
     * https://leetcode.cn/problems/remove-linked-list-elements/
     * 给你一个链表的头节点 head 和一个整数 val ，请你删除链表中所有满足 Node.val == val 的节点，并返回 新的头节点 。
     *
     * @param head
     * @param val
     * @return
     */
    public static ListNode removeElements(ListNode head, int val) {
        // 不使用虚拟头节点的方式

        // 首先考虑头节点就是需要移除的节点情况
        while (head != null && head.val == val) {
            head = head.next;
        }
        // 如果为null直接返回
        if (head == null) {
            return null;
        }

        // 确定了此时的head的val!=val
        ListNode pre = head;
        ListNode cur = head.next;
        while (cur != null) {
            if (cur.val == val) {
                // 关键：将pre的next指向cur的next对象，删除当前节点
                pre.next = cur.next;
            } else {
                pre = cur;
            }
            cur = cur.next;
        }
        return head;
    }

    /**
     * 采用虚拟头节点的方式
     *
     * @param head
     * @param val
     * @return
     */
    public static ListNode removeElements2(ListNode head, int val) {
        if (head == null) {
            return null;
        }
        ListNode dummy = new ListNode(-1, head);
        ListNode pre = dummy;
        ListNode cur = head;
        while (cur != null) {
            if (cur.val == val) {
                pre.next = cur.next;
            } else {
                pre = cur;
            }
            cur = cur.next;
        }
        return dummy.next;
    }

}
