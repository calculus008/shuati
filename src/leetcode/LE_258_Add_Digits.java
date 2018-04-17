package leetcode;

/**
 * Created by yuank on 4/11/18.
 */
public class LE_258_Add_Digits {
    /**
         Given a non-negative integer num, repeatedly add all its digits until the result has only one digit.

         For example:

         Given num = 38, the process is like: 3 + 8 = 11, 1 + 1 = 2. Since 2 has only one digit, return it.

         Follow up:
         Could you do it without any loop/recursion in O(1) runtime?
     */

    //Solution 1 : Time and Space : O(n)
    public int addDigits1(int num) {
        int sum = 0;
        while (num != 0) {
            sum += num % 10;
            num /= num;
        }

        if (sum > 10) {
            return addDigits1(sum);
        } else {
            return sum;
        }
    }

    /**
     Solution 2

     num: 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 ...
     res; 1 2 3 4 5 6 7 8 9  1  2  3  4  5  6  7  8  9  1  2 ...

     9的循环

     Time and Space : O(1)
     */
    public int addDigits2(int num) {
        return (num - 1) % 9 + 1;
    }


}
