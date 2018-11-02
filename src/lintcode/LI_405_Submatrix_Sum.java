package lintcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuank on 10/1/18.
 */
public class LI_405_Submatrix_Sum {
    /**
         Given an integer matrix, find a submatrix where the sum of numbers is zero.
         Your code should return the coordinate of the left-up and right-down number.

         Have you met this question in a real interview?
         Example
         Given matrix

         [
             [1 ,5 ,7],
             [3 ,7 ,-8],
             [4 ,-8 ,9],
         ]
         return [(1,1), (2,2)]

         Medium
     */

    public int[][] submatrixSum(int[][] matrix) {
        int[][] result = new int[2][2];
        int M = matrix.length;
        if (M == 0) return result;
        int N = matrix[0].length;
        if (N == 0) return result;

        //!!! pre-compute: sum[i][j] = sum of submatrix [(0, 0), (i, j)]
        int[][] sum = new int[M + 1][N + 1];

        for (int j = 0; j <= N; ++j) sum[0][j] = 0;
        for (int i = 1; i <= M; ++i) sum[i][0] = 0;
        for (int i = 0; i < M; ++i) {
            for (int j = 0; j < N; ++j)
                sum[i + 1][j + 1] = matrix[i][j] + sum[i + 1][j] + sum[i][j + 1] - sum[i][j];
        }

        for (int l = 0; l < M; ++l) {
            for (int h = l + 1; h <= M; ++h) {
                Map<Integer, Integer> map = new HashMap<>();
                for (int j = 0; j <= N; ++j) {
                    int diff = sum[h][j] - sum[l][j];
                    if (map.containsKey(diff)) {
                        int k = map.get(diff);
                        result[0][0] = l;
                        result[0][1] = k;
                        /**
                         * operate on sum, which is padded, so based on
                         * sum[j + 1] - sum[i] is the subarray sum between [i, j],
                         * higher end should minus 1.
                         */
                        result[1][0] = h - 1;
                        result[1][1] = j - 1;
                        return result;
                    } else {
                        map.put(diff, j);
                    }
                }
            }
        }
        return result;
    }

}
