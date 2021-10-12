package leetcode;

import java.util.*;

public class LE_1424_Diagonal_Traverse_II {
    /**
     * Given a list of lists of integers, nums, return all elements of nums in diagonal order as shown in the below images.
     *
     * Example 1:
     * Input: nums = [[1,2,3],[4,5,6],[7,8,9]]
     * Output: [1,4,2,7,5,3,8,6,9]
     *
     * Example 2:
     * Input: nums = [[1,2,3,4,5],[6,7],[8],[9,10,11],[12,13,14,15,16]]
     * Output: [1,6,2,8,7,3,9,4,12,10,5,13,11,14,15,16]
     *
     * Example 3:
     * Input: nums = [[1,2,3],[4],[5,6,7],[8],[9,10,11]]
     * Output: [1,4,2,5,3,8,6,9,7,10,11]
     *
     * Example 4:
     * Input: nums = [[1,2,3,4,5,6]]
     * Output: [1,2,3,4,5,6]
     *
     * Constraints:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i].length <= 10^5
     * 1 <= nums[i][j] <= 10^9
     * There at most 10^5 elements in nums.
     *
     * Medium
     *
     * https://leetcode.com/problems/diagonal-traverse-ii/
     */

    /**
     * An variation from
     * LE_498_Diagonal_Traverse
     *
     * Still the key observation:
     * All values in the same diagonal share the same sum value of x index + y index (!!!)
     *
     * Hence, use hashmap, the elements on the same diagonal is saved in a list and its key is row index + col index.
     * Since we print in order from bottom left to upper right, therefore, we keep adding elements at the start of
     * the list.
     */
    class Solution {
        public int[] findDiagonalOrder(List<List<Integer>> nums) {
            Map<Integer, List<Integer>> map = new HashMap<>();
            int maxKey = 0;
            int count = 0;

            for (int i = 0; i < nums.size(); i++) {
                for (int j = 0; j < nums.get(i).size(); j++) {
                    map.computeIfAbsent(i + j, l -> new ArrayList<>()).add(0, nums.get(i).get(j));
                    maxKey = Math.max(maxKey, i + j);
                    count++;
                }
            }

            int[] res = new int[count];
            count = 0;

            for (int i = 0; i <= maxKey; i++) {
                for (int num : map.get(i)) {
                    res[count] = num;
                    count++;
                }
            }

            return res;
        }
    }
}
