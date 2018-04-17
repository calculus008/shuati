package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuank on 3/8/18.
 */
public class LE_90_Subsets_II {
    /*
        Given a collection of integers that might contain duplicates, nums, return all possible subsets (the power set).

        Note: The solution set must not contain duplicate subsets.

        For example,
        If nums = [1,2,2], a solution is:

        [
          [2],
          [1],
          [1,2,2],
          [2,2],
          [1,2],
          []
        ]
     */

    //Time : O(2 ^ n), Space : O(n)
    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length == 0) return res;

        Arrays.sort(nums);
        helper(res, nums, 0, new ArrayList<Integer>());
        return res;
    }

    public static void helper(List<List<Integer>> res, int[] nums, int idx, List<Integer> temp) {
        res.add(new ArrayList<Integer>(temp));
        for (int i = idx; i < nums.length; i++) {
            //!!! "i != idx", because i starts from idx
            if (i != idx && nums[i] == nums[i - 1]) continue;

            temp.add(nums[i]);
            //!!! "i + 1"
            helper(res, nums, i + 1, temp);
            temp.remove(temp.size() - 1);
        }
    }
}
