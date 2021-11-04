package leetcode;

public class LE_1504_Count_Submatrices_With_All_Ones {
    /**
     * Given an m x n binary matrix mat, return the number of submatrices that have all ones.
     *
     * Example 1:
     * Input: mat = [[1,0,1],[1,1,0],[1,1,0]]
     * Output: 13
     * Explanation:
     * There are 6 rectangles of side 1x1.
     * There are 2 rectangles of side 1x2.
     * There are 3 rectangles of side 2x1.
     * There is 1 rectangle of side 2x2.
     * There is 1 rectangle of side 3x1.
     * Total number of rectangles = 6 + 2 + 3 + 1 + 1 = 13.
     *
     * Example 2:
     * Input: mat = [[0,1,1,0],[0,1,1,1],[1,1,1,0]]
     * Output: 24
     * Explanation:
     * There are 8 rectangles of side 1x1.
     * There are 5 rectangles of side 1x2.
     * There are 2 rectangles of side 1x3.
     * There are 4 rectangles of side 2x1.
     * There are 2 rectangles of side 2x2.
     * There are 2 rectangles of side 3x1.
     * There is 1 rectangle of side 3x2.
     * Total number of rectangles = 8 + 5 + 2 + 4 + 2 + 2 + 1 = 24.
     *
     * Constraints:
     * 1 <= m, n <= 150
     * mat[i][j] is either 0 or 1.
     *
     * Medium
     *
     * https://leetcode.com/problems/count-submatrices-with-all-ones/
     */

    /**
     * DP
     *
     * Create DP array with padding at the first row and first column
     *
     * 0 0 0 0 0
     * 0 x x x x
     * 0 x x x x
     *
     * dp[i][j] = 在当前i, j的位置下面，从右往左这一行有多少个矩阵
     *
     * Look left and look up.
     *
     * 1.在处理矩阵时，先处理行，再处理列；（向左看，向上看）
     * 2.行向上，只单独考虑单行所形成的新的矩阵；（向左看）dp[i][j]
     * 3.列向上，既要考虑和前面各行所形成的新的列矩阵，也要考虑可能形成的
     * （i+1行，j+1列）的新矩阵。
     * 结论是：取min（dp[i][j],   dp[i-x][j]） （i - x : from i-1 to 0 )
     *
     * https://www.youtube.com/watch?v=EqzTpLSYK6g
     *
     * Time  : O(m * m * n)
     * Space : O(m * n)
     */
    class Solution {
        public int numSubmat(int[][] mat) {
            int m = mat.length;
            int n = mat[0].length;

            if (m == 0 || n == 0) return 0;

            int[][] dp = new int[m + 1][n + 1];
            int res = 0;

            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    /**
                     * only calculate if current element in mat is 1
                     */
                    if (mat[i - 1][j - 1] == 1) {
                        /**
                         * look to left
                         */
                        dp[i][j] = dp[i][j - 1] + 1;
                        res += dp[i][j];

                        /**
                         * look up
                         */
                        int min = dp[i][j];
                        for (int k = i - 1; k >= 0; k--) {
                            min = Math.min(min, dp[k][j]);
                            res += min;
                        }
                    }
                }
            }

            return res;
        }
    }
}
