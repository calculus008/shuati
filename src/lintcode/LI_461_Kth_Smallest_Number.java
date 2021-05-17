package lintcode;

/**
 * Created by yuank on 7/12/18.
 */
public class LI_461_Kth_Smallest_Number {
    /**
         Find the kth smallest numbers in an unsorted integer array.

         Example
         Given [3, 4, 1, 2, 5], k = 3, the 3rd smallest numbers are [1, 2, 3].

         Challenge
         An O(nlogn) algorithm is acceptable, if you can do it in O(n), that would be great.
     */

    class Solution1 {
        public int kthSmallest(int k, int[] nums) {
            if (nums == null || nums.length == 0) {
                return 0;
            }

            return quickFind(nums, 0, nums.length - 1, k);
        }

        public int quickFind(int[] nums, int start, int end, int k) {
            if (start >= end) {
                return nums[start];
            }

            int left = start;
            int right = end;
            int pivot = nums[(start + end) / 2];

            while (left <= right) {
                while (left <= right && nums[left] < pivot) {
                    left++;
                }

                while (left <= right && nums[right] > pivot) {
                    right--;
                }

                if (left <= right) {
                    int temp = nums[left];
                    nums[left] = nums[right];
                    nums[right] = temp;

                    left++;
                    right--;
                }
            }

            if (start + k - 1 <= right) {
                return quickFind(nums, start, right, k);
            }

            if (start + k - 1 >= left) {
                return quickFind(nums, left, end, k - (left - start));
            }

            return nums[right + 1];
        }
    }


    /**
      The above code change k value so that it is relative to the start index of the recursion.

      Or, we don't change k, just pass it as index,
      here is the code:
     */
    class Solution2 {
        public int kthSmallest1(int k, int[] nums) {
            if (nums == null || nums.length == 0) {
                return 0;
            }

            /**
             * !!!  Here we pass the index, therefore "k-1", meaning, it is the index that we should finally return
             * "k - 1" : we pass index.
             */
            return quickSelect(nums, 0, nums.length - 1, k - 1);
        }

        public int quickSelect(int[] nums, int start, int end, int k) {
            if (start >= end) {
                return nums[start];
            }

            int left = start;
            int right = end;
            int pivot = nums[(start + end) / 2];

            while (left <= right) {
                while (left <= right && nums[left] < pivot) {
                    left++;
                }

                while (left <= right && nums[right] > pivot) {
                    right--;
                }

                if (left <= right) {
                    int temp = nums[left];
                    nums[left] = nums[right];
                    nums[right] = temp;

                    left++;
                    right--;
                }
            }

            //!!!
            if (right >= k && right >= start) {
                return quickSelect(nums, start, right, k);
            } else if (left <= k && left <= end) {
                return quickSelect(nums, left, end, k);
            } else {
                return nums[k];
            }
        }
    }

}
