package tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * @author djl
 */
public class TreeNode {
    private final int val;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(int val) {
        this.val = val;
    }


    /**
     * 前序遍历：中左右
     *
     * @param cur
     * @param list
     */
    private void preTraversal(TreeNode cur, List<Integer> list) {
        if (cur == null) {
            return;
        }
        list.add(cur.val);
        preTraversal(cur.left, list);
        preTraversal(cur.right, list);
    }

    /**
     * 通过迭代的方式，实现前序遍历二叉树，主要是通过stack实现
     *
     * @param cur
     * @param list
     */
    private void preTraversalByIterator(TreeNode cur, List<Integer> list) {
        Stack<TreeNode> stack = new Stack<>();
        stack.push(cur);
        // 中 左 右
        while (!stack.isEmpty()) {
            // 弹出栈顶元素
            final TreeNode node = stack.pop();
            // 保存结果
            list.add(node.val);
            // 为什么先压栈右子树，因为栈结构的关系，导致先进后出
            // 这样的话就是先弹出左节点，再弹出右节点，这样就变相的等于：中 左 右
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.right);
            }
        }
    }

    /**
     * 前序遍历：中左右，采用统一的迭代法写法
     * 关键：在于需要在处理的节点前面做标记，所以也叫做标记法
     *
     * @param root
     */
    public List<Integer> preOrder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
            final TreeNode node = stack.pop();
            if (node != null) {
                // 解释一下为啥上述是：先右后左，是因为stack是先进后出，
                // 所以到时候弹出的话就是先left后right，这样就形成了中左右的左右
                if (node.right != null) {
                    stack.push(node.right);
                }
                if (node.left != null) {
                    stack.push(node.left);
                }
                // 这里解释为啥需要把node加入回去stack，是因为把需要处理的节点暂存起来然后在前面用null标记它是需要处理的
                stack.push(node);
                stack.push(null);
            } else {
                final TreeNode handleNode = stack.pop();
                result.add(handleNode.val);
            }
        }
        return result;
    }

    /**
     * 中序遍历：左中右
     *
     * @param cur
     * @param list
     */
    private void middleTraversal(TreeNode cur, List<Integer> list) {
        if (cur == null) {
            return;
        }
        middleTraversal(cur.left, list);
        list.add(cur.val);
        middleTraversal(cur.right, list);
    }

    /**
     * 迭代法中序遍历，由于访问节点的顺序与处理节点的顺序不一致，则需要使用栈来缓冲需要处理的元素
     * 中序：左 中 右
     *
     * @param cur
     * @param list
     */
    private static void middleTraversalByIterator(TreeNode cur, List<Integer> list) {
        Stack<TreeNode> stack = new Stack<>();
        while (cur != null || !stack.empty()) {
            // 如果当前节点不为空，则陆续将节点入栈后设置指针指向左节点
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                // 表示当前节点为null，需要进行弹出操作
                final TreeNode node = stack.pop();
                list.add(node.val);
                cur = node.right;
            }
        }
    }

    /**
     * 后序遍历：左右中
     *
     * @param cur
     * @param list
     */
    private static void postTraversal(TreeNode cur, List<Integer> list) {
        if (cur == null) {
            return;
        }
        postTraversal(cur.left, list);
        postTraversal(cur.right, list);
        list.add(cur.val);
    }

    /**
     * 迭代法后序遍历：左 右 中，在迭代法前序的基础上微调
     *
     * @param cur
     * @param list
     */
    private static void postTraversalByIterator(TreeNode cur, List<Integer> list) {
        Stack<TreeNode> stack = new Stack<>();
        stack.push(cur);
        while (!stack.empty()) {
            final TreeNode node = stack.pop();
            list.add(node.val);
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        Collections.reverse(list);
    }

    public static void main(String[] args) {
        final TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);
        List<Integer> list = new ArrayList<>();
        postTraversal(root, list);
        System.out.println("list = " + list);

        list.clear();
        middleTraversalByIterator(root, list);

        System.out.println("list = " + list);

        list.clear();
        postTraversal(root, list);
        System.out.println("post list = " + list);

        list.clear();
        postTraversalByIterator(root, list);
        System.out.println("list = " + list);
    }
}
