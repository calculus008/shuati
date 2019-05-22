package leetcode;

import java.util.LinkedList;
import java.util.Queue;

import common.Coordinate;
import common.UnionFindWithCount1;

/**
 * Created by yuank on 3/24/18.
 */
public class LE_200_Number_Of_Islands {
    /**
        Given a 2d grid map of '1's (land) and '0's (water), count the number of islands.
        An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically.
        You may assume all four edges of the grid are all surrounded by water.

        Example 1:

        11110
        11010
        11000
        00000
        Answer: 1

        Example 2:

        11000
        11000
        00100
        00011
        Answer: 3
     */
    /**
        Solution 1: DFS, Time and Space : O(m * n)
        Have potential of stack overflow, not recommended
     **/
    class Solution1 {
        public int numIslands(char[][] grid) {
            if (grid == null || grid.length == 0) return 0;

            int res = 0;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == '1') {
                        helper(grid, i, j);
                        res++;
                    }
                }
            }

            return res;
        }

        private void helper(char[][] grid, int x, int y) {
            if (x < 0 || y < 0 || x >= grid.length || y >= grid[0].length || grid[x][y] != '1') {
                return;
            }

            /**
             * !!!
             * 千万别忘了这一步!!!
             */
            grid[x][y] = '0';

            helper(grid, x + 1, y);
            helper(grid, x - 1, y);
            helper(grid, x, y + 1);
            helper(grid, x, y - 1);
        }
    }

    /**
     * Same DFS, but not modify input grid array, using visited to mark visited island
     */
    class Solution1_1 {
        public int numIslands(char[][] grid) {
            if (grid == null || grid.length == 0) return 0;

            int m = grid.length;
            int n = grid[0].length;

            int res = 0;

            boolean visited[][] = new boolean[m][n];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    /**
                     * !!!
                     * #1. "&& !visited[i][j]
                     */
                    if (grid[i][j] == '1' && !visited[i][j]) {
                        helper(grid, m, n, i, j, visited);
                        res++;
                    }
                }
            }

            return res;
        }

        private void helper(char[][] grid, int m, int n, int x, int y, boolean[][] visited) {
            /**
             * #2
             */
            if (x < 0 || x >= m || y < 0 || y >= n || visited[x][y] || grid[x][y] != '1') return;

            /**
             * #3
             */
            visited[x][y] = true;

            helper(grid, m, n, x + 1, y, visited);
            helper(grid, m, n, x - 1, y, visited);
            helper(grid, m, n, x, y + 1, visited);
            helper(grid, m, n, x, y - 1, visited);
        }
    }

    class Solution1_DFS_Practice {
        public int numIslands(char[][] grid) {
            if (grid == null || grid.length == 0) return 0;

            int m = grid.length;
            int n = grid[0].length;
            int res = 0;
            boolean[][] visited = new boolean[m][n];

            for (int i = 0; i < m ; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == '1' && !visited[i][j]) {
                        helper(grid, m, n, i, j, visited);
                        res++;
                    }
                }
            }

            return res;
        }

        private void helper(char[][] grid, int m, int n, int x, int y, boolean[][] visited) {
            if (x < 0 || x >= m || y < 0 || y >= n || visited[x][y] || grid[x][y] != '1') {
                return;
            }

            visited[x][y] = true;
            helper(grid, m, n, x + 1, y, visited);
            helper(grid, m, n, x - 1, y, visited);
            helper(grid, m, n, x, y + 1, visited);
            helper(grid, m, n, x, y - 1, visited);
        }

    }

    /**
     * Solution 2 : BFS, preferred. DFS has the danger of stack overflow if recursion depth is too big
     */
    class Solution2 {
        public int numIslands_JiuZhang(boolean[][] grid) {
            if (grid == null || grid.length == 0 || grid[0].length == 0) {
                return 0;
            }

            int n = grid.length;
            int m = grid[0].length;
            int islands = 0;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (grid[i][j]) {
                        markByBFS(grid, i, j);
                        islands++;
                    }
                }
            }

            return islands;
        }

        /**
         * Start from given coordinate which is true, "sink" the island (set all on the island to false)
         * in BFS
         */
        private void markByBFS(boolean[][] grid, int x, int y) {
            // magic numbers!
            int[] directionX = {0, 1, -1, 0};
            int[] directionY = {1, 0, 0, -1};

            Queue<Coordinate> queue = new LinkedList<>();

            queue.offer(new Coordinate(x, y));
            grid[x][y] = false;

            while (!queue.isEmpty()) {
                Coordinate coor = queue.poll();
                for (int i = 0; i < 4; i++) {
                    Coordinate adj = new Coordinate(
                            coor.x + directionX[i],
                            coor.y + directionY[i]
                    );
                    if (!inBound(adj, grid)) {
                        continue;
                    }
                    if (grid[adj.x][adj.y]) {
                        grid[adj.x][adj.y] = false;
                        queue.offer(adj);
                    }
                }
            }
        }

        private boolean inBound(Coordinate coor, boolean[][] grid) {
            int n = grid.length;
            int m = grid[0].length;

            return coor.x >= 0 && coor.x < n && coor.y >= 0 && coor.y < m;
        }
    }

    /**
     * BFS with visited 2D array
     */
    class Solution2_1 {
        public int numIslands(char[][] grid) {
            if (grid == null || grid.length == 0) return 0;

            int m = grid.length;
            int n = grid[0].length;

            int res = 0;

            boolean visited[][] = new boolean[m][n];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    /**
                     * !!!
                     * "grid[i][j] == '1' && !visited[i][j]"
                     */
                    if (grid[i][j] == '1' && !visited[i][j]) {
                        helper(grid, m, n, i, j, visited);
                        res++;
                    }
                }
            }

            return res;
        }

        int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        private void helper(char[][] grid, int m, int n, int x, int y, boolean[][] visited) {
            Queue<int[]> q = new LinkedList<>();
            /**
             * !!!
             * Mark visited when enqueue the element
             */
            q.offer(new int[] {x, y});
            visited[x][y] = true;

            while (!q.isEmpty()) {
                int[] cur = q.poll();

                for (int[] dir : dirs) {
                    /**
                     * !!!
                     */
                    int nx = cur[0] + dir[0];
                    int ny = cur[1] + dir[1];

                    /**
                     * !!!
                     * Compare with DFS, validation happens when branching to 4 directions,
                     * before adding element into q and mark visited
                     * !!!
                     *
                     * "grid[nx][ny] != '1' || visited[nx][ny]"
                     */
                    if (nx < 0 || nx >= m || ny < 0 || ny >= n || grid[nx][ny] != '1' || visited[nx][ny]) {
                        continue;
                    }

                    /**
                     * !!!
                     * Mark visited when enqueue the element
                     */
                    q.offer(new int[]{nx, ny});
                    visited[nx][ny] = true;
                }
            }
        }
    }

    /**
     * Solution 3
     * Union Find
     * Time and Space : O(m * n)
     *
     * 1.Create UnionFindWithCount, keep count of islands.
     * 2."parents[]" is initialized with size n * m
     * 3.Initial number of islands should be the total number of element with "TRUE" value (here grid is boolean type)
     * 4.Traverse grid, for each element grid[i][j], check its 4 neighbours, if it's TRUE, union (i, j) with it.
     * 5.Trick of using 1D parents[] array for 2D array grid[][]:
     *   convert 2D to 1D : col = i * n + y
     */
    public class Solution3 {
        int m;
        int n;

        public int numIslands(boolean[][] grid) {
            if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;

            m = grid.length;
            n = grid[0].length;

            UnionFindWithCount1 uf = new UnionFindWithCount1(m * n);

            int total = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j]){
                        total++;
                    }
                }
            }

            //!!!
            uf.setCount(total);

            int[][] dir = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j]) {
                        for (int k = 0; k < dir.length; k++) {
                            int nextX = i + dir[k][0];
                            int nextY = j + dir[k][1];
                            if (isValid(nextX, nextY) && grid[nextX][nextY]) {
                                uf.union(getIdx(i, j), getIdx(nextX, nextY));
                            }
                        }
                    }
                }
            }

            return uf.getCount();
        }

        private boolean isValid(int x, int y) {
            return (x >= 0 && x < m && y >= 0 && y < n);
        }

        private int getIdx(int x, int y) {
            return x * n + y;
        }


        /**
         * Solution 4
         * Optimized version
         *
         * 1.只遍历一次grid[][], 我们并不需要初始化count.一边遍历，一边统计total, 最后total加上getCount()即可(getCount()此时返回的是个负数）
         *
         * 2.但是对于每块陆地，只需要检测其右面和下面是否是陆地然后做connect，不需要对左和上再作判断，因为遍历顺序是从上到下，从左到右。
         *
         */
        public int numIslands2(boolean[][] grid) {
            // write your code here
            if (grid == null || grid.length == 0 || grid[0].length == 0){
                return 0;
            }
            int n = grid.length;
            int m = grid[0].length;
            int total = 0;
            UnionFindWithCount1 uf = new UnionFindWithCount1(n * m);

            int[] offsetX = new int[]{0, -1};
            int[] offsetY = new int[]{-1, 0};

            for (int i = 0; i < n; i++){
                for (int j = 0; j < m; j++){
                    if (!grid[i][j]){
                        continue;
                    }
                    total++;
                    for (int k = 0; k < 2; k++){
                        int x = i + offsetX[k];
                        int y = j + offsetY[k];
                        if (x >= 0 && y >= 0 && grid[x][y]){
                            uf.union(i * m + j, x * m + y);
                        }
                    }
                }
            }
            return uf.getCount() + total;
        }

    }
}
