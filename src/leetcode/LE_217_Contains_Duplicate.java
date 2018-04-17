package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yuank on 3/28/18.
 */
public class LE_217_Contains_Duplicate {
    /*
        Given an array of integers, find if the array contains any duplicates.
        Your function should return true if any value appears at least twice in the array,
        and it should return false if every element is distinct.
  '*/

    //Time and Space : O(n)
    public boolean containsDuplicate(int[] nums) {
        if (nums == null || nums.length == 0) return false;

        Set<Integer> set = new HashSet<>();

        for(int num : nums) {
            if (!set.add(num)) {
                return true;
            }
        }

        return false;
    }
}
