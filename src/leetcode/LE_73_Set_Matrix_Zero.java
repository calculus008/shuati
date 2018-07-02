package leetcode;

/**
 * Created by yuank on 3/5/18.
 */
public class LE_73_Set_Matrix_Zero {
    /**
        Given a m x n matrix, if an element is 0, set its entire row and column to 0. Do it IN PLACE.
     */


    /**
        Use first row and first column as markers.
        if matrix[i][j] = 0, mark respected row and col marker = 0;
        indicating that later this respective row and col must be marked 0;
        And because you are altering first row and column,
        you need to have two variables to track their own status.
        So, for example, if any one of the first row is 0, row = 0,
        and at the end set all first row to 0;
     **/

    //Time : O(m * n), Space : O(1)
    public static void setZeroes(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return;

        int m = matrix.length;
        int n = matrix[0].length;
        boolean isFirstRowZero = false;
        boolean isFirstColZero = false;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    if (i == 0) isFirstRowZero = true;
                    if (j == 0) isFirstColZero = true;
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        for (int i = 1; i < m; i++) {
            if (matrix[i][0] == 0) {
                for (int j = 1; j < n; j++) {
                    matrix[i][j] = 0;
                }
            }
        }

        for (int j = 1; j < n; j++) {
            if (matrix[0][j] == 0) {
                for (int i = 1; i < m; i++) {
                    matrix[i][j] = 0;
                }
            }
        }

        if (isFirstRowZero) {
            for (int j = 0; j < n; j++) {
                matrix[0][j] = 0;
            }
        }

        if (isFirstColZero) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }

    }
}
