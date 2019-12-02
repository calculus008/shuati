package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 3/2/18.
 */
public class LE_54_Spiral_Matrix {
    /**
        Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral order.

        For example,
        Given the following matrix:

        [
         [ 1, 2, 3 ],
         [ 4, 5, 6 ],
         [ 7, 8, 9 ]
        ]
        You should return [1,2,3,6,9,8,7,4,5].
     */

    public List<Integer> spiralOrder1(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return res;
        }

        int rowBegin = 0;
        int rowEnd = matrix.length - 1;
        int colBegin = 0;
        int colEnd = matrix[0].length - 1;

        /**
         * !!!
         * outer while loop checks boundary condition
         */
        while (rowBegin <= rowEnd && colBegin <= colEnd) {
            for (int i = colBegin; i <= colEnd; i++) {
                res.add(matrix[rowBegin][i]);
            }
            rowBegin++;

            for (int i = rowBegin; i <= rowEnd; i++) {
                res.add(matrix[i][colEnd]);
            }
            colEnd--;

            /**
             * !!!
             * if
             */
            if (rowBegin <= rowEnd) {
                for (int i = colEnd; i >= colBegin; i--) {
                    res.add(matrix[rowEnd][i]);
                }
            }
            rowEnd--;

            /**
             * !!!
             * if
             */
            if (colBegin <= colEnd) {
                for (int i = rowEnd; i >= rowBegin; i--) {
                    res.add(matrix[i][colBegin]);
                }
            }
            colBegin++;
        }

        return res;
    }

    public List<Integer> spiralOrder2(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if(null == matrix || matrix.length == 0) return result;

        int m = matrix.length;
        int n = matrix[0].length;

        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        int[] range = {n, m-1}; // {col, row}

        // int i = n*m;
        int row = 0;
        int col = -1;
        int d = 0; //direction index - 0: left to right, 1:top to bottom, 2:right to left, 3:bottom to top

        while(range[d%2] != 0){
            for(int i = 0; i < range[d%2]; i++){
                row += dir[d][0];
                col += dir[d][1];
                result.add(matrix[row][col]);
            }

            range[d%2] -= 1;
            d = (d + 1) % 4;
        }

        return result;
    }
}
