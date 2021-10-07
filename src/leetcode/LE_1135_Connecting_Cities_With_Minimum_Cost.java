package leetcode;

import java.util.*;

public class LE_1135_Connecting_Cities_With_Minimum_Cost {
    /**
     * There are n cities labeled from 1 to n. You are given the integer n and an array connections where connections[i]
     * = [xi, yi, costi] indicates that the cost of connecting city xi and city yi (bidirectional connection) is costi.
     *
     * Return the minimum cost to connect all the n cities such that there is at least one path between each pair of
     * cities. If it is impossible to connect all the n cities, return -1,
     *
     * The cost is the sum of the connections' costs used.
     *
     * Example 1:
     * Input: n = 3, connections = [[1,2,5],[1,3,6],[2,3,1]]
     * Output: 6
     * Explanation: Choosing any 2 edges will connect all cities so we choose the minimum 2.
     *
     * Example 2:
     * Input: n = 4, connections = [[1,2,3],[3,4,4]]
     * Output: -1
     * Explanation: There is no way to connect all cities even if all edges are used.
     *
     * Constraints:
     * 1 <= n <= 104
     * 1 <= connections.length <= 104
     * connections[i].length == 3
     * 1 <= xi, yi <= n
     * xi != yi
     * 0 <= costi <= 105
     *
     * Medium
     *
     * https://leetcode.com/problems/connecting-cities-with-minimum-cost/
     */

    /**
     * Prim's algorithm
     * https://en.wikipedia.org/wiki/Prim%27s_algorithm#Time_complexity
     *
     * Prim's algorithm (also known as Jarník's algorithm) is a greedy algorithm that finds a minimum spanning tree for
     * a weighted undirected graph.
     * A minimum spanning tree (MST) or minimum weight spanning tree is a subset of the edges of a connected, edge-weighted
     * undirected graph that connects all the vertices together, without any cycles and with the minimum possible total edge
     * weight. That is, it is a spanning tree whose sum of edge weights is as small as possible.
     *
     * Time  : O(ElogE), can be reduced to O(ElogV) by using binary heap.
     */
    class Solution1 {
        public int minimumCost(int n, int[][] connections) {
            Map<Integer, List<int[]>> graph = new HashMap<>();
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[2], b[2]));
            Set<Integer> visited = new HashSet<>();

            for (int [] conn : connections) {
                int n1 = conn[0];
                int n2 = conn[1];
                int cost = conn[2];

                graph.computeIfAbsent(n1, l -> new ArrayList<>());
                graph.computeIfAbsent(n2, l -> new ArrayList<>());
                graph.get(n1).add(new int[] {n2, cost});
                graph.get(n2).add(new int[] {n1, cost});
            }

            /**
             * !!!
             * Starting point
             */
            pq.offer(new int[]{1, 1, 0});
            int res = 0;

            while (!pq.isEmpty()) {
                int[] cur = pq.poll();
                int n1 = cur[0];
                int n2 = cur[1];
                int cost = cur[2];

                if (visited.contains(n2)) continue;

                res += cost;
                visited.add(n2);

                if (visited.size() == n) return res;

                for (int[] next : graph.get(n2)) {
                    pq.offer(new int[]{n2, next[0], next[1]});
                }
            }

            return visited.size() == n ? res : -1;
        }
    }

    /**
     * Kruskal’s algorithm (Union Find)
     * https://en.wikipedia.org/wiki/Kruskal%27s_algorithm
     *
     * 1.Sort edges to no-decreasing order
     * 2.Pick the smallest edge that does not form a cycle
     * 3.Repeat until MST is formed and every node is connected.
     *
     * Time : O(ElogV)
     */
    class Solution2 {
        int[] parent;
        int n;

        private void union(int x, int y) {
            int px = find(x);
            int py = find(y);

            if (px != py) {
                parent[px] = py;
                n--;
            }
        }

        private int find(int x) {
            if (parent[x] == x) {
                return parent[x];
            }
            parent[x] = find(parent[x]); // path compression
            return parent[x];
        }

        public int minimumCost(int N, int[][] connections) {
            parent = new int[N + 1];
            n = N;

            for (int i = 0; i <= N; i++) {
                parent[i] = i;
            }

            /**
             * sort on cost
             */
            Arrays.sort(connections, (a, b) -> (a[2] - b[2]));

            int res = 0;

            for (int[] c : connections) {
                int x = c[0], y = c[1];
                if (find(x) != find(y)) {
                    res += c[2];
                    union(x, y);
                }
            }

            return n == 1 ? res : -1;
        }
    }
}
