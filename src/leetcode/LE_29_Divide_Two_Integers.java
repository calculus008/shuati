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

    public int divide(int dividend, int divisor) {
        boolean isNegative = (dividend > 0 && divisor < 0) || (dividend < 0 && divisor > 0);

        long a = Math.abs((long)dividend);
        long b = Math.abs((long)divisor);

        if (a == 0 || a < b) {
            return 0;
        }

        long lres = divide(a, b);
        if (lres > Integer.MAX_VALUE) {
            return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        } else {
            int sign = isNegative ? -1 : 1;
            return (int)(lres * sign);
        }
    }

    public long divide(long a, long b) {
        if (a < b) return 0;
        long sum = b;
        long multiple = 1;

        while ((sum + sum) <= a) {
            sum += sum;
            multiple += multiple;
        }

        return multiple + divide(a - sum, b);
    }
}
