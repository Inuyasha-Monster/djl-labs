package tree;

import java.util.List;

/**
 * @author djl
 */
public class TreeNode {
    private int val;
    private TreeNode left;
    private TreeNode right;


    /**
     * 前序遍历
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
     * 中序遍历
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
     * 后序遍历
     *
     * @param cur
     * @param list
     */
    private void postTraversal(TreeNode cur, List<Integer> list) {
        if (cur == null) {
            return;
        }
        postTraversal(cur.left, list);
        postTraversal(cur.right, list);
        list.add(cur.val);
    }


    public static void main(String[] args) {

    }
}
