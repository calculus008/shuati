package leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class LE_490_The_Maze {
    /**
     * There is a ball in a maze with empty spaces and walls. The ball can go through
     * empty spaces by rolling up, down, left or right, but it won't stop rolling until
     * hitting a wall. When the ball stops, it could choose the next direction.
     *
     * Given the ball's start position, the destination and the maze, determine whether
     * the ball could stop at the destination.
     *
     * The maze is represented by a binary 2D array. 1 means the wall and 0 means the
     * empty space. You may assume that the borders of the maze are all walls. The start
     * and destination coordinates are represented by row and column indexes.
     *
     *
     *
     * Example 1:
     *
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
     * Output: true
     *
     * Hard
     */

    /**
     * An variation of BFS, we not just move one cell at a time, we move to one direction
     * until we hit a wall, use a while loop to simulate this movement.
     *
     * Time and Space : O(mn)
     */
    class Solution {
        public boolean hasPath(int[][] maze, int[] start, int[] destination) {
            int m = maze.length;
            int n = maze[0].length;

            Queue<int[]> q = new LinkedList<>();

            q.offer(start);
            boolean[][] visited = new boolean[m][n];
            int[][] dirs = new int[][]{{1,0}, {-1,0}, {0, 1}, {0, -1}};

            while (!q.isEmpty()) {
                int[] cur = q.poll();

                for (int[] dir : dirs) {
                    int x = cur[0];
                    int y = cur[1];

                    /**
                     * !!!
                     * Use while loop to simulate the ball rolling in one direction
                     * until hit a wall. We check if we are still with boundary AND
                     * the next cell is not a WALL:
                     *
                     * "maze[x][y] == 0"!!!
                     *
                     * Keep adding dir[0] and dir[1]: move to one direction.
                     */
                    while (x >= 0 && x < m && y >= 0 && y < n && maze[x][y] == 0) {
                        x += dir[0];
                        y += dir[1];
                    }

                    /**
                     * !!!
                     * roll back one step since we hit the WALL in the last step of the while loop.
                     */
                    x -= dir[0];
                    y -= dir[1];

                    if (x == destination[0] && y == destination[1]) {
                        return true;
                    }

                    /**
                     * !!!
                     * We only count visited when we stop at a WALL, not for all cells on the way to the current
                     * stop position.
                     */
                    if (visited[x][y]) continue;
                    visited[x][y] = true;

                    /**
                     * !!!
                     * So the q of BFS only stores location that the ball can change direction
                     * (run into wall on current direction)
                     */
                    q.offer(new int[]{x, y});
                }
            }

            return false;
        }
    }
}
