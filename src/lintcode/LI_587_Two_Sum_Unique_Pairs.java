package lintcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuank on 7/9/18.
 */
public class LI_587_Two_Sum_Unique_Pairs {
    /**
         Given an array of integers, find how many unique pairs in the array such that their sum is equal to a specific target number.
         Please return the number of pairs.

         Example
         Given nums = [1,1,2,45,46,46], target = 47
         return 2

         1 + 46 = 47
         2 + 45 = 47

         Medium
     */

    /**
        HashMap Solution
        Time : O(n), Space : O(n)
        O(n) 时间复杂度，使用HashMap，Key, value 分别对应 数字和是出现过匹配的数字
        不用sort， 时间复杂度得到了提高
     **/
    public int twoSum1(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return 0;
        }

        Map<Integer, Boolean> map = new HashMap<>();

        int count = 0;
        for (int num : nums) {
            if (map.containsKey(target - num)) {
                if (!map.get(target - num)) {
                    count++;
                    map.put(target - num, true);
                    map.put(num, true);
                }
            } else {
                map.put(num, false);
            }
        }
        return count;
    }


    /**
     *  Two pointers solution
     *  Time : O(nlogn), Space : O(1)
     */
    public int twoSum6(int[] nums, int target) {
        // Write your code here
        if (nums == null || nums.length < 2)
            return 0;

        Arrays.sort(nums);

        int cnt = 0;
        int left = 0, right = nums.length - 1;

        while (left < right) {
            int v = nums[left] + nums[right];
            if (v == target) {
                cnt ++;

                left ++;
                right --;
                while (left < right && nums[left] == nums[left - 1]) left ++;
                while (left < right && nums[right] == nums[right + 1]) right --;

                //Or
//                while (left < right && nums[left] == nums[left + 1]) left++;
//                while (left < right && nums[right] == nums[right - 1]) right--;
//                start++;
//                end--;
            } else if (v > target) {
                right --;
            } else {
                left ++;
            }
        }
        return cnt;
    }

}
