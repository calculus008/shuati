package leetcode;

/**
 * Created by yuank on 11/25/18.
 */
public class LE_730_Count_Different_Palindromic_Subsequences {
    /**
         Given a string S, find the number of different non-empty palindromic subsequences in S,
         and return that number modulo 10^9 + 7.

         A subsequence of a string S is obtained by deleting 0 or more characters from S.

         A sequence is palindromic if it is equal to the sequence reversed.

         Two sequences A_1, A_2, ... and B_1, B_2, ... are different if there is some i for which A_i != B_i.

         Example 1:
         Input:
         S = 'bccb'
         Output: 6
         Explanation:
         The 6 different non-empty palindromic subsequences are 'b', 'c', 'bb', 'cc', 'bcb', 'bccb'.
         Note that 'bcb' is counted only once, even though it occurs twice.

         Example 2:
         Input:
         S = 'abcdabcdabcdabcdabcdabcdabcdabcddcbadcbadcbadcbadcbadcbadcbadcba'
         Output: 104860361
         Explanation:
         There are 3104860382 different non-empty palindromic subsequences, which is 104860361 modulo 10^9 + 7.
         Note:

         The length of S will be in the range [1, 1000].
         Each character S[i] will be in the set {'a', 'b', 'c', 'd'}.

         Hard
     */

    /**
     * http://zxi.mytechroad.com/blog/dynamic-programming/leetcode-730-count-different-palindromic-subsequences/
     *
     * Recusion with Memization
     * dp[i][j] : count of different palindromic subsequence between s[i] ~ s[j] (inclusive)
     *
     * dp[i][j] =
     *   if s[i] != s[j]                                   # count(abcd)
     *   dp[i][j - 1] + dp[i + 1][j] - dp[i + 1][j - 1]    # count(abc) + count(bcd) - count(bc)
     *
     *   if s[i] == s[j]
     *   dp[i + 1][j - 1] * 2 + 2                   s[i] NOT in s[i + 1] ~ s[j - ]   "bccb"
     *   dp[i + 1][j - 1] * 2 + 1                   count of s[i] between s[i + 1] ~ s[j - 1] is 1    "bcbcb"
     *   dp[i + 1][j - 1] * 2 - dp[l + 1][r - 1]    l and r is the first/last position of s[i] in s[i + 1] ~ s[j - 1]   "bbcabb"
     *
     * Time  : O(n ^ 2)
     * Space : O(n ^ 2)
     */
    class Solution1 {
        private static final int kMod = 1000000007;
        private int[][] mem;
        public int countPalindromicSubsequences(String S) {
            if (S == null || S.length() == 0) {
                return 0;
            }

            int n = S.length();
            mem = new int[n][n];

            return helper(S.toCharArray(), 0, n - 1);
        }

        private int helper(char[] s, int i, int j) {
            if (i > j) {
                return 0;
            }

            /**
             !!! only one char
             **/
            if (i == j) {
                return 1;
            }

            if (mem[i][j] > 0) {
                return mem[i][j];
            }

            long res = 0;
            if (s[i] == s[j]) {
                res = helper(s, i + 1, j - 1) * 2;

                int l = i + 1;
                int r = j - 1;
                while (l <= r && s[l] != s[i]) {
                    l++;
                }
                while (l <= r && s[r] != s[i]) {
                    r--;
                }

                /**
                 * !!!
                 * l and r is the first/last position of s[i] in s[i + 1 : j - 1]
                 */
                if (l > r) {
                    res += 2;
                } else if (l == r) {
                    res += 1;
                } else {
                    /**
                     helper(s, l + 1, r - 1), NOT (s, i + 1,j - 1)
                     **/
                    res -= helper(s, l + 1, r - 1);
                }
            } else {
                res = helper(s, i, j - 1) + helper(s, i + 1, j) - helper(s, i + 1, j - 1);
            }

            mem[i][j] = (int) ((res + kMod) % kMod);
            return mem[i][j];
        }
    }
}
