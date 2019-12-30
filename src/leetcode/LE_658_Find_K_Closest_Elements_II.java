package leetcode;

import java.util.ArrayList;
import java.util.List;

public class LE_658_Find_K_Closest_Elements_II {
    /**
     * Regular binary search + two pointers solution
     *
     * Key Insights:
     * 1.Binary search for the idx of the first element in input that >= x
     * 2.Start from idx, use two pointers to go left and right, find the range of the indexes.
     * 3.Loop through index range to put elements in list so it is in sorted order.
     *
     * Time : O(logn + k)
     */
    class Solution1 {
        public List<Integer> findClosestElements(int[] arr, int k, int x) {
            List<Integer> res = new ArrayList<>();
            if (arr == null || arr.length < k) return res;

            int idx = findIndex(arr, x);

            System.out.println("indx = " + idx);
            int l = idx - 1;
            int r = idx;
            int count = 0;

            while (count < k) {
                /**
                 * !!!
                 * 1.Must in this order to make sure l and r is valid index.
                 * 2."Math.abs(arr[l] - x) <= Math.abs(arr[r] - x", it is compare left/right element with x, not arr[idx]!!!!
                 */
                if (l < 0) {
                    r++;
                } else if (r >= arr.length) {
                    l--;
                } else if (Math.abs(arr[l] - x) <= Math.abs(arr[r] - x)) {
                    l--;
                } else {
                    r++;
                }

                count++;
            }

            for (int i = l + 1; i <= r - 1; i++) {
                res.add(arr[i]);
            }

            return res;
        }

        /**
         * find the smallest idx so that arr[idx] >= x
         **/
        private int findIndex(int[] arr, int x) {
            int l = 0;
            int r = arr.length;

            while (l < r) {
                int m = l + (r - l) / 2;
                if (arr[m] >= x) {
                    r = m;
                } else {
                    l = m + 1;
                }
            }

            return l;
        }
    }

    /**
     * O(log(n - k))
     *
     * 1) res will be a consecutive subarray of k size
     * 2) say if we need to pick 4 elems, now we r looking at 5 elem n1, n2, n3, n4, n5
     *    we need to compare two ends: diff(x, n1) and diff(x, n5), the number with bigger diff on the end will be eleminated
     */
    class Solution2 {
        public List<Integer> findClosestElements(int[] arr, int k, int x) {
            List<Integer> res = new ArrayList<>();
            if (arr == null || arr.length < k) return res;

            /**
             *  lo and hi: range of all possible start of subarray with size k + 1, so we could compare both ends
             */
            int l = 0;
            int r = arr.length - k;

            while (l < r) {
                int m = l + (r - l) / 2;

                /**
                 * for subarray starting at mid with size k+1, we compare element of two ends to eliminate the loser
                 */
                if (x - arr[m] <= arr[m + k] - x) {
                    r = m; // arr[mid+k] is the one further away, all [mid, hi] will have similar situation, eliminate them
                } else {
                    l = m + 1; // arr[mid] is the one further away from x, eliminate range[l, mid]
                }
            }

            for (int i = l; i < l + k; i++) {
                res.add(arr[i]);
            }

            return res;
        }
    }
}
