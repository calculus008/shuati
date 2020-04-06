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
     *
     * Given the ball's start position, the destination and the maze, find the shortest distance
     * for the ball to stop at the destination. The distance is defined by the number of empty
     * spaces traveled by the ball from the start position (excluded) to the destination (included).
     * If the ball cannot stop at the destination, return -1.
     *
     * The maze is represented by a binary 2D array. 1 means the wall and 0 means the empty space.
     * You may assume that the borders of the maze are all walls. The start and destination
     * coordinates are represented by row and column indexes.
     *
     * Example 1:
     * Input 1: a maze represented by a 2D array
     *
     * 0 0 1 0 0
     * 0 0 0 0 0
     * 0 0 0 1 0
     * 1 1 0 1 1
     * 0 0 0 0 0
     *
     * Input 2: start coordinate (rowStart, colStart) = (0, 4)
     * Input 3: destination coordinate (rowDest, colDest) = (4, 4)
     *
     * Output: 12
     *
     * Explanation: One shortest way is : left -> down -> left -> down -> right -> down -> right.
     * The total distance is 1 + 1 + 3 + 1 + 2 + 2 + 2 = 12.
     *
     * Hard
     */

    /**
     * BFS
     *
     * q -  contains the new positions to be explored in the future.
     *
     *      We start from the current position cur[][], try to traverse in a particular direction,
     *      obtain the corresponding distance (d) for that direction, reaching an end position
     *      of (x,y) just near the boundary or a wall. If the position (i,j) can be reached in a
     *      lesser number of steps from the current route than any other previous route checked:
     *
     *          dist[cur[0]][cur[1]] + d < dist[x][y]
     *
     *      we need to update dist[x][y] as distance[x][y] + d.
     *
     *      After this, we add the new position obtained, (x, y) to the back of a queue, so that
     *      the various paths possible from this new position will be explored later on when all
     *      the directions possible from the current position (x,y) have been explored. After
     *      exploring all the directions from the current position, we remove an element from the
     *      front of the queue and continue checking the routes possible through all the directions
     *      now taking the new position(obtained from the queue) as the current position.
     *
     * Time : O(m*n*max(m,n))
     *        Complete traversal of maze will be done in the worst case. Here, m and n refers to the number
     *        of rows and columns of the maze. Further, for every current node chosen, we can travel up to
     *        a maximum depth of max(m,n) in any direction.
     *
     * Space : O(mn) queue size can grow up to m*n in the worst case.
     */
    class Solution_BFS_Practice {
        public int shortestDistance(int[][] maze, int[] start, int[] destination) {
            int m = maze.length;
            int n = maze[0].length;

            /**
             * dist[i][j] : shortest distance from start to maze[i][j]
             */
            int[][] dist = new int[m][n];
            for (int[] d : dist) {
                Arrays.fill(d, Integer.MAX_VALUE);
            }
            dist[start[0]][start[1]] = 0;

            int[][] dirs = new int[][]{{1,0}, {-1,0}, {0, 1}, {0, -1}};

            Queue<int[]> q = new LinkedList<>();
            q.offer(start);

            while (!q.isEmpty()) {
                int[] cur = q.poll();

                for (int[] dir : dirs) {
                    int x = cur[0];
                    int y = cur[1];
                    /**
                     * !!!
                     */
                    int d = 0;

                    while (x >= 0 && x < m && y >= 0 && y < n && maze[x][y] == 0) {
                        x += dir[0];
                        y += dir[1];
                        /**
                         * !!!
                         */
                        d++;
                    }

                    x -= dir[0];
                    y -= dir[1];
                    /**
                     * !!!
                     */
                    d--;

                    /**
                     * !!!
                     * cur[][] is the starting point, d is the steps we have traveled to
                     * get to cell {x, y}. Here, if we find a shorter path (starting from start)
                     * to hit cell {x, y}, we record it in dist[x][y] and put it in queue.
                     */
                    if (dist[cur[0]][cur[1]] + d < dist[x][y]) {
                        dist[x][y] = dist[cur[0]][cur[1]] + d;
                        q.offer(new int[]{x, y});
                    }
                }
            }
            return dist[destination[0]][destination[1]] == Integer.MAX_VALUE ? -1 : dist[destination[0]][destination[1]] ;
        }
    }

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
                 * "&& && maze[x][y] == 0" : keep moving until hits a wall
                 */
                while (x >= 0 && y >= 0 && x < maze.length && y < maze[0].length && maze[x][y] == 0) {
                    x += dir[0];
                    y += dir[1];
                    count++;
                }

                /**
                 * "[x - dir[0]", "y - dir[1]"
                 * After while loop stops, we already moves into an invalid position,
                 * therefore, need to backtrack it to the last valid position.
                 */
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
                    int[] cur = queue.remove();
                    for (int[] dir: dirs) {
                        int x = cur[0] + dir[0];
                        int y = cur[1] + dir[1];
                        int count = 0;
                        while (x >= 0 && y >= 0 && x < maze.length && y < maze[0].length && maze[x][y] == 0) {
                            x += dir[0];
                            y += dir[1];
                            count++;
                        }

                        if (distance[cur[0]][cur[1]] + count < distance[x - dir[0]][y - dir[1]]) {
                            distance[x - dir[0]][y - dir[1]] = distance[cur[0]][cur[1]] + count;
                            queue.add(new int[] {x - dir[0], y - dir[1]});
                        }
                    }
                }
                return distance[dest[0]][dest[1]] == Integer.MAX_VALUE ? -1 : distance[dest[0]][dest[1]];
            }
        }
    }
}
