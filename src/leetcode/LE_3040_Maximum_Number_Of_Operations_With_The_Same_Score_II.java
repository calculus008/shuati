package leetcode;

import java.util.*;

public class LE_3040_Maximum_Number_Of_Operations_With_The_Same_Score_II {
    /**
     * Given an array of integers called nums, you can perform any of the following operation while nums contains
     * at least 2 elements:
     *
     * Choose the first two elements of nums and delete them.
     * Choose the last two elements of nums and delete them.
     * Choose the first and the last elements of nums and delete them.
     * The score of the operation is the sum of the deleted elements.
     *
     * Your task is to find the maximum number of operations that can be performed, such that all operations have
     * the same score.
     *
     * Return the maximum number of operations possible that satisfy the condition mentioned above.
     *
     *
     *
     * Example 1:
     * Input: nums = [3,2,1,2,3,4]
     * Output: 3
     * Explanation: We perform the following operations:
     * - Delete the first two elements, with score 3 + 2 = 5, nums = [1,2,3,4].
     * - Delete the first and the last elements, with score 1 + 4 = 5, nums = [2,3].
     * - Delete the first and the last elements, with score 2 + 3 = 5, nums = [].
     * We are unable to perform any more operations as nums is empty.
     *
     * Example 2:
     * Input: nums = [3,2,6,1,4]
     * Output: 2
     * Explanation: We perform the following operations:
     * - Delete the first two elements, with score 3 + 2 = 5, nums = [6,1,4].
     * - Delete the last two elements, with score 1 + 4 = 5, nums = [6].
     * It can be proven that we can perform at most 2 operations.
     *
     *
     * Constraints:
     *
     * 2 <= nums.length <= 2000
     * 1 <= nums[i] <= 1000
     *
     * Medium
     *
     * https://leetcode.com/problems/maximum-number-of-operations-with-the-same-score-ii
     */

    class Solution {
        int[] nums;
        int[][] memo;

        public int maxOperations(int[] nums) {
            int n = nums.length;
            this.nums = nums;
            this.memo = new int[n][n];
            int res = 0;
            res = Math.max(res, helper(0, n - 1, nums[0] + nums[n - 1]));
            res = Math.max(res, helper(0, n - 1, nums[0] + nums[1]));
            res = Math.max(res, helper(0, n - 1, nums[n - 2] + nums[n - 1]));
            return res;
        }

        public int helper(int i, int j, int target) {
            for (int k = 0; k < nums.length; k++) {
                Arrays.fill(memo[k], -1);
            }
            return dfs(i, j, target);
        }

        public int dfs(int i, int j, int target) {
            if (i >= j) {
                return 0;
            }
            if (memo[i][j] != -1) {
                return memo[i][j];
            }
            int ans = 0;
            if (nums[i] + nums[i + 1] == target) {
                ans = Math.max(ans, dfs(i + 2, j, target) + 1);
            }
            if (nums[j - 1] + nums[j] == target) {
                ans = Math.max(ans, dfs(i, j - 2, target) + 1);
            }
            if (nums[i] + nums[j] == target) {
                ans = Math.max(ans, dfs(i + 1, j - 1, target) + 1);
            }
            memo[i][j] = ans;
            return ans;
        }
    }
    /**
     * 方法一：记忆化搜索
     * 思路与算法
     *
     * 由于题目要求所有操作分数相同，我们可以观察到如果第一删除操作时确定了删除的分数，那么后续每次删除的分数都保持不变，
     * 因此操作的得分最多只有三种情形，设数组  target 的长度为n，则操作分数 nums 只能为以下之一：
     *
     * nums [ 0 ] + nums[1]；
     * nums [ 0 ] + nums[n-1]；
     * nums [ n − 2 ] + nums[n-1]
     *
     * 假设给定了删除的分数target，由于每次只能从两端删除，每次删除区间的长度减2，此时可以利用「区间动态规划」
     * 来解决该问题。每次删除时从两端删除，如果要找到区间  [ i , j ] 的最优状态，此时只需要找到三个区间
     * [ i + 2 , j ] , [ i , j − 2 ] , [ i + 1 , j − 1 ] 的最优状态即可。
     *
     * 在给定删除分数target 的前提下，定义  dfs ( i , j ) 表示当前剩余元素为
     * nums [ i ] , nums [ i + 1 ] , ⋯  , nums [ j ] 时最多可以进行的删除操作次数。
     *
     * 根据上述分析，接下来有以下情况：
     *
     * 如果满足  nums [ i ] + nums [ i + 1 ] = target，此时可以进行1次操作，剩余的元素为  nums [ i + 2 ] , nums [ i + 3 ] , ⋯ , nums [ j ]，此时状态转移为： dfs ( i , j ) = max ( dfs ( i , j ) , 1 + dfs ( i + 2 , j ) )；
     * 如果满足  nums [ j − 1 ] + nums [ j ] = target，此时可以进行1次操作，剩余的元素为  nums [ i ] , nums [ i + 1 ] , ⋯ , nums [ j − 2 ]，此时状态转移为： dfs ( i , j ) = max ( dfs ( i , j ) , 1 + dfs ( i , j − 2 ) )；
     * 如果满足  nums [ i ] + nums [ j ] = target，此时可以进行1次操作，剩余的元素为  nums [ i + 1 ] , nums [ i + 2 ] , ⋯  , nums [ j − 1 ] 此时状态转移为： dfs ( i , j ) = max ( dfs ( i , j ) , dfs ( i + 1 , j − 1 ) ))；
     * 如果上述三种方式都不满足，则此时  dfs ( i , j ) = 0。
     * 递归开始时，分别枚举三种不同的分数target，当  i ≥ j时，此时无法再进行删除操作，递归终止
     *
     * Time and Space : O(n ^ 2)
     */
}
