package lintcode;

public class LI_837_Palindromic_Substrings {
    /**
     * Given a string, your task is to count how many palindromic substrings in this string.
     * The substrings with different start indexes or end indexes are counted as different
     * substrings even they consist of same characters.
     *
     * Example1
     *
     * Input: "abc"
     * Output: 3
     * Explanation:
     * 3 palindromic strings: "a", "b", "c".
     * Example2
     *
     * Input: "aba"
     * Output: 4
     * Explanation:
     * 4 palindromic strings: "a", "b", "a", "aba".
     *
     * Easy
     */

    /**
     * 考虑如果substring(i,j)如果是回文串，那么str[i]和str[j]一定相同，并且一定满足以下两个条件之一
     * 1.substring(i+1,j-1)也是回文串
     * 2.j-i<=2，即substring(i,j)长度<=2
     * 那么我们就只需要顺着这个思路dp就行了，复杂度O(n^2)
     */

    public int countPalindromicSubstrings (String str) {
        int n = str.length();
        int ans = 0;
        int[][] dp = new int[n][n];

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j <= i; ++j) {
                dp[j][i] = ( (str.charAt (j) == str.charAt (i)) && (i - j <= 2 || dp[j + 1][i - 1] == 1)) ? 1 : 0;
                ans += dp[j][i];
            }
        }
        return ans;
    }
}
