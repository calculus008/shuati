package leetcode;

/**
 * Created by yuank on 4/30/18.
 */
public class LE_311_Sparse_Matrix_Multiplication {
    /**
         Given two sparse matrices A and B, return the result of AB.

         You may assume that A's column number is equal to B's row number.

         Example:

         A = [
         [ 1, 0, 0],
         [-1, 0, 3]
         ]

         B = [
         [ 7, 0, 0 ],
         [ 0, 0, 0 ],
         [ 0, 0, 1 ]
         ]


              |  1 0 0 |   | 7 0 0 |   |  7 0 0 |
         AB = | -1 0 3 | x | 0 0 0 | = | -7 0 3 |
                           | 0 0 1 |

         Medium
     */

    /**
     *  Time : O(m * n * nB);
     *  Space : O(m * nB)
     *
     *  Reference : https://www.mathsisfun.com/algebra/matrix-multiplying.html
     */
    public int[][] multiply(int[][] A, int[][] B) {
        int m = A.length;
        int n = A[0].length;
        int nB = B[0].length;

        int[][] res = new int[m][nB];

        for (int i = 0; i < m ; i++) {
            for (int k = 0; k < n; k++) {
                if(A[i][k] !=  0) {
                    for (int j = 0; j < nB; j++) {
                        if (B[k][j] != 0) {
                            res[i][j] += A[i][k] * B[k][j];
                        }
                    }
                }
            }
        }

        return res;
    }
}
