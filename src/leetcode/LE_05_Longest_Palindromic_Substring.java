package leetcode;

/**
 * Created by yuank on 3/17/18.
 */
public class LE_05_Longest_Palindromic_Substring {
    /*
        Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.

        Example:

        Input: "babad"

        Output: "bab"

        Note: "aba" is also a valid answer.


        Example:

        Input: "cbbd"

        Output: "bb"
     */

    //Time : O(n ^ 2), Space : O(n ^ 2)
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

     private void helper(String s, int left, int right) {
         while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
             left--;
             right++;
         }

         String temp = s.substring(left + 1, right);
         if (temp.length() > res.length()) {
             res = temp;
         }
     }

    //Solution 2 : DP
    public String longestPalindrome2(String s) {
        if (s == null || s.length() == 0) return s;

        int n = s.length();
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
                // System.out.println("i=" + i + ",j="+j);
                dp[i][j] = s.charAt(i) == s.charAt(j) && (j - i <= 2 || dp[i + 1][j - 1]);
                if (dp[i][j] && j - i + 1 > res.length()) {
                    res = s.substring(i, j + 1);
                }
            }
        }

        return res;
    }
}
