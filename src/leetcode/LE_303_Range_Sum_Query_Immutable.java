package leetcode;

/**
 * Created by yuank on 4/27/18.
 */
public class LE_303_Range_Sum_Query_Immutable {
    /**
         Given an integer array nums, find the sum of the elements between indices i and j (i ≤ j), inclusive.

         Example:
         Given nums = [-2, 0, 3, -5, 2, -1]

         sumRange(0, 2) -> 1
         sumRange(2, 5) -> -1
         sumRange(0, 5) -> -3
         Note:
         You may assume that the array does not change.
         There are many calls to sumRange function.

         Easy
     */

    //Time and Space : O(n);
    class NumArray {
        int[] sums;

        //O(n)
        public NumArray(int[] nums) {
            sums = new int[nums.length];
            int sum = 0;
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
                sums[i] = sum;
            }
        }

        //O(1)
        public int sumRange(int i, int j) {
            if (i < 0 || j > sums.length) return 0;

            return i == 0 ? sums[j] : sums[j] - sums[i - 1];
        }
    }

    class NumArray1 {
        private int[] sums;

        public NumArray1(int[] nums) {
            for(int i=1; i<nums.length; i++) {
                nums[i] = nums[i-1] + nums[i];
            }

            sums = nums;
        }

        public int sumRange(int i, int j) {
            if(i==0) return sums[j];
            return sums[j] - sums[i-1];
        }
    }
}
