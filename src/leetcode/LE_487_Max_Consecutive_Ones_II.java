package leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class LE_487_Max_Consecutive_Ones_II {
    /**
     * Given a binary array, find the maximum number of consecutive 1s in this array
     * if you can flip at most one 0.
     *
     * Example 1:
     * Input: [1,0,1,1,0]
     * Output: 4
     * Explanation: Flip the first zero will get the the maximum number of consecutive 1s.
     *     After flipping, the maximum number of consecutive 1s is 4.
     *
     * Note:
     * The input array will only contain 0 and 1.
     * The length of input array is a positive integer and will not exceed 10,000
     *
     *
     * !!!
     * Follow up:
     * What if the input numbers come in one by one as an infinite stream? In other words,
     * you can't store all numbers coming from the stream as it's too large to hold in memory.
     * Could you solve it efficiently?
     */

    /**
     * Directly use sliding window solution for LE_1004_Max_Consecutive_Ones_III, set K to 1.
     */
    class Solution {
        public int findMaxConsecutiveOnes(int[] A) {
            int n = A.length;
            int res = Integer.MIN_VALUE;
            int count = 0;
            int K = 1;

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

    /**
     * We need to store up to k indexes of zero within the window [l, h] so that we know where to
     * move l next when the window contains more than k zero. If the input stream is infinite,
     * then the output could be extremely large because there could be super long consecutive ones.
     * In that case we can use BigInteger for all indexes. For simplicity, here we will use int
     *
     * Time: O(n) Space: O(k)
     */
    class Solution_For_Follow_Up {
        public int findMaxConsecutiveOnes(int[] nums) {
            int max = 0, k = 1; // flip at most k zero
            Queue<Integer> zeroIndex = new LinkedList<>();

            for (int j = 0, i = 0; i < nums.length; i++) {
                if (nums[i] == 0) {
                    zeroIndex.offer(i);
                }

                if (zeroIndex.size() > k) {
                    /**
                     * !!!
                     * Move to the next idx of the earliest 0 value index
                     */
                    j = zeroIndex.poll() + 1;
                }

                max = Math.max(max, i - j + 1);
            }

            return max;
        }
    }
}
