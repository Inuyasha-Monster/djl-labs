package tree;

import java.util.*;

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
     * 中序：左 中 右
     *
     * @param root
     * @return
     */
    public List<Integer> middleOrder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
            final TreeNode node = stack.pop();
            if (node != null) {
                // 因为栈的缘故，入栈顺序为：右 中 左，所以出栈的顺序：左 中 右边
                if (node.right != null) {
                    stack.push(node.right);
                }

                stack.push(node);
                stack.push(null);

                if (node.left != null) {
                    stack.push(node.left);
                }
            } else {
                // 如果node为null表示下一个节点就是需要处理的节点
                final TreeNode waitHandle = stack.pop();
                result.add(waitHandle.val);
            }
        }
        return result;
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

    /**
     * 统一迭代法（标记法）后序遍历：左 右 中
     *
     * @param root
     * @return
     */
    public List<Integer> postOrder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
            final TreeNode node = stack.pop();
            if (node != null) {

                stack.push(node);
                stack.push(null);

                if (node.right != null) {
                    stack.push(node.right);
                }

                if (node.left != null) {
                    stack.push(node.left);
                }
            } else {
                // 如果node为null表示下一个节点就是需要处理的节点
                final TreeNode waitHandle = stack.pop();
                result.add(waitHandle.val);
            }
        }
        return result;
    }

    /**
     * 层序遍历二叉树，借助队列实现层的分割
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            final int size = queue.size();
            for (int i = 0; i < size; i++) {
                final TreeNode node = queue.remove();
                list.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            result.add(list);
        }
        return result;
    }

    /**
     * 递归实现层序遍历
     *
     * @param root
     * @return
     */
    public static List<List<Integer>> levelOrder2(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        internalHandle(root, 0, result);
        return result;
    }

    /**
     * 递归三部曲：定义方法的参数和返回值；定义退出条件；定义单层逻辑
     *
     * @param cur
     * @param deep
     * @param result
     */
    private static void internalHandle(TreeNode cur, int deep, List<List<Integer>> result) {
        if (cur == null) {
            return;
        }
        // 默认从0开始，所以我们从第一层开始
        deep++;
        // 如果结果集长度小于深度，则增加一层结果用于存放当层二叉树节点
        if (result.size() < deep) {
            List<Integer> list = new ArrayList<>();
            result.add(list);
        }
        result.get(deep - 1).add(cur.val);
        // 单层逻辑递归处理
        internalHandle(cur.left, deep, result);
        internalHandle(cur.right, deep, result);
    }

    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            final int size = queue.size();
            for (int i = 0; i < size; i++) {
                final TreeNode node = queue.remove();
                list.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            result.add(list);
        }
        Collections.reverse(result);
        return result;
    }

    public List<Integer> rightSideView(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return Collections.emptyList();
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            final int size = queue.size();
            for (int i = 0; i < size; i++) {
                final TreeNode node = queue.remove();
                list.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            result.add(list);
        }
        // 从每层结果集中取最末尾的节点形成最后结果返回
        List<Integer> nums = new ArrayList<>();
        for (List<Integer> list : result) {
            nums.add(list.get(list.size() - 1));
        }
        return nums;
    }

    public List<Integer> rightSideView2(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            final int size = queue.size();
            for (int i = 0; i < size; i++) {
                final TreeNode node = queue.remove();
                // 如果出队的是最后一个元素则加入结果集
                if (i == size - 1) {
                    result.add(node.val);
                }
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
        return result;
    }

    public List<Double> averageOfLevels(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return Collections.emptyList();
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            final int size = queue.size();
            for (int i = 0; i < size; i++) {
                final TreeNode node = queue.remove();
                list.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            result.add(list);
        }
        // 从每层结果集中取最末尾的节点形成最后结果返回
        List<Double> nums = new ArrayList<>();
        for (List<Integer> list : result) {
            double total = 0;
            for (Integer num : list) {
                total += num;
            }
            nums.add(total / list.size());
        }
        return nums;
    }

    public List<Double> averageOfLevels2(TreeNode root) {
        List<Double> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            double total = 0;
            final int size = queue.size();
            for (int i = 0; i < size; i++) {
                final TreeNode node = queue.remove();
                total += node.val;
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            result.add(total / size);
        }
        return result;
    }

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
     * 层序遍历多叉树
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            // 单层结果集
            List<Integer> list = new ArrayList<>();
            // 单层节点数量
            final int size = queue.size();
            for (int i = 0; i < size; i++) {
                final Node node = queue.remove();
                list.add(node.val);
                // 遍地当前节点的所有孩子
                if (node.children != null) {
                    queue.addAll(node.children);
                }
            }
            result.add(list);
        }
        return result;
    }

    public List<Integer> largestValues(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            // 单层结果
            int max = queue.peek().val;
            // 单层节点数量
            final int size = queue.size();
            for (int i = 0; i < size; i++) {
                final TreeNode node = queue.remove();
                if (max < node.val) {
                    max = node.val;
                }
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            result.add(max);
        }
        return result;
    }

    /**
     * 递归判断是否对称二叉树
     * 1、确定参数和返回值
     * 2、确定退出条件
     * 3、定义单层逻辑
     *
     * @param left  左侧节点
     * @param right 右侧节点
     * @return 是否对称
     */
    private boolean test(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        } else if (left == null) {
            return false;
        } else if (right == null) {
            return false;
        } else if (left.val != right.val) {
            return false;
        }
        final boolean b1 = test(left.left, right.right);
        final boolean b2 = test(left.right, right.left);
        return b1 && b2;
    }

    public boolean isSymmetric(TreeNode root) {
        return test(root.left, root.right);
    }

    /**
     * 迭代法通过队列实现
     *
     * @param root
     * @return
     */
    public boolean isSymmetric2(TreeNode root) {
        if (root == null) {
            return false;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root.left);
        queue.add(root.right);
        while (!queue.isEmpty()) {
            final TreeNode leftNode = queue.remove();
            final TreeNode rightNode = queue.remove();
            if (leftNode == null && rightNode == null) {
                continue;
            }

            if (leftNode == null || rightNode == null || leftNode.val != rightNode.val) {
                return false;
            }

            queue.add(leftNode.left);
            queue.add(rightNode.right);

            queue.add(leftNode.right);
            queue.add(rightNode.left);
        }
        return true;
    }

    /**
     * 统计完全二叉树的节点数量(先按照普通二叉树的层序遍历求解)
     *
     * @param root
     * @return
     */
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int count = 0;
        while (!queue.isEmpty()) {
            final int size = queue.size();
            for (int i = 0; i < size; i++) {
                final TreeNode node = queue.remove();
                count++;
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
        return count;
    }

    /**
     * 递归后序求解
     *
     * @param root
     * @return
     */
    public int countNodes2(TreeNode root) {
        return countNodes2Impl(root);
    }

    /**
     * 按照普通二叉树后序递归
     *
     * @param node
     * @return
     */
    public int countNodes2Impl(TreeNode node) {
        if (node == null) {
            return 0;
        }
        final int l = countNodes2Impl(node.left);
        final int r = countNodes2Impl(node.right);
        return 1 + l + r;
    }

    /**
     * 按照完全二叉树的递归
     *
     * @param node
     * @return
     */
    public int countNodes3Impl(TreeNode node) {
        // 思路：由于是完全二叉树，所以比较「左子树的深度」等于「右子树的深度」则为满二叉树
        // 然后通过满二叉树的公示求解：2^n - 1
        if (node == null) {
            return 0;
        }
        TreeNode left = node.left;
        TreeNode right = node.right;
        // 分别求解左右子树的深度
        int leftDepth = 0;
        int rightDepth = 0;
        while (left != null) {
            left = left.left;
            leftDepth++;
        }
        while (right != null) {
            right = right.right;
            rightDepth++;
        }
        if (leftDepth == rightDepth) {
            return (2 << leftDepth) - 1;
        }
        return countNodes3Impl(node.left) + countNodes3Impl(node.right) + 1;
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

        final List<List<Integer>> lists = levelOrder2(root);
        System.out.println("lists = " + lists);
    }
}
