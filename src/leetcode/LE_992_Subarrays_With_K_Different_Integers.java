package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
     * Example 1:
     * Input: A = [1,2,1,2,3], K = 2
     * Output: 7
     * Explanation: Subarrays formed with exactly 2 different integers:
     * [1,2], [2,1], [1,2], [2,3], [1,2,1], [2,1,2], [1,2,1,2].
     *
     * Example 2:
     * Input: A = [1,2,1,3,4], K = 3
     * Output: 3
     * Explanation: Subarrays formed with exactly 3 different integers: [1,2,1,3], [2,1,3], [1,3,4].
     *
     * Note:
     * 1 <= A.length <= 20000
     * 1 <= A[i] <= A.length (!!!)
     * 1 <= K <= A.length
     *
     * Hard
     *
     * https://leetcode.com/problems/subarrays-with-k-different-integers
     */

    class Solution_sliding_window_editorial {
        class Solution {
            public int subarraysWithKDistinct(int[] nums, int k) {
                int[] count = new int[nums.length + 1];  // Array to store the count of distinct values encountered, because "1 <= A[i] <= A.length"
                int total = 0;
                int curCount = 0;

                for(int i = 0, j = 0; i < nums.length; i++) {
                    if (count[nums[i]] == 0){ //Run into a new unique number
                        k--;
                    }
                    count[nums[i]]++;

                    if (k < 0) { //have more than k unique numbers in window, move left side
                        count[nums[j]]--;
                        j++;
                        k++;     //!!! the while loop below makes sure that there's no duplicate for the element at j
                        curCount = 0;
                    }

                    if (k == 0) {
                        while (count[nums[j]] > 1) {
                            count[nums[j]]--;
                            j++;
                            curCount++;
                        }
                        total += (curCount + 1);
                    }
                }

                return total;
            }
        }
    }

    /**
     * Sliding Window
     *
     * https://leetcode.com/problems/subarrays-with-k-different-integers/discuss/235235/C%2B%2BJava-with-picture-prefixed-sliding-window
     *
     * O(n)
     *
     * Intuition
     * If the subarray [j, i] contains K unique numbers, and first prefix numbers also appear in [j + prefix, i] subarray,
     * we have total 1 + prefix good subarrays. For example, there are 3 unique numbers in [1, 2, 1, 2, 3]. First two numbers
     * also appear in the remaining subarray [1, 2, 3], so we have 1 + 2 good subarrays: [1, 2, 1, 2, 3], [2, 1, 2, 3] and
     * [1, 2, 3].
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
//                    if (dist.get(A[j]) == 0) {
                    map.remove(A[j]);
//                    }

                    /**
                     * !!!
                     * once we move left boundary of sliding window here due to having new
                     * unique number on the right side, since the left most one has no duplicate,
                     * the substring pattern is "broken" here, so we need to reset prefix to 0.
                     *
                     * For example:
                     * when j at idx 4 and i at idx 6:
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

    /**
     * Two pass sliding window solution
     *
     * Use getMostK
     */
    class Solution2 {
        public int subarraysWithKDistinct(int[] A, int K) {
            if (K <= 0) return 0;

            return atMostK(A, K) - atMostK(A, K - 1);

        }

        /**
         * !!!
         * Get number of substrings that has at most K unique numbers
         *
         * 和 LE_340_Longest_Substring_With_At_Most_K_Distinct_Chars 几乎完全相同的逻辑。
         * 这里，我们不是要找 longest substring, 而是要找 number of substrings that meets certain
         * criteria.
         *
         * 我们还是找到一个有K个unique number的window (subarray).那么这个window 的长度就是 number of
         * subarray in the window that has at most K unique numbers.
         *
         * For Example ,  [1,2,1,2,3], K = 2;
         *
         * getMostK(A, 2) -> [1, 12, 121, 1212, 23], 1 + 2 + 3 + 4 + 2 = 12
         * getMostK(A, 1) -> [1, 2, 1, 2, 3], 1 + 1 + 1 + 1 + 1 = 5
         *
         * 12 - 5 = 7
         *
         * It's possible to merge find K and K - 1 in one loop.
         */
        private int atMostK(int[] A, int K) {
            int count = K, res = 0;
            Map<Integer, Integer> map = new HashMap<>();

            for (int i = 0, j = 0; i < A.length; i++) {
                /**
                 * move right, meet a new unique number in the current sliding window
                 */
                if (map.getOrDefault(A[i], 0) == 0) {
                    count--;
                }

                map.put(A[i], map.getOrDefault(A[i], 0) + 1);

                while (count < 0) {
                    map.put(A[j], map.get(A[j]) - 1);
                    if (map.get(A[j]) == 0) count++;
                    j++;
                }

                res += i - j + 1;
            }

            return res;
        }

        private int atMostK_With_Debug_Output(int[] A, int K) {
            int count = K, res = 0;
            Map<Integer, Integer> map = new HashMap<>();

            List<String> pairs = new ArrayList<>();

            for (int i = 0, j = 0; i < A.length; i++) {
                if (map.getOrDefault(A[i], 0) == 0) {
                    count--;
                }

                map.put(A[i], map.getOrDefault(A[i], 0) + 1);

                while (count < 0) {
                    map.put(A[j], map.get(A[j]) - 1);
                    if (map.get(A[j]) == 0) count++;
                    j++;
                }

                res += i - j + 1;

                StringBuilder sb = new StringBuilder();
                for (int k = j; k <= i; k++) {
                    sb.append(A[k]);
                }
                pairs.add(sb.toString());
            }

            System.out.println(pairs.toString());

            return res;
        }
    }

    public static int subarraysWithKDistinct(int[] A, int K) {
        if (K <= 0) return 0;

        int res = 0;
        int prefix = 0;
        int n = A.length;

        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0, j = 0; i < n; i++) {
            System.out.println("i="+i + ", A["+i + "]="+A[i] + ", prefix="+prefix);

            map.put(A[i], map.getOrDefault(A[i], 0) + 1);

            if (map.size() > K) {
                map.put(A[j], map.get(A[j]) - 1);
                map.remove(A[j]);

                System.out.println("move j, j=" + j + ", set prefix to 0");
                prefix = 0;
                j++;
            }

            while (map.getOrDefault(A[j], 0) > 1) {
                prefix++;
                map.put(A[j], map.get(A[j]) - 1);
                j++;
                System.out.println("move j for uniqueness, prefix=" + prefix);
            }

            if (map.size() == K) {
                res += prefix + 1;
                System.out.println("res="+ res);
            }
        }

        return res;
    }

    public static void main(String[] args) {
        /**
         * k = 2
         *
         * {1,2} idx 0 - 1
         * {1,2,1}
         * {1,2,1,2}
         *
         * {2,1}
         * {2,1,2}
         *
         * {2,3}
         *
         * {1,2} idx 2 - 3
         */
        int[] nums = {1,2,1,2,3};
        System.out.println(subarraysWithKDistinct(nums, 2));
    }

}
