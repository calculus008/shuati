package leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class LE_785_Is_Graph_Bipartite {
    /**
         Given an undirected graph, return true if and only if it is bipartite.

         Recall that a graph is bipartite if we can split it's set of nodes into
         two independent subsets A and B such that every edge in the graph has
         one node in A and another node in B.

         The graph is given in the following form: graph[i] is a list of indexes
         j for which the edge between nodes i and j exists.  Each node is an integer
         between 0 and graph.length - 1.  There are no self edges or parallel edges:
         graph[i] does not contain i, and it doesn't contain any element twice.

         Example 1:
         Input: [[1,3], [0,2], [1,3], [0,2]]
         Output: true
         Explanation:
         The graph looks like this:
         0----1
         |    |
         |    |
         3----2
         We can divide the vertices into two groups: {0, 2} and {1, 3}.

         Example 2:
         Input: [[1,2,3], [0,2], [0,1,3], [0,2]]
         Output: false
         Explanation:
         The graph looks like this:
         0----1
         | \  |
         |  \ |
         3----2
         We cannot find a way to divide the set of nodes into two independent subsets.

         Note:

         graph will have length in range [1, 100].
         graph[i] will contain integers in range [0, graph.length - 1].
         graph[i] will not contain i or duplicate values.
         The graph is undirected: if any element j is in graph[i], then i will be in graph[j].

         Medium

         Same as LE_886_Possible_Bipartition
     */

    /**
     * DFS coloring
     *
     * This solution goes without building a adjacent matrix (as LE_886_Possible_Bipartition does).
     * Because the difference with 886 is that the inputs are different, for this problem:
     *
     * "graph[i] is a list of indexes j for which the edge between nodes i and j exists."
     * graph[][] : there could be element like [[1,2,3,6], [5], [7,8]],
     * here, graph[0] = [1,2,3,6], it means:
     *                   0
     *                   |
     *              _____|_____
     *             |  |     |  |
     *             1  2     3  6
     *
     * So we already have the graph in input.
     *
     * In 886, it is strictly [[x, y], [x1, y1],...], each row just have 2 elements and they dislike each other.
     * So we must build an adjacent matrix (build the graph)
     */
    class SolutionDFS {
        public boolean isBipartite(int[][] graph) {
            int n = graph.length;
            int[] group = new int[n];

            for (int i = 0; i < n; i++) {
                if (group[i] == 0 && !helper(graph, group, i, 1)) {
                    return false;
                }
            }

            return true;
        }

        private boolean helper(int[][] graph, int[] group, int idx, int c) {
            if (group[idx] != 0) {
                return group[idx] == c;
            }

            group[idx] = c;

            for (int node : graph[idx]) {
                if (!helper(graph, group, node, -c)) {
                    return false;
                }
            }

            return true;
        }
    }

    class SolutionBFS {
        public boolean isBipartite(int[][] graph) {
            int len = graph.length;
            int[] colors = new int[len];

            for (int i = 0; i < len; i++) {
                if (colors[i] != 0) {
                    continue;
                }

                Queue<Integer> queue = new LinkedList<>();
                queue.offer(i);
                colors[i] = 1;   // Blue: 1; Red: -1.

                while (!queue.isEmpty()) {
                    int cur = queue.poll();
                    for (int next : graph[cur]) {
                        if (colors[next] == 0) {          // If this node hasn't been colored;
                            colors[next] = -colors[cur];  // Color it with a different color;
                            queue.offer(next);
                        } else if (colors[next] != -colors[cur]) {   // If it is colored and its color is different, return false;
                            return false;
                        }
                    }
                }
            }

            return true;
        }
    }
}