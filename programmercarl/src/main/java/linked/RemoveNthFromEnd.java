package linked;

/**
 * @author djl
 */
public class RemoveNthFromEnd {
    public static void main(String[] args) {
        final ListNode next3 = new ListNode(4);
        final ListNode next2 = new ListNode(3, next3);
        final ListNode next1 = new ListNode(2, next2);
        final ListNode head = new ListNode(1, next1);
        LinkedUtil.printListNode(head);

        //final ListNode removeNthFromEnd = removeNthFromEnd(head, 2);
        //LinkedUtil.printListNode(removeNthFromEnd);

        final ListNode nthFromEnd = removeNthFromEnd2(head, -10);
        LinkedUtil.printListNode(nthFromEnd);
    }

    /**
     * https://leetcode.cn/problems/remove-nth-node-from-end-of-list/
     * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
     *
     * @param head
     * @param n
     * @return
     */
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return null;
        }
        if (n <= 0) {
            return head;
        }
        int count = 0;
        ListNode cur = head;
        while (cur != null) {
            count++;
            cur = cur.next;
        }
        int index = count - n;
        if (index < 0) {
            return head;
        }

        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode pre = dummy;
        ListNode current = head;
        while (index >= 0) {
            if (index == 0) {
                pre.next = current.next;
                current.next = null;
                break;
            }
            pre = current;
            current = current.next;
            index--;
        }
        return dummy.next;
    }

    /**
     * 使用快慢指针求解
     *
     * @param head
     * @param n
     * @return
     */
    public static ListNode removeNthFromEnd2(ListNode head, int n) {
        if (head == null) {
            return null;
        }
        if (n <= 0) {
            return head;
        }
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode slow = dummy;
        ListNode fast = dummy;
        // 移动fast指针到n+1位置
        int temp = n + 1;
        for (int i = 0; i < n + 1; i++) {
            if (fast == null) {
                break;
            }
            fast = fast.next;
            temp--;
        }
        if (fast == null && temp > 0) {
            return dummy.next;
        }
        // 同时移动fast+slow直到fast为null
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        // 删除节点
        slow.next = slow.next.next;
        return dummy.next;
    }
}
