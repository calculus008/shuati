package leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yuank on 6/27/18.
 */
public class LE_658_Find_K_Closest_Elements {
    /**
         Given a SORTED(!!!) array, two integers k and x, find the k closest elements to x in the array.
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

         Medium
     */
    /**
         https://www.jiuzhang.com/solution/find-k-closest-elements/#tag-highlight

         Binary Search + Two Pointers

         找到最后一个 < target 的位置，然后从这个位置开始用两根指针向两边走来获得最后最接近的 k 个整数。
         时间复杂度 O(logn + k), Space : O(k)
     **/
    class Solution1 {
        public int[] kClosestNumbers(int[] A, int target, int k) {
            int left = findLowerClosest(A, target);
            int right = left + 1;

            int[] res = new int[k];
            for (int i = 0; i < k; i++) {
                if (isLeftCloser(A, target, left, right)) {
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
    }

    /**
     * Problem of Solution1 is that it needs to do a sort to meet requirement
     * "he result should also be sorted in ascending order".
     *
     * This solution solves the issue by moving index high and low in a given range,
     * then return the sublist between high and low.
     */
    public class Solution2 {
        public List<Integer> findClosestElements(List<Integer> arr, int k, int x) {
            int n = arr.size();

            if (x <= arr.get(0)) {
                /**
                 * If the target x is less or equal than the first element in the sorted array,
                 * the first k elements are the result.
                 */
                return arr.subList(0, k);
            } else if (arr.get(n - 1) <= x) {
                /**
                 * Similarly, if the target x is more or equal than the last element in the
                 * sorted array, the last k elements are the result.
                 */
                return arr.subList(n - k, n);
            } else {
                /**
                 * Otherwise, we can use binary search to find the index of the element,
                 * which is equal (when this list has x) or a little bit larger than x
                 * (when this list does not have it).
                 *
                 * Then set low to its left k-1 position, and high to the right k-1 position of
                 * this index as a start. The desired k numbers must in this rang [index-k-1, index+k-1].
                 *
                 * So we can shrink this range to get the result using the following rules :
                 * 1.If low reaches the lowest index 0 or the low element is closer to x than the high element,
                 *   decrease the high index.
                 * 2.If high reaches to the highest index arr.size()-1 or it is nearer to x than the low element,
                 *   increase the low index.
                 *
                 * The looping ends when there are exactly k elements in [low, high], the subList of which is the result.
                 */
                int index = Collections.binarySearch(arr, x);
                /**
                 * Collections.binarySearch() return value :
                 * the index of the search key, if it is contained in the list; otherwise, (-(insertion point) - 1).
                 * The insertion point is defined as the point at which the key would be inserted into the list:
                 * the index of the first element greater than the key, or list.size() if all elements in the list
                 * are less than the specified key.
                 *
                 * Note that this guarantees that the return value will be >= 0 if and only if the key is found.
                 *
                 * x = -index - 1
                 * index = -(x + 1) = -x - 1
                 */
                if (index < 0) {
                    index = -index - 1;
                }

                int low = Math.max(0, index - k - 1);
                int high = Math.min(arr.size() - 1, index + k - 1);

                while (high - low + 1 > k) {//length of the range
                    if (low < 0 || (x - arr.get(low)) <= (arr.get(high) - x)) {
                        high--;
                    } else if (high > arr.size() - 1 || (x - arr.get(low)) > (arr.get(high) - x)) {
                        low++;
                    }
                }

                return arr.subList(low, high + 1);//!!!"hight + 1", subList requires end index be none-inclusive
            }
        }
    }


    /**
     * Solution 2
     * Time  : O(nlogn)
     * Space : O(k)
     *
     * if input is int[], use Java 8 stream to convert it to list:
     *
     * List<Integer> nums = Arrays.stream(arr).boxed().collect(Collectors.toList());
     */

    public List<Integer> findClosestElements(List<Integer> arr, int k, int x) {
        Collections.sort(arr, (a, b) -> a == b ? a - b : Math.abs(a - x) - Math.abs(b - x));
        arr = arr.subList(0, k); //!!!subList()
        Collections.sort(arr);
        return arr;
    }

    /**
     * O(n) Solution
     */
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int lo = 0;
        int hi = arr.length - 1;
        while (hi - lo >= k) {
            if (Math.abs(arr[lo] - x) > Math.abs(arr[hi] - x)) {
                lo++;
            } else {
                hi--;
            }
        }
        List<Integer> result = new ArrayList<>(k);
        for (int i = lo; i <= hi; i++) {
            result.add(arr[i]);
        }
        return result;
    }
}
