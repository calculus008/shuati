package leetcode;

/**
 * Created by yuank on 3/9/18.
 */
public class LE_97_Interleaving_String {
    /*
        Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.

        For example,
        Given:
        s1 = "aabcc",
        s2 = "dbbca",

        When s3 = "aadbbcbcac", return true.
        When s3 = "aadbbbaccc", return false.
     */

    /*
        DP, Time and Space : O(m * n)
        dp[i][j] : if first i elements in s2 and first j elements in s1 can interleave to form the first i + j elemets in s3.

        Example:  1 for TURE, 0 for FALSE
         s2
      s1    '' a a b c c
        ''  1  1 1 0 0 0
         d  0  0 1 1 0 0
         b  0  0 1 1 1 0
         b  0  0 1 0 1 1
         c  0  0 1 1 1 0
         a  0  0 0 0 1 1

       s3  a a d b b c b c a c
     */
    public static boolean isInterleave(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) return false;

        boolean[][] dp = new boolean[s2.length() + 1][s1.length() + 1];
        dp[0][0] = true;

        for (int i = 1; i < dp.length; i++) {
            dp[i][0] = dp[i - 1][0] && s2.charAt(i - 1) == s3.charAt(i - 1);
        }
        for (int j = 1; j < dp[0].length; j++) {
            dp[0][j] = dp[0][j - 1] && s1.charAt(j - 1) == s3.charAt(j - 1);
        }

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                //dp数组中的下标数值k是指s1或s2中的头k个元素(one based)。而charAt()中用的数值是字符串中char的下标(zero based)。
                //dp[i][j-1] is ture : s1头(j-1)个元素和s2头i个元素可以组成s3的头(i+j-1)个元素。那么，现在看是否s1的第j个元素(下标为(j-1))
                //等于s3的第(i+j)个元素(下标为(i+j-1))。如果是，则s1头j个元素和s2头i个元素可以组成s3的头(i+j)个元素 => dp[i][j] = TRUE.
                dp[i][j] = dp[i][j - 1] && s1.charAt(j - 1) == s3.charAt(i + j - 1)
                        || dp[i - 1][j] && s2.charAt(i - 1) == s3.charAt(i + j - 1);
            }
        }

        return dp[s2.length()][s1.length()];
    }
}
