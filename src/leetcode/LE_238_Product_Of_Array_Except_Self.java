package leetcode;

/**
 * Created by yuank on 4/3/18.
 */
public class LE_238_Product_Of_Array_Except_Self {
    /**
        Given an array of n integers where n > 1, nums, return an array output
        such that output[i] is equal to the product of all the elements of num
        except nums[i].

        Solve it without division and in O(n).

        For example, given [1,2,3,4], return [24,12,8,6].

        Follow up:
        Could you solve it with constant space complexity?
        (Note: The output array does not count as extra space for the purpose of space complexity analysis.)

        Medium

        https://leetcode.com/problems/product-of-array-except-self
     */

    /**
      nums[1,2,3,4]

      #1.First iteration:
      res [1,1,2,6]
      ----->

      #2.Second iteration:
             <--------
      nums[1,  2,  3, 4]
      res [1,  1,  2, 6]
    right 24, 24, 12, 6, 1

      res 24, 12,  8, 6

     Time and Space : O(n), if output space is not counted, it is space O(1)
     */

    /**
     * https://leetcode.com/problems/product-of-array-except-self/solution/
     *
     *
     * Given numbers [2, 3, 4, 5], regarding the third number 4, the product of array except 4 is 2*3*5
     * which consists of two parts: left 2*3 and right 5. The product is left*right.
     *
     * We can get lefts and rights:
     *
     * Numbers:     2    3    4     5
     * Lefts:            2  2*3 2*3*4
     * Rights:  3*4*5  4*5    5
     *
     * Let’s fill the empty with 1:
     *
     * Numbers:     2    3    4     5
     * Lefts:       1    2  2*3 2*3*4
     * Rights:  3*4*5  4*5    5     1
     *
     * We can calculate lefts and rights in 2 loops. The time complexity is O(n).
     */

    /**
     * Improved from Solution2
     * Use 1 extra array, 2 passes.
     *
     * Since the result space will not be counted, this solution is space O(1)
     */
    class Solution1 {
        public int[] productExceptSelf(int[] nums) {
            if (nums == null || nums.length == 0) return nums;

            int[] res = new int[nums.length];
            res[0] = 1;

            for (int i = 1; i < nums.length; i++) {
                res[i] = res[i - 1] * nums[i - 1];//!!! i - 1
            }

            int right = 1; //!!!
            for (int i = nums.length - 1; i >= 0; i--) {
                res[i] *= right;
                right *= nums[i];
            }

            return res;
        }
    }

    /**
     * use 2 extra arrays, 3 passes
     */
    class Solution2 {
        public int[] productExceptSelf(int[] nums) {
            int length = nums.length;

            int[] L = new int[length];
            int[] R = new int[length];

            // Final answer array to be returned
            int[] answer = new int[length];

            L[0] = 1;
            for (int i = 1; i < length; i++) {
                L[i] = nums[i - 1] * L[i - 1];
            }

            R[length - 1] = 1;
            for (int i = length - 2; i >= 0; i--) {
                R[i] = nums[i + 1] * R[i + 1];
            }

            for (int i = 0; i < length; i++) {
                answer[i] = L[i] * R[i];
            }

            return answer;
        }
    }
}
