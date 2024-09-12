package leetcode;

import java.util.HashMap;

/**
 * Created by yuank on 5/4/18.
 */
public class LE_325_Maximum_Size_Subarray_Sum_Equals_k {
    /**
         Given an array nums and a target value k, find the maximum length of a
         subarray that sums to k. If there isn't one, return 0 instead.

         Note:
         The sum of the entire nums array is guaranteed to fit within the
         32-bit signed integer range.

         Example 1:
         Given nums = [1, -1, 5, -2, 3], k = 3,
         return 4. (because the subarray [1, -1, 5, -2] sums to 3 and is the longest)

         Example 2:
         Given nums = [-2, -1, 2, 1], k = 1,
         return 2. (because the subarray [-1, 2] sums to 1 and is the longest)

         Follow Up:
         Can you do it in O(n) time?

         Medium
     */

    public int maxSubArrayLen_clean(int[] nums, int k) {
        if (nums == null || nums.length == 0) return 0;

        int res = 0;
        int sum = 0;
        HashMap<Integer, Integer> map = new HashMap<>(); //map key: prefix sum at idx i, value: index i

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sum == k) {//从nums[0]开始的和是
                res = i + 1;
            } else if(map.containsKey(sum - k)) {
                res = Math.max(res, i - map.get(sum - k));
            }

            if (!map.containsKey(sum)) {
                map.put(sum, i);
            }
        }
        return res;
    }

    /**
     Time and Space : O(n)
     */
    public int maxSubArrayLen(int[] nums, int k) {
        if (nums == null || nums.length == 0) return 0;

        int res = 0;
        int sum = 0;
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sum == k) {//从nums[0]开始的和是
                /**
                 * go from 0 to nums.length, so it keeps overwriting previous
                 * result, and the final value is the longest length.
                 */
                res = i + 1;
            } else if(map.containsKey(sum - k)) {
                /**
                 * sum - s = k => s = sum - k.
                 * index of s = dist.get(sum - k).
                 *
                 * length of subarray:
                 *
                 * i - (index + 1) + 1 = i - index = i - dist.get(sum - k)
                 */
                res = Math.max(res, i - map.get(sum - k));
            }

            /**
             * !!!
             * 保证max length, 后面出现的不覆盖前面出现的,
             * 以保证对同样的sum值，只保留最靠前的index值。
             */
            if (!map.containsKey(sum)) {
                map.put(sum, i);
            }
        }
        return res;
    }
}
