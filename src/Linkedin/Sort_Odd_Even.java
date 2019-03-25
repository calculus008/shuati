package Linkedin;

import java.util.Arrays;

public class Sort_Odd_Even {
    /**
     * 一个未排序整数数组，有正负数，重新排列使负数排在正数前面，并且要求不改变原来的正负数之间相对顺序，
     * 比如： input: 1,7,-5,9,-12,15 ans: -5,-12,1,7,9,15
     *
     * 要求时间复杂度O(n),空间O(1)。
     *
     * Vairation of LE_905_Sort_Array_By_Parity
     */

    /**
     *    scan array, do nothing if the number is positive, until we see a negative number :
     *    1, 7, -5, 9, -12, 15
     *  left    i
     *
     *    swap numbers at left and i :
     *    -5, 7, 1, 9, -12, 15
     *  left     i
     *
     *    left advances by 1:
     *    -5, 7, 1, 9, -12, 15
     *      left i
     *
     *    -5, 7, 1, 9, -12, 15
     *      left i
     *
     *    -5, 1, 7, 9, -12, 15
     *      left i
     *
     *    -5, 1, 7, 9, -12, 15
     *      left        i
     *
     *    -5, -12, 7, 9, 1, 15
     *           left    i
     *
     *    -5, -12, 9, 7, 1, 15
     *           left    i
     *
     *    -5, -12, 1, 7, 9, 15
     *           left    i
     *
     *
     */
    public static void oddEven(int[] nums) {
        int left = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < 0) {
                if (left == i) {
                    /**
                     * left should try to point to positive number,
                     * here it points negative, so we need to move it
                     * forward.
                     */
                    left++;
                } else {
                    int temp = nums[left];
                    nums[left] = nums[i];
                    nums[i] = temp;

                    left++;

                    /**
                     *  两个swap 吧交换后的left ～ i的正数部分恢复原来的顺序关系
                     *
                     *   1 2 3 4 5 -1
                     *  -1 2 3 4 5  1
                     *     ———————
                     *  -1 5 4 3 2  1
                     *     ——————————
                     *
                     *  -1 1 2 3 4  5
                     */
                    swap(nums, left, i - 1);
                    swap(nums, left, i);
                }

            }
        }
    }

    public static void swap (int[] nums, int left, int right) {
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }

    public static void main (String[] args) {
//        int[] numbers = new int[]{5,6,7,-1,-2,-3};
        int[] numbers = new int[]{2, 2, 3, -10, -12, 3, -9, 5, 6, 7, -1, -2, 5, -3};

        oddEven(numbers);

//        sortArrayByParity(numbers);

        System.out.println(Arrays.toString(numbers));
        //[-1, -2, -3, 5, 6, 7]
    }
}
