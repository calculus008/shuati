package leetcode;

/**
 * Created by yuank on 3/2/18.
 */
public class LE_52_N_Queens_II {
    /*
        Now, instead outputting board configurations, return the total number of distinct solutions.
     */

    //Solution 1 : same as LE_51
    int res = 0;

    public int totalNQueens(int n) {
        if (n <= 0) return 0;
        helper(new int[n], 0);
        return res;
    }

    private void helper(int[] queens, int pos) {
        if (pos == queens.length) {
            res++;

            /**
             * !!!
             */
            return;
        }

        for (int i = 0; i < queens.length; i++) {
            queens[pos] = i;
            if (isValid(queens, pos)) {
                helper(queens, pos + 1);
            }
        }
    }

    private boolean isValid(int[] queues, int row) {
        for (int i = 0; i < row; i++) {
            if (queues[i] == queues[row]) {
                return false;
            } else if (Math.abs(queues[i] - queues[row]) == Math.abs(i - row)) {
                return false;
            }
        }
        return true;
    }

    //Solution 2 : use 3 boolean arrays for status checking
    int count = 0;

    public int totalNQueens2(int n) {
        boolean[] cols = new boolean[n];     // columns   |
        boolean[] d1 = new boolean[2 * n];   // diagonals \
        boolean[] d2 = new boolean[2 * n];   // diagonals /
        backtracking(0, cols, d1, d2, n);
        return count;
    }

    public void backtracking(int row, boolean[] cols, boolean[] d1, boolean[] d2, int n) {
        if (row == n) count++;

        for (int col = 0; col < n; col++) {
            int id1 = col - row + n;
            int id2 = col + row;
            if (cols[col] || d1[id1] || d2[id2]) continue;

            cols[col] = true;
            d1[id1] = true;
            d2[id2] = true;
            backtracking(row + 1, cols, d1, d2, n);
            cols[col] = false;
            d1[id1] = false;
            d2[id2] = false;
        }
    }
}
