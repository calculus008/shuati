package leetcode;

public class LE_562_Longest_Line_Of_Consecutive_One_In_Matrix {
    /**
     * Given a 01 matrix M, find the longest line of consecutive one in the matrix.
     * The line could be horizontal, vertical, diagonal or anti-diagonal.
     *
     * Example:
     * Input:
     * [[0,1,1,0],
     * [0,1,1,0],
     * [0,0,0,1]]
     * Output: 3
     *
     * Hint: The number of elements in the given matrix will not exceed 10,000.
     *
     * Medium
     */

    /**
     * 3D DP
     *
     * Key : "line" means straight line.
     *
     * dp[i][j][k] : max length of a straight line ends at cell[i][j] at direction k
     *              k - 0 : horizontal, 1 : vertical, 2 : diagnal, 3 : anti diagnal
     *
     * Transition : dp[i][j][k] = dp[i][j][k] + previous point on the same straight line, if the point exists.
     *
     * Anwser : max of all dp[i][j][k]
     *
     */
    public int longestLine(int[][] M) {
        if (null == M || M.length == 0) return 0;

        int n = M.length;
        int m = M[0].length;

        int[][][] table = new int[n][m][4];
        int max = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (M[i][j] == 0) {
                    continue;
                }

                for (int k = 0; k < 4; k++) table[i][j][k] = 1;

                if (j > 0) {//horizontal
                    table[i][j][0] += table[i][j - 1][0];
                }

                if (i > 0) {//vertical
                    table[i][j][1] += table[i - 1][j][1];
                }

                if (i > 0 && j > 0) {//antiDiagnal
                    table[i][j][2] += table[i - 1][j - 1][2];
                }

                if (i > 0 && j < m - 1) {//Diagnal
                    table[i][j][3] += table[i - 1][j + 1][3];
                }

                max = Math.max(max, table[i][j][0]);
                max = Math.max(max, table[i][j][1]);
                max = Math.max(max, table[i][j][2]);
                max = Math.max(max, table[i][j][3]);

            }
        }

        return max;
    }

    public int longestLine_Practice(int[][] M) {
        if (M == null || M.length == 0) return 0;

        int m = M.length;
        int n = M[0].length;

        int[][][] dp = new int[m][n][4];
        int res = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (M[i][j] == 0) {
                    continue;
                }

                for (int k = 0; k < 4; k++) {
                    dp[i][j][k] = 1;
                }

                if (j - 1 >= 0) {
                    dp[i][j][0] += dp[i][j - 1][0];
                }

                if (i - 1 >= 0) {
                    dp[i][j][1] += dp[i - 1][j][1];
                }

                if (i - 1 >= 0 && j - 1 >= 0) {
                    dp[i][j][2] += dp[i - 1][j - 1][2];
                }

                if (i - 1 >= 0 && j + 1 < n) {
                    dp[i][j][3] += dp[i - 1][j + 1][3];
                }

                res = Math.max(res, dp[i][j][0]);
                res = Math.max(res, dp[i][j][1]);
                res = Math.max(res, dp[i][j][2]);
                res = Math.max(res, dp[i][j][3]);
            }
        }


        return res;
    }

}
