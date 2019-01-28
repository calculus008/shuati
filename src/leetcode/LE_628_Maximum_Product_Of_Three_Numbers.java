package leetcode;

import java.util.Arrays;

public class LE_628_Maximum_Product_Of_Three_Numbers {
    /**
     * Given an integer array, find three numbers whose product is maximum and output the maximum product.
     *
     * Example 1:
     * Input: [1,2,3]
     * Output: 6
     * Example 2:
     * Input: [1,2,3,4]
     * Output: 24
     * Note:
     * The length of the given array will be in range [3,104] and all elements are in the range [-1000, 1000].
     * Multiplication of any three numbers in the input won't exceed the range of 32-bit signed integer.
     *
     * Easy
     *
     */

    /**
     * https://zxi.mytechroad.com/blog/math/leetcode-628-maximum-product-of-three-numbers/
     */

    /**
     * Time  : O(nlogn)
     * Space : O(1)
     * 37 ms
     */
    public class Solution1 {
        public int maximumProduct(int[] nums) {
            Arrays.sort(nums);
            int n = nums.length;

            int a = nums[n - 1] * nums[n - 2] * nums[n - 3];
            int b = nums[0] * nums[1] * nums[n - 1];

            return a > b ? a : b;
        }
    }


    /**
     * Time  : O(n)
     * Space : O(1);
     * 6 ms
     */
    class Solution2 {
        public int maximumProduct(int[] nums) {
            int max1 = Integer.MIN_VALUE;
            int max2 = Integer.MIN_VALUE;
            int max3 = Integer.MIN_VALUE;

            int min1 = Integer.MAX_VALUE;
            int min2 = Integer.MAX_VALUE;

            for (int num : nums) {
                if (num > max1) {
                    max3 = max2;
                    max2 = max1;
                    max1 = num;
                } else if (num > max2) {
                    max3 = max2;
                    max2 = num;
                } else if (num > max3) {
                    max3 = num;
                }

                if (num < min1) {
                    min2 = min1;
                    min1 = num;
                } else if (num < min2) {
                    min2 = num;
                }
            }

            return Math.max(max1 * max2 * max3, min1 * min2 * max1);
        }
    }

}