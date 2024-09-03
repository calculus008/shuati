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

    class Solution_clean {
        public List<Integer> spiralOrder(int[][] matrix) {
            if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return new ArrayList<>();

            int rowBegin = 0;
            int rowEnd = matrix.length - 1;
            int colBegin = 0;
            int colEnd = matrix[0].length - 1;

            List<Integer> res = new ArrayList<>();

            while (rowBegin <= rowEnd && colBegin <= colEnd) {
                for (int i = colBegin; i <= colEnd; i++) {
                    res.add(matrix[rowBegin][i]);
                }
                rowBegin++;

                for (int i = rowBegin; i <= rowEnd; i++) {
                    res.add(matrix[i][colEnd]);
                }
                colEnd--;

                if (rowBegin <= rowEnd) {
                    for (int i = colEnd; i >= colBegin; i--) {
                        res.add(matrix[rowEnd][i]);
                    }
                    rowEnd--;
                }

                if (colBegin <= colEnd) {
                    for (int i = rowEnd; i >= rowBegin; i--) {
                        res.add(matrix[i][colBegin]);
                    }
                    colBegin++;
                }
            }

            return res;
        }
    }
    class Solution1 {
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
    }

    /**
     * When traversing the matrix in the spiral order, at any time we follow one
     * out of the following four directions: RIGHT DOWN LEFT UP. Suppose we are
     * working on a 5 x 3 matrix as such:
     *
     * 0  1  2  3  4
     * 6  7  8  9  10
     * 11 12 13 14 15
     *
     * Imagine a cursor starts off at (0, -1), i.e. the position at '0', then we can
     * achieve the spiral order by doing the following:
     *
     * Go right 5 times
     * Go down 2 times
     * Go left 4 times
     * Go up 1 times.
     * Go right 3 times
     * Go down 0 times -> quit
     *
     * Notice that the directions we choose always follow the order 'right->down->left->up',
     * and for horizontal movements, the number of shifts follows:{5, 4, 3}, and vertical
     * movements follows {2, 1, 0}.
     *
     * Thus, we can make use of a direction matrix that records the offset for all directions,
     * then an array of two elements that stores the number of shifts for horizontal and vertical
     * movements, respectively. This way, we really just need one for loop instead of four.
     */
    class Solution2 {
        public List<Integer> spiralOrder2(int[][] matrix) {
            List<Integer> result = new ArrayList<>();
            if (null == matrix || matrix.length == 0) return result;

            int m = matrix.length;
            int n = matrix[0].length;

            int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            int[] range = {n, m - 1}; // {col, row}

            //initial position
            int row = 0;
            int col = -1;
            int d = 0; //direction index - 0: left to right, 1:top to bottom, 2:right to left, 3:bottom to top

            while (range[d % 2] != 0) {
                for (int i = 0; i < range[d % 2]; i++) {
                    row += dir[d][0];
                    col += dir[d][1];
                    result.add(matrix[row][col]);
                }

                range[d % 2] -= 1;
                d = (d + 1) % 4;
            }

            return result;
        }
    }

    /**
     * https://www.geeksforgeeks.org/print-given-matrix-counter-clock-wise-spiral-form/?ref=rp
     *
     * Counter Clock wise spiral
     */
    class Solution3 {
        public List<Integer> spiralOrder1(int[][] matrix) {
            List<Integer> res = new ArrayList<>();
            if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
                return res;
            }

            int rowBegin = 0;
            int rowEnd = matrix.length - 1;
            int colBegin = 0;
            int colEnd = matrix[0].length - 1;

            while (rowBegin <= rowEnd && colBegin <= colEnd) {
                for (int i = rowBegin; i <= rowEnd; i++) {
                    res.add(matrix[i][colBegin]);
                }
                colBegin++;

                for (int i = colBegin; i <= colEnd; i++) {
                    res.add(matrix[rowEnd][i]);
                }
                rowEnd--;

                if (colBegin <= colEnd) {
                    for (int i = rowEnd; i >= rowBegin; i--) {
                        res.add(matrix[i][colEnd]);
                    }
                }
                colEnd--;

                if (rowBegin <= rowEnd) {
                    for (int i = colEnd; i >= colBegin; i--) {
                        res.add(matrix[rowBegin][i]);
                    }
                }
                rowBegin++;
            }

            return res;
        }
    }
}
