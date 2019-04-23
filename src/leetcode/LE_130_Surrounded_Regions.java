package leetcode;

import common.UnionFindWithCount1;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by yuank on 3/15/18.
 */
public class LE_130_Surrounded_Regions {
    /**
        Given a 2D board containing 'X' and 'O' (the letter O), capture all regions surrounded by 'X'.

        A region is captured by flipping all 'O's into 'X's in that surrounded region.

        For example,
        X X X X
        X O O X
        X X O X
        X O X X

        After running your function, the board should be:

        X X X X
        X X X X
        X X X X
        X O X X
     */

    /** Solution 1
     *  DFS
     *  Space and Time : O(m * n)
     **/

    public static void solve(char[][] board) {
        if (board == null || board.length == 0) return;

        int m = board.length;
        int n = board[0].length;

        /**
            'O'只可能在四条边上才不能被'X'包围，而且所有和这个边上的'O'相邻的‘O'也不是被‘X'包围的。
            所以:
            1.遍历每一条边，找'O'，然后以它为起点进行DFS，把所有找到的相邻的'O'设置为'1'.
            2.遍历整个矩阵，把所有还存在的'O'设置为'X',把'1'还原成'O'
        **/

        for (int i = 0; i < n; i++) {
            if (board[0][i] == 'O') {
                helper(board, 0, i);
            }
        }

        for (int i = 0; i < n; i++) {
            if (board[m - 1][i] == 'O') {
                helper(board, m - 1, i);
            }
        }

        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O') {
                helper(board, i, 0);
            }
        }

        for (int i = 0; i < m; i++) {
            if (board[i][n - 1] == 'O') {
                helper(board, i, n - 1);
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                } else if (board[i][j] == '1') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    public static void helper(char[][] board, int i, int j) {
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] != 'O') return;

        board[i][j] = '1';

        helper(board, i + 1, j);
        helper(board, i - 1, j);
        helper(board, i, j + 1);
        helper(board, i, j - 1);
    }

    /**
     * Solution 2
     * Union Find
     * Time and Space : O(m * n)
     *
     */
    public class Solution {
        public void surroundedRegions(char[][] board) {
            if (board == null || board.length == 0 || board[0].length == 0) return;

            int m = board.length;
            int n = board[0].length;
            int total = m * n;
            int[][] dir = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
            UnionFindWithCount1 uf = new UnionFindWithCount1(total);

            /**
             * First loop, use UnionFind to separate 'O' cells on the outside
             * and the 'O' inside.
             */
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (board[i][j] == 'X') continue;

                    if (i == 0 || i == m - 1 || j == 0 || j == n - 1) {
                        /**
                         * Each outside 'O' cell is unioned with id value m * n.
                         * Based on union by rank logic in union(), parent ID is always
                         * the one with larger value, so this set of cells will all have
                         * parent ID as m * n (or "total")
                         */
                        uf.union(i * n + j, total);
                    } else {
                        for (int k = 0; k < dir.length; k++) {
                            int nextX = i + dir[k][0];
                            int nextY = j + dir[k][1];

                            /**
                             * !!! Must check the value for newly calculated location
                             */
                            if (board[nextX][nextY] == 'O') {
                                /**
                                 * For inside 'O' cells, union current one with 'O' cells
                                 * in its 4 neighbours. Their parent ID will be smaller
                                 * than m * n. So they are in different set.
                                 */
                                uf.union(i * n + j, nextX * n + nextY);
                            }
                        }
                    }
                }
            }

            /**
             * Second loop, convert the inside 'O' cells to 'X'
             */
            for (int i = 1; i < m - 1; i++) {
                for (int j = 1; j < n - 1; j++) {
                    if (board[i][j] == 'O') {
                        if (uf.query(i * n + j) != total) {
                            board[i][j] = 'X';
                        }
                    }
                }
            }
        }
    }

    /** version: 高频题班
     *  BFS
     **/
    public class Solution3 {
        public void surroundedRegions(char[][] board) {
            int n = board.length;
            if (n == 0) {
                return;
            }
            int m = board[0].length;

            for (int i = 0; i < n; i++) {
                bfs(board, i, 0);
                bfs(board, i, m - 1);
            }

            for (int j = 0; j < m; j++) {
                bfs(board, 0, j);
                bfs(board, n - 1, j);
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (board[i][j] == 'W') {
                        board[i][j] = 'O';
                    } else {
                        board[i][j] = 'X';
                    }
                }
            }
        }

        void bfs(char[][] board, int sx, int sy) {
            if (board[sx][sy] != 'O') {
                return;
            }
            int n = board.length;
            int m = board[0].length;
            int[] dx = {0, 1, 0, -1};
            int[] dy = {1, 0, -1, 0};

            Queue<Integer> qx = new LinkedList<>();
            Queue<Integer> qy = new LinkedList<>();
            qx.offer(sx);
            qy.offer(sy);
            board[sx][sy] = 'W'; // 'W' -> Water

            while (!qx.isEmpty()) {
                int cx = qx.poll();
                int cy = qy.poll();

                for (int i = 0; i < 4; i++) {
                    int nx = cx + dx[i];
                    int ny = cy + dy[i];
                    if (0 <= nx && nx < n && 0 <= ny && ny < m && board[nx][ny] == 'O') {
                        board[nx][ny] = 'W'; // 'W' -> Water
                        qx.offer(nx);
                        qy.offer(ny);
                    }
                }
            }
        }
    }

}
