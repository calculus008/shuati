package leetcode;

/**
 * Created by yuank on 10/28/18.
 */
public class LE_115_Distinct_Subsequences {
    /**
         Given a string S and a string T, count the number of distinct subsequences of S which equals T.

         A subsequence of a string is a new string which is formed from the original string by deleting some
         (can be none) of the characters without disturbing the relative positions of the remaining characters.
         (ie, "ACE" is a subsequence of "ABCDE" while "AEC" is not).

         Example 1:

         Input: S = "rabbbit", T = "rabbit"
         Output: 3
         Explanation:

         As shown below, there are 3 ways you can generate "rabbit" from S.
         (The caret symbol ^ means the chosen letters)

         rabbbit
         ^^^^ ^^
         rabbbit
         ^^ ^^^^
         rabbbit
         ^^^ ^^^
         Example 2:

         Input: S = "babgbag", T = "bag"
         Output: 5
         Explanation:

         As shown below, there are 5 ways you can generate "bag" from S.
         (The caret symbol ^ means the chosen letters)

         babgbag
         ^^ ^
         babgbag
         ^^    ^
         babgbag
         ^    ^^
         babgbag
         ^  ^^
         babgbag
         ^^^

         Hard
     */


    /**
     * DP
     *
     * http://www.cnblogs.com/yuzhangcmu/p/4196373.html
     *
     * State :
     * dp[i][j] : number of distinct subsequences in the first i chars from s that matches the first chars (subarray) of t.
     *
     * Init :
     *   dp[0][0] = 1, null in s matches null in t, so there's one valid subsequence.
     *
     *   When i = 0 and j >= 1, meaning it is null in s, t starts from first char,  dp[0][j] = 0,
     *   null does not match any
     *
     *   when j = 0 and i >= 1, meaning it is null in t, s starts from first char , dp[i][0] = 1,
     *   so they all have a subsequence "null" that matches the null in t.
     *
     * Equation:
     *    s.charAt(i - 1) == t.charAt(j - 1) :  if the current character in S equal to the current character T,
     *                                          then the distinct number of subsequences:
     *                                          the number we had before
     *                                                plus
     *                                          the distinct number of subsequences we had with less longer T and less longer S.
     *                                          dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j]
     *
     *
     *    s.charAt(i - 1) != t.charAt(j - 1) :  if the current character in S doesn't equal to current character T,
     *                                          then we have the same number of distinct subsequences as we had without the new character.
     *                                          dp[i][j] = dp[i -1][j];
     *
     * Example: s = "babgbag", t = "bag"
     *
     *        null b a g
     *  null   1   0 0 0
     *   b     1   1 0 0
     *   a     1   1 1 0
     *   b     1   2 1 0
     *   g     1   2 1 1
     *   b     1   3 1 1
     *   a     1   3 4 1
     *   g     1   3 4 5
     *
     *   Time and Space : O(mn)
     *
     */
    public int numDistinct(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) return 0;

        int m = s.length();
        int n = t.length();
        int[][] dp = new int[m + 1][n + 1];

        dp[0][0] = 1;

        /**
         * !!!start at 1
         */
        for (int i = 1; i <= n; i++) {
            dp[0][i] = 0;
        }

        /**
         * !!!start at 1
         */
        for (int i = 1; i <= m; i++) {
            dp[i][0] = 1;
        }

        /**
         * the init logic can be simplied as :
         *
         *         for (int i = 0; i <= m; i++) {
         *              dp[i][0] = 1;
         *         }
         */
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[m][n];
    }


}
