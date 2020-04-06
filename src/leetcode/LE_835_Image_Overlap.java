package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LE_835_Image_Overlap {
    /**
     * Two images A and B are given, represented as binary, square matrices
     * of the same size.  (A binary matrix has only 0s and 1s as values.)
     *
     * We translate one image however we choose (sliding it left, right, up,
     * or down any number of units), and place it on top of the other image.
     * After, the overlap of this translation is the number of positions that
     * have a 1 in both images.
     *
     * (Note also that a translation does not include any kind of rotation.)
     *
     * What is the largest possible overlap?
     *
     * Example 1:
     *
     * Input: A = [[1,1,0],
     *             [0,1,0],
     *             [0,1,0]]
     *        B = [[0,0,0],
     *             [0,1,1],
     *             [0,0,1]]
     * Output: 3
     * Explanation: We slide A to right by 1 unit and down by 1 unit.
     * Notes:
     *
     * 1 <= A.length = A[0].length = B.length = B[0].length <= 30
     * 0 <= A[i][j], B[i][j] <= 1
     *
     * Medium
     */

    /**
     * https://leetcode.com/problems/image-overlap/discuss/138976/A-generic-and-easy-to-understand-method
     *
     * 1.First put coordinates of value 1 in A and B in l1 and l2
     * 2.Iterate elements in l1 and l2, calculate vector for each coordinate pair,
     *   use hashmap to calculate frequency of each vector.
     * 3.Get max from dist values
     *
     * Time  : O(N ^ 4)
     * Space : O(N ^ 2)
     *
     * For the time complexity,
     * saving the pixel coordinates takes: O(N ^ 2)
     * The list can have a maximum of N ^ 2 entries, so filling in the dist takes O(N ^ 2 * N ^ 2) i.e O(N ^ 4)
     */
    class Solution {
        public int largestOverlap(int[][] A, int[][] B) {
            if (null == A || null == B) return 0;

            List<int[]> l1 = new ArrayList<>();
            List<int[]> l2 = new ArrayList<>();

            for (int i = 0; i < A.length; i++) {
                for (int j = 0; j < A[0].length; j++) {
                    if (A[i][j] == 1) {
                        l1.add(new int[]{i, j});
                    }
                    if (B[i][j] == 1) {
                        l2.add(new int[]{i, j});
                    }
                }
            }

            Map<String, Integer> map = new HashMap<>();

            for (int[] a : l1) {
                for (int[] b : l2) {
                    String v = (a[0] - b[0]) + " " + (a[1] - b[1]);
                    map.put(v, map.getOrDefault(v, 0) + 1);
                }
            }

            int res = 0;
            for (int val : map.values()) {
                res = Math.max(res, val);
            }

            return res;
        }
    }
}
