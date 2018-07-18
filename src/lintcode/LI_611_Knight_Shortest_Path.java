package lintcode;

import common.Point;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by yuank on 7/18/18.
 */
public class LI_611_Knight_Shortest_Path {
    /**
         Given a knight in a chessboard (a binary matrix with 0 as empty and 1 as barrier) with a source position,
         find the shortest path to a destination position, return the length of the route.
         Return -1 if knight can not reached.

         Example
         [[0,0,0],
         [0,0,0],
         [0,0,0]]
         source = [2, 0] destination = [2, 2] return 2

         [[0,1,0],
         [0,0,0],
         [0,0,0]]
         source = [2, 0] destination = [2, 2] return 6

         [[0,1,0],
         [0,0,1],
         [0,0,0]]
         source = [2, 0] destination = [2, 2] return -1

         Medium
     */

    /**
     * BFS, Time : O(V + E), Space : O(V)
     */
    public int shortestPath(boolean[][] grid, Point source, Point destination) {
        Queue<Point> queue = new LinkedList<>();
        queue.offer(source);

        int[][] dir = {{-2, -1}, {-2, 1}, {2, -1}, {2, 1}, {1, -2}, {1, 2}, {-1, -2}, {-1, 2}};
        int res = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                Point cur = queue.poll();

                if (cur.x == destination.x && cur.y == destination.y) {
                    return res;
                }

                for (int k = 0; k < dir.length; k++) {
                    Point next = new Point(cur.x + dir[k][0], cur.y + dir[k][1]);
                    if (!isValid(next, grid)) {
                        continue;
                    }

                    queue.offer(next);
                    /**
                     * !!! Don't forget
                     */
                    grid[next.x][next.y] = true;
                }
            }

            res++;
        }

        return -1;
    }

    public boolean isValid(Point p, boolean[][] grid) {
        if (p.x < 0 || p.x >= grid.length || p.y < 0 || p.y >= grid[0].length || grid[p.x][p.y]) {
            return false;
        }

        return true;
    }
}
