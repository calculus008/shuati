package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuank on 2/27/18.
 */
public class LE_40_Combination_SUM_II {
    /*
        (Possible Duplicate in collection)
        )Given a collection of candidate numbers (C) and a target number (T), find all unique combinations in C where the candidate numbers sums to T.

        Each number in C may only be used ONCE in the combination.

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

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(candidates);

        getAns(candidates, res, target, new ArrayList<>(), 0);

        return res;
    }

    public void getAns(int[] candidates, List<List<Integer>> res, int target, List<Integer> cur,  int s) {
        if(target < 0) return;
        if(target==0) {
            res.add(new ArrayList<Integer>(cur));
            return;
        }

        for(int i=s; i<candidates.length; i++) {
            if(candidates[i] > target) return;

            //!!!去重， “i > s" means this is not the start eelement in this loop
            // The for loop here is to try all possible elements for one fixed position, for this position, one value only needs to appear once
            if(i > s && candidates[i] == candidates[i-1]) continue;

            cur.add(candidates[i]);
            getAns(candidates, res, target-candidates[i], cur, i+1);
            cur.remove(cur.size()-1);
        }
    }
}
