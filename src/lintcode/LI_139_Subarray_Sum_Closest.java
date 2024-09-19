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

            /**
             * map.higtherKey and map.lowerKey
             *
             * For current prefix sum - "sum", we look back, get the
             * closet higher and lower value, 打擂台， find answer.
             */
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


    /**
     * Solution 1
     * 不使用 pair，不使用 TreeMap，只使用 HashMap + Array + Sort 的方法.
     * 用 HashMap 记录之前的位置，用 Array 来打擂台找最小差距
     * 时间复杂度为O(nlogn), 空间复杂度为O(n)
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

        //Same code as in LI_138_Subarray_Sum
        Map<Integer, Integer> map = new HashMap<>();
        int[] prefixSum = new int[nums.length + 1];
        int sum = 0;

        /**
         * 必须加这个entry以处理开始位置在index=0
         */
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

        //!!!
        Arrays.sort(prefixSum);

        //打擂台找最小
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
            results[0] = map.get(left) + 1;//!!! " + 1"
            results[1] = map.get(right);
        } else {
            results[0] = map.get(right) + 1;//!!! " + 1"
            results[1] = map.get(left);
        }
        return results;
    }


    /**
        问：为什么需要一个 (0,0) 的初始 Pair?

        答：
        我们首先需要回顾一下，在 subarray 这节课里，我们讲过一个重要的知识点，叫做 Prefix Sum
        比如对于数组 [1,2,3,4]，他的 Prefix Sum 是 [1,3,6,10]
        分别表示 前1个数之和，前2个数之和，前3个数之和，前4个数之和
        这个时候如果你想要知道 子数组 从下标  1 到下标 2 的这一段的和(2+3)，就用前 3个数之和 减去 前1个数之和
        = PrefixSum[2] - PrefixSum[0] = 6 - 1 = 5

        你可以看到这里的 前 x 个数，和具体对应的下标之间，存在 +-1 的问题
        第 x 个数的下标是 x - 1，反之 下标 x 是第 x + 1 个数

        那么问题来了，如果要计算 下标从 0~2 这一段呢？也就是第1个数到第3个数，因为那样会访问到 PrefixSum[-1]
        所以我们把 PrefixSum 整体往后面移动一位，把第0位空出来表示前0个数之和，也就是0. => [0,1,3,6,10]

        那么此时就用 PrefixSum[3] - PrefixSum[0] ，这样计算就更方便了。
        此时，PrefixSum[i] 代表 前i个数之和，也就是 下标区间在 0 ~ i-1 这一段的和

        那么回过头来看看，为什么我们需要一个 (0,0) 的 pair 呢？
        因为 这个 0,0 代表的就是前0个数之和为0
        一个 n 个数的数组， 变成了 prefix Sum 数组之后，会多一个数出来
    **/
}
