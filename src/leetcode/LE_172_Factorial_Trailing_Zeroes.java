package leetcode;

/**
 * Created by yuank on 3/22/18.
 */
public class LE_172_Factorial_Trailing_Zeroes {
    /*
        Given an integer n, return the number of trailing zeroes in n!.

        Note: Your solution should be in logarithmic time complexity.
     */

    //Time : log(n), Space: O(n)
    public int trailingZeroes(int n) {
        return n == 0 ? 0 : n / 5 + trailingZeroes(n / 5);
    }
}
