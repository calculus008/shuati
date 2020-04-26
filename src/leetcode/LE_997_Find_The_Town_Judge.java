package leetcode;

import java.util.HashMap;
import java.util.Map;

public class LE_997_Find_The_Town_Judge {
    /**
     * In a town, there are N people labelled from 1 to N.  There is a rumor that one of
     * these people is secretly the town judge.
     *
     * If the town judge exists, then:
     *
     * The town judge trusts nobody.
     * Everybody (except for the town judge) trusts the town judge.
     * There is exactly one person that satisfies properties 1 and 2.
     * You are given trust, an array of pairs trust[i] = [a, b] representing that the person
     * labelled a trusts the person labelled b.
     *
     * If the town judge exists and can be identified, return the label of the town judge.
     * Otherwise, return -1.
     *
     * Example 1:
     * Input: N = 2, trust = [[1,2]]
     * Output: 2
     *
     * Example 2:
     * Input: N = 3, trust = [[1,3],[2,3]]
     * Output: 3
     *
     * Example 3:
     * Input: N = 3, trust = [[1,3],[2,3],[3,1]]
     * Output: -1
     *
     * Example 4:
     * Input: N = 3, trust = [[1,2],[2,3]]
     * Output: -1
     *
     * Example 5:
     * Input: N = 4, trust = [[1,3],[1,4],[2,3],[2,4],[4,3]]
     * Output: 3
     *
     * Note:
     * 1 <= N <= 1000
     * trust.length <= 10000
     * trust[i] are all different
     * trust[i][0] != trust[i][1]
     * 1 <= trust[i][0], trust[i][1] <= N
     *
     * Easy
     */

    /**
     * Same as LE_277_Find_The_Celebrity
     *
     * Convert to DAG issue, the target should have N - 1 in-degree and 0 out-degree.
     *
     * !!!
     * We can use two arrays to record in-degree and out-degree for each person, here
     * we simply use the property of town judge : in-degree - out-degree = N - 1, hence
     * we use only 1 array
     *
     * One optimization we can make is to observe that it is impossible for there to be a
     * town judge if there are not at least N - 1 edges in the trust array. This is because a
     * town judge must have N - 1 in-going edges, and so if there aren't at least N - 1 edges
     * in total, then it is impossible to meet this requirement.
     *
     * Time Complexity : O(E)
     * We loop over the trust list once. The cost of doing this is O(E)
     * We then loop over the people. The cost of doing this is O(N)
     *
     * Going by this, it now looks this is one those many graph problems where the cost is O(\max(N, E) = O(N + E).
     * After all, we don't know whether E or N is the bigger one, right?
     *
     * However, remember how we terminate early if E < N - 1? This means that in the best case, the time complexity
     * is O(1). And in the worst case, we know that E ≥ N - 1. For the purpose of big-oh notation, we ignore the
     * constant of 1. Therefore, in the worst case, E has to be bigger, and so we can simply drop the N,
     * leaving O(E).
     *
     * Space Complexity : O(N).
     */
    class Solution1 {
        public int findJudge(int N, int[][] trust) {
            if (trust.length < N - 1) return -1;

            int[] a = new int[N + 1];

            for (int[] t : trust) {
                a[t[1]]++;
                a[t[0]]--;
            }

            for (int i = 1; i <= N; i++) {
                if (a[i] == N - 1) return i;
            }

            return -1;
        }
    }

    /**
     * Same DAG idea, a little bit clumsy by using hashmap,
     * need to take care corner case etc, not recommended
     */
    class Solution2 {
        public int findJudge(int N, int[][] trust) {
            if (trust == null || trust.length == 0) {
                if (N == 1) return 1;
                return -1;
            }

            Map<Integer, Integer> inmap = new HashMap<>();
            Map<Integer, Integer> outmap = new HashMap<>();

            for (int[] t : trust) {
                inmap.put(t[1], inmap.getOrDefault(t[1], 0) + 1);
                outmap.put(t[0], outmap.getOrDefault(t[0], 0) + 1);
            }

            for (int key : inmap.keySet()) {
                if (inmap.get(key) == N - 1 && !outmap.containsKey(key)) {
                    return key;
                }
            }

            return -1;
        }
    }

    /**
     * The exact same solution for LE_277_Find_The_Celebrity
     *
     * Simply adapt to use different kind of input here.
     *
     * Time : O(N)
     */
    class Solution3 {
        public int findJudge(int N, int[][] trust) {
            boolean[][] g = new boolean[N + 1][N + 1];

            for (int[] t : trust) {
                int i = t[0];
                int j = t[1];

                g[i][j] = true;
            }

            int candidate = 1;
            for (int i = 2; i <= N; i++) {
                if (trust(g, candidate, i)) {
                    candidate = i;
                }
            }

            for (int i = 1; i <= N; i++) {
                if (i != candidate && (trust(g, candidate, i) || !trust(g, i, candidate))) {
                    return -1;
                }
            }

            return candidate;
        }

        private boolean trust(boolean[][] g, int i, int j) {
            return g[i][j];
        }
    }
}
