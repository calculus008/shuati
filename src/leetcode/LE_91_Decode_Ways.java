package leetcode;

/**
 * Created by yuank on 3/10/18.
 */
public class LE_91_Decode_Ways {
    /**
        A message containing letters from A-Z is being encoded to numbers using the following mapping:

        'A' -> 1
        'B' -> 2
        ...
        'Z' -> 26
        Given an encoded message containing digits, determine the total number of ways to decode it.

        For example,
        Given encoded message "12", it could be decoded as "AB" (1 2) or "L" (12).

        The number of ways decoding "12" is 2.

        Medium

        https://leetcode.com/problems/decode-ways
     */

    //!!! Very Very Important !!!

    /**
     * http://zxi.mytechroad.com/blog/dynamic-programming/leetcode-91-decode-ways/
     *
     * DP
     *
     * dp[i] : number of decoding ways of the first i chars
     *         dp[i] =
     *             dp[i - 1] : if ith char (s.charAt(i - 1)) is not '0'
     *               +
     *             dp[i - 2] : if number formed by ith char and (i - 1)the char is within [10, 26] (!!!NOT [1, 26])
     *
     *
     *
        s : "1231"

        col  0 1 2 3
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

        !!!
        这里，要注意的是，dp[i] = dp[i - 1] + dp[i - 2]的实际意思是：
           dp[i - 1] : 把当前的字符（ith char) 作为单独的一个字符解码，隐含的意思是，我们遵从的是前 i - 1 个字符解码的方式，
                       他们有多少种方式，我现在就有多少种方式。
           dp[i - 2] : 把当前的字符（ith char) 和前一个字符 ( (i - 1) th char ) 合并作为的一个字符解码，隐含的意思是，
                       我们遵从的是前 i - 2 个字符解码的方式，他们有多少中方式，我现在就有多少种方式。
        当然，这两种方式都是有条件的， 也就是在什么条件下，才能做这种解码 （ith char is not '0', value of ith and (i - 1) th
        char combined, its int value should be >= 10 and <= 26)

        Refer to the explanation in official solution:
        https://leetcode.com/articles/decode-ways/
  */

    class Solution_Practice {
        /**
         * Bottom up, time and space O(n)
         *
         * Use substring, slower but looks cleaner
         */
        public int numDecodings(String s) {
            if (s == null || s.length() == 0) return 0;

            int n = s.length();
            int[] dp = new int[n + 1];

            dp[0] = 1;
            dp[1] = s.charAt(0) == '0' ? 0 : 1;

            for (int i = 2; i <= n; i++) {
                int one = Integer.parseInt(s.substring(i - 1, i));
                int two = Integer.parseInt(s.substring(i - 2, i));

                if (one != 0) {
                    dp[i] += dp[i - 1]; //"+="
                }
                if (two >= 10 && two <= 26) {
                    dp[i] += dp[i - 2]; //"+="
                }
            }

            return dp[n];
        }

        /**
         * bottom up, Time and Space O(n)
         */
        public int numDecodings1(String s) {
            if (s == null || s.length() == 0) return 0;

            int n = s.length();
            int[] dp = new int[n + 1];
            dp[0] = 1;
            dp[1] = s.charAt(0) == '0' ? 0 : 1;

            for (int i = 2; i <= n; i++) {
                char c1 = s.charAt(i - 1);
                char c2 = s.charAt(i - 2);

                boolean v1 = (c1 == '0' ? false : true);
                int num = (c2 - '0') * 10 + c1 - '0';
                boolean v2 = (num >= 10 && num <= 26);

                if (!v1 && !v2) return 0;

                if (v1) dp[i] += dp[i - 1];
                if (v2) dp[i] += dp[i - 2];
            }

            return dp[n];
        }
        /**
         * bottom up, time O(n), space O(1)
         */
        public int numDecodings2(String s) {
            if (s == null || s.length() == 0) return 0;

            int n = s.length();

            int dp1 = 1;
            int dp2 = s.charAt(0) == '0' ? 0 : 1;

            for (int i = 2; i <= n; i++) {
                char c1 = s.charAt(i - 1);
                char c2 = s.charAt(i - 2);

                boolean v1 = (c1 == '0' ? false : true);
                int num = (c2 - '0') * 10 + c1 - '0';
                boolean v2 = (num >= 10 && num <= 26);

                if (!v1 && !v2) return 0;

                int dp = 0;
                if (v1) dp += dp2;
                if (v2) dp += dp1;

                dp1 = dp2;
                dp2 = dp;
            }

            return dp2;
        }
    }


    class Solution_DP_Bottom_Up {
        /**
         * DP
         * dp[i] : number of decoding ways of the first i chars
         *
         * Transition:
         * dp[i] = dp[i - 1] : if ith char (s.charAt(i - 1)) is not '0'
         *           +
         *         dp[i - 2] : if number formed by ith char and (i - 1)the char is within [10, 26] (!!!NOT [1, 26])
         *
         * Answer:
         * dp[n]
         *
         * 就是说， 前i个字符的总共的解码方式，是：
         * 将第i个数字（s.charAt(i - 1), 因为是zero based）单独映射为一个字母的解码方式的和，
         * 加上
         * 将第i个和第i-1个数字合在一起映射为一个字母 (如果这个两位的数字是在 10 ～ 26 的范围内）的解码方式的和。
         */
        public int numDecodings_JiuZhang(String s) {
            if (s == null || s.length() == 0) {
                return 0;
            }

            int n = s.length();
            int[] dp = new int[n + 1];
            dp[0] = 1;

            for (int i = 1; i <= n ; i++) {
                if (s.charAt(i - 1) != '0') {
                    dp[i] += dp[i - 1];
                }

                if (i >= 2) {
                    int num = (s.charAt(i - 2) - '0') * 10 + s.charAt(i - 1) - '0';
                    if (num >= 10 && num <= 26) {
                        dp[i] += dp[i - 2];
                    }
                }
            }

            return dp[n];
        }

        /**
         * Based on huahua's DP Solution, much faster
         * 1 ms
         *
         * i 1 2 3 4 5 6
         *   1 0 2 2 3 1
         *         ^
         *
         * say now i = 4, check subarray start from index (i - 1) -> '2',  v1 = true
         *                check subarray start from index (i - 2) -> '22', v2 = true
         *
         * v1 = true, meaning, '2' can be decoded, so the number of decode should add dp[i - 1] -> solution for '102'
         * v2 = true, meaning, '22' can be decoded, so the number of decode should add dp[i - 2] -> solution for '10'
         * Therefore, dp[4] = dp[3] + dp[2]
         *
         *
         * say now i = 6, check subarray start from index (i - 1) -> '1',  v1 = true
         *                check subarray start from index (i - 2) -> '31', v2 = false
         *
         * v1 = true, meaning, '1' can be decoded, so the number of decode should add dp[i - 1] -> solution for '10223'
         * v2 = false, meaning, '31' can NOT be decoded -> '1022' + '31' does not have a solution, it can not be decode this one.
         * Therefore, dp[6] = dp[5]
         *
         */
        public int numDecodings(String s) {
            if (null == s || s.length() == 0) {
                return 0;
            }

            int n = s.length();
            int[] dp = new int[n + 1];
            dp[0] = 1;
            dp[1] = s.charAt(0) == '0' ? 0 : 1;

            for (int i = 2; i <= n; i++) {
                boolean v1 = validOne(s.charAt(i - 1));
                boolean v2 = validTwo(s.charAt(i - 2), s.charAt(i - 1));

                /***
                 * !!!
                 * if there's sequence '00', then there's no way to decode it.
                 */
                if (!v1 && !v2) {
                    return 0;
                }

                if (v1) {
                    dp[i] += dp[i - 1];
                }

                if (v2) {
                    dp[i] += dp[i - 2];
                }
            }

            return dp[n];
        }

        private boolean validOne(char c) {
            return c >= '1' && c <= '9';
        }

        private boolean validTwo(char c1, char c2) {
            int num = (c1 - '0') * 10 + c2 - '0';
            return num >= 10 && num <= 26;
        }
    }

    //Solution 1 : Time and Space : O(n)
    public static int numDecodings1(String s) {
        if (s == null || s.length() == 0) return 0;
        int len = s.length();
        int[] dp = new int[len + 1]; //how many ways for the first i chars
        dp[0] = 1;
        dp[1] = s.charAt(0) == '0' ? 0 : 1; //for array col "0"

        for (int i = 2; i <= len; i++) {
            int first = Integer.valueOf(s.substring(i - 1, i));
            int second = Integer.valueOf(s.substring(i - 2, i));
            if (first >= 1 && first <= 9) {
                dp[i] += dp[i - 1];
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
        int c2 = s.charAt(0) == '0' ? 0 : 1; //for array col "0"

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
