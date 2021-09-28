package leetcode;

import java.util.*;

public class LE_954_Array_Of_Doubled_Pairs {
    /**
     * Given an integer array of even length arr, return true if it is possible to reorder arr such that
     * arr[2 * i + 1] = 2 * arr[2 * i] for every 0 <= i < len(arr) / 2, or false otherwise.
     *
     * Example 1:
     * Input: arr = [3,1,3,6]
     * Output: false
     *
     * Example 2:
     * Input: arr = [2,1,2,6]
     * Output: false
     *
     * Example 3:
     * Input: arr = [4,-2,2,-4]
     * Output: true
     * Explanation: We can take two groups, [-2,-4] and [2,4] to form [-2,-4,2,4] or [2,4,-2,-4].
     *
     * Example 4:
     * Input: arr = [1,2,4,16,8,4]
     * Output: false
     *
     * Constraints:
     * 2 <= arr.length <= 3 * 104
     * arr.length is even.
     * -105 <= arr[i] <= 105
     *
     * Medium
     *
     * https://leetcode.com/problems/array-of-doubled-pairs/
     */

    /**
     * Greedy
     * Time  : O(nlogn)
     * Space : O(n)
     *
     * 配对，关键：
     * We greedily process elements starting from the smallest value, WHY smallest value but not an arbitrary value?
     * Because since it's the smallest values, let say x, there is only ONE choice to pair with x:
     *   If x is a positive number, then it pairs with y = x*2, for example: x = 4 pair with y = 8.
     *   If x is a non-positive number, then it pairs with y = x/2, for example: x = -8 pair with y = -4.
     *   If there is no corresponding y then it's IMPOSSIBLE, return FALSE.
     *
     * If it's an arbitrary value, let say x, there are two choices, either x/2 or x*2 is also a good pairing with x
     * (no matter if x is a possible or negative number), if we choose x/2 or x*2 to pair with x, it maybe WRONG,
     * because some other elements may need it to make pair.
     *
     * For example: arr = [2, 4, 1, 8]
     * If we process x = 2 first, then there are 2 choices, either 4 or 1 can be paired with 2, if we choose 4 -> we got
     * WRONG ANSWER. Because 8 needs 4, so 2 should be paired with 1.
     *
     * Similar Problem:
     * LE_2007_Find_Original_Array_From_Doubled_Array
     */
    class Solution {
        public boolean canReorderDoubled(int[] arr) {
            Map<Integer, Integer> count = new HashMap<>();

            /**
             * HashMap to store element and its frequency.
             */
            for (int i = 0; i < arr.length; i++) {
                count.put(arr[i], count.getOrDefault(arr[i], 0) + 1);
            }

            /**
             * See explanation above why need to do sort
             */
            Arrays.sort(arr);

            /**
             * 可以想象用笔手工配对的过程，从最小的开始找。每找到一对，就把它们去掉。
             */
            for (int num : arr) {
                /**
                 * "count.get(num) == 0" 意味着这个数存在于原始数组中，但是已经被前面的配对给用完了，所以跳过这个数。
                 */
                if (count.get(num) == 0) continue;

                /**
                 * example: [-5, -2], -5 / 2 = -2, but they are not a pair
                 */
                if (num < 0 && num % 2 != 0) return false;

                int target = num >= 0 ? num * 2 : num / 2;

                /**
                 * 当前配对的数不存在的两种可能性：
                 * "!count.containsKey(target)" ：target在原始数组中根本不存在
                 * "count.get(target) == 0"     ：target存在于原始数组中，但是已经被前面的配对给用完了
                 */
                if (!count.containsKey(target) || count.get(target) == 0) return false;

                count.put(target, count.get(target) - 1);
                count.put(num, count.get(num) - 1);
            }

            return true;
        }
    }
}
