package leetcode;

import java.util.HashMap;
import java.util.Map;

public class LE_1074_Number_Of_Submatrices_That_Sum_To_Target {
    /**
     * Given a matrix, and a target, return the number of non-empty
     * submatrices that sum to target.
     *
     * A submatrix x1, y1, x2, y2 is the set of all cells matrix[x][y]
     * with x1 <= x <= x2 and y1 <= y <= y2.
     *
     * Two submatrices (x1, y1, x2, y2) and (x1', y1', x2', y2') are
     * different if they have some coordinate that is different: for example, if x1 != x1'.
     *
     * Example 1:
     * Input: matrix = [[0,1,0],[1,1,1],[0,1,0]], target = 0
     * Output: 4
     * Explanation: The four 1x1 submatrices that only contain 0.
     *
     * Example 2:
     * Input: matrix = [[1,-1],[-1,1]], target = 0
     * Output: 5
     * Explanation: The two 1x2 submatrices, plus the two 2x1 submatrices, plus the 2x2 submatrix.
     *
     *
     * Note:
     * 1 <= matrix.length <= 300
     * 1 <= matrix[0].length <= 300
     * -1000 <= matrix[i] <= 1000
     * -10^8 <= target <= 10^8
     *
     * Hard
     *
     * Similar LE_363_Max_Sum_Of_Rectangle_No_Larger_Than_K
     */

    /**
     * 2D version of LE_560_Subarray_Sum_Equals_K
     *
     * #Subarray Sum
     * LE_209_Minimum_Size_Subarray_Sum
     * LE_325_Maximum_Size_Subarray_Sum_Equals_k
     * LE_523_Continuous_Subarray_Sum
     * LE_560_Subarray_Sum_Equals_K
     * LE_974_Subarray_Sums_Divisible_By_K
     *
     * LI_138_Subarray_Sum
     * LI_139_Subarray_Sum_Closest
     * LI_402_Continuous_Subarray_Sum
     * LI_403_Continuous_Subarray_Sum_II
     * LI_404_Subarray_Sum_II
     * LI_406_Minimum_Size_Subarray_Sum
     *
     * #2D
     * LE_363_Max_Sum_Of_Rectangle_No_Larger_Than_K
     * LE_1074_Number_Of_Submatrices_That_Sum_To_Target
     */

    /**
     * https://leetcode.com/problems/number-of-submatrices-that-sum-to-target/discuss/303750/JavaC%2B%2BPython-Find-the-Subarray-with-Target-Sum
     *
     * Time  : O(M * N ^ 2)
     * Space : O(N)
     *
     * For each row, calculate the prefix sum.
     * For each pair of columns,
     * calculate the accumulated sum of rows.
     * Now this problem is same to, "Find the Subarray with Target Sum".
     *
     * Brutal Force solution will try each submatrics, it costs O(M ^ 2 * N ^ 2).
     * We use prefix-sum technique to lower the time complexity to O(M * N ^ 2)
     */
    class Solution {
        public int numSubmatrixSumTarget(int[][] matrix, int target) {
            int m = matrix.length;
            int n = matrix[0].length;

            int[][] A = matrix.clone();

            /**
             * calculate prefix-sum for each row
             */
            for (int i = 0; i < m; i++) {
                for (int j = 1; j < n; j++) {
                    A[i][j] += A[i][j - 1];
                }
            }

            int res = 0;

            /**
             * Double "for" loops go through each pair of columns.
             * Then inner loop go through each row.
             */
            for (int i = 0; i < n; i++) {
                for (int j = i; j < n; j++) {
                    Map<Integer, Integer> map = new HashMap<>();
                    map.put(0, 1);
                    int cur = 0;

                    for (int k = 0; k < m; k++) {
                        cur += A[k][j] - (i > 0 ? A[k][i - 1] : 0);
                        res += map.getOrDefault(cur - target, 0);
                        map.put(cur, map.getOrDefault(cur, 0) + 1);
                    }
                }
            }

            return res;
        }
    }
}
