package lintcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by yuank on 10/1/18.
 */
public class LI_139_Subarray_Sum_Closest {
    /**
         Description
         Given an integer array, find a subarray with sum closest to zero.
         Return the indexes of the first number and last number.

         Example
         Given [-3, 1, 1, -3, 5], return [0, 2], [1, 3], [1, 1], [2, 2] or [0, 4].

         Challenge
         O(nlogn) time

         Medium
     */

    /**
     * Solution 1
     * 不使用 pair，不使用 TreeMap，只使用 HashMap + Array + Sort 的方法.
     * 用 HashMap 记录之前的位置，用 Array 来打擂台找最小差距时间复杂度为O(nlogn), 空间复杂度为O(n)
     */

    public int[] subarraySumClosest1(int[] nums) {
        // write your code here
        int[] results = new int[2];

        // edge case
        if (nums == null || nums.length == 0) {
            return new int[]{};
        }
        if (nums.length == 1) {
            return new int[]{0,0};
        }

        // general
        Map<Integer, Integer> map = new HashMap<>();
        int[] prefixSum = new int[nums.length + 1];
        int sum = 0;
        map.put(0, -1);
        prefixSum[0] = 0;

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (map.containsKey(sum)) {
                results[0] = map.get(sum) + 1;
                results[1] = i;
                return results;
            }
            map.put(sum, i);
            prefixSum[i + 1] = sum;
        }

        Arrays.sort(prefixSum);

        int minDiff = Integer.MAX_VALUE;
        int left = 0, right = 0;

        for (int i = 0; i < prefixSum.length - 1; i++) {
            if (minDiff > Math.abs(prefixSum[i] - prefixSum[i + 1])) {
                minDiff = Math.abs(prefixSum[i] - prefixSum[i + 1]);
                left = prefixSum[i];
                right = prefixSum[i + 1];
            }
        }
        if (map.get(left) < map.get(right)) {
            results[0] = map.get(left) + 1;
            results[1] = map.get(right);
        } else {
            results[0] = map.get(right) + 1;
            results[1] = map.get(left);
        }
        return results;
    }

    /**
     * Solution 2
     * 利用TreeMap 进行查询，如果之前出现过，那么必定为0。否则，找最近的一个数，
     * 大的或小的，然后求他们他们之间的差值，即为最小的subarray的sum值
     *
     * TreeMap provides guaranteed log(n) time cost for the containsKey,
     * get, put and remove operations
     *
     * Time : O(nlogn)
     */
    public int[] subarraySumClosest(int[] nums) {
        int[] res = new int[2];
        if (nums.length == 0) return res;

        int closest = Integer.MAX_VALUE, sum = 0;
        TreeMap<Integer, Integer> map = new TreeMap<>();

        //!!!
        map.put(0, -1);

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (map.containsKey(sum)) {
                res[0] = map.get(sum) + 1;
                res[1] = i;
                return res;
            }

            Integer greater = map.higherKey(sum);
            if (greater != null && Math.abs(sum - greater) < closest) {
                closest = Math.abs(sum - greater);
                res[0] = map.get(greater) + 1;
                res[1] = i;
            }
            Integer lower = map.lowerKey(sum);
            if (lower != null && Math.abs(sum - lower) < closest) {
                closest = Math.abs(sum - lower);
                res[0] = map.get(lower) + 1;
                res[1] = i;
            }
            map.put(sum, i);
        }
        return res;
    }
}
