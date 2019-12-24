package leetcode;

public class LE_633_Sum_Of_Square_Numbers {
    /**
     * Given a non-negative integer c, your task is to decide whether
     * there're two integers a and b such that a2 + b2 = c.
     *
     * Example 1:
     *
     * Input: 5
     * Output: True
     * Explanation: 1 * 1 + 2 * 2 = 5
     *
     *
     * Example 2:
     *
     * Input: 3
     * Output: False
     */

    /**
     * Tow pointers
     */
    public class Solution1 {
        public boolean judgeSquareSum(int c) {
            if (c < 0) {
                return false;
            }

            int left = 0, right = (int)Math.sqrt(c);

            while (left <= right) {
                int cur = left * left + right * right;
                if (cur < c) {
                    left++;
                } else if (cur > c) {
                    right--;
                } else {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Binary Search + Two Pointers
     *
     * Time : O(sqrt(c) * log(c))
     * Space : O(log(c))
     *
     */
    public class Solution {
        public boolean judgeSquareSum(int c) {
            for (long a = 0; a * a <= c; a++) {
                int b = c - (int)(a * a);
                if (binary_search(0, b, b)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * O(log(C))
         */
        public boolean binary_search(long s, long e, int n) {
            if (s > e) {
                return false;
            }

            long mid = s + (e - s) / 2;
            if (mid * mid == n) {
                return true;
            }

            if (mid * mid > n) {
                return binary_search(s, mid - 1, n);
            }

            return binary_search(mid + 1, e, n);
        }
    }
}
