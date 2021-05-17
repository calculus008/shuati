package leetcode;

import java.util.ArrayList;
import java.util.List;

public class LE_442_Find_All_Duplicates_In_An_Array {
    /**
     * Given an array of integers, 1 ≤ a[i] ≤ n (n = size of array),
     * some elements appear twice and others appear once.
     *
     * Find all the elements that appear twice in this array.
     *
     * Could you do it without extra space and in O(n) runtime?
     *
     * Example:
     * Input:
     * [4,3,2,7,8,2,3,1]
     *
     * Output:
     * [2,3]
     */

    /**
     * Time : O(n)
     * Space : O(1)
     *
     * num is between 1 and n, index is between 0 and n - 1.
     * So it's like a linked list, the value at an index can be seen as the index of the next node.
     * For example: nums[0] = 4, its next index is 4 - 1 = 3, so it is nums[3].
     * So for those numbers that appear twice, there must be a loop that will come back to the same node.
     *
     * We mark the visited node by making its value -nums[i]
     *
     * [4,3,2,7,8,2,3,1]
     *
     * [4,3,2,-7,8,2,3,1]
     *  |_____|
     *
     * [4,3,-2,-7,8,2,3,1]
     *    |__|
     *
     * [4,-3,-2,-7,8,2,3,1]
     *     |__|
     *
     * [4,-3,-2,-7,8,2,-3,1]
     *           |______|
     *
     * [4,-3,-2,-7,8,2,-3,-1]
     *             |______|
     *
     * [4, 3,-2,-7,8,2,-3,-1], add 1+1 = 2
     *     |_________|
     *
     * [4, 3,2,-7,8,2,-3,-1], add 2+1 = 3
     *       |_________|
     *
     * [-4, 3,2,-7,8,2,-3,-1]
     *   |________________|
     *
     * ===========
     *
     * [4, 3, 2, -7, 8, 2, 3, 1]
     * []
     *
     * [4, 3, -2, -7, 8, 2, 3, 1]
     * []
     *
     * [4, -3, -2, -7, 8, 2, 3, 1]
     * []
     *
     * [4, -3, -2, -7, 8, 2, -3, 1]
     * []
     *
     * [4, -3, -2, -7, 8, 2, -3, -1]
     * []
     *
     * [4, 3, -2, -7, 8, 2, -3, -1]
     * [2]
     *
     * [4, 3, 2, -7, 8, 2, -3, -1]
     * [2, 3]
     *
     * [-4, 3, 2, -7, 8, 2, -3, -1]
     * [2, 3]
     *
     */
    public class Solution {
        public List<Integer> findDuplicates(int[] nums) {
            List<Integer> result = new ArrayList<>();

            for (int i = 0; i < nums.length; i++) {
                int idx = Math.abs(nums[i]) - 1;
                if (nums[idx] < 0) {
                    result.add(idx + 1);
                }
                //!!!
                nums[idx] = -nums[idx];
            }
            return result;
        }
    }

    /**
     * Same trick to find disappearing number
     */
    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> res = new ArrayList();

        for(int num : nums){
            int index = Math.abs(num) - 1;
            if(nums[index] > 0) {
                nums[index] = -nums[index];
            }
        }

        for(int i = 0; i < nums.length; i++){
            if(nums[i] > 0) res.add(i + 1);
        }

        return res;
    }

}
