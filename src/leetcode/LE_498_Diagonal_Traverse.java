package leetcode;

import java.util.*;

public class LE_498_Diagonal_Traverse {
    /**
     * Given a matrix of M x N elements (M rows, N columns), return all elements of
     * the matrix in diagonal order as shown in the below image.
     *
     * Example:
     *
     * Input:
     * [
     *  [ 1, 2, 3 ],
     *  [ 4, 5, 6 ],
     *  [ 7, 8, 9 ]
     * ]
     *
     * Output:  [1,2,4,7,5,3,6,8,9]
     *
     * Medium
     *
     * https://leetcode.com/problems/diagonal-traverse/
     */

    /**
     * Key Insights:
     * 1.All values in the same diagonal share the same sum value of x index + y index
     * 2.Direction of going up right or going down left depends whether the index sum is even or odd
     ** /

    /**
     * The same solution as in:
     * LE_1424_Diagonal_Traverse_II
     *
     * Use hashmap, the elements on the same diagonal is saved in a list and its key is row index + col index.
     * As for the order of the element in the list:
     *   if key is even, we go from bottom to upper right, hence insert at the start of the list
     *   if key is odd,  we go from top to bottom left, hence insert at the end of the list.
     *
     * Then iterate the HashMap, put each element into result array.
     *
     * This solution uses more resources (HashMap) and more expensive in computing (10 ms). But it is intuitive and easy
     * to remember.
     */
    class Solution {
        public int[] findDiagonalOrder(int[][] mat) {
            if (null == mat || mat.length == 0) return new int[]{};

            Map<Integer, List<Integer>> map = new HashMap<>();

            int m = mat.length;
            int n = mat[0].length;
            int[] res = new int[m * n];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (!map.containsKey(i + j)) map.put(i + j, new ArrayList<>());

                    if ((i + j ) % 2 == 0) {
                        map.get(i + j).add(0, mat[i][j]);
                    } else {
                        map.get(i + j).add(mat[i][j]);
                    }
                }
            }

            int count = 0;
            for (int i = 0; i <= m - 1 + n - 1; i++) {
                for (int num : map.get(i)) {
                    res[count] = num;
                    count++;
                }
            }

            return res;
        }
    }

    /**
     * This solution is more efficient in both time and space but a little to tricky to understand.
     *
     * 2 ms
     *
     * Key:
     * For each even or odd diagonal, there are three cases:
     *     a. there is room to go that direction
     *     b. there is no row space to go further but there is col space
     *     c. there is no col space to go further but there is row space
     */
    class Solution1 {
        public int[] findDiagonalOrder(int[][] matrix) {
            if (null == matrix || matrix.length == 0) return new int[]{};
            int m = matrix.length;
            int n = matrix[0].length;

            int r = 0, c = 0;
            int[] res = new int[m * n];

            for (int i = 0; i < m * n; i++) {
                res[i] = matrix[r][c];

                if ((r + c) % 2 == 0) {
                    if (r - 1 >= 0 && c + 1 < n) {//going up right
                        r--;
                        c++;
                    } else if (r - 1 < 0 && c + 1 < n) {//for example, going form [0, 0] to [0, 1]
                        c++;
//                    } else if (r + 1 <= m && c + 1 >= n - 1) {//for example, from [0, 2] to [1, 2] in example above
                    } else if (r + 1 < m && c + 1 >= n) {
                        r++;
                    }
                } else {
                    if (r + 1 < m && c - 1 >= 0) {
                        r++;
                        c--;
                    } else if (r + 1 < m && c - 1 < 0) {
                        r++;
//                    } else if (r + 1 >= m - 1 && c + 1 < n) {
                    } else if (r + 1 >= m  && c + 1 < n) {
                        c++;
                    }
                }
            }

            return res;
        }
    }
}
