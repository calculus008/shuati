package leetcode;

import java.util.*;

public class LE_1438_Longest_Continuous_Subarray_With_Absolute_Diff_Less_Than_Or_Equal_To_Limit {
    /**
     * Given an array of integers nums and an integer limit, return the size of the longest non-empty subarray such that
     * the absolute difference between any two elements of this subarray is less than or equal to limit.
     *
     * Example 1:
     * Input: nums = [8,2,4,7], limit = 4
     * Output: 2
     * Explanation: All subarrays are:
     * [8] with maximum absolute diff |8-8| = 0 <= 4.
     * [8,2] with maximum absolute diff |8-2| = 6 > 4.
     * [8,2,4] with maximum absolute diff |8-2| = 6 > 4.
     * [8,2,4,7] with maximum absolute diff |8-2| = 6 > 4.
     * [2] with maximum absolute diff |2-2| = 0 <= 4.
     * [2,4] with maximum absolute diff |2-4| = 2 <= 4.
     * [2,4,7] with maximum absolute diff |2-7| = 5 > 4.
     * [4] with maximum absolute diff |4-4| = 0 <= 4.
     * [4,7] with maximum absolute diff |4-7| = 3 <= 4.
     * [7] with maximum absolute diff |7-7| = 0 <= 4.
     * Therefore, the size of the longest subarray is 2.
     *
     * Example 2:
     * Input: nums = [10,1,2,4,7,2], limit = 5
     * Output: 4
     * Explanation: The subarray [2,4,7,2] is the longest since the maximum absolute diff is |2-7| = 5 <= 5.
     *
     * Example 3:
     * Input: nums = [4,2,2,2,4,4,2,2], limit = 0
     * Output: 3
     *
     * Constraints:
     * 1 <= nums.length <= 105
     * 1 <= nums[i] <= 109
     * 0 <= limit <= 109
     *
     * Medium
     */

    /**
     * Sliding Window + Deque (Mono Stack)
     *
     * 找符合条件的subarray的最大长度，很容易想到是要用Sliding Window. 问题在于，每次移动左右边界，判断新的window是否符合条件，如果
     * 用Brutal Force, 那就需要计算美个元素和其他元素的差，O(n ^ 2), 这肯定不对。
     *
     * !!! 关键：
     * "Absolute difference between any two elements is less than or equal to limit" is basically => "Absolute difference between min and max elements of subarray"
     * 所以，需要保存当前subarray的最大和最小值 -> Use mono queue (Deque)
     *
     * Now the question becomes => find the longest subarray in which the absolute difference between min and max is less
     * than or equal to limit. What we can do is to have two pointers: left and right, and then find the longest subarray
     * for every right pointer (iterate it) by shrinking left pointer. And return the longest one among them.
     *
     * maxDeque and minDeque are used to store the max and min value (both at the head of the Deque !!!) in this particular
     * subarray which range is from the left pointer(l) to right pointer(r). thinking this subarray is Array[l , r], so
     * two deques store max and min value individually.
     *
     * Time and Space : O(n)
     */
    class Solution1 {
        public int longestSubarray(int[] nums, int limit) {
            Deque<Integer> maxDeque = new LinkedList<>();
            Deque<Integer> minDeque = new LinkedList<>();

            int res = 0;
            for (int right = 0, left = 0; right < nums.length; right++) {
                while (!maxDeque.isEmpty() && maxDeque.peekLast() < nums[right]) {//!!!while
                    maxDeque.removeLast();
                }
                maxDeque.addLast(nums[right]);

                while (!minDeque.isEmpty() && minDeque.peekLast() > nums[right]) {//!!!while
                    minDeque.removeLast();
                }
                minDeque.addLast(nums[right]);

                while (maxDeque.peekFirst() - minDeque.peekFirst() > limit) {//!!!while
                    if (maxDeque.peekFirst() == nums[left]) {//!!!nums[left]
                        maxDeque.removeFirst();
                    }
                    if (minDeque.peekFirst() == nums[left]) {//!!!nums[left]
                        minDeque.removeFirst();
                    }
                    left++;//!!!
                }

                res = Math.max(res, right - left + 1);
            }

            return res;
        }
    }

    /**
     * Sliding Winow + TreeMap
     * Use TreeMap to get min/max.
     * More costly than Solution1, because TreeMap takes O(nlogn)
     */
    class Solution2 {
        public int longestSubarray(int[] A, int limit) {
            int left = 0, right;
            TreeMap<Integer, Integer> m = new TreeMap<>();
            int res = 0;

            for (right = 0; right < A.length; right++) {
                m.put(A[right], 1 + m.getOrDefault(A[right], 0));

                while (m.lastEntry().getKey() - m.firstEntry().getKey() > limit) {
                    m.put(A[left], m.get(A[left]) - 1);
                    if (m.get(A[left]) == 0) {
                        m.remove(A[left]);
                    }
                    left++;
                }
                res = Math.max(res, right - left + 1);
            }

            return res;
        }
    }
}
