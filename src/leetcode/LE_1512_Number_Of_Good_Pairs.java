package leetcode;

public class LE_1512_Number_Of_Good_Pairs {
    /**
     * Given an array of integers nums, return the number of good pairs.
     *
     * A pair (i, j) is called good if nums[i] == nums[j] and i < j.
     *
     * Example 1:
     * Input: nums = [1,2,3,1,1,3]
     * Output: 4
     * Explanation: There are 4 good pairs (0,3), (0,4), (3,4), (2,5) 0-indexed.
     *
     * Example 2:
     * Input: nums = [1,1,1,1]
     * Output: 6
     * Explanation: Each pair in the array are good.
     *
     * Example 3:
     * Input: nums = [1,2,3]
     * Output: 0
     *
     * Constraints:
     * 1 <= nums.length <= 100
     * 1 <= nums[i] <= 100
     *
     * Easy
     *
     * https://leetcode.com/problems/number-of-good-pairs/
     */

    /**
     * Two loops O(n) solution
     */
    class Solution1 {
        public int numIdenticalPairs(int[] nums) {
            int[] bucket = new int[101];
            int res = 0;

            for (int num : nums) {
                bucket[num]++;
            }

            for (int n : bucket) {
                res += n * (n - 1) / 2;
            }

            return res;
        }
    }

    /**
     * One loop O(n) solution
     */
    class Solution2 {
        public int numIdenticalPairs(int[] nums) {
            int[] bucket = new int[101];
            int res = 0;

            for (int num : nums) {
                res += bucket[num];
                bucket[num]++;
            }

            return res;
        }
    }
}
