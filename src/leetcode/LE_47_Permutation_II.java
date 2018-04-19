package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuank on 2/28/18.
 */
public class LE_47_Permutation_II {
    /*
        Given a collection of numbers that might contain duplicates, return all possible unique permutations.

        For example,
        [1,1,2] have the following unique permutations:
        [
          [1,1,2],
          [1,2,1],
          [2,1,1]
        ]
     */

    public static List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (null == nums || nums.length == 0) return res;

        //!!!
        Arrays.sort(nums);

        helper(nums, new boolean[nums.length], new ArrayList<Integer>(), res);
        return res;
    }

    //O(n * n!)
    private static void helper(int[] nums, boolean[] used, List<Integer> temp, List<List<Integer>> res) {
        if (temp.size() == nums.length) {
            res.add(new ArrayList<>(temp));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            /**
            !!! "!used[i-1]" - this value has been tried for current location (used[i-1] has been set back to false after used for current location)
            */
            if (used[i] || (i > 0 && nums[i] == nums[i-1] && !used[i-1])) continue;

            temp.add(nums[i]);
            used[i] = true;
            helper(nums, used, temp, res);
            used[i] = false;
            temp.remove(temp.size() - 1);
        }
    }
}
