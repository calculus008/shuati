package Interviews.Apple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Find_Cycle {
    /**
     * 给了两个pseudo code API描述有向图，找出图中是否存在环。
     *
     * LE_261_Graph_Valid_Tree
     *
     * Undirected Graph
     *
     *         boolean hasCycle(List<List<Integer>> adjList, int u, boolean[] visited, int parent) {
     *             visited[u] = true;
     *
     *             for (int i = 0; i < adjList.get(u).size(); i++) {
     *                 int v = adjList.get(u).get(i);
     *
     *                 if ((visited[v] && parent != v) || (!visited[v] && hasCycle(adjList, v, visited, u)))
     *                     return true;
     *             }
     *
     *             return false;
     *         }
     *
     *
     * LE_802_Find_Eventual_Safe_States
     */

    class Solution_Practice {
        final int UNKOWN = 0;
        final int VISITING = 1;
        final int UNSAFE = 2; //has cycle
        final int SAFE = 3; //visited

        public List<Integer> eventualSafeNodes(int[][] graph) {
            List<Integer> res = new ArrayList<>();
            if (graph == null || graph.length == 0) return res;

            int[] status = new int[graph.length];

            for (int i = 0; i < graph.length; i++) {
                if (dfs(graph, status, i) == SAFE) {
                    res.add(i);
                }
            }

            return res;
        }

        private int dfs(int[][] graph, int[] status, int id) {
            if (status[id] == VISITING) {
                status[id] = UNSAFE;
                return UNSAFE;
            }

            if (status[id] != UNKOWN) {
                return status[id];
            }

            /**
             * !!! !!!
             * Don't forget to set visiting status
             * otherwise, it will stack overflow
             */
            status[id] = VISITING;
            for (int n : graph[id]) {
                if (dfs(graph, status, n) == UNSAFE) {
                    status[n] = UNSAFE;
                    return UNSAFE;
                }
            }

            status[id] = SAFE;
            return SAFE;
        }

        /**
         * Return true if node with given id is in a cycle.
         *
         * Use HashMap as mem to record status of each node:
         * ViSITING
         * SAFE       not in a cycle
         * UNSAFE     in a cycle
         */
        private boolean hasCycle(int[][] graph, Map<Integer, Integer> status, int id) {
            if (status.get(id) == VISITING) {
                return true;
            }

            if (status.containsKey(id)) {
                return status.get(id) == UNSAFE ? true : false;
            }

            status.put(id, VISITING);
            for (int n : graph[id]) {
                if (hasCycle(graph, status, n)) {
                    status.put(n, UNSAFE);
                    return true;
                }
            }

            status.put(id, SAFE);
            return false;
        }
    }

}
