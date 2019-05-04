package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 3/2/18.
 */
public class LE_59_Spiral_Matrix_II {
    /**
        Given an integer n, generate a square matrix filled with elements from 1 to n^2 in spiral order.

        For example,
        Given n = 3,

        You should return the following matrix:
        [
         [ 1, 2, 3 ],
         [ 8, 9, 4 ],
         [ 7, 6, 5 ]
        ]
     */

    public int[][] generateMatrix1(int n) {
        int[][] matrix = new int[n][n];

        int rowBegin = 0;
        int rowEnd = n - 1;
        int colBegin = 0;
        int colEnd = n - 1;
        int num = 1;

        while (rowBegin <= rowEnd && colBegin <= colEnd) {
            for (int i = colBegin; i <= colEnd; i++) {
                matrix[rowBegin][i] = num++;
            }
            rowBegin++;

            for (int i = rowBegin; i <= rowEnd; i++) {
                matrix[i][colEnd] = num++;
            }
            colEnd--;

            //!!! if
            for (int i = colEnd; i >= colBegin; i--) {
                matrix[rowEnd][i] = num++;
            }

            rowEnd--;

            //!!! if
            for (int i = rowEnd; i >= rowBegin; i--) {
                matrix[i][colBegin] = num++;
            }
            colBegin++;
        }

        return matrix;
    }

    public int[][] generateMatrix2(int n) {

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
