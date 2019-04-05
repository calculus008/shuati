package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuank on 2/27/18.
 */
public class LE_40_Combination_SUM_II {
    /**
        (Possible Duplicate in collection)
        Given a collection of candidate numbers (C) and a target number (T),
        find all unique combinations in C where the candidate numbers sums to T.

        Each number in C may ONLY be used ONCE in the combination.

        Note:
        All numbers (including target) will be positive integers.
        The solution set must not contain duplicate combinations.
        For example, given candidate set [10, 1, 2, 7, 6, 1, 5] and target 8,
        A solution set is:

        [
          [1, 7],
          [1, 2, 5],
          [2, 6],
          [1, 1, 6]
        ]
     */

    /**
     * http://zxi.mytechroad.com/blog/searching/leetcode-40-combination-sum-ii/
     *
     * DFS
     * Time  : O(2 ^ n)
     * Space : O(k * n)
     */

    class Solution1 {
        public List<List<Integer>> combinationSum(int[] candidates, int target) {
            List<List<Integer>> res = new ArrayList<>();

            //!!!
            Arrays.sort(candidates);

            getAns(candidates, res, target, new ArrayList<>(), 0);

            return res;
        }

        public void getAns(int[] candidates, List<List<Integer>> res, int target, List<Integer> cur, int s) {
            if (target < 0) return;
            if (target == 0) {
                res.add(new ArrayList<Integer>(cur));
                return;
            }

            for (int i = s; i < candidates.length; i++) {
                //!!!
                if (candidates[i] > target) return;

                /**!!!
                 * 去重， “i > s" means this is not the start element in this loop.
                 * The for loop here is to try all possible elements for one fixed position,
                 * for this position, one value only needs to appear once
                 **/
                if (i > s && candidates[i] == candidates[i - 1]) continue;

                cur.add(candidates[i]);
                getAns(candidates, res, target - candidates[i], cur, i + 1);
                cur.remove(cur.size() - 1);
            }
        }
    }

    class Solution2 {
        public List<List<Integer>> combinationSum_JiuZhang(int[] num, int target) {
            List<List<Integer>> res = new ArrayList<>();
            if (num == null || num.length == 0) {
                return res;
            }

            //!!!
            Arrays.sort(num);
            helper(num, target, 0, res, new ArrayList<>());
            return res;
        }

        private void helper(int[] num, int target, int startIdx, List<List<Integer>> res, List<Integer> temp) {
            if (target == 0) {
                res.add(new ArrayList<>(temp));
                return;
            }

            for (int i = startIdx; i < num.length; i++) {
                if (i != startIdx && num[i] == num[i - 1]) {
                    continue;
                }

                //!!!
                if (num[i] > target) {
                    break;
                }

                temp.add(num[i]);
                helper(num, target - num[i], i + 1, res, temp);
                temp.remove(temp.size() - 1);
            }
        }
    }
}
