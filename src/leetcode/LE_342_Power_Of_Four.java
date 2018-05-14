package leetcode;

/**
 * Created by yuank on 5/13/18.
 */
public class LE_342_Power_Of_Four {
    /**
     * Given an integer (signed 32 bits), write a function to check whether it is a power of 4.

         Example:
         Given num = 16, return true. Given num = 5, return false.

         Follow up: Could you solve it without loops/recursion?

       Easy
     */

    public boolean isPowerOfFour(int num) {
        return Math.log10(num) / Math.log10(4) % 1 == 0;
    }
}
