package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yuank on 3/26/18.
 */
public class LE_202_Happy_Number {
    /**
        Write an algorithm to determine if a number is "happy".

        A happy number is a number defined by the following process: Starting with any positive integer,
        replace the number by the sum of the squares of its digits, and repeat the process until the
        number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1.
        Those numbers for which this process ends in 1 are happy numbers.

        Example: 19 is a happy number

        1^2 + 9^2 = 82
        8^2 + 2^2 = 68
        6^2 + 8^2 = 100
        1^2 + 0^2 + 0^2 = 1

        Easy

        https://leetcode.com/problems/happy-number
     */

    /**
     * Math + HashSet
     *
     * Time and Space : O(log(n))
     */
    public boolean isHappy(int n) {
        //"it loops endlessly in a cycle which does not include 1.", hence, use HashSet to detect the loop in cycles
        HashSet<Integer> set = new HashSet<>();

        int squareSum = 0;//!!! must declare here for the next line

        while (set.add(squareSum)) {//!!!
            squareSum = 0;//!!!
            while (n > 0) {
                int m =  n % 10;
                squareSum += m * m;
                n /= 10;
            }
            if (squareSum == 1) {
                return true;
            } else {//!!! for the next round
                n = squareSum;
            }
        }

        return false;
    }

    public boolean isHappy_practice(int n) {
        int sum = 0;
        int x = n;

        Set<Integer> set = new HashSet<>();

        while (set.add(sum)) {
            sum = 0;

            while (x > 0) {
                int m  = x % 10;
                x /= 10;
                sum += m * m;
            }

            if (sum == 1) {
                return true;
            } else {
                x = sum;
            }
        }

        return false;
    }
}
