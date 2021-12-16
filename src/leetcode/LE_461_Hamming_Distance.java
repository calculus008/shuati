package leetcode;

public class LE_461_Hamming_Distance {
    /**
     * The Hamming distance between two integers is the number of positions at which the corresponding bits are different.
     *
     * Given two integers x and y, return the Hamming distance between them.
     *
     * Example 1:
     * Input: x = 1, y = 4
     * Output: 2
     * Explanation:
     * 1   (0 0 0 1)
     * 4   (0 1 0 0)
     *        ↑   ↑
     * The above arrows point to positions where the corresponding bits are different.
     *
     * Example 2:
     * Input: x = 3, y = 1
     * Output: 1
     *
     * Constraints:
     * 0 <= x, y <= 231 - 1
     *
     * Easy
     *
     * https://leetcode.com/problems/hamming-distance/
     */

    /**
     * Time and Space : O(1)
     */
    class Solution1 {
        public int hammingDistance(int x, int y) {
            // return Integer.bitCount(x ^ y);
            int res = 0;
            int xor = x ^ y;
            while (xor != 0) {
                if ((xor & 1) == 1) {
                    res++;
                }
                xor >>= 1;
            }
            return res;
        }
    }

    /**
     * Brian Kernighan's Algorithm
     */
    class Solution2 {
        public int hammingDistance(int x, int y) {
            // return Integer.bitCount(x ^ y);
            int res = 0;
            int xor = x ^ y;
            while (xor != 0) {
                res++;

                /**
                 * remove the rightmost bit of '1'
                 */
                xor = xor & (xor - 1);
            }
            return res;
        }
    }
}
