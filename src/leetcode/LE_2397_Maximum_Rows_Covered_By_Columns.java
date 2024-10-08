package leetcode;

import java.util.*;

public class LE_2397_Maximum_Rows_Covered_By_Columns {
    /**
     * You are given an m x n binary matrix and an integer numSelect.
     *
     * Your goal is to select exactly numSelect distinct columns from matrix such that you cover as many rows as possible.
     *
     * A row is considered covered if all the 1's in that row are also part of a column that you have selected. If a row does not have any 1s, it is also considered covered.
     *
     * More formally, let us consider selected = {c1, c2, ...., cnumSelect} as the set of columns selected by you. A row i is covered by selected if:
     *
     * For each cell where matrix[i][j] == 1, the column j is in selected.
     * Or, no cell in row i has a value of 1.
     * Return the maximum number of rows that can be covered by a set of numSelect columns.
     *
     *
     *
     * Example 1:
     * Input: matrix = [[0,0,0],[1,0,1],[0,1,1],[0,0,1]], numSelect = 2
     *
     * Output: 3
     *
     * Explanation:
     * One possible way to cover 3 rows is shown in the diagram above.
     * We choose s = {0, 2}.
     * - Row 0 is covered because it has no occurrences of 1.
     * - Row 1 is covered because the columns with value 1, i.e. 0 and 2 are present in s.
     * - Row 2 is not covered because matrix[2][1] == 1 but 1 is not present in s.
     * - Row 3 is covered because matrix[2][2] == 1 and 2 is present in s.
     * Thus, we can cover three rows.
     * Note that s = {1, 2} will also cover 3 rows, but it can be shown that no more than three rows can be covered.
     *
     * Example 2:
     * Input: matrix = [[1],[0]], numSelect = 1
     *
     * Output: 2
     *
     * Explanation:
     *
     * Selecting the only column will result in both rows being covered since the entire matrix is selected.
     *
     * Constraints:
     *
     * m == matrix.length
     * n == matrix[i].length
     * 1 <= m, n <= 12
     * matrix[i][j] is either 0 or 1.
     * 1 <= numSelect <= n
     *
     * Medium
     *
     * https://leetcode.com/problems/maximum-rows-covered-by-columns
     */

    /**
     * Recursion: pick or not pick
     *      Time:  O(N * M * 2^M)
     *      Space: O(M * 2 ^ M)
     */
    public int maximumRows(int[][] mat, int cols) {
        return helper(0, cols, mat);
    }

    Set<Integer> used = new HashSet<>();

    int helper(int idx, int cols, int[][] mat) {
        if (idx == mat[0].length || cols == 0) {// finish placing all cols
            int res = count(mat);
            return res;
        }

        used.add(idx);
        int pick = helper(idx + 1, cols - 1, mat);
        used.remove(idx);
        int notPick = helper(idx + 1, cols, mat);
        return Math.max(pick, notPick);
    }

    int count(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int coveredRowCount = 0;

        for (int i = 0; i < m; i++) {
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 1 && !used.contains(j)) {
                    flag = false;
                }
            }
            if (flag) coveredRowCount++;
        }

        return coveredRowCount;
    }

    /**
     * Approach - if you look at problem in little deep , you can directly see that for each column we have two options
     *
     * pick that column (if picked then, go to next index and increased the count of total picked column)
     * Not pick that column (if not picked, got to next step in this case since we have not picked it total count is not
     * going to increase).
     *
     * Base Condition Explanation
     * Think logically when we are going to hit base i,e {index == matrix[0].length} simple.
     * and if base condition is met that we need to do, we need to calculate total of rows covered with currently
     * selected column.
     *
     * TC: O(N * M * 2^M)
     * SC: O(M * 2 ^ M)
     */
}
