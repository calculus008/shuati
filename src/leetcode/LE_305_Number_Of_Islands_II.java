package leetcode;

import common.Point;
import common.UnionFindWithCount;
import common.UnionFindWithCount1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuank on 4/25/18.
 */
public class LE_305_Number_Of_Islands_II {
    /**
         A 2d grid dist of m rows and n columns is initially filled with water.
         We may perform an addLand operation which turns the water at position (row, col) into a land.
         Given a list of positions to operate, count the number of islands after each addLand operation.
         An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically.
         You may assume all four edges of the grid are all surrounded by water.

         Example:

         Given m = 3, n = 3, positions = [[0,0], [0,1], [1,2], [2,1]].
         Initially, the 2d grid grid is filled with water. (Assume 0 represents water and 1 represents land).

         0 0 0
         0 0 0
         0 0 0
         Operation #1: addLand(0, 0) turns the water at grid[0][0] into a land.

         1 0 0
         0 0 0   Number of islands = 1
         0 0 0
         Operation #2: addLand(0, 1) turns the water at grid[0][1] into a land.

         1 1 0
         0 0 0   Number of islands = 1
         0 0 0
         Operation #3: addLand(1, 2) turns the water at grid[1][2] into a land.

         1 1 0
         0 0 1   Number of islands = 2
         0 0 0
         Operation #4: addLand(2, 1) turns the water at grid[2][1] into a land.

         1 1 0
         0 0 1   Number of islands = 3
         0 1 0
         We return the result as an array: [1, 1, 2, 3]

         Challenge:

         Can you do it in time complexity O(k log mn), where k is the length of the positions?

        Hard
     */

    /**
     * Solution 1 : A partial Union Find Solution
     *
     *  Key :
     *  1.Convert 2D position to a 1D index;
     *  2.Each time we add a land in a cell, check its 4 neighbours, if the neighbour is also a land
     *    and it is not merged with the current one, do merge. Keep count of the connected components
     *
     *  Time : O(n ^ 2), (not doing optimization (path compression and merge by rank))
     *  Space : O(n)
     */
    public List<Integer> numIslands2_1(int m, int n, int[][] positions) {
        List<Integer> res = new ArrayList<>();
        if (positions == null || m <= 0 || n <= 0) return res;

        int[] roots = new int[m * n];
        //!!!
        Arrays.fill(roots, -1);

        int count = 0;
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};//define directions to move

        for (int[] position : positions) {
            int pos = position[0] * n + position[1];
            /**
             * !!!
             * The cell with no land has value -1.
             */
            roots[pos] = pos;
            count++;

            for(int[] dir : dirs) {
                int x = position[0] + dir[0];
                int y = position[1] + dir[1];
                int curPos = x * n + y;

                /**
                 !!!"roots[curPos] == -1", this means this cell is not yet filled with land
                 */
                if (x < 0 || x >= m || y < 0 || y >=n || roots[curPos] == -1) {
                    continue;
                }

                int clusterId = find(roots, curPos);
                if (clusterId != pos) {//adjacent cells not in the same cluster, merge
                    /**
                     * the newly added land is merged to existing land
                     */
                    roots[pos] = clusterId;
                    /**
                     * !!!
                     * Don't forget to update value in pos which will be used in the next loop
                     */
                    pos = clusterId;
                    count--;
                }
            }

            res.add(count);
        }

        return res;
    }

    private int find(int[] roots, int n) {
        while (roots[n] != n) {
            roots[n] = roots[roots[n]]; //path compression, with this line, LE run time is saved 7 ms (19 ms vs 26 ms)
            n = roots[roots[n]];
        }
        return n;
    }

    /**
     * Solution 2
     * A full Union Find solution : Solution 3 at
     * https://leetcode.com/articles/number-of-islands-ii/
     *
     * Time complexity : O(m * n + L) where L is the number of operations,
     * m is the number of rows and n is the number of columns.
     *
     * It takes O(mn) to initialize UnionFind, and O(L) to process positions.
     * Note that Union operation takes essentially constant time when UnionFind
     * is implemented with both path compression and union by rank.
     *
     * Space complexity : O(mn) as required by UnionFind data structure.
     */

    public List<Integer> numIslands2_2(int m, int n, int[][] positions) {
        List<Integer> res = new ArrayList<>();
        if (positions == null || m <= 0 || n <= 0) return res;

        UnionFindWithCount ufs = new UnionFindWithCount(m * n);
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};//define directions to move

        for (int[] position : positions) {
            int pos = position[0] * n + position[1];
            ufs.setParent(pos);

            List<Integer> lands = new ArrayList<>();

            for (int[] dir : dirs) {
                int x = position[0] + dir[0];
                int y = position[1] + dir[1];
                int curPos = x * n + y;

                if(x >= 0 && x < m && y >= 0 && y < n && ufs.isValid(curPos)) {
                    lands.add(curPos);
                }
            }

            for (int land : lands) {
                ufs.union(land, pos);
            }

            res.add(ufs.getCount());
        }

        return res;
    }


    /**
     * Solution 3
     * UnionFind
     * Use the same Union Find class "UnionFindWithCount1" as LE_200_Number_Of_Islands
     *
     * Time  : O(m * n + L) (L is length of operators)
     * Space : O(m * n)
     *
     * Delta of number of islands for each operator:
     *
     * 1 (flip one cell to true or add one island) - number of merging with existing islands with 4 possible neighbours
     *
     * So the range of delta is -3 to 1.
     *
     */
    public class Solution {
        public List<Integer> numIslands2(int n, int m, Point[] operators) {
            List<Integer> res = new ArrayList<>();
            if (operators == null || operators.length == 0) return res;

            //init grid since it's not provided in input params
            boolean[][] grid = new boolean[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    grid[i][j] = false;
                }
            }

            UnionFindWithCount1 uf = new UnionFindWithCount1(n * m);

            for (Point operator : operators) {
                int x = operator.x, y = operator.y;

                /**
                 * !!!
                 * if given coordinate is not valid or cell value
                 * is already true, bypass the union step
                 * 如果要把已经是岛的地方再次变成岛，则无需有任何操作
                 * **/
                if (isValid(x, y, grid) && !grid[x][y]) {
                    grid[x][y] = true;

                    /**
                     * !!!we are adding one land, so increase count in uf here,
                     * union() method will take care of decreasing count when merge
                     **/
                    uf.setCount(uf.getCount() + 1);

                    int[][] dir = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
                    for (int k = 0; k < dir.length; k++) {
                        int nextX = x + dir[k][0];
                        int nextY = y + dir[k][1];

                        /**
                         * "grid[nextX][nextY]"
                         * !!!
                         * Only do union() if the valid
                         * neighbour is TRUE
                         **/
                        if (isValid(nextX, nextY, grid) && grid[nextX][nextY]) {
                            uf.union(getIdx(x, y, m), getIdx(nextX, nextY, m));
                        }
                    }
                }

                res.add(uf.getCount());
            }

            return res;
        }


        private boolean isValid(int x, int y, boolean[][] grid) {
            int m = grid.length;
            int n = grid[0].length;
            return (x >= 0 && x < m && y >= 0 && y < n);
        }

        private int getIdx(int x, int y, int n) {
            return x * n + y;
        }
    }

    class Solution_Practice {
        class UFS {
            int[] parents;
            int count;

            public UFS(int n) {
                count = 0;
                parents = new int[n + 1];
                for (int i = 0; i < parents.length; i++) {
                    parents[i] = i;
                }
            }

            public int find(int u) {
                while (parents[u] != u) {
                    parents[u] = parents[parents[u]];
                    u = parents[u];
                }
                return u;
            }

            public boolean union(int u, int v) {
                int pu = find(u);
                int pv = find(v);

                if (pu == pv) return false;

                parents[pu] = pv;
                count--;
                return true;
            }

            public void setCount(int n) {
                count = n;
            }

            public int getCount() {
                return count;
            }
        }

        int[] parents;

        public List<Integer> numIslands2(int m, int n, int[][] positions) {
            List<Integer> res = new ArrayList<>();
            if (positions == null || positions.length == 0) return res;

            int size = m * n;
            UFS ufs = new UFS(size);

            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            boolean[][] grid = new boolean[m][n];

            for (int[] position : positions) {
                int r = position[0];
                int c = position[1];

                /**
                 * can't use :
                 * "if (!isValid(r,c,m,n) || grid[r][c]) continue"
                 *
                 * Because it will miss the line :
                 * "res.add(ufs.getCount());"
                 *
                 * So if there's invalid cell, we will miss a value in res.
                 */
                if (isValid(r, c, m, n) && !grid[r][c]) {
                    /**
                     * !!!
                     */
                    grid[r][c] = true;
                    /**
                     * !!!
                     */
                    ufs.setCount(ufs.getCount() + 1);

                    for (int[] dir : dirs) {
                        int x = r + dir[0];
                        int y = c + dir[1];

                        if (isValid(x, y, m, n) && grid[x][y]) {
                            ufs.union(getIdx(x, y, n), getIdx(r, c, n));
                        }

                    }
                }

                res.add(ufs.getCount());
            }

            return res;
        }

        private boolean isValid(int x, int y, int m, int n) {
            return x >= 0 && x < m && y >= 0 && y < n;
        }

        private int getIdx(int x, int y, int n) {
            return x * n + y;
        }

    }

}
