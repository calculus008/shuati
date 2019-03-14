package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuank on 2/27/18.
 */
public class LE_39_Combination_Sum {
    /**
        Given a set of candidate numbers (C) (without duplicates) and a target number (T),
        find all unique combinations in C where the candidate numbers sums to T.

        The same repeated number may be chosen from C unlimited number of times.

        Note:
        All numbers (including target) will be POSITIVE(!!!) integers.
        The solution set must not contain duplicate combinations.
        For example, given candidate set [2, 3, 6, 7] and target 7,
        A solution set is:
        [
          [7],
          [2, 2, 3]
        ]
     */

    // http://zxi.mytechroad.com/blog/searching/leetcode-39-combination-sum/

    //用DFS生成排列组合, 组合的顺序是固定的（有别于排列（permutation））
    //17 ms
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();

        //!!! 为了剪枝
        Arrays.sort(candidates);

        getAns(candidates, target, 0, new ArrayList<Integer>(), res);

        return res;
    }

    public void getAns(int[] candidates, int target, int start, List<Integer> cur, List<List<Integer>> res) {
        if (target < 0) return;//22 ms
        if (target == 0) {
            res.add(new ArrayList<Integer>(cur));//!!!make a copy of cur and add to final result, cur changes dynamically.
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            /**
             * !!!
             * 剪枝，因为已经排了序， 如果当前元素已经大于target,后面的元素也会大与target,没必要再继续下去
             **/
            if (target < candidates[i]) break;

            cur.add(candidates[i]);
            /**
             * !!!
             * 本题允许重复使用同一元素，所以继续pass当前下标i, 这与求组合的标准算法有区别 - 组合算法pass下一个下标i+1
             */
            getAns(candidates, target - candidates[i], i, cur, res);
            cur.remove(cur.size() - 1);
        }
    }
}
