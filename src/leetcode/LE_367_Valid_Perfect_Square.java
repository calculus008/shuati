package leetcode;

/**
 * Created by yuank on 10/20/18.
 */
public class LE_367_Valid_Perfect_Square {
    /**
         Given a positive integer num, write a function which returns True if num is a
         perfect square else False.

         Note: Do not use any built-in library function such as sqrt.

         Example 1:

         Input: 16
         Output: true

         Example 2:

         Input: 14
         Output: false

         Easy

         Same as LE_69_Sqrt
     */

    /**
     * !!!
     * Notice the difference from the solution for LE_69_Sqrt.
     * Here we must use long for mid and sqr, since we must calculate mid * mid.
     * We must do "(int)sqr == num" and can't do "m == num / m". For example :
     *
     * if num = 5, when mid is 2, 5/2 == 2, but 5 is NOT a perfect square number.
     */
    public boolean isPerfectSquare(int num) {
        if (num <= 1) return true;

        int start = 1;
        int end = num;

        while (start + 1 < end) {
            long mid = start + (end - start) / 2;
            long sqr = mid * mid;
            if ((int)sqr == num ) {
                return true;
            } else if ((int)sqr > num) {
                end = (int)mid - 1;
            } else {
                start = (int)mid + 1;
            }
        }

//        System.out.println(start + ", " + end);
        if (start * start == num || end * end == num) return true;

        return false;
    }

    /**
     * same algorithm, simpler syntax.
     */
    public boolean isPerfectSquare2(int num) {
        if (num <= 1) return true;

        long l = 1;
        long r = num;

        while (l + 1 < r) {
            long m = l + (r - l) / 2;
            long sqr = m * m;
            if (sqr == num) {
                return true;
            } else if (sqr > num) {
                r = m;
            } else {
                l = m;
            }
        }

        return (l * l == num) || (r * r == num);
    }
}
