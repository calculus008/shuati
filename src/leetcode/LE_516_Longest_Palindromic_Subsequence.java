package leetcode;

/**
 * Created by yuank on 5/29/18.
 */
public class LE_516_Longest_Palindromic_Subsequence {
    /**
         Given a string s, find the longest palindromic subsequence's length in s.
         You may assume that the maximum length of s is 1000.

         Example 1:
         Input:

         "bbbab"
         Output:
         4

         One possible longest palindromic subsequence is "bbbb".

         Example 2:
         Input:

         "cbbd"
         Output:
         2

         One possible longest palindromic subsequence is "bb".

         Medium
     */

    class Solution {
        /**
             DP
             http://zxi.mytechroad.com/blog/dynamic-programming/leetcode-516-longest-palindromic-subsequence/

             dp[i][j]: the longest palindromic subsequence's length of substring(i, j + 1)
             // here, the 'end' is exclusive in String.substring(int start, int end);

             Solution 1 : Time and Space : O(n ^ 2)

             Input
             "bbbab"

             stdout
             i=0,j=0
             i=1,j=1
             i=2,j=2
             i=3,j=3
             i=4,j=4
             i=0,j=1
             i=1,j=2
             i=2,j=3
             i=3,j=4
             i=0,j=2
             i=1,j=3
             i=2,j=4
             i=0,j=3
             i=1,j=4
             i=0,j=4
         **/
        public int longestPalindromeSubseq1(String s) {
            if (s == null || s.length() == 0) return 0;

            int len = s.length();
            int[][] dp = new int[len][len];

            for (int l = 1; l <= len; l++) {
                /**
                 * The only difference from Solution 2 is here :
                 * "i + 1 <= len", it looks the same, but it allows case like
                 * s = "a" to pass without first init dp[i][i] in a separate
                 * loop.
                 */
                for (int i = 0; i + 1 <= len; i++) {
                    int j = i + l - 1;

                    // System.out.println("i="+i+",j="+j);
                    if (i == j) {
                        dp[i][j] = 1;
                        continue;
                    }

                    if (s.charAt(i) == s.charAt(j)) {
                        dp[i][j] = dp[i + 1][j - 1] + 2;
                    } else {
                        dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                    }
                }
            }

            return dp[0][len - 1];
        }

        /**
         * Another form of solution 1, init dp[i][i] separately and "i + l < len",
         * same as LE_730_Count_Different_Palindromic_Subsequences, Solution 2.
         */
        public int longestPalindromeSubseq3(String s) {
            if (s == null || s.length() == 0) return 0;

            int len = s.length();
            int[][] dp = new int[len][len];

            for (int i = 0; i < len; i++) {
                dp[i][i] = 1;
            }

            for (int l = 1; l <= len; l++) {
                for (int i = 0; i + l < len; i++) {
                    int j = i + l;

                    if (s.charAt(i) == s.charAt(j)) {
                        dp[i][j] = dp[i + 1][j - 1] + 2;
                    } else {
                        dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                    }
                }
            }

            return dp[0][len - 1];
        }

        /**
         Solution 2
         Use rolling array to reduce space to O(n)

         Time : O(n ^ 2)
         Space : O(n)
         **/
        public int longestPalindromeSubseq2(String s) {
            int len = s.length();

            int[] dp0 = new int[len];// length of l,     dp0[i] - result for substring starting at i and length is l
            int[] dp1 = new int[len];// length of l - 1, dp1[i] - result for substring starting at i and length is l - 1
            int[] dp2 = new int[len];// length of l - 2, dp2[i] - result for substring starting at i and length is l - 2

            for (int l = 1; l <= len; l++) {
                for (int i = 0; i + l <= len; i++) {
                    int j = i + l - 1;
                    if (i == j) {
                        dp0[i] = 1;
                        continue;
                    }

                    if (s.charAt(i) == s.charAt(j)) {
                        dp0[i] = dp2[i + 1] + 2;
                    } else {
                        dp0[i] = Math.max(dp1[i], dp1[i + 1]);
                    }
                }
                int[] tmp = dp2;
                dp2 = dp1;
                dp1 = dp0;
                dp0 = tmp;
            }

            return dp1[0];
        }

        class Solution_Practice {
            public int longestPalindromeSubseq(String s) {
                if (null == s || s.length() == 0) {
                    return 0;
                }

                int n = s.length();
                int[][] dp = new int[n][n];
                for (int i = 0; i < n; i++) {
                    dp[i][i] = 1;
                }

                char[] t = s.toCharArray();
                for (int l = 1; l <= n; l++) {
                    for (int i = 0; i + l < n; i++) {
                        int j = i + l;

                        if (i == j) {
                            continue;
                        }

                        if (t[i] == t[j]) {
                            dp[i][j] = 2 + dp[i + 1][j - 1];
                        } else {
                            dp[i][j] = Math.max(dp[i][j - 1], dp[i + 1][j]);
                        }
                    }
                }

                return dp[0][n - 1];
            }
        }
    }
}
