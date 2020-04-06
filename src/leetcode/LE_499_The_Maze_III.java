package leetcode;

import java.util.Arrays;
import java.util.PriorityQueue;

public class LE_499_The_Maze_III {
    /**
     * There is a ball in a maze with empty spaces and walls. The ball can go through empty
     * spaces by rolling up (u), down (d), left (l) or right (r), but it won't stop rolling
     * until hitting a wall. When the ball stops, it could choose the next direction. There
     * is also a hole in this maze. The ball will drop into the hole if it rolls on to the hole.
     *
     * Given the ball position, the hole position and the maze, find out how the ball could
     * drop into the hole by moving the shortest distance. The distance is defined by the
     * number of empty spaces traveled by the ball from the start position (excluded) to
     * the hole (included). Output the moving directions by using 'u', 'd', 'l' and 'r'.
     * Since there could be several different shortest ways, you should output the lexicographically
     * smallest way. If the ball cannot reach the hole, output "impossible".
     *
     * The maze is represented by a binary 2D array. 1 means the wall and 0 means the empty space.
     * You may assume that the borders of the maze are all walls. The ball and the hole coordinates
     * are represented by row and column indexes.
     *
     * Example 1:
     * Input 1: a maze represented by a 2D array
     *
     * 0 0 0 0 0
     * 1 1 0 0 1
     * 0 0 0 0 0
     * 0 1 0 0 1
     * 0 1 0 0 0
     *
     * Input 2: ball coordinate (rowBall, colBall) = (4, 3)
     * Input 3: hole coordinate (rowHole, colHole) = (0, 1)
     *
     * Output: "lul"
     *
     * Explanation: There are two shortest ways for the ball to drop into the hole.
     * The first way is left -> up -> left, represented by "lul".
     * The second way is up -> left, represented by 'ul'.
     * Both ways have shortest distance 6, but the first way is lexicographically smaller
     * because 'l' < 'u'. So the output is "lul".
     *
     * Example 2:
     * Input 1: a maze represented by a 2D array
     *
     * 0 0 0 0 0
     * 1 1 0 0 1
     * 0 0 0 0 0
     * 0 1 0 0 1
     * 0 1 0 0 0
     *
     * Input 2: ball coordinate (rowBall, colBall) = (4, 3)
     * Input 3: hole coordinate (rowHole, colHole) = (3, 0)
     *
     * Output: "impossible"
     *
     * Explanation: The ball cannot reach the hole.
     *
     * Note:
     * There is only one ball and one hole in the maze.
     * Both the ball and hole exist on an empty space, and they will not be at the same position initially.
     * The given maze does not contain border (like the red rectangle in the example pictures), but you
     * could assume the border of the maze are all walls.
     * The maze contains at least 2 empty spaces, and the width and the height of the maze won't exceed 30.
     *
     * Hard
     */

    /**
     * Time : mnlog(mn)
     * Space : O(mn)
     */
    class Solution_BFS {
        /**
         * "implements Comparable<Point>"
         */
        class Point implements Comparable<Point> {
            int x, y, dist;
            String moves;

            public Point(int x, int y, int dist, String moves) {
                this.x = x;
                this.y = y;
                this.dist = dist;
                this.moves = moves;
            }

            public int compareTo(Point that) {
                if (this.dist != that.dist)     return this.dist - that.dist;
                return this.moves.compareTo(that.moves);
            }
        }

        int[][] dirs = {{1, 0}, {0, -1}, {0, 1}, {-1, 0}};
        char[] dirc = {'d', 'l', 'r', 'u'};

        public String findShortestWay(int[][] maze, int[] ball, int[] hole) {
            int m = maze.length, n = maze[0].length;

            boolean[][] visited = new boolean[m][n];

            PriorityQueue<Point> pq = new PriorityQueue<>();
            pq.add(new Point(ball[0], ball[1], 0, ""));

            while(!pq.isEmpty()) {
                Point cur = pq.poll();

                /**
                 * !!!
                 * Return when hit hole in processing element from pq
                 */
                if (cur.x == hole[0] && cur.y == hole[1]) {
                    return cur.moves;
                }

                if (!visited[cur.x][cur.y]) continue;

                visited[cur.x][cur.y] = true;

                for (int i = 0; i < 4; i++) {
                    Point next = moveForward(maze, cur, i, hole);
                    /**
                     * !!!
                     * "next.moves + dirc[i]" : direction string generated
                     */
                    pq.add(new Point(next.x, next.y, next.dist, next.moves + dirc[i]));
                }

            }

            return "impossible";
        }

        /**
         * Start from current position move forward in one direction until hit the wall,
         * return the last position before hitting the wall
         */
        private Point moveForward(int[][] maze, Point cur, int direction, int[] hole) {
            int m = maze.length, n = maze[0].length;
            int nx = cur.x, ny = cur.y, dis = cur.dist;

            while (nx >= 0 && nx < m && ny >= 0 && ny < n && maze[nx][ny] == 0) {
                nx += dirs[direction][0];
                ny += dirs[direction][1];
                dis++;

                if (nx == hole[0] && ny == hole[1]) {
                    return new Point(nx, ny, dis, cur.moves);
                }
            }

            nx -= dirs[direction][0];
            ny -= dirs[direction][1];
            dis--;

            return new Point(nx, ny, dis, cur.moves);
        }
    }

    /**
     * 2ms 100% solution from Leetcode
     */
    class Solution_DFS {
        int min; // min distance to hole
        String minS; // min distance's path string
        int[] hole;
        int[][] maze;
        int[][] dist; // shortest distant traveling from ball to this point
        int[][] dirs = {{0, 1}, {-1, 0}, {1, 0}, {0, -1}}; //r, u, d, l

        public String findShortestWay(int[][] maze, int[] ball, int[] hole) {
            this.min = Integer.MAX_VALUE;
            this.minS = null;
            this.hole = hole;
            this.maze = maze;
            this.dist = new int[maze.length][maze[0].length];

            for (int[] ints : dist) {
                Arrays.fill(ints, Integer.MAX_VALUE);
            }

            move(ball[0], ball[1], 0, "", -1);

            return (minS == null) ? "impossible" : minS;
        }

        private void move(int r, int c, int cnt, String path, int dir) {//dir is a index of dirs
            if (cnt > min || cnt > dist[r][c]) return; //not a shortest route for sure

            /**
             * when we have dir as -1, we have no direction, the logic is different
             */
            if (dir != -1) { // if not from start point
                // add path
                if (dir == 0) path += 'r';
                else if (dir == 1) path += 'u';
                else if (dir == 2) path += 'd';
                else path += 'l';

                // roll along dir
                while (r >= 0 && r < maze.length && c >= 0 && c < maze[0].length && maze[r][c] == 0) {
                    dist[r][c] = Math.min(dist[r][c], cnt);

                    if (r == hole[0] && c == hole[1]) { // check hole
                        if (cnt == min && path.compareTo(minS) < 0) {
                            minS = path;
                        } else if (cnt < min) {
                            min = cnt;
                            minS = path;
                        }
                        return;
                    }

                    r += dirs[dir][0];
                    c += dirs[dir][1];
                    cnt++;
                }

                r -= dirs[dir][0];//[r,c] is wall, need to walk back 1 step
                c -= dirs[dir][1];
                cnt--;
            }

            // hit wall (or start) -> try to turn
            for (int i = 0; i < dirs.length; i++) {
                if (dir == i) continue; // dont keep going
                if (dir == (3 - i)) continue; // dont go back

                int newR = r + dirs[i][0];
                int newC = c + dirs[i][1];

                if (newR >= 0 && newR < maze.length && newC >= 0 && newC < maze[0].length && maze[newR][newC] == 0) {
                    move(r, c, cnt, path, i); // can go
                }
            }
        }
    }


}
