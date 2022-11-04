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
     * 获取二叉树的最大深度（通过前序递归实现）
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

    public static void main(String[] args) {

    }
}
