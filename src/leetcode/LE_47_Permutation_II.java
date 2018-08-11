package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuank on 2/28/18.
 */
public class LE_47_Permutation_II {
    /**
        Given a collection of numbers that might contain duplicates, return all possible unique permutations.

        For example,
        [1,1,2] have the following unique permutations:
        [
          [1,1,2],
          [1,2,1],
          [2,1,1]
        ]
     */

    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null) {
            return res;
        }

        Arrays.sort(nums);
        helper(nums, res, new ArrayList<>(), new boolean[nums.length]);
        return res;
    }

    private void helper(int[] nums, List<List<Integer>> res, List<Integer> temp, boolean[] visited) {
        if (temp.size() == nums.length) {
            res.add(new ArrayList<>(temp));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (visited[i]) {
                continue;
            }

            /**
             * For duplicates, we just need to choose one of them as "representative",
             * the rule we use is to choose the first one (after sort).
             *
             * nums[i] == nums[i - 1] : only compare its previous neighbour
             * i > 0                  : make sure "nums[i - 1]" not out of index boundary.
             * !visited[i - 1]        : since we always choose the first one as "representative",
             *                          now with identical char at i and i - 1, the previous one is not used,
             *                          therefore it is invalid case, just continue.
             */
            if (i > 0 && nums[i] == nums[i - 1] && !visited[i - 1]) {
                continue;
            }

            temp.add(nums[i]);
            visited[i] = true;
            helper(nums, res, temp, visited);
            temp.remove(temp.size() - 1);
            visited[i] = false;
        }
    }
}
