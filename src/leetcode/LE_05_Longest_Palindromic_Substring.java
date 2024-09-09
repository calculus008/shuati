package leetcode;

/**
 * Created by yuank on 3/17/18.
 */
public class LE_05_Longest_Palindromic_Substring {
    /**
        Given a string s, find the longest palindromic substring in s.
        You may assume that the maximum length of s is 1000.

        Example:

        Input: "babad"

        Output: "bab"

        Note: "aba" is also a valid answer.


        Example:

        Input: "cbbd

        Output: "bb"

        Easy

        https://leetcode.com/problems/longest-palindromic-substring
     */

    /**
     * Time : O(n ^ 2), Space : O(n ^ 2)
     */

    //Solution 1 : Expand left and right, 枚举中心轴
     String res = "";
     public String longestPalindrome1(String s) {
         if (s == null || s.length() == 0) return s;

         for (int i = 0; i < s.length(); i++) {
             helper(s, i, i);
             helper(s, i, i + 1);
          }

         return res;
     }

    /**
     * !!!
     * Left and Right instead of Center:
     * expand function takes left and right boundary indices, NOT the index of expanding center
     */
     private void helper(String s, int left, int right) {
         /**
          * !!!
          * Must check boundary in while loop to prevent boundary overflow.
          * This is why we can iterate from index 0 to n - 1 in the function
          * longestPalindrome1(). Since we do boundary check here, we don't
          * need to worry about boundary overflow when calling help().
          */
         while (left >= 0 && right < s.length()
                 && s.charAt(left) == s.charAt(right)) {
             left--;
             right++;
         }

//         String temp = s.substring(left + 1, right);
//         if (temp.length() > res.length()) {
//             res = temp;
//         }

         left++;
         right--;

         if (right - left + 1 > res.length()) {
             res = s.substring(left, right + 1);
         }
     }

    /**Solution 2 : DP
     * dp[i][j] : if substring between index i ~ j is palindrome
     * Transition :  dp[i][j] = true if char at i and j are equal AND :
     *                 1.j - i == 0, 即i和j相等，是同一个char, 例如， “b"
     *                 2.j - i == 1, 例如， “bb"
     *                 3.j - i == 2, 例如， “bab"
     *                 4.i+1 - j-1 是palindrome (dp[i + 1][j - 1] = true)
     *
     **/
    public String longestPalindrome2(String s) {
        if (s == null || s.length() == 0) return s;

        int n = s.length();
        /**
         * !!!dp is boolean
         */
        boolean dp[][] = new boolean[n][n];
        // dp[0][0] = true;
        String res = "";

        // for (int j = 1; j < n; j++) {
        //     for (int i = 1; i <= j; i++) {
        //         int left = i - 1;
        //         int right = j - 1;
        //         System.out.println("left=" + left + ",right="+right);
        //         dp[i][j] = s.charAt(right) == s.charAt(left) && (right - left <= 2 || dp[left - 1][right + 1]);
        //         if (dp[i][j] && right - left + 1 > res.length()) {
        //             res = s.substring(left, right + 1);
        //         }
        //     }
        // }

        for (int j = 0; j < n; j++) {
            for (int i = 0; i <= j; i++) {
                /**
                   !!! "(j - i <= 2 || dp[i + 1][j - 1])"

                   就是说， 当在i和j的char相同时，有两种情况可以认定i ~ j是palindrome:

                   1.j - i == 0, 即i和j相等，是同一个char, 例如， “b"
                     j - i == 1, 例如， “bb"
                     j - i == 2, 例如， “bab"

                   2.i+1 - j-1 是palindrome


                   !!! 必须把“j - i <= 2"写在“dp[i + 1][j - 1]”的前面，只有这样才能避免下标越界。
                 */
                dp[i][j] = s.charAt(i) == s.charAt(j) && (j - i <= 2 || dp[i + 1][j - 1]);

                /**
                 * !!!
                 * Don't forget "dp[i][j] == true" should be the first condition here
                 */
                if (dp[i][j] && j - i + 1 > res.length()) {
                    res = s.substring(i, j + 1);
                }
            }
        }

        return res;
    }
}
