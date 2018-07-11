package lintcode;

/**
 * Created by yuank on 7/10/18.
 */
public class LI_604_Window_Sum {
    /**
         Given an array of n integer, and a moving window(size k),
         move the window at each iteration from the start of the array, f
         ind the sum of the element inside the window at each moving.

         Example
         For array [1,2,7,8,5], moving window size k = 3.
         1 + 2 + 7 = 10
         2 + 7 + 8 = 17
         7 + 8 + 5 = 20
         return [10,17,20]

         Easy
     */

    public int[] winSum(int[] nums, int k) {
        if (nums == null || nums.length < k || k <= 0)
            return new int[0];

        int[] res = new int[nums.length - k + 1];

        int sum = 0;
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }
        res[0] = sum;

        for (int i = 1; i < res.length; i++) {
            res[i] = res[i - 1] - nums[i - 1] + nums[i + k - 1];
        }

        return res;
    }
}
