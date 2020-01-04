package src.leetcode;

import java.util.ArrayList;
import java.util.List;

public class LE_679_24_Game {
    /**
     * You have 4 cards each containing a number from 1 to 9. You need to judge whether
     * they could operated through *, /, +, -, (, ) to get the value of 24.
     *
     * Example 1:
     * Input: [4, 1, 8, 7]
     * Output: True
     * Explanation: (8-4) * (7-1) = 24
     * Example 2:
     * Input: [1, 2, 1, 2]
     * Output: False
     *
     * Note:
     * The division operator / represents real division, not integer division.
     * For example, 4 / (1 - 2/3) = 12.
     *
     * Every operation done is between two numbers. In particular, we cannot use - as
     * a unary operator. For example, with [1, 1, 1, 1] as input,
     * the expression -1 - 1 - 1 - 1 is not allowed.
     *
     * You cannot concatenate numbers together. For example, if the input is [1, 2, 1, 2],
     * we cannot write this as 12 + 12.
     *
     * Hard
     */

    /**
     * https://leetcode.com/problems/24-game/discuss/113972/Very-Easy-JAVA-DFS
     *
     * Time : O(C(2,4) * 6 * C(2,3) * 6 * 6) = O(1)
     *
     */
    class Solution {
        public boolean judgePoint24(int[] nums) {
            List<Double> list = new ArrayList<>();
            for (int num : nums) {
                list.add((double)num);
            }

            return helper(list);
        }

        private boolean helper(List<Double> list) {
            /**
             * To the end of the recursion, there's only one value left
             * in list, this means the calculation is finished, we always
             * need two numbers to calculate
             */
            if (list.size() == 1) {
                /**
                 * !!!
                 * Must use "Math.abs()" to check!!!
                 */
                if (Math.abs(list.get(0) - 24.0) < 0.0001) {
                    return true;
                }
                return false;
            }

            /**
             * Get every possible pair of numbers in list, calculate.
             * Because we get each unique pair of numbers in list, this
             * actually simulates the use of '(' and ')'
             */
            for (int i = 0; i < list.size(); i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    /**
                     * calculate
                     */
                    List<Double> result = getResult(list.get(i), list.get(j));

                    /**
                     * For each result in current calculation, get it into a
                     * new list, put all other numbers in the list, this list
                     * goes into the next round calculation.
                     */
                    for (double d : result) {
                        List<Double> next = new ArrayList<>();
                        next.add(d);

                        for (int k = 0; k < list.size(); k++) {
                            if (k == i || k == j) continue;
                            next.add(list.get(k));
                        }

                        if (helper(next)) {
                            return true;
                        }
                    }
                }
            }

            return false;
        }

        /**
         * Get all possible results between 2 numbers
         */
        private List<Double> getResult(double a, double b) {
            List<Double> res = new ArrayList<>();
            res.add(a + b);
            res.add(a - b);
            res.add(b - a);
            res.add(a * b);
            res.add(a / b);
            res.add(b / a);

            return res;
        }
    }
}
