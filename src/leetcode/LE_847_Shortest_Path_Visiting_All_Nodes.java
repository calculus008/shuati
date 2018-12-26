package leetcode;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class LE_847_Shortest_Path_Visiting_All_Nodes {
    /**
     An undirected, connected graph of N nodes (labeled 0, 1, 2, ..., N-1) is given as graph.

     graph.length = N, and j != i is in the list graph[i] exactly once, if and only if nodes i and j are connected.

     Return the length of the shortest path that visits every node. You may start and stop at any node,
     you may revisit nodes multiple times, and you may reuse edges.

     Example 1:

     Input: [[1,2,3],[0],[0],[0]]
     Output: 4
     Explanation: One possible path is [1,0,2,0,3]

     Example 2:

     Input: [[1],[0,2,4],[1,3,4],[2],[1,2]]
     Output: 4
     Explanation: One possible path is [0,1,4,2,3]

     Hard
     **/

    /**
     * Huahua's version
     * http://zxi.mytechroad.com/blog/graph/leetcode-847-shortest-path-visiting-all-nodes/
     * Time  : O(n * 2 ^ n)
     * Space : O(n * 2 ^ n)
     */

    class Solution1 {
        class Pair {
            int cur;//current node index
            int state;

            public Pair(int c, int s) {
                cur = c;
                state = s;
            }
        }

        public int shortestPathLength(int[][] graph) {
            int target = (1 << graph.length) - 1;
            Queue<Pair> q = new LinkedList<>();
            Set<Integer> visited = new HashSet<>();

            for (int i = 0; i < graph.length; ++i) {
                q.offer(new Pair(i, 1 << i));
            }

            int steps = 0;
            while (!q.isEmpty()) {
                int s = q.size();

                for (int i = 0; i < s; i++) {
                    Pair p = q.poll();
                    int n = p.cur;
                    int state = p.state;

                    if (state == target) {
                        return steps;
                    }

                    int key = (n << 16) | state;
                    if (visited.contains(key)) {//prevent duplicate path
                        continue;
                    }

                    visited.add(key);
                    for (int next : graph[n])
                        q.offer(new Pair(next, state | (1 << next)));
                }
                ++steps;
            }

            return -1;
        }
    }

    /**
     * Leetcode version
     * https://leetcode.com/problems/shortest-path-visiting-all-nodes/discuss/135809/Fast-BFS-Solution-(46ms)-Clear-Detailed-Explanation-Included
     * <p>
     * BFS
     * <p>
     * Time  : O(n*2^n)
     * Space : O(n*2^n)
     */

    class Solution2 {

        public int shortestPathLength(int[][] graph) {

            int N = graph.length;

            Queue<Tuple> queue = new LinkedList<>();
            Set<Tuple> set = new HashSet<>();

            /**
             * We initialize our queue to contain N possible paths each starting from [0,N-1].
             * This is because we can start at any of N possible Nodes.
             */
            for (int i = 0; i < N; i++) {
                int tmp = (1 << i);
                set.add(new Tuple(tmp, i, 0));
                queue.add(new Tuple(tmp, i, 1));
            }

            while (!queue.isEmpty()) {
                Tuple curr = queue.remove();

                /**
                 * At each step, we remove element from the queue and see if we have covered all 12 nodes in our bitMask.
                 * If we cover all nodes, we return the cost of the path immediately. Since we are using BFS,
                 * this is guranteed to be path with the lowest cost.
                 */
                if (curr.bitMask == (1 << N) - 1) {
                    return curr.cost - 1;//!!!
                } else {
                    int[] neighbors = graph[curr.curr];

                    for (int v : neighbors) {
                        int bitMask = curr.bitMask;
                        bitMask = bitMask | (1 << v);

                        /**
                         * In order to prevent duplicate paths from being visited, we use a Set<Tuple>
                         * to store the Set<Path> that we have visited before. Since we don't really
                         * need the cost here, I set cost to 0 for elements stored in Set. You could
                         * also set the actual cost value here, it wouldn't make a difference.
                         */
                        Tuple t = new Tuple(bitMask, v, 0);
                        if (!set.contains(t)) {
                            queue.add(new Tuple(bitMask, v, curr.cost + 1));
                            set.add(t);
                        }
                    }
                }
            }
            return -1;
        }
    }

    /**
     * In order to represent a path, I used a combination of 3 variables:
     *
     * int bitMask:
     * mask of all the nodes we visited so far: 0 -> not visited,
     * 1 -> visited (Originally this was Set<Integer>of all nodes we visited so far,
     * but since the Solution TLE and N <= 12, it turns out we can use a bitMask and 32 bits
     * total in an Integer can cover all the possibilities. This is a small speed up optimization.)
     *
     * int curr:
     * current node we are on
     *
     * int cost:
     * the total cost in the path.
     *
     * Each path taken will have a unique combination of these 3 variables.
     */
    class Tuple {
        int bitMask;
        int curr;
        int cost;

        public Tuple(int bit, int n, int c) {
            bitMask = bit;
            curr = n;
            cost = c;
        }

        public boolean equals(Object o) {//!!!
            Tuple p = (Tuple) o;
            return bitMask == p.bitMask && curr == p.curr && cost == p.cost;
        }

        public int hashCode() {//!!!
            return 1331 * bitMask + 7193 * curr + 727 * cost;
        }
    }
}