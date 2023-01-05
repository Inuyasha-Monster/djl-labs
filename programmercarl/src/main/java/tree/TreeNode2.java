package tree;

import java.util.*;

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
     * https://leetcode.cn/problems/path-sum/
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
        // 再遍历一遍对比 targetSum
        for (Integer v : results) {
            if (v == targetSum) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param node    当前节点
     * @param paths   跟节点到叶子节点的路径
     * @param results 结果集
     */
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
            // 关键:递归之后紧跟回溯,需要移除尾部元素
            paths.remove(paths.size() - 1);
        }
        if (node.right != null) {
            collectPathSum(node.right, paths, results);
            paths.remove(paths.size() - 1);
        }
    }

    /**
     * 根据传入的数组构建一颗最大二叉树,注意:数组长度大于0,且数组元素为正整数
     * https://leetcode.cn/problems/maximum-binary-tree/
     *
     * @param nums
     * @return
     */
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        // 备注:构建一颗二叉树,由于是从root节点开始的,所以我们的遍历顺序都是前序:中左右
        if (nums.length == 1) {
            return new TreeNode(nums[0]);
        }

        // 找到数组的最大元素以及坐标
        int maxValue = 0;
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > maxValue) {
                maxValue = nums[i];
                index = i;
            }
        }

        TreeNode root = new TreeNode(maxValue);
        // 构建左子树,需要判断是否有左数组
        if (index > 0) {
            // 构建左子树所需要的数组
            int[] leftNums = new int[index];
            for (int i = 0; i < leftNums.length; i++) {
                leftNums[i] = nums[i];
            }
            root.left = constructMaximumBinaryTree(leftNums);
        }
        // 构建右子树,需要判断是否有右数组
        if (index < nums.length - 1) {
            int[] rightNums = new int[nums.length - 1 - index];
            for (int i = index + 1, j = 0; i < nums.length; i++, j++) {
                rightNums[j] = nums[i];
            }
            root.right = constructMaximumBinaryTree(rightNums);
        }
        return root;
    }

    /**
     * 构建最大二叉树,优化版:省略数据拷贝过程
     *
     * @param nums [3,2,1,6,0,5]
     * @return
     */
    public TreeNode constructMaximumBinaryTree2(int[] nums) {
        return internal(nums, 0, nums.length);
    }

    private TreeNode internal(int[] nums, int start, int end) {
        // 备注:构建一颗二叉树,由于是从root节点开始的,所以我们的遍历顺序都是前序:中左右

        if (end - start < 1) {
            return null;
        }

        if (end - start == 1) {
            return new TreeNode(nums[start]);
        }

        // 找到数组的最大元素以及坐标
        int maxValue = 0;
        int index = 0;
        for (int i = start; i < end; i++) {
            if (nums[i] > maxValue) {
                maxValue = nums[i];
                index = i;
            }
        }

        TreeNode root = new TreeNode(maxValue);

        // 注意区间
        root.left = internal(nums, start, index);
        root.right = internal(nums, index + 1, end);

        return root;
    }

    /**
     * 根据中序数组和后序数组构建还原二叉树
     * https://leetcode.cn/problems/construct-binary-tree-from-inorder-and-postorder-traversal/
     *
     * @param inorder
     * @param postorder
     * @return
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        // 举例:inorder = [9,3,15,20,7], postorder = [9,15,7,20,3]
        /**
         * 由于后序数组末尾是root节点
         * 然后根据root节点在中序数组进行切分
         * 再根据切分的左中序和右中序,在后序数组中切分
         * 分别将切分的左结果赋予左节点,右结果赋予右节点
         */

        if (postorder.length == 0) {
            return null;
        }

        int rootValue = postorder[postorder.length - 1];
        TreeNode root = new TreeNode(rootValue);
        // 如果是叶子节点直接返回
        if (postorder.length == 1) {
            return root;
        }

        // 寻找中序数组的切割点
        int delimiterIndex = -1;
        for (int i = 0; i < inorder.length; i++) {
            if (inorder[i] == rootValue) {
                delimiterIndex = i;
                break;
            }
        }

        // 切割中序数组为:左中序数组 和 右中序数组
        int[] leftInorder = new int[delimiterIndex];
        for (int i = 0; i < leftInorder.length; i++) {
            leftInorder[i] = inorder[i];
        }
        int[] rightInorder = new int[inorder.length - 1 - delimiterIndex];
        for (int i = delimiterIndex + 1, j = 0; i < inorder.length; i++, j++) {
            rightInorder[j] = inorder[i];
        }

        // 切后序数组之前,将已经用过的末尾元素删掉
        int[] newPostorder = new int[postorder.length - 1];
        System.arraycopy(postorder, 0, newPostorder, 0, postorder.length - 1);
        postorder = newPostorder;

        // 根据左右中序数组长度来切割后序数组
        int[] leftPostorder = new int[leftInorder.length];
        for (int i = 0; i < leftPostorder.length; i++) {
            leftPostorder[i] = postorder[i];
        }
        int[] rightPostorder = new int[rightInorder.length];
        for (int i = 0, j = leftPostorder.length; i < rightPostorder.length; i++, j++) {
            rightPostorder[i] = postorder[j];
        }

        // 构建左孩子
        root.left = buildTree(leftInorder, leftPostorder);
        root.right = buildTree(rightInorder, rightPostorder);
        return root;
    }

    /**
     * 根据中序数组和后序数组构建还原二叉树(优化版:省略数据拷贝过程)
     *
     * @param inorder
     * @param postorder
     * @return
     */
    public TreeNode buildTree2(int[] inorder, int[] postorder) {
        map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) { // 用map保存中序序列的数值对应位置
            map.put(inorder[i], i);
        }
        // 左闭右开
        return findNode(inorder, 0, inorder.length, postorder, 0, postorder.length);
    }

    /**
     * 中序的节点值-数组索引的映射表
     */
    Map<Integer /*value*/, Integer /*index*/> map;  // 方便根据数值查找位置

    public TreeNode findNode(int[] inorder, int inBegin, int inEnd, int[] postorder, int postBegin, int postEnd) {
        // 参数里的范围都是:左闭右开
        if (inBegin >= inEnd || postBegin >= postEnd) {  // 不满足左闭右开，说明没有元素，返回空树
            return null;
        }
        int rootIndex = map.get(postorder[postEnd - 1]);  // 找到后序遍历的最后一个元素在中序遍历中的位置
        TreeNode root = new TreeNode(inorder[rootIndex]);  // 构造结点
        int lenOfLeft = rootIndex - inBegin;  // 保存中序左子树个数，用来确定后序数列的个数

        root.left = findNode(inorder, inBegin, rootIndex,
                postorder, postBegin, postBegin + lenOfLeft);
        root.right = findNode(inorder, rootIndex + 1, inEnd,
                postorder, postBegin + lenOfLeft, postEnd - 1); // postEnd - 1 表示忽略最后一个已经用过的元素

        return root;
    }

    public static void main(String[] args) {

    }
}
