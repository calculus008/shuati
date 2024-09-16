package leetcode;

public class LE_485_Max_Consecutive_Ones {
    /**
     * Given a binary array, find the maximum number of consecutive 1s in this array.
     *
     * Example 1:
     * Input: [1,1,0,1,1,1]
     * Output: 3
     * Explanation: The first two digits or the last three digits are consecutive 1s.
     *     The maximum number of consecutive 1s is 3.
     *
     * Note:
     * The input array will only contain 0 and 1.
     * The length of input array is a positive integer and will not exceed 10,000
     *
     * Easy
     *
     * https://leetcode.com/problems/max-consecutive-ones
     */

    class Solution2 {
        public int findMaxConsecutiveOnes(int[] nums) {
            int count = 0;
            int maxCount = 0;

            for(int i = 0; i < nums.length; i++) {
                if(nums[i] == 1) {
                    count += 1; // Increment the count of 1's by one.
                } else {
                    maxCount = Math.max(maxCount, count); // Find the maximum till now.
                    count = 0;
                }
            }

            return Math.max(maxCount, count); //!!!
        }
    }

    /**
     * Directly use sliding window solution for LE_1004_Max_Consecutive_Ones_III, set K to 0.
     */
    class Solution1 {
        public int findMaxConsecutiveOnes(int[] A) {
            int n = A.length;
            int res = Integer.MIN_VALUE;
            int count = 0;
            int K = 0;

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
}
