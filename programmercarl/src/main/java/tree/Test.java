package tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author djl
 */
public class Test {

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    /**
     * 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
     *
     * @param root
     * @return
     */
    public Node connect(Node root) {
        if (root == null) {
            return null;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            final int size = queue.size();
            Stack<Node> stack = new Stack<>();
            for (int i = 0; i < size; i++) {
                final Node node = queue.remove();
                stack.push(node);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            Node pre = null;
            while (!stack.empty()) {
                final Node node = stack.pop();
                node.next = pre;
                pre = node;
            }
        }
        return root;
    }

    public Node connect2(Node root) {
        if (root == null) {
            return null;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {

            final int size = queue.size();

            // 拿出第一个节点
            Node cur = queue.remove();
            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }

            for (int i = 1; i < size; i++) {
                final Node node = queue.remove();
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
                // 将当前节点的next指向node
                cur.next = node;
                // 将当前节点指向node
                cur = node;
            }
        }
        return root;
    }

    public class TreeNode {
        private int val;
        private TreeNode left;
        private TreeNode right;
    }

    /**
     * 直接层序遍历层数就是深度
     *
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int result = 0;
        while (!queue.isEmpty()) {
            final int size = queue.size();
            for (int i = 0; i < size; i++) {
                final TreeNode node = queue.remove();
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            result++;
        }
        return result;
    }

    public static void main(String[] args) {

    }
}
