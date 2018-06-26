package lintcode;

/**
 * Created by yuank on 6/25/18.
 */
public class LI_140_Fast_Power {
    /**
         Calculate the an % b where a, b and n are all 32bit integers.

         Example
         For 231 % 3 = 2

         For 1001000 % 1000 = 0

         Challenge
         O(logn)

         Easy
     */

    public int fastPower(int a, int b, int n) {
        if (n == 0) {
            return 1 % b;
        }

        if (n == 1) {
            return a % b;
        }

        long val = fastPower(a, b, n / 2);
        val = (val * val) % b;
        if (n % 2 == 1) {
            val = (val * a) % b;
        }

        return (int)val;
    }
}
