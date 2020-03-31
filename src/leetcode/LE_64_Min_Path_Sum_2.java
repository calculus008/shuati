package leetcode;

public class LE_64_Min_Path_Sum_2 {
    /**
     * Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right
     * which minimizes the sum of all numbers along its path.
     *
     * Note: You can only move either down or right at any point in time.
     *
     *  Example 1:
     *         [[1,3,1],
     *          [1,5,1],
     *          [4,2,1]]
     * Given the above grid map, return 7. Because the path 1→3→1→1→1 minimizes the sum.
     */

    /**
     * 2D DP Array
     *
     * Time  : O(mn)
     * Space : O(mn)
     */
    public class Solution1 {
        public int minPathSum(int[][] grid) {
            if (grid == null || grid.length == 0) return 0;

            int m = grid.length;
            int n = grid[0].length;
            int[][] dp = new int[m][n];

            for (int i = m - 1; i >= 0; i--) {
                for (int j = n - 1; j >= 0; j--) {
                    if (i == m - 1 && j == n - 1) {
                        dp[i][j] = grid[m - 1][n - 1];
                    } else if (i == m - 1) {
                        dp[i][j] = grid[i][j] + dp[i][j + 1];
                    } else if (j == n - 1) {
                        dp[i][j] = grid[i][j] + dp[i + 1][j];
                    } else {
                        dp[i][j] = grid[i][j] + Math.min(dp[i][j + 1], dp[i + 1][j]);
                    }
                }
            }

            return dp[0][0];
        }
    }

    /**
     * 1D DP Array
     *
     * Time : O(mn)
     * Space : O(n)
     */
    public class Solution2 {
        public int minPathSum(int[][] grid) {
            if (grid == null || grid.length == 0) return 0;

            int m = grid.length;
            int n = grid[0].length;
            int[] dp = new int[n];

            for (int i = m - 1; i >= 0; i--) {
                for (int j = n - 1; j >= 0; j--) {
                    if (i == m - 1 && j == n - 1) {
                        dp[j] = grid[m - 1][n - 1];
                    } else if (i == m - 1) {
                        dp[j] = grid[i][j] + dp[j + 1];
                    } else if (j == n - 1) {
                        dp[j] = grid[i][j] + dp[j];
                    } else {
                        dp[j] = grid[i][j] + Math.min(dp[j + 1], dp[j]);
                    }
                }
            }

            return dp[0];
        }
    }

    /**
     * From bottom-left to top-right。
     *
     * grid[m - 1][0] to grid[0][n - 1], 反着推，start from grid[0][n - 1] to grid[m - 1][0]
     */
    public class Solution3 {
        public int minPathSum(int[][] grid) {
            if (grid == null || grid.length == 0) return 0;

            int m = grid.length;
            int n = grid[0].length;
            int[][] dp = new int[m][n];

            for (int i = 0; i >= m - 1; i++) {//i++
                for (int j = n - 1; j >= 0; j--) {//j--
                    if (i == 0 && j == n - 1) {
                        dp[i][j] = grid[0][n - 1];
                    } else if (i == 0) {
                        dp[i][j] = grid[i][j] + dp[i][j + 1];
                    } else if (j == n - 1) {
                        dp[i][j] = grid[i][j] + dp[i - 1][j];//i - 1
                    } else {
                        dp[i][j] = grid[i][j] + Math.min(dp[i][j + 1], dp[i - 1][j]);
                    }
                }
            }

            return dp[m - 1][0];
        }
    }

    /**
     * Follow up:
     *
     * Minimum Path Sum 找出到達終點時的最小值
     * 然後再給你一個一樣大小的array
     * 記錄哪些點是不能走的
     * ex:
     * 在新的array 裡
     * 1 表示有炸彈不能行走，0表示可行走的區域
     */

    public class Solution4 {
        public int minPathSum(int[][] grid, int[][] bombs) {
            if (grid == null || grid.length == 0) return 0;

            int m = grid.length;
            int n = grid[0].length;
            int[][] dp = new int[m][n];

            if (bombs[m - 1][n - 1] == 1 || bombs[0][0] == 1) return Integer.MAX_VALUE;

            for (int i = m - 1; i >= 0; i--) {
                for (int j = n - 1; j >= 0; j--) {
                    if (i == m - 1 && j == n - 1) {
                        dp[i][j] = grid[m - 1][n - 1];
                    } else if (i == m - 1) {
                        dp[i][j] = bombs[i][j + 1] == 1 ? Integer.MAX_VALUE : grid[i][j] + dp[i][j + 1];
                    } else if (j == n - 1) {
                        dp[i][j] = bombs[i + 1][j] == 1 ? Integer.MAX_VALUE : grid[i][j] + dp[i + 1][j];
                    } else {
                        int min = Math.min(dp[i][j + 1], dp[i + 1][j]);
                        dp[i][j] = min == Integer.MAX_VALUE ? Integer.MAX_VALUE : grid[i][j] + min;
                    }
                }
            }

            return dp[0][0];
        }
    }
}
