package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 3/1/18.
 */

public class LE_51_N_Queens {
    /**
        Given an integer n, return all distinct solutions to the n-queens puzzle.

        Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.'
        both indicate a queen and an empty space respectively.

        For example,
        There exist two distinct solutions to the 4-queens puzzle:

        [
         [".Q..",  // Solution 1
          "...Q",
          "Q...",
          "..Q."],

         ["..Q.",  // Solution 2
          "Q...",
          "...Q",
          ".Q.."]
        ]

        Hard

        https://leetcode.com/problems/n-queens
     **/

    //Backtracking
    //Time : O(n ^ n),  Space : O(n)
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();
        if(n <= 0) return res;
        helper(res, new int[n], 0);
        return res;
    }

    private void helper(List<List<String>> res, int[] queens, int pos) {
        //"pos" is the index of row
        if (pos == queens.length) {
            buildRes(res, queens);
            return;
        }

        //try every column
        for (int i = 0; i < queens.length; i++) {
            //!!!Queen is put at row "pos" and column "i"
            queens[pos] = i;
            if (isValid(queens, pos)) {
                //OK, column i works for row pos, move on to the next row (pos + 1)
                helper(res, queens, pos + 1);
            }
            //!!!queens[pos] will be updated each loop, so no need to recover its value as normal backtracking logic does
        }
    }

    private boolean isValid(int[] queens, int row) {
        //!!! "i < row"
        for (int i = 0; i < row; i++) {
            if (queens[i] == queens[row]) {//ith row has the same column value as current row
                return false;
            } else if(Math.abs(queens[i] - queens[row]) == Math.abs(i - row)) {//the simpliest way of checking diagno duplicates
                // queens[i] - queens[row] : col1 - col2
                // i - row : row1 - row2
                //如果两点的行之差(绝对值)等于列之差(绝对值) 那两点就在同一对角线上
                return false;
            }
        }
        return true;
    }

    private void buildRes(List<List<String>> res, int[] queues) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < queues.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < queues.length; j++) {
                if (queues[i] == j) {
                    sb.append("Q");
                } else {
                    sb.append(".");
                }
            }
            list.add(sb.toString());
        }

        res.add(list);
    }


}

/*
HuahuaJiang solution
Diaganal number = n*2-1  and n*2-1
idx = x+y  and x-y+(n-1)
 */
class Solution {
    private boolean[] cols;
    private boolean[] diag1;
    private boolean[] diag2;
    private char[][] board;

    public List<List<String>> solveNQueens(int n) {
        board = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }

        List<List<String>> res = new ArrayList<List<String>>();
        cols = new boolean[n];
        diag1 = new boolean[2 * n - 1];
        diag2 = new boolean[2 * n - 1];
        helper(res, n, 0);
        return res;
    }

    private void helper(List<List<String>> res, int n, int row) {
        if (row == n) {
            buildRes(res);
            return;
        }

        for (int y = 0; y < n; y++) {
            if (!available(row, y, n)) continue;
            updateBoard(row, y, n, true);
            helper(res, n, row + 1);
            updateBoard(row, y, n, false);
        }
    }

    private boolean available(int x, int y, int n) {
        return !cols[y] && !diag1[x + y] && !diag2[x - y + n - 1];
    }

    private void updateBoard(int x, int y, int n, boolean isPut) {
        cols[y] = isPut;
        diag1[x + y] = isPut;
        diag2[x - y + n - 1] = isPut;
        board[x][y] = isPut ? 'Q' : '.';
    }

    private void buildRes(List<List<String>> res) {
        List<String> sol = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            String row = String.valueOf(board[i]);
            sol.add(row);
        }
        res.add(sol);
    }
}