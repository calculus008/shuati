package leetcode;

import java.util.Arrays;

/**
 * Created by yuank on 3/17/18.
 */
public class LE_132_Palindrome_Partition_II {
    /**
        Given a string s, partition s such that every substring of the partition is a palindrome.

        Return the minimum cuts needed for a palindrome partitioning of s.

        For example, given s = "aab",
        Return 1 since the palindrome partitioning ["aa","b"] could be produced using 1 cut.
     */

    /**
     * Partition to groups + Palindrome
     *
     * Tricky part of the problem is that it is two DP problems combined :
     * 1.valid[j][i] : DP to find out if substring between index j and i is palindrome
     *                 [0,...., j, j + 1, ....., i - 1, i, ...., n - 1]
     *                               |_____________|
     *                             valid[j + 1][i - 1]
     *
     *                     valid[i][j] = s[i] == s[j] && valid[j + 1][i - 1]
     *
     * 2.dp[i] : use valid[j][i], find out the min number of cuts for substring between index 0 and i
     *           so that every part is palindrome.
     *                [0, ....., j, j + 1, ....., i - 1, i, ...., n - 1]
     *                 |_________|    |__________________|
     *                  subproblem        1 palindrome
     *
     *                  if valid[j + 1][i], dp[i] = min(dp[i], dp[j] + 1)
     *
     *                  Try all possible partitions, 0 <= j <= i
     *
     *   Notice: 1.it's valid[j][i], not valid[i][j], 0 <= j <= i
     *           2.Deal with the case when j = 0
     *           3.Here i and j are actual index number, no padding needed in both dp arrays.
     *
     *  This solution combined two DP in a single double for loops.
     *
     *  A more regular DP solution is Huahua's first solution:
     *  https://zxi.mytechroad.com/blog/dynamic-programming/leetcode-132-palindrome-partitioning-ii/
     *
     *  It takes two double for loops, first, it populates valid[][],
     *  then, use valid[][] to populate dp[].
     *
     *  Solution1 is a another form of Solution2.
     */
    class Solution1 {
        public int minCut(String s) {
            if (null == s || s.length() == 0) return 0;

            int n = s.length();
            boolean[][] valid = new boolean[n][n];
            int[] dp = new int[n];
            /**
             * !!!
             * We just need to set a max number, here for string length of n,
             * the max possible number of cuts to meet requirements is n - 1,
             * it means we cut it as individual char.
             *
             * Must have this step, don't forget!!!
             */
            Arrays.fill(dp, n);

            for (int i = 0; i < n; i++) {
                /**
                 * !!!
                 * "j <= i"
                 */
                for (int j = 0; j <= i; j++) {
                    /**
                     * !!!
                     * "valid[j + 1][i - 1], j在前， i在后!!!
                     */
                    if (s.charAt(i) == s.charAt(j) && (i - j < 2 || valid[j + 1][i - 1])) {
                        /**
                         * !!!
                         * "valid[j][i], j在前， i在后!!!
                         */
                        valid[j][i] = true;
                        /**
                         * !!!
                         * "j == 0 ?", because we need to use dp[j - 1], we must
                         * do this check to make sure index is not out of boundary.
                         * When j is 0, j - 1 will be -1, so we can't use it as index.
                         * When we come to this step and j is 0, it means valid[0][j] is true,
                         * so we don't need to make any cut, it is already palindrome, so
                         * we set dp[i] to 0.
                         */
                        dp[i] = j == 0 ? 0 : Math.min(dp[i], dp[j - 1] + 1);
                    }
                }
            }

            return dp[n - 1];
        }
    }

    /**
        DP
        Time : O(n ^ 2)
        Space : O(n ^ 2)


         cut[i] is the minimum of cut[j - 1] + 1 (j <= i), if [j, i] is palindrome.
         If [j, i] is palindrome, [j + 1, i - 1] is palindrome, and c[j] == c[i].

         a   b   a   |   c  c
                         j  i
                j-1  |  [j, i] is palindrome
            cut(j-1) +  1
    */
    class Solution2 {
        public int minCut(String s) {
            if (s == null || s.length() == 0) return 0;

            int len = s.length();
            int[] cuts = new int[len];
            boolean[][] isPalindrome = new boolean[len][len];

            for (int i = 0; i < len; i++) {
                int min = i; //number of cuts if each cut is a single char, example a b a, length is 3, 2 cuts
                for (int j = 0; j <= i; j++) {//枚举分割点with j
                    //Check if substring i to j is a palindrome:
                    //c='acbca' : j=0, i=4
                    // i - j < 2: 0 -> aa, 1 -> aba
                    if (s.charAt(i) == s.charAt(j) && (i - j < 2 || isPalindrome[j + 1][i - 1])) {
                        isPalindrome[j][i] = true;
                        min = j == 0 ? 0 : Math.min(min, cuts[j - 1] + 1);
                    }
                }
                cuts[i] = min;
            }
            return cuts[len - 1];
        }
    }

    /**
     * Optimal solution, reduce space to O(n), use expanding solution to check if j to i is palindrome
     *
     * 1.substring j to i is palindrome with length as odd number:
     *      [0,...., j, j + 1, ....., m, ....., i - 1, i, ...., n - 1]
     *               |<---------------|--------------->|
     *
     * 2.substring j to i is palindrome with length as even number:
     *      [0,...., j, j + 1, ....., m, m + 1, ....., i - 1, i, ...., n - 1]
     *               |<---------------|    |--------------->|
     *
     *  Same idea as the expanding solution for LE_05_Longest_Palindromic_Substring
     */
    class Solution3 {
        public int minCut(String s) {
            if (null == s || s.length() == 0) return 0;

            int n = s.length();
            int[] dp = new int[n];
            Arrays.fill(dp, n);

            for (int m = 0; m < n; m++) {
                for (int i = m, j = m; i >= 0 && j < n && s.charAt(i) == s.charAt(j); j++, i--) {
                    dp[j] = Math.min(dp[j], i == 0 ? 0 : dp[i - 1] + 1);
                }

                for (int i = m, j = m + 1; i >= 0 && j < n && s.charAt(i) == s.charAt(j); j++, i--) {
                    dp[j] = Math.min(dp[j], i == 0 ? 0 : dp[i - 1] + 1);
                }
            }

            return dp[n - 1];
        }
    }
}
