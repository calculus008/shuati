package Interviews.DoorDash;

public class Find_Min_Element_Index_In_Rotated_Sorted_Array {
    /**
     * 类似于find target in rotated array
     *
     * 不过是找的rotated的index在哪儿
     *
     * Same logic as :
     * LE_153_Find_Min_In_Rotated_Sorted_Array
     */

    public static int findMin(int[] nums) {
        return findMin(nums, 0, nums.length - 1);
    }

    private static int findMin(int[] nums, int left, int right) {
        /**
         * 1 or 2 elements left.
         */
        if (left + 1 >= right) {
            if (nums[left] < nums[right]) {
                return left;
            } else {
                return right;
            }
        }

        /**
         * check if it is sorted, we can do it because there's no duplicate
         */
        if (nums[left] < nums[right]) {
            return left;
        }

        int mid = left + (right - left) / 2;

        int lResult = findMin(nums, left, mid - 1);
        int rResult = findMin(nums, mid, right);

        if (nums[lResult] < nums[rResult]) {
            return lResult;
        } else {
            return rResult;
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{4, 5, 6, 7, 0, 1, 2};

        System.out.println("min index = " + findMin(nums));
    }

}
