package leetcode;

public class LE_1493_Longest_Subarray_Of_1_After_Deleting_One_Element {
    /**
     * Given a binary array nums, you should delete one element from it.
     *
     * Return the size of the longest non-empty subarray containing only 1's in the resulting array. Return 0 if there
     * is no such subarray.
     *
     * Example 1:
     * Input: nums = [1,1,0,1]
     * Output: 3
     * Explanation: After deleting the number in position 2, [1,1,1] contains 3 numbers with value of 1's.
     *
     * Example 2:
     * Input: nums = [0,1,1,1,0,1,1,0,1]
     * Output: 5
     * Explanation: After deleting the number in position 4, [0,1,1,1,1,1,0,1] longest subarray with value of 1's is [1,1,1,1,1].
     *
     * Example 3:
     * Input: nums = [1,1,1]
     * Output: 2
     * Explanation: You must delete one element.
     *
     *
     * Constraints:
     *
     * 1 <= nums.length <= 105
     * nums[i] is either 0 or 1.
     *
     * Medium
     *
     * https://leetcode.com/problems/longest-subarray-of-1s-after-deleting-one-element
     */

    //Sliding Window
    class Solution_editorial {
        public int longestSubarray(int[] nums) {
            int zeroCount = 0;
            int res = 0;

            for (int i = 0, j = 0; i < nums.length; i++) {
                zeroCount += (nums[i] == 0 ? 1 : 0);

                while (zeroCount > 1) {// Shrink the window until the zero counts come under the limit (less or equal to 1)
                    zeroCount -= (nums[j] == 0 ? 1 : 0);
                    j++;
                }

                res = Math.max(res, i - j);//!!! need to remove one element, so i - j + 1 - 1 = i - j
            }

            return res;
        }
    }

    class Solution_mine {
        public int longestSubarray(int[] nums) {
            int res = 0;
            int sum = 0;

            for (int i = 0, j = 0; i < nums.length; i++) {
                sum += nums[i];

                if (sum == i - j + 1) {
                    res = Math.max(res, i - j + 1);
                }

                if (sum == i - j) {
                    res = Math.max(res, i - j);
                }

                while (j <= i && sum < i - j) {
                    sum -= nums[j];
                    j++;
                }
            }

            return sum == nums.length ? nums.length - 1 : res; // "nums.length - 1", if all elements in array are 1
        }
    }
}
