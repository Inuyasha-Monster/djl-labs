package linked;

import java.util.HashSet;
import java.util.Set;

/**
 * @author djl
 */
public class GetIntersectionNode {

    /**
     * https://leetcode.cn/problems/intersection-of-two-linked-lists-lcci/
     * 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表没有交点，返回 null 。
     *
     * @param headA
     * @param headB
     * @return
     */
    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        Set<Object> set = new HashSet<>();
        ListNode cur1 = headA;
        while (cur1 != null) {
            set.add(((Object) cur1));
            cur1 = cur1.next;
        }
        ListNode cur2 = headB;
        while (cur2 != null) {
            if (set.contains(cur2)) {
                return cur2;
            }
            cur2 = cur2.next;
        }
        return null;
    }

    public static ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
        ListNode curA = headA;
        ListNode curB = headB;
        int lenA = 0, lenB = 0;
        // 求链表A的长度
        while (curA != null) {
            lenA++;
            curA = curA.next;
        }
        // 求链表B的长度
        while (curB != null) {
            lenB++;
            curB = curB.next;
        }
        curA = headA;
        curB = headB;
        // 让curA为最长链表的头，lenA为其长度
        if (lenB > lenA) {
            //1. swap (lenA, lenB);
            int tmpLen = lenA;
            lenA = lenB;
            lenB = tmpLen;
            //2. swap (curA, curB);
            ListNode tmpNode = curA;
            curA = curB;
            curB = tmpNode;
        }
        // 求长度差
        int gap = lenA - lenB;
        // 让curA和curB在同一起点上（末尾位置对齐）
        while (gap-- > 0) {
            curA = curA.next;
        }
        // 遍历curA 和 curB，遇到相同则直接返回
        while (curA != null) {
            if (curA == curB) {
                return curA;
            }
            curA = curA.next;
            curB = curB.next;
        }
        return null;
    }


    public static void main(String[] args) {
        final ListNode tmp = new ListNode(11);

        ListNode node10 = new ListNode(10, tmp);
        ListNode node1 = new ListNode(1, node10);
        // 2->1->10->11->
        ListNode node2 = new ListNode(2, node1);

        // 22->10->11->
        ListNode node22 = new ListNode(22, node10);

        final ListNode intersectionNode = getIntersectionNode(node22, node2);
        System.out.println("intersectionNode = " + intersectionNode);

        final ListNode intersectionNode2 = getIntersectionNode2(node22, node2);
        System.out.println("intersectionNode2 = " + intersectionNode2);
    }
}
