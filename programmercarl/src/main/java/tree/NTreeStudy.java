package tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
     * 递归法
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

    public static void main(String[] args) {

    }
}
