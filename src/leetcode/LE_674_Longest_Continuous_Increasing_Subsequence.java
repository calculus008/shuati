package leetcode;

/**
 * Created by yuank on 10/23/18.
 */
public class LE_674_Longest_Continuous_Increasing_Subsequence {
    /**
         Given an unsorted array of integers, find the length of longest
         continuous increasing subsequence (subarray).

         Example 1:
         Input: [1,3,5,4,7]
         Output: 3
         Explanation: The longest continuous increasing subsequence is [1,3,5], its length is 3.
         Even though [1,3,5,7] is also an increasing subsequence, it's not a continuous one where 5 and 7 are separated by 4.

         Example 2:
         Input: [2,2,2,2,2]
         Output: 1
         Explanation: The longest continuous increasing subsequence is [2], its length is 1.
         Note: Length of the array will not exceed 10,000.

         Easy
     */

    /**
     * DP
     * http://zxi.mytechroad.com/blog/dynamic-programming/leetcode-674-longest-continuous-increasing-subsequence/
     *
     * Time : O(n)
     * Space : O(1)
     */
    public int findLengthOfLCIS(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int up = 1;
        int res = 1;//!!!

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                up++;
            } else {
                up = 1;
            }
            res = Math.max(res, up);
        }

        return res;
    }
}
