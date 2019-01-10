package leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class LE_934_Shortest_Bridge {
    /**
         In a given 2D binary array A, there are two islands.
         (An island is a 4-directionally connected group of 1s not connected to any other 1s.)

         Now, we may change 0s to 1s so as to connect the two islands together to form 1 island.

         Return the smallest number of 0s that must be flipped.
         (It is guaranteed that the answer is at least 1.)

         Example 1:

         Input: [[0,1],[1,0]]
         Output: 1

         Example 2:

         Input: [[0,1,0],[0,0,0],[0,0,1]]
         Output: 2

         Example 3:

         Input: [[1,1,1,1,1],[1,0,0,0,1],[1,0,1,0,1],[1,0,0,0,1],[1,1,1,1,1]]
         Output: 1

         Note:
         1 <= A.length = A[0].length <= 100
         A[i][j] == 0 or A[i][j] == 1

         Medium
     */

    /**
     * https://zxi.mytechroad.com/blog/graph/leetcode-934-shortest-bridge/
     *
     * Graph
     * 1.Use DFS find one island (mark with "2")
     * 2.USe BFS to find shortest path to the other island
     *
     * Multi-sources and  multi-destination shorted path problem
     *
     * Time  : O(mn)
     * Space : O(mn)
     */
    class Solution {
        class Pair {
            int x;
            int y;

            public Pair(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }

        public int shortestBridge(int[][] A) {
            if (null == A || A.length == 0) {
                return 0;
            }

            int m = A.length;
            int n = A[0].length;

            Queue<Pair> q = new LinkedList<>();

            /**
             * Key 1:
             * We just need to mark one island, have to use boolean found to
             * end the 2 for loops after dfs is done.
             *
             * !!! Can't just use "break" after dfs(), it will only break from
             * the inner for loop, but not the outer for loop.
             */
            boolean found = false;
            for (int i = 0; i < m && !found; i++) {
                for (int j = 0; j < n && !found; j++) {
                    if (A[i][j] == 1) {
                        dfs(A, m, n, i, j, q);
                        found = true;
                    }
                }
            }

            return bfs(A, q);

        }

        private void dfs(int[][] A, int m, int n, int x, int y, Queue<Pair> q) {
            if (x < 0 || x >= m || y < 0 || y >= n || A[x][y] != 1) {
                return;
            }

            A[x][y] = 2;

            /**
             * Key 2:
             * !!!
             * In DFS, we are getting ready for BFS in 2nd step,
             * after mark island cell to 2, put the cell into Queue.
             */
            q.offer(new Pair(x, y));

            dfs(A, m, n, x + 1, y, q);
            dfs(A, m, n, x - 1, y, q);
            dfs(A, m, n, x, y + 1, q);
            dfs(A, m, n, x, y - 1, q);
        }

        private int bfs(int[][] A, Queue<Pair> q) {
            int m = A.length;
            int n = A[0].length;
            int steps = 0;
            int[][] dirs = new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            while (!q.isEmpty()) {
                int size = q.size();

                /**
                 * Key 3:
                 * Since we put all island cells into q in DFS, therefore, the first level
                 * of BFS will go over all island cells (with value 2).
                 */
                for (int i = 0; i < size; i++) {
                    Pair cur = q.poll();

                    for (int j = 0; j < 4; j++) {
                        int nx = cur.x + dirs[j][0];
                        int ny = cur.y + dirs[j][1];

                        if (nx < 0 || nx >= m || ny < 0 || ny >= n || A[nx][ny] == 2) {
                            continue;
                        }

                        /**
                         * we now reach the other island
                         */
                        if (A[nx][ny] == 1) {
                            return steps;
                        }

                        A[nx][ny] = 2;
                        q.offer(new Pair(nx, ny));
                    }
                }

                steps++;
            }

            return -1;
        }
    }
}