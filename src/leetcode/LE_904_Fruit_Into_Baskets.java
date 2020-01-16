package src.leetcode;

import java.util.HashMap;
import java.util.Map;

public class LE_904_Fruit_Into_Baskets {
    /**
     * In a row of trees, the i-th tree produces fruit with type tree[i].
     *
     * You start at any tree of your choice, then repeatedly perform the following steps:
     *
     * Add one piece of fruit from this tree to your baskets.  If you cannot, stop.
     * Move to the next tree to the right of the current tree.  If there is no tree to the right, stop.
     * Note that you do not have any choice after the initial choice of starting tree:
     * you must perform step 1, then step 2, then back to step 1, then step 2, and so on until you stop.
     *
     * You have two baskets, and each basket can carry any quantity of fruit, but you want each basket
     * to only carry one type of fruit each.
     *
     * What is the total amount of fruit you can collect with this procedure?
     *
     *
     *
     * Example 1:
     *
     * Input: [1,2,1]
     * Output: 3
     * Explanation: We can collect [1,2,1].
     * Example 2:
     *
     * Input: [0,1,2,2]
     * Output: 3
     * Explanation: We can collect [1,2,2].
     * If we started at the first tree, we would only collect [0, 1].
     * Example 3:
     *
     * Input: [1,2,3,2,2]
     * Output: 4
     * Explanation: We can collect [2,3,2,2].
     * If we started at the first tree, we would only collect [1, 2].
     * Example 4:
     *
     * Input: [3,3,3,1,2,1,1,2,3,3,4]
     * Output: 5
     * Explanation: We can collect [1,2,1,1,2].
     * If we started at the first tree or the eighth tree, we would only collect 4 fruits.
     *
     *
     * Note:
     *
     * 1 <= tree.length <= 40000
     * 0 <= tree[i] < tree.length
     *
     * Same Question as LE_159_Longest_Substring_With_At_Most_Two_Distinct_Chars
     */

    /**
     * Use array
     */
    class Solution1 {
        public int totalFruit(int[] tree) {
            if (null == tree || tree.length == 0) return 0;

            int n = tree.length;
            int[] count = new int[n];
            int num = 0;
            int res = 0;

            for (int i = 0, j = 0; i < n; i++) {
                if (count[tree[i]] == 0) {
                    num++;
                }
                count[tree[i]]++;

                while (num > 2) {
                    count[tree[j]]--;
                    if (count[tree[j]] == 0) {
                        num--;
                    }
                    j++;
                }

                res = Math.max(res, i - j + 1);
            }

            return res;
        }
    }

    /**
     * Use Map
     */
    class Solution2 {
        public int totalFruit(int[] tree) {
            if (null == tree || tree.length == 0) return 0;

            int n = tree.length;
            Map<Integer, Integer> map = new HashMap<>();
            int res = 0;

            for (int i = 0, j = 0; i < n; i++) {
                map.put(tree[i], map.getOrDefault(tree[i], 0) + 1);

                while (map.size() > 2) {
                    map.put(tree[j], map.get(tree[j]) - 1);
                    if (map.get(tree[j]) == 0) {
                        map.remove(tree[j]);
                    }

                    j++;
                }

                res = Math.max(res, i - j + 1);
            }

            return res;
        }
    }
}
