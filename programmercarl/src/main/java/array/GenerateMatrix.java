package array;

import java.util.Arrays;

/**
 * @author djl
 */
public class GenerateMatrix {
    public static void main(String[] args) {
        final int[][] matrix = generateMatrix(5);
        for (int[] ints : matrix) {
            System.out.println(Arrays.toString(ints));
        }
    }

    /**
     * https://leetcode.cn/problems/spiral-matrix-ii/
     *
     * @param n
     * @return
     */
    public static int[][] generateMatrix(int n) {
        // 申请二维数组
        int[][] res = new int[n][n];
        // 定义循环次数（一圈代表一次），例如：n=4，则loop=2；n=3，则loop=1
        int loop = n / 2;
        // 定义开始坐标
        int startRowIndex = 0;
        int startColIndex = 0;
        // 定义偏移量
        int offset = 1;
        // 定义填充数字
        int count = 1;
        // 计算中间位置
        int mid = n / 2;
        // 定义循环不变量规则(一圈一圈的填充数字)，且全程都是：左闭右开 的区间模式
        while (loop > 0) {
            int i = startRowIndex;
            int j = startColIndex;

            // 上方：从左到右进行填充，也就是行坐标不动，列坐标移动，例如：n=3；1 2
            for (; j < startColIndex + n - offset; j++) {
                res[i][j] = count++;
            }

            // 右边：从上到下的填充，列坐标不动，行坐标移动，例如：n=3；3 4
            for (; i < startRowIndex + n - offset; i++) {
                res[i][j] = count++;
            }

            // 下边：从右到左填充，行坐标不动，列坐标移动，例如：n=3；6 5
            for (; j > startColIndex; j--) {
                res[i][j] = count++;
            }

            // 左边：从下到上
            for (; i > startRowIndex; i--) {
                res[i][j] = count++;
            }

            loop--;
            startRowIndex++;
            startColIndex++;
            offset += 2;
        }
        // 如果n是奇数则填充中间数字
        if (n % 2 == 1) {
            res[mid][mid] = count;
        }
        return res;
    }
}
