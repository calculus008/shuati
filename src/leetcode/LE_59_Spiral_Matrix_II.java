package leetcode;

/**
 * Created by yuank on 3/2/18.
 */
public class LE_59_Spiral_Matrix_II {
    /*
        Given an integer n, generate a square matrix filled with elements from 1 to n2 in spiral order.

        For example,
        Given n = 3,

        You should return the following matrix:
        [
         [ 1, 2, 3 ],
         [ 8, 9, 4 ],
         [ 7, 6, 5 ]
        ]
     */

    public int[][] generateMatrix(int n) {

        if (n <= 0) {
            int[][] r = {};
            return r;
        }

        int[][] matrix = new int[n][n];

        int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int row = 0;
        int col = -1;

        int[] range = {n, n - 1};
        int d = 0;
        int k = 0;

        while (range[d % 2] != 0) {
            for (int i = 0; i < range[d % 2]; i++) {
                k++;
                row += dir[d][0];
                col += dir[d][1];
                matrix[row][col] = k;
            }

            range[d % 2] -= 1;
            d = (d + 1) % 4;
        }

        return matrix;
    }
}
