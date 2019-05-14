package leetcode;

public class LE_665_Non_Decreasing_Array {
    /**
     * Given an array with n integers, your task is to check if it could
     * become non-decreasing by modifying at most 1 element.
     *
     * We define an array is non-decreasing if array[i] <= array[i + 1]
     * holds for every i (1 <= i < n).
     *
     * Example 1:
     * Input: [4,2,3]
     * Output: True
     * Explanation: You could modify the first 4 to 1 to get a non-decreasing array.
     *
     * Example 2:
     * Input: [4,2,1]
     * Output: False
     * Explanation: You can't get a non-decreasing array by modify at most one element.
     *
     * Note: The n belongs to [1, 10,000].
     *
     * Easy
     */

    /**
     * This problem is like a greedy problem. When you find nums[i-1] > nums[i] for some i,
     * you will prefer to change nums[i-1]'s value, since a larger nums[i] will give you more
     * risks that you get inversion errors after position i. But, if you also find nums[i-2] > nums[i],
     * then you have to change nums[i]'s value instead, or else you need to change both of nums[i-2]'s
     * and nums[i-1]'s values.
     *
     * Time : O(n)
     */
    class Solution1 {
        public boolean checkPossibility1(int[] nums) {
            int cnt = 0; //the number of changes

            for (int i = 1; i < nums.length && cnt <= 1; i++) {
                if (nums[i - 1] > nums[i]) {
                    cnt++;

                    if (i - 2 < 0 || nums[i - 2] <= nums[i]) {//modify nums[i-1] of a priority
                        nums[i - 1] = nums[i];
                    } else { //have to modify nums[i]
                        nums[i] = nums[i - 1];
                    }
                }
            }

            return cnt <= 1;
        }

        public boolean checkPossibility2(int[] nums) {
            boolean modified = false;

            for (int i = 1; i < nums.length; i++) {
                if (nums[i] < nums[i - 1]) {
                    if (modified) {
                        return false;
                    } else {
                        modified = true;
                        if (i - 2 >= 0 && nums[i] < nums[i - 2]) {
                            nums[i] = nums[i - 1];
                        }
                    }
                }
            }

            return true;
        }
    }

    /**
     * 提供一种略有不同的方法。
     * 顺序检查凹变段和逆序检查凸变段。
     * 如果满足，则asc和desc中的较小值必然不大于1。
     * 时间开销O(n)，空间开销O(1)，缺点是双向检查，优点是便于理解
     */
    class Solution2 {
        public boolean checkPossibility(int[] nums) {
            for(int i = 0, m = 0, n = nums.length - 1, asc = 0, desc = 0;i < nums.length;i++) {
                if(nums[m] <= nums[i]) {
                    m = i;
                } else {
                    asc++;
                }

                if(nums[n] >= nums[nums.length - 1 - i]) {
                    n = nums.length - 1 - i;
                } else {
                    desc++;
                }

                if(asc > 1 && desc > 1) {
                    return false;
                }
            }

            return true;
        }
    }
}
