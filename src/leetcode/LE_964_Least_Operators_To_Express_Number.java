package leetcode;

public class LE_964_Least_Operators_To_Express_Number {
    /**
     * Given a single positive integer x, we will write an expression of the form
     * x (op1) x (op2) x (op3) x ... where each operator op1, op2, etc. is either
     * addition, subtraction, multiplication, or division (+, -, *, or /).
     * For example, with x = 3, we might write 3 * 3 / 3 + 3 - 3 which is a value of 3.
     *
     * When writing such an expression, we adhere to the following conventions:
     *
     * The division operator (/) returns rational numbers.
     * There are no parentheses placed anywhere.
     * We use the usual order of operations: multiplication and division happens
     * before addition and subtraction.
     *
     * It's not allowed to use the unary negation operator (-).  For example, "x - x" is
     * a valid expression as it only uses subtraction, but "-x + x" is not because it
     * uses negation.
     *
     * We would like to write an expression with the least number of operators such that
     * the expression equals the given target.  Return the least number of operators used.
     *
     *
     *
     * Example 1:
     *
     * Input: x = 3, target = 19
     * Output: 5
     * Explanation: 3 * 3 + 3 * 3 + 3 / 3.  The expression contains 5 operations.
     * Example 2:
     *
     * Input: x = 5, target = 501
     * Output: 8
     * Explanation: 5 * 5 * 5 * 5 - 5 * 5 * 5 + 5 / 5.  The expression contains 8 operations.
     * Example 3:
     *
     * Input: x = 100, target = 100000000
     * Output: 3
     * Explanation: 100 * 100 * 100 * 100.  The expression contains 3 operations.
     *
     *
     * Note:
     *
     * 2 <= x <= 100
     * 1 <= target <= 2 * 10^8
     */

    public int leastOpsExpressTarget(int x, int y) {
        /**
         * pos the number of operations needed to get y % (x ^ (k+1))
         * neg the number of operations needed to get x ^ (k + 1) - y % (x ^ (k + 1))
         */
        int pos = 0, neg = 0, k = 0, pos2, neg2, cur;

        while (y > 0) {
            cur = y % x;
            y /= x;
            if (k > 0) {
                pos2 = Math.min(cur * k + pos, (cur + 1) * k + neg);
                neg2 = Math.min((x - cur) * k + pos, (x - cur - 1) * k + neg);
                pos = pos2;
                neg = neg2;
            } else {
                pos = cur * 2;
                neg = (x - cur) * 2;
            }
            k++;
        }
        return Math.min(pos, k + neg) - 1;
    }
}
