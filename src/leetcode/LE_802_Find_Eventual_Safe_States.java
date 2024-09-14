package leetcode;

import java.util.ArrayList;
import java.util.List;

public class LE_802_Find_Eventual_Safe_States {
    /**
         In a directed graph, we start at some node and every turn, walk along a directed edge of the graph.
         If we reach a node that is terminal (that is, it has no outgoing directed edges), we stop.

         Now, say our starting node is eventually safe if and only if we must eventually walk to a terminal
         node.  More specifically, there exists a natural number K so that for any choice of where to walk,
         we must have stopped at a terminal node in less than K steps.

         Which nodes are eventually safe?  Return them as an array in sorted order.

         The directed graph has N nodes with labels 0, 1, ..., N-1, where N is the length of graph.
         The graph is given in the following form: graph[i] is a list of labels j such that (i, j)
         is a directed edge of the graph.

         Example:
         Input: graph = [[1,2],[2,3],[5],[0],[5],[],[]]
         Output: [2,4,5,6]
         Here is a diagram of the above graph.

         Illustration of graph

         Note:

         graph will have length at most 10000.
         The number of edges in the graph will not exceed 32000.
         Each graph[i] will be a sorted list of different integers, chosen within the range [0, graph.length - 1].

         Medium

         https://leetcode.com/problems/find-eventual-safe-states
     */

    class Solution_Practice {
        final int UNKOWN = 0;
        final int VISITING = 1;
        final int UNSAFE = 2;
        final int SAFE = 3;

        public List<Integer> eventualSafeNodes(int[][] graph) {
            List<Integer> res = new ArrayList<>();
            if (graph == null || graph.length == 0) return res;

            /**
             * !!!
             * why use status array?
             * What the problem asks as return is a list of node id which are SAFE, in sorted order.
             * It's not asking to return status for all nodes. So we use status[] to record the status
             * of all nodes during dfs(), only when it is SAFE,we add the id into result.
             *
             * Also the for loop guaranteed the ids will be in sorted order in result list.
             *
             */
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

            if (status[id] != UNKOWN) {// find value in mem
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
    }

    /**
     * http://zxi.mytechroad.com/blog/graph/leetcode-802-find-eventual-safe-states/
     *
     * 实际上是要在给定的graph中找cycle.
     * 所有loop里面和可能走到loop里面的node都是UNSAFE的。
     *
     * Input graph[][] is already an adjacent list (graph), no need to build graph.
     * Use every node as starting point, DFS the whole graph.
     *
     * 特殊的地方在于，dfs() returns the status of the starting node. It's basically
     * DFS + Memoization. "Status[]" acts as memoization.
     *
     * Time  : O(V + E)
     * Space : O(V + E)
     *
     */
    class Solution {
        public List<Integer> eventualSafeNodes(int[][] graph) {
            int n = graph.length;
            int[] status = new int[n];
            List<Integer> res = new ArrayList<>();

            /**
             * 0:UNKOWN, 1:VISITING, 2:SAFE, 3:UNSAFE
             **/
            for (int i = 0; i < n; i++) {
                if (dfs(graph, status, i) == 2) {
                    res.add(i);
                }
            }

            return res;
        }

        private int dfs(int[][] graph, int[] status, int id) {
            /**
             * If we run into a node in VISITING state, meaning we see
             * a node that is seen previously in current DFS traversal,
             * we are in a cycle.!!!
             *
             * Based on problem def, all nodes that can go into a cycle
             * is in UNSAFE state.
             */
            if (status[id] == 1) {//1
                /**
                 * !!! Don't forget to set the state before return
                 */
                status[id] = 3;
                return 3;
            }

            if (status[id] != 0) {//2, 3
                return status[id];
            }

            //0
            status[id] = 1;//!!!
            for (int next : graph[id]) {
                if (dfs(graph, status, next) == 3) {
                    /**
                     * !!! Don't forget to set the state before return
                     */
                    status[id] = 3;
                    return 3;
                }
            }

            /**
             * !!! Don't for get to set the state before return
             */
            status[id] = 2;
            return 2;
        }
    }


}