package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuank on 3/8/18.
 */
public class LE_90_Subsets_II {
    /**
        Given a collection of integers that might contain duplicates, nums,
        return all possible subsets (the power set).

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

        Medium

        https://leetcode.com/problems/subsets-ii

        LE_78_Subsets
     */

    /**
     * Time  : O(n * 2 ^ n)
     * As we can see in the diagram above, this approach does not generate any duplicate subsets. Thus, in the worst
     * case (array consists of n distinct elements), the total number of recursive function calls will be 2 ^ n.
     * Also, at each function call, a deep copy of the subset currentSubset generated so far is created and added to
     * the subsets list. This will incur an additional O(n) time.
     * Hence : O(n * 2 ^ n)
     *
     * Space : O(n)
     **/
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
            //!!! "i != idx", because i starts from col
            if (i != idx && nums[i] == nums[i - 1]) continue;

            temp.add(nums[i]);
            //!!! "i + 1"
            helper(res, nums, i + 1, temp);
            temp.remove(temp.size() - 1);
        }
    }

    /**
     * Follow up
     * What if we want to save memory usage in recursion stack?
     * We set all possible variables as global
     */
    class Solution {
        List<List<Integer>> res;
        List<Integer> temp;
        int[] nums;
        int idx;

        public List<List<Integer>> subsetsWithDup(int[] nums) {
            res = new ArrayList<>();
            temp = new ArrayList<>();
            this.nums = nums;
            this.idx = 0;

            if (nums == null || nums.length == 0) return res;

            Arrays.sort(nums);
            helper(0);
            return res;
        }

        public void helper(int idx) {
            res.add(new ArrayList<Integer>(temp));
            for (int i = idx; i < nums.length; i++) {
                if (i != idx && nums[i] == nums[i - 1]) {
                    continue;
                }

                temp.add(nums[i]);
                helper(i + 1);
                temp.remove(temp.size() - 1);
            }
        }
    }
}
