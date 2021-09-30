package leetcode;

public class LE_775_Global_And_Local_Inversions {
    /**
     * You are given an integer array nums of length n which represents a permutation of all the integers in the range [0, n - 1].
     *
     * The number of global inversions is the number of the different pairs (i, j) where:
     *
     * 0 <= i < j < n
     * nums[i] > nums[j]
     * The number of local inversions is the number of indices i where:
     *
     * 0 <= i < n - 1
     * nums[i] > nums[i + 1]
     * Return true if the number of global inversions is equal to the number of local inversions.
     *
     * Example 1:
     * Input: nums = [1,0,2]
     * Output: true
     * Explanation: There is 1 global inversion and 1 local inversion.
     *
     * Example 2:
     * Input: nums = [1,2,0]
     * Output: false
     * Explanation: There are 2 global inversions and 1 local inversion.
     *
     * Constraints:
     * n == nums.length
     * 1 <= n <= 105
     * 0 <= nums[i] < n
     * All the integers of nums are unique.
     * nums is a permutation of all the numbers in the range [0, n - 1].
     *
     * Medium
     *
     * https://leetcode.com/problems/global-and-local-inversions/
     */

    /**
     * Brutal Force TLE
     *
     * All local inversions are global inversions.
     * If the number of global inversions is equal to the number of local inversions,
     * it means that all global inversions in permutations are local inversions.
     *
     * In other words, local inversion is special kind of global inversion. For each element at index i,
     * check every index j >= i + 1, if it is a global inversion, if yes, then return false.
     *
     * Time  : O(n ^ 2)
     * Space : O(1)
     */
    class Solution1 {
        public boolean isIdealPermutation(int[] nums) {
            int n = nums.length;
            for (int i = 0; i < n; i++) {
                for (int j = i + 2; j < n; j++) {
                    if (nums[i] > nums[j]) return false;
                }
            }

            return true;
        }
    }

    /**
     * Improved from Brutal Force, scan backward, remember the min so far, if element nums[i - 2] > min, then
     * there's one global inversion, return false.
     *
     * Time  : O(n)
     * Space : O(1)
     */
    class Solution2 {
        public boolean isIdealPermutation(int[] nums) {
            int n = nums.length;
            int min = Integer.MAX_VALUE;

            for (int i = n - 1; i >= 2; i--) {
                min = Math.min(min, nums[i]);
                if (nums[i - 2] > min) return false;
            }

            return true;
        }
    }

    /**
     * Try to arrange an ideal permutation.
     * Then I found that to place number i
     * I could only place i at A[i-1], A[i] or A[i+1].
     * So it came up to me,
     * It will be easier just to check if all A[i] - i equals to -1, 0 or 1.
     */
    class Solution {
        public boolean isIdealPermutation(int[] nums) {
            int n = nums.length;

            for (int i = 0; i < n; i++) {
                if (Math.abs(nums[i] - i) > 1) return false;
            }

            return true;
        }
    }
}
