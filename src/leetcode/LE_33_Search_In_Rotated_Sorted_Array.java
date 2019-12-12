package leetcode;

/**
 * Created by yuank on 2/26/18.
 */
public class LE_33_Search_In_Rotated_Sorted_Array {
    /**
        Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

        (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).

        You are given a target value to search. If found in the array return its index, otherwise return -1.

        You may assume NO DUPLICATE exists in the array.
     */

    class Solution_Practice {
        public int search(int[] nums, int target) {
            if (nums == null || nums.length == 0) return - 1;

            int l = 0, h = nums.length - 1;

            while (l + 1 < h) {
                int m = l + (h - l) / 2;

                /**
                 * !!!
                 * "<="
                 *
                 * when calculating m, (h - l) / 2 may be rounded, so it's possible m can be equal to h or l.
                 * So need to use "<=" here.
                 */
                if (nums[m] <= nums[h]) {
                    /**
                     * "<="
                     */
                    if (nums[m] <= target && target <= nums[h]) {
                        l = m;
                    } else {
                        h = m;
                    }
                } else {
                    if (nums[l] <= target && target <= nums[m]) {
                        h = m;
                    } else {
                        l = m;
                    }
                }
            }

            if (nums[l] == target) return l;

            if (nums[h] == target) return h;

            return -1;
        }
    }

    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int start = 0;
        int end = nums.length - 1;

        while (start + 1 < end) {
            int mid = (end - start) / 2 + start;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[start] <= nums[mid]) {//!!! "<=", to be compatible with LE_81
                if(nums[start] <= target && target <= nums[mid]) {
                    end = mid;
                } else {
                    start = mid;
                }
            } else {
                if(nums[mid] <= target && target <= nums[end]) {
                    start = mid;
                } else {
                    end = mid;
                }
            }
        }

        if (nums[start] == target) return start;
        if (nums[end] == target) return end;
        return -1;
    }

    /**
     * jiuzhang solution
     */

    public int search_JiuZhang(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int start = 0;
        int end = nums.length - 1;
        while (start + 1 < end) {
            int mid = (end - start) / 2 + start;

            if (nums[start] < nums[mid]) {
                if (nums[start] <= target && target <= nums[mid]) {
                    end = mid;
                } else {
                    start = mid;
                }
            } else {
                if (nums[mid] <= target && target <= nums[end]) {
                    start = mid;
                } else {
                    end = mid;
                }
            }
        }

        if (nums[start] == target) {
            return start;
        }

        if (nums[end] == target) {
            return end;
        }

        return -1;
    }
}
