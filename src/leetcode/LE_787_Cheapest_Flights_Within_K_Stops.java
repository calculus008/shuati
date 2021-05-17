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

         Example 1:
         Input:
         n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
         src = 0, dst = 2, k = 1
         Output: 200

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
     * Dijskra
     * Modified from Dijskra solution from LE_743_Network_Delay_Time
     *
     * Time  : O(E + NlogN), where E is the total number of flights (edges).
     * Space : O(N), the size of the heap.
     */
    class Solution_Dijskra_1 {
        class Pair {
            int dest;
            int cost;
            /**
             * We have stops constrain, we need to have a property to save it.
             */
            int stops;

            public Pair(int dest, int cost, int stops) {
                this.dest = dest;
                this.cost = cost;
                this.stops = stops;
            }
        }

        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
            Map<Integer, List<Pair>> graph = new HashMap<>();
            for (int[] t : flights) {
                if (!graph.containsKey(t[0])) {
                    graph.put(t[0], new ArrayList<>());
                }
                /**
                 * we don't care stops in graph, so just set as 0.
                 * This is not quite space efficient.
                 */
                graph.get(t[0]).add(new Pair(t[1], t[2], 0));
            }

            PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> a.cost - b.cost);
            /**
             * We start from src, the cost to src is 0, steps is 0
             */
            pq.offer(new Pair(src, 0, 0));

            while (!pq.isEmpty()) {
                Pair cur = pq.poll();

                if (cur.dest == dst) return cur.cost;

                /**
                 * Must have this validation
                 */
                if (!graph.containsKey(cur.dest)) continue;

                if (cur.stops <= K) {
                    for (Pair p : graph.get(cur.dest)) {
                        pq.offer(new Pair(p.dest, cur.cost + p.cost, cur.stops + 1));
                    }
                }
            }

            return -1;
        }
    }

    /**
     * No using Pair class.
     *
     * We save different data in graph and heap.
     *
     * Graph : Map of Maps
     * (src -> (dst -> cost))
     *
     * heap: triplet in form of array[]
     * {dst, cost, stops}
     *
     * here:
     * dst   - current stop we get to (from src)
     * cost  - total cost along the way from src to dst
     * stops - total number stops from src to dst
     */
    class Solution_Dijskra_2 {
        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
            Map<Integer, Map<Integer, Integer>> graph = new HashMap<>();
            for (int[] t : flights) {
                if (!graph.containsKey(t[0])) {
                    graph.put(t[0], new HashMap<>());
                }
                graph.get(t[0]).put(t[1], t[2]);
            }

            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
            pq.offer(new int[]{src, 0, 0});

            while (!pq.isEmpty()) {
                int[] cur = pq.poll();
                int dest = cur[0];
                int cost = cur[1];
                int stops = cur[2];

                if (dest == dst) return cost;

                if (!graph.containsKey(dest)) continue;

                if (stops <= K) {
                    Map<Integer, Integer> map = graph.get(dest);
                    for (int key : map.keySet()) {
                        pq.offer(new int[]{key, map.get(key) + cost, stops + 1});
                    }
                }
            }

            return -1;
        }
    }

    /**
     * http://zxi.mytechroad.com/blog/dynamic-programming/leetcode-787-cheapest-flights-within-k-stops/
     *
     * Single source, single destination, lowest cost within K steps
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
            //build DIRECTED graph
            Map<Integer, List<Pair>> graph = new HashMap<>();
            for (int[] flight : flights) {
                if (!graph.containsKey(flight[0])) {
                    graph.put(flight[0], new ArrayList<Pair>());
                }
                graph.get(flight[0]).add(new Pair(flight[1], flight[2]));
            }

            boolean[] visited = new boolean[n];
            visited[src] = true;

            /**
             * "K + 1" : with K stops, total number of steps is K + 1,
             * "0" : start cost is 0
             */
            helper(src, dst, K + 1, 0, visited, graph);

            return res == Integer.MAX_VALUE ? -1 : res;
        }

        private void helper(int src, int dst, int k, int cost, boolean[] visited, Map<Integer, List<Pair>> graph) {
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
             * !!! src does not exist as key in graph
             */
            if (graph.get(src) == null) {
                return;
            }

            for (Pair p : graph.get(src)) {
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
                helper(p.dst, dst, k - 1, cost + p.cost, visited, graph);
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
            //build DIRECTED graph
            Map<Integer, List<Pair>> graph = new HashMap<>();
            for (int[] flight : flights) {
                if (!graph.containsKey(flight[0])) {
                    graph.put(flight[0], new ArrayList<Pair>());
                }
                graph.get(flight[0]).add(new Pair(flight[1], flight[2]));
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

                    if (dst == curDst) {//we get to the destination
                        res = Math.min(res, cost);
                    }

                    if (!graph.containsKey(curDst)) {
                        continue;
                    }

                    for (Pair p : graph.get(curDst)) {
                        if (cost + p.cost > res) {//strong pruning
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
     *   1    2    3    4
     * a -> b -> c -> c -> d
     * stops   : 3
     * flights : 4
     *
     * So with K stops, we have K + 1 flights from src to dst.
     *
     * dp[k][i]: min cost from src to i taken up to k flights (k + 1 stops)
     * init: dp[0:k+2][src] = 0
     * transition: dp[k][i] = min(dp[k-1][j] + price[j][i], dp[k][i])
     * ans: dp[K+1][dst]
     *
     * Time  : O(k * E) -> O(k * n^2), E is number edges -> number of total flights, max value is n ^ 2.
     * Space : O(k*n) -> O(n)
     * w/o space compression O(k*n)
     */
    class Solution3 {
        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
            //"with nodes labeled from 0 to n - 1"
            int[][] dp = new int[K + 2][n];

            /**
             * Can't use Integer.MAX_VALUE here becasue we need to do calculation
             *  "dp[i - 1][f[0]] + f[2])"
             * It will overflow. Therefore, we just specify a very large number here.
             * In the end, any value that is ">=" this number means no answer.
             */
            int InfCost = 1000000009;
            for (int[] a : dp) {
                Arrays.fill(a, InfCost);
            }
            dp[0][src] = 0;

            /**
             * loop through K + 1 flights -> take 1 flight, 2 flights, 3 flights .... K + 1 flights
             */
            for (int i = 1; i <= K + 1; i++) {
                /**
                 * no matter how many flights taken, cost to get to src is always 0
                 */
                dp[i][src] = 0;
                for (int[] f : flights) {
                    /**
                     * f[0] : start
                     * f[1] : end
                     * f[2] : cost
                     *
                     * With i flights
                     * 直达 ：dp[i][f[1]]
                     * 中转 ：dp[i - 1][f[0]] + f[2]， 假设在i个flights里，最后的一个flight(ith)是当前的f (f[0]到f[1],
                     *       costs f[2]).
                     *       dp[i - 1][f[0]] : min cost after the last flight (destination is the start point for
                     *                         current flight f)
                     */
                    dp[i][f[1]] = Math.min(dp[i][f[1]], dp[i - 1][f[0]] + f[2]);
                }
            }

            return dp[K + 1][dst] >= InfCost ? -1 : dp[K + 1][dst];
        }
    }
}
