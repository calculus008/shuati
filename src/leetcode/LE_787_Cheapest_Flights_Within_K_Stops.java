package leetcode;

import java.util.*;

/**
 * Created by yuank on 11/17/18.
 */
public class LE_787_Cheapest_Flights_Within_K_Stops {
    /**
         There are n cities connected by m flights.
         Each fight starts from city u and arrives at v with a price w.

         Now given all the cities and flights, together with starting
         city src and the destination dst, your task is to find the
         cheapest price from src to dst with up to k stops.
         If there is no such route, output -1.

         Note:

         The number of nodes n will be in range [1, 100], with nodes labeled from 0 to n - 1.
         The size of flights will be in range [0, n * (n - 1) / 2].
         The format of each flight will be (src, dst, price).
         The price of each flight will be in the range [1, 10000].
         k is in the range of [0, n - 1].
         There will not be any duplicated flights or self cycles.

         Medium
     */

    /**
     * http://zxi.mytechroad.com/blog/dynamic-programming/leetcode-787-cheapest-flights-within-k-stops/
     */

    /**
     * Solution 1
     * DFS
     * Time  : O(n ^ (K + 1)) (possible n branches for each of the K + 1 depth)
     * Space : O(K + 1) (Depth of the DFS)
     *
     * 41 ms, 55.56%
     */
    class Solution1 {
        class Pair {
            int dst, cost;

            public Pair(int dst, int cost) {
                this.dst = dst;
                this.cost = cost;
            }
        }

        int res = Integer.MAX_VALUE;

        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
            Map<Integer, List<Pair>> map = new HashMap<>();
            for (int[] flight : flights) {
                if (!map.containsKey(flight[0])) {
                    System.out.println("add key " + flight[0]);
                    map.put(flight[0], new ArrayList<Pair>());
                }
                map.get(flight[0]).add(new Pair(flight[1], flight[2]));
            }

            boolean[] visited = new boolean[n];
            visited[src] = true;

            /**
             * "K + 1" : with K stops, total number of steps is K + 1,
             * "0" : start cost is 0
             */
            helper(src, dst, K + 1, 0, visited, map);

            return res == Integer.MAX_VALUE ? -1 : res;
        }

        private void helper(int src, int dst, int k, int cost, boolean[] visited, Map<Integer, List<Pair>> map) {
            if (src == dst) {
                res = cost;
                return;
            }

            /**
             * take all steps but does not make to dst
             */
            if (k == 0) {
                return;
            }

            /**
             * !!! src does not exist as key in map
             */
            if (map.get(src) == null) {
                return;
            }

            for (Pair p : map.get(src)) {
                if (visited[p.dst]) {
                    continue;
                }

                /**
                 * strong pruning, when current cost is bigger than res, not need to proceed.
                 */
                if (cost + p.cost > res) {
                    continue;
                }

                visited[p.dst] = true;
                helper(p.dst, dst, k - 1, cost + p.cost, visited, map);
                visited[p.dst] = false;
            }

        }
    }

    /**
     * Soluiton 2
     * BFS
     * Time  : O(n ^ (K + 1))
     * Space : O(n ^ (K + 1))
     *
     * 22 ms, 65.11%
     */
    class Solution2 {
        class Pair{
            int dst, cost;
            public Pair(int dst, int cost) {
                this.dst = dst;
                this.cost = cost;
            }
        }

        int res = Integer.MAX_VALUE;
        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
            Map<Integer, List<Pair>> map = new HashMap<>();
            for (int[] flight : flights) {
                if (!map.containsKey(flight[0])) {
                    System.out.println("add key " + flight[0]);
                    map.put(flight[0], new ArrayList<Pair>());
                }
                map.get(flight[0]).add(new Pair(flight[1], flight[2]));
            }

            int steps = 0;
            Queue<Pair> q = new LinkedList<>();
            q.offer(new Pair(src, 0));//start point, src to src with 0 cost

            while (!q.isEmpty()) {
                int size = q.size();
                for (int i = 0; i < size; i++) {
                    Pair cur = q.poll();
                    int cost = cur.cost;
                    int curDst = cur.dst;

                    if (dst == curDst) {
                        res = Math.min(res, cost);
                    }

                    if (!map.containsKey(curDst)) {
                        continue;
                    }

                    for (Pair p : map.get(curDst)) {
                        if (cost + p.cost > res) {
                            continue;
                        }

                        q.offer(new Pair(p.dst, cost + p.cost));
                    }
                }

                if (steps++ > K) break;
            }


            return res == Integer.MAX_VALUE ? -1 : res;
        }
    }

    /**
     * Solution 3
     * DP, Bellman-Ford algorithm
     *
     * dp[k][i]: min cost from src to i taken up to k flights (k-1 stops)
     * init: dp[0:k+2][src] = 0
     * transition: dp[k][i] = min(dp[k-1][j] + price[j][i])
     * ans: dp[K+1][dst]
     *
     * Time  : O(k * |flights|) / O(k*n^2)
     * Space : O(k*n) -> O(n)
     * w/o space compression O(k*n)
     */
    class Solution3 {
        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
            int[][] dp = new int[K + 2][n];

            //!!!
            int InfCost = 1000000009;
            for (int[] a : dp) {
                Arrays.fill(a, InfCost);
            }
            dp[0][src] = 0;

            for (int i = 1; i <= K + 1; i++) {//loop through K + 1 stops
                dp[i][src] = 0;
                for (int[] f : flights) {
                    dp[i][f[1]] = Math.min(dp[i][f[1]], dp[i - 1][f[0]] + f[2]);
                }
            }

            return dp[K + 1][dst] >= InfCost ? -1 : dp[K + 1][dst];
        }
    }
}
