package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 3/28/18.
 */
public class LE_228_Summary_Ranges {
    /**
        Given a sorted integer array without duplicates, return the summary of its ranges.

        Example 1:
        Input: [0,1,2,4,5,7]
        Output: ["0->2","4->5","7"]
        Example 2:
        Input: [0,2,3,4,6,8,9]
        Output: ["0","2->4","6","8->9"]
     */

    public List<String> summaryRanges(int[] nums) {
        List<String> res = new ArrayList<>();
        if (nums == null || nums.length == 0) return res;

        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];

            /**
             * Another question of two pointers: one point stays and another pointer runs
             *
             * Here we need to find continuous increasing sequence. Therefore "nums[i] + 1 == nums[i + 1]"
             *
             * Since we reference to "nums[i + 1]", must use condition "i < nums.length - 1" for while loop
             */
            while (i < nums.length - 1 && nums[i] + 1 == nums[i + 1]) {
                i++;
            }

            if (num == nums[i]) {
                res.add(num + ""); //or res.add(String.valueOf(num));
            } else {
                res.add(num + "->" + nums[i]);
            }
        }

        return res;
    }
}
