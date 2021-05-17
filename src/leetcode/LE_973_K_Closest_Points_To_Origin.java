package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class LE_973_K_Closest_Points_To_Origin {
    /**
     * We have a list of points on the plane.  Find the K closest points to the origin (0, 0).
     *
     * (Here, the dirs between two points on a plane is the Euclidean dirs.)
     *
     * You may return the answer in any order.
     * The answer is guaranteed to be unique (except for the order that it is in.)
     *
     *
     *
     * Example 1:
     *
     * Input: points = [[1,3],[-2,2]], K = 1
     * Output: [[-2,2]]
     * Explanation:
     * The dirs between (1, 3) and the origin is sqrt(10).
     * The dirs between (-2, 2) and the origin is sqrt(8).
     * Since sqrt(8) < sqrt(10), (-2, 2) is closer to the origin.
     * We only want the closest K = 1 points from the origin, so the answer is just [[-2,2]].
     * Example 2:
     *
     * Input: points = [[3,3],[5,-1],[-2,4]], K = 2
     * Output: [[3,3],[-2,4]]
     * (The answer [[-2,4],[3,3]] would also be accepted.)
     *
     *
     * Note:
     *
     * 1 <= K <= points.length <= 10000
     * -10000 < points[i][0] < 10000
     * -10000 < points[i][1] < 10000
     *
     * Easy
     *
     * A simpler version of LI_612_K_Closest_Points, here it does not have requirement
     * for output order, and the origin is (0,0).
     */

    /**
     * Solution 1 : heap
     *
     * Time  : O(nlog(k))
     * Space : O(k)
     *
     * The advantage of this solution is it can deal with real-time(online) stream data.
     * It does not have to know the size of the data previously.
     *
     * The disadvantage of this solution is it is not the most efficient solution.
     *
     * 80 ms
     */
    class Solution1 {
        public int[][] kClosest(int[][] points, int K) {
            /**
             * !!!
             * Since we want "closest", so we should keep the smallest K dirs values
             * in heap, therefore, it should be a MAX HEAP.
             *
             * Comparator expects return type is int, here,
             * since dist() return type is long, can not simply use "(a, b) -> dist(b) - dist(a)".
             *
             * Instead, use "Long.compare(dist(b), dist(a)".
             */
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Long.compare(dist(b), dist(a)));

            /**
             * !!!
             * First offer(), then check size boundary and poll()
             */
            for (int[] point : points) {
                pq.offer(point);
                if (pq.size() > K) {
                    pq.poll();
                }
            }

            int[][] res = new int[K][2];

            for (int i = 0; i < K; i++) {
                res[i] = pq.poll();
            }

            return res;
        }

        private long dist(int[] A) {
            return A[0] * A[0] + A[1] * A[1];
        }
    }

    /**
     * Use in[] x to simulate K closest points to a given point
     */
    class Solution1_Practice {
        public int[][] kClosest(int[][] points, int K) {
            if (null == points) return new int[][]{};

            int[] x = new int[]{0, 0};

            /**
             * !!!
             * "new PriorityQueue<>((a, b) -> Long.compare(dist(b, x), dist(a, x)));"
             *
             * Don't forget "<>"
             */
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Long.compare(dist(b, x), dist(a, x)));

            for (int[] point : points) {
                pq.offer(point);

                if (pq.size() > K) {
                    pq.poll();
                }
            }

            List<int[]> res = new ArrayList<>();
            while (!pq.isEmpty()) {
                res.add(pq.poll());
            }

            return res.toArray(new int[res.size()][2]);
        }

        private long dist(int[] a, int[] x) {
            return (a[0] - x[0]) * (a[0] - x[0]) + (a[1] - x[1]) * (a[1] - x[1]);
        }
    }

    /**
     * Brutal Force Solution
     * Time and Space : O(nlogn)
     *
     * 72 ms
     */
    class Solution2 {
        public int[][] kClosest(int[][] points, int K) {
            Arrays.sort(points, (p1, p2) -> p1[0] * p1[0] + p1[1] * p1[1] - p2[0] * p2[0] - p2[1] * p2[1]);
            return Arrays.copyOfRange(points, 0, K);
        }
    }

    /**
     * Quick Select
     *
     * The last solution is based on quick sort, we can also call it quick select.
     * In the quick sort, we will always choose a pivot to compare with other elements.
     * After one iteration, we will get an array that all elements smaller than the
     * pivot are on the left side of the pivot and all elements greater than the pivot
     * are on the right side of the pivot (assuming we sort the array in ascending order).
     * So, inspired from this, each iteration, we choose a pivot and then find the
     * position p the pivot should be. Then we compare p with the K, if the p is smaller
     * than the K, meaning the all element on the left of the pivot are all proper candidates
     * but it is not adequate, we have to do the same thing on right side, and vice versa.
     * If the p is exactly equal to the K, meaning that we've found the K-th position.
     * Therefore, we just return the first K elements, since they are not greater than the pivot.
     *
     * Theoretically, the average time complexity is O(N) , but just like quick sort,
     * in the worst case, this solution would be degenerated to O(N^2), and practically,
     * the real time it takes on leetcode is 15ms.
     *
     * The advantage of this solution is it is very efficient.
     *
     * The disadvantage of this solution are it is neither an online solution nor a stable one.
     * And the K elements closest are not sorted in ascending order.
     */
    class Solution3 {
        public int[][] kClosest(int[][] points, int K) {
            int len =  points.length, l = 0, r = len - 1;
            while (l <= r) {
                int mid = helper(points, l, r);
                if (mid == K) break;
                if (mid < K) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
            return Arrays.copyOfRange(points, 0, K);
        }

        private int helper(int[][] A, int l, int r) {
            int[] pivot = A[l];
            while (l < r) {
                while (l < r && compare(A[r], pivot) >= 0) r--;
                A[l] = A[r];
                while (l < r && compare(A[l], pivot) <= 0) l++;
                A[r] = A[l];
            }
            A[l] = pivot;
            return l;
        }

        private int compare(int[] p1, int[] p2) {
            return p1[0] * p1[0] + p1[1] * p1[1] - p2[0] * p2[0] - p2[1] * p2[1];
        }
    }

    /**
     *             int left = start;
     *             int right = end;
     *             int pivot = nums[(start + end) / 2];
     *
     *             while (left <= right) {
     *                 while (left <= right && nums[left] < pivot) {
     *                     left++;
     *                 }
     *
     *                 while (left <= right && nums[right] > pivot) {
     *                     right--;
     *                 }
     *
     *                 if (left <= right) {
     *                     int temp = nums[left];
     *                     nums[left] = nums[right];
     *                     nums[right] = temp;
     *
     *                     left++;
     *                     right--;
     *                 }
     *             }
     */
}
