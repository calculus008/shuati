package leetcode;

import java.util.HashMap;
import java.util.Map;

public class LE_1088_Confusing_Number_II {
    /**
     * We can rotate digits by 180 degrees to form new digits. When 0, 1, 6, 8, 9 are rotated 180 degrees, they become
     * 0, 1, 9, 8, 6 respectively. When 2, 3, 4, 5 and 7 are rotated 180 degrees, they become invalid.
     *
     * A confusing number is a number that when rotated 180 degrees becomes a different number with each digit valid.
     * (Note that the rotated number can be greater than the original number.)
     *
     * Given a positive integer N, return the number of confusing numbers between 1 and N inclusive.
     *
     * Example 1:
     *
     * Input: 20
     * Output: 6
     * Explanation:
     * The confusing numbers are [6,9,10,16,18,19].
     * 6 converts to 9.
     * 9 converts to 6.
     * 10 converts to 01 which is just 1.
     * 16 converts to 91.
     * 18 converts to 81.
     * 19 converts to 61.
     *
     *
     * Example 2:
     *
     * Input: 100
     * Output: 19
     * Explanation:
     * The confusing numbers are [6,9,10,16,18,19,60,61,66,68,80,81,86,89,90,91,98,99,100].
     *
     *
     * Note:
     * 1 <= N <= 10^9
     *
     * Hard
     */

    /**
     * A brutal force solution will iterate from 1 to N, for each number, use solution from LE_1056_Confusing_Number to
     * check if it is a confusing number and count. Time complexity is O(N * (k ^ 2)).
     *
     * We can do better. Since we know, a confusing number will only consist with digits in {0, 1, 6, 8, 9}, so we will
     * generate numbers that only have those 5 digits, then check if the number is equal to its rotated number, and count.
     *
     * Time complexity : O(5 ^ k), k is digits of N in decimal presentation.
     * Space O(1)
     */
    class Solution {
        int[] nums = {0, 1, 6, 8, 9};
        Map<Integer, Integer> map;
        int limit = 0;
        int count = 0;

        public int confusingNumberII(int N) {
            limit = N;
            map = new HashMap<>();
            map.put(0, 0);
            map.put(1, 1);
            map.put(6, 9);
            map.put(8, 8);
            map.put(9, 6);

            dfs(0, 0, 1);

            return count;
        }

        /**
         * !!!
         * num and rotatedNum should be "long", not "int"
         */
        private void dfs(long num, long rotatedNum, int base) {
            if (num > limit) return;

            if (num != rotatedNum) count++;

            for (int d : nums) {
                if (num == 0 && d == 0) continue;

                /**
                 * Generate number and its rotated number in dfs:
                 * rotatedNum : map.get(d) * base + rotatedNum !!!
                 */
                dfs(num * 10 + d, map.get(d) * base + rotatedNum, base * 10);
            }
        }
    }
}
