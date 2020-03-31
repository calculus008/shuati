package leetcode;

import java.util.TreeSet;

public class LE_363_Max_Sum_Of_Rectangle_No_Larger_Than_K {
    /**
     * Given a non-empty 2D matrix matrix and an integer k, find the max sum
     * of a rectangle in the matrix such that its sum is no larger than k.
     *
     * Example:
     *
     * Input: matrix = [[1,0,1],[0,-2,3]], k = 2
     * Output: 2
     * Explanation: Because the sum of rectangle [[0, 1], [-2, 3]] is 2,
     *              and 2 is the max number no larger than k (k = 2).
     *
     * Note:
     * The rectangle inside the matrix must have an area > 0.
     * What if the number of rows is much larger than the number of columns?
     *
     * Hard
     */

    /**
     * Time : O(n ^ 3 * logn)
     */
    public class Solution {
        public int maxSumSubmatrix(int[][] matrix, int k) {
            if (null == matrix || matrix.length == 0) {
                return 0;
            }

            int row = matrix.length;
            int col = matrix[0].length;
            int result = Integer.MIN_VALUE;

            //should be l<col, r<row, NOT col-1, row-1 !!!!!
            for (int l = 0; l < col; l++) {
                int[] nums = new int[row];

                //r starts with l!!!!
                for (int r = l; r < col; r++) {
                    for (int t = 0; t < row; t++) {
                        nums[t] += matrix[t][r];
                    }

                    TreeSet<Integer> set = new TreeSet<>();
                    set.add(0);
                    int curSum = 0;

                    for (int num : nums) {
                        curSum += num;
                        //Interger!!!
                        Integer n = set.ceiling(curSum - k);
                        if (n != null) {
                            result = Math.max(result, curSum - n); //curSum - n !!!!
                        }
                        set.add(curSum);
                    }
                }
            }

            return result;
        }
    }
}
