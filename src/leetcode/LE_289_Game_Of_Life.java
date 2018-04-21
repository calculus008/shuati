package leetcode;

/**
 * Created by yuank on 4/19/18.
 */
public class LE_289_Game_Of_Life {
    /**
         According to the Wikipedia's article: "The Game of Life, also known simply as Life,
         is a cellular automaton devised by the British mathematician John Horton Conway in 1970."

         Given a board with m by n cells, each cell has an initial state live (1) or dead (0).
         Each cell interacts with its eight neighbors (horizontal, vertical, diagonal) using the following four rules
         (taken from the above Wikipedia article):

         Any live cell with fewer than two live neighbors dies, as if caused by under-population.
         Any live cell with two or three live neighbors lives on to the next generation.
         Any live cell with more than three live neighbors dies, as if by over-population..
         Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
         Write a function to compute the next state (after one update) of the board given its current state.

         Follow up:
         1.Could you solve it in-place? Remember that the board needs to be updated at the same time:
         You cannot update some cells first and then use their updated values to update other cells.

         2.In this question, we represent the board using a 2D array. In principle, the board is infinite,
         which would cause problems when the active area encroaches the border of the array. How would you address these problems

        Medium
     */

    /**
     Time : O(mn), Space : O(1)

     We can solve it by using extra space to remember intermediate value. (We can't just update cell with new state value since it will impact the decision for other cells)

     To solve it in space O(1), the clever way is to use bit operation. The idea is that we can save both the old and new state value
     in the same integer number of each cell. We know that there are only 2 valid values in board at the beginning - 0 and 1:

     live : 01
     dead : 00

     So:

     if next round state is "live" (condition #2 and #4) : curState + 2 (10 in binary), first bit is current state, second bit is previous state.
     Then right shit one bit will get new state.

     live -> live : 01 -> 11
     dead -> live : 00 -> 10

     if next round state is "dead" : No need to do anything

     live -> dead : 01 -> 01
     dead -> dead : 00 -> 00

     First round of pass will calculate number of living neighbours by reading the the right most bit of the value.
     Plus 2 if the new state is "live", do nothing if new state is "dead".

     Then with another round of pass in board, we shift each number 1 bit to the right to get the correct new state value.
     */


    public void gameOfLife(int[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) return;

        int m = board.length;
        int n = board[0].length;
        //First pass, get count and neighbours and set value
        for (int i = 0; i < m ; i++) {
            for (int j = 0; j < n; j++) {
                checkAndUpdateCell(board, i, j);
            }
        }

        //2nd pass, do bit shift to get the final correct value for each cell
        for (int i = 0; i < m ; i++) {
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

