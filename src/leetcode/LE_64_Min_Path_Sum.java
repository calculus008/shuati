package leetcode;

/**
 * Created by yuank on 3/4/18.
 */
public class LE_64_Min_Path_Sum {
    /**
        Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right
        which minimizes the sum of all numbers along its path.

        Note: You can only move either down or right at any point in time.

        Example 1:
        [[1,3,1],
         [1,5,1],
         [4,2,1]]
        Given the above grid map, return 7. Because the path 1→3→1→1→1 minimizes the sum.
     */

    public static int minPathSum(int[][] grid) {
        if(null == grid || grid.length ==0) return 0;

        //!!! we leave out grid[0][0], since it is the start point
        for (int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if (i == 0 && j != 0) grid[i][j] += grid[i][j-1]; //first row
                if (i != 0 && j == 0) grid[i][j] += grid[i-1][j]; //forst column
                if (i != 0 && j != 0) grid[i][j] += Math.min(grid[i][j-1], grid[i-1][j]);
            }
        }

        return grid[grid.length - 1][grid[0].length - 1];
    }

    /**
     * Time and Space : O(mn)
     */
    public int minPathSum2(int[][] grid) {
        int[][] dp = new int[grid.length][grid[0].length];
        for (int i = grid.length - 1; i >= 0; i--) {
            for (int j = grid[0].length - 1; j >= 0; j--) {
                if(i == grid.length - 1 && j != grid[0].length - 1)
                    dp[i][j] = grid[i][j] +  dp[i][j + 1];
                else if(j == grid[0].length - 1 && i != grid.length - 1)
                    dp[i][j] = grid[i][j] + dp[i + 1][j];
                else if(j != grid[0].length - 1 && i != grid.length - 1)
                    dp[i][j] = grid[i][j] + Math.min(dp[i + 1][j], dp[i][j + 1]);
                else
                    dp[i][j] = grid[i][j];
            }
        }
        return dp[0][0];
    }

    public class Solution {
        public int minPathSum(int[][] grid) {
            int[] dp = new int[grid[0].length];
            for (int i = grid.length - 1; i >= 0; i--) {
                for (int j = grid[0].length - 1; j >= 0; j--) {
                    if(i == grid.length - 1 && j != grid[0].length - 1)
                        dp[j] = grid[i][j] +  dp[j + 1];
                    else if(j == grid[0].length - 1 && i != grid.length - 1)
                        dp[j] = grid[i][j] + dp[j];
                    else if(j != grid[0].length - 1 && i != grid.length - 1)
                        dp[j] = grid[i][j] + Math.min(dp[j], dp[j + 1]);
                    else
                        dp[j] = grid[i][j];
                }
            }
            return dp[0];
        }
    }

    public class Solution2 {
        public int minPathSum(int[][] grid) {
            if (null == grid || grid.length == 0) return 0;

            int n = grid.length;
            int m = grid[0].length;

            int[] a = new int[m];
            a[0] = grid[0][0];

            for (int k = 1; k < m; k++) {
                a[k] = grid[0][k] + a[k - 1];
            }

            for (int i = 1; i < n; i++) {
                a[0] += grid[i][0];
                // System.out.println("a[0]=" + a[0]);
                for (int j = 1; j < m; j++) {
                    a[j] = Math.min(a[j - 1], a[j]) + grid[i][j];
                    // System.out.print("a[" + j + "]=" + a[j]);
                }
            }

            return a[m - 1];
        }
    }
}
