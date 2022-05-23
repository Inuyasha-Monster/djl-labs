package linked;

/**
 * @author djl
 */
public class MyLinkedListTest {

    static class MyLinkedList {

        class ListNode {
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
         * 定义虚拟节点
         */
        private final ListNode dummy = new ListNode();

        public MyLinkedList() {

        }

        /**
         * 获取链表中第 index 个节点的值。如果索引无效，则返回-1。
         * dummy->1->2->4->null index=1，result=2；index=3，result=-1
         *
         * @param index
         * @return
         */
        public int get(int index) {
            if (index < 0 || dummy.next == null) {
                return -1;
            }
            ListNode cur = dummy.next;
            while (cur != null) {
                if (index == 0) {
                    return cur.val;
                }
                cur = cur.next;
                index--;
            }
            return -1;
        }

        /**
         * addAtHead(val)：在链表的第一个元素之前添加一个值为 val 的节点。插入后，新节点将成为链表的第一个节点。
         *
         * @param val
         */
        public void addAtHead(int val) {
            final ListNode head = dummy.next;
            if (head == null) {
                dummy.next = new ListNode(val);
                return;
            }
            dummy.next = new ListNode(val, head);
        }

        /**
         * addAtTail(val)：将值为 val 的节点追加到链表的最后一个元素。
         *
         * @param val
         */
        public void addAtTail(int val) {
            final ListNode head = dummy.next;
            if (head == null) {
                dummy.next = new ListNode(val);
                return;
            }
            ListNode cur = dummy.next;
            while (cur.next != null) {
                cur = cur.next;
            }
            cur.next = new ListNode(val);
        }

        /**
         * addAtIndex(index,val)：在链表中的第 index 个节点之前添加值为 val 的节点。如果 index 等于链表的长度，
         * 则该节点将附加到链表的末尾。如果 index 大于链表长度，则不会插入节点。如果index小于0，则在头部插入节点。
         *
         * @param index
         * @param val
         */
        public void addAtIndex(int index, int val) {
            if (index < 0) {
                addAtTail(val);
                return;
            }
            ListNode cur = dummy.next;
            ListNode prev = dummy;
            while (cur != null) {
                if (index == 0) {
                    prev.next = new ListNode(val, cur);
                    return;
                }
                prev = cur;
                cur = cur.next;
                index--;
            }
            if (index == 0) {
                prev.next = new ListNode(val);
            }
        }

        /**
         * deleteAtIndex(index)：如果索引 index 有效，则删除链表中的第 index 个节点。
         *
         * @param index
         */
        public void deleteAtIndex(int index) {
            if (index < 0) {
                return;
            }
            ListNode cur = dummy.next;
            ListNode prev = dummy;
            while (cur != null) {
                if (index == 0) {
                    prev.next = cur.next;
                    return;
                }
                prev = cur;
                cur = cur.next;
                index--;
            }
        }
    }

    public static void main(String[] args) {
        //MyLinkedList myLinkedList = new MyLinkedList();
        //myLinkedList.addAtHead(1);
        //myLinkedList.addAtHead(2);
        //myLinkedList.addAtHead(3);
        //myLinkedList.addAtHead(4);
        //final int val = myLinkedList.get(0);
        //System.out.println("val = " + val);
        //
        //myLinkedList.addAtTail(5);
        //final int val2 = myLinkedList.get(2);
        //System.out.println("val2 = " + val2);
        //
        //System.out.println("---addAtIndex---");
        //myLinkedList.addAtIndex(2, 8);
        //final int val3 = myLinkedList.get(3);
        //System.out.println("val3 = " + val3);
        //
        //myLinkedList.deleteAtIndex(3);
        //final int val4 = myLinkedList.get(3);
        //System.out.println("val4 = " + val4);

        MyLinkedList myLinkedList = new MyLinkedList();
        myLinkedList.addAtHead(7);
        myLinkedList.addAtHead(2);
        myLinkedList.addAtHead(1);
        myLinkedList.addAtIndex(3, 0);
        myLinkedList.deleteAtIndex(2);
        myLinkedList.addAtHead(6);
        myLinkedList.addAtTail(4);
        final int val = myLinkedList.get(4);
        System.out.println("val = " + val);
        myLinkedList.addAtHead(4);
        myLinkedList.addAtIndex(5, 0);
        myLinkedList.addAtHead(6);
    }
}
