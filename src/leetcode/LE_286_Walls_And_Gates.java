package leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by yuank on 4/19/18.
 */
public class LE_286_Walls_And_Gates {
    /**
     *  You are given a m x n 2D grid initialized with these three possible values.

         -1 - A wall or an obstacle.
         0 - A gate.
         INF - Infinity means an empty room. We use the value 2^31 - 1 = 2147483647 to
         represent INF as you may assume that the distance to a gate is less than 2147483647.

         Fill each empty room with the distance to its nearest gate. If it is impossible to
         reach a gate, it should be filled with INF.

         For example, given the 2D grid:
         INF  -1  0  INF
         INF INF INF  -1
         INF  -1 INF  -1
         0  -1 INF INF
         After running your function, the 2D grid should be:
         3  -1   0   1
         2   2   1  -1
         1  -1   2  -1
         0  -1   3   4
     */

    /**
     * Simplified, without counting steps in BFS
     */
    class Solution3_1 {
        public void wallsAndGates(int[][] rooms) {
            if (rooms == null || rooms.length == 0) return;

            Queue<int[]> q = new LinkedList<>();
            int m = rooms.length;
            int n = rooms[0].length;

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (rooms[i][j] == 0) {
                        q.offer(new int[]{i, j});
                    }
                }
            }

            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            while (!q.isEmpty()) {
                int[] cur = q.poll();
                int x = cur[0];
                int y = cur[1];

                for (int[] dir : dirs) {
                    int nx = x + dir[0];
                    int ny = y + dir[1];

                    if (nx >= 0 && nx < m && ny >= 0 && ny < n && rooms[nx][ny] > rooms[x][y] + 1) {
                        rooms[nx][ny] = rooms[x][y] + 1;
                        q.offer(new int[]{nx, ny});
                    }
                }
            }
        }
    }

    class Solution3 {
        public void wallsAndGates(int[][] rooms) {
            if (rooms == null || rooms.length == 0) return;

            Queue<int[]> q = new LinkedList<>();
            int m = rooms.length;
            int n = rooms[0].length;

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (rooms[i][j] == 0) {
                        q.offer(new int[]{i, j});
                    }
                }
            }

            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            int steps = 0;

            while (!q.isEmpty()) {
                int size = q.size();
                steps++;

                for (int i = 0; i < size; i++) {
                    int[] cur = q.poll();

                    for (int[] dir : dirs) {
                        int nx = cur[0] + dir[0];
                        int ny = cur[1] + dir[1];

                        if (nx >= 0 && nx < m && ny >= 0 && ny < n
                                && rooms[nx][ny] == Integer.MAX_VALUE) {
                            rooms[nx][ny] = steps;
                            q.offer(new int[]{nx, ny});
                        }
                    }
                }
            }

        }
    }

    public class Solution3_Practice {
        public void wallsAndGates(int[][] rooms) {
            if (rooms == null || rooms.length == 0) return;

            int m = rooms.length;
            int n = rooms[0].length;
            Queue<int[]> q = new LinkedList<>();
            int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
            int steps = 0;

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (rooms[i][j] == 0) {
                        q.offer(new int[]{i, j});
                    }
                }
            }

            while (!q.isEmpty()) {
                steps++;
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    int[] cur = q.poll();
                    int x = cur[0];
                    int y = cur[1];

                    for (int j = 0; j < 4; j++) {
                        int nx = x + dirs[j][0];
                        int ny = y + dirs[j][1];

                        if (nx >= 0 && nx < m && ny >= 0 && ny < n && rooms[nx][ny] == 2147483647) {
                            rooms[nx][ny] = steps;
                            q.offer(new int[]{nx, ny});
                        }
                    }
                }
            }
        }
    }

    /**
     * version: 高频题班
     *
     * !!!
     * Loop to add all doors to queue
     *
     * "Super start point", 多源点单终点 → 单源点多终点，最短路常用转化套路
     *
     * 增加了超级源后，其实相当于从超级源的单源最短路
     *
     * 多源多终点→单源多终点 (增加超级源，最短路常用转化套路)
     **/
    public class Solution1 {
        public void wallsAndGates(int[][] rooms) {
            // Write your code here
            int n = rooms.length;
            if (n == 0) {
                return;
            }
            int m = rooms[0].length;

            int dx[] = {0, 1, 0, -1};
            int dy[] = {1, 0, -1, 0};

            Queue<Integer> qx = new LinkedList<>();
            Queue<Integer> qy = new LinkedList<>();

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (rooms[i][j] == 0) {
                        qx.offer(i);
                        qy.offer(j);
                    }
                }
            }

            while (!qx.isEmpty()) {
                int x = qx.poll();
                int y = qy.poll();

                for (int i = 0; i < 4; i++) {
                    int nx = x + dx[i];
                    int ny = y + dy[i];
                    if (0 <= nx && nx < n && 0 <= ny && ny < m
                            && rooms[nx][ny] == Integer.MAX_VALUE) {
                        qx.offer(nx);
                        qy.offer(ny);

                        /**
                         * !!!
                         */
                        rooms[nx][ny] = rooms[x][y] + 1;
                    }
                }
            }
        }
    }


    public class Solution1_Practice {
        public void wallsAndGates(int[][] rooms) {
            if (null == rooms || rooms.length == 0) return;

            /**
             * Or we can use 1 queue saving int[], as in Solution2
             */
            Queue<Integer> xq = new LinkedList<>();
            Queue<Integer> yq = new LinkedList<>();
            int m = rooms.length;
            int n = rooms[0].length;
            int[] mx = new int[]{0, 1, 0, -1};
            int[] my = new int[]{1, 0, -1, 0};

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (rooms[i][j] == 0) {
                        xq.offer(i);
                        yq.offer(j);
                    }
                }
            }

            // int steps = 0;
            while (!xq.isEmpty()) {
                // steps++;
//                int size = xq.size();

                /**
                 * No need to count steps as explained below, therefore no need to do for loop with size()
                 */
                // for (int i = 0; i < size; i++) {
                int x = xq.poll();
                int y = yq.poll();

                for (int i = 0; i < 4; i++) {
                    int nx = x + mx[i];
                    int ny = y + my[i];

                    /**
                     * The solution uses the property that gate cell has value '0',
                     * so we don't need to count steps in BFS, just use " rooms[nx][ny] = rooms[x][y] + 1"
                     * for all valid cells (valid means in range and it is a room).
                     *
                     * Important!!!
                     * Since BFS guarantees shortest path, so no need to do
                     * "rooms[nx][ny] == Math.min(rooms[nx][ny], steps);"
                     *
                     * For using steps, see Solution3
                     */
                    // if (rooms[nx][ny] == 2147483647) {
                    //     rooms[nx][ny] = steps;
                    //     xq.offer(mx);
                    //     yq.offer(my);
                    // } else if (rooms[nx][ny] != 0 && rooms[nx][ny] != -1) {
                    //     rooms[nx][ny] == Math.min(rooms[nx][ny], steps);
                    // }

                    if (nx >= 0 && nx < m && ny >= 0 && ny >= 0 && ny < n && rooms[nx][ny] == 2147483647) {
                        xq.offer(nx);
                        yq.offer(ny);
                        rooms[nx][ny] = rooms[x][y] + 1;
                    }
                }
                // }
            }
        }
    }

    class Solution2 {
        //BFS, Time and Space : O(mn)
        public void wallsAndGatesBFS(int[][] rooms) {
            if (rooms == null || rooms.length == 0 || rooms[0].length == 0) return;

            int m = rooms.length;
            int n = rooms[0].length;
            Queue<int[]> queue = new LinkedList<>();

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (rooms[i][j] == 0) {
                        //!!!
                        queue.add(new int[]{i, j});
                    }
                }
            }

            while (!queue.isEmpty()) {
                int[] top = queue.remove();
                int row = top[0], col = top[1];
                if (row > 0 && rooms[row - 1][col] == Integer.MAX_VALUE) {
                    rooms[row - 1][col] = rooms[row][col] + 1;
                    queue.add(new int[]{row - 1, col});
                }
                if (row < m - 1 && rooms[row + 1][col] == Integer.MAX_VALUE) {
                    rooms[row + 1][col] = rooms[row][col] + 1;
                    queue.add(new int[]{row + 1, col});
                }
                if (col > 0 && rooms[row][col - 1] == Integer.MAX_VALUE) {
                    rooms[row][col - 1] = rooms[row][col] + 1;
                    queue.add(new int[]{row, col - 1});
                }
                if (col < n - 1 && rooms[row][col + 1] == Integer.MAX_VALUE) {
                    rooms[row][col + 1] = rooms[row][col] + 1;
                    queue.add(new int[]{row, col + 1});
                }
            }
        }
    }

}
