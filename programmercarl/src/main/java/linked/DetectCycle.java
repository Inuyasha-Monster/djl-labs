package linked;

import java.util.HashSet;
import java.util.Set;

/**
 * @author djl
 */
public class DetectCycle {

    /**
     * 给定一个链表的头节点  head ，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
     * 简单版本：空间换时间
     *
     * @param head
     * @return
     */
    public static ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        ListNode cur = head;
        Set set = new HashSet();
        while (cur != null) {
            if (set.contains(cur)) {
                return cur;
            }
            set.add(cur);
            cur = cur.next;
        }
        return null;
    }

    /**
     * 保证空间复杂度最低（ps：超过时间复杂度了）
     *
     * @param head
     * @return
     */
    public static ListNode detectCycle2(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        // 定义虚拟节点
        ListNode dummy = new ListNode();
        dummy.next = head;
        // 定义当前节点
        ListNode cur = head;
        // 定义循环
        while (true) {
            if (cur.next == null) {
                return null;
            } else {
                // 判断是否成环状: 从链表头开始判断到自己的前一个节点为止
                ListNode tmp = dummy.next;
                while (tmp != null) {
                    if (tmp == cur) {
                        break;
                    }
                    if (tmp == cur.next) {
                        return tmp;
                    }
                    tmp = tmp.next;
                }
            }
            cur = cur.next;
        }
    }

    public static void main(String[] args) {
        ListNode node = new ListNode(1);
        node.next = new ListNode(2, node);
        final ListNode result = detectCycle2(node);
        System.out.println("result = " + result);
    }
}
