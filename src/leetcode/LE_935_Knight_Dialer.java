package leetcode;

/**
 * Created by yuank on 11/19/18.
 */
public class LE_935_Knight_Dialer {
    /**
         A chess knight can move as indicated in the chess diagram below:
         https://leetcode.com/problems/knight-dialer/description/

         This time, we place our chess knight on any numbered key of a
         phone pad (indicated above), and the knight makes N-1 hops.
         Each hop must be from one key to another numbered key.

         Each time it lands on a key (including the initial placement of the knight),
         it presses the number of that key, pressing N digits total.

         How many distinct numbers can you dial in this manner?

         Since the answer may be large, output the answer modulo 10^9 + 7.

         Example 1:

         Input: 1
         Output: 10
         Example 2:

         Input: 2
         Output: 20
         Example 3:

         Input: 3
         Output: 46


         Note:

         1 <= N <= 5000

         Medium
     */

    /**
     * https://zxi.mytechroad.com/blog/dynamic-programming/leetcode-935-knight-dialer/
     *
     * DP:
     *  dp[k][i][j] : after k steps, lands on location (i, j).
     * Transition :
     *  dp[k][i][j] = sum(dp[k - 1][x][y]), (x, y) are valid locations that can reach (i, j).
     * Init :
     *  dp[0][i][j] = 1, dp[0][3][0] = dp[0][3][2] = 0
     * Answer:
     *  sum(dp[N][x][y])
     *
     * Similar to LE_688_Knight_Probability_In_Chessboard, except cell (3, 0) and (3, 2) are invalid
     *
     * Time  : O(N * m * n) = O(N * 12) = O(N)
     * Space : O(N * m * n) = O(N)
     **/
    class Solution1 {
        public int knightDialer(int N) {
            int mod = 1000000007;
            int m = 4, n = 3;
            int dp[][][] = new int[N + 1][m][n];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    dp[0][i][j] = 1;
                }
            }
            dp[0][3][0] = 0;
            dp[0][3][2] = 0;

            int[][] dirs = new int[][] {{2, 1}, {-2, 1}, {1, 2}, {1, -2},
                    {2, -1}, {-2, -1}, {-1, 2}, {-1, -2}};

            //!!! "makes N-1 hops"
            for (int k = 1; k < N; k++) {
                for (int i = 0; i < m; i++) {
                    for (int j = 0; j < n; j++) {
                        if (isInvalid(i, j)) {
                            continue;
                        }

                        for (int d = 0; d < dirs.length; d++) {
                            int dx = i + dirs[d][0];
                            int dy = j + dirs[d][1];

                            if ((dx < 0 || dx >= m || dy < 0 || dy >= n) || isInvalid(dx, dy)) {
                                continue;
                            }

                            dp[k][i][j] = ((dp[k][i][j] + dp[k - 1][dx][dy]) % mod);
                        }
                    }
                }
            }

            int res = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    res = (res + dp[N - 1][i][j]) % mod;
                }
            }

            return res;
        }

        private boolean isInvalid(int x, int y) {
            return x == 3 && y != 1;
        }
    }

    /**
     * Space optimized version, using rolling arrays.
     * Time  : O(N * m * n) = O(N * 12) = O(N)
     * Space : O(m * n) = O(12) = O(1)
     *
     */
    class Solution2 {
        public int knightDialer(int N) {
            int mod = 1000000007;
            int m = 4, n = 3;
            int dp[][] = new int[m][n];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    dp[i][j] = 1;
                }
            }
            dp[3][0] = 0;
            dp[3][2] = 0;

            int[][] dirs = new int[][] {{2, 1}, {-2, 1}, {1, 2}, {1, -2},
                    {2, -1}, {-2, -1}, {-1, 2}, {-1, -2}};

            for (int k = 1; k < N; k++) {
                int[][] temp = new int[m][n];//!!!

                for (int i = 0; i < m; i++) {
                    for (int j = 0; j < n; j++) {
                        if (isInvalid(i, j)) {
                            continue;
                        }

                        for (int d = 0; d < dirs.length; d++) {
                            int dx = i + dirs[d][0];
                            int dy = j + dirs[d][1];

                            if ((dx < 0 || dx >= m || dy < 0 || dy >= n) || isInvalid(dx, dy)) {
                                continue;
                            }

                            temp[i][j] = ((temp[i][j] + dp[dx][dy]) % mod);//!!!
                        }
                    }
                }

                dp = temp;//!!!
            }

            int res = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    res = (res + dp[i][j]) % mod;
                }
            }

            return res;
        }

        private boolean isInvalid(int x, int y) {
            return x == 3 && y != 1;
        }
    }
}
