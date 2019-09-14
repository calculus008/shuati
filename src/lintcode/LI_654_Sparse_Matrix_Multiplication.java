package lintcode;

import java.util.ArrayList;
import java.util.List;

public class LI_654_Sparse_Matrix_Multiplication {
    /**
     * Given two Sparse Matrix A and B, return the result of AB.
     *
     * You may assume that A's column number is equal to B's row number.
     *
     * Example1
     *
     * Input:
     * [[1,0,0],[-1,0,3]]
     * [[7,0,0],[0,0,0],[0,0,1]]
     * Output:
     * [[7,0,0],[-7,0,3]]
     * Explanation:
     * A = [
     *   [ 1, 0, 0],
     *   [-1, 0, 3]
     * ]
     *
     * B = [
     *   [ 7, 0, 0 ],
     *   [ 0, 0, 0 ],
     *   [ 0, 0, 1 ]
     * ]
     *
     *
     *      |  1 0 0 |   | 7 0 0 |   |  7 0 0 |
     * AB = | -1 0 3 | x | 0 0 0 | = | -7 0 3 |
     *                   | 0 0 1 |
     *
     * Same as LE_311_Sparse_Matrix_Multiplication
     */

    // version: 高频题班
    //常规做法
    public class Solution1 {
        public int[][] multiply(int[][] A, int[][] B) {
            // Write your code here
            int n = A.length;
            int m = B[0].length;
            int t = A[0].length;
            int[][] C = new int[n][m];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    for (int k = 0; k < t; k++) {
                        C[i][j] += A[i][k] * B[k][j];
                    }
                }
            }
            return C;
        }
    }



    //改进做法
    public class Solution2 {
        public int[][] multiply(int[][] A, int[][] B) {
            // Write your code here
            int n = A.length;
            int m = B[0].length;
            int t = A[0].length;
            int[][] C = new int[n][m];

            for (int i = 0; i < n; i++) {
                for (int k = 0; k < t; k++) {
                    if (A[i][k] == 0) {
                        continue;
                    }
                    for (int j = 0; j < m; j++) {
                        C[i][j] += A[i][k] * B[k][j];
                    }
                }
            }
            return C;
        }
    }


    /**
     * 进一步改进
     *
     * 1.Pre-processing B, get the none-zero elements into "col", O(n ^ 2)
     *   Here, what we put into col is not the value of the element, but the column index value
     *   of the none-zero element
     * 2.Loop through A, only do calculation for none-zero elements in A and elements in "col",
     *   O(a * b) : a number of none zero in A , b number of none zero in B.
     *
     **/
    public class Solution3 {
        public int[][] multiply(int[][] A, int[][] B) {
            // Write your code here
            int n = A.length;
            int m = B[0].length;
            int t = A[0].length;
            int[][] C = new int[n][m];

            List<List<Integer>> col = new ArrayList<>();
            for (int i = 0; i < t; i++) {
                col.add(new ArrayList<>());
                for (int j = 0; j < m; j++) {
                    if (B[i][j] != 0) {
                        col.get(i).add(j);
                    }
                }
            }

            for (int i = 0; i < n; i++) {
                for (int k = 0; k < t; k++) {
                    if (A[i][k] == 0) {
                        continue;
                    }
                    for (int j: col.get(k)) {
                        C[i][j] += A[i][k] * B[k][j];
                    }
                }
            }
            return C;
        }
    }
}
