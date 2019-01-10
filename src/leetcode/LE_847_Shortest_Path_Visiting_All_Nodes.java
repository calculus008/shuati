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

     Note:

     1 <= graph.length <= 12
     0 <= graph[i].length < graph.length

     Hard
     **/

    /**
     * Huahua's version
     * http://zxi.mytechroad.com/blog/graph/leetcode-847-shortest-path-visiting-all-nodes/
     * Time  : O(n * 2 ^ n)
     * Space : O(n * 2 ^ n)
     *
     * Keys
     * Shortest path -> BFS, but it has some important variations for this problem :
     *
     * 1. As problem requires - "you may revisit nodes multiple times, and you may reuse edges",
     *    it means you may revisit the same node many times. Therefore we can't just put a node
     *    into visited[] and filter it. visited should capture the unique state of the BFS traversal,
     *    which has two elements - current node id and all nodes that have been visited by now.
     *    When this unique state re-appears in BFS, we can tell now we come back to a state that already
     *    happened before (visiting the same node and with the same nodes that have been visited),
     *    so we don't need to continue.
     *
     * 2. Therefore, we use class Pair to save the unique state. Normally, for visited nodes, we can
     *    use a set, here, since we know, "1 <= graph.length <= 12", we can use the trick of bit operation
     *    to represent visited node in a single int value.
     *
     * 3. Another bit operation trick : for visited set, we combine node id and visited integer into a single
     *    Integer and use it as the key in the set.
     *
     * 4. Pop each Pair out of queue, first process, then expand to next level. In other words, all processing
     *    of Pair happens after it is popped out of the queue.
     *
     * 5. Init queue by put all nodes into the queue, since we can start any of those nodes.
     *    Similar to LE_934_Shortest_Bridge
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

                    //!!! #3
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
     *
     * BFS
     *
     * Time  : O(n * 2 ^ n)
     * Space : O(n * 2 ^ n)
     *
     * This solution uses the same BFS algorithm as Solution1, difference :
     * 1.Keep steps in Tuple class, instead of maintaining "steps" variable.
     * 2.Notice we must implement equal() and hashCode() in class Tuple
     */

    class Solution2 {

        public int shortestPathLength(int[][] graph) {

            int N = graph.length;
            int target = (1 << N) - 1;

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
                 * At each step, we remove element from the queue and see if we have covered all 12 nodes in our state.
                 * If we cover all nodes, we return the cost of the path immediately. Since we are using BFS,
                 * this is guaranteed to be path with the lowest cost.
                 */
                if (curr.state == target) {
                    return curr.cost - 1;//!!!
                } else {
                    int[] neighbors = graph[curr.curr];

                    for (int v : neighbors) {
                        int state = curr.state;
                        state = state | (1 << v);

                        /**
                         * In order to prevent duplicate paths from being visited, we use a Set<Tuple>
                         * to store the Set<Path> that we have visited before. Since we don't really
                         * need the cost here, I set cost to 0 for elements stored in Set. You could
                         * also set the actual cost value here, it wouldn't make a difference.
                         */
                        Tuple t = new Tuple(state, v, 0);
                        if (!set.contains(t)) {
                            queue.add(new Tuple(state, v, curr.cost + 1));
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
     * int state:
     * mask of all the nodes we visited so far: 0 -> not visited,
     * 1 -> visited (Originally this was Set<Integer>of all nodes we visited so far,
     * but since the Solution TLE and N <= 12, it turns out we can use a state and 32 bits
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
        int state;
        int curr;
        int cost;

        public Tuple(int bit, int n, int c) {
            state = bit;
            curr = n;
            cost = c;
        }

        public boolean equals(Object o) {//!!!
            Tuple p = (Tuple) o;
            return state == p.state && curr == p.curr && cost == p.cost;
        }

        public int hashCode() {//!!!
            return 1331 * state + 7193 * curr + 727 * cost;
        }
    }
}