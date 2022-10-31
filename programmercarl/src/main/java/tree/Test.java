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

    /**
     * 后序求解树的高度
     *
     * @param node
     * @return
     */
    private int getMaxHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }
        final int leftH = getMaxHeight(node.left);
        final int rightH = getMaxHeight(node.right);
        return 1 + Math.max(leftH, rightH);
    }

    /**
     * 递归求最大深度，其实就是求树的高度
     *
     * @param root
     * @return
     */
    public int maxDepth2(TreeNode root) {
        return getMaxHeight(root);
    }

    /**
     * 二叉树的最小深度：root节点到最近的叶子节点的节点数量
     *
     * @param root
     * @return
     */
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int minDepth = 0;
        while (!queue.isEmpty()) {
            final int size = queue.size();
            minDepth++;
            for (int i = 0; i < size; i++) {
                final TreeNode node = queue.remove();
                // 如果是叶子节点直接返回结果
                if (node.left == null && node.right == null) {
                    return minDepth;
                }
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
        return minDepth;
    }

    /**
     * 后序遍历：左 右 中，因为这里需要从叶子节点开始层层将结果返回到上一层
     *
     * @param node
     * @return
     */
    private int getMinHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }
        final int leftH = getMinHeight(node.left);
        final int rightH = getMinHeight(node.right);
        // 注意最小深度需要额外判断当前节点是否存在左右节点
        if (node.right == null) {
            return 1 + leftH;
        }
        if (node.left == null) {
            return 1 + rightH;
        }
        return 1 + Math.min(leftH, rightH);
    }

    public int minDepth2(TreeNode root) {
        return getMinHeight(root);
    }

    /**
     * 反转二叉树，根据前序进行处理：中 左 右边
     *
     * @param root
     * @return
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        swap(root);
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }

    private void swap(TreeNode cur) {
        final TreeNode tmp = cur.left;
        cur.left = cur.right;
        cur.right = tmp;
    }

    public static void main(String[] args) {

    }
}
