package lintcode;

/**
 * Created by yuank on 9/23/18.
 */
public class LI_944_Maximum_Submatrix {
    /**
         Given an n x n matrix of positive and negative integers, find the submatrix with the largest possible sum.

         Have you met this question in a real interview?
         Example
         Given matrix =
         [
         [1,3,-1],
         [2,3,-2],
         [-1,-2,-3]
         ]
         return 9.

         Explanation:
         the submatrix with the largest possible sum is:
         [
         [1,2],
         [2,3]
         ]
     */

    /**
     * 有两种方法
     * 第一种naive的方法就是矩阵的左上角可以有N^2的可能性，右下角有N^2可能性，
     * 因为，左上角和右下角就可以确定一个矩阵了，所以用嵌套遍历的话，复杂度会是N^4
     *
     * 第二种方法是这个叫Kadane的算法，意思就是可以用N^3的方法来做出来，听起来屌一点。解释起来不难理解，
     * 大概就是可以设置一个左边界和右边界，然后在这个左右边界确定了之后，然后再在行上边变换找子矩阵。
     * 因为左右边界的确定大概需要N^2的时间复杂度，所以需要在行的遍历上边做优化。
     *
     * 下面这个解法是设置上下边界，然后在列上遍历做优化。
     */

    public int maxSubmatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int m = matrix.length;
        int n = matrix[0].length;
        int[][] sums = new int[m + 1][n + 1];

        /**
         * sums[i][j] : for column index in j - 1 in matrix[][], the sum of elements from
         *              matrix[0][j - 1] to matrix[i - 1][j - 1]
         */
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                sums[i][j] = sums[i - 1][j] + matrix[i - 1][j - 1];
            }
        }

        int max = Integer.MIN_VALUE;
        for (int i = 0; i <= m; i++) {
            for (int j = i + 1; j <= m; j++) {
                int cur = 0;
                for (int k = 1; k <= n; k++) {
                    cur += (sums[j][k] - sums[i][k]);
                    max = Math.max(cur, max);
                    cur = Math.max(0, cur);
                }
            }
        }

        return max;
    }
}
