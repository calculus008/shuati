package leetcode;

import java.util.*;

public class LE_1509_Minimum_Difference_Between_Largest_And_Smallest_Value_In_Three_Moves {
    /**
     * Given an array nums, you are allowed to choose one element of nums and change it by any value in one move.
     *
     * Return the minimum difference between the largest and smallest value of nums after perfoming at most 3 moves.
     *
     * Example 1:
     * Input: nums = [5,3,2,4]
     * Output: 0
     * Explanation: Change the array [5,3,2,4] to [2,2,2,2].
     * The difference between the maximum and minimum is 2-2 = 0.
     *
     * Example 2:
     * Input: nums = [1,5,0,10,14]
     * Output: 1
     * Explanation: Change the array [1,5,0,10,14] to [1,1,0,1,1].
     * The difference between the maximum and minimum is 1-0 = 1.
     *
     * Example 3:
     * Input: nums = [6,6,0,1,1,4,6]
     * Output: 2
     *
     * Example 4:
     * Input: nums = [1,5,6,14,15]
     * Output: 1
     *
     * Constraints:
     * 1 <= nums.length <= 10^5
     * -10^9 <= nums[i] <= 10^9
     *
     * Medium
     */

    /**
     * Sorting
     *
     * Intuition
     * If we can do 0 move, return max(A) - min(A)
     * If we can do 1 move, return min(the second max(A) - min(A), the max(A) - second min(A))
     * and so on.
     *
     *
     * Explanation
     * We have 4 plans:
     *
     * kill 3 biggest elements
     * kill 2 biggest elements + 1 smallest elements
     * kill 1 biggest elements + 2 smallest elements
     * kill 3 smallest elements
     *
     * Example :
     *
     * A = [1,5,6,13,14,15,16,17]
     * n = 8
     *
     * Case 1: kill 3 biggest elements
     *
     * All three biggest elements can be replaced with 14
     * [1,5,6,13,14,15,16,17] -> [1,5,6,13,14,14,14,14] == can be written as A[n-4] - A[0] == (14-1 = 13)
     *
     * Case 2: kill 2 biggest elements + 1 smallest elements
     *
     * [1,5,6,13,14,15,16,17] -> [5,5,6,13,14,15,15,15] == can be written as A[n-3] - A[1] == (15-5 = 10)
     *
     * Case 3: kill 1 biggest elements + 2 smallest elements
     *
     * [1,5,6,13,14,15,16,17] -> [6,6,6,13,14,15,16,16] == can be written as A[n-2] - A[2] == (16-6 = 10)
     *
     * Case 4: kill 3 smallest elements
     *
     * [1,5,6,13,14,15,16,17] -> [13,13,13,13,14,15,16,17] == can be written as A[n-1] - A[3] == (17-13 = 4)
     *
     * Answer is minimum of all these cases!
     *
     * Time : O(nlogn)
     * Space : O(1)
     */
    class Solution1 {
        public int minDifference(int[] nums) {
            int n = nums.length;
            if (n < 5) return 0;

            Arrays.sort(nums);

            int res = Integer.MAX_VALUE;
            for (int i = 0; i < 4; i++) {
                res = Math.min(res, nums[n - 4 + i] - nums[i]);
            }

            return res;
        }
    }

    /**
     * None complete sorting, use Heap to get the biggest 4 and smallest 4 elements.
     *
     * Time : O(nlog4) = O(n)
     * Space : O(1)
     */
    class Solution2 {
        public int minDifference(int[] nums) {
            int n = nums.length;
            if (n < 5) return 0;

            PriorityQueue<Integer> min = new PriorityQueue<>();//4 largest numbers
            PriorityQueue<Integer> max = new PriorityQueue<>((a, b) -> Integer.compare(b, a));//4 smallest numbers

            for (int num : nums) {
                min.offer(num);
                max.offer(num);

                /**
                 * !!!
                 */
                if (max.size() > 4) {
                    max.remove();
                    min.remove();
                }
            }

            List<Integer> list = new ArrayList<>();
            while (!min.isEmpty()) {
                list.add(min.remove());
            }

            int res = Integer.MAX_VALUE;
            for (int i = list.size() - 1; i >= 0; i--) {
                res = Math.min(res, list.get(i) - max.remove());
            }

            return res;
        }
    }
}
