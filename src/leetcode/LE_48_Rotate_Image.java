package leetcode;

/**
 * Created by yuank on 2/28/18.
 */
public class LE_48_Rotate_Image {
    /**
        You are given an n x n 2D matrix representing an image.

        Rotate the image by 90 degrees (clockwise).

        Note:
        You have to rotate the image in-place, which means you have to modify the input 2D
        matrix directly. DO NOT allocate another 2D matrix and do the rotation.

        Example 1:

        Given input matrix =
        [
          [1,2,3],
          [4,5,6],
          [7,8,9]
        ],

        rotate the input matrix in-place such that it becomes:
        [
          [7,4,1],
          [8,5,2],
          [9,6,3]
        ]
     */

    /**
     *  clockwise rotate
     *  first reverse up to down, then swap the symmetry
     *  1 2 3     7 8 9     7 4 1
     *  4 5 6  => 4 5 6  => 8 5 2
     *  7 8 9     1 2 3     9 6 3
     *
     */
    class Solution1 {
        public static void rotate(int[][] matrix) {
            int m = matrix.length;
            int n = matrix[0].length;

            // [1,2,3],
            // [4,5,6],
            // [7,8,9]
            for (int i = 0; i < m; i++) {
                for (int j = i; j < n; j++) {
                    int temp = matrix[i][j];
                    matrix[i][j] = matrix[j][i];
                    matrix[j][i] = temp;
                }
            }

            // [1,4,7],
            // [2,5,8],
            // [3,6,9]
            int l = 0;
            int r = n - 1;
            while (l < r) {
                for (int i = 0; i < m; i++) {
                    int temp = matrix[i][l];
                    matrix[i][l] = matrix[i][r];
                    matrix[i][r] = temp;
                }
                l++;
                r--;
            }
        }
    }


}
