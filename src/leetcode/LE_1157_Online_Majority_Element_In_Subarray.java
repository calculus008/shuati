package leetcode;

import java.util.*;

public class LE_1157_Online_Majority_Element_In_Subarray {
    /**
     * Implementing the class MajorityChecker, which has the following API:
     *
     * MajorityChecker(int[] arr) constructs an instance of MajorityChecker with the given array arr;
     * int query(int left, int right, int threshold) has arguments such that:
     * 0 <= left <= right < arr.length representing a subarray of arr;
     * 2 * threshold > right - left + 1, ie. the threshold is always a strict majority of the length of the subarray
     * Each query(...) returns the element in arr[left], arr[left+1], ..., arr[right] that occurs at least threshold times, or -1 if no such element exists.
     *
     * Example:
     * MajorityChecker majorityChecker = new MajorityChecker([1,1,2,2,1,1]);
     * majorityChecker.query(0,5,4); // returns 1
     * majorityChecker.query(0,3,3); // returns -1
     * majorityChecker.query(2,3,2); // returns 2
     *
     * Constraints:
     * 1 <= arr.length <= 20000
     * 1 <= arr[i] <= 20000
     * For each query, 0 <= left <= right < len(arr)
     * For each query, 2 * threshold > right - left + 1
     * The number of queries is at most 10000
     *
     * Hard
     */

    /**
     * https://leetcode.com/problems/online-majority-element-in-subarray/discuss/356227/C%2B%2B-Codes-of-different-approaches-(Random-Pick-Trade-off-Segment-Tree-Bucket)
     *
     * 4 different solutions
     */

    /**
     * Random Pick
     * As the majority occurs more than half in the interval [l, r], we will have the probability of more than 1/2 to
     * find the "more than half" majority if we randomly pick a number in the interval. Thus, if we randomly pick
     * try_bound times, we will have the probability of (1-(1/2)) ^ try_bound not to find the "more than half" majority.
     * The probability will be less than 1e-6 if we set try_bound as 20. If we find nothing in try_bound times, we can
     * claim that there is no "more than half" majority.
     *
     * Here we use No.5 to count the occurrences of a randomly picked number:
     * "We can use a Map<int, List<int>> to store the positions of each number in [l, r]. The key in the
     * unordered_map represents a number and the value represents the positions of that number in increasing order.
     * Thus we can count the occurrences of a number in a given interval [l, r] in O(log(n)) time by using binary
     * search."
     *
     * Pre-calculation: O(n)
     * Query: O(sqrt(n) * log(n))
     */
    class MajorityChecker {
        Map<Integer, List<Integer>> map;
        int[] nums;

        public MajorityChecker(int[] arr) {
            map = new HashMap<>();
            nums = arr;

            for (int i = 0; i < arr.length; i++) {
                map.computeIfAbsent(arr[i], l -> new ArrayList<>()).add(i);
            }
        }

        public int query(int left, int right, int threshold) {
            for (int i = 0; i < 20; i++) {
                int min = left;
                int max = right;
                int candidate = nums[min + (int) (Math.random() * (max - min + 1))];

                List<Integer> temp = map.get(candidate);
                if (temp.size() < threshold) continue;

                /**
                 * current candidate appearance number >= threshold, now we want to find out how many times it appears
                 * between range nums[left] ~ nums[right]. Do binary search on the list, get range, which will be [low,high]
                 */
                int low = Collections.binarySearch(temp, left);
                int high = Collections.binarySearch(temp, right);

                /**
                 * if low or high is negative, means not found, will return (-(insert position) - 1)
                 * low = -insert-1 => insert = -(low + 1)
                 *
                 * Make high positive, then high-- (can be 0)
                 */
                if (low < 0) low = -low - 1;
                if (high < 0) high = (-high - 1) - 1;

                if (high - low + 1 >= threshold) return candidate;
            }

            return -1;
        }
    }
}
