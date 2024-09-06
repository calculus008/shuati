package leetcode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class LE_909_Snakes_And_Ladders {
    /**
     * On an N x N board, the numbers from 1 to N*N are written boustrophedonically starting
     * from the bottom left of the board, and alternating direction each row.  For example,
     * for a 6 x 6 board, the numbers are written as follows:
     *
     *
     * You start on square 1 of the board (which is always in the last row and first column).
     * Each move, starting from square x, consists of the following:
     *
     * You choose a destination square S with number x+1, x+2, x+3, x+4, x+5, or x+6, provided
     * this number is <= N*N. (This choice simulates the result of a standard 6-sided die roll
     * : ie., there are always at most 6 destinations, regardless of the size of the board.)
     * If S has a snake or ladder, you move to the destination of that snake or ladder.
     * Otherwise, you move to S.
     *
     * A board square on row r and column c has a "snake or ladder" if board[r][c] != -1.
     * The destination of that snake or ladder is board[r][c].
     *
     * Note that you only take a snake or ladder at most once per move: if the destination to
     * a snake or ladder is the start of another snake or ladder, you do not continue moving.
     * (For example, if the board is `[[4,-1],[-1,3]]`, and on the first move your destination
     * square is `2`, then you finish your first move at `3`, because you do not continue moving to `4`.)
     *
     * Return the least number of moves required to reach square N*N.  If it is not possible, return -1.
     *
     * Example 1:
     *
     * Input: [
     * [-1,-1,-1,-1,-1,-1],
     * [-1,-1,-1,-1,-1,-1],
     * [-1,-1,-1,-1,-1,-1],
     * [-1,35,-1,-1,13,-1],
     * [-1,-1,-1,-1,-1,-1],
     * [-1,15,-1,-1,-1,-1]]
     *
     * Output: 4
     *
     * Explanation:
     * At the beginning, you start at square 1 [at row 5, column 0].
     * You decide to move to square 2, and must take the ladder to square 15.
     * You then decide to move to square 17 (row 3, column 5), and must take the snake to square 13.
     * You then decide to move to square 14, and must take the ladder to square 35.
     * You then decide to move to square 36, ending the game.
     * It can be shown that you need at least 4 moves to reach the N*N-th square, so the answer is 4.
     *
     * Note:
     *
     * 2 <= board.length = board[0].length <= 20
     * board[i][j] is between 1 and N*N or is equal to -1.
     * The board square with number 1 has no snake or ladder.
     * The board square with number N*N has no snake or ladder.
     *
     * Medium
     *
     * https://leetcode.com/problems/snakes-and-ladders
     */

    /**
     * "Return the least number of moves required to reach square N*N" -> shortest path -> BFS
     *
     * Each cell by default has "-1", if it's not, it "has a snake ladder", the value is the destination cell.
     * You can just take one snake ladder in each move.
     *
     * For each move, you have 6 possible destinations, should be be cur + 1 to cur +_ 6 without snake ladder.
     * With snake ladder, there are other destinations.
     *
     * Key points:
     * 1.如果有snake ladder, 必须take且只能take一次。So the branching factor in BFS is still 6.
     * 2.需要有value to cell coordinates 的 mapping, 注意，grid是上下颠倒，而且zig zag.
     * 3.BFS -> mark visited
     */
    class Solution_BFS_1 {
        public int snakesAndLadders(int[][] board) {
            int n = board.length;
            int end = n * n;
            boolean[] visited = new boolean[n * n + 1];
            /**
             * !!!
             * visited[1] process is in the BFS loop, not here.
             * if set visited[1] to true here, it will fails.
             */
            visited[0] = true;

            Queue<Integer> q = new LinkedList<>();
            q.offer(1);
            int steps = 0;

            while (!q.isEmpty()) {
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    int cur = q.poll();
                    /**
                     * Mark visited when the cell popped out of q.
                     * Think it this way: at this point, we start from this cell to move on,
                     * it is the start point for next move. So here if we take ladder,
                     * we only count it as one move, so only the destination of the ladder
                     * will be marked as visited.
                     */
                    if (visited[cur]) continue;

                    visited[cur] = true;
                    if (cur == n * n) {
                        return steps;
                    }

                    /**
                     * "j <= 6 && cur + j <= end"
                     */
                    for (int j = 1; j <= 6 && cur + j <= end ; j++) {
                        int next = cur + j;

                        /**
                         * With the constrain of using ladder,
                         * the branching factor in BFS is still 6.
                         */
                        int val = getBoardValue(board, next);
                        if (val > 0) {
                            next = val;
                        }

                        if (!visited[next]) {
                            q.offer(next);
                        }
                    }
                }
                steps++;
            }

            return -1;
        }

        private int getBoardValue(int[][] board, int num) {
            int n = board.length;
            int r = (num - 1) / n;

            /**
             * upside-down conversion
             */
            int x = n - 1 - r;


            /**
             * zig-zag adjustment
             * At row 0: left -> right, (y + 1) + n * r = num => y = num - 1 - r * n
             * At row 1: right -> left, (n - 1) - (y - 1) + n * r = num
             *                              n - y + n * r = num
             *                              => y = n + r * n - num
             * .....
             *
             * x is transformed as upside-down index, r is not, we still use r to calculate column
             * zig-zag conversion
             */
            int y = r % 2 == 0 ? num - 1 - r * n : n + r * n - num;
            return board[x][y];
        }
    }

    /**
     * Variation: 梯子可以无限次使用, 梯子和走x步只能选一.
     */
    class Solution_BFS_1_Variation {
        public int snakesAndLadders(int[][] board) {
            int n = board.length;
            int end = n * n;
            boolean[] visited = new boolean[n * n + 1];
            visited[0] = true;

            Queue<Integer> q = new LinkedList<>();
            q.offer(1);
            int steps = 0;

            while (!q.isEmpty()) {
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    int cur = q.poll();
                    if (visited[cur]) continue;

                    visited[cur] = true;
                    if (cur == n * n) {
                        return steps;
                    }

                    for (int j = 1; j <= 6 && cur + j <= end ; j++) {
                        int next = cur + j;
                        int val = getBoardValue(board, next);

                        /**
                         * !!!
                         * 假如题意是：
                         * next 到达梯子处，可以选择用或不用梯子，可以连续使用梯子：
                         * next 和 每一个梯子的终点都属于BFS的当前的这一层，应该放入q.
                         */
                        while (val > 0) {
                            int s = val;
                            if (!visited[s]) {
                                q.offer(s);
                            }
                            val = getBoardValue(board, s);
                        }

                        if (!visited[next]) {
                            q.offer(next);
                        }
                    }
                }
                steps++;
            }

            return -1;
        }

        private int getBoardValue(int[][] board, int num) {
            int n = board.length;
            int r = (num - 1) / n;

            int x = n - 1 - r;//upside-down adjustment
            int y = r % 2 == 0 ? num - 1 - r * n : n + r * n - num;

            return board[x][y];
        }
    }


    /**
     * Convert to 1D + BFS
     *
     * https://leetcode.com/problems/snakes-and-ladders/solution/
     *
     * Time  : O(N ^ 2)
     * Space : O(N ^ 2)
     */
    class Solution_BFS_2 {
        public int snakesAndLadders(int[][] board) {
            int N = board.length;

            /**
             * Map
             * key   : an integer representing an location on chessboard
             * value : number of steps taken to get to this location (from start point 1)
             */
            Map<Integer, Integer> dist = new HashMap();
            dist.put(1, 0);

            Queue<Integer> queue = new LinkedList();
            queue.add(1);

            while (!queue.isEmpty()) {
                int cur = queue.remove();

                /**
                 * !!!
                 * we get to the destination
                 */
                if (cur == N * N) {
                    return dist.get(cur);
                }

                /**
                 * For every step, move range is s + 6 and it is bounded by N*N
                 */
                for (int s2 = cur + 1; s2 <= Math.min(cur + 6, N * N); s2++) {
                    int rc = get(s2, N);
                    int r = rc / N, c = rc % N;

                    int s2Final = board[r][c] == -1 ? s2 : board[r][c];

                    if (!dist.containsKey(s2Final)) {
                        dist.put(s2Final, dist.get(cur) + 1);
                        queue.add(s2Final);
                    }
                }
            }

            return -1;
        }

        /**
         * Map from grid number to 2D array coordination.
         *
         * s = row * N + col + 1
         *
         * Note:
         * start at square 1 [at row 5, column 0].
         */
        public int get(int s, int N) {
            // Given a square num s, return board coordinates (r, c) as r*N + c
            int x = (s - 1) / N;//current row
            int y = (s - 1) % N; //current col

            /**
             * adjust for zig-zag (col) and upside-down (row)
             *
             * col :
             * left to right, then right to left
             */
            int row = N - 1 - x;
            int col = row % 2 != N % 2 ? y : N - 1 - y;
            return row * N + col;
        }
    }
}
