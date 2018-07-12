package lintcode;

/**
 * Created by yuank on 7/11/18.
 */
public class LI_005_Kth_Largest_Number {
    /**
         Find K-th largest element in an array.

         Example
         In array [9,3,2,4,8], the 3rd largest element is 4.

         In array [1,2,3,4,5], the 1st largest element is 5, 2nd largest element is 4, 3rd largest element is 3 and etc.

         Challenge
         O(n) time, O(1) extra memory.
     */

    public int kthLargestElement(int k, int[] nums) {
        // write your code here
        return quickSelect(nums, 0, nums.length - 1, k);
    }

    private int quickSelect(int[] nums, int left, int right, int k) {
        if (left == right) {
            return nums[left];
        }

        int i = left, j = right;
        int pivot = nums[(i + j) / 2];

        while (i <= j) {
            while (i <= j && nums[i] > pivot) {
                i++;
            }
            while (i <= j && nums[j] < pivot) {
                j--;
            }
            if (i <= j) {
                int tmp = nums[i];
                nums[i] = nums[j];
                nums[j] = tmp;
                i++;
                j--;
            }
        }

        if (left + k - 1 <= j) {
            return quickSelect(nums, left, j, k);
        }
        if (left + k - 1 >= i) {
            return quickSelect(nums, i, right, k - (i - left));
        }
        return nums[j + 1];
    }

    //Version that does not need to calcualte k when recurssing
    public int kthLargestElement1(int k, int[] nums) {
        // write your code here
        return quickSelect1(nums, 0, nums.length - 1, k - 1);
    }

    private int quickSelect1(int[] nums, int start, int end, int k) {
        if (start >= end) {
            return nums[start];
        }

        int left = start;
        int right= end;
        int pivot = nums[(left + right) / 2];

        while (left <= right) {
            while (left <= right && nums[left] > pivot) {
                left++;
            }
            while (left <= right && nums[right] < pivot) {
                right--;
            }
            if (left <= right) {
                int tmp = nums[left];
                nums[left] = nums[right];
                nums[right] = tmp;

                left++;
                right--;
            }
        }

        if (right >= k && right >= start) {
            return quickSelect1(nums, start, right, k);
        } else if (left <= k && left <= end) {
            return quickSelect1(nums, left, end, k);
        } else {
            return nums[k];
        }
    }
}
