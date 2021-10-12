package leetcode;

public class LE_1314_Matrix_Block_Sum {
    /**
     * Given a m x n matrix mat and an integer k, return a matrix answer where each answer[i][j] is the sum of all
     * elements mat[r][c] for:
     *
     * i - k <= r <= i + k,
     * j - k <= c <= j + k, and
     * (r, c) is a valid position in the matrix.
     *
     *
     * Example 1:
     * Input: mat = [[1,2,3],[4,5,6],[7,8,9]], k = 1
     * Output: [[12,21,16],[27,45,33],[24,39,28]]
     *
     * Example 2:
     * Input: mat = [[1,2,3],[4,5,6],[7,8,9]], k = 2
     * Output: [[45,45,45],[45,45,45],[45,45,45]]
     *
     * Constraints:
     * m == mat.length
     * n == mat[i].length
     * 1 <= m, n, k <= 100
     * 1 <= mat[i][j] <= 100
     *
     * Medium
     *
     * https://leetcode.com/problems/matrix-block-sum/
     */

    /**
     * https://leetcode.com/problems/matrix-block-sum/discuss/477036/JavaPython-3-PrefixRange-sum-w-analysis-similar-to-LC-30478
     *
     * Same method of using rangeSum as in:
     *
     * LE_304_Range_Sum_Query_2D_Immutable
     * LE_307_Range_Sum_Query_Mutable
     * LE_308_Range_Sum_Query_2D_Mutable
     *
     * 1.rangeSum[i + 1][j + 1] corresponds to cell (i, j);
     * 2.rangeSum[0][j] and rangeSum[i][0] are all dummy values, which are used for the convenience of computation of\
     *   DP state transmission formula.
     *
     * rangeSum:
     * +-----+-+-------+     +--------+-----+     +-----+---------+     +-----+--------+
     * |     | |       |     |        |     |     |     |         |     |     |        |
     * |     | |       |     |        |     |     |     |         |     |     |        |
     * +-----+-+       |     +--------+     |     |     |         |     +-----+        |
     * |     | |       |  =  |              |  +  |     |         |  -  |              | + mat[i][j]
     * +-----+-+       |     |              |     +-----+         |     |              |
     * |               |     |              |     |               |     |              |
     * |               |     |              |     |               |     |              |
     * +---------------+     +--------------+     +---------------+     +--------------+
     *
     * rangeSum[i+1][j+1] =  rangeSum[i][j+1] + rangeSum[i+1][j]    -   rangeSum[i][j]   + mat[i][j]
     *
     * +---------------+   +--------------+   +---------------+   +--------------+   +--------------+
     * |               |   |         |    |   |   |           |   |         |    |   |   |          |
     * |   (r1,c1)     |   |         |    |   |   |           |   |         |    |   |   |          |
     * |   +------+    |   |         |    |   |   |           |   +---------+    |   +---+          |
     * |   |      |    | = |         |    | - |   |           | - |      (r1,c2) | + |   (r1,c1)    |
     * |   |      |    |   |         |    |   |   |           |   |              |   |              |
     * |   +------+    |   +---------+    |   +---+           |   |              |   |              |
     * |        (r2,c2)|   |       (r2,c2)|   |   (r2,c1)     |   |              |   |              |
     * +---------------+   +--------------+   +---------------+   +--------------+   +--------------+
     *
     *  res[i][j] =        rangeSum[r2][c2] - rangeSum[r2][c1] - rangeSum[r1][c2]  + rangeSum[r1][c1]
     *
     * A little tricky to understand the logic of calulating r1 , c1:
     * Suppose K=1 and that j=2. Then c1=1 and c2=3.
     * So for a given r2 (eg. 10), there are 3 "squares": (10, 1) (10, 2) (10, 3) between c1 and c3. When applying the
     * formula, you want the sum to include all these 3 squares. So you want sums[10, 3]. But you don't want to subtract
     * sums[10, 1] from it because then you would skip counting the square (10, 1). So instead you want to subtract
     * sums[10, 0] and that's why sums[r2, c1 - 1].
     *
     * In other words, as we show above:
     *
     * r1* = i - k - 1
     * c1* = j - k - 1
     *
     * Since we use padding in rangeSum DP array, its index becomes :
     * r1 = r1* + 1 = i - k - 1 + 1 = i - k
     * c1 = c1* + 1 = j - k - 1 + 1 = j - k
     *
     * Time and Space : O(m * n)
     */
    class Solution {
        public int[][] matrixBlockSum(int[][] mat, int k) {
            int m = mat.length;
            int n = mat[0].length;
            int[][] rangeSum = new int[m + 1][n + 1]; //DP array with padding

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    rangeSum[i + 1][j + 1] = rangeSum[i][j + 1] + rangeSum[i + 1][j] - rangeSum[i][j] + mat[i][j];
                }
            }

            int[][] res = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    int r1 = Math.max(0, i - k); //!!!it's actually i - k + 1 - 1, see explanation above.
                    int c1 = Math.max(0, j - k);
                    int r2 = Math.min(m, i + k + 1);
                    int c2 = Math.min(n, j + k + 1);

                    res[i][j] = rangeSum[r2][c2] - rangeSum[r2][c1] - rangeSum[r1][c2] + rangeSum[r1][c1];
                }
            }

            return res;
        }
    }
}
