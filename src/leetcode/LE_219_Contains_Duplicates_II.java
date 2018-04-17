package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuank on 3/28/18.
 */
public class LE_219_Contains_Duplicates_II {
    /*
        Given an array of integers and an integer k, find out whether there are two distinct indices i and j in the array such that nums[i] = nums[j]
        and the absolute difference between i and j is at most k.
     */

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        if (nums == null || nums.length == 0) return false;

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (!map.containsKey(nums[i])) {
                map.put(nums[i], i);
            } else {
                if (i - map.get(nums[i]) <= k) {
                    return true;
                } else {
                    map.put(nums[i], i);
                }
            }
        }

        return false;
    }
}
