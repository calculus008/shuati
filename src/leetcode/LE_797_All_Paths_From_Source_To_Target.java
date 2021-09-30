package leetcode;

import java.util.*;

public class LE_797_All_Paths_From_Source_To_Target {
    /**
     * Given a directed acyclic graph (DAG) of n nodes labeled from 0 to n - 1, find all possible paths from node 0 to
     * node n - 1 and return them in any order.
     *
     * The graph is given as follows: graph[i] is a list of all nodes you can visit from node i (i.e., there is a
     * directed edge from node i to node graph[i][j]).
     *
     * Example 1:
     * Input: graph = [[1,2],[3],[3],[]]
     * Output: [[0,1,3],[0,2,3]]
     * Explanation: There are two paths: 0 -> 1 -> 3 and 0 -> 2 -> 3.
     *
     * Example 2:
     * Input: graph = [[4,3,1],[3,2,4],[3],[4],[]]
     * Output: [[0,4],[0,3,4],[0,1,3,4],[0,1,2,3,4],[0,1,4]]
     *
     * Example 3:
     * Input: graph = [[1],[]]
     * Output: [[0,1]]
     *
     * Example 4:
     * Input: graph = [[1,2,3],[2],[3],[]]
     * Output: [[0,1,2,3],[0,2,3],[0,3]]
     *
     * Example 5:
     * Input: graph = [[1,3],[2],[3],[]]
     * Output: [[0,1,2,3],[0,3]]
     *
     * Constraints:
     * n == graph.length
     * 2 <= n <= 15
     * 0 <= graph[i][j] < n
     * graph[i][j] != i (i.e., there will be no self-loops).
     * All the elements of graph[i] are unique.
     * The input graph is guaranteed to be a DAG.
     *
     * Medium
     *
     * https://leetcode.com/problems/all-paths-from-source-to-target/
     */

    /**
     * DFS Backtracking
     *
     * No "visited" set required. Its an "Acyclic" graph, so there is no way you can go around in circles. Also a given
     * node can be visited multiple times through different paths.
     *
     * Complexity Analysis:
     * https://leetcode.com/problems/all-paths-from-source-to-target/solution/
     *
     * Time  : O(n * 2 ^ n)
     * "
     * If we continue to add nodes to the graph, one insight is that every time we add a new node into the graph,
     * the number of paths would double. As a result, for a graph with NN nodes, at maximum, there could be
     * SUM(2 ^ i), 0 <= i < n - 1 ==> 2 ^ (n - 1) - 1
     * of paths between the starting and the ending nodes.
     * ...
     * 1.As we calculate shortly before, there could be at most 2^(N-1) - 1 possible paths in the graph.
     * 2.For each path, there could be at most N-2 intermediate nodes, i.e. it takes O(N) time to build a path.
     * "
     *
     * Space : O(n * 2 ^ n)
     * "
     * 1.Similarly, since at most we could have 2^(n - 1) paths as the results and each path can contain up to N nodes,
     *   the space we need to store the results would be O(n * 2 ^ n)
     * 2.Since we also applied recursion in the algorithm, the recursion could incur additional memory consumption in the
     *   function call stack. The stack can grow up to NN consecutive calls. Meanwhile, along with the recursive call,
     *   we also keep the state of the current path, which could take another O(N) space. Therefore, in total, the recursion
     *   would require additional O(n) space.
     * To sum up, the space complexity of the algorithm is :
     *   O(n * 2 ^ n) + o(n) = O(n * 2 ^ n)
     * "
     */

    /**
     * add/remove node to path before going into next level of recursion, not in current recursion level
     */
    class Solution1 {
        List<List<Integer>> res;

        public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
            res = new ArrayList<>();

            List<Integer> path = new ArrayList<>();
            path.add(0);
            dfs(0, graph, path);

            return res;
        }

        private void dfs(int node, int[][] graph, List<Integer> path) {
            if (node == graph.length - 1) {
                res.add(new ArrayList<>(path));
                return;
            }

            for (int i = 0; i < graph[node].length; i++) {
                int next = graph[node][i];
                path.add(next);
                dfs(next, graph, path);
                path.remove(path.size() - 1);
            }
        }
    }

    /**
     * add/remove node in current recursion level.
     */
    class Solution {
        List<List<Integer>> res;

        public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
            res = new ArrayList<>();

            dfs(0, graph, new ArrayList<>());

            return res;
        }

        private void dfs(int node, int[][] graph, List<Integer> path) {
            if (node == graph.length - 1) {
                path.add(node);
                res.add(new ArrayList<>(path));
                path.remove(path.size() - 1);
                return;
            }

            path.add(node);
            for (int i = 0; i < graph[node].length; i++) {
                int next = graph[node][i];
                dfs(next, graph, path);
            }
            path.remove(path.size() - 1);
        }
    }
}
