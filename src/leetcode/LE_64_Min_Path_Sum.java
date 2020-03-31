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

    /**
     * Brutal Force
     * The Brute Force approach involves recursion. For each element, we consider two paths,
     * rightwards and downwards and find the minimum sum out of those two. It specifies whether
     * we need to take a right step or downward step to minimize the sum.
     *
     * Time  : O(2^(m+n))
     * Space : O(m+n)
     */
    public class Solution_Brutal_Force {
        public int minPathSum(int[][] grid) {
            return calculate(grid, 0, 0);
        }

        public int calculate(int[][] grid, int i, int j) {
            if (i == grid.length || j == grid[0].length) return Integer.MAX_VALUE;
            if (i == grid.length - 1 && j == grid[0].length - 1) return grid[i][j];

            return grid[i][j] + Math.min(calculate(grid, i + 1, j), calculate(grid, i, j + 1));
        }
    }

    /**
     * Space O(1), since no new space is used, only operate on input array.
     * This may not be allowed.
     */
    class Solution_Practice_1 {
        public int minPathSum(int[][] grid) {
            int m = grid.length;
            int n = grid[0].length;

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == 0 && j == 0) {
                        /**
                         * !!!
                         */
                        continue;
                    } else if (i == 0) {
                        grid[i][j] += grid[i][j - 1];
                    } else if (j == 0) {
                        grid[i][j] += grid[i - 1][j];
                    } else {
                        /**
                         * !!!
                         * "+="
                         */
                        grid[i][j] += Math.min(grid[i - 1][j], grid[i][j - 1]);
                    }
                }
            }

            return grid[m - 1][n - 1];
        }
    }

    /**
     * 1D DP Array
     * If it's not allowed to operate on input array, this solution only uses 1D extra array,
     * Space can be O(min(m, n))
     */
    class Solution_Practice_2 {
        public int minPathSum(int[][] grid) {
            int m = grid.length;
            int n = grid[0].length;

            int[] dp = new int[n];
            dp[0] = grid[0][0];

            //first row
            for (int i = 1; i < n; i++) {
                dp[i] = dp[i - 1] + grid[0][i];
            }

            for (int i = 1; i < m; i++) {
                /**
                 * keep adding for the first column
                 */
                dp[0] += grid[i][0];

                for (int j = 1; j < n; j++) {
                    /**
                     * !!!
                     * Each dp value is composed of two parts:
                     * grid[i][j]: the value at current coordinates in grid.
                     * min(a[j - 1], a[j]): the min of the sum values
                     *                      from LEFT column (dp[j - 1], updated in previous iteration)
                     *                      and current column (dp[j] before update)
                     */
                    dp[j] = grid[i][j] + Math.min(dp[j - 1], dp[j]);
                }
            }

            return dp[n - 1];
        }
    }

    /**
     * 2D DP array
     * Use extra 2D dp array, time and space O(mn)
     *
     * dp[i][j] : represents the minimum sum of the path from the index (i, j)
     *            to the bottom rightmost element
     */
    class Solution_Practice_3 {
        public int minPathSum(int[][] grid) {
            int m = grid.length;
            int n = grid[0].length;

            int[][] dp = new int[m][n];
            dp[0][0] = grid[0][0];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == 0 && j == 0) {
                        /**
                         * !!!
                         */
                        continue;
                    } else if (i == 0) {
                        dp[i][j] = grid[i][j] + dp[i][j - 1];
                    } else if (j == 0) {
                        dp[i][j] = grid[i][j] + dp[i - 1][j];
                    } else {
                        dp[i][j] = grid[i][j] + Math.min(dp[i - 1][j], dp[i][j - 1]);
                    }
                }
            }

            return dp[m - 1][n - 1];
        }
    }


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
