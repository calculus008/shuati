package lintcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 8/7/18.
 */
public class LI_652_Factorization {
    /**
         A non-negative numbers can be regarded as product of its factors.
         Write a function that takes an integer n and return all possible combinations of its factors.

         Example
         Given n = 8
         return [[2,2,2],[2,4]]
         // 8 = 2 x 2 x 2 = 2 x 4.

         Given n = 1
         return []

         Given n = 12
         return [[2,6],[2,2,3],[3,4]]

         Medium

         Related : LI_235_Prime_Factorization
     */

    //Solution 1 : DFS
    class Solution1 {
        public List<List<Integer>> getFactors(int n) {
            List<List<Integer>> res = new ArrayList<>();
            helper(res, new ArrayList<>(), n, 2);
            return res;
        }

        private void helper(List<List<Integer>> res, List<Integer> cur, int n, int start) {
            if (n <= 1) {
                if (cur.size() > 1) {//!!!
                    res.add(new ArrayList<>(cur));
                }
                return;//!!!
            }

            for (int i = start; i <= Math.sqrt(n); i++) {
                if (n % i == 0) {
                    cur.add(i);
                    helper(res, cur, n / i, i); //!!!
                    cur.remove(cur.size() - 1);
                }
            }

            //!!! ?
            if (n >= start) {
                cur.add(n);
                helper(res, cur, 1, n);
                cur.remove(cur.size() - 1);
            }
        }
    }

    public class Solution2 {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();

        public List<List<Integer>> getFactors(int n) {
            dfs(2, n);
            return ans;
        }

        private void dfs(int start, int remain) {
            if (remain == 1) {
                if (path.size() != 1) {
                    ans.add(new ArrayList<>(path)); //deep copy
                }
                return;
            }

            for (int i = start; i <= remain; i++) {
                if (i > remain / i) {//same effect as using "i <= Math.sqrt(remain)" as end condition for this for loop
                    break;
                }
                if (remain % i == 0) {
                    path.add(i);                  //进栈
                    dfs(i, remain / i);
                    path.remove(path.size() - 1); //出栈
                }
            }

            /**
             * !!!
             * one more step after loop
             * set remain as 1, so the next level will be base case and
             * add path into ans. Without this step, ans will be empty list.
             */
            path.add(remain);
            dfs(remain, 1);
            path.remove(path.size() - 1);
        }
    }

}
