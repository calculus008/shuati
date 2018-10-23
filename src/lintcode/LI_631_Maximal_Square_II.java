package lintcode;

/**
 * Created by yuank on 10/23/18.
 */
public class LI_631_Maximal_Square_II {
    /**
         Description
         Given a 2D binary matrix filled with 0's and 1's,
         find the largest square which diagonal is all 1 and others is 0.

         Only consider the main diagonal situation.

         Have you met this question in a real interview?
         Example
         For example, given the following matrix:

         1 0 1 0 0
         1 0 0 1 0
         1 1 0 0 1
         1 0 0 1 0

         Return 9

         1  0  0

         0  1  0

         0  0  1

         Related problem
         LE_221_Maximal_Square (lint 436)
     */

    /**
     * 设d(i, j)为以(i, j)为右下角正方形的最大边长。
     * 设up(i, j) 为从上往下0的长度，(相当于正方形的一条边)，
     * 设left(i, j）为从左往右0的长度(相当于正方形的另一条边)，
     * dp(i - 1, j - 1)控制对角线。
     *
     * 能否从(i - 1, j - 1)扩大到(i, j)取决于up(i - 1, j), left(i, j - 1)和dp(i - 1, j - 1)的最小值。
     */
    public int maxSquare2(int[][] matrix) {
        int n = matrix.length;
        if (n == 0)
            return 0;

        int m = matrix[0].length;
        if (m == 0)
            return 0;

        int[][] f = new int[n][m];//以f[i][j]为右下角的所有正方形中，符合条件(对角为1，其余为零)的那个正方形的边长。
        int[][] u = new int[n][m];//为从上往下0的长度
        int[][] l = new int[n][m];//为从左往右0的长度

        int length = 0;
        for (int i = 0; i < n; ++i)
            for (int j = 0; j < m; ++j) {
                if (matrix[i][j] == 0) {
                    f[i][j] = 0;
                    u[i][j] = l[i][j] = 1;

                    if (i > 0) {
                        u[i][j] = u[i - 1][j] + 1;
                    }

                    if (j > 0) {
                        l[i][j] = l[i][j - 1] + 1;
                    }
                } else {
                    u[i][j] = l[i][j] = 0;
                    if (i > 0 && j > 0) {
                        f[i][j] = Math.min(f[i - 1][j - 1], Math.min(u[i - 1][j], l[i][j - 1])) + 1;
                    } else {
                        f[i][j] = 1;
                    }
                }
                length = Math.max(length, f[i][j]);
            }
        return length * length;
    }
}
