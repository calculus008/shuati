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

    public class Solution {
        public List<Integer> findDuplicates(int[] nums) {
            List<Integer> result = new ArrayList<>();

            for(int i=0; i<nums.length; i++) {
                int idx = Math.abs(nums[i]) - 1;
                if(nums[idx] < 0) {
                    result.add(idx+1);
                }
                //!!!
                nums[idx] = -nums[idx];
            }
            return result;
        }
    }
}
