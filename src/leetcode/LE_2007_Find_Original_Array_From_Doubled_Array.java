package leetcode;

import java.util.*;

public class LE_2007_Find_Original_Array_From_Doubled_Array {
    /**
     * An integer array original is transformed into a doubled array changed by appending twice the value of every
     * element in original, and then randomly shuffling the resulting array.
     *
     * Given an array changed, return original if changed is a doubled array. If changed is not a doubled array,
     * return an empty array. The elements in original may be returned in any order.
     *
     * Example 1:
     * Input: changed = [1,3,4,2,6,8]
     * Output: [1,3,4]
     * Explanation: One possible original array could be [1,3,4]:
     * - Twice the value of 1 is 1 * 2 = 2.
     * - Twice the value of 3 is 3 * 2 = 6.
     * - Twice the value of 4 is 4 * 2 = 8.
     * Other original arrays could be [4,3,1] or [3,1,4].
     *
     * Example 2:
     * Input: changed = [6,3,0,1]
     * Output: []
     * Explanation: changed is not a doubled array.
     *
     * Example 3:
     * Input: changed = [1]
     * Output: []
     * Explanation: changed is not a doubled array.
     *
     * Constraints:
     * 1 <= changed.length <= 105
     * 0 <= changed[i] <= 105
     *
     * Medium
     *
     * https://leetcode.com/problems/find-original-array-from-doubled-array/
     */

    /**
     * 配对
     *
     * Similar problem:
     * LE_954_Array_Of_Doubled_Pairs
     *
     * Start picking pairs from the smallest number, no need to use TreeMap, sort changed array separately and iterate array.
     * Therefore, save the time in put elements in TreeMap (Which takes O(nlogn)
     *
     * Time  : O(nlogn)
     * Space : O(n)
     */
    class Solution2 {
        public int[] findOriginalArray(int[] changed) {
            int n = changed.length;
            if (n % 2 != 0) return new int[]{};

            int[] res = new int[n / 2];

            Map<Integer, Integer> count = new HashMap<>();

            for (int num : changed) {
                count.put(num, count.getOrDefault(num, 0) + 1);
            }

            Arrays.sort(changed);

            int i = 0;
            for (int key : changed) {
                while (count.get(key) != 0) {
                    int target = key * 2;
                    count.put(key, count.get(key) - 1);

                    if (!count.containsKey(target) || count.get(target) == 0) {
                        return new int[]{};
                    }

                    count.put(target, count.get(target) - 1);
                    res[i] = key;
                    i++;

                    if (i == n) break;
                }
            }

            return res;
        }
    }
}
