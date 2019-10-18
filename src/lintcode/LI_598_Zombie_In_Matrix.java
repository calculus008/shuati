package lintcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by yuank on 7/18/18.
 */
public class LI_598_Zombie_In_Matrix {
    /**
         Given a 2D grid, each cell is either a wall 2, a zombie 1 or people 0 (the number zero, one, two).
         Zombies can turn the nearest people(up/down/left/right) into zombies every day, but can not through wall.
         How long will it take to turn all people into zombies? Return -1 if can not turn all people into zombies.

         Example
         Given a matrix:

         0 1 2 0 0
         1 0 0 2 1
         0 1 0 0 0
         return 2

         Medium
     */

    /**
     * BFS
     * Key Insights :
     * 1. How to tell if it is done?
     *    Get number of people at the beginning, during BFS, minus 1 each time one people is turned to zombie.
     *    When it gets to 0, it is done. Or after BFS, still number of people is not zero, return -1.
     * 2. Here, checking number of people has to be done during BFS, if it is after BFS finished,
     *    it will add extra days (or number of levels)
     * 3. Here, one day is actually one level for BFS
     * 4. Since each people (0) will be truned into zombie (1), so we don't need to record visited people,
     *    they are all zombies.
     * 5. Maintain two counters during BFS :
     *    Number of people left
     *    Number of Steps
     */

    public class Solution_Practice {
        public int zombie(int[][] grid) {
            if (null == grid || grid.length == 0) return -1;

            int m = grid.length;
            int n = grid[0].length;
            Queue<int[]> q = new LinkedList<>();
            int[][] dirs = { {1, 0}, {0, 1}, {-1, 0}, {0, -1}};
            int days = 0;
            int count = 0;

            for (int i = 0; i < m ; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 0) {
                        count++;
                    } else if(grid[i][j] == 1) {
                        q.offer(new int[]{i, j});
                    }
                }
            }

            while (!q.isEmpty()) {
                days++;
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    int[] cur = q.poll();
                    int x = cur[0];
                    int y = cur[1];

                    for (int j = 0; j < 4; j++) {
                        int nx = x + dirs[j][0];
                        int ny = y + dirs[j][1];

                        if (nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx][ny] == 0) {
                            grid[nx][ny] = 1;
                            q.offer(new int[]{nx, ny});
                            count--;

                            if(count == 0) return days;
                        }
                    }
                }
            }

            return -1;
        }
    }

    class Solution1 {
        public int zombie(int[][] grid) {
            if (grid == null) {
                return -1;
            }

            int people = 0;

            Queue<int[]> q = new LinkedList<>();
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == 1) {
                        /**
                         * use int[][] to save a coordinate in queue
                         */
                        int[] z = {i, j};
                        q.offer(z);
                    } else if (grid[i][j] == 0) {
                        /**
                         * !!!
                         * need to know total number of people
                         * so we can decide when there's no people in the grid
                         */
                        people++;
                    }
                }
            }

            //!!!
            if (people == 0) {
                return 0;
            }

            int res = 1;
            int[][] dir = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            while (!q.isEmpty()) {
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    int[] cur = q.poll();

                    for (int j = 0; j < dir.length; j++) {
                        int[] next = new int[2];
                        next[0] = cur[0] + dir[j][0];
                        next[1] = cur[1] + dir[j][1];

                        if (isValid(grid, next)) {
                            grid[next[0]][next[1]] = 1;

                            people--;//!!!

                            q.offer(next);

                            /**
                             * !!!
                             */
                            if (people == 0) {
                                return res;
                            }
                        }
                    }
                }
                res++;
            }

            return -1;
        }

        boolean isValid(int[][] grid, int[] point) {
            int x = point[0];
            int y = point[1];
            if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] != 0) {
                return false;
            }

            return true;
        }
    }
}
