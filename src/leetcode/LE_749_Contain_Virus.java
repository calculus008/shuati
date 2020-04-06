package leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LE_749_Contain_Virus {
    /**
         A virus is spreading rapidly, and your task is to quarantine the infected area by installing walls.

         The world is modeled as a 2-D array of cells, where 0 represents uninfected cells, and 1 represents
         cells contaminated with the virus. A wall (and only one wall) can be installed between any two
         4-directionally adjacent cells, on the shared boundary.

         Every night, the virus spreads to all neighboring cells in all four directions unless blocked by
         a wall. Resources are limited. Each day, you can install walls around only one region -- the affected
         area (continuous block of infected cells) that threatens the most uninfected cells the following night.
         There will never be a tie.

         Can you save the day? If so, what is the number of walls required? If not, and the world becomes fully infected,
         return the number of walls used.

         Example 1:
         Input: grid =
         [[0,1,0,0,0,0,0,1],
         [0,1,0,0,0,0,0,1],
         [0,0,0,0,0,0,0,1],
         [0,0,0,0,0,0,0,0]]
         Output: 10
         Explanation:
         There are 2 contaminated regions.
         On the first day, add 5 walls to quarantine the viral region on the left. The board after the virus spreads is:

         [[0,1,0,0,0,0,1,1],
         [0,1,0,0,0,0,1,1],
         [0,0,0,0,0,0,1,1],
         [0,0,0,0,0,0,0,1]]

         On the second day, add 5 walls to quarantine the viral region on the right. The virus is fully contained.

         Example 2:
         Input: grid =
         [[1,1,1],
         [1,0,1],
         [1,1,1]]
         Output: 4
         Explanation: Even though there is only one cell saved, there are 4 walls built.
         Notice that walls are only built on the shared boundary of two different cells.
         Example 3:
         Input: grid =
         [[1,1,1,0,0,0,0,0,0],
         [1,0,1,0,1,1,1,1,1],
         [1,1,1,0,0,0,0,0,0]]
         Output: 13
         Explanation: The region on the left only builds two new walls.

         Note:
         The number of rows and columns of grid will each be in the range [1, 50].
         Each grid[i][j] will be either 0 or 1.
         Throughout the described process, there is always a contiguous viral region that will infect strictly more uncontaminated squares in the next round.

         Hard
     */

    /**
     * http://zxi.mytechroad.com/blog/searching/leetcode-749-contain-virus/
     *
     * DFS
     *
     * 23 ms
     *
     * Time  : O(m * n * (m + n)) or O(n ^ 3), each day, it takes O(m * n), it takes O(m + n) days for virus to take over the whole grid.
     * Space : O(m * n) or O(n ^ 2)
     *
     * Everyday, check grid, find the area with the most affected cells (DFS), apply quarantine (walls)
     *
     * For each affected area, need to have an Id to identify, then pass the Id with the most affected cells to update() (build walls)
     *
     * The key implementation detail is how to track number of cells that will be infected and how many new walls should be built for
     * each day.
     *
     * For quarantine, for 1st day, the infected area will be marked "-1". For the rest of days, the boundary cells of infected area are
     * marked "-1".
     *
     * Example:
     *         [
     *          [0,1,0,0,0,0,0,1],
     *          [0,1,0,0,0,0,0,1],
     *          [0,0,0,0,0,0,0,1],
     *          [0,0,0,0,0,0,0,0]
     *         ]
     *
     * After 1st day check
     *         [
     *          [0,2,0,0,0,0,0,3],
     *          [0,2,0,0,0,0,0,3],
     *          [0,0,0,0,0,0,0,3],
     *          [0,0,0,0,0,0,0,0]
     *         ]
     *
     * After 1st day update
     *         [
     *          [0,-1,0,0,0,0,1,3],
     *          [0,-1,0,0,0,0,1,3],
     *          [0,0,0,0,0,0, 1,3],
     *          [0,0,0,0,0,0, 0,1]
     *         ]
     *
     * After 2nd day check
     *         [
     *          [0,-1,0,0,0,0,2,3],
     *          [0,-1,0,0,0,0,2,3],
     *          [0,0,0,0,0,0, 2,3],
     *          [0,0,0,0,0,0, 0,2]
     *         ]
     *
     * After 2nd day update
     *         [
     *          [0,-1,0,0,0,0,-1,3],
     *          [0,-1,0,0,0,0,-1,3],
     *          [0,0,0,0,0,0, -1,3],
     *          [0,0,0,0,0,0, 0,-1]
     *         ]
     *
     **/
    class Solution1 {
        public int containVirus(int[][] grid) {
            int[] cost = new int[]{0};

            while (check(grid, cost));//!!! while

            return cost[0];
        }

        // update every day information and return false if no improvement can be made
        private boolean check(int[][] grid, int[] cost) {
            int count = 1;
            int max = -1;
            boolean flag = false;
            /**
             * info is created for each check() -> each day
             */
            List<int[]> info = new ArrayList<>();

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == 1) {
                        count++;
                        /**
                         * walls[][] and res[] is newly created for each infected area on each day
                         * so it only services for a given area, no need to
                         * worry about a cell infected by multiple areas.
                         */
                        int[][] walls = new int[grid.length][grid[0].length];

                        /**
                         * There are cases that a wall is already built and no new walls need to be built,
                         * here we use res[] to record wall status.
                         *
                         * res[0] : number of cells that are threatened by the virus in given area,
                         *          need to use this value to decide which area to quarantee for each day.
                         * res[1] : total number of new walls need to be built for the given area
                         *          need to use it to calculate the final return value, which is the total
                         *          number of walls built.
                         */
                        int[] res = new int[2];

                        /**
                         * give each affected area an Id, which starts from value 2
                         * because 1 is already used to represent virus.
                         */
                        grid[i][j] = count;

                        dfs(i, j, grid, count, walls, res);

                        if (res[0] != 0) {
                            flag = true;
                        }

                        /**
                         * max is the index in ArrayList into that has the most new build walls, since index starts from 0 and our area Id
                         * starts from 2, so it needs to be mapped here before adding new element into info.
                         */
                        if (max == -1 || res[0] > info.get(max)[0]) {
                            max = count - 2;
                        }
                        info.add(res);
                    }
                }
            }

            /**
             * means no cell in grid[][] has value of 1 :
             * it's either all infected area is quarantined (cell value is set to "-1"),so the possible values in grid are 0 and -1
             * Or
             * the whole grid is infected and marked by an area id K, the possible values in grid are K and -1
             */
            if (count == 1) {
                return false;
            }

            //build walls
            cost[0] += info.get(max)[1];
            update(grid, max + 2);

            return flag;
        }

        //dfs and record number of walls need to block this area and how many 0s are under infection
        private void dfs(int row, int col, int[][] grid, int count, int[][] walls, int[] res) {
            int[] shiftX = new int[]{1, 0, -1, 0};
            int[] shiftY = new int[]{0, 1, 0, -1};

            for (int i = 0; i < 4; i++) {
                int newRow = row + shiftX[i];
                int newCol = col + shiftY[i];
                if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length) {
                    if (grid[newRow][newCol] == 1) {//if it's virus, mark it with area Id, then go to the next layer
                        grid[newRow][newCol] = count;
                        dfs(newRow, newCol, grid, count, walls, res);
                    } else if (grid[newRow][newCol] == 0) {//if it's not virus, it is a cell that will be infected
                        /**
                         * if there's no wall for the given cell
                         */
                        if (walls[newRow][newCol] == 0) {
                            res[0]++;
                        }

                        /**
                         * for each cell, there are max 4 possible walls, use bit shift and or
                         * operation to record them in a single integer
                         *
                         * "walls[newRow][newCol] & 1 << i", if currently there's no wall on the given side
                         */
                        if ((walls[newRow][newCol] & 1 << i) == 0) {
                            res[1]++;
                            walls[newRow][newCol] |= 1 << i;
                        }
                    }
                }
            }
        }

        //set the new infected area and set blocked area to be -1, this is where quarantine is done
        private void update(int[][] grid, int quarantine) {
            int[] shiftX = new int[]{1, 0, -1, 0};
            int[] shiftY = new int[]{0, 1, 0, -1};

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    /**
                     * if it's infected area but NOT the current targeted area, it will keep infecting its neighbors,
                     * simulate it by setting all possible neighbors to "1"
                     */
                    if (grid[i][j] > 1 && grid[i][j] != quarantine) {
                        for (int k = 0; k < 4; k++) {
                            int newRow = i + shiftX[k];
                            int newCol = j + shiftY[k];
                            if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length && grid[newRow][newCol] == 0) {
                                grid[newRow][newCol] = 1;
                            }
                        }
                        grid[i][j] = 1;
                    } else if (grid[i][j] == quarantine) {
                        /**
                         * simulate building wall by set infected cells in targeted area to "-1"
                         */
                        grid[i][j] = -1;
                    }
                }
            }
        }
    }

    /**
     * Adapted from Huahua's solution
     * http://zxi.mytechroad.com/blog/searching/leetcode-749-contain-virus/
     *
     * This solution is more straight-forward conceptually.
     *
     * Keys :
     * 1.Use key = y * row + col, the advantage is that we can use the key to put location of cell (x, y) into list and set,
     *   also can use the key in visited[] to mark '1' cell is visited.
     *
     * 2.For each day，check cells，by DFS(), get 3 things :
     *   a."next"  : the set of cells that will be infected (the ones with '0' and around island with '1')
     *   b."walls" : number walls should be built
     *   c."curr"  : cells in current island, used to do quarantine (mark those cells as '2')
     *
     *
     * 3.Then 打擂台，maintain :
     *   a."block_walls" : the max number of walls
     *   b."block_index" : the index of virus cells to be quarantined
     *   c."virus_area"  : set of cells to be quarantined
     *
     * 4.add "next" to "nexts" (list of sets that contain cells that should be affected without quarantine)
     *
     * 5.If "nexts" is empty, no more area to be infected, exit.
     *
     * 6.Update grid :
     *   a.set the area that has the most infected cells to 2 :
     *     the one with index "block_index", cells are in "virus_area"
     *   b.set the cells that will be infected without quarantined to 1 (in "nexts")
     *
     * 24 ms
     *
     * Example:
     *         [
     *          [0,1,0,0,0,0,0,1],
     *          [0,1,0,0,0,0,0,1],
     *          [0,0,0,0,0,0,0,1],
     *          [0,0,0,0,0,0,0,0]
     *         ]
     *
     *
     * After 1st day update
     *         [
     *          [0,2,0,0,0,0,1,1],
     *          [0,2,0,0,0,0,1,1],
     *          [0,0,0,0,0,0,1,1],
     *          [0,0,0,0,0,0,0,1]
     *         ]
     *
     * After 2nd day update
     *         [
     *          [0,2,0,0,0,0,2,2],
     *          [0,2,0,0,0,0,2,2],
     *          [0,0,0,0,0,0,2,2],
     *          [0,0,0,0,0,0,0,2]
     *         ]
     *
     */
    class Solution2 {
        /**
         * has to make those global because DFS helper change those values
         * and they are used in update action
         */
        int walls = 0;
        List<Integer> curr;

        public int containVirus(int[][] grid) {
            if (null == grid || grid.length == 0) return 0;

            int m = grid.length;
            int n = grid[0].length;

            int total_walls = 0;
            curr = new ArrayList<>();

            while (true) {
                /**
                 * here we use key = y * row + col, then we can use List and Set to record cells.
                 */
                int[] visited = new int[m * n];

                /**
                 * contains the key of each cell in the current infected area
                 */
                List<Integer> virus_area = new ArrayList<>();

                /**
                 * list of sets that contains keys for cells that WILL be infected for each area
                 */
                List<Set<Integer>> nexts = new ArrayList<>();

                int block_index = -1;
                int block_walls = -1;

                /**
                 * Each day, find infected areas info.
                 *
                 * nexts : All infected areas, list of set, each set contains ids of each infected cell that will be infected today.
                 * next : set contains ids for cells that will be infected in each iteration.
                 * block_index : id for the infected area that should be quarantine today.
                 * virus_area  : list of ids which belong to the area that should be quarantine today.
                 */
                for (int i = 0; i < m; i++) {
                    for (int j = 0; j < n; j++) {
                        int key = i * n + j;
                        if (grid[i][j] != 1 || visited[key] == 1) {
                            continue;
                        }

                        Set<Integer> next = new HashSet<>();//set that contains the cells that will be infected around the current area

                        /**
                         * remember to reset global variable before iteration on each area
                         */
                        curr.clear();
                        walls = 0;

                        helper(j, i, m, n, grid, visited, next);

                        if (next.isEmpty()) {
                            continue;
                        }

                        if (nexts.isEmpty() || next.size() > nexts.get(block_index).size()) {
                            // or deep copy each time
                            // virus_area = new ArrayList<>(curr);
                            virus_area.clear();
                            virus_area.addAll(curr);

                            block_index = nexts.size();
                            block_walls = walls;
                        }

                        nexts.add(next);
                    }
                }

                /**
                 * no more area to quarantine, exit.
                 */
                if (nexts.isEmpty()) {
                    break;
                }

                total_walls += block_walls;

                //update
                for (int i = 0; i < nexts.size(); i++) {
                    if (i == block_index) {
                        for (int key : virus_area) {
                            int y = key / n;
                            int x = key % n;
                            grid[y][x] = 2; // blocked or quarantined
                        }
                    } else {
                        for (int key : nexts.get(i)) {
                            int y = key / n;
                            int x = key % n;
                            grid[y][x] = 1; // newly affected
                        }
                    }
                }
            }//end of while()

            return total_walls;
        }

        private void helper(int x, int y, int m, int n,
                            int[][] grid,
                            int[] visited,
                            Set<Integer> next) {
            int dirs[][] = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

            if (x < 0 || x >= n || y < 0 || y >= m || grid[y][x] == 2) {
                return;
            }

            /**
             * dist 2D coordinate to 1D number, better use this mapping,, instead of creating a Pair class
             * since we need to record which coordinates have been visited, use this mapping, we can use
             * 1 1D array visited[]. Use Pair class, we need to implement compare method etc, add more
             * complexity and error-prone in interview.
             */
            int key = y * n + x;

            // need one wall
            if (grid[y][x] == 0) {
                /**
                 * Only check visited after checking if current cell is 0,
                 * if it's 0, put it into next.
                 *
                 * We use Set (next) here to avoid getting duplicate cells
                 *
                 * So for 0 cell, walls increases by 1 each time it is accessed.
                 * It also adds to Set (next) each time, but Set makes it unique.
                 */
                walls++;
                next.add(key);

                /**
                 * !!!
                 * we just need to go one layer for cell with 0 value - the directed infected target
                 * for a given area. Therefore, return here.
                 */
                return;
            }

            /**
             * By logic, visited checking only happens after checking 0 value above.
             * In other words, it only works for none-zero cell.
             */
            if (visited[key] == 1) {
                return;
            }

            /**
             * for a cell with value 1, mark it as visited and add it curr.
             * curr is the potential area to be quarantined.
             */
            visited[key] = 1;
            curr.add(key);

            /**
             * only recurse on cells with value 1.
             */
            for (int i = 0; i < 4; ++i) {
                helper(x + dirs[i][0], y + dirs[i][1], m, n, grid, visited, next);
            }
        }
    }

    class Solution_CleanView {
        int walls = 0;
        List<Integer> curr;

        public int containVirus(int[][] grid) {
            if (null == grid || grid.length == 0) return 0;

            int m = grid.length;
            int n = grid[0].length;

            int total_walls = 0;
            curr = new ArrayList<>();

            while (true) {
                int[] visited = new int[m * n];
                List<Integer> virus_area = new ArrayList<>();
                List<Set<Integer>> nexts = new ArrayList<>();

                int block_index = -1;
                int block_walls = -1;

                for (int i = 0; i < m; i++) {
                    for (int j = 0; j < n; j++) {
                        int key = i * n + j;
                        if (grid[i][j] != 1 || visited[key] == 1) {
                            continue;
                        }

                        Set<Integer> next = new HashSet<>();
                        walls = 0;
                        curr.clear();

                        helper(j, i, m, n, grid, visited, next);

                        if (next.isEmpty()) {
                            continue;
                        }

                        if (nexts.isEmpty() || next.size() > nexts.get(block_index).size()) {
                            virus_area.clear();
                            virus_area.addAll(curr);

                            block_index = nexts.size();
                            block_walls = walls;
                        }

                        nexts.add(next);
                    }
                }

                if (nexts.isEmpty()) {
                    break;
                }

                total_walls += block_walls;

                //update
                for (int i = 0; i < nexts.size(); i++) {
                    if (i == block_index) {
                        for (int key : virus_area) {
                            int y = key / n;
                            int x = key % n;
                            grid[y][x] = 2; // blocked.
                        }
                    } else {
                        for (int key : nexts.get(i)) {
                            int y = key / n;
                            int x = key % n;
                            grid[y][x] = 1; // newly affected
                        }
                    }
                }
            }//end of while()

            return total_walls;
        }

        /**
         * DFS
         * The key here is that DFS not only gets the connected component, here it
         * als returns :
         * 1.Number of walls needed ("walls"
         * 2.The cells that will be infected ("Set<Integer> next)")
         */
        private void helper(int x, int y, int m, int n,
                            int[][] grid,
                            int[] visited,
                            Set<Integer> next) {
            int dirs[][] = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

            if (x < 0 || x >= n || y < 0 || y >= m || grid[y][x] == 2) {
                return;
            }

            int key = y * n + x;

            if (visited[key] == 1) {
                return;
            }

            // need one wall
            if (grid[y][x] == 0) {
                walls++;
                next.add(key);
                return;
            }

            visited[key] = 1;
            curr.add(key);

            for (int i = 0; i < 4; ++i) {
                helper(x + dirs[i][0], y + dirs[i][1], m, n, grid, visited, next);
            }
        }
    }
}