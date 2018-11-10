package lintcode;

/**
 * Created by yuank on 11/8/18.
 */
public class LI_558_Sliding_Window_Matrix_Maximum {
    /**
         Given an array of n * m matrix, and a moving matrix window (size k * k),
         move the window from top left to botton right at each iteration,
         find the maximum sum inside the window at each moving.

         Return 0 if the answer does not exist.

         Example
         For matrix

         [
         [1, 5, 3],
         [3, 2, 1],
         [4, 1, 9],
         ]
         The moving window size k = 2.
         return 13.

         At first the window is at the start of the array like this

         [
         [|1, 5|, 3],
         [|3, 2|, 1],
         [4, 1, 9],
         ]
         ,get the sum 11;
         then the window move one step forward.
     */

    public int maxSlidingMatrix(int[][] matrix, int k) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0 || k <= 0) {
            return 0;
        }

        int m = matrix.length;
        int n = matrix[0].length;

        /**
         * 根据题意，如果window不满，返回0.
         */
        if (m < k || n < k) return 0;//!!!

        /**
         * sums[i][j] : sums of values in the area with matrix[i - 1][j - 1] as the right bottom corner and matrix[0][0] as left upper corner
         */
        int[][] sums = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                sums[i][j] = sums[i][j - 1] + sums[i - 1][j] - sums[i - 1][j - 1] + matrix[i - 1][j - 1];
            }
        }

        int res = Integer.MIN_VALUE;
        for (int i = k; i <= m; i++) {
            for (int j = k; j <= n; j++) {
                int cur = sums[i][j] - sums[i][j - k] - sums[i - k][j] + sums[i - k][j - k];
                res = Math.max(cur, res);
            }
        }

        return res;
    }
}
