package leetcode;

/**
 * Created by yuank on 3/22/18.
 */
public class LE_191_Number_Of_1_Bits {
/**
 * Write a function that takes an unsigned integer and returns the number of ’1' bits it has (also known as the Hamming weight).
 *
 * For example, the 32-bit integer ’11' has binary representation 00000000000000000000000000001011, so the function should return 3.
 *
 * Easy
 *
 * https://leetcode.com/problems/number-of-1-bits
 */

    public int hammingWeight(int n) {
        if (n == 0) return 0;
        int res = 0;
        while (n != 0) {
            n = n & (n - 1);  //n & (n-1) : remove 1 at the lowest bit
            res++;
        }
        return res;
    }
}
