package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuank on 7/12/18.
 */
public class LE_18_4Sum {
    /**
         Given an array nums of n integers and an integer target, are there elements a, b, c,
         and d in nums such that a + b + c + d = target? Find all unique quadruplets
         in the array which gives the sum of target.

         Note:

         The solution set must not contain duplicate quadruplets.

         Example:

         Given array nums = [1, 0, -1, 0, -2, 2], and target = 0.

         A solution set is:
         [
             [-1,  0, 0, 1],
             [-2, -1, 1, 2],
             [-2,  0, 0, 2]
         ]
     */

    //Time : O(n ^ 3), Space : O(n)
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        if(nums.length < 4) return res;

        //!!!!!
        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            for (int j = i + 1; j < nums.length - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;

                int start = j + 1;
                int end = nums.length - 1;

                while (start < end) {
                    int sum = nums[i] + nums[j] + nums[start] + nums[end];
                    if (sum == target) {
                        res.add(Arrays.asList(nums[i], nums[j], nums[start], nums[end]));

                        while (start < end && nums[start] == nums[start + 1]) start++;
                        while (start < end && nums[end] == nums[end - 1]) end--;

                        start++;
                        end--;
                    } else if (sum < target) {
                        start++;
                    } else {
                        end--;
                    }
                }
            }
        }

        return res;
    }

}
