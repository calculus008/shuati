package leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class LE_200_Number_Of_Islands_2 {
    class Solution_DFS {
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

            grid[x][y] = '0';

            helper(grid, x + 1, y);
            helper(grid, x - 1, y);
            helper(grid, x, y + 1);
            helper(grid, x, y - 1);
        }
    }

    class Solution1_DFS_With_Visited {
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

    class Solution_BFS_With_Visited {
        public int numIslands(char[][] grid) {
            if (grid == null || grid.length == 0) return 0;

            int m = grid.length;
            int n = grid[0].length;

            int res = 0;

            boolean visited[][] = new boolean[m][n];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
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
            q.offer(new int[] {x, y});
            visited[x][y] = true;

            while (!q.isEmpty()) {
                int[] cur = q.poll();

                for (int[] dir : dirs) {
                    int nx = cur[0] + dir[0];
                    int ny = cur[1] + dir[1];

                    if (nx < 0 || nx >= m || ny < 0 || ny >= n || grid[nx][ny] != '1' || visited[nx][ny]) {
                        continue;
                    }

                    q.offer(new int[]{nx, ny});
                    visited[nx][ny] = true;
                }
            }
        }
    }

    class Solution_UnionFind {
        public class UnionFind {
            int count;
            int[] parents;

            public UnionFind(int size) {
                count = 0;
                parents = new int[size + 1];
                for (int i = 0; i < parents.length; i++) {
                    parents[i] = i;
                }
            }

            public void union(int u, int v) {
                int pu = find(u);
                int pv = find(v);
                if (pu == pv) return;

                parents[pu] = pv;
                count--;
            }

            public int find(int x) {
                if (parents[x] != x) {
                    parents[x] = find(parents[x]);
                }

                return parents[x];
            }
        }

        /**
         * Can be optimized for only go right an down, since the iteration is from up-left to right down.
         */
        //    int[][] distance = {{1,0},{0,1}};
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

        public int numIslands(char[][] grid) {
            if (grid == null || grid.length == 0 || grid[0].length == 0)  {
                return 0;
            }

            int m = grid.length;
            int n = grid[0].length;
            UnionFind uf = new UnionFind(m * n);

            int total = 0;

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] != '1') continue;

                    total++;

                    for (int[] d : dirs) {
                        int x = i + d[0];
                        int y = j + d[1];

                        if (x >= 0 && x < m && y >= 0 && y < n && grid[x][y] == '1') {
                            uf.union(i * n + j, x * n + y);
                        }
                    }
                }
            }

            return uf.count + total;
        }
    }
}
