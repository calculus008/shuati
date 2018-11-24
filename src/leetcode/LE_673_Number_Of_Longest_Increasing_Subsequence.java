package leetcode;

import java.util.Arrays;

/**
 * Created by yuank on 11/21/18.
 */
public class LE_673_Number_Of_Longest_Increasing_Subsequence {
    /**
         Given an unsorted array of integers, find the number of longest increasing subsequence.

         Example 1:
         Input: [1,3,5,4,7]
         Output: 2
         Explanation: The two longest increasing subsequence are [1, 3, 4, 7] and [1, 3, 5, 7].

         Example 2:
         Input: [2,2,2,2,2]
         Output: 5
         Explanation: The length of longest continuous increasing subsequence is 1,
         and there are 5 subsequences' length is 1, so output 5.

         Note: Length of the given array will be not exceed 2000 and the answer is guaranteed to be fit in 32-bit signed int.

         Hard
     */

    /**
     * DP
     * Time  : O(n ^ 2)
     * Space : O(n)
     *
     * len[i] : length of the longest increasing subsequence that ends at nums[i]
     *
     * count[i] : number of the longest increasing subsequences that ends at nums[i]
     *
     * Example : {1, 2, 4, 2, 3} :
     *
     * i  0 1 2 3 4
     *    1 2 4 2 3
     * l  1 1 1 1 1
     * c  1 1 1 1 1
     *
     *    1 2
     * l  1 2
     * c  1 1
     *
     *    1 2 4
     * l  1 2 3
     * c  1 1 1
     *
     *
     *    1 2 4 2
     * l  1 2 3 2
     * c  1 1 1 1
     *
     *    1 2 4 2 3
     * l  1 2 3 2 3
     * c  1 1 1 1 2
     *
     * maxLen = 3
     *
     * l[2] and l[4] has maxLen value 3
     *
     * res = c[2] + c[4] = 2
     *
     */
    class Solution1 {
        public int findNumberOfLIS(int[] nums) {
            if (null == nums || nums.length == 0) {
                return 0;
            }

            int n = nums.length;
            int[] len = new int[n];
            int[] count = new int[n];

            Arrays.fill(len, 1);
            Arrays.fill(count, 1);

            for (int i = 1; i < n; i++) {
                for (int j = 0; j < i; j++) {
                    if (nums[i] > nums[j]) {
                        if (len[j] + 1 == len[i]) {
                            count[i] += count[j];
                        } else if (len[j] >= len[i]) {
                            count[i] = count[j];
                            len[i] = len[j] + 1;
                        }
                    }
                }
            }

            int maxLen = 0;
            for (int l : len) {
                maxLen = Math.max(l, maxLen);
            }

            int res = 0;
            for (int i = 0; i < n; i++) {
                if (len[i] == maxLen) {
                    res += count[i];
                }
            }

            return res;
        }
    }
}
