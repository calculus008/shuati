package leetcode;

import java.util.*;

public class LE_2970_Count_The_Number_Of_Incremovable_Subarrays_I {
    /**
     * You are given a 0-indexed array of positive integers nums.
     *
     * A subarray of nums is called incremovable if nums becomes strictly increasing on removing the subarray.
     * For example, the subarray [3, 4] is an incremovable subarray of [5, 3, 4, 6, 7] because removing this
     * subarray changes the array [5, 3, 4, 6, 7] to [5, 6, 7] which is strictly increasing.
     *
     * Return the total number of incremovable subarrays of nums.
     *
     * (!!!)Note that an empty array is considered strictly increasing.
     *
     * A subarray is a contiguous non-empty sequence of elements within an array.
     *
     *
     *
     * Example 1:
     * Input: nums = [1,2,3,4]
     * Output: 10
     * Explanation: The 10 incremovable subarrays are: [1], [2], [3], [4], [1,2], [2,3], [3,4], [1,2,3], [2,3,4],
     * and [1,2,3,4], because on removing any one of these subarrays nums becomes strictly increasing. Note that
     * you cannot select an empty subarray.
     *
     * Example 2:
     * Input: nums = [6,5,7,8]
     * Output: 7
     * Explanation: The 7 incremovable subarrays are: [5], [6], [5,7], [6,5], [5,7,8], [6,5,7] and [6,5,7,8].
     * It can be shown that there are only 7 incremovable subarrays in nums.
     *
     * Example 3:
     * Input: nums = [8,7,6,6]
     * Output: 3
     * Explanation: The 3 incremovable subarrays are: [8,7,6], [7,6,6], and [8,7,6,6]. Note that [8,7] is not an
     * incremovable subarray because after removing [8,7] nums becomes [6,6], which is sorted in ascending order
     * but not strictly increasing.
     *
     *
     * Constraints:
     *
     * 1 <= nums.length <= 50
     * 1 <= nums[i] <= 50
     *
     * Hard
     *
     * https://leetcode.com/problems/count-the-number-of-incremovable-subarrays-i
     */

    /**
     * https://leetcode.com/problems/count-the-number-of-incremovable-subarrays-ii/solutions/4454192/best-solution-o-n-with-0ms-in-java-c-c-c-python-3-javascript-typescript/
     *
     * Only difference from LE_2972_Count_The_Number_Of_Incremovable_Subarrays_II is the return type is int, not long.
     */
    class Solution {
        public int incremovableSubarrayCount(int[] nums) {
            int n = nums.length;
            int leftIdx = 0;

            for (int i = 1; i < n; i++) {
                if (nums[i - 1] < nums[i]) {
                    leftIdx++;
                } else {
                    break;
                }
            }

            if (leftIdx == n - 1) {
                int ans = n * (n + 1) / 2;
                return ans;
            }

            int rightIdx = n - 1;

            for (int i = n - 1; i > 0; i--) {
                if (nums[i - 1] < nums[i]) {
                    rightIdx--;
                } else {
                    break;
                }
            }

            int totalIncremovableSubarrays = 0;
            totalIncremovableSubarrays += (n - rightIdx) + 1;

            int l = 0, r = rightIdx;

            while (l <= leftIdx) {
                while (r < n && nums[l] >= nums[r]) {
                    r++;
                }
                totalIncremovableSubarrays += (n - r + 1);
                l++;
            }

            return totalIncremovableSubarrays;
        }

    }


    /**
     * Time : O(n ^ 3)
     * <p>
     * Loop k: Checks each element in the array, skipping elements within the current subarray range
     * (defined by indices i and j). For each element, it verifies if it's strictly greater than the last
     * element seen outside the current subarray.
     * <p>
     * 'lst' Variable: Keeps track of the last element seen outside the current subarray range
     */
    class Solution_Brutal_Force {
        public int incremovableSubarrayCount(int[] nums) {
            int ans = 0;
            int n = nums.length;

            for (int i = 0; i < n; i++) {
                for (int j = i; j < n; j++) {
                    boolean ok = true;
                    int last = -1;

                    for (int k = 0; k < n; k++) {
                        if (k >= i && k <= j) {
                            continue;
                        } else {
                            if (last >= nums[k]) {
                                ok = false;
                                break;
                            }
                            last = nums[k];
                        }
                    }

                    ans += ok ? 1 : 0;
                }
            }
            return ans;
        }
    }
}

