package tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author djl
 */
public class TreeNode2 {

    private static class TreeNode {
        private final int val;
        private TreeNode left;
        private TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    private int result = 0;

    /**
     * 获取二叉树的最大深度（通过前序递归实现），因为这里是深度，深度是对比root节点的距离，所以遍历顺序是：中左右
     *
     * @param root
     * @return
     */
    public int getMaxDepth(TreeNode root) {
        getDepth(root, 0);
        return result;
    }

    private void getDepth(TreeNode node, int depth) {
        if (node != null) {
            depth++;
            if (depth > result) {
                result = depth;
            }
            getDepth(node.left, depth);
            getDepth(node.right, depth);
        }
    }


    /**
     * 判断是否平衡二叉树：要求左子树与右子树的高度差不超过1
     * 由于是寻找高度，高度是针对叶子节点的距离，所以采用后序遍历：左右中，层层将结果返回给父层
     *
     * @param root
     * @return
     */
    public boolean isBalanced(TreeNode root) {
        return getHeight(root) != -1;
    }

    /**
     * 获取传入节点的左右节点高度差
     *
     * @param node
     * @return -1 表示不是平衡二叉树
     */
    private int getHeight(TreeNode node) {
        // 确定递归退出条件
        if (node == null) {
            return 0;
        }
        // 确认单层逻辑
        final int leftH = getHeight(node.left); // 左
        if (leftH == -1) {
            return -1;
        }
        final int rightH = getHeight(node.right); // 右
        if (rightH == -1) {
            return -1;
        }
        if (Math.abs(leftH - rightH) > 1) {
            return -1;
        }
        // 特别说明：leetcode 默认叶子节点的高度是1度，所以这里是 1+x
        return 1 + Math.max(leftH, rightH); // 中
    }

    /**
     * 记录二叉树的所有路径，这里由于是从中节点出发，所以这里采用：前序（中左有）
     *
     * @param root
     * @return
     */
    public List<String> binaryTreePaths(TreeNode root) {
        final ArrayList<String> result = new ArrayList<>();
        process(root, new ArrayList<>(), result);
        return result;
    }

    private void process(TreeNode node, List<Integer> path, List<String> result) {

        // 单层逻辑
        path.add(node.val); // 中

        // 退出条件：遍历到叶子节点，然后把path记录到result中
        if (node.left == null && node.right == null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < path.size() - 1; i++) {
                sb.append(path.get(i)).append("->");
            }
            sb.append(path.get(path.size() - 1));
            result.add(sb.toString());
            return;
        }

        if (node.left != null) { // 左
            process(node.left, path, result);
            // 回溯，需要与递归成对
            path.remove(path.size() - 1);
        }
        if (node.right != null) { // 右
            process(node.right, path, result);
            // 回溯，需要与递归成对
            path.remove(path.size() - 1);
        }
    }

    /**
     * 求解二叉树的左叶子的和，左叶子：该节点没有左右孩子，且是父节点的左孩子
     *
     * @param node
     * @return
     */
    public int sumOfLeftLeaves(TreeNode node) {
        // 退出条件
        if (node == null) {
            return 0;
        }
        // 其中叶子节点也直接返回
        if (node.left == null && node.right == null) {
            return 0;
        }
        // 单层逻辑：左 右 中
        int leftSum = sumOfLeftLeaves(node.left); // 计算左子树的左叶子之和

        // 判断当前节点是否可以直接算左子树的左叶子之和
        if (node.left != null && node.left.left == null && node.left.right == null) {
            leftSum = node.left.val;
        }

        final int rightSum = sumOfLeftLeaves(node.right); // 计算右子树的左叶子之和
        return leftSum + rightSum;
    }

    /**
     * 找出该二叉树的 最底层 最左边 节点的值
     * 采用层序的方式：找到最后一层的第一个节点即可
     *
     * @param root
     * @return
     */
    public int findBottomLeftValue(TreeNode root) {
        int result = -1;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            // 当前层的节点数量
            final int size = queue.size();
            for (int i = 0; i < size; i++) {
                final TreeNode node = queue.remove();
                // 将该层的第一个节点值暂定为结果值
                if (i == 0) {
                    result = node.val;
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

    /**
     * 采用递归遍历的方式，其实就是找寻深度最大的节点的值，知识要求先遍历左孩子再遍历右孩子，因为是找最左侧的节点的值
     *
     * @param root
     * @return
     */
    public int findBottomLeftValue2(TreeNode root) {
        findLeftValue(root, 0);
        return value;
    }

    private int Deep = -1;
    private int value = 0;

    private void findLeftValue(TreeNode root, int deep) {
        if (root.left == null && root.right == null) {
            if (deep > Deep) {
                value = root.val;
                Deep = deep;
            }
            return;
        }
        if (root.left != null) {
            deep++;
            findLeftValue(root.left, deep);
            deep--;
        }
        if (root.right != null) {
            deep++;
            findLeftValue(root.right, deep);
            deep--;
        }
    }

    /**
     * 给你二叉树的根节点 root 和一个表示目标和的整数 targetSum 。判断该树中是否存在 根节点到叶子节点 的路径，
     * 这条路径上所有节点值相加等于目标和 targetSum 。如果存在，返回 true ；否则，返回 false 。
     *
     * @param root
     * @param targetSum
     * @return
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        final ArrayList<Integer> results = new ArrayList<>();
        collectPathSum(root, new ArrayList<>(), results);
        for (Integer v : results) {
            if (v == targetSum) {
                return true;
            }
        }
        return false;
    }

    private void collectPathSum(TreeNode node, List<Integer> paths, List<Integer> results) {

        // 中
        paths.add(node.val);

        // 终止条件：叶子节点
        if (node.left == null && node.right == null) {
            int sum = 0;
            for (Integer v : paths) {
                sum += v;
            }
            results.add(sum);
            return;
        }

        if (node.left != null) {
            collectPathSum(node.left, paths, results);
            paths.remove(paths.size() - 1);
        }
        if (node.right != null) {
            collectPathSum(node.right, paths, results);
            paths.remove(paths.size() - 1);
        }
    }

    public static void main(String[] args) {

    }
}
