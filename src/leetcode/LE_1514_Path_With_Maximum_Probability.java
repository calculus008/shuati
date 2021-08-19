package leetcode;

import java.util.*;

public class LE_1514_Path_With_Maximum_Probability {
    /**
     * You are given an undirected weighted graph of n nodes (0-indexed), represented by an edge list where edges[i] = [a, b]
     * is an undirected edge connecting the nodes a and b with a probability of success of traversing that edge succProb[i].
     *
     * Given two nodes start and end, find the path with the maximum probability of success to go from start to end and
     * return its success probability.
     *
     * If there is no path from start to end, return 0. Your answer will be accepted if it differs from the correct answer
     * by at most 1e-5.
     *
     * Example 1:
     * Input: n = 3, edges = [[0,1],[1,2],[0,2]], succProb = [0.5,0.5,0.2], start = 0, end = 2
     * Output: 0.25000
     * Explanation: There are two paths from start to end, one having a probability of success = 0.2 and the other has
     * 0.5 * 0.5 = 0.25.
     *
     * Example 2:
     * Input: n = 3, edges = [[0,1],[1,2],[0,2]], succProb = [0.5,0.5,0.3], start = 0, end = 2
     * Output: 0.30000
     *
     * Example 3:
     * Input: n = 3, edges = [[0,1]], succProb = [0.5], start = 0, end = 2
     * Output: 0.00000
     * Explanation: There is no path between 0 and 2.
     *
     * Constraints:
     * 2 <= n <= 10^4
     * 0 <= start, end < n
     * start != end
     * 0 <= a, b < n
     * a != b
     * 0 <= succProb.length == edges.length <= 2*10^4
     * 0 <= succProb[i] <= 1
     * There is at most one edge between every two nodes.
     *
     * Medium
     */

    /**
     * Good summary at:
     * https://leetcode.com/problems/path-with-maximum-probability/discuss/731767/JavaPython-3-2-codes%3A-Bellman-Ford-and-Dijkstra's-algorithm-w-brief-explanation-and-analysis.
     */

    /**
     * Bellman Ford Algorithm: BFS with Queue
     *
     * Time : O(V * E)
     * Space : O(v + E)
     * where E = edges.length, V = n
     *
     * 1.Markov Chain
     * 2.Simply BFS, at every stage, you need to remember 2 things: current node + current probability at this node
     * 3.One node can be reached from multiple paths, we cannot simply use a visited array or a set to avoid repeating paths.
     *   What we can do is to record "best probability so far for each node". Then add to queue for BFS only if: it can
     *   make a better prob for this current node.
     * 4.Here we only need to return the min probability to read end, so in BFS, we don't need to count steps, therefore
     *   no logic needed for looping through current size of the queue.
     *
     * The reason is BFS approach is an improved Bellman Ford Algorithm (https://en.wikipedia.org/wiki/Shortest_Path_Faster_Algorithm),
     * It is actually a DP solution with worst case V * E. DFS + memorize solution is more like DFS with prone(worst case is still exponential V^E).
     * You can think like this, if your DFS contains 100 steps, but the 2 step is update, all other 98 steps need to update again.
     */
    class Solution1 {
        class State {
            int node;
            double prob;

            public State(int node, double prob) {
                this.node = node;
                this.prob = prob;
            }
        }

        public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {
            //build graph
            Map<Integer, List<double[]>> graph = new HashMap<>();
            for (int i = 0; i < edges.length; i++) {
                int[] edge = edges[i];
                int a = edge[0];
                int b = edge[1];

                graph.putIfAbsent(a, new ArrayList<>());
                graph.putIfAbsent(b, new ArrayList<>());

                graph.get(edge[0]).add(new double[]{b, succProb[i]});
                graph.get(edge[1]).add(new double[]{a, succProb[i]});
            }

            /**
             * record the best (max) probability from ith node to reach end
             */
            double[] probs = new double[n];

            Queue<State> q = new LinkedList<>();
            q.offer(new State(start, 1.0));

            while (!q.isEmpty()) {
                State cur = q.poll();
                int curNode = cur.node;
                double curProb = cur.prob;

                for (double[] next : graph.getOrDefault(curNode, new ArrayList<>())) {
                    /**
                     * If current prob at the next node is bigger (better) than the one calculated from curNode to next,
                     * then we don't use this path, no need to add it to the queue.
                     */
                    int nextId = (int)next[0];
                    double newProb = next[1] * curProb;
                    if (probs[(int)next[0]] >= newProb) continue;

                    q.offer(new State(nextId, newProb));

                    /**
                     * !!!
                     * We have a better prob, update it. If we can't reach end after BFS, the value will remain 0.
                     * Since we just want to find the max prob among all paths from start to end, not to find a possible
                     * path from start to end, we don't need to check if we reach end in BFS loop.
                     */
                    probs[nextId] = newProb;
                }
            }

            return probs[end];
        }
    }

    /**
     * Same BFS algorithm without using extra State class
     * Instead of using State class, only put node ID into queue.
     * probs[] should have the best prob we need, we just need to reference to it with node ID.
     */
    class Solution2 {
        public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {
            Map<Integer, List<double[]>> graph = new HashMap<>();
            for (int i = 0; i < edges.length; i++) {
                int[] edge = edges[i];
                int a = edge[0];
                int b = edge[1];

                graph.putIfAbsent(a, new ArrayList<>());
                graph.putIfAbsent(b, new ArrayList<>());

                graph.get(edge[0]).add(new double[]{b, succProb[i]});
                graph.get(edge[1]).add(new double[]{a, succProb[i]});
            }

            double[] probs = new double[n];
            probs[start] = 1.0;
            Queue<Integer> q = new LinkedList<>();
            q.offer(start);//!!!

            while (!q.isEmpty()) {
                int cur = q.poll();
                /**
                 * !!! no need to use State class, the prob for a given node is recorded in probs[]
                 */
                double curProb = probs[cur];

                /**
                 * Smart use of "graph.getOrDefault(cur, new ArrayList<>())", so we don't need write extra line:
                 *    if (!graph.containsKey(cur)) continue;
                 */
                for (double[] next : graph.getOrDefault(cur, new ArrayList<>())) {
                    double newProb = next[1] * curProb;
                    int nextId = (int)next[0];

                    if (probs[nextId] >= newProb) continue;

                    q.offer(nextId);
                    probs[nextId] = newProb;
                }
            }

            return probs[end];
        }
    }

    /**
     * Dijkstra
     *
     * Use PriorityQueue instead of Queue as in Solution1.
     *
     * 1.Initialize all vertices probabilities as 0, except start, which is 1;
     * 2.Put all currently reachable vertices into a Priority Queue/heap, priority ordered by the corresponding current
     *   probability, REVERSELY;
     * 3.Whenever popped out a vertex with currently highest probability, check if it is the end vertex; If yes, we have
     *   already found the solution; otherwise, traverse all its neighbors to update neighbors' probabilities if necessary;
     *   NOTE: when forwarding one step, multiply the prob value of the next node with the prob of current node.
     * 4.Repeat 2 & 3 to find the max probability for end; If can NOT, return 0.0.
     *
     * Time : O(V + ElogV)
     * Space : O(V + E)
     * where E = edges.length, V = n
     */
    class Solution3 {
        class State {
            int node;
            double prob;

            public State(int node, double prob) {
                this.node = node;
                this.prob = prob;
            }
        }

        public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {
            Map<Integer, List<double[]>> graph = new HashMap<>();
            for (int i = 0; i < edges.length; i++) {
                int[] edge = edges[i];
                int a = edge[0];
                int b = edge[1];

                graph.putIfAbsent(a, new ArrayList<>());
                graph.putIfAbsent(b, new ArrayList<>());

                graph.get(edge[0]).add(new double[]{b, succProb[i]});
                graph.get(edge[1]).add(new double[]{a, succProb[i]});
            }

            double[] probs = new double[n];

            /**
             * max heap, the one with the largest prob is always at the top
             */
            PriorityQueue<State> pq = new PriorityQueue<>((a, b) -> Double.compare(b.prob, a.prob));
            pq.offer(new State(start, 1.0));

            while (!pq.isEmpty()) {
                State cur = pq.poll();
                int curId = cur.node;
                double curProb = cur.prob;

                /**
                 * !!!
                 * Just think it reversely. Largest prob == Shortest path. When you hit a node, it is definitely the largest
                 * prob for that (since we pop out the largest prob every time for calculation).
                 */
                if (curId == end) return curProb;

                for (double[] next : graph.getOrDefault(curId, new ArrayList<>())) {
                    int nextId = (int)next[0];
                    /**
                     * !!!
                     */
                    double newProb = curProb * next[1];

                    if (probs[nextId] >= newProb) continue;

                    pq.offer(new State(nextId, newProb));
                    probs[nextId] = newProb;
                }
            }

            /**
             * !!!
             */
            return 0.0;
        }
    }
}
