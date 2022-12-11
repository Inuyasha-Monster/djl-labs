package tree;

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

    public static void main(String[] args) {

    }
}
