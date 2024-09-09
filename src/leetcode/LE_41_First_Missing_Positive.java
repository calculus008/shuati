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

        https://leetcode.com/problems/first-missing-positive
     */

    class Solution_cycle_sort_clean {
        public int firstMissingPositive(int[] nums) {
            int n = nums.length;
            int i = 0;
            while (i < n) {
                int idx = nums[i] - 1;
                if (nums[i] > 0 && nums[i] <= n && nums[i] != nums[idx]) { //!!! the most tricky part, i does not move after swap
                    swap(nums, i, idx);
                } else { //!!!
                    i++;
                }
            }

            for (int j = 0; j < n; j++) { // i is defined, use a different variable name
                if (nums[j] != j + 1) return j + 1;
            }

            return n + 1;
        }

        public void swap(int[] nums, int i1, int i2) {
            int temp = nums[i1];
            nums[i1] = nums[i2];
            nums[i2] = temp;
        }
    }

    class Solution_circle_sort {
        public int firstMissingPositive(int[] nums) {
            int n = nums.length;

            // Use cycle sort to place positive elements smaller than n
            // at the correct index
            int i = 0;
            while (i < n) {
                int correctIdx = nums[i] - 1;
                if (nums[i] > 0 && nums[i] <= n && nums[i] != nums[correctIdx]) {
                    swap(nums, i, correctIdx);
                } else {
                    i++;
                }
            }

            // Iterate through nums
            // return smallest missing positive integer
            for (i = 0; i < n; i++) {
                if (nums[i] != i + 1) {
                    return i + 1;
                }
            }

            // If all elements are at the correct index
            // the smallest missing positive number is n + 1
            return n + 1;
        }

        // Swaps two elements in nums
        private void swap(int[] nums, int index1, int index2) {
            int temp = nums[index1];
            nums[index1] = nums[index2];
            nums[index2] = temp;
        }
    }

    class Solution_idx_as_hash_key_clean {
        class Solution {
            public int firstMissingPositive(int[] nums) {
                int n = nums.length;
                boolean hasOne = false;

                for (int i = 0; i < n; i++) {
                    if (nums[i] == 1) hasOne = true;
                    if (nums[i] <= 0 || nums[i] > n)  nums[i] = 1;
                }

                if (!hasOne) return 1;

                for (int i = 0; i < n; i++) {
                    int val = Math.abs(nums[i]);
                    if (val == n) {
                        nums[0] = -Math.abs(nums[0]);
                    } else {
                        nums[val] = -Math.abs(nums[val]);
                    }
                }

                for (int i = 1; i < n; i++) {
                    if (nums[i] > 0) return i;
                }

                if (nums[0] > 0) return n;

                return n + 1;
            }
        }
    }

    class Solution_idx_as_hash_key {
        /**
         * Time : O(n)
         * Space : O(1)
         *
         * Key is using index ad hash key and sign as presence indicator
         */
        class Solution {

            public int firstMissingPositive(int[] nums) {
                int n = nums.length;
                boolean contains1 = false;

                // Replace negative numbers, zeros,
                // and numbers larger than n with 1s.
                // After this nums contains only positive numbers.
                for (int i = 0; i < n; i++) {
                    // Check whether 1 is in the original array
                    if (nums[i] == 1) {
                        contains1 = true;
                    }
                    if (nums[i] <= 0 || nums[i] > n) {
                        nums[i] = 1;
                    }
                }

                if (!contains1) return 1;

                // Mark whether integers 1 to n are in nums
                // Use index as a hash key and negative sign as a presence detector.
                for (int i = 0; i < n; i++) {
                    int value = Math.abs(nums[i]); //!!! abs
                    if (value == n) { // The largest index is n - 1, so for value n, we put it at index 0
                        nums[0] = -Math.abs(nums[0]); //!!! abs
                    } else {
                        nums[value] = -Math.abs(nums[value]);//!!! abs
                    }
                }

                // First positive in nums is the smallest missing positive integer
                for (int i = 1; i < n; i++) {
                    if (nums[i] > 0) return i; //!!! ">"
                }

                // nums[0] stores whether n is in nums
                if (nums[0] > 0) { //!!! ">"
                    return n;
                }

                // If nums contains all elements 1 to n
                // the smallest missing positive number is n + 1
                return n + 1;
            }
        }
    }

    class Solution_boolean_array{
        /**
         * Time and Space O(n)
         * Not meeting requirement using constant space, just here for reference.
         */
        class Solution {
            public int firstMissingPositive(int[] nums) {
                int n = nums.length;
                boolean[] seen = new boolean[n + 1]; // Array for lookup

                // Mark the elements from nums in the lookup array
                for (int num : nums) {
                    if (num > 0 && num <= n) {
                        seen[num] = true;
                    }
                }

                // Iterate through integers 1 to n
                // return smallest missing positive integer
                for (int i = 1; i <= n; i++) {
                    if (!seen[i]) {
                        return i;
                    }
                }

                // If seen contains all elements 1 to n
                // the smallest missing positive number is n + 1
                return n + 1;
            }
        }
    }

    /**
     * Variations
     *
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
     * *******************************************
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
            /**
             * !!!
             * "nums[i] <= nums.length" : 此处， nums[i]是作为下标值的。
             */
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
