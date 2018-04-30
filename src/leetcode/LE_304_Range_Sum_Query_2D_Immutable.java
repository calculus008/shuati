package leetcode;

/**
 * Created by yuank on 4/29/18.
 */
public interface LE_304_Range_Sum_Query_2D_Immutable {
    /**
         Given a 2D matrix matrix, find the sum of the elements inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).

         Range Sum Query 2D
         The above rectangle (with the red border) is defined by (row1, col1) = (2, 1) and (row2, col2) = (4, 3), which contains sum = 8.

         Example:
         Given matrix = [
         [3, 0, 1, 4, 2],
         [5, 6, 3, 2, 1],
         [1, 2, 0, 1, 5],
         [4, 1, 0, 1, 7],
         [1, 0, 3, 0, 5]
         ]

         sumRegion(2, 1, 4, 3) -> 8
         sumRegion(1, 1, 2, 2) -> 11
         sumRegion(1, 2, 2, 4) -> 12
         Note:
         You may assume that the matrix does not change.
         There are many calls to sumRegion function.
         You may assume that row1 ≤ row2 and col1 ≤ col2.

         Medium
     */

    class NumMatrix {
        int[][] sums;

        //Time : O(mn)
        public NumMatrix(int[][] matrix) {
            if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return;

            int m = matrix.length;
            int n = matrix[0].length;

            sums = new int[m + 1][n + 1];

            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    //!!!"sums[i][j] =",  "+ matrix[i - 1][j - 1]"
                    sums[i][j] =  sums[i - 1][j] + sums[i][j - 1] - sums[i - 1][j - 1] + matrix[i - 1][j - 1];
                }
            }
        }

        //Time : O(1)
        public int sumRegion(int row1, int col1, int row2, int col2) {
            int r1 = Math.min(row1, row2);
            int r2 = Math.max(row1, row2);
            int c1 = Math.min(col1, col2);
            int c2 = Math.max(col1, col2);

            //!!!"- sums[r2 + 1][c1]", " - sums[r1][c2 + 1]"
            return sums[r2 + 1][c2 + 1] - sums[r2 + 1][c1] - sums[r1][c2 + 1] + sums[r1][c1];
        }
    }

}
