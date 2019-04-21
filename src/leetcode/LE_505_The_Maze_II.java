package leetcode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class LE_505_The_Maze_II {
    /**
     * here is a ball in a maze with empty spaces and walls. The ball can go through empty spaces
     * by rolling up, down, left or right, but it won't stop rolling until hitting a wall. When
     * the ball stops, it could choose the next direction.
     * <p>
     * Given the ball's start position, the destination and the maze, find the shortest distance
     * for the ball to stop at the destination. The distance is defined by the number of empty
     * spaces traveled by the ball from the start position (excluded) to the destination (included).
     * If the ball cannot stop at the destination, return -1.
     * <p>
     * The maze is represented by a binary 2D array. 1 means the wall and 0 means the empty space.
     * You may assume that the borders of the maze are all walls. The start and destination
     * coordinates are represented by row and column indexes.
     * <p>
     * <p>
     * <p>
     * Example 1:
     * <p>
     * Input 1: a maze represented by a 2D array
     * <p>
     * 0 0 1 0 0
     * 0 0 0 0 0
     * 0 0 0 1 0
     * 1 1 0 1 1
     * 0 0 0 0 0
     * <p>
     * Input 2: start coordinate (rowStart, colStart) = (0, 4)
     * Input 3: destination coordinate (rowDest, colDest) = (4, 4)
     * <p>
     * Output: 12
     * <p>
     * Explanation: One shortest way is : left -> down -> left -> down -> right -> down -> right.
     * The total distance is 1 + 1 + 3 + 1 + 2 + 2 + 2 = 12.
     */

    /**
     * BFS
     *
     */
    public class Solution1 {
        class Point {
            int x, y, l;

            public Point(int _x, int _y, int _l) {
                x = _x;
                y = _y;
                l = _l;
            }
        }

        public int shortestDistance(int[][] maze, int[] start, int[] destination) {
            int m = maze.length, n = maze[0].length;
            int[][] length = new int[m][n]; // record length
            for (int i = 0; i < m * n; i++) {
                length[i / n][i % n] = Integer.MAX_VALUE;
            }

            int[][] dir = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
            PriorityQueue<Point> list = new PriorityQueue<>((o1, o2) -> o1.l - o2.l); // using priority queue
            list.offer(new Point(start[0], start[1], 0));

            while (!list.isEmpty()) {
                Point p = list.poll();
                if (length[p.x][p.y] <= p.l) {
                    continue; // if we have already found a route shorter
                }

                length[p.x][p.y] = p.l;
                for (int i = 0; i < 4; i++) {
                    int xx = p.x, yy = p.y, l = p.l;
                    while (xx >= 0 && xx < m && yy >= 0 && yy < n && maze[xx][yy] == 0) {
                        xx += dir[i][0];
                        yy += dir[i][1];
                        l++;
                    }
                    xx -= dir[i][0];
                    yy -= dir[i][1];
                    l--;
                    list.offer(new Point(xx, yy, l));
                }
            }
            return length[destination[0]][destination[1]] == Integer.MAX_VALUE ? -1 : length[destination[0]][destination[1]];
        }
    }

    /**
     * DFS
     *
     * Time complexity : O(m * n * max(m,n)).
     * Complete traversal of maze will be done in the worst case. Here, m and n refers to the
     * number of rows and columns of the maze. Further, for every current node chosen, we can
     * travel up to a maximum depth of max(m,n) in any direction.
     *
     * Space complexity : O(mn)
     * distance array of size m * n is used.
     */
    public class Solution2 {
        public int shortestDistance(int[][] maze, int[] start, int[] dest) {
            /**
             * distance[i][j] :
             * represents the minimum number of steps required to reach the positon (i, j)
             * starting from the start position. This array is initialized with largest
             * integer values in the beginning.
             */
            int[][] distance = new int[maze.length][maze[0].length];
            for (int[] row : distance) {
                Arrays.fill(row, Integer.MAX_VALUE);
            }
            distance[start[0]][start[1]] = 0;

            dfs(maze, start, distance);

            return distance[dest[0]][dest[1]] == Integer.MAX_VALUE ? -1 : distance[dest[0]][dest[1]];
        }

        public void dfs(int[][] maze, int[] start, int[][] distance) {
            int[][] dirs = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
            for (int[] dir : dirs) {
                int x = start[0] + dir[0];
                int y = start[1] + dir[1];
                int count = 0;

                /**
                 * this while simulates moving to one direction until it hits a wall
                 */
                while (x >= 0 && y >= 0 && x < maze.length && y < maze[0].length && maze[x][y] == 0) {
                    x += dir[0];
                    y += dir[1];
                    count++;
                }

                if (distance[start[0]][start[1]] + count < distance[x - dir[0]][y - dir[1]]) {
                    distance[x - dir[0]][y - dir[1]] = distance[start[0]][start[1]] + count;
                    dfs(maze, new int[]{x - dir[0], y - dir[1]}, distance);
                }
            }
        }

        /**
         * BFS
         */
        public class Solution3 {
            public int shortestDistance(int[][] maze, int[] start, int[] dest) {
                int[][] distance = new int[maze.length][maze[0].length];
                for (int[] row: distance) {
                    Arrays.fill(row, Integer.MAX_VALUE);
                }

                distance[start[0]][start[1]] = 0;
                int[][] dirs={{0, 1} ,{0, -1}, {-1, 0}, {1, 0}};
                Queue< int[] > queue = new LinkedList< >();
                queue.add(start);

                while (!queue.isEmpty()) {
                    int[] s = queue.remove();
                    for (int[] dir: dirs) {
                        int x = s[0] + dir[0];
                        int y = s[1] + dir[1];
                        int count = 0;
                        while (x >= 0 && y >= 0 && x < maze.length && y < maze[0].length && maze[x][y] == 0) {
                            x += dir[0];
                            y += dir[1];
                            count++;
                        }

                        if (distance[s[0]][s[1]] + count < distance[x - dir[0]][y - dir[1]]) {
                            distance[x - dir[0]][y - dir[1]] = distance[s[0]][s[1]] + count;
                            queue.add(new int[] {x - dir[0], y - dir[1]});
                        }
                    }
                }
                return distance[dest[0]][dest[1]] == Integer.MAX_VALUE ? -1 : distance[dest[0]][dest[1]];
            }
        }
    }
}
