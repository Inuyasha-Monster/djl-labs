package tree;

import java.util.*;

/**
 * @author djl
 */
public class NTreeStudy {

    static class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    /**
     * N叉树的前序遍历：中 左 右，迭代法
     *
     * @param root
     * @return
     */
    public List<Integer> preorder(Node root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
            final Node node = stack.pop();
            result.add(node.val);
            if (node.children != null) {
                // 从右到左入栈，才能出栈的时候按照前序进行遍历
                for (int i = 0; i < node.children.size(); i++) {
                    final Node tmp = node.children.get(node.children.size() - 1 - i);
                    stack.push(tmp);
                }
            }
        }
        return result;
    }

    /**
     * 递归法前序遍历N叉树
     *
     * @param root
     * @return
     */
    public List<Integer> preorder2(Node root) {
        final ArrayList<Integer> result = new ArrayList<>();
        preorderImpl(root, result);
        return result;
    }

    private void preorderImpl(Node cur, List<Integer> result) {
        if (cur == null) {
            return;
        }
        result.add(cur.val);
        if (cur.children != null) {
            for (Node child : cur.children) {
                preorderImpl(child, result);
            }
        }
    }

    /**
     * 递归后序遍历N叉树:左 右 中
     *
     * @param root
     * @return
     */
    public List<Integer> postorder(Node root) {
        final ArrayList<Integer> result = new ArrayList<>();
        postorderImpl(root, result);
        return result;
    }

    /**
     * 递归后序
     *
     * @param cur
     * @param result
     */
    private void postorderImpl(Node cur, List<Integer> result) {
        if (cur == null) {
            return;
        }
        // 先处理孩子节点
        if (cur.children != null) {
            for (Node child : cur.children) {
                postorderImpl(child, result);
            }
        }
        // 再处理中间节点
        result.add(cur.val);
    }

    /**
     * 统一迭代法(标记法)后序N叉树：左 右 中
     *
     * @param root
     * @return
     */
    public List<Integer> postorder2(Node root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
            final Node node = stack.pop();
            if (node != null) {
                stack.push(node);
                stack.push(null);

                // 处理孩子因为栈的原因为右到左
                if (node.children != null) {
                    for (int i = 0; i < node.children.size(); i++) {
                        stack.push(node.children.get(node.children.size() - 1 - i));
                    }
                }
            } else {
                final Node pop = stack.pop();
                result.add(pop.val);
            }
        }
        return result;
    }

    public static void main(String[] args) {

    }
}
