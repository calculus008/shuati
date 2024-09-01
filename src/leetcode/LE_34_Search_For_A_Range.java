package leetcode;

/**
 * Created by yuank on 2/26/18.
 */
public class LE_34_Search_For_A_Range {
    /**
        Given an array of integers sorted in ascending order, find the starting and ending
        position of a given target value.

        Your algorithm's runtime complexity must be in the order of O(log n).

        If the target is not found in the array, return [-1, -1].

        For example,
        Given [5, 7, 7, 8, 8, 10] and target value 8,
        return [3, 4].
     **/



    class Solution {
        //https://www.youtube.com/watch?v=bPdnC5X5xDw
        public int[] searchRange(int[] nums, int target) {
            if (nums == null || nums.length == 0) return new int[]{-1, -1};

            int first = findFirst(nums, target);
            if (first == -1) return new int[]{-1, -1};

            int last = findLast(nums, target);

            return new int[]{first, last};
        }

        public int findFirst(int[] nums, int target) {
            int l = 0, r = nums.length - 1;
            while (l < r) {//!!! Must be "<", not "<=="
                int mid = (r - l) / 2 + l;
                if (nums[mid] >= target) {
                    r = mid;
                } else {
                    l = mid + 1;
                }
            }

            return nums[l] == target ? l : -1;
        }

        public int findLast(int[] nums, int target) {
            int l = 0, r = nums.length - 1;
            while (l < r) {
                int mid = (r - l + 1) / 2 + l; //!!!
                if (nums[mid] <= target) {
                    l = mid;
                } else {
                    r = mid - 1;
                }
            }

            return nums[l] == target ? l : -1;
        }
    }




    /**
     * Similar
     * Simple_Queries_With_Two_Arrays  (upper bound/lower bound)
     *
     * 这里不能用binary search找upper/lower bound的方法，
     * 因为我们需要知道target是否在nums中存在。
     */
    class Solution1 {
        public int[] searchRange(int[] nums, int target) {
            if (nums == null || nums.length == 0) {
                return new int[]{-1, -1};
            }

            int start = findFirst(nums, target);
            //!!!
            if (start == -1) return new int[]{-1, -1};

            int end = findLast(nums, target);

            return new int[]{start, end};
        }

        private int findFirst(int[] nums, int target) {
            int start = 0;
            int end = nums.length - 1;

            while (start + 1 < end) {
                int mid = (end - start) / 2 + start;
                if (nums[mid] < target) {
                    start = mid;
                } else {
                    end = mid;
                }
            }

            /**
             * !!!
             * check start first, then end
             */
            if (nums[start] == target) {
                return start;
            }

            if (nums[end] == target) {
                return end;
            }

            return -1;
        }

        private int findLast(int[] nums, int target) {
            int start = 0;
            int end = nums.length - 1;

            while (start + 1 < end) {
                int mid = (end - start) / 2 + start;
                //!!!
                if (nums[mid] > target) {
                    end = mid;
                } else {
                    start = mid;
                }
            }

            /**
             * !!!
             * check end first, then start
             */
            if (nums[end] == target) {
                return end;
            }

            if (nums[start] == target) {
                return start;
            }

            return -1;

        }
    }

    public class Solution_Jiuzhang {
        public int[] searchRange(int[] A, int target) {
            if (A.length == 0) {
                return new int[]{-1, -1};
            }

            int start, end, mid;
            int[] bound = new int[2];

            // search for left bound
            start = 0;
            end = A.length - 1;
            while (start + 1 < end) {
                mid = start + (end - start) / 2;
                if (A[mid] == target) {
                    end = mid;
                } else if (A[mid] < target) {
                    start = mid;
                } else {
                    end = mid;
                }
            }
            if (A[start] == target) {
                bound[0] = start;
            } else if (A[end] == target) {
                bound[0] = end;
            } else {
                bound[0] = bound[1] = -1;
                return bound;
            }

            // search for right bound
            start = 0;
            end = A.length - 1;
            while (start + 1 < end) {
                mid = start + (end - start) / 2;
                if (A[mid] == target) {
                    start = mid;
                } else if (A[mid] < target) {
                    start = mid;
                } else {
                    end = mid;
                }
            }
            if (A[end] == target) {
                bound[1] = end;
            } else if (A[start] == target) {
                bound[1] = start;
            } else {
                bound[0] = bound[1] = -1;
                return bound;
            }

            return bound;
        }
    }
}
