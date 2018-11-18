package leetcode;

/**
 * Created by yuank on 11/15/18.
 */
public class LE_576_Out_Of_Boundary_Paths {
    /**
         There is an m by n grid with a ball. Given the start coordinate (i,j) of the ball,
         you can move the ball to adjacent cell or cross the grid boundary in four directions
         (up, down, left, right). However, you can at most move N times. Find out the number
         of paths to move the ball out of grid boundary. The answer may be very large,
         return it after mod 109 + 7.

         Example 1:

         Input: m = 2, n = 2, N = 2, i = 0, j = 0
         Output: 6

         Medium
     */

    /**
         Start from outside border, count how many ways to get to cell(i, j)
         dp[k][i][j] : count number of ways to get to cell (i, j) by moving k steps

         dp[s][x][y] : if x, y out of border, 1
         if x, y inside border, dp[s - 1][x - 1][y] +
         dp[s - 1][x][y - 1] +
         dp[s - 1][x + 1][y] +
         dp[s - 1][x][y + 1]

         Answer : dp[K][i][j]

         Time  : O(n * m * N)
         Space : O(n * m * N)
     **/
    public int findPaths1(int m, int n, int N, int i, int j) {
        int[][][] dp = new int[N + 1][m][n];//!!! "N + 1"

        int mod = 1000000007;
        int[][] dirs = new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int k = 1; k <= N; k++) {
            for (int x = 0; x < m; x++) {
                for (int y = 0; y < n; y++) {
                    for (int d = 0; d < dirs.length; d++) {
                        int dx = x + dirs[d][0];
                        int dy = y + dirs[d][1];
                        if (dx < 0 || dx >= m || dy < 0 || dy >= n) {
                            dp[k][x][y] += 1;//!!! "+= 1"
                        } else {
                            dp[k][x][y] = (dp[k][x][y] + dp[k - 1][dx][dy]) % mod;//!!!
                        }
                    }
                }
            }
        }

        return dp[N][i][j];
    }

    /**
         Optimize on space, use 2D array instead of 3D array

         Time  : O(n * m * N)
         Space : O(n * m)
     */
    public int findPaths2(int m, int n, int N, int i, int j) {
        int[][] dp = new int[m][n];

        int mod = 1000000007;
        int[][] dirs = new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int k = 1; k <= N; k++) {
            int[][] temp = new int[m][n];

            for (int x = 0; x < m; x++) {
                for (int y = 0; y < n; y++) {
                    for (int d = 0; d < dirs.length; d++) {
                        int dx = x + dirs[d][0];
                        int dy = y + dirs[d][1];
                        if (dx < 0 || dx >= m || dy < 0 || dy >= n) {
                            temp[x][y] += 1;
                        } else {
                            temp[x][y] = (temp[x][y] + dp[dx][dy]) % mod;
                        }
                    }
                }
            }

            dp = temp;
        }

        return dp[i][j];
    }
}
