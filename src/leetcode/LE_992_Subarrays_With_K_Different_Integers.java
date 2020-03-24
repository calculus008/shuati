package leetcode;

import java.util.HashMap;
import java.util.Map;

public class LE_992_Subarrays_With_K_Different_Integers {
    /**
     * Given an array A of positive integers, call a (contiguous, not necessarily distinct)
     * subarray of A good if the number of different integers in that subarray is exactly K.
     *
     * (For example, [1,2,3,1,2] has 3 different integers: 1, 2, and 3.)
     *
     * Return the number of good subarrays of A.
     *
     *
     *
     * Example 1:
     * Input: A = [1,2,1,2,3], K = 2
     * Output: 7
     * Explanation: Subarrays formed with exactly 2 different integers:
     * [1,2], [2,1], [1,2], [2,3], [1,2,1], [2,1,2], [1,2,1,2].
     *
     *
     * Example 2:
     * Input: A = [1,2,1,3,4], K = 3
     * Output: 3
     * Explanation: Subarrays formed with exactly 3 different integers: [1,2,1,3], [2,1,3], [1,3,4].
     *
     * Note:
     * 1 <= A.length <= 20000
     * 1 <= A[i] <= A.length
     * 1 <= K <= A.length
     *
     * Hard
     */

    /**
     * Sliding Window
     *
     * https://leetcode.com/problems/subarrays-with-k-different-integers/discuss/235235/C%2B%2BJava-with-picture-prefixed-sliding-window
     *
     * O(n)
     *
     * The Main idea of this approach is to keep track of the number of subarrays with the same number of distinct integers.
     * (we store it in our variable prefix) These subarrays are POTENTIAL candidates of having K distinct integers, they are
     * not actual candidates yet. (not until we have encountered K distinct integers) We only add this prefix to our res
     * variable (which represents the no. of subarrays with K distinct integers) when we have actually encountered K distinct
     * integers.
     *
     * The prefix contains only duplicates of digits inside the window,
     * so we will move the left side of the window when finding a duplicate on the window's left side.
     * [1,2,1,2,3] K=3
     *  --- =====
     *  prefix=2 window=3
     *      -----
     *    -------
     *  --------- result = 1(for the window) + 2 (for prefix size) = 3
     *
     *
     */
    class Solution1 {
        public int subarraysWithKDistinct(int[] A, int K) {
            if (K <= 0) return 0;

            int res = 0;
            int prefix = 0;
            int n = A.length;

            Map<Integer, Integer> map = new HashMap<>();

            for (int i = 0, j = 0; i < n; i++) {
                map.put(A[i], map.getOrDefault(A[i], 0) + 1);

                /**
                 * Since the while loop below makes sure that the first element
                 * in sliding window has no duplicates in current sliding window,
                 * once we have K + 1 unique number in the sliding window, we
                 * simply move the left side of the sliding window to right by 1.
                 * Then the unique number in sliding window is back to K.
                 */
                if (map.size() > K) {
                    map.put(A[j], map.get(A[j]) - 1);

                    /**
                     * since left most one is always unique, no need to check
                     * if we should remove it from hashmap
                     */
//                    if (map.get(A[j]) == 0) {
                    map.remove(A[j]);
//                    }

                    /**
                     * !!!
                     * once we move left boundary of sliding window here due to having new
                     * unique number on the right side, since the left most one has no duplicate,
                     * the substring pattern is "broken" here, so we need to reset prefix to 0.
                     *
                     * For example:
                     * Once i moves to value "4", we move j (at "1") to "2". We are sure current
                     * sliding window has no "1" in it. So "2,3,4" can't form substring with previous
                     * number that meets requirement, need to reset prefix to 0.
                     *
                     * K = 3
                     *             j        i
                     * 1, 2, 1, 2, 1, 2, 3, 4
                     *             |________|
                     */
                    prefix = 0;
                    j++;
                }

                while (map.getOrDefault(A[j], 0) > 1) {
                    prefix++;
                    map.put(A[j], map.get(A[j]) - 1);
                    j++;
                }

                if (map.size() == K) {
                    res += prefix + 1;
                }
            }

            return res;
        }
    }
}
