package leetcode;

/**
 * Created by yuank on 10/20/18.
 */
public class LE_367_Valid_Perfect_Square {
    /**
         Given a positive integer num, write a function which returns True if num is a perfect square else False.

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
}
