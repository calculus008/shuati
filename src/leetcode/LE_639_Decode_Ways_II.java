package leetcode;

/**
 * Created by yuank on 11/21/18.
 */
public class LE_639_Decode_Ways_II {
    /**
         A message containing letters from A-Z is being encoded to numbers using the following mapping way:

         'A' -> 1
         'B' -> 2
         ...
         'Z' -> 26
         Beyond that, now the encoded string can also contain the character '*',
         which can be treated as one of the numbers from 1 to 9.

         Given the encoded message containing digits and the character '*',
         return the total number of ways to decode it.

         Also, since the answer may be very large, you should return the output mod 109 + 7.

         Example 1:
         Input: "*"
         Output: 9
         Explanation: The encoded message can be decoded to the string: "A", "B", "C", "D", "E", "F", "G", "H", "I".

         Example 2:
         Input: "1*"
         Output: 9 + 9 = 18

         Note:
         The length of the input string will fit in range [1, 105].
         The input string will only contain the character '*' and digits '0' - '9'.

         Hard

         A more general form of LE_91_Decode_Ways
     */

    /**
         http://zxi.mytechroad.com/blog/dynamic-programming/leetcode-639-decode-ways-ii/

         DP
         ways() :
         '*' : 9
         '0' : 0
         otherwise : 1

         '**' : 9 (11 ~ 19) + 6 (21 ~ 26) = 15
         '*A' : if A in (0 ~ 6), 2 (for example, 11 or 21)
         otherwise 1, for example 7, then only way to decode is '17' (* is '1')
         'A*' : if A = 1, 9 (11 ~ 19)
         if A = 2, 6 (21 ~ 26)
         oterwise, 0

         DP :
         dp[i] : total number of ways of decoding for the first i chars.

         Init :
         dp[0] = 1 (empty)
         dp[1] = C(s[0])

         Transfer :
         dp[i] = C(s[i - 1]) * dp[i - 1] + C(s[i - 1], s[i - 2]) * dp[i - 2]

         Answer :
         dp[n]

         Time  : O(n)
         Space : O(n)
     **/
    public int numDecodings(String s) {
        if (null == s || s.length() == 0) {
            return 0;
        }

        int mod = 1000000007;
        int n = s.length();

        long[] dp = new long[n + 1];//!!! use long to prevent overflow
        dp[0] = 1; //empty string
        /**
         * !!!
         * init dp[1], 调用decodeOne()。
         */
        dp[1] = decodeOne(s.charAt(0));

        for (int i = 2; i <= n; i++) {
            /**
             * !!!
             * 注意，dp[]的size 是 n + 1 (first i elements), 所以i映射到原数组中，
             * 下标是 i - 1.
             */
            dp[i] = decodeOne(s.charAt((i - 1))) * dp[i - 1] + decodeTwo(s.charAt((i - 1) - 1), s.charAt(i - 1)) * dp[i - 2];
            dp[i] %= mod;
        }

        return (int) dp[n];
    }

    /**
     * Space Optimized using rolling arrray.
     * Only change is changeing dp[] def from size[n + 1] to [3], then do ' % 3' whenever dp index is referenced.
     *
     * Space : O(1)
     */
    public int numDecodings1(String s) {
        if (null == s || s.length() == 0) {
            return 0;
        }

        int mod = 1000000007;
        int n = s.length();

        long[] dp = new long[3];
        dp[0] = 1; //empty string
        dp[1] = decodeOne(s.charAt(0));

        for (int i = 2; i <= n; i++) {
            dp[i % 3] = decodeOne(s.charAt((i - 1))) * dp[(i - 1) % 3] + decodeTwo(s.charAt((i - 1) - 1), s.charAt(i - 1)) * dp[(i - 2) % 3];
            dp[i % 3] %= mod;
        }

        return (int)dp[n % 3];
    }

    private int decodeOne(char c) {
        if (c == '*') {
            return 9;
        } else if (c == '0') {
            return 0;
        } else {
            return 1;
        }
    }

    private int decodeTwo(char c1, char c2) {
        if (c1 == '*' && c2 == '*') {
            return 15;
        } else if (c1 == '*') {
            return c2 >= '0' && c2 <= '6' ? 2 : 1;
        } else if (c2 == '*') {
            switch (c1) {
                case '1':
                    return 9;
                case '2':
                    return 6;
                default:
                    return 0;
            }
        } else {
            int num = (c1 - '0') * 10 + (c2 - '0');
            return num <= 26 && num >= 10 ? 1 : 0;
        }
    }
}
