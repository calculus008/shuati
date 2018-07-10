package leetcode;

import java.util.Hashtable;

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
