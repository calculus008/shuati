package leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by yuank on 4/19/18.
 */
public class LE_289_Game_Of_Life {
    /**
         According to the Wikipedia's article: "The Game of Life, also known simply as Life,
         is a cellular automaton devised by the British mathematician John Horton Conway in 1970."

         Given a board with m by n cells, each cell has an initial state live (1) or dead (0).
         Each cell interacts with its eight neighbors (horizontal, vertical, diagonal) using the
         following four rules
         (taken from the above Wikipedia article):

         #1.Any live cell with fewer than two live neighbors dies, as if caused by under-population.
         #2.Any live cell with two or three live neighbors lives on to the next generation.
         #3.Any live cell with more than three live neighbors dies, as if by over-population..
         #4.Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.

         Write a function to compute the next state (after one update) of the board given its current state.

         Follow up:
         1.Could you solve it in-place? Remember that the board needs to be updated at the same time:
         You cannot update some cells first and then use their updated values to update other cells.

         2.In this question, we represent the board using a 2D array. In principle, the board is infinite,
         which would cause problems when the active area encroaches the border of the array.
         How would you address these problems

        Medium
     */

    /**
     * Time : O(mn)
     * Space : O(1)
     */
    class Solution1 {
        private int die = 2;
        private int live = 3;

        public void gameOfLife(int[][] board) {
            /**
             * The tricky part is how we change the value in cell and
             * still be able to track what is its initial state.
             *
             * Solution:
             * We only flip the 1 to "die"(2) and 0 to "live"(3)
             * so when we find a die around, it must be a previous 1
             * then we can count the 1s without being affected
             **/
            int rows = board.length;
            int cols = board[0].length;

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    int around = countLive(i, j, board);

                    if (board[i][j] == 0 && around == 3) {//#4, original state is "dead" and there are 3 neighbours in "live" state
                        board[i][j] = live;
                    } else if (board[i][j] == 1) {//original state is "live"
                        if (around == 2 || around == 3) {//#2, keep as "live"
                            continue;
                        }
                        if (around < 2 || around > 3) {//#1 or #3, original "live" to "dead"
                            board[i][j] = die;
                        }
                    }
                }
            }

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (board[i][j] == die) {
                        board[i][j] = 0;
                    }
                    if (board[i][j] == live) {
                        board[i][j] = 1;
                    }
                }
            }

        }

        private int countLive(int i, int j, int[][] board) {
            int count = 0;
            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

            for (int[] dir : dirs) {
                int x = i + dir[0];
                int y = j + dir[1];

                if (x >= 0 && y >= 0 && x < board.length && y < board[0].length) {
                    /**
                     * "board[x][y] == die", this can only be possible by changing "1" to "2"
                     * so we know its previous state is "1".
                     */
                    if (board[x][y] == 1 || board[x][y] == die)
                        count++;
                }
            }

            return count;

        }
    }

    /**
     Time : O(mn), Space : O(1)

     We can solve it by using extra space to remember intermediate value.
     (We can't just update cell with new state value since it will impact
      the decision for other cells)

     To solve it in space O(1), the clever way is to use bit operation.
     The idea is that we can save both the old and new state value in the
     same integer number of each cell. We know that there are only 2 valid
     values in board at the beginning - 0 and 1:

     live : 01
     dead : 00

     So:

     if next round state is "live" (condition #2 and #4) : curState + 2 (10 in binary),
     first bit is current state, second bit is previous state.
     Then right shift one bit will get new state.

     live -> live : 01 -> 11
     dead -> live : 00 -> 10

     if next round state is "dead" : No need to do anything

     live -> dead : 01 -> 01
     dead -> dead : 00 -> 00

     First round of pass will calculate number of living neighbours by reading
     the right most bit of the value.
     Plus 2 if the new state is "live", do nothing if new state is "dead".

     Then with another round of pass in board, we shift each number 1 bit to
     the right to get the correct new state value.
     */

    class Solutio2 {
        public void gameOfLife(int[][] board) {
            if (board == null || board.length == 0 || board[0].length == 0) return;

            int m = board.length;
            int n = board[0].length;
            /**
             * First pass, get count and neighbours and set value
             **/
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    checkAndUpdateCell(board, i, j);
                }
            }

            /**
             * 2nd pass, do bit shift to get the final correct value for each cell
             **/
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    board[i][j] = board[i][j] >> 1;
                }
            }
        }

        public void checkAndUpdateCell(int[][] board, int i, int j) {
            int count = 0;
            //!!!"row <="
            for (int row = Math.max(i - 1, 0); row <= Math.min(i + 1, board.length - 1); row++) {
                //!!!"col <="
                for (int col = Math.max(j - 1, 0); col <= Math.min(j + 1, board[0].length - 1); col++) {
                    //!!!
                    if (row == i && col == j) continue;
                    /**
                     !!!"(board[row][col] & 1)". “&”和"=="不是同级别运算符，不加括号实际上代表"board[row][col] & (1 == 1)"
                     */
                    if ((board[row][col] & 1) == 1) {
                        count++;
                    }
                }
            }

            if (board[i][j] == 1) {//#2
                //!!! "||"
                if (count == 2 || count == 3) {
                    board[i][j] += 2;
                }
            } else if (count == 3) {//#4
                board[i][j] += 2;
            }
        }
    }

    /**
     * Follow up 2:
     * Infinite Board
     *
     * See section "Follow up 2 : Infinite Board" in https://leetcode.com/articles/game-of-life/
     *
     * If the board becomes infinitely large, there are multiple problems our current solution would run into:
     *
     * 1.It would be computationally impossible to iterate a matrix that large.
     * 2.It would not be possible to store that big a matrix entirely in memory.
     *   We have huge memory capacities these days i.e. of the order of hundreds of GBs.
     *   However, it still wouldn't be enough to store such a large matrix in memory.
     * 3.We would be wasting a lot of space if such a huge board only has a few live cells
     *   and the rest of them are all dead. In such a case, we have an extremely sparse matrix
     *   and it wouldn't make sense to save the board as a "matrix".
     *
     * If we have an extremely sparse matrix, it would make much more sense to actually save the location
     * of only the live cells and then apply the 4 rules accordingly using only these live cells.
     *
     * Essentially, we obtain only the live cells from the entire board and then apply the different rules
     * using only the live cells and finally we update the board in-place. The only problem with this solution
     * would be when the entire board cannot fit into memory. If that is indeed the case, then we would have
     * to approach this problem in a different way. For that scenario, we assume that the contents of the matrix
     * are stored in a file, one row at a time.
     *
     * In order for us to update a particular cell, we only have to look at its 8 neighbors which essentially
     * lie in the row above and below it. So, for updating the cells of a row, we just need the row above and
     * the row below. Thus, we read one row at a time from the file and at max we will have 3 rows in memory.
     * We will keep discarding rows that are processed and then we will keep reading new rows from the file,
     * one at a time.
     *
     * This is also a prefect case for solving using Map Reduce.
     *
     * https://leetcode.com/problems/game-of-life/discuss/73217/Infinite-board-solution/201780
     *
     * Only save live cells coordination and process based on it.
     */
    class Solution_Infinite_Board {
        private class Coord {
            int i;
            int j;

            private Coord(int i, int j) {
                this.i = i;
                this.j = j;
            }

            public boolean equals(Object o) {
                return o instanceof Coord && ((Coord) o).i == i && ((Coord) o).j == j;
            }

            public int hashCode() {
                int hashCode = 1;
                hashCode = 31 * hashCode + i;
                hashCode = 31 * hashCode + j;

                return hashCode;
            }
        }

        /**
         * wrapper to make the solution to work in LE env
         */
        public void gameOfLife(int[][] board) {
            Set<Coord> live = new HashSet<>();
            int m = board.length;
            int n = board[0].length;

            /**
             * convert matrix into Set of live cells Coord objects
             */
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (board[i][j] == 1) {
                        live.add(new Coord(i, j));
                    }
                }
            }

            /**
             * Generate new Set of Coord for the live cells in the next round
             * and populate the matrix.
             */
            live = gameOfLife(live);

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    board[i][j] = live.contains(new Coord(i, j)) ? 1 : 0;
                }
            }
            ;

        }

        private Set<Coord> gameOfLife(Set<Coord> live) {
            /**
             * map to count the number of "live" neighbours each "live" coordinate has
             */
            Map<Coord, Integer> neighbours = new HashMap<>();

            /**
             * Iterate through current live cells
             */
            for (Coord cell : live) {
                /**
                 * !!!
                 * Why we don't use dirs[] to go 8 directions:
                 * Since the board is infinite, we can't use the length of board to check
                 * if next coordinate is out of boundary.
                 *
                 * Instead, here, we iterate through the 3 rows and 3 cols that the current
                 * coordinate neighbours should be in.
                 */
                for (int i = cell.i - 1; i < cell.i + 2; i++) {
                    for (int j = cell.j - 1; j < cell.j + 2; j++) {
                        if (i == cell.i && j == cell.j) continue;

                        /**
                         * !!!
                         * We go over the 8 neighbours of the current live cell.
                         * For each of those cells, since it is a neighbour of
                         * the current live cell, therefore its live neighbour
                         * count increase by one, for example:
                         *
                         * [
                         *  [0,1,0],
                         *  [0,0,1],
                         *  [1,1,1],
                         *  [0,0,0]
                         * ]
                         *
                         * live neighbours count:
                         *
                         * [
                         *  [1,1,2],
                         *  [3,5,3],
                         *  [1,3,2],
                         *  [2,3,2]
                         * ]
                         */
                        Coord c = new Coord(i, j);
                        neighbours.put(c, neighbours.getOrDefault(c, 0) + 1);
                    }
                }
            }

            Set<Coord> newLive = new HashSet<>();

            for (Map.Entry<Coord, Integer> cell : neighbours.entrySet()) {
                /**!!!
                 * Here we only care about which cells will be in "live" state in tbe next round,
                 *
                 * Base on the rules, what kind of cells will be "live" in the next round?
                 * 1.Has 3 live neighbours
                 *   If init state is live, it will keep "live" (rule #2)
                 *   If init state is dead, it will change into "live" (rule #3)
                 *   "cell.getValue() == 3"
                 * Or
                 *
                 * 2.Has 2 live neighbours AND its init state is "live (rule #2)
                 *   "(cell.getValue() == 2 && live.contains(cell.getKey()))"
                 */
                if (cell.getValue() == 3 || (cell.getValue() == 2 && live.contains(cell.getKey()))) {
                    newLive.add(cell.getKey());
                }
            }

            return newLive;
        }
    }
}

