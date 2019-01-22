package leetcode;

/**
 * Created by yuank on 10/28/18.
 */
public class LE_926_Flip_String_To_Monotone_Increasing {
    /**
         A string of '0's and '1's is monotone increasing if it consists of some number of '0's (possibly 0), followed by some number of '1's (also possibly 0.)

         We are given a string S of '0's and '1's, and we may flip any '0' to a '1' or a '1' to a '0'.

         Return the minimum number of flips to make S monotone increasing.

         Example 1:

         Input: "00110"
         Output: 1
         Explanation: We flip the last digit to get 00111.

         Example 2:

         Input: "010110"
         Output: 2
         Explanation: We flip to get 011111, or alternatively 000111.

         Example 3:

         Input: "00011000"
         Output: 2
         Explanation: We flip to get 00000000.


         Note:

         1 <= S.length <= 20000
         S only consists of '0' and '1' characters.

         Medium
     */

    /**
     * Solution 1
     * DP
     * Time  : O(n)
     * Space : O(n)
     *
     * Make n + 1 break points
     * l[i] : 在当前分割处从右往左看，有多少'1'需要flip为'0'。
     * r[i] : 在当前分割处从左往右看，有多少'0'需要flip为'1'。
     *
     * Example :
     *
     * col 0 1 2 3 4
     *     0 0 1 1 0
     *
     * '|' 代表分割处。 <== | ==>
     *
     *        | 0 | 0 | 1 | 1 | 0 |
     * col    0   1   2   3   4   5
     *      <== ------------------->
     * l[i]   0   0   0   1   2   2
     *
     *      <-------------------- ==>
     * r[i]   3   2   1   1   1   0
     *
     * sum    3   2   1   2   3   2
     *
     * min is 1
     *
     */
    public int minFlipsMonoIncr1(String S) {
        if (S == null || S.length() == 0) return 0;

        int n = S.length();
        char[] chars = S.toCharArray();

        int[] l = new int[n + 1];
        l[0] = 0;
        for (int i = 0; i < n; i++) {
            l[i + 1] = l[i] + chars[i] - '0';
        }

        int[] r = new int[n + 1];
        r[n - 1] = 0;
        for (int i = n - 1; i >= 0; i--) {
            r[i] = r[i + 1] + '1' - chars[i];
        }

        int res = Integer.MAX_VALUE;

        for (int i = 0; i <= n; i++) {
            res = Math.min(res, l[i] + r[i]);
        }

        return res;
    }
}
