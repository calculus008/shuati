package leetcode;

import java.util.Arrays;

/**
 * Created by yuank on 3/5/18.
 */
public class LE_72_Edit_Distance {
    /**
        Given two words word1 and word2, find the minimum number of steps required to
        convert word1 to word2. (each operation is counted as 1 step.)

        You have the following 3 operations permitted on a word:

        a) Insert a character
        b) Delete a character
        c) Replace a character
     **/

    /**
     * 对该题最清楚的解释
     * http://zxi.mytechroad.com/blog/dynamic-programming/leetcode-72-edit-distance/
     */

    /**
        DP Solution : Time : O(m * n), Space : O(m * n)

        "convert word1 to word2", so all actions (insert, delete and replace) are done on word1
        dp[i][j] : min steps needed to convert word1 (1 ~ i) to word2 (1 ~ j) .

        http://zxi.mytechroad.com/blog/dynamic-programming/leetcode-72-edit-distance/

       D("abbc", "acc")
       = D("abb", "ac")
       = 1 + min ( D("ab", "ac")   //Delete : Delete "b" from "abb"
                   D("abb", "a")   //Insert : Insert "c" at the end of "abb" -> "abbc", which has the same char 'c' as "ac"
                                   //         at the last position, so they cancel each other:
                                   //         "abbc" vs "ac" --> "abb" vs "a"

                   D("ab",  "a"))  //Replace : Replace last "b" in "abb" with "c", "abc" vs "ac" --> "ab" vs "a"

        DP:
        d(i, j) = minDistance(word1[0..i-1], word2[0..j-1]), or the first i chars in word1 vs the first j chars in word2.
                  d(i, j) 表示把 str1 前 i 个字符编辑成 str2 前 j 个字符所需要的最小 edit distance

        Init:
        1.if j = 0, i
        2.if i = 0, j

        Transition:
        if word1[i -1] = word2[j -1]
            d(i - i, j - 1)
        else
           min (d(i - 1, j), d(i, j - 1), d(i - 1, j - 1) + 1

        Result:
        dp(l1, l2)
     **/

    /**
        "0" at dp[0]0] represents empty string, the init process is using delete action, for example : abcd, i=0, "a", one delete to get to "";
        i=1, "ab", two deletes to get to "";....

              | a b c d
            0 | 1 2 3 4
     --------------------
         a  1 | 0 1 2 3
         e  2 | 1 1 2 3
         f  3 | 2 2 2 3

               |  null   m
     ----------------------
         null  |   0     1
               |
          k    |   1     1

        Convert 'm' to 'k', 3 ways :
        1. "null m" -> "null", delete 'm' (move right), then, "null" -> "null k",  ADD 'k'
        2. "null" -> "null k", add 'k' (move down), then "null k m" -> "null k", DELETE 'm'
        3. "null m" -> "null k", REPLACE 'm' with 'k' (move diagonally)

        Here we take rout 3, since it's the min cost (0)

        Math.min(Math.min(dp[i][j - 1], dp[i - 1][j]), dp[i - 1][j - 1]) + 1, therefore cost is 1.

        !!!
        If cost for operations are different, ADD - 3, DELETE - 5, REPLACE - 7, then the equation becomes:

        Math.min(Math.min(dp[i][j - 1] + 5, dp[i - 1][j] + 3), dp[i - 1][j - 1] + 7) + 1

     **/

    class Solution_DP_Practice {
        public int minDistance(String word1, String word2) {
            if (word1 == null || word2 == null) return -1;

            int m = word1.length();
            int n = word2.length();

            int[][] dp = new int[m + 1][n + 1];

            for (int i = 0; i < dp.length; i++) {
                /**
                 * !!!
                 * "= i", NOT " = m"
                 */
                dp[i][0] = i;
            }

            for (int j = 0; j < dp[0].length; j++) {
                /**
                 * !!!
                 * "= j", NOT " = n"
                 */
                dp[0][j] = j;
            }

            dp[0][0] = 0;

            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else {
                        /**
                         * !!!
                         * "1 + Math.min(...)"
                         *  加一!!!
                         */
                        dp[i][j] = 1 + Math.min(dp[i - 1][j], Math.min(dp[i][j - 1], dp[i - 1][j - 1]));
                    }
                }
            }

            return dp[m][n];

        }
    }

    /**
     * DP
     * Bottom up
     * 9 ms
     */
    class Solution1 {
        public  int minDistance(String word1, String word2) {
            int[][] dp = new int[word1.length() + 1][word2.length() + 1];
            int len1 = word1.length();
            int len2 = word2.length();

            /**
             * 注意， 根据dp的定义决定初始化循环的变量。
             * 此处我们定义dp为int[word1.length() + 1][word2.length() + 1]，
             * 所以dp[i][0]需要在以len1为i的终值的循环中。
             */
            //!! "<="
            for (int i = 0; i <= len1; i++) {
                dp[i][0] = i;
            }

            //!! "<="
            for (int i = 0; i <= len2; i++) {
                dp[0][i] = i;
            }

            //!! "<=" and starts at index "1"
            for (int i = 1; i <= len1; i++) {
                //!! "<=" and starts at index "1"
                for (int j = 1; j <= len2; j++) {
                    // compare char at "i - 1 and "j - 1"
                    if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else {
                        dp[i][j] = Math.min(Math.min(dp[i][j - 1], dp[i - 1][j]), dp[i - 1][j - 1]) + 1;
                    }
                }
            }

            return dp[len1][len2];
        }
    }

    /**
     * Recursion + Memoization
     * Top-down
     * 5 ms
     */
    class Solution2 {
        int[][] mem;
        public int minDistance(String word1, String word2) {
            if (word1 == null && word2 == null) return 0;
            if (word1 == null) return word2.length();
            if (word2 == null) return word1.length();

            int l1 = word1.length();
            int l2 = word2.length();

            mem = new int[l1 + 1][l2 + 1];
            for (int[] m : mem) {
                Arrays.fill(m, -1);
            }
            return helper(word1, word2, l1, l2);
        }

        private int helper(String word1, String word2, int l1, int l2) {
            if (l1 == 0) {
                return l2;
            }
            if (l2 == 0) {
                return l1;
            }

            if (mem[l1][l2] > 0) {
                return mem[l1][l2];
            }

            int res = 0;
            if (word1.charAt(l1 - 1) == word2.charAt(l2 - 1)) {
                res =  helper(word1, word2, l1 - 1, l2 - 1);
            } else {
                res = Math.min(Math.min(helper(word1, word2, l1 - 1, l2), helper(word1, word2, l1, l2 - 1)),
                        helper(word1, word2, l1 - 1, l2 - 1)) + 1;
            }

            mem[l1][l2] = res;
            return res;
        }
    }
}
