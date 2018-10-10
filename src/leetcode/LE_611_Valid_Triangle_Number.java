package leetcode;

import java.util.Arrays;

/**
 * Created by yuank on 7/9/18.
 */
public class LE_611_Valid_Triangle_Number {
    /**
         Given an array consists of non-negative integers, your task is to count the number of triplets chosen
         from the array that can make triangles if we take them as side lengths of a triangle.

         Example 1:
         Input: [2,2,3,4]
         Output: 3
         Explanation:
         Valid combinations are:
         2,3,4 (using the first 2)
         2,3,4 (using the second 2)
         2,2,3

         Note:
         The length of the given array won't exceed 1000.
         The integers in the given array are in the range of [0, 1000].

         Medium

         LI_609_Two_Sum_Less_Than_Or_Equal_To_Target
     */

    //Time : O(n), Space : O(1)
    public int triangleNumber(int[] nums) {
        /**
         * 1.Must sort
         */
        Arrays.sort(nums);
        int n = nums.length;
        int count = 0;

        /**
         * 2.当前的for循环确定3条边中最长的那条(nums[i])
         */
        for (int i = n - 1; i >= 2; i--) {
            int l = 0;

            /**
             * 3.当最长的边确定后，找其他两条边，
             *   在已经排好序的数组中，这两条边的上边界是nums[i-1]
             */
            int r = i - 1;
            while (l < r) {
                if (nums[l] + nums[r] > nums[i]) {
                    count += r - l;
                    /**
                     * 4.上边界下移，继续找
                     */
                    r--;
                } else {
                    l++;
                }
            }
        }

        return count;
    }

    public int triangleCount_JiuZhang(int S[]) {
        int left = 0, right = S.length - 1;
        int ans = 0;
        Arrays.sort(S);

        for(int i = 0; i < S.length; i++) {
            left = 0;
            right = i - 1;
            while(left < right) {
                if(S[left] + S[right] > S[i]) {
                    ans = ans + (right - left);
                    right --;
                } else {
                    left ++;
                }
            }
        }
        return ans;
    }
}
