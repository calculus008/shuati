package leetcode;

public class LE_1680_Concatenation_Of_Consecutive_Binary_Numbers {
    /**
     * Given an integer n, return the decimal value of the binary string formed by concatenating the binary
     * representations of 1 to n in order, modulo 10 ^ 9 + 7.
     *
     * Example 1:
     * Input: n = 1
     * Output: 1
     * Explanation: "1" in binary corresponds to the decimal value 1.
     *
     * Example 2:
     * Input: n = 3
     * Output: 27
     * Explanation: In binary, 1, 2, and 3 corresponds to "1", "10", and "11".
     * After concatenating them, we have "11011", which corresponds to the decimal value 27.
     *
     * Example 3:
     * Input: n = 12
     * Output: 505379714
     * Explanation: The concatenation results in "1101110010111011110001001101010111100".
     * The decimal value of that is 118505380540.
     * After modulo 109 + 7, the result is 505379714.
     *
     * Constraints:
     * 1 <= n <= 10^5
     *
     * Medium
     */

    /**
     * Complexity Analysis
     * Time Complexity: O(nlog(n)). We iterate n numbers, and for each number we spend O(log(n)) to transform it into
     *                  the binary form and add it to the final result.
     * Space Complexity: O(n)
     * */
    class Solution1 {
        public int concatenatedBinary(int n) {
            int res = 0;
            int MOD = 1000000007;

            for (int i = 1; i <= n; i++) {
                String binary = Integer.toBinaryString(i);
                for (int j = 0; j < binary.length(); j++) {
                    /**
                     * can think it like integer, each time multiple by 10
                     */
                    res = (res * 2 + binary.charAt(j) - '0') % MOD;
                }
            }

            return res;
        }
    }

    class Solution2 {
        public int concatenatedBinary(int n) {
            long res = 0;
            int MOD = 1000000007;
            int len = 0;

            for (int i = 1; i <= n; i++) {
                /**
                 * check whether a number is the power of 2 in O(1)
                 * We only need to increase the length when we meet a power of 2.
                 */
                if ((i & (i - 1)) == 0) len++;

                res = ((res << len) | i) % MOD;
            }

            return (int)res;
        }
    }
}
