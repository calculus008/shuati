package leetcode;

import java.util.*;

public class LE_886_Possible_Bipartition {
    /**
         Given a set of N people (numbered 1, 2, ..., N),
         we would like to split everyone into two groups of any size.

         Each person may dislike some other people,
         and they should not go into the same group.

         Formally, if dislikes[i] = [a, b], it means it is not allowed
         to put the people numbered a and b into the same group.

         Return true if and only if it is possible to split everyone into two groups in this way.

         Example 1:
         Input: N = 4, dislikes = [[1,2],[1,3],[2,4]]
         Output: true
         Explanation: group1 [1,4], group2 [2,3]

         Example 2:
         Input: N = 3, dislikes = [[1,2],[1,3],[2,3]]
         Output: false

         Example 3:
         Input: N = 5, dislikes = [[1,2],[2,3],[3,4],[4,5],[1,5]]
         Output: false

         Note:

         1 <= N <= 2000
         0 <= dislikes.length <= 10000
         1 <= dislikes[i][j] <= N
         dislikes[i][0] < dislikes[i][1]
         There does not exist i != j for which dislikes[i] == dislikes[j].

         Medium
     */

    /**
     * https://zxi.mytechroad.com/blog/graph/leetcode-886-possible-bipartition/
     *
     * DFS Coloring
     *
     * Time  : O(V + E)
     * Space : O (V + E)
     *
     * The same problem as LE_785_Is_Graph_Bipartite
     */
    class SolutionDFS_With_AdjacentList {
        ArrayList<Integer>[] graph;

        /**
         * person Id -> color Id
         */
        Map<Integer, Integer> color;

        public boolean possibleBipartition(int N, int[][] dislikes) {
            graph = new ArrayList[N + 1];
            for (int i = 1; i <= N; ++i) {//person id is 1-based
                graph[i] = new ArrayList();
            }

            /**
             * A person's "neighbours" are those people that he dislikes,
             * so we want to see if this person and his "neighbours" can
             * be divided with Bipartition process.
             *
             * !!!
             * This is an undirected graph, so we need to add two edges for
             * each dislike relationship.
             */
            for (int[] edge : dislikes) {
                graph[edge[0]].add(edge[1]);
                graph[edge[1]].add(edge[0]);
            }

            color = new HashMap();
            for (int node = 1; node <= N; ++node) {
                if (!color.containsKey(node) && !dfs(node, 1)) {
                    return false;
                }
            }
            return true;
        }

        public boolean dfs(int node, int c) {
            if (color.containsKey(node)) {
                return color.get(node) == c;
            }

            color.put(node, c);

            for (int nei : graph[node]) {
                if (!dfs(nei, -c)) {
                    return false;
                }
            }

            return true;
        }
    }

    class SolutionDFS  {
        public boolean possibleBipartition(int N, int[][] dislikes) {
            if (null == dislikes || dislikes.length == 0) {
                return true;
            }

            int[][] graph = new int[N][N];

            /**
             * create graph (adjacent matrix), for each dislike[x, y], in graph,
             * mark both graph[x][y] and graph[y][x] as 1 (meaning
             * they dislike each other)
             *
             * d[0] - 1 : person id is 1-based, graph is 0-based.
             */
            for (int[] d : dislikes) {
                graph[d[0] - 1][d[1] - 1] = 1;
                graph[d[1] - 1][d[0] - 1] = 1;
            }

            //0 : unkown, 1 : red, -1 : blue
            int[] group = new int[N];

            /**
             * Iterate through group[], try to do coloring
             */
            for (int i = 0; i < group.length; i++) {
                /**
                 * Only do coloring if the current person is not colored (0)
                 */
                if (group[i] == 0 && !helper(graph, group, i, 1)) {
                    return false;
                }
            }

            return true;
        }

        boolean helper(int[][] graph, int[] group, int idx, int color) {
            /**
             * color the current person
             **/
            group[idx] = color;

            /**
             * Then check the graph, pull the target person who dislikes current person
             */
            for (int i = 0; i < graph.length; i++) {
                if (graph[idx][i] == 1) {
                    /**
                     * If the target person that current person dislikes is already colored
                     * with the same color, return false
                     */
                    if (group[i] == color) {
                        return false;
                    }

                    /**
                     * If the target person is not colored yet, try to do color, go to
                     * the next level of dfs, if it returns false, then return false.
                     * Coloring action always happens in the next level of dfs.
                     */
                    if (group[i] == 0 && !helper(graph, group, i, -color)) {
                        return false;
                    }
                }
            }

            return true;
        }
    }

    class SolutionBFS {
        public boolean possibleBipartition(int N, int[][] dislikes) {
            if (null == dislikes || dislikes.length == 0) {
                return true;
            }

            int[][] graph = new int[N][N];

            for (int[] d : dislikes) {
                graph[d[0] - 1][d[1] - 1] = 1;
                graph[d[1] - 1][d[0] - 1] = 1;
            }

            int[] group = new int[N];
            Queue<Integer> q = new LinkedList<>();

            for (int i = 0; i < N; i++) {
                if (group[i] != 0) {
                    continue;
                }

                group[i] = 1;
                q.offer(i);

                while (!q.isEmpty()) {
                    int cur = q.poll();

                    for (int j = 0; j < N; j++) {
                        if (graph[cur][j] == 1) {
                            if (group[j] == group[cur]) {
                                return false;
                            }

                            if (group[j] != 0) {
                                continue;
                            }

                            group[j] = -group[cur];
                            q.offer(j);
                        }
                    }
                }
            }

            return true;
        }

    }
}