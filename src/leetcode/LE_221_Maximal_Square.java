package leetcode;

/**
 * Created by yuank on 4/5/18.
 */
public class LE_221_Maximal_Square {
    /**
        Given a 2D binary matrix filled with 0's and 1's,
        find the largest square containing only 1's and return its area.

        For example, given the following matrix:

        1 0 1 0 0
        1 0 1 1 1
        1 1 1 1 1
        1 0 0 1 0
        Return 4.

     */

    //DP, Time and Space : O(m * n)

    //Similar and more difficult problem at LE_85
    //https://www.youtube.com/watch?v=vkFUB--OYy0&t=186s
    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;

        int m = matrix.length;
        int n = matrix[0].length;

        /**
         * dp[x][y] : 如果以matrix[x - 1][y - 1]为正方形bottom right顶点所形成的所有正方形中，边长最大的那个正方形的边长的值。
         * 注意 ： 是边长的值。
         * **/
        int[][] dp = new int[m + 1][n + 1];
        int res = 0;

        //!!! "<=" !!!
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (matrix[i - 1][j - 1] == '1') {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                }
                res = Math.max(res, dp[i][j]);
            }
        }

        return res * res;
    }
}
