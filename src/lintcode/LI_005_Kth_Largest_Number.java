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

         Quick Select
     */

    public int kthLargestElement(int k, int[] nums) {
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

    /**
     * Version that does not need to calculate k when doing recursion
     */
    public int kthLargestElement1(int k, int[] nums) {
        // write your code here
        return quickSelect1(nums, 0, nums.length - 1, k - 1);
    }

    /**
     * here k is index (zero based)
     */
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

    /**
     *九章算法强化班里讲过的标准 Parition 模板。
     */
    class Solution1 {
        public int kthLargestElement(int k, int[] nums) {
            if (nums == null || nums.length == 0 || k < 1 || k > nums.length){
                return -1;
            }
            /**
             * !!!
             * partition()是从小到大，所以，求第k大元素转换为求第 n - k + 1小的元素，
             * n - k + 1 = nums.length - 1 - k + 1 = nums.length - k
             */
            return partition(nums, 0, nums.length - 1, nums.length - k);
        }

        private int partition(int[] nums, int start, int end, int k) {
            if (start == end) {
                return nums[k];
            }

            int left = start, right = end;
            int pivot = nums[start + (end - start) / 2];

            while (left <= right) {
                /**
                 * !!!
                 * "nums[left] < pivot", NOT "nums[pivot]"!!!
                 *
                 * !!!
                 * "<", not "<="
                 */
                while (left <= right && nums[left] < pivot) {
                    left++;
                }
                while (left <= right && nums[right] > pivot) {
                    right--;
                }
                if (left <= right) {
                    swap(nums, left, right);

                    /**
                     * after the following steps, since while condition is "left <= right",
                     * right must be smaller than left, two possibilities, right + 1 = left, or
                     * right + 2 = left
                     */
                    left++;//!!!
                    right--;//!!!
                }
            }

            /**
             * start_________|_|____________end
             *          right  left
             */
            if (k <= right) {//move to left section
                return partition(nums, start, right, k);
            }
            if (k >= left) {//move to right section
                return partition(nums, left, end, k);
            }

            //k is between right and left
            return nums[k];
        }

        private void swap(int[] nums, int i, int j) {
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }
    }

    /**
     *标准的 Quick Select 算法
     */
    class Solution2 {

        public int kthLargestElement(int k, int[] nums) {
            int low = 0, high = nums.length - 1;

            while (low <= high) {
                int pivot = nums[high];
                int index = low - 1;

                for (int i = low; i < high; i++) {
                    if (nums[i] > nums[high]) {
                        swap(nums, i, ++index);
                    }
                }

                swap(nums, ++index, high);

                if (index == k - 1) {
                    return nums[index];
                }

                if (index < k - 1) {
                    low = index + 1;
                } else {
                    high = index - 1;
                }
            }
            return -1;
        }

        private void swap(int[] nums, int a, int b) {
            int temp = nums[a];
            nums[a] = nums[b];
            nums[b] = temp;
        }
    }
}
