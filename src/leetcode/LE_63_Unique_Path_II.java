package leetcode;

/**
 * Created by yuank on 3/4/18.
 */
public class LE_63_Unique_Path_II {
    /**
        Follow up for "Unique Paths":

        Now consider if some obstacles are added to the grids. How many unique paths would there be?

        An obstacle and empty space is marked as 1 and 0 respectively in the grid.

        For example,
        There is one obstacle in the middle of a 3x3 grid as illustrated below.

        [
          [0,0,0],
          [0,1,0],
          [0,0,0]
        ]
        The total number of unique paths is 2.

        Note: m and n will be at most 100.
     */

    /**
     * DP, optimized using rolling array
     * Time  : O(m * n)
     * Space : O(n) (could be log(min(n, m)))
     */
    class Solution1 {
        public int uniquePathsWithObstacles(int[][] obstacleGrid) {
            if (obstacleGrid[0][0] == 1) return 0;
            int m = obstacleGrid.length;
            int n = obstacleGrid[0].length;
            int[] a = new int[n];
            a[0] = 1; //!!! if entry point is "1", we already return "0", so here we set a[0] to 1.


            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    //int array is initialized with default value of "0",
                    // once a "1" appears in the first column and first row
                    //, the rest will also be 0
                    if (obstacleGrid[i][j] == 1) {
                        a[j] = 0;
                    } else if (j > 0) {
                        a[j] += a[j - 1];
                    }
                }
            }

            return a[n - 1];
        }
    }

    /**
     * 直接从Solution2 改过来的版本。
     * Time : O(m * n)
     * Space : O(m)
     */
    class Solution1_1 {
        public int uniquePathsWithObstacles(int[][] obstacleGrid) {
            if (null == obstacleGrid || obstacleGrid.length == 0 || obstacleGrid[0][0] == 1) {
                return 0;
            }

            int m = obstacleGrid.length;
            int n = obstacleGrid[0].length;

            int[] dp = new int[m];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    /**
                     * !!!
                     * 注意，rwo现在在inner loop with variable j,
                     * 所以, dp[j][i], 不是dp[i][j]
                     */
                    if (obstacleGrid[j][i] == 1) {
                        dp[j] = 0;
                    } else {
                        if (i == 0 && j == 0) {
                            dp[j] = 1;
                        } else if (i == 0 && j != 0) {
                            dp[j] = dp[j - 1];
                        } else if (i != 0 && j == 0) {
                            dp[j] = dp[j];
                        } else {
                            dp[j] = dp[j] + dp[j - 1];
                        }
                    }
                }
            }

            return dp[m - 1];
        }
    }
    /**
     * Time  : O(m * n)
     * Space : O(m * n)
     */
    class Solution2 {
        public int uniquePathsWithObstacles(int[][] obstacleGrid) {
            if (null == obstacleGrid || obstacleGrid.length == 0 || obstacleGrid[0][0] == 1) {
                return 0;
            }

            int m = obstacleGrid.length;
            int n = obstacleGrid[0].length;

            int[][] dp = new int[m][n];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j <n; j++) {
                    if (obstacleGrid[i][j] == 1) {
                        dp[i][j] = 0;
                    } else {
                        if (i == 0 && j == 0) {
                            /**
                             * !!!
                             */
                            dp[i][j] = 1;
                        } else if (i == 0 && j != 0) {
                            dp[i][j] = dp[i][j - 1];
                        } else if (i != 0 && j == 0) {
                            dp[i][j] = dp[i - 1][j];
                        } else {
                            dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                        }
                    }
                }
            }

            return dp[m - 1][n - 1];
        }
    }
}
