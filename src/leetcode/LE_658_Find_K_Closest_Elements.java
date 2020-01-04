package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
     * Best solution
     *
     * 时间复杂度 O(logn + k)
     *
     * 直接在数组中二分查找 target, 如果不存在则返回大于 target 的最小的或者小于 target 的最大的元素均可.
     *
     * 然后使用两根指针从该位置开始向两端遍历, 每次把差值比较小的元素放入答案中然后将该指针向边界方向移动一下即可.
     */
    class Solution_Preferred {
        public int[] kClosestNumbers(int[] A, int target, int k) {
            int[] result = new int[k];

            if (A == null || A.length == 0) {
                return A;
            }
            if (k > A.length) {
                return A;
            }

            int index = firstIndex(A, target);

            int start = index - 1;
            int end = index;
            for (int i = 0; i < k; i++) {
                if (start < 0) {
                    result[i] = A[end++];
                } else if (end >= A.length) {
                    result[i] = A[start--];
                } else {
                    if (target - A[start] <= A[end] - target) {
                        result[i] = A[start--];
                    } else {
                        result[i] = A[end++];
                    }
                }
            }
            return result;
        }

        private int firstIndex(int[] A, int target) {
            int start = 0, end = A.length - 1;
            while (start + 1 < end) {
                int mid = start + (end - start) / 2;
                if (A[mid] < target) {
                    start = mid;
                } else if (A[mid] > target) {
                    end = mid;
                } else {
                    end = mid;
                }
            }

            if (A[start] >= target) {
                return start;
            }
            if (A[end] >= target) {
                return end;
            }
            return A.length;
        }
    }


    /**
     * 三种解法都要会
     *
     * O(logn)   Solution1
     * O(nlogn)  Solution2
     * O(n)      Solution3
     */

    /**
     * Binary Search
     * Time : O(logn), if given input is a list, we can do "subList()" to get final answer.
     * subList() gets a view so it takes O(1).
     *
     * Assume we are taking A[i] ~ A[i + k -1] for final answer.
     * We can binary research i, in other words, Binary Search for the start index, based on the location of x.
     *
     * We compare the distance between x - A[mid] and A[mid + k] - x
     *
     * If x - A[mid] > A[mid + k] - x,
     * it means A[mid + 1] ~ A[mid + k] is better than A[mid] ~ A[mid + k - 1],
     * and we have mid smaller than the right i.
     * So assign left = mid + 1.
     *
     * Reversely, it's similar.
     *
     * 二分求得左端点。将整个array分为这几个部分：1....mid....mid+k....arr.length - k.
     * 当x位于mid到mid+k中点往左的位置时，左端点肯定在mid左侧，反之，则在右侧。
     *
     * https://www.youtube.com/watch?v=3ifFNvdfjyg
     */
    class Solution1 {
        public List<Integer> findClosestElements(List<Integer> list, int k, int x) {
//            List<Integer> list = Arrays.stream(arr).boxed().collect(Collectors.toList());

            int lo = 0, hi = list.size() - k;
            while (lo < hi) {
                int mid = (lo + hi) / 2;
                if (x > list.get(mid)) {
                    if (x - list.get(mid) > list.get(mid + k) - x) {
                        lo = mid + 1;
                    } else {
                        hi = mid;
                    }
                } else {
                    hi = mid;
                }
            }
            return list.subList(lo, lo + k);
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

    class Solution2 {
        public List<Integer> findClosestElements_list(List<Integer> arr, int k, int x) {
            Collections.sort(arr, (a, b) -> a == b ? a - b : Math.abs(a - x) - Math.abs(b - x));
            arr = arr.subList(0, k); //!!!subList()
            Collections.sort(arr);
            return arr;
        }

        public List<Integer> findClosestElements_array(int[] arr, int k, int x) {
            List<Integer> list = Arrays.stream(arr).boxed().collect(Collectors.toList());
            Collections.sort(list, (a, b) -> a == b ? a - b : Math.abs(a - x) - Math.abs(b - x));
            list = list.subList(0, k); //!!!subList()
            Collections.sort(list);
            return list;
        }
    }

    /**
     * O(n) Solution
     *
     * If input is List, it can be also O(logn)
     */
    class Solution3 {
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

        public List<Integer> findClosestElements_list(List<Integer> list, int k, int x) {
            int lo = 0;
            int hi = list.size() - 1;
            while (hi - lo >= k) {
                if (Math.abs(list.get(lo) - x) > Math.abs(list.get(hi) - x)) {
                    lo++;
                } else {
                    hi--;
                }
            }

            return list.subList(lo, hi);
        }
    }

    /**
         https://www.jiuzhang.com/solution/find-k-closest-elements/#tag-highlight

         Binary Search + Two Pointers

         找到最后一个 < target 的位置，然后从这个位置开始用两根指针向两边走来获得最后最接近的 k 个整数。
         时间复杂度 O(logn + k), Space : O(k)
     **/
    class Solution4 {
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
     * "the result should also be sorted in ascending order".
     *
     * This solution solves the issue by moving index high and low in a given range,
     * then return the sublist between high and low.
     *
     * Time complexity : O(log(n)+k)
     * Space complexity : O(k)
     */
    class Solution5 {
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

                return arr.subList(low, high + 1);//!!!"high + 1", subList requires end index be none-inclusive
            }
        }
    }

    /**
     * Binary Search + Two Pointers
     * Use existing binarySearch() in Collections.
     * Convert arr[] to list, do binary search, move left and right
     *
     * It's actually O(n), since we first convert array to list
     * if given input is a list, it is O(logn + k)
     *
     * arr[idx] is the first number which is euqal to or geater than x
     * (if all numbers are less than x, index is arr.size()),
     * and the result is arr[i+1, i+2, ... j].
     */
    class Solution {
        public List<Integer> findClosestElements(int[] arr, int k, int x) {
            List<Integer> list = Arrays.stream(arr).boxed().collect(Collectors.toList());

            int idx = Collections.binarySearch(list, x);

            if (idx < 0) {
                idx = -(idx + 1);
            }

            int n = arr.length;
            /**
             * !!!
             * "i = idx - 1"
             */
            int i = idx - 1, j = idx;

            while (k > 0) {
                if (i < 0 || (j < n && Math.abs(list.get(j) - x) < Math.abs(list.get(i) - x))) {
                    j++;
                } else {
                    i--;
                }

                k--;
            }

            return list.subList(i + 1, j);
        }
    }

}

