package lintcode;

/**
 * Created by yuank on 10/23/18.
 */
public class LI_397_Longest_Continuous_Increasing_Subsequence {
    /**
         Give an integer array，find the longest increasing continuous subsequence in this array.

         An increasing continuous subsequence:

         Can be from right to left or from left to right.
         Indices of the integers in the subsequence should be continuous.
         Example
         For [5, 4, 2, 1, 3], the LICS is [5, 4, 2, 1], return 4.

         For [5, 1, 2, 3, 4], the LICS is [1, 2, 3, 4], return 4.

         Challenge
         O(n) time and O(1) extra space.
     */

    public int longestIncreasingContinuousSubsequence(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int up = 1;
        int down = 1;
        int res = 1;//!!!

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                down = 1;
                up++;
            } else if (nums[i] < nums[i - 1]) {
                up = 1;
                down++;
            } else {
                up = 1;
                down = 1;
            }
            res = Math.max(res, Math.max(up, down));
        }

        return res;
    }
}
