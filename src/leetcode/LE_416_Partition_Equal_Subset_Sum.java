package leetcode;

/**
 * Created by yuank on 10/29/18.
 */
public class LE_416_Partition_Equal_Subset_Sum {
    /**
         Given a non-empty array containing only positive integers,
         find if the array can be partitioned into two subsets such
         that the sum of elements in both subsets is equal.

         Note:
         Each of the array element will not exceed 100.
         The array size will not exceed 200.

         Example 1:

         Input: [1, 5, 11, 5]

         Output: true

         Explanation: The array can be partitioned as [1, 5, 5] and [11].

         Example 2:

         Input: [1, 2, 3, 5]

         Output: false

         Explanation: The array cannot be partitioned into equal sum subsets.

         Medium
     */

    /**
     * Knapsack DP
     * LI_92_Backpack 的马甲
     *
     * Solution 1
     * Time and Space : O(n * m)
     */
    public boolean canPartition1(int[] nums) {
        if (nums == null || nums.length == 0) return false;

        int sum = 0;
        for(int num : nums) {
            sum += num;
        }

        if (sum % 2 != 0) return false; //!!!

        int m = sum / 2;
        int n = nums.length;
        boolean[][] dp = new boolean[n + 1][m + 1];
        dp[0][0] = true;//!!!

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= nums[i - 1] && dp[i - 1][j - nums[i - 1]]) {//!!!
                    dp[i][j] = true;
                }
            }
        }

        return dp[n][m];
    }

    /**
     * Solution 2
     * Time  : O(m * n)
     * Space : O(m)
     *
     * Same logic as Solution 3 for LI_92_Backpack but use 1D boolean dp array
     */
    public boolean canPartition2(int[] nums) {
        if (nums == null || nums.length == 0) return false;

        int sum = 0;
        for(int num : nums) {
            sum += num;
        }

        if (sum % 2 != 0) return false;

        int m = sum / 2;
        int n = nums.length;

        boolean[] dp = new boolean[m + 1];//dp[i] : if we can pack to size i.
        dp[0] = true;

        for (int i = 0; i < n; i++) {
            for (int j = m; j >= nums[i]; j--) {
                dp[j] = dp[j - nums[i]] || dp[j]; //!!!
            }
        }

        return dp[m];
    }
}
