package lintcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuank on 9/21/18.
 */
public class LI_138_Subarray_Sum {
    /**
         Given an integer array, find a subarray where the sum of numbers is zero.
         Your code should return the index of the first number and the index of the last number.

         Example
         Given [-3, 1, 2, -3, 4], return [0, 2] or [1, 3].

         Notice
         There is at least one subarray that it's sum equals to zero.

         Easy
     */

    public List<Integer> subarraySum(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0) return res;

        int sum = 0;
        Map<Integer, Integer> map = new HashMap<>();
        /**
         * To deal with the case that the subarray with sum as 0 starts from index 0.
         */
        map.put(0, -1);

        for (int i = 0; i < nums.length; i++){
            sum += nums[i];
            if (map.containsKey(sum)) {
                res.add(map.get(sum) + 1);
                res.add(i);
                return res;
            }
            map.put(sum, i);
        }

        return res;
    }
}
