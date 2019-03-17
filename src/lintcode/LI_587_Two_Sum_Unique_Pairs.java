package lintcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuank on 7/9/18.
 */
public class LI_587_Two_Sum_Unique_Pairs {
    /**
         Given an array of integers, find how many unique pairs in the array
         such that their sum is equal to a specific target number.
         Please return the number of pairs.

         Example
         Given nums = [1,1,2,45,46,46], target = 47
         return 2

         1 + 46 = 47
         2 + 45 = 47

         Medium
     */

    /**
        HashMap Solution, optimized for time.
        Time :  O(n),
        Space : O(n)

        O(n) 时间复杂度，使用HashMap :
          Key   - 当前数组中的数字，以及和它匹配(相加等于target)的数字。
          value - boolean value, 代表key里的数字是否已经被使用过了，也就是
                  这个数字是否已经在答案里了。

        不用sort， 时间复杂度得到了提高

        Example : [1,1,2,45,46,46]

        HashMap
        1 -> false
        2 -> false

        when it comes to 45, 47 - 45 = 2, 2 exists in HashMap, map.get(2) = false,
        which means 2 has never been paired with another number to sum to target,
        so, count++, and set 2 and 45 to true in map.

        HashMap
        1 -> false
        2 -> false
        45 ?
        res{}

        1 -> false
        2 -> true
        45 -> true
        res : {{45, 2}}

        Same for 46, count++.
         1 -> false
         2 -> true
         45 ->  true
         46 ?

         1 -> true
         2 -> true
         45 -> true
         46 -> true
         res : {{45, 3}, {46, 1}}

        when it comes to 2nd 46, map.get(1) = true, meaning it has been used. so
        we don't count it.
     **/
    public int twoSum1(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return 0;
        }

        Map<Integer, Boolean> map = new HashMap<>();
        //List<List<Integer>> res = new ArrayList<>();
        int count = 0;
        for (int num : nums) {
            if (map.containsKey(target - num)) {
                if (!map.get(target - num)) {
                    count++;
                    //List<Integer> ans = new ArrayList<>();
                    //ans.add(num);
                    //ans.add(target - num)
                    //res.add(ans);

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
     *  Two pointers solution, optimized for space
     *
     *  Time   : O(nlogn)
     *  Space : O(1)
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
