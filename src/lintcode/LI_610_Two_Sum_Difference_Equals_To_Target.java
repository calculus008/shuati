package lintcode;

import java.util.HashMap;

/**
 * Created by yuank on 7/11/18.
 */
public class LI_610_Two_Sum_Difference_Equals_To_Target {
    /**
         Given an array of integers, find two numbers that their difference equals to a target value.
         where index1 must be less than index2. Please note that your returned answers
         (both index1 and index2) are NOT zero-based.

         Medium
     */

    //HashMap solution, Time and Space : O(n)
    public int[] twoSum7(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            //case 1 : current num - one appeared previously = target
            int k = nums[i] - target;
            if (map.containsKey(k)) {
                int[] res1 = new int[2];
                res1[0] = map.get(k) + 1;
                res1[1] = i + 1;
                return res1;
            }
            //case 2 : one appeared previously - num current = target
            int j = nums[i] + target;
            if (map.containsKey(j)) {
                int[] res2 = new int[2];
                res2[0] = map.get(j) + 1;
                res2[1] = i + 1;
                return res2;
            }

            map.put(nums[i], i);
        }

        return new int[]{};
    }
}
