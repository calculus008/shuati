package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 3/5/18.
 */
public class LE_78_Subsets {
    /*
        Given a set of DISTINCT integers, nums, return all possible subsets (the power set).

        Note: The solution set must not contain duplicate subsets.

        For example,
        If nums = [1,2,3], a solution is:

        [
          [3],
          [1],
          [2],
          [1,2,3],
          [1,3],
          [2,3],
          [1,2],
          []
        ]
     */

    //Time : O(2 ^ n)
    //Example : [1, 2, 3]
    //[]
    //[1]
    //[1,2]
    //[1,2,3]
    //[1,3]
    //[2]
    //[2,3]
    //[3]

    //1-2-3
    //| |
    //2 3
    //|
    //3

    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if(nums == null || nums.length == 0) return res;
        helper(res, nums, new ArrayList<Integer>(), 0);
        return res;
    }

    public static void helper(List<List<Integer>> res, int[] nums, List<Integer> temp, int index) {
        res.add(new ArrayList<Integer>(temp));

        for (int i = index; i < nums.length; i++) {
            temp.add(i);
            helper(res, nums, temp, i + 1);
            temp.remove(temp.size() - 1);
        }
    }
}
