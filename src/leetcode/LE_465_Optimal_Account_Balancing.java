package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LE_465_Optimal_Account_Balancing {
    /**
     * A group of friends went on holiday and sometimes lent each other money.
     * For example, Alice paid for Bill's lunch for $10. Then later Chris gave
     * Alice $5 for a taxi ride. We can model each transaction as a tuple (x, y, z)
     * which means person x gave person y $z. Assuming Alice, Bill, and Chris are
     * person 0, 1, and 2 respectively (0, 1, 2 are the person's ID), the transactions
     * can be represented as [[0, 1, 10], [2, 0, 5]].
     *
     * Given a list of transactions between a group of people, return the minimum
     * number of transactions required to settle the debt.
     *
     * Note:
     *
     * A transaction will be given as a tuple (x, y, z). Note that x ≠ y and z > 0.
     * Person's IDs may not be linear, e.g. we could have the persons 0, 1, 2 or we
     * could also have the persons 0, 2, 6.
     *
     * Example 1:
     * Input:
     * [[0,1,10], [2,0,5]]
     *
     * Output:
     * 2
     *
     * Explanation:
     * Person #0 gave person #1 $10.
     * Person #2 gave person #0 $5.
     *
     * Two transactions are needed. One way to settle the debt is person #1 pays
     * person #0 and #2 $5 each.
     *
     * Example 2:
     * Input:
     * [[0,1,10], [1,0,1], [1,2,5], [2,0,5]]
     *
     * Output:
     * 1
     *
     * Explanation:
     * Person #0 gave person #1 $10.
     * Person #1 gave person #0 $1.
     * Person #1 gave person #2 $5.
     * Person #2 gave person #0 $5.
     *
     * Therefore, person #1 only need to give person #0 $4, and all debt is settled.
     *
     * Hard
     */

    /**
     * Backtracking
     */
    class Solution {
        public int minTransfers(int[][] transactions) {
            if (null == transactions || transactions.length == 0) return 0;

            Map<Integer, Integer> map = new HashMap<>();
            for (int[] t : transactions) {
                map.put(t[0], map.getOrDefault(t[0], 0) + t[2]);
                map.put(t[1], map.getOrDefault(t[1], 0) - t[2]);
            }

            List<Integer> list = new ArrayList<>();
            for (int v : map.values()) {
                if (v != 0) {
                    list.add(v);
                }
            }

            return dfs(list, 0);
        }

        private int dfs(List<Integer> list, int k) {
            if (k == list.size()) return 0;

            int cur = list.get(k);
            if (cur == 0) {
                return dfs(list, k + 1);
            }

            int min = Integer.MAX_VALUE;
            for (int i = k + 1; i < list.size(); i++) {
                int next = list.get(i);

                /**
                 * Meaning one has money to give and one needs to receive money,
                 * so they can do a transaction.
                 */
                if (next * cur < 0) {
                    list.set(i, cur + next);

                    /**
                     * !!!
                     * dfs(list, k + 1), not "i + 1"
                     */
                    min = Math.min(min, 1 + dfs(list, k + 1));
                    list.set(i, next);

                    if (cur + next == 0) break;
                }
            }
            return min;
        }
    }
}
