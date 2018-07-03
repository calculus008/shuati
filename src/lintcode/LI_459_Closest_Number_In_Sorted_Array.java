package lintcode;

/**
 * Created by yuank on 7/2/18.
 */
public class LI_459_Closest_Number_In_Sorted_Array {
    /**
         Given a target number and an integer array A sorted in ascending order,
         find the index i in A such that A[i] is closest to the given target.

         Return -1 if there is no element in the array.

         Example
         Given [1, 2, 3] and target = 2, return 1.

         Given [1, 4, 6] and target = 3, return 1.

         Given [1, 4, 6] and target = 5, return 1 or 2.

         Given [1, 3, 3, 4] and target = 2, return 0 or 1 or 2.

         Challenge
         O(logn) time complexity.
     */

    //Time : O(logn), same idea as LE_658_Find_K_Closest_Elements
    public int closestNumber(int[] A, int target) {
        if (A == null || A.length == 0) {
            return -1;
        }

        int start = 0;
        int end = A.length - 1;

        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (A[mid] == target) {
                return mid;
            } else if (A[mid] > target) {
                end = mid;
            } else {
                start = mid;
            }
        }

        if (Math.abs(A[start] - target) < Math.abs(A[end] - target)) {
            return start;
        }

        return end;
    }
}
