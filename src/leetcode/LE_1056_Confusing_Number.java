package leetcode;

public class LE_1056_Confusing_Number {
    /**
     * Given a number N, return true if and only if it is a confusing number, which satisfies the following condition:
     *
     * We can rotate digits by 180 degrees to form new digits. When 0, 1, 6, 8, 9 are rotated 180 degrees, they become
     * 0, 1, 9, 8, 6 respectively. When 2, 3, 4, 5 and 7 are rotated 180 degrees, they become invalid. A confusing number
     * is a number that when rotated 180 degrees becomes a different number with each digit valid.
     *
     * Example 1:
     *
     * Input: 6
     * Output: true
     * Explanation:
     * We get 9 after rotating 6, 9 is a valid number and 9!=6.
     *
     *
     * Example 2:
     *
     * Input: 89
     * Output: true
     * Explanation:
     * We get 68 after rotating 89, 86 is a valid number and 86!=89.
     *
     *
     * Example 3:
     *
     * Input: 11
     * Output: false
     * Explanation:
     * We get 11 after rotating 11, 11 is a valid number but the value remains the same, thus 11 is not a confusing number.
     *
     *
     * Example 4:
     *
     * Input: 25
     * Output: false
     * Explanation:
     * We get an invalid number after rotating 25.
     *
     * Note:
     * 0 <= N <= 10^9
     * After the rotation we can ignore leading zeros, for example if after rotation we have 0008 then this number is considered as just 8.
     *
     *  Easy
     */

    class Solution1 {
        public boolean confusingNumber(int N) {
            if (N == 0 || N == 1) return false;

            int reverse = 0;
            while (N > 0) {
                int d = N % 10;
                if ((d >= 2 && d <=5) || d == 7) return false;

                if (d == 6) {
                    d = 9;
                } else if (d == 9) {
                    d = 6;
                }

                reverse = reverse * 10 + d;
                N /= 10;
            }

            return N != reverse;
        }
    }

    /**
     * Compare with Solution1, use an array (or a hashmap) to map the reversed digit result. For invalid digit,
     * set mapped value to -1. So we have a lookup table to get reversed digit, instead of doing many 'if'.
     */
    class Solution2 {
        public boolean confusingNumber(int N) {
            int[] mapping = {0, 1, -1, -1, -1, -1, 9, -1, 8, 6};

            int dummyN = N;
            int reversedN = 0;

            while (dummyN > 0) {
                int currDigit = dummyN % 10;     // get rightmost digit
                dummyN = dummyN / 10;            // remove rightmost (least significant) digit

                if (mapping[currDigit] != -1) {
                    reversedN = reversedN * 10 + mapping[currDigit];
                } else {
                    return false;
                }
            }

            return reversedN != N;
        }
    }
}
