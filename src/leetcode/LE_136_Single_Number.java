package leetcode;

/**
 * Created by yuank on 3/16/18.
 */
public class LE_136_Single_Number {
    /**
        Given an array of integers, every element appears twice except for one. Find that single one.

        Note:
        Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?
     */

    public int singleNumber(int[] nums) {
        int result = 0;
        for(int num : nums){
            result^=num;
        }
        return result;
    }
}
