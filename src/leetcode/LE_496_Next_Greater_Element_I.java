package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class LE_496_Next_Greater_Element_I {
    /**
     * You are given two arrays (without duplicates) nums1 and nums2
     * where nums1’s elements are subset of nums2. Find all the next
     * greater numbers for nums1's elements in the corresponding
     * places of nums2.
     *
     * The Next Greater Number of a number x in nums1 is the first
     * greater number to its right in nums2. If it does not exist,
     * output -1 for this number.
     *
     * Example 1:
     * Input: nums1 = [4,1,2], nums2 = [1,3,4,2].
     * Output: [-1,3,-1]
     * Explanation:
     *     For number 4 in the first array, you cannot find the next
     *     greater number for it in the second array, so output -1.
     *     For number 1 in the first array, the next greater number
     *     for it in the second array is 3.
     *     For number 2 in the first array, there is no next greater
     *     number for it in the second array, so output -1.
     *
     * Example 2:
     * Input: nums1 = [2,4], nums2 = [1,2,3,4].
     * Output: [3,-1]
     * Explanation:
     *     For number 2 in the first array, the next greater number
     *     for it in the second array is 3.
     *     For number 4 in the first array, there is no next greater
     *     number for it in the second array, so output -1.
     *
     * Note:
     * All elements in nums1 and nums2 are unique.
     * The length of both nums1 and nums2 would not exceed 1000.
     *
     * Easy
     */

    /**
     * Mono Stack
     * https://leetcode.com/problems/next-greater-element-i/solution/
     *
     * Mono Stack O(n) : each element just in/out stack once
     *
     * Iterate nums2 from left to right
     *
     * Example:  nums1 = [2,4], nums2 = [1,2,3,4].
     *
     * 1
     * stack: 1
     * map:
     *
     * 2
     * stack: 2
     * map: 1 -> 2
     *
     * 3
     * stack: 3
     * map: 1 -> 2
     *      2 -> 3
     *
     * 4
     * stack: 4
     * map: 1 -> 2
     *      2 -> 3
     *      3 -> 4
     *
     * nums1: 2 => 3
     *        4 => -1
     *
     */
    class Solution1 {
        public int[] nextGreaterElement(int[] nums1, int[] nums2) {
            Map<Integer, Integer> map = new HashMap<>();
            Deque<Integer> stack = new ArrayDeque<>();

            for (int i = 0; i < nums2.length; i++) {
                while (!stack.isEmpty() && nums2[i] > stack.peek()) {
                    map.put(stack.pop(), nums2[i]);
                }
                stack.push(nums2[i]);
            }

            /**
             * !!!
             */
            while (!stack.isEmpty()) {
                map.put(stack.pop(), -1);
            }

            int[] res = new int[nums1.length];
            for (int i = 0; i < nums1.length; i++) {
                res[i] = map.get(nums1[i]);
            }

            return res;
        }
    }

    /**
     * left to right, save index in stack
     */
    class Solution1_1 {
        public int[] nextGreaterElement(int[] nums1, int[] nums2) {
            Map<Integer, Integer> map = new HashMap<>();
            Deque<Integer> stack = new ArrayDeque<>();

            /**
             * #1
             */
            for (int i = 0; i < nums2.length; i++) {
                while (!stack.isEmpty() && nums2[i] > nums2[stack.peek()]) {
                    map.put(nums2[stack.pop()], nums2[i]);
                }
                stack.push(i);
            }

            /**
             * #2
             */
            while (!stack.isEmpty()) {
                map.put(nums2[stack.pop()], -1);
            }

            /**
             * #3
             */
            int[] res = new int[nums1.length];
            for (int i = 0; i < nums1.length; i++) {
                res[i] = map.get(nums1[i]);
            }

            return res;
        }
    }

    /**
     * Mono stack
     * Iterate from right to left
     *
     * Example:  nums1 = [2,4], nums2 = [1,2,3,4].
     *
     * 4
     * stack: 4
     * map: 4 -> -1
     *
     * 3
     * stack: 4 3
     * map: 4 -> -1
     *      3 -> 4
     *
     * 2
     * stack: 4 3 2
     * map: 4 -> -1
     *      3 -> 4
     *      2 -> 3
     *
     * 1
     * stack: 4 3 2 1
     * map: 4 -> -1
     *      3 -> 2
     *      2 -> 3
     *      1 -> 2
     */
    class Solution2 {
        public int[] nextGreaterElement(int[] nums1, int[] nums2) {
            Map<Integer, Integer> map = new HashMap<>();
            Deque<Integer> stack = new ArrayDeque<>();

            for (int i = nums2.length - 1; i >= 0; i--) {
                /**
                 * !!!
                 * Since the question says "no duplicate", we can use ">" or ">=",
                 * otherwise, need to use ">="
                 */
                while (!stack.isEmpty() && nums2[i] >= stack.peek()) {
                    stack.pop();
                }
                map.put(nums2[i], stack.isEmpty() ? -1 : stack.peek());
                stack.push(nums2[i]);
            }

            int[] res = new int[nums1.length];
            for (int i = 0; i < nums1.length; i++) {
                res[i] = map.get(nums1[i]);
            }

            return res;
        }
    }
}
