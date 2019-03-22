package leetcode;

import java.util.Arrays;

public class LE_698_Partition_To_K_Equal_Sum_Subsets {
    /**
     * Given an array of integers nums and a positive integer k, find whether it's
     * possible to divide this array into k non-empty subsets whose sums are all equal.
     *
     * Example 1:
     *
     * Input: nums = [4, 3, 2, 3, 5, 2, 1], k = 4
     * Output: True
     * Explanation: It's possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.
     *
     *
     * Note:
     *
     * 1 <= k <= len(nums) <= 16.
     * 0 < nums[i] < 10000.
     * Medium
     */

    /**
     * Top-Down DFS Search
     *
     * Time Complexity: O(k ^ (N - k) * k!)
     * where N is the length of nums, and k is as given.
     * As we skip additional zeroes in groups, naively we will make O(k!) calls to search,
     * then an additional O(k ^ (N - k)) calls after every element of groups is nonzero.
     *
     * Space Complexity: O(N), the space used by recursive calls to search in our call stack.
     */
    class Solution2 {
        public boolean canPartitionKSubsets(int[] nums, int k) {
            int sum = 0;
            for (int num : nums) {
                sum += num;
            }
            if (k <= 0 || sum % k != 0) {
                return false;
            }
            int[] visited = new int[nums.length];

            return canPartition(nums, visited, 0, k, 0, 0, sum / k);
        }

        public boolean canPartition(int[] nums, int[] visited, int start_index, int k,
                                    int cur_sum, int count, int target) {
            /**
             * !!!
             * The condition we can start canParitition() is : sum % k == 0
             * So here we have a final group and the sum of the
             * rest of the numbers must be target.
             */
            if (k == 1) {
                return true;
            }

            if(cur_sum > target) {
                return false;
            }

            /**
             * We find a group, start to find the next group,
             *
             * Since "0 < nums[i] < 10000", we don't need to have the "count" param here.
             */
            if (cur_sum == target && count > 0) {
                return canPartition(nums, visited, 0, k - 1,
                        0, 0, target);
            }

            for (int i = start_index; i < nums.length; i++) {
                if (visited[i] == 0) {
                    visited[i] = 1;
                    if (canPartition(nums, visited, i + 1, k, cur_sum + nums[i], count++, target)) {
                        return true;
                    }
                    visited[i] = 0;
                }
            }
            return false;
        }
    }
}
