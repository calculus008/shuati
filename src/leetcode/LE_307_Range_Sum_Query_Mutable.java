package leetcode;

/**
 * Created by yuank on 4/29/18.
 */
public class LE_307_Range_Sum_Query_Mutable {
    /**
         Given an integer array nums, find the sum of the elements between indices i and j (i ≤ j), inclusive.

         The update(i, val) function modifies nums by updating the element at index i to val.
         Example:
         Given nums = [1, 3, 5]

         sumRange(0, 2) -> 9
         update(1, 2)
         sumRange(0, 2) -> 8
         Note:
         The array is only modifiable by the update function.
         You may assume the number of calls to update and sumRange function is distributed evenly.

         Medium
     */

    class NumArray {
        private BinaryIndexedTree biTree;
        private int[] input;

        public NumArray(int[] nums) {
            biTree = new BinaryIndexedTree(nums.length);
            input = nums;

            //!!! initialize biTree
            for (int i = 0; i < nums.length; i++) {
                //!!! “i+ 1" : BinaryIndexedTree里的下标是输入下标加1
                biTree.update(i + 1, nums[i]);
            }
        }

        public void update(int i, int val) {
            //!!! “i+ 1" : BinaryIndexedTree里的下标是输入下标加1
            biTree.update(i + 1, val - input[i]);
            /**
             *!!! MUST update input[i]
             */
            input[i] = val;
        }

        public int sumRange(int i, int j) {
            //!!! “Math.max(i, j) + 1", "Math.min(i, j) + 1 - 1)" : BinaryIndexedTree里的下标是输入下标加1
            return biTree.query(Math.max(i, j) + 1) - biTree.query(Math.min(i, j) + 1 - 1);
        }
    }

    //JiuZhang version
    public class NumArray2 {
        private int[] arr, bit;

        public NumArray2(int[] nums) {
            arr = new int[nums.length];
            bit = new int[nums.length + 1];

            for (int i = 0; i < nums.length; i++) {
                update(i, nums[i]);
            }
        }

        public void update(int index, int val) {
            int delta = val - arr[index];
            arr[index] = val;

            for (int i = index + 1; i <= arr.length; i = i + lowbit(i)) {
                bit[i] += delta;
            }
        }

        public int getPrefixSum(int index) {
            int sum = 0;
            for (int i = index + 1; i > 0; i = i - lowbit(i)) {
                sum += bit[i];
            }
            return sum;
        }

        private int lowbit(int x) {
            return x & (-x);
        }

        public int sumRange(int left, int right) {
            return getPrefixSum(right) - getPrefixSum(left - 1);
        }
    }


}
