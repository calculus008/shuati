package src.leetcode;

import java.util.Arrays;

public class LE_473_Matchsticks_To_Square {
    /**
     * Remember the story of Little Match Girl? By now, you know exactly what matchsticks
     * the little match girl has, please find out a way you can make one square by using
     * up all those matchsticks. You should not break any stick, but you can link them up,
     * and each matchstick must be used exactly one time.
     *
     * Your input will be several matchsticks the girl has, represented with their stick
     * length. Your output will either be true or false, to represent whether you could make
     * one square using all the matchsticks the little match girl has.
     *
     * Example 1:
     * Input: [1,1,2,2,2]
     * Output: true
     *
     * Explanation: You can form a square with length 2, one side of the square came two
     * sticks with length 1.
     *
     * Example 2:
     * Input: [3,3,3,3,4]
     * Output: false
     *
     * Explanation: You cannot find a way to form a square with all the matchsticks.
     * Note:
     * The length sum of the given matchsticks is in the range of 0 to 10^9.
     * The length of the given matchstick array will not exceed 15.
     *
     * Hard
     */

    /**
     * In essence, this problem is to find out if a group of numbers can be divided into
     * n groups of equal sum.
     *
     * Same as LE_698_Partition_To_K_Equal_Sum_Subsets
     *
     * Use DFS/Backtracking
     *
     * LE_698_Partition_To_K_Equal_Sum_Subsets
     *
     * Complexity Analysis
     *
     * Time : O(4^N)
     * because we have a total of N sticks and for each one of those matchsticks, we have 4 different
     * possibilities for the subsets they might belong to any side of the square they might be a part of.
     *
     * Space : O(N)
     * For recursive solutions, the space complexity is the stack space occupied by all the recursive calls.
     * The deepest recursive call here would be of size N and hence the space complexity is O(N).
     * There is no additional space other than the recursion stack in this solution.
     */

    class Solution {
        public boolean makesquare(int[] nums) {
            if (nums == null || nums.length < 4) return false;

            int sum = 0;
            for (int num : nums) {
                sum += num;
            }

            if (sum % 4 != 0) return false;

            int target = sum / 4;

            /**
             * !!!
             * Sort first. Sorting the input array DESC will make the DFS process run much faster.
             * Reason behind this is we always try to put the next matchstick in the first subset.
             * If there is no solution, trying a longer matchstick first will get to negative
             * conclusion earlier.
             */
            Arrays.sort(nums);

            return helper(nums, new boolean[nums.length], target, 0, 0, 1);
        }

        private boolean helper(int[] nums, boolean[] visited, int target, int pos, int cur, int groupId) {
            if (groupId == 4) return true;
            if (target == cur) {
                /**
                 * !!!
                 * IMPORTANT : once we start working on a new group, we go back to index 0, NOT pos!!!
                 */
                return helper(nums, visited, target, 0, 0, groupId + 1);
            }

            if (cur > target) return false;

            for (int i = pos; i < nums.length; i++) {
                if (visited[i]) continue;

                if (i > 0 && nums[i] == nums[i - 1] && !visited[i - 1]) continue;

                /**
                 * !!!
                 * Backtrack, for any of the number in the for loop, if next level
                 * helper() call is true, we can return true.
                 */
                visited[i] = true;
                if (helper(nums, visited, target, i + 1, cur + nums[i], groupId)) {
                    return true;
                }
                visited[i] = false;
            }

            return false;
        }
    }
}
