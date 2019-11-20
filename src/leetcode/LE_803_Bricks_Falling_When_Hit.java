package leetcode;

public class LE_803_Bricks_Falling_When_Hit {
    /**
     * We have a grid of 1s and 0s; the 1s in a cell represent bricks.
     * A brick will not drop if and only if it is directly connected to the top of the grid,
     * or at least one of its (4-way) adjacent bricks will not drop.
     *
     * We will do some erasures sequentially. Each time we want to do the erasure at the
     * location (i, j), the brick (if it exists) on that location will disappear, and then
     * some other bricks may drop because of that erasure.
     *
     * Return an array representing the number of bricks that will drop after each
     * erasure in sequence.
     *
     * Example 1:
     * Input:
     * grid = [[1,0,0,0],[1,1,1,0]]
     * hits = [[1,0]]
     * Output: [2]
     * Explanation:
     * If we erase the brick at (1, 0), the brick at (1, 1) and (1, 2) will drop. So we should return 2.
     * Example 2:
     * Input:
     * grid = [[1,0,0,0],[1,1,0,0]]
     * hits = [[1,1],[1,0]]
     * Output: [0,0]
     * Explanation:
     * When we erase the brick at (1, 0), the brick at (1, 1) has already disappeared due to the
     * last move. So each erasure will cause no bricks dropping.  Note that the erased brick (1, 0)
     * will not be counted as a dropped brick.
     *
     *
     * Note:
     *
     * The number of rows and columns in the grid will be in the range [1, 200].
     * The number of erasures will not exceed the area of the grid.
     * It is guaranteed that each erasure will be different from any other erasure, and located inside the grid.
     * An erasure may refer to a location with no brick - if it does, no bricks drop.
     *
     * Hard
     */

    /**
     * UnionFind
     *
     * https://leetcode.com/problems/bricks-falling-when-hit/discuss/244730/Java-Short-UnionFind-Solution-!!!
     *
     * https://leetcode.com/problems/bricks-falling-when-hit/solution/
     *
     * Time Complexity: O(N*Q*\alpha(N * Q))O(N∗Q∗α(N∗Q)), where N = R*CN=R∗C is the number of grid squares,
     * QQ is the length of hits, and \alphaα is the Inverse-Ackermann function.
     *
     * Space Complexity: O(N)
     */
    class Solution_UnionFind {
        class UnionFindWithSize {
            int[] parents;
            int[] size;

            public UnionFindWithSize(int n) {
                parents = new int[n];
                size = new int[n];

                for (int i = 0; i < parents.length; i++) {
                    parents[i] = i;
                    size[i] = 1;
                }
            }

            public int find(int n) {
                while (parents[n] != n) {
                    parents[n] = parents[parents[n]];
                    n = parents[n];
                }

                return n;
            }

            public void union(int u, int v) {
                int pu = find(u);
                int pv = find(v);

                if (pu != pv) {
                    parents[pu] = pv;
                    size[pv] += size[pu];
                }
            }

            public int getSize(int idx) {
                return size[idx];
            }
        }

        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        UnionFindWithSize ufs;

        public int[] hitBricks(int[][] grid, int[][] hits) {
            int m = grid.length;
            int n = grid[0].length;

            ufs = new UnionFindWithSize(m * n + 1);

            for (int[] hit : hits) {
                if (grid[hit[0]][hit[1]] == 1) {
                    grid[hit[0]][hit[1]] = 2;
                }
            }

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1) {
                        unionAll(grid, i, j);
                    }
                }
            }

            int[] res = new int[hits.length];
            int count = ufs.getSize(ufs.find(0));

            for (int i = hits.length - 1; i >= 0; i--) {
                int x = hits[i][0];
                int y = hits[i][1];

                if (grid[x][y] == 2) {
                    unionAll(grid, x, y);
                    grid[x][y] = 1;
                }

                int curCount = ufs.getSize(ufs.find(0));
                res[i] = curCount > count ? curCount - count - 1 : 0;
                count = curCount;
            }

            return res;
        }

        private void unionAll(int[][] grid, int i, int j) {
            int m = grid.length;
            int n = grid[0].length;

            for (int[] dir : dirs) {
                int x = i + dir[0];
                int y = j + dir[1];

                if (x < 0 || x >= m || y < 0 || y >= n || grid[x][y] != 1) continue;

                ufs.union(i * n + j + 1, x * n + y + 1);
            }

            if (i == 0) {
                ufs.union(j + 1, 0);
            }
        }
    }
}
