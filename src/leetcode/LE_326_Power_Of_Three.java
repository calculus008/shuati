package leetcode;

/**
 * Created by yuank on 5/4/18.
 */
public class LE_326_Power_Of_Three {
    /**
         Given an integer, write a function to determine if it is a power of three.

         Follow up:
         Could you do it without using any loop / recursion?

         Easy
     */

    public boolean isPowerOfThree1(int n) {
        if (n <= 0) return false;
        while (n % 3 == 0) {
            n = n / 3;
        }

        return n == 1;
    }

    public boolean isPowerOfThree(int n) {
        return Math.log10(n) / Math.log10(3) % 1 == 0;
    }
}
