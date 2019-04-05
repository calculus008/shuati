package leetcode;

import java.util.Arrays;

/**
 * Created by yuank on 2/27/18.
 */
public class LE_41_First_Missing_Positive {
    /**
        Given an unsorted integer array, find the first missing POSITIVE integer.

        For example,
        Given [1,2,0] return 3,
        and [3,4,-1,1] return 2.

        Your algorithm should run in O(n) time and uses constant space.

        Hard
     */

    /**
     * 然后开始做题。第一题三哥出的，给了一堆server，然后让找出first available server，
     * 我想这不是first missing positive吗，leetcode原题，但L家面试题里从来没见过，
     * 但方法是记得的，但开始还是假装说最intuitive的办法就说sort，但肯定有更好的办法，
     * 容我想想！三哥很急的说，ok ok let me give you a hint,我当时就急了心中大喊我
     * 不要hint!我这演技已经是影后的水平了吗！大概三哥听到我心里的呐喊，就说我再等你想想。
     * 我哪还敢等，马上开始写。写完过了test case，这时已经33分钟了。.
     *
     * 给出一个大小为n的无序数组。这些元素与1～n的全排列相比缺少了一个数字，并有一个数字
     * 出现了两次。找出这两个数。
     */

    /**
     * Brutal Force is O(nlogn) (do sorting first)
     *
     * Bucket sorting solution : value in nums[i] should be i+1
     *
     * Time and Space : O(n)
     *
     * Example 1:
     * [3,1,4,-1]
     *
     * i=0, nums :[3, 1, 4, -1]
     * After swap:[4, 1, 3, -1]
     * After swap:[-1, 1, 3, 4]
     *
     * i=1, nums :[-1, 1, 3, 4]
     * After swap:[1, -1, 3, 4]
     *
     * i=2, nums :[1, -1, 3, 4]
     * i=3, nums :[1, -1, 3, 4]
     *
     * Example 2 : upper boundary with large number
     * [2,1,100,-1]
     *
     * i=0, nums :[2, 1, 100, -1]
     * After swap:[1, 2, 100, -1]
     *
     * i=1, nums :[1, 2, 100, -1]
     *
     * i=2, nums :[1, 2, 100, -1] : here arr[2]= 100, which is larger than array size, do nothing
     *
     * i=3, nums :[1, 2, 100, -1] : here arr[3]= -1, which is smaller than 1, do nothing
     *
     * Example 3 : upper boundary
     * [2,1,3,-1]
     *
     * i=0, nums :[2, 1, 3, -1]
     * After swap:[1, 2, 3, -1]
     *
     * i=1, nums :[1, 2, 3, -1]
     *
     * i=2, nums :[1, 2, 3, -1]
     *
     * i=3, nums :[1, 2, 3, -1]
     *
     * Example 4 : lower boundary
     * [2,6,3,-1]
     *
     * i=0, nums :[2, 6, 3, -1]
     * After swap:[6, 2, 3, -1]  : here arr[0]= 6, which is larger than array size, do nothing
     *
     * i=1, nums :[6, 2, 3, -1]  : here arr[1]= 2, which is at the right slot, do nothing
     * i=2, nums :[6, 2, 3, -1]
     * i=3, nums :[6, 2, 3, -1]
     *
     **/
    public static int firstMissingPositive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 1;
        }

        int n = nums.length;

        for (int i = 0; i < n; i++) {
            System.out.println("i="+i+", nums :" + Arrays.toString(nums));

            /**
             * nums[i] > 0 : we only care about positive number
             * nums[i] <= nums.length : number should be within index boundary
             *
             * nums[i] - 1 : index that value in nums[i] supposed to be
             * nums[nums[i] - 1] == nums[i] : meaning it is in the right place
             *
             */
            while (nums[i] > 0
                    && nums[i] <= nums.length
                    && nums[i] != nums[nums[i] - 1]) {
                swap(nums, i, nums[i] - 1);
                System.out.println("After swap:" + Arrays.toString(nums));
            }
        }

        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }

        /**
         * !!!
         * This is for case like example 3 above, when we execute to this point
         * and not return result yet, the firest missing number must be n + 1
         */
        return n + 1;
    }

    private static void swap(int[] nums, int i, int j) {
        nums[i] ^= nums[j];
        nums[j] ^= nums[i];
        nums[i] ^= nums[j];
    }

    public static int firstMissingPositive_Practice(int[] nums) {
        if (null == nums || nums.length == 0) return 1;

        for (int i = 0; i < nums.length; i++) {
            while (nums[i] > 0 && nums[i] <= nums.length && nums[nums[i] - 1] != nums[i]) {
                int temp = nums[i];
                nums[i] = nums[nums[i] - 1];
                nums[temp - 1] = temp;
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }

        return nums.length + 1;
    }

    public static void main(String [] args) {
//        int[] arr = {3,1,4,-1};
        int[] arr = {2,6,3,-1};

        firstMissingPositive(arr);
    }
}
