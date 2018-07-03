package lintcode;

/**
 * Created by yuank on 7/2/18.
 */
public class LI_462_Total_Occurrence_Of_Target {
    /**
         Given a target number and an integer array sorted in ascending order.
         Find the total number of occurrences of target in the array.

         Example
         Given [1, 3, 3, 4, 5] and target = 3, return 2.

         Given [2, 2, 3, 4, 6] and target = 4, return 1.

         Given [1, 2, 3, 4, 5] and target = 6, return 0.

         Challenge
         Time complexity in O(logn)
     */

    //用二分找到target 第一次出现的位置，在找到最后一次出现的位置，算一下距离差就知道了。
    public int totalOccurrence(int[] A, int target) {
        if (A == null || A.length == 0) {
            return 0;
        }

        if (target < A[0] || A[A.length - 1] < target) {
            return 0;
        }

        int first = findFirstIdx(A, target);

        if (first == -1) return 0;

        int last = findLastIdx(A, target);

        return last - first + 1;
    }

    public int findFirstIdx(int[] A, int target) {
        int start = 0;
        int end = A.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (A[mid] == target) {
                end = mid;
            } else if (A[mid] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }

        if (A[start] == target) {
            return start;
        } else if (A[end] == target) {
            return end;
        } else {
            return -1;
        }
    }

    public int findLastIdx(int[] A, int target) {
        int start = 0;
        int end = A.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (A[mid] == target) {
                start = mid;
            } else if (A[mid] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }

        if (A[end] == target) {
            return end;
        } else if (A[start] == target) {
            return start;
        } else {
            return -1;
        }
    }
}
