package leetcode;

/**
 * Created by yuank on 3/30/18.
 */
public class LE_231_Power_Of_Two {
    /*
        Given an integer, write a function to determine if it is a power of two.
     */

    public boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }
}
