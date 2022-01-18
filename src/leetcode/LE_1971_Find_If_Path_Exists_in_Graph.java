package leetcode;

import java.util.*;

public class LE_1971_Find_If_Path_Exists_in_Graph {
    /**
     * There is a bi-directional graph with n vertices, where each vertex is labeled from 0 to n - 1 (inclusive).
     * The edges in the graph are represented as a 2D integer array edges, where each edges[i] = [ui, vi] denotes
     * a bi-directional edge between vertex ui and vertex vi. Every vertex pair is connected by at most one edge, and
     * no vertex has an edge to itself.
     *
     * You want to determine if there is a valid path that exists from vertex start to vertex end.
     *
     * Given edges and the integers n, start, and end, return true if there is a valid path from start to end, or false otherwise.
     *
     * Example 1:
     * Input: n = 3, edges = [[0,1],[1,2],[2,0]], start = 0, end = 2
     * Output: true
     * Explanation: There are two paths from vertex 0 to vertex 2:
     * - 0 → 1 → 2
     * - 0 → 2
     *
     * Example 2:
     * Input: n = 6, edges = [[0,1],[0,2],[3,5],[5,4],[4,3]], start = 0, end = 5
     * Output: false
     * Explanation: There is no path from vertex 0 to vertex 5.
     *
     * Constraints:
     * 1 <= n <= 2 * 105
     * 0 <= edges.length <= 2 * 105
     * edges[i].length == 2
     * 0 <= ui, vi <= n - 1
     * ui != vi
     * 0 <= start, end <= n - 1
     * There are no duplicate edges.
     * There are no self edges.
     *
     * Easy
     *
     * https://leetcode.com/problems/find-if-path-exists-in-graph/
     */

    class Solution_DFS {
        boolean res = false;

        public boolean validPath(int n, int[][] edges, int start, int end) {
            Map<Integer, List<Integer>> graph = new HashMap<>();

            for (int i = 0; i < edges.length; i++) {
                graph.computeIfAbsent(edges[i][0], l -> new ArrayList<>()).add(edges[i][1]);
                graph.computeIfAbsent(edges[i][1], l -> new ArrayList<>()).add(edges[i][0]);
            }

            dfs(graph, start, end, new HashSet<>());

            return res;
        }

        private void dfs(Map<Integer, List<Integer>> graph, int cur, int end, Set<Integer> visited) {
            if (cur == end) {
                res = true;
                return;
            }

            if (res || visited.contains(cur)) return;

            visited.add(cur);
            List<Integer> next = graph.getOrDefault(cur, new ArrayList<>());

            for (int n : next) {
                dfs(graph, n, end, visited);
            }
        }
    }
}
