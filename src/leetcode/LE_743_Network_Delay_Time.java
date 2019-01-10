package leetcode;

import java.util.*;

public class LE_743_Network_Delay_Time {
    /**
         There are N network nodes, labelled 1 to N.

         Given times, a list of travel times as directed edges times[i] = (u, v, w),
         where u is the source node, v is the target node, and w is the time it
         takes for a signal to travel from source to target.

         Now, we send a signal from a certain node K. How long will it take for
         all nodes to receive the signal? If it is impossible, return -1.

         Note:
         N will be in the range [1, 100].
         K will be in the range [1, N].
         The length of times will be in the range [1, 6000].
         All edges times[i] = (u, v, w) will have 1 <= u, v <= N and 1 <= w <= 100.

         Easy
     */

    /**
     * http://zxi.mytechroad.com/blog/graph/leetcode-743-network-delay-time/
     *
     * Bellman - Ford : Single source, all destination, shortest path
     *
     * DP
     *
     * dp[i] : the min cost from starting point K to i
     * init  : all dp[i] set to MAX_COST, meaning the cost is indefinite
     * Transition : dp[v] = min(dp[u] + cost, dp[v]]
     *              For a given tuple [u, v, cost], dp[v] is the min of
     *              directly going from k to v
     *              and
     *              going to u first, then go from u to v.
     *
     * We want to know when all nodes can get the message, then it must
     * be the max value in dp[] - the longest time among the shortest
     * cost form k each of the node.
     *
     * Time  : O(N * E)
     * Space : O(N)
     */
    class Solution1 {
        public int networkDelayTime(int[][] times, int N, int K) {
            //max weight is 100,number of nodes is 100
            int MAX_COST = 101 * 100;

            int[] dp = new int[N];
            Arrays.fill(dp, MAX_COST);
            dp[K - 1] = 0;

            for (int i = 0; i < N; i++) {
                for (int[] t : times) {
                    int u = t[0] - 1;
                    int v = t[1] - 1;
                    int cost = t[2];
                    dp[v] = Math.min(dp[u] + cost, dp[v]);
                }
            }

            int res = Integer.MIN_VALUE;
            for (int d : dp) {
                res = Math.max(res, d);
            }

            return res == MAX_COST ? -1 : res;
        }
    }

    /**
     * Floyd-Warshall, all pair shortest path
     *
     * DP
     *
     * dp[i][j] : min cost from i to j
     * init : dp[][] all init with MAX_COST (indefinite)
     *        dp[i][i] = 0
     *        dp[t[0] - 1][t[1] - 1] = t[2]
     * transition : dp[i][j] = min(dp[i][j], dp[i][k] + dp[k][j])
     *
     * Answer : max value among dp[K - 1][i]
     *
     * Time  : O(N ^ 3)
     * Space : O(N ^ 2)
     */
    class Solution2 {
        public int networkDelayTime(int[][] times, int N, int K) {
            int MAX_COST = 101 * 100;
            int[][] dp = new int[N][N];

            for (int[] d : dp) {
                Arrays.fill(d, MAX_COST);
            }

            for (int[] t : times) {
                dp[t[0] - 1][t[1] - 1] = t[2];
            }

            for (int i = 0; i < N; i++) {
                dp[i][i] = 0;
            }

            /**
             * 给定k (中转站）, check dp[i][j]
             */
            for (int k = 0; k < N; k++) {
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k][j]);
                    }
                }
            }

            int res = Integer.MIN_VALUE;
            for (int i = 0; i < N; i++) {
                /**
                 * K is 1-based, need to convert to K - 1
                 */
                res = Math.max(dp[K - 1][i], res);
            }

            return res >= MAX_COST ? -1 : res;
        }
    }

    /**
     * Dijskra
     * https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
     *
     * Heap implementation
     *
     * Time  : O(NlogN + E)
     * Space : O(N + E)
     *
     */
    class Solution3 {
        public int networkDelayTime(int[][] times, int N, int K) {
            /**
             * build Directed Weighed Graph
             **/
            Map<Integer, List<int[]>> graph = new HashMap();
            for (int[] edge : times) {
                if (!graph.containsKey(edge[0])) {
                    graph.put(edge[0], new ArrayList<int[]>());
                }
                graph.get(edge[0]).add(new int[]{edge[1], edge[2]});//source -> (destination, cost)
            }

            /**
             * Heap : store pair {distance, destination}, sorted by distance
             */
            PriorityQueue<int[]> heap = new PriorityQueue<int[]>((a, b) -> a[0] - b[0]);
            heap.offer(new int[]{0, K});

            Map<Integer, Integer> dist = new HashMap();

            while (!heap.isEmpty()) {
                int[] info = heap.poll();
                int d = info[0], node = info[1];

                if (dist.containsKey(node)) {
                    continue;
                }

                dist.put(node, d);

                /**
                 * process the node with the shortest distance from source
                 */
                if (graph.containsKey(node))
                    for (int[] edge : graph.get(node)) {
                        int nei = edge[0], d2 = edge[1];
                        if (!dist.containsKey(nei))
                            heap.offer(new int[]{d + d2, nei});
                    }
            }

            /**
             * can't make it to every node
             */
            if (dist.size() != N) {
                return -1;
            }

            int ans = 0;

            for (int cand : dist.values()) {
                ans = Math.max(ans, cand);
            }

            return ans;
        }
    }
}