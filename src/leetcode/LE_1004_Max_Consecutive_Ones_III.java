package leetcode;

public class LE_1004_Max_Consecutive_Ones_III {
    /**
     * Given an array A of 0s and 1s, we may change up to K values from 0 to 1.
     *
     * Return the length of the longest (contiguous) subarray that contains only 1s.
     *
     *
     * Example 1:
     * Input: A = [1,1,1,0,0,0,1,1,1,1,0], K = 2
     * Output: 6
     * Explanation:
     * [1,1,1,0,0,1,1,1,1,1,1]
     * Bolded numbers were flipped from 0 to 1.  The longest subarray is underlined.
     *
     * Example 2:
     * Input: A = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1], K = 3
     * Output: 10
     * Explanation:
     * [0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1]
     * Bolded numbers were flipped from 0 to 1.  The longest subarray is underlined.
     *
     *
     * Note:
     * 1 <= A.length <= 20000
     * 0 <= K <= A.length
     * A[i] is 0 or 1
     *
     * Medium
     */

    /**
     * sliding window
     *
     * Time  : O(n)
     * Space : O(1)
     */
    public int longestOnes(int[] A, int K) {
        int n = A.length;
        int res = Integer.MIN_VALUE;
        int count = 0;

        for (int i = 0, j = 0; i < n; i++) {
            if (A[i] == 0) {
                count++;
            }

            while (count > K) {
                if (A[j] == 0) {
                    count--;
                }
                j++;
            }

            res = Math.max(res, i - j + 1);
        }

        return res;
    }
}
