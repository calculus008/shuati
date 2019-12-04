package leetcode;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by yuank on 7/9/18.
 */
public class LE_01_Two_Sum {
    /**
         Given an array of integers, return indices of the two numbers such that they add up to a specific target.

         You may assume that each input would have exactly one solution, and you may not use the same element twice.

         Example:

         Given nums = [2, 7, 11, 15], target = 9,

         Because nums[0] + nums[1] = 2 + 7 = 9,
         return [0, 1].

         Easy
     */

    /**
     *  对每一个数值， 计算期望的另一个值作为map的key, value是当前的下标。
     *  意思就是，对应于该key, 它的partner是在位置为value的数值.
     */
    class Solution {
        public int[] twoSum(int[] nums, int target) {
            if (nums == null) return new int[]{};

            Map<Integer, Integer> map = new HashMap<>();

            for (int i = 0; i < nums.length; i++) {
                if (!map.containsKey(nums[i])) {
                    map.put(target - nums[i], i);
                } else {
                    return new int[]{map.get(nums[i]), i};
                }
            }

            return new int[]{};
        }
    }

    public int[] twoSum(int[] numbers, int target) {
        int[] ans = new int[2];
        int len = numbers.length;
        Hashtable<Integer, Integer> table = new Hashtable<Integer, Integer>();

        for (int i = 0; i < len; ++i) {
            if (table.containsKey(numbers[i])) {
                //The previous number with the lesser index has been stored in Hashtable.
                ans[0] = table.get(numbers[i]);
                ans[1] = i;
                break;
            } else {
                table.put(target - numbers[i], i);
            }
        }
        return ans;
    }
}
