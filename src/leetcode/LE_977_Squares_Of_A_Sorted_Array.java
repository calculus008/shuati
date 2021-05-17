package src.leetcode;

public class LE_977_Squares_Of_A_Sorted_Array {
    /**
     * Given an array of integers A sorted in non-decreasing order, return an array of
     * the squares of each number, also in sorted non-decreasing order.
     *
     *
     *
     * Example 1:
     *
     * Input: [-4,-1,0,3,10]
     * Output: [0,1,9,16,100]
     * Example 2:
     *
     * Input: [-7,-3,2,3,11]
     * Output: [4,9,9,49,121]
     *
     *
     * Note:
     *
     * 1 <= A.length <= 10000
     * -10000 <= A[i] <= 10000
     * A is sorted in non-decreasing order.
     *
     * Easy
     */

    class Solution {
        public int[] sortedSquares(int[] A) {
            int n = A.length;
            int[] res = new int[A.length];

            if (A[0] >= 0) {
                for (int i = 0; i < n; i++) {
                    res[i] = A[i] * A[i];
                }
                return res;
            }

            /**
             * If the first element is smaller than 0, then turn into a 2 pointers problem.
             */
            int l = 0;
            int r = n - 1;
            int i = n - 1;

            while (l <= r) {
                if (Math.abs(A[l]) > Math.abs(A[r])) {
                    res[i] = A[l] * A[l];
                    l++;
                } else {
                    res[i] = A[r] * A[r];
                    r--;
                }
                i--;
            }

            return res;
        }
    }
}
