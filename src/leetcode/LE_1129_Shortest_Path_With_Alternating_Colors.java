package leetcode;

import java.util.*;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;

public class LE_1129_Shortest_Path_With_Alternating_Colors {
    /**
     * You are given an integer n, the number of nodes in a directed graph where the nodes are labeled from 0 to n - 1.
     * Each edge is red or blue in this graph, and there could be self-edges and parallel edges.
     *
     * You are given two arrays redEdges and blueEdges where:
     *
     * redEdges[i] = [ai, bi] indicates that there is a directed red edge from node ai to node bi in the graph, and
     * blueEdges[j] = [uj, vj] indicates that there is a directed blue edge from node uj to node vj in the graph.
     * Return an array answer of length n, where each answer[x] is the length of the shortest path from node 0 to node
     * x such that the edge colors alternate along the path, or -1 if such a path does not exist.
     *
     * Example 1:
     * Input: n = 3, redEdges = [[0,1],[1,2]], blueEdges = []
     * Output: [0,1,-1]
     *
     * Example 2:
     * Input: n = 3, redEdges = [[0,1]], blueEdges = [[2,1]]
     * Output: [0,1,-1]
     *
     * Constraints:
     * 1 <= n <= 100
     * 0 <= redEdges.length, blueEdges.length <= 400
     * redEdges[i].length == blueEdges[j].length == 2
     * 0 <= ai, bi, uj, vj < n
     *
     * Medium
     *
     * https://leetcode.com/problems/shortest-path-with-alternating-colors/
     */

    /**
     * "...is the length of the shortest path.." -> BFS
     *
     * A very good variation of basic BFS. It requires alternate paths (blue and red). Therefore:
     * 1.The state we keep in queue needs to include the color of the path. So the state is a pair:
     *   <Node Id, color of the path that leads to this node>
     * 2.As all BFS, we have queue and visited set. For queue, we start with <0, 0> and <0, 1>:
     *   First element '0' represents starting node '0'.
     *   Second element in pair represents color, '0' - red, '1' - blue
     * 3.Use Math.min() to calculate steps when getting cur pair out of the queue, therefore we want
     *   to init res[] with Integer.MAX_VALUE
     * 4.Use java.util.AbstractMap.SimpleEntry as Pair class
     * 5.When processing next Pair, only add to queue if it is not visited.
     * 6.In the end, iterate res[] to set -1 to unreachable nodes.
     */
    class Solution {
        public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
            Map<Integer, List<Integer>> red = new HashMap<>();
            for (int[] e : redEdges) {
                red.putIfAbsent(e[0], new ArrayList<>());
                red.get(e[0]).add(e[1]);
            }

            Map<Integer, List<Integer>> blue = new HashMap<>();
            for (int[] e : blueEdges) {
                blue.putIfAbsent(e[0], new ArrayList<>());
                blue.get(e[0]).add(e[1]);
            }

            int[] res = new int[n];
            Arrays.fill(res, Integer.MAX_VALUE);

            Queue<SimpleEntry<Integer, Integer>> q = new LinkedList<>();
            q.offer(new SimpleEntry(0, 0));
            q.offer(new SimpleEntry(0, 1));

            Set<SimpleEntry<Integer, Integer>> visited = new HashSet<>();

            int steps = 0;

            while (!q.isEmpty()) {
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    SimpleEntry<Integer, Integer> cur = q.poll();
                    visited.add(cur);
                    int curId = cur.getKey();
                    int color = cur.getValue();

                    /**
                     * !!!
                     * Calculate answer when we get the pair out of queue
                     */
                    res[curId] = Math.min(res[curId], steps);

                    if (color == 0) {
                        for (int nextId : blue.getOrDefault(curId, new ArrayList<>())) {
                            SimpleEntry<Integer, Integer> next = new SimpleEntry(nextId, 1);
                            /**
                             * !!!
                             * Only add to queue if the state is not visited
                             */
                            if (!visited.contains(next)) {
                                q.offer(next);
                            }
                        }
                    } else {
                        for (int nextId : red.getOrDefault(curId, new ArrayList<>())) {
                            SimpleEntry<Integer, Integer> next = new SimpleEntry(nextId, 0);
                            if (!visited.contains(next)) {
                                q.offer(next);
                            }
                        }
                    }
                }

                steps++;
            }

            /**
             * !!!
             * Deal with unreachable node, whose result is till MAX_VALUE
             */
            for (int i = 0; i < res.length; i++) {
                if (res[i] == Integer.MAX_VALUE) res[i] = -1;
            }
            return res;
        }
    }
}
