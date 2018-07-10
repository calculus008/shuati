package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuank on 4/11/18.
 */
public class LE_15_3Sum {
    /**
        Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0? Find all unique triplets
        in the array which gives the sum of zero.

        Note: The solution set must not contain duplicate triplets.

        For example, given array S = [-1, 0, 1, 2, -1, -4],

        A solution set is:
        [
          [-1, 0, 1],
          [-1, -1, 2]
        ]

        LE_259
     */

    /**
     * Time : O(n ^ 2)
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();

        //!!!
        Arrays.sort(nums);

        for (int i = 0 ; i < nums.length - 2; i++) {
            //!!!去重
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            int sum = 0 - nums[i];
            int start = i + 1;
            int end = nums.length - 1;

            //Same as 2sum
            while (start < end) {
                if (nums[start] + nums[end] == sum) {
                    res.add(Arrays.asList(nums[i], nums[start], nums[end]));

                    //!!!去重
                    while (start < end && nums[start] == nums[start + 1]) start++;
                    while (start < end && nums[end] == nums[end - 1]) end--;
                    start++;
                    end--;
                } else if (nums[start] + nums[end] < sum) {
                    start++;
                } else {
                    end--;
                }
            }
        }

        return res;
    }
}
