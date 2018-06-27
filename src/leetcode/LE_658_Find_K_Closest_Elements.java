package leetcode;

import java.util.Collections;
import java.util.List;

/**
 * Created by yuank on 6/27/18.
 */
public class LE_658_Find_K_Closest_Elements {
    /**
         Given a sorted array, two integers k and x, find the k closest elements to x in the array.
         The result should also be sorted in ascending order. If there is a tie, the smaller elements are always preferred.

         Example 1:
         Input: [1,2,3,4,5], k=4, x=3
         Output: [1,2,3,4]
         Example 2:
         Input: [1,2,3,4,5], k=4, x=-1
         Output: [1,2,3,4]
         Note:
         The value k is positive and will always be smaller than the length of the sorted array.
         Length of the given array is positive and will not exceed 104
         Absolute value of elements in the array and x will not exceed 104

         Medium
     */
    /**
         https://www.jiuzhang.com/solution/find-k-closest-elements/#tag-highlight

         找到最后一个 < target 的位置，然后从这个位置开始用两根指针向两边走来获得最后最接近的 k 个整数。
         时间复杂度 O(logn + k), Space : O(k)
     **/
    public int[] kClosestNumbers(int[] A, int target, int k) {
        int left = findLowerClosest(A, target);
        int right = left + 1;

        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            if(isLeftCloser(A, target, left, right)) {
                res[i] = A[left];
                left--; //!!!
            } else {
                res[i] = A[right];
                right++;
            }
        }

        return res;
    }

    public boolean isLeftCloser(int[] A, int target, int left, int right) {
        if (left < 0) {//!!!
            return false;
        }

        if (right >= A.length) {
            return true;//!!!
        }

        //!!!
        if (target - A[left] != A[right] - target) {
            return target - A[left] < A[right] - target;
        }

        return true;
    }

    public int findLowerClosest(int[] A, int target) {
        int start = 0;
        int end = A.length - 1;

        while (start + 1 < end) {
            int mid = (end - start) / 2 + start;

            if (A[mid] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }

        if (A[end] < target) {
            return end;
        }

        if (A[start] < target) {
            return start;
        }

        return -1;
    }

    /**
     * Solution 2
     * Time  : O(nlogn)
     * Space : O(k)
     */

    public List<Integer> findClosestElements(List<Integer> arr, int k, int x) {
        Collections.sort(arr, (a,b) -> a == b ? a - b : Math.abs(a-x) - Math.abs(b-x));
        arr = arr.subList(0, k); //!!!subList()
        Collections.sort(arr);
        return arr;
    }
}
