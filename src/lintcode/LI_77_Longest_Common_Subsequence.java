package lintcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yuank on 10/26/18.
 */
public class LI_77_Longest_Common_Subsequence {
    /**
         Given two strings, find the longest common subsequence (LCS).

         Your code should return the length of LCS.

         Example
         For "ABCD" and "EDCA", the LCS is "A" (or "D", "C"), return 1.

         For "ABCD" and "EACB", the LCS is "AC", return 2.

         Clarification
         What's the definition of Longest Common Subsequence?

         https://en.wikipedia.org/wiki/Longest_common_subsequence_problem
         http://baike.baidu.com/view/2020307.htm

         Medium
     */

    /**
     * Solution 1
     * DP
     * Time and Space : O(m * n)
     *
     • state: f[i][j]表示A前i个字符配上B前j个字符的LCS的长度
     • function: f[i][j] = f[i-1][j-1] + 1           // A[i - 1] == B[j - 1]
     •                   = MAX(f[i-1][j], f[i][j-1]) // A[i - 1] != B[j - 1]
     • intialize: f[i][0] = 0 f[0][j] = 0
     • answer: f[n][m]
     */
    public int longestCommonSubsequence1(String A, String B) {
        if (A == null || B == null || A.length() == 0 || B.length() == 0) return 0;

        int len1 = A.length();
        int len2 = B.length();
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (A.charAt(i - 1) != B.charAt(j - 1)) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                } else {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
            }
        }

        return dp[len1][len2];
    }

    /**
     * Solution 2
     * DFS + 记忆化搜索，核心思路和使用Iterative的方法一样。
     * In case面试官follow up，要求使用Recursive的方法
     * 时间复杂度也是O(mn)
     */
    public int longestCommonSubsequence2(String A, String B) {
        if (A.isEmpty() || B.isEmpty()) {
            return 0;
        }

        int m = A.length();
        int n = B.length();
        int[][] memo = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            Arrays.fill(memo[i], -1);
        }

        return dfs(A, B, memo, m, n);
    }

    private int dfs(String A, String B, int[][] memo, int m, int n) {
        if (memo[m][n] != -1) {
            return memo[m][n];
        }

        int result = 0;

        if (m == 0 || n == 0) {
            result = 0;
        } else if (A.charAt(m - 1) == B.charAt(n - 1)) {
            result = dfs(A, B, memo, m - 1, n - 1) + 1;
        } else {
            result = Math.max(dfs(A, B, memo, m, n - 1), dfs(A, B, memo, m - 1, n));
        }
        memo[m][n] = result;
        return result;
    }

    /**
     * 本题的两个fellow up：
     * fellow up 1：求出任意一个LCS
     * fellow up 2：求出所有的LCS
     *
     * dp[][] is already built before calling following methods.
     */
    private String getAnyOneLCS_NonRecursive(String A, String B, int[][] dp) {
        StringBuilder sb = new StringBuilder();
        int i = A.length(), j = B.length();
        while (i > 0 && j > 0) {
            if (A.charAt(i - 1) == B.charAt(j - 1)) {
                sb.append(A.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] >= dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return sb.reverse().toString();
    }

    private String getAnyOneLCS_Recursive(String A, String B, int[][] dp, int i, int j) {
        if (i == 0 || j == 0) {
            return "";
        }

        if (A.charAt(i - 1) == B.charAt(j - 1)) {
            return getAnyOneLCS_Recursive(A, B, dp, i - 1, j - 1) + A.charAt(i - 1);
        } else if (dp[i - 1][j] >= dp[i][j - 1]) {
            return getAnyOneLCS_Recursive(A, B, dp, i - 1, j);
        } else {
            return getAnyOneLCS_Recursive(A, B, dp, i, j - 1);
        }
    }

    private Set<String> getAllLCS_imp2(String A, String B, int i, int j, int[][] dp) {
        Set<String> ret = new HashSet<>();
        if (i == 0 || j == 0) {
            ret.add("");
            return ret;
        }

        if (A.charAt(i - 1) == B.charAt(j - 1)) {
            Set<String> tmp = getAllLCS_imp2(A, B, i - 1, j - 1, dp);
            for (String s : tmp) {
                ret.add(s + A.charAt(i - 1));
            }
            return ret;
        } else {
            Set<String> tmp = new HashSet<>();
            if (dp[i - 1][j] >= dp[i][j - 1]) {
                tmp = getAllLCS_imp2(A, B, i - 1, j, dp);
                ret.addAll(tmp);
            }
            if (dp[i][j - 1] >= dp[i - 1][j]) {
                tmp = getAllLCS_imp2(A, B, i, j - 1, dp);
                ret.addAll(tmp);
            }
            return ret;
        }
    }

    private Set<String> getAllLCS_imp1(String A, String B, int[][] dp) {
        Set<String> results = new HashSet<>();
        dfs("", A.length(), B.length(), A, B, dp, results);
        return results;
    }

    private void dfs(String s, int i, int j, String A, String B, int[][] dp, Set<String> results) {
        if (i == 0 || j == 0) {
            results.add(new StringBuilder(s).reverse().toString());
            return;
        }

        if (A.charAt(i - 1) == B.charAt(j - 1)) {
            dfs(s + A.charAt(i - 1), i - 1, j - 1, A, B, dp, results);
        } else {
            if (dp[i - 1][j] >= dp[i][j - 1]) {
                dfs(s, i - 1, j, A, B, dp, results);
            }

            if (dp[i][j - 1] >= dp[i - 1][j]) {
                dfs(s, i, j - 1, A, B, dp, results);
            }
        }
    }
}
