package leetcode;

public class LE_920_Number_Of_Music_Playlists {
    /**
     * Your music player contains N different songs and she wants to listen to L
     * (not necessarily different) songs during your trip.  You create a playlist so that:
     *
     * Every song is played at least once
     * A song can only be played again only if K other songs have been played
     * Return the number of possible playlists.  As the answer can be very large, return it modulo 10^9 + 7.
     *
     *
     *
     * Example 1:
     * Input: N = 3, L = 3, K = 1
     * Output: 6
     * Explanation: There are 6 possible playlists. [1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1].
     *
     * Example 2:
     * Input: N = 2, L = 3, K = 0
     * Output: 6
     * Explanation: There are 6 possible playlists. [1, 1, 2], [1, 2, 1], [2, 1, 1], [2, 2, 1], [2, 1, 2], [1, 2, 2]
     *
     * Example 3:
     * Input: N = 2, L = 3, K = 1
     * Output: 2
     * Explanation: There are 2 possible playlists. [1, 2, 1], [2, 1, 2]
     *
     * Note:
     *
     * 0 <= K < N <= L <= 100
     *
     * Hard
     *
     * Same as LI_1574_Music_Playlist
     */

    /**
     * DP
     *
     * Time : O(LN)
     */
    class Solution {
        public int numMusicPlaylists(int N, int L, int K) {
            int mod = (int)(1e9 + 7);

            /**
             * dp[i][j] : number list by using j unique songs to create the first i songs in the list
             */
            long[][] dp = new long[L + 1][N + 1];
            dp[0][0] = 1;

            for (int i = 1; i <= L; i++) {
                for (int j = 1; j <= N; j++) {
                    dp[i][j] = (dp[i][j] + dp[i - 1][j - 1] * (N - (j - 1)) % mod) % mod;
                    if (j > K) {
                        dp[i][j] = (dp[i][j] + dp[i - 1][j] * (j - K) % mod) % mod;
                    }
                }
            }

            return (int)dp[L][N];
        }
    }
}
