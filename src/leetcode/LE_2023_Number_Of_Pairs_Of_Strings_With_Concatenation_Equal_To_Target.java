package leetcode;

import java.util.*;

public class LE_2023_Number_Of_Pairs_Of_Strings_With_Concatenation_Equal_To_Target {
    /**
     * Given an array of digit strings nums and a digit string target, return the number of pairs of indices (i, j) (where i != j)
     * such that the concatenation of nums[i] + nums[j] equals target.
     *
     * Example 1:
     * Input: nums = ["777","7","77","77"], target = "7777"
     * Output: 4
     * Explanation: Valid pairs are:
     * - (0, 1): "777" + "7"
     * - (1, 0): "7" + "777"
     * - (2, 3): "77" + "77"
     * - (3, 2): "77" + "77"
     *
     * Example 2:
     * Input: nums = ["123","4","12","34"], target = "1234"
     * Output: 2
     * Explanation: Valid pairs are:
     * - (0, 1): "123" + "4"
     * - (2, 3): "12" + "34"
     *
     * Example 3:
     * Input: nums = ["1","1","1"], target = "11"
     * Output: 6
     * Explanation: Valid pairs are:
     * - (0, 1): "1" + "1"
     * - (1, 0): "1" + "1"
     * - (0, 2): "1" + "1"
     * - (2, 0): "1" + "1"
     * - (1, 2): "1" + "1"
     * - (2, 1): "1" + "1"
     *
     *
     * Constraints:
     *
     * 2 <= nums.length <= 100
     * 1 <= nums[i].length <= 100
     * 2 <= target.length <= 100
     * nums[i] and target consist of digits.
     * nums[i] and target do not have leading zeros.
     *
     * Medium
     *
     * https://leetcode.com/problems/number-of-pairs-of-strings-with-concatenation-equal-to-target
     */

    /**
     * Time : O(N * T), N - length of nums, T - average length of each num
     *
     * Use HashMap as count map, then iterate target by separting it into two parts, check if the
     * two parts exist in count map. Then calculate the result.
     */
    public int numOfPairs(String[] nums, String target) {
        //key: string in nums,
        //value : frequency
        HashMap<String, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }

        int res = 0;
        int n = target.length();
        String a = "", b = "";

        for (int i = 1; i < n; i++) {//partition target string
            a = target.substring(0, i);
            b = target.substring(i, n);

            if (map.containsKey(a) && map.containsKey(b)) {
                if (a.equals(b)) {
                    res += (map.get(a) * (map.get(a) - 1)); //!!!
                } else {
                    res += (map.get(a) * map.get(b));
                }
            }
        }
        return res;
    }
}
