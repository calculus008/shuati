package leetcode;

import java.util.*;

public class LE_944_Delete_Columns_To_Make_Sorted {
    /**
     * You are given an array of n strings strs, all of the same length.
     * The strings can be arranged such that there is one on each line, making a grid.
     * For example, strs = ["abc", "bce", "cae"] can be arranged as follows:
     * abc
     * bce
     * cae
     * You want to delete the columns that are not sorted lexicographically. In the above example (0-indexed),
     * columns 0 ('a', 'b', 'c') and 2 ('c', 'e', 'e') are sorted, while column 1 ('b', 'c', 'a') is not, so you would delete column 1.
     *
     * Return the number of columns that you will delete.
     *
     * Example 1:
     * Input: strs = ["cba","daf","ghi"]
     * Output: 1
     * Explanation: The grid looks as follows:
     *   cba
     *   daf
     *   ghi
     * Columns 0 and 2 are sorted, but column 1 is not, so you only need to delete 1 column.
     *
     * Example 2:
     * Input: strs = ["a","b"]
     * Output: 0
     * Explanation: The grid looks as follows:
     *   a
     *   b
     * Column 0 is the only column and is sorted, so you will not delete any columns.
     *
     * Example 3:
     * Input: strs = ["zyx","wvu","tsr"]
     * Output: 3
     * Explanation: The grid looks as follows:
     *   zyx
     *   wvu
     *   tsr
     * All 3 columns are not sorted, so you will delete all 3.
     *
     *
     * Constraints:
     * n == strs.length
     * 1 <= n <= 100
     * 1 <= strs[i].length <= 1000
     * strs[i] consists of lowercase English letters.
     *
     * Easy
     *
     * https://leetcode.com/problems/delete-columns-to-make-sorted/description/
     */

    /**
     * Full traversal and sort, not efficeint
     *
     */
    class Solution1 {
        public int minDeletionSize(String[] strs) {
            int m = strs.length;
            int n = strs[0].length();
            char[][] sbs = new char[n][m];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    sbs[j][i] = strs[i].charAt(j);
                }
            }

            int res = 0;
            for (int i = 0; i < n; i++) {
                String s1 = new String(sbs[i]);
                Arrays.sort(sbs[i]);
                String s2 = new String(sbs[i]);
                if (!s1.equals(s2)) {
                    res++;
                }
            }

            return res;
        }
    }

    /**
     * Min traversal and no sort, detect unsorted column while traversal
     */
    class Solution2 {
        public int minDeletionSize(String[] strs) {
            int m = strs.length;
            int n = strs[0].length();
            int res = 0;

            for (int i = 0; i < n; i++) {//iterate each column
                for (int j = 1; j < m; j++) {//then check char at current and previous index of the column
                    char current = strs[j].charAt(i);
                    char previous = strs[j - 1].charAt(i);

                    if (current < previous) {
                        res++;
                        break;
                    }

                }
            }

            return res;
        }
    }
}
