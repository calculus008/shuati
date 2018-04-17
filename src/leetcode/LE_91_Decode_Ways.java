package leetcode;

/**
 * Created by yuank on 3/10/18.
 */
public class LE_91_Decode_Ways {
    /*
        A message containing letters from A-Z is being encoded to numbers using the following mapping:

        'A' -> 1
        'B' -> 2
        ...
        'Z' -> 26
        Given an encoded message containing digits, determine the total number of ways to decode it.

        For example,
        Given encoded message "12", it could be decoded as "AB" (1 2) or "L" (12).

        The number of ways decoding "12" is 2.
     */

    //!!! Very Very Important !!!

    /*
    s : "1231"

    idx  0 1 2 3
     s   1 2 3 1

    i   0 1 2 3 4
    dp  1 1 2 3 3

    i=2, first=2, second=12, dp[2] = dp[2] + dp[1] = 0 + 1 = 1
                             dp[2] = dp[2] + dp[0] = 1 + 1 = 2      1,2 -> AB, 12 -> L

    i=3, first=3, second=23, dp[3] = dp[3] + dp[2] = 0 + 2 = 2
                             dp[3] = dp[3] + dp[1] = 2 + 1 = 3      1,2,3 -> ABC, 12,3 -> LC, 1,23 -> AW

    i=4, first=2, second=12, dp[4] = dp[4] + dp[3] = 0 + 3 = 3      1,2,3,1 -> ABCA, 12,3,1 -> LCA, 1,23,1 -> AWA
                             "31" is invalid

    Here, i in for loop is the index for dp[], its relationship to index of s : index_s = i - 1
 */
    //Solution 1 : Time and Space : O(n)
    public static int numDecodings1(String s) {
        if (s == null || s.length() == 0) return 0;
        int len = s.length();
        int[] dp = new int[len + 1]; //how many ways for the first i chars
        dp[0] = 1;
        dp[1] = s.charAt(0) == '0' ? 0 : 1; //for array idx "0"

        for (int i = 2; i <= len; i++) {
            int first = Integer.valueOf(s.substring(i - 1, i));
            int second = Integer.valueOf(s.substring(i - 2, i));
            if (first >= 1 && first <= 9) {
                dp[i] += dp[i-  1];
            }

            if(second >= 10 && second <= 26) {
                dp[i] += dp[i - 2];
            }
        }

        return dp[len];
    }

    //Solution 2 : Time : O(n), Space : O(1)
    public static int numDecodings2(String s) {
        if (s == null || s.length() == 0) return 0;
        int len = s.length();
        int c1 = 1;
        int c2 = s.charAt(0) == '0' ? 0 : 1; //for array idx "0"

        for (int i = 2; i <= len; i++) {
            int c3 = 0;
            int first = Integer.valueOf(s.substring(i - 1, i));
            int second = Integer.valueOf(s.substring(i - 2, i));
            if (first >= 1 && first <= 9) {
                c3 +=  c2;
            }

            if(second >= 10 && second <= 26) {
                c3 += c1;
            }

            c1 = c2;
            c2 = c3;
        }

        return c2;
    }
}
