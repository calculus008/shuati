package leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuank on 11/28/18.
 */
public class LE_525_Contiguous_Array {
    /**
         Given a binary array, find the maximum length of a contiguous subarray with equal number of 0 and 1.

         Example 1:
         Input: [0,1]
         Output: 2
         Explanation: [0, 1] is the longest contiguous subarray with equal number of 0 and 1.

         Example 2:
         Input: [0,1,0]
         Output: 2
         Explanation: [0, 1] (or [1, 0]) is a longest contiguous subarray with equal number of 0 and 1.

         Note: The length of the given binary array will not exceed 50,000.

         Medium
     */

    /**
     * http://zxi.mytechroad.com/blog/hashtable/leetcode-525-contiguous-array/
     *
     * 思路
     * 1.Brutal Force
     * Try each substring (O(n ^ 2)), check number of 0 and 1, (O(n)). Time : O(n ^ 3).
     * Can be reduced to O(n ^ 2) by using prefix sum, sum between i ~ j X = sum[i] - sum[j - 1],
     * see if X = (i - j + 1) / 2
     *
     * 2.Prefix Sum + HashMap
     * Time  : O(n)
     * Space : O(n)
     *
     * 很显然，要用prefix sum.LI_138_Subarray_Sum, sum of subarray eqauls to 0, 方法：sum[i] - sum[j - 1] == 0, then i to j 的sum为0。
     * 所以，用-1取代0，计算prefix sum. 这样，对于0和1数目相等的区域，prefix sum为0, 转化为LI_138_Subarray_Sum。
     *
     * 用HashMap记录prefix sum的值仅当该值第一次出现(保证所求区域的起始点尽量靠前，这样区域长度更大), 当该值再次出现时，找到了合法区域的终点，
     * 计算长度并求最大。
     *
     * 特殊情况，prefix sum为0，表明合法区域从index 0 开始，长度为i + 1.
     */

    /**
     * HashMap version
     * 41 ms
     */
    public int findMaxLength1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int n = nums.length;
        Map<Integer, Integer> map = new HashMap<>();

        int res = 0;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += (nums[i] == 0 ? -1 : 1);
            if (sum == 0) {
                //can also be : res = i + 1, since we are sure this is the earliest possible start point, must be the max length
                res = Math.max(i + 1, res);
                continue;
            }

            if(map.containsKey(sum)) {
                res = Math.max(i - map.get(sum), res);
            } else {
                map.put(sum, i);
            }
        }

        return res;
    }

    /**
     * Array version
     * 23 ms
     */
    public int findMaxLength2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int n = nums.length;
        int[] map = new int[2 * n + 1];
        Arrays.fill(map, -1);

        int res = 0;
        int sum = 0;

        for (int i = 0; i < n; i++) {
            sum += (nums[i] == 0 ? -1 : 1);
            if (sum == 0) {
                res = Math.max(i + 1, res);
                continue;
            }

            if(map[n + sum] >= 0) {
                /**
                 * n is offset
                 */
                res = Math.max(i - map[n + sum], res);
            } else {
                map[n + sum] = i;
            }
        }

        return res;
    }
}
