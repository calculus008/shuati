package leetcode;

public class LE_995_Minimum_Number_Of_K_Consecutive_Bit_Flips {
    /**
     * In an array A containing only 0s and 1s, a K-bit flip consists of choosing
     * a (contiguous) subarray of length K and simultaneously changing every 0 in
     * the subarray to 1, and every 1 in the subarray to 0.
     *
     * Return the minimum number of K-bit flips required so that there is no 0 in
     * the array.  If it is not possible, return -1.
     *
     * Example 1:
     * Input: A = [0,1,0], K = 1
     * Output: 2
     * Explanation: Flip A[0], then flip A[2].
     *
     * Example 2:
     * Input: A = [1,1,0], K = 2
     * Output: -1
     * Explanation: No matter how we flip subarrays of size 2, we can't make the
     * array become [1,1,1].
     *
     * Example 3:
     * Input: A = [0,0,0,1,0,1,1,0], K = 3
     * Output: 3
     * Explanation:
     * Flip A[0],A[1],A[2]: A becomes [1,1,1,1,0,1,1,0]
     * Flip A[4],A[5],A[6]: A becomes [1,1,1,1,1,0,0,0]
     * Flip A[5],A[6],A[7]: A becomes [1,1,1,1,1,1,1,1]
     *
     * Note:
     * 1 <= A.length <= 30000
     * 1 <= K <= A.length
     *
     * Hard
     */

    /**
     * Greedy
     *
     * flipCount: 0
     *  0  1  2  3  4  5  6  7   K = 3
     * [0, 0, 0, 1, 0, 1, 1, 0]
     *  ^
     * flipCount: 1   res:1
     *     ^
     *        ^
     *           ^
     *          flipCount: 0
     *              ^
     *              flipCount: 1   res:2
     *                 ^
     *                 flipCount: 2    res: 3
     *                        ^
     *                        flipCount:1
     *
     */
    class Solution {
        public int minKBitFlips(int[] A, int K) {
            int n = A.length;
            boolean[] flipped = new boolean[n];
            int res = 0;
            int flipCount = 0;

            for (int i = 0; i < n; i++) {
                if (i - K >= 0) {
                    /**
                     * we step out of the last flipped interval:
                     * flipped[i - K] is the start of the last flipped interval.
                     */
                    if (flipped[i - K]) {
                        flipCount--;
                    }
                }

                /**
                 * After the last if check, we have the correct fliCount value for current interval
                 *
                 * flipCount % 2 == 0, A[i] = 0, original value is 0, we need to flip it to 1
                 * flipCount % 2 == 1, A[i] = 1, original value is 1, but we already flip it to 0, need to flip again to 1.
                 */
                if (flipCount % 2 == A[i]) {
                    if (i + K - 1 >= n) {
                        return -1;
                    }

                    flipCount++;

                    /**
                     * Set mark at the start of the flipped interval
                     */
                    flipped[i] = true;
                    res++;
                }
            }

            return res;
        }
    }
}
