package leetcode;

/**
 * Created by yuank on 3/6/18.
 */
public class LE_80_Remove_Dup_From_Sorted_Array_II {
    /**
        Follow up for "Remove Duplicates":
        What if duplicates are allowed at most twice?

        For example,
        Given sorted array nums = [1,1,1,2,2,3],

        Your function should return length = 5, with the first five elements of nums being 1, 1, 2, 2 and 3.
        It doesn't matter what you leave beyond the new length.
     */

    //A follow up question of LE_26

    class Solution_Two_Dup {
        public  int removeDuplicates(int[] nums) {
            if (nums == null || nums.length == 0) return 0;

            int count = 2;

            /**
             when nums[i] == nums[count - 2], count stays the same, i will increase 1 in for loop.
             It means, i always keeps moving to the element. Only when i gets to the element that
             is not the same value as nums[count - 2], we put that value into nums[count] to replace its original value, then
             count move forward by 1. In other words, count stays at the location where the replacement
             action should happen.
             **/
            for (int i = 2; i < nums.length; i++) {
                /**
                 * "nums[i] != nums[count - 2]", it is nums[count - 2], NOT nums[i - 2] !!!!!!
                 */
                if (nums[i] != nums[count - 2]) {
                    nums[count++] = nums[i];
                }
            }

            /**
             * Even though count points to the next location, but since it is index (0 based),
             * so it happens to be the right value to be returned at the end.
             *
             * For example : [1,1,1,2,2,3], when exit for loop, count value is 5, it is the correct count of total elements.
             */
            return count;
        }
    }

    class Solution_K_Dup {
        /**
         * Remove duplicates from sorted array,
         * at most k duplicates allowed
         * (for the same number, at most duplicates allowed)
         *
         * The idea is :
         * 1.Each k elements (from start) can be seen as a group
         * 2.In this k elements group, duplicate are allowed, so
         *   don't care.
         * 3.Both runner i and "count" starts at index K, element at index count
         *   should not be the same as element at index (count - k).
         * 4.Therefore, runner i will be responsible to find the next element
         *   that is different from element at count - k (nums[count - k].
         *   Once i finds it, copy it to count - k, then count move forward one step
         *
         */
        public int removeDuplicates(int[] nums, int k) {
            if (null == nums || nums.length == 0) {
                return 0;
            }

            if (k >= nums.length) {
                return nums.length;
            }

            int count = k;
            for (int i = k; i < nums.length; i++) {
                if (nums[i] != nums[count - k]) {
                    nums[count++] = nums[i];
                }
            }

            return count;
        }
    }

    /**
     k = 3
     *
     0 1 2 3 4 5 6 7 8
     ------------------
     1 1 2 2 3 4 4 5 5

     i         *
         1 1 1 1 1 2 2 3 4
     count     *

     i             *
         1 1 1 1 1 2 2 3 4
     count     *

     i             *
         1 1 1 2 1 2 2 3 4
     count       *

     i               *
         1 1 1 2 2 2 2 3 4
     count         *

     i               *
         1 1 1 2 2 2 2 3 4
     count         *

     i               *
         1 1 1 2 2 2 2 3 4
     count           *

     i                 *
         1 1 1 2 2 2 2 3 4
     count           *

     i                 *
         1 1 1 2 2 2 3 3 4
     count             *

     i                   *
         1 1 1 2 2 2 3 3 4
     count             *

     i                   *
         1 1 1 2 2 2 3 4 4
     count               *
     **/
}
