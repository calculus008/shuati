package Interviews.Apple;

public class Search_In_Rotated_Sorted_Array_With_Duplicates {
    /**
     * Variation from LE_33_Search_In_Rotated_Sorted_Array, which has no duplicate. Here we have duplicates.
     *
     * How Duplicates Are Handled:
     * When duplicates are encountered (i.e., nums[left] == nums[mid] == nums[right]), the solution simply shrinks the search space by incrementing left
     * and decrementing right. This is necessary because duplicates can make it difficult to determine which part of the array is sorted, which is the
     * basis of the binary search approach in this problem.
     *
     * Time Complexity: O(log n) in the average case.
     *                  In the worst case (due to duplicates), it could degrade to O(n) if we need to linearly skip through duplicates.
     * Space Complexity: O(1), as we are using only constant extra space.
     *
     */

    public class Solution {
        public int search(int[] nums, int target) {
            if (nums == null || nums.length == 0) {
                return -1;
            }

            int l = 0, r = nums.length - 1;

            while (l <= r) {
                int mid = l + (r - l) / 2;

                if (nums[mid] == target) {
                    return mid;
                }

                if (nums[l] == nums[mid] && nums[mid] == nums[r]) { // If we cannot determine which part is sorted due to duplicates
                    l++;
                    r--;
                } else if (nums[l] <= nums[mid]) {// Left half is sorted
                    if (nums[l] <= target && target < nums[mid]) {
                        r = mid - 1;
                    } else {
                        l = mid + 1;
                    }
                } else {// Right half is sorted
                    if (nums[mid] < target && target <= nums[r]) {
                        l = mid + 1;
                    } else {
                        r = mid - 1;
                    }
                }
            }

            return -1;
        }

        // Example usage
//        public static void main(String[] args) {
//            Solution solution = new Solution();
//            int[] nums = {4, 5, 6, 7, 0, 1, 2};
//            int target = 5;
//            System.out.println(solution.search(nums, target));  // Output: 1
//        }
    }

}
