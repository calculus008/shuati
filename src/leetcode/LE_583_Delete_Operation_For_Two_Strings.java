package leetcode;

public class LE_583_Delete_Operation_For_Two_Strings {
    /**
     * Given two words word1 and word2, find the minimum number of steps required
     * to make word1 and word2 the same, where in each step you can delete one
     * character in either string.
     *
     * Example 1:
     *
     * Input: "sea", "eat"
     * Output: 2
     * Explanation: You need one step to make "sea" to "ea" and another step to
     * make "eat" to "ea".
     *
     * Note:
     * The length of given words won't exceed 500.
     * Characters in given words can only be lower-case letters.
     */

    class Solution {
        //based on LCS: http://www.geeksforgeeks.org/longest-common-subsequence/
        public int minDistance(String word1, String word2) {
            int lcs = lcs(word1, word2);

            return word1.length() - lcs + word2.length() - lcs;
        }

        private int lcs(String word1, String word2) {
            int m = word1.length();
            int n = word2.length();

            int[][] table = new int[m + 1][n + 1];

            for (int i = 0; i <= m; i++) {
                for (int j = 0; j <= n; j++) {
                    if (i == 0 || j == 0) {
                        table[i][j] = 0;
                    } else {
                        //!!!compare char at i-1 and j-1
                        if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                            table[i][j] = 1 + table[i - 1][j - 1];
                        } else {
                            table[i][j] = Math.max(table[i - 1][j], table[i][j - 1]);
                        }
                    }
                }
            }

            return table[m][n];
        }
    }

    class Solution_Practice {
        public int minDistance(String word1, String word2) {
            if (null == word1 || null == word2) {
                return 0;
            }

            int l1 = word1.length();
            int l2 = word2.length();

            int lcs = getLCS(word1.toCharArray(), word2.toCharArray());

            return l1 + l2 - 2 * lcs;
        }

        private int getLCS(char[] word1, char[] word2) {
            int[][] dp = new int[word1.length + 1][word2.length + 1];

            for (int i = 1; i <= word1.length; i++) {
                for (int j = 1; j <= word2.length; j++) {
                    if (word1[i - 1] == word2[j - 1]) {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                    } else {
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                    }
                }
            }

            return dp[word1.length][word2.length ];
        }
    }
}
