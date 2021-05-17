package leetcode;

import java.util.Arrays;

/**
 * Created by yuank on 3/20/18.
 */
public class LE_169_Majority_Element {
    /**
        Given an array of size n, find the majority element. The majority element is the element
        that appears more than ⌊ n/2 ⌋ times.

        You may assume that the array is non-empty and the majority element always exist in the array.

        Possible follow up:
        If we don't know if there's majority element in array?
        See Majority_Element
     */

    //Time : O(n), Space : O(1)
    public int majorityElement1(int[] nums) {
        int res = nums[0];
        int count = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == res) {
                count++;
            } else {
                //!!! count-- and reset count : only do one of them in the loop
                if (count == 0) {
                    res = nums[i];
                } else {
                    count--;
                }
            }
        }

        return res;
    }

    //Time : O(nlogn), Space : O(1)
     public int majorityElement(int[] nums) {
         Arrays.sort(nums);
         int mid = (nums.length-1)/2;
         return nums[mid];
     }
}
