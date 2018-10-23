package lintcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuank on 10/19/18.
 */
public class LI_390_Find_Peak_Element_II {
    /**
         There is an integer matrix which has the following features:

         The numbers in adjacent positions are different.
         The matrix has n rows and m columns.
         For all i < m, A[0][i] < A[1][i] && A[n - 2][i] > A[n - 1][i].
         For all j < n, A[j][0] < A[j][1] && A[j][m - 2] > A[j][m - 1].
         We define a position P is a peek if:

         A[j][i] > A[j+1][i] && A[j][i] > A[j-1][i] && A[j][i] > A[j][i+1] && A[j][i] > A[j][i-1]
         Find a peak element in this matrix. Return the index of the peak.

         Example
         Given a matrix:

         [
             [1 ,2 ,3 ,6 ,5],
             [16,41,23,22,6],
             [15,17,24,21,7],
             [14,18,19,20,10],
             [13,14,11,10,9]
         ]

         return index of 41 (which is [1,1]) or index of 24 (which is [2,2])

         Challenge
         Solve it in O(n+m) time.

         If you come up with an algorithm that you thought it is O(n log m) or O(m log n),
         can you prove it is actually O(n+m) or propose a similar but O(n+m) algorithm?

         Notice
         The matrix may contains multiple peeks, find any of them.

         Hard
     */
    /**
     * Solution 1
     * Binary Search on row
     * Time : O(nlogm)
     */
    class Solution1 {
        public List<Integer> findPeakII(int[][] A) {
            List<Integer> res = new ArrayList<>();
            if (A == null || A.length == 0 || A[0].length == 0) return res;

            int m = A.length;
            int n = A[0].length;

            int start = 1;
            int end = m - 2;

            while (start <= end) {
                int mid = (end - start) / 2 + start;
                int col = search(A, mid);
                if (A[mid][col] < A[mid - 1][col]) {//"mid - 1"
                    end = mid - 1;
                } else if (A[mid][col] < A[mid + 1][col]) {//"mid + 1"
                    start = mid + 1;
                } else {
                    res.add(mid);
                    res.add(col);
                    break;//!!!
                }
            }

            return res;
        }

        private int search(int[][] A, int mid) {
            int col = 0;
            for (int i = 1; i < A[0].length; i++) {
                if (A[mid][i] > A[mid][col]) {
                    col = i;
                }
            }

            return col;
        }
    }

    /**
     * Solution 2
     * Binary Search on row and col
     * Time : O(n + m)
     *
     * Reduce n * m matrix into n/2 * m/2 matrix in O(n)
     * T(n)   = T(n/2) + cn
     * T(n/2) = T(n/4) + cn + cn/2
     * T(n/4) = T(n/8) + cn + cn/2 + cn/4
     * ....
     * T(n) = T(1) + c(1 + 2 + 4 +....+n/4 + n/2 + n) -> O(n)
     *
     * 二分法。既对行二分，又对列二分。如果只对行二分，时间复杂度是O(mlogn)。如果只对列二分，
     * 时间复杂度是O(nlogm)。如果既对行二分又对列二分，时间复杂度为O(n+m)比如，先对行二分，
     * 接着对列二分，交替进行。假设问题规模为S.
     * T(S) = O(m) + O(n/2) + T(S/4) = O(m) + O(m/2) + O(n/2) + O(n/4) + T(S/16)
     *      = O(m) + O(m/2) +... + O(1) + O(n/2) + O(n/4) +...+ O(1) + T(1) = O(m) + O(n)
     */
    class Solution2 {
        public List<Integer> find(int x1, int x2, int y1, int y2, int[][] A) {

            int mid1 = x1 + (x2 - x1) / 2;
            int mid2 = y1 + (y2 - y1) / 2;

            int x = mid1, y = mid2;
            int max = A[mid1][mid2];
            for (int i = y1; i <= y2; ++i) {
                if (A[mid1][i] > max) {
                    max = A[mid1][i];
                    x = mid1;
                    y = i;
                }
            }

            for (int i = x1; i <= x2; ++i) {
                if (A[i][mid2] > max) {
                    max = A[i][mid2];
                    x = i;
                    y = mid2;
                }
            }

            boolean isPeak = true;
            if (A[x - 1][y] > A[x][y]) {
                isPeak = false;
                x -= 1;
            } else if (A[x + 1][y] > A[x][y]) {
                isPeak = false;
                x += 1;
            } else if (A[x][y - 1] > A[x][y]) {
                isPeak = false;
                y -= 1;
            } else if (A[x][y + 1] > A[x][y]) {
                isPeak = false;
                y += 1;
            }

            if (isPeak) {
                return new ArrayList<Integer>(Arrays.asList(x, y));
            }

            if (x >= x1 && x < mid1 && y >= y1 && y < mid2) {
                return find(x1, mid1 - 1, y1, mid2 - 1, A);
            }

            if (x >= 1 && x < mid1 && y > mid2 && y <= y2) {
                return find(x1, mid1 - 1, mid2 + 1, y2, A);
            }

            if (x > mid1 && x <= x2 && y >= y1 && y < mid2) {
                return find(mid1 + 1, x2, y1, mid2 - 1, A);
            }

            if (x >= mid1 && x <= x2 && y > mid2 && y <= y2) {
                return find(mid1 + 1, x2, mid2 + 1, y2, A);
            }

            return new ArrayList<Integer>(Arrays.asList(-1, -1));

        }

        public List<Integer> findPeakII(int[][] A) {
            // write your code here
            int n = A.length;
            int m = A[0].length;
            return find(1, n - 2, 1, m - 2, A);
        }
    }


}
