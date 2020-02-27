package Interviews.Indeed;

public class Find_Median_In_Unsorted_Array {
    public static double findMedian(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;

        if (n % 2 == 0) {
            return 0.5 * (findKthSmallest(nums, 0, n - 1, n / 2) +
                    findKthSmallest(nums, 0, n - 1, n / 2 - 1));
        }

        return (double)findKthSmallest(nums, 0, n - 1, n / 2);
    }


    public static int findKthSmallest(int[] nums, int start, int end, int k) {
        if (start >= end) {
            return nums[start];
        }

        int l = start;
        int r = end;
        int pivot = nums[(start + end) / 2];

        while (l <= r) {
            while (l <= r && nums[l] < pivot) {
                l++;
            }
            while (l <= r && nums[r] > pivot) {
                r--;
            }

            if (l <= r) {
                int temp = nums[l];
                nums[l] = nums[r];
                nums[r] = temp;

                l++;
                r--;
            }
        }

        if (r >= k && r >= start) {
            return findKthSmallest(nums, start, r, k);
        } else if (l <= k && l <= end) {
            return findKthSmallest(nums, l, end, k);
        } else {
            return nums[k];
        }
    }

    public static void main(String[] args) {
        int[] nums1 = {4, 2, 5, 3, 6, 7, 1};
        int[] nums2 = {4, 2, 5, 3, 6, 7, 1, 8};

        int[] nums3 = {0, 0, 1, 100, 500, 600, 800};
        int[] nums4 = {0, 0, 1, 100, 500, 600, 800, 1000};


        System.out.println(findMedian(nums1));
        System.out.println(findMedian(nums2));
        System.out.println(findMedian(nums3));
        System.out.println(findMedian(nums4));


    }
}
