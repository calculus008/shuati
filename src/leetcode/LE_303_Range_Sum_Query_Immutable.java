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

    /**
     *Time : O(1) time per query, O(n)time pre-computation.
     *       Since the cumulative sum is cached, each sumRange query can be calculated in O(1)time.
     *Space : O(n)
     **/
    class NumArray {
        int[] sums;

        public NumArray(int[] nums) {
            sums = new int[nums.length + 1];
            for (int i = 0; i < nums.length; i++) {
                sums[i + 1] = sums[i] + nums[i];
            }
        }

        public int sumRange(int i, int j) {
            int min = Math.min(i, j);
            int max = Math.max(i, j);

            return sums[max + 1] - sums[min];
        }
    }


    class NumArray1 {
        int[] sums;

        //O(n)
        public NumArray1(int[] nums) {
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

    /**
     * we inserted a dummy 0 as the first element in the sum array.
     * This trick saves us from an extra conditional check in sumRange function.
     */
    class NumArray2 {
        private int[] sum;

        public NumArray2(int[] nums) {
            sum = new int[nums.length + 1];
            for (int i = 0; i < nums.length; i++) {
                sum[i + 1] = sum[i] + nums[i];
            }
        }

        public int sumRange(int i, int j) {
            return sum[j + 1] - sum[i];
        }
    }
}
