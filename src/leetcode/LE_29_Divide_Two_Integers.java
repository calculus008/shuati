package leetcode;

/**
 * Created by yuank on 7/2/18.
 */
public class LE_29_Divide_Two_Integers {
    /**
         Given two integers dividend and divisor, divide two integers without using multiplication, division and mod operator.

         Return the quotient after dividing dividend by divisor.

         The integer division should truncate toward zero.

         Example 1:

         Input: dividend = 10, divisor = 3
         Output: 3
         Example 2:

         Input: dividend = 7, divisor = -3
         Output: -2
         Note:

         Both dividend and divisor will be 32-bit signed integers.
         The divisor will never be 0.
         Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range: [−231,  231 − 1].
         For the purpose of this problem, assume that your function returns 231 − 1 when the division result overflows.

        Very Important
     */


    /**
     * Time and Space : O(logn) !!!
     */

    class Solution1 {
        public int divide(int dividend, int divisor) {
            int sign = 1;
            if ((dividend > 0 && divisor < 0) || (dividend < 0 && divisor > 0)) {
                sign = -1;
            }

            long divd = Math.abs((long) dividend);
            long divs = Math.abs((long) divisor);
            long lres = longDivd(divd, divs);

            if (lres > Integer.MAX_VALUE){
                if (sign > 0) {
                    return Integer.MAX_VALUE;
                } else {
                    return Integer.MIN_VALUE;
                }
            }

            if (sign > 0) return (int) lres;
            return (int) (-lres);
        }

        private long longDivd(long divd, long divs){
            // Recursion exit condition
            if (divd < divs) return 0;
            long sum = divs;
            long multi = 1;

            //  Find the largest multiple so that (divisor * multiple <= dividend),
            //  whereas we are moving with stride 1, 2, 4, 8, 16...2^n for performance reason.
            //  Think this as a binary search.
            while (sum + sum <= divd){
                sum += sum;
                multi += multi;
            }

            //Look for additional value for the multiple from the reminder (dividend - sum) recursively.
            return multi + longDivd(divd - sum, divs);
        }
    }

    /**
     * 8 / 2
     *
     * a = 8, b = 2
     *
     * sum = 2,  multiple = 1
     *
     * sum = 4,  multiple = 2
     *
     * return 2 + 2
     * ------------
     *
     * a = 8 - 4 = 4, b = 2
     *
     * sum = 2
     * multiple = 1
     *
     * sum = 4
     * multiple = 2
     *
     * return 2 + 0
     * ------------
     *
     * a = 4 - 4 = 0, b = 2
     *
     * return 0
     *
     */


    //Solution 2 : use bit operation
    public int divide2(int dividend, int divisor) {
        if (divisor == 0 || dividend == Integer.MIN_VALUE && divisor == -1) return Integer.MAX_VALUE;

        int res = 0;
        int sign = (dividend < 0) ^ (divisor < 0) ? -1 : 1;
        long dvd = Math.abs((long) dividend);
        long dvs = Math.abs((long) divisor);

        while (dvs <= dvd) {
            long temp = dvs, mul = 1;
            while (dvd >= temp << 1) {
                temp <<= 1;
                mul <<= 1;
            }
            dvd -= temp;
            res += mul;
        }
        return sign == 1 ? res : -res;
    }
}
