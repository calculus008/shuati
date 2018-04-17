package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 3/5/18.
 */
public class LE_77_Combinations {
    /*
        Given two integers n and k, return all possible combinations of k numbers out of 1 ... n.

        For example,
        If n = 4 and k = 2, a solution is:

        [
          [2,4],
          [3,4],
          [2,3],
          [1,2],
          [1,3],
          [1,4],
        ]
     */

    //Time : O(n ^ min(k, n - 1)), Space : O(n)
    public static List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        helper(res, new ArrayList<Integer>(), n, k, 1);
        return res;
    }

    public static void helper(List<List<Integer>> res, List<Integer> temp, int n, int k, int start) {
        if (k == 0) {
            res.add(new ArrayList<>(temp));
            //!!!Don't forget to return !!!
            return;
        }

        for (int i = start; i <= n; i++) {
            temp.add(i);
            helper(res, temp, n, k - 1, i + 1);
            temp.remove(temp.size() - 1);
        }
    }
}
