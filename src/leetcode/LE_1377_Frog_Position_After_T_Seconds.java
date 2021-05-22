package leetcode;

import java.util.*;

public class LE_1377_Frog_Position_After_T_Seconds {
    /**
     * Given an undirected tree consisting of n vertices numbered from 1 to n. A frog starts jumping from vertex 1.
     * In one second, the frog jumps from its current vertex to another unvisited vertex if they are directly connected.
     * The frog can not jump back to a visited vertex. In case the frog can jump to several vertices, it jumps randomly
     * to one of them with the same probability. Otherwise, when the frog can not jump to any unvisited vertex, it jumps
     * forever on the same vertex.
     *
     * The edges of the undirected tree are given in the array edges, where edges[i] = [ai, bi] means that exists an edge
     * connecting the vertices ai and bi.
     *
     * Return the probability that after t seconds the frog is on the vertex target.
     *
     * Example 1:
     * Input: n = 7, edges = [[1,2],[1,3],[1,7],[2,4],[2,6],[3,5]], t = 2, target = 4
     * Output: 0.16666666666666666
     * Explanation: The figure above shows the given graph. The frog starts at vertex 1, jumping with 1/3 probability to
     * the vertex 2 after second 1 and then jumping with 1/2 probability to vertex 4 after second 2. Thus the probability
     * for the frog is on the vertex 4 after 2 seconds is 1/3 * 1/2 = 1/6 = 0.16666666666666666.
     *
     * Example 2:
     * Input: n = 7, edges = [[1,2],[1,3],[1,7],[2,4],[2,6],[3,5]], t = 1, target = 7
     * Output: 0.3333333333333333
     * Explanation: The figure above shows the given graph. The frog starts at vertex 1, jumping with 1/3 =
     * 0.3333333333333333 probability to the vertex 7 after second 1.
     *
     * Example 3:
     * Input: n = 7, edges = [[1,2],[1,3],[1,7],[2,4],[2,6],[3,5]], t = 20, target = 6
     * Output: 0.16666666666666666
     *
     * Constraints:
     * 1 <= n <= 100
     * edges.length == n - 1
     * edges[i].length == 2
     * 1 <= ai, bi <= n
     * 1 <= t <= 50
     * 1 <= target <= n
     * Answers within 10-5 of the actual value will be accepted as correct.
     *
     * Hard
     */

    /**
     * BFS
     *
     * Several mind turns:
     * 1.From simulate how to solve those examples, we get the idea that (since it is a tree) we should go level by
     *   level, hence BFS, it is the easy part.
     * 2.Start to apply BFS template, a very easy mistake here : when we calculate the probability for a node, it should
     *   be 1 / (number of children of the node in the next level), it is NOT 1 / (current size of the q). Current size
     *   of the q is the total number of nodes in current level.
     * 3.The tough part : how do we track the probability when we go from level to level?
     *   Use an array, dynamically update the prob value for a given node when we do BFS. So when we go x steps, prob[n]
     *   is the probability that the frog stays on node n after x steps.
     *   !!! "when the frog can not jump to any unvisited vertex, it jumps forever on the same vertex":
     *   If the current node x still has unvisited child, it won't stay at the current node x, so we should update
     *   prob[x] to 0, which means it has 0 chance to stay at x.
     */
    class Solution {
        public double frogPosition(int n, int[][] edges, int t, int target) {
            Map<Integer, List<Integer>> graph = new HashMap<>();
            for (int[] edge : edges) {
                graph.computeIfAbsent(edge[0], l -> new ArrayList<>()).add(edge[1]);
                graph.computeIfAbsent(edge[1], l -> new ArrayList<>()).add(edge[0]);
            }

            Queue<Integer> q = new LinkedList<>();
            q.offer(1);

            /**
             * Node id starts from 1, so we choose to init array with size n + 1, then we can directly use node id as
             * index without doing minus 1 to translate it to 0-based index.
             */
            boolean[] visited = new boolean[n + 1];
            double[] prob = new double[n + 1];
            visited[1] = true;
            prob[1] = 1f;

            int steps = 0;
            while (!q.isEmpty() && steps != t) {
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    int cur = q.poll();

                    /**
                     * !!!
                     * For leave nodes. Must have this check, otherwise will run into null pointer exception
                     */
                    if (!graph.containsKey(cur)) continue;

                    /**
                     * optimization
                     * We can use the for loop to find the number of children for current node.
                     * Since it's defined as a tree, so the number of children can simply be :
                     * number of its neighbours if it is root node
                     * number of its neighbours - 1 (its parent) if it is a not a root node
                     *
                     * Thus we save the time in for loop
                     */
//                    int next = 0;
//                    for (int node : graph.get(cur)) {
//                        if (!visited[node]) next++;
//                    }
                    int count = graph.get(cur).size();
                    int next = cur == 1 ? count : count - 1;

                    for (int node : graph.get(cur)) {
                        if (visited[node]) continue;
                        q.offer(node);
                        visited[node] = true;
                        prob[node] = (double)prob[cur] / next;
                    }

                    /**
                     * !!!
                     */
                    if (next > 0) prob[cur] = 0;

                    /**
                     * optimization : early exist. This is for case that we get to target node before step t, so
                     * simply break because we know the prob will be 0.
                     */
                    if (cur == target) break;
                }

                steps++;
            }

            return prob[target];
        }
    }

    /**
     * Solution from leetcode
     * https://leetcode.com/problems/frog-position-after-t-seconds/discuss/532505/Java-Straightforward-BFS-Clean-code-O(N)
     *
     * Same algorithm, use array of list as graph.
     */
    class Solution_leetcode {
        public double frogPosition(int n, int[][] edges, int t, int target) {
            List<Integer>[] graph = new List[n + 1];
            for (int i = 0; i < n; i++) {
                graph[i] = new ArrayList<>();
            }
            for (int[] e : edges) {
                graph[e[0]].add(e[1]);
                graph[e[1]].add(e[0]);
            }

            boolean[] visited = new boolean[n + 1];
            visited[1] = true;
            double[] prob = new double[n + 1];
            prob[1] = 1f;

            Queue<Integer> q = new LinkedList<>(); q.offer(0);
            while (!q.isEmpty() && t-- > 0) {
                for (int size = q.size(); size > 0; size--) {
                    int cur = q.poll();

                    int nextVerticesCount = 0;
                    for (int v : graph[cur]) {
                        if (!visited[v]) nextVerticesCount++;
                    }
                    for (int v : graph[cur]) {
                        if (!visited[v]) {
                            visited[v] = true;
                            q.offer(v);
                            prob[v] = prob[cur] / nextVerticesCount;
                        }
                    }
                    if (nextVerticesCount > 0) prob[cur] = 0;
                }
            }
            return prob[target];
        }
    }
}
