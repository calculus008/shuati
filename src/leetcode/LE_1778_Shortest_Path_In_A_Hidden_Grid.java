package leetcode;

import java.util.*;

public class LE_1778_Shortest_Path_In_A_Hidden_Grid {
    /**
     * This is an interactive problem.
     *
     * There is a robot in a hidden grid, and you are trying to get it from its starting cell to the target cell in this
     * grid. The grid is of size m x n, and each cell in the grid is either empty or blocked. It is guaranteed that the
     * starting cell and the target cell are different, and neither of them is blocked.
     *
     * You want to find the minimum distance to the target cell. However, you do not know the grid's dimensions, the
     * starting cell, nor the target cell. You are only allowed to ask queries to the GridMaster object.
     *
     * Thr GridMaster class has the following functions:
     *
     * boolean canMove(char direction) Returns true if the robot can move in that direction. Otherwise, it returns false.
     * void move(char direction) Moves the robot in that direction. If this move would move the robot to a blocked cell
     * or off the grid, the move will be ignored, and the robot will remain in the same position.
     * boolean isTarget() Returns true if the robot is currently on the target cell. Otherwise, it returns false.
     * Note that direction in the above functions should be a character from {'U','D','L','R'}, representing the directions
     * up, down, left, and right, respectively.
     *
     * Return the minimum distance between the robot's initial starting cell and the target cell. If there is no valid path
     * between the cells, return -1.
     *
     * Custom testing:
     * The test input is read as a 2D matrix grid of size m x n where:
     *
     * grid[i][j] == -1 indicates that the robot is in cell (i, j) (the starting cell).
     * grid[i][j] == 0 indicates that the cell (i, j) is blocked.
     * grid[i][j] == 1 indicates that the cell (i, j) is empty.
     * grid[i][j] == 2 indicates that the cell (i, j) is the target cell.
     * There is exactly one -1 and 2 in grid. Remember that you will not have this information in your code.
     *
     * Example 1:
     * Input: grid = [[1,2],[-1,0]]
     * Output: 2
     * Explanation: One possible interaction is described below:
     * The robot is initially standing on cell (1, 0), denoted by the -1.
     * - master.canMove('U') returns true.
     * - master.canMove('D') returns false.
     * - master.canMove('L') returns false.
     * - master.canMove('R') returns false.
     * - master.move('U') moves the robot to the cell (0, 0).
     * - master.isTarget() returns false.
     * - master.canMove('U') returns false.
     * - master.canMove('D') returns true.
     * - master.canMove('L') returns false.
     * - master.canMove('R') returns true.
     * - master.move('R') moves the robot to the cell (0, 1).
     * - master.isTarget() returns true.
     * We now know that the target is the cell (0, 1), and the shortest path to the target cell is 2.
     *
     * Example 2:
     * Input: grid = [[0,0,-1],[1,1,1],[2,0,0]]
     * Output: 4
     * Explanation: The minimum distance between the robot and the target cell is 4.
     *
     * Example 3:
     * Input: grid = [[-1,0],[0,2]]
     * Output: -1
     * Explanation: There is no path from the robot to the target cell.
     *
     * Constraints:
     * 1 <= n, m <= 500
     * m == grid.length
     * n == grid[i].length
     * grid[i][j] is either -1, 0, 1, or 2.
     * There is exactly one -1 in grid.
     * There is exactly one 2 in grid.
     *
     * Medium
     *
     * https://leetcode.com/problems/shortest-path-in-a-hidden-grid/
     */

    //dummy class to clear compile errors
    class GridMaster {
        boolean canMove(char direction) {return true;};
        void move(char direction) { return;};
        boolean isTarget() {return true;};
    }

    /**
     * DFS + BFS
     *
     * BFS的拓展题。很考验对BFS的理解。
     *
     * "Shortest_Path"，肯定是用BFS。但是这里，我们没有一个grid, 只能通过GridMaster interface来获取信息。尤其是：isTarget(), 它
     * 没有input param, 隐含的意思是，只有在执行了"move(char dir)"之后, 它才能告诉你你的当前的位置是不是target. 所以它的本质是DFS,
     * 只有在DFS中，你才方便用move方法来实现backtracking, 在BFS中，及其不自然，即便可以，也很expansive, 会TLE。
     *
     * 所以，解法是DFS+BFS, explore, then walk. BFS必须有一个已知的grid才可能。
     *
     * 1.DFS, explore grid using GridMaster interface, create our own grid
     * 2.Standard BFS to get the answer.
     *
     * Some details. As "Constrains" has:
     * 1 <= n, m <= 500
     * The start point could be anywhere in the grid, it can be at {0, 0}, or {499, 499}, therefore we have to put start
     * point at the center. Also, we also need add one two more rows and columns for the grid because we need them to
     * signify the boundary. Therefore, the size of grid should be 2 * 500 + 2.
     */
    class Solution {
        char[] forward  = {'U', 'D', 'L', 'R'};
        char[] backward = {'D', 'U', 'R', 'L'};
        int[][] dirs = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};

        int UNVISITED = 0;
        int EMPTY    = 1;
        int TARGET   = 2;
        int BLOCKED  = 3;

        public int findShortestPath(GridMaster master) {
            int N = 500;
            int[][] grid = new int[2 * (N + 1)][2 * (N + 1)];

            dfs(master, grid, N, N);

            int[] start = {N, N};
            Queue<int[]> q = new LinkedList<>();
            q.offer(start);

            /**
             * We can use a set to save visited info, or, more easily, just mark the grid cell as BLOCKED so it won't be
             * visited again.
             */
            // Set<int[]> visited = new HashSet<>();
            // visited.add(start);
            /**
             * !!!
             * Same effect as put start point to visited set.
             * Don't forget!!!!!
             */
            grid[N][N] = BLOCKED;

            int res = 1;
            while (!q.isEmpty()) {
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    int[] cur = q.poll();

                    for (int j = 0; j < 4; j++) {
                        int nx = cur[0] + dirs[j][0];
                        int ny = cur[1] + dirs[j][1];
                        int[] next = new int[]{nx, ny};

                        if (grid[nx][ny] == TARGET) return res;

                        if (grid[nx][ny] == BLOCKED) continue;

                        q.offer(next);
                        /**
                         * mark visited, don't foraget!!!!!
                         */
                        grid[nx][ny] = BLOCKED;
                        // visited.add(next);
                    }
                }

                res++;
            }

            return -1;
        }

        private void dfs(GridMaster master, int[][] grid, int x, int y) {
            if (grid[x][y] != UNVISITED) return;

            /**
             * cell process happens here
             */
            if (master.isTarget()) {
                grid[x][y] = TARGET;
            } else {
                grid[x][y] = EMPTY;
            }

            for (int i = 0; i < 4; i++) {
                int nx = x + dirs[i][0];
                int ny = y + dirs[i][1];
                char f = forward[i];
                char b = backward[i];

                if (!master.canMove(f)) {
                    grid[nx][ny] = BLOCKED;
                    continue;
                }

                /**
                 * !!! move, recurse then backtrack
                 */
                master.move(f);
                dfs(master, grid, nx, ny);
                master.move(b);
            }
        }
    }
}
